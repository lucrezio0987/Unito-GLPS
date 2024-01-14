package com.example.prva.model;

import org.apache.commons.csv.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Server {
    private static String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT_CONNECTION = 8000;
    private static final int SERVER_PORT_MAIL = 8001;
    private static final int SERVER_PORT_MODIFY = 8002;
    private static int CLIENT_PORT_BRAODCAST = 0;
    private static int CLIENT_PORT_MAIL = 0;
    private static final String[] MAIL_HEADER = {"Uuid", "Sender", "Recipients", "Object", "Text", "CreationDateTime", "LastModifyDateTime", "read"};
    private static final String[] INF_HEADER = {"Username", "LastConnectionDataTime"};

    private static String CSV_MAIL_SEND_PATH = null;
    private static String CSV_MAIL_RECEIVED_PATH = null;
    private static String CSV_INFO_PATH = null;

    private static ClientController controller;

    private static Map<String, Mail> mailSent;
    private static Map<String, Mail> mailReceived;

    private boolean connected = false;
    private static String localAddress = null;
    Thread clientMessageServerThread = null;
    Thread serverBroadcastThread = null;

    public Server(ClientController controller, String serveHost){
        this.controller = controller;
        SERVER_ADDRESS = serveHost;

        CSV_INFO_PATH = pathConstructor(null, "data");

        mailSent = new HashMap<>();
        mailReceived = new HashMap<>();

        controller.addAllRowToTable(readCSVInfo());
    }

    public boolean isConnected() {
        return connected;
    }
    private void setConnected(boolean connected) {
        if(!connected && this.connected)
            stopListeningThreads();
        this.connected = connected;
        controller.setConnection(connected);
    }
    void setAddress(String localAddress) {
        this.localAddress = localAddress;

        CSV_MAIL_SEND_PATH = pathConstructor(localAddress, "sender");
        CSV_MAIL_RECEIVED_PATH = pathConstructor(localAddress, "received");

        loadBackup();
    }
    void setServerAddress(String serverAddress) {
        SERVER_ADDRESS = serverAddress;
    }

    private void stopListeningThreads() {
        CLIENT_PORT_MAIL = 0;
        CLIENT_PORT_BRAODCAST = 0;

        if (clientMessageServerThread != null)
            clientMessageServerThread.interrupt();

        if (serverBroadcastThread != null)
            serverBroadcastThread.interrupt();

        try {
            if (clientMessageServerThread != null)
                clientMessageServerThread.join();

            if (serverBroadcastThread != null)
                serverBroadcastThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startListening(int CLIENT_PORT_MAIL, int CLIENT_PORT_BRAODCAST) {
        stopListeningThreads();

        this.CLIENT_PORT_MAIL = CLIENT_PORT_MAIL;
        this.CLIENT_PORT_BRAODCAST = CLIENT_PORT_BRAODCAST;

        clientMessageServerThread = new Thread(() -> {
            try {
                ServerSocket clientMessageServerSocket = new ServerSocket(CLIENT_PORT_MAIL);
                System.out.println("Client in ascolto sulla porta " + CLIENT_PORT_MAIL + " per le mail...");
                clientMessageServerSocket.setSoTimeout(1000);

                while (!Thread.interrupted()) {
                    try {
                        Socket socket = clientMessageServerSocket.accept();
                        try {
                            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                            Mail mail = new Gson().fromJson((String) inputStream.readObject(), Mail.class);

                            System.out.println("MAIL ricevuta da " + mail.getSender() + ": " + mail.getText());
                            addMailReceived(mail);
                            backup();

                            socket.close();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            System.out.println("Errore durante la lettura della mail dal Server");
                        }
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientMessageServerThread.start();

        serverBroadcastThread = new Thread(() -> {
            try {
                ServerSocket serverConnectionSocket = new ServerSocket(CLIENT_PORT_BRAODCAST);
                System.out.println("Client in ascolto sulla porta " + CLIENT_PORT_BRAODCAST + " per i messaggi di disconnessione del server...");
                serverConnectionSocket.setSoTimeout(1000);

                while (!Thread.interrupted()) {
                    try {
                        Socket socket = serverConnectionSocket.accept();
                        try {
                            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                            String jsonMail = (String) inputStream.readObject();
                            boolean connected = new Gson().fromJson(jsonMail, boolean.class);

                            setConnected(connected);

                            socket.close();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            System.out.println("Errore durante la lettura del messaggio dal Server");
                        }
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverBroadcastThread.start();

    }
    public void disconnectToServer() {
        setConnected(false);
        backup();
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT_CONNECTION), 2000);
            socket.setSoTimeout(2000);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            ConnectionInfo connectionInfo = new ConnectionInfo(false, localAddress);

            // INVIO: informazioni di disconnessione
            outputStream.writeObject(new Gson().toJson(connectionInfo));
            outputStream.flush();

            // RICEZIONE: risposta "Disconnessione notificata"
            // String serverReturn = (String) inputStream.readObject();
            // if(serverReturn.equals("Disconnessione notificata"))
            //     System.out.println("Disconnessione: " + localAddress);

            inputStream.close();
            outputStream.close();

            writeCSVInfo(localAddress, connectionInfo.getLastConnectionDateTime());

            socket.close();
        } catch (IOException e) {
            System.out.println("Disconnessione al Server Fallita");
            writeCSVInfo(localAddress, null);
        }
    }
    public void connectToServer() {
        loadBackup();
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT_CONNECTION), 2000);
            socket.setSoTimeout(2000);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            String lastModifyDataClient = mailSent.values().stream()
                    .flatMap(mail -> Stream.of(mail, mailReceived.get(mail.getUuid())))
                    .filter(Objects::nonNull)
                    .sorted()
                    .map(Mail::getLastModify)
                    .findFirst()
                    .orElse("01/01/0001 00:00:00");

            ConnectionInfo connectionInfo = new ConnectionInfo(true, localAddress, lastModifyDataClient);

            // INVIO: informazioni di connessione
            System.out.println(":::::::   INIZIO -> INVIO: informazioni di connessione == lastModifyDataClient: " + lastModifyDataClient);
            outputStream.writeObject(new Gson().toJson(connectionInfo));
            outputStream.flush();
            System.out.println(":::::::   FINE -> INVIO: informazioni di connessione");

            // RICEZIONE: data ultima modifica del server
            System.out.println(":::::::   INIZIO -> RICEZIONE: data ultima modifica del server + porte");
            connectionInfo = new Gson().fromJson((String) inputStream.readObject(), ConnectionInfo.class);
            String lastModifyDataServer = connectionInfo.getLastConnectionDateTime();
            System.out.println(":::::::   FINE -> RICEZIONE: data ultima modifica del server + porte == lastModifyDataServer: " + lastModifyDataServer);

            Map<String, Map<String, Mail>> mapMailClient = new HashMap<>();
            mapMailClient.put("sent",
                    mailSent.values().stream()
                            .filter(m -> m.moreRecentlyOf(lastModifyDataServer))
                            .collect(Collectors.toMap(Mail::getUuid, mail -> mail))
            );
            mapMailClient.put("received",
                    mailReceived.values().stream()
                            .filter(m -> m.moreRecentlyOf(lastModifyDataServer))
                            .collect(Collectors.toMap(Mail::getUuid, mail -> mail))
            );

            // INVIO: modifiche del client offline
            System.out.println(":::::::   INIZIO -> INVIO: modifiche del client offline == mapMailClient: " + mapMailClient.get("sent").size() + " " + mapMailClient.get("received").size());
            outputStream.writeObject(new Gson().toJson(mapMailClient));
            outputStream.flush();
            System.out.println(":::::::   FINE -> INVIO: modifiche del client offline");

            // RICEZIONE: lista modifiche client-server combinate
            System.out.println(":::::::   INIZIO -> RICEZIONE: lista modifiche client-server combinate");
            String jsonSenderCSV = (String) inputStream.readObject();

            Type type = new TypeToken<Map<String, Map<String, Mail>>>() {}.getType();
            Map<String, Map<String, Mail>> mapMailServer = new Gson().fromJson(jsonSenderCSV, type);
            System.out.println(":::::::   FINE -> RICEZIONE: lista modifiche client-server combinate == mapMailServer: " + mapMailServer.get("sent").size() + " " + mapMailServer.get("received").size());

            mailSent.putAll(mapMailServer.get("sent"));
            mailReceived.putAll(mapMailServer.get("received"));

            startListening(connectionInfo.getMailPort(), connectionInfo.getBroadcastPort());

            backup();

            outputStream.close();
            inputStream.close();

            socket.close();

            setConnected(true);
        } catch (IOException | ClassNotFoundException e) {
            stopListeningThreads();
            //e.printStackTrace();
            //System.out.println("Connessione al Server Fallita (connectToServer)");
            setConnected(false);
        }
        setConnected(false);
    }

    public ArrayList<Mail> getMails(Map<String, Mail> mailMap) {
        return new ArrayList<>(mailMap.values().stream()
                .sorted()
                .filter(mail -> !mail.isDelete())
                .toList());
    }
    public ArrayList<Mail> getMailsSent() {
        return getMails(mailSent);
    }
    public ArrayList<Mail> getMailsReceived() {
        return getMails(mailReceived);
    }

    public boolean addMailSent(Mail mail){
        boolean ret = false;
        if(isConnected()) {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_MAIL);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonMail = new Gson().toJson(mail);
                outputStream.writeObject(jsonMail);
                outputStream.flush();

                socket.close();

                ret = true;
                System.out.println("Email inviata a: " + mail.getRecipients());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("invio server fallita");
                return ret;
            }
        }
        mailSent.put(mail.getUuid(), mail);
        controller.createCardSent(mail);
        backup();
        return ret;
    }
    public void addMailReceived(Mail mail) {
        mailReceived.put(mail.getUuid(), mail);
        backup();
        System.out.println("MESSAGGI: " + mail.getText());
        Platform.runLater(() -> controller.createCardReceived(mail));
    }

    private void notifyModifyToServer(MailModifyInfo mailModifyInfo) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_MODIFY);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            String jsonModifyInfo = new Gson().toJson(mailModifyInfo);
            outputStream.writeObject(jsonModifyInfo);
            outputStream.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteMailSent(String uuid) {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(mailSent.get(uuid), localAddress, true).setDeleate());
        mailSent.get(uuid).setDelete();
        backup();
    }
    public void deleteMailReceived(String uuid) {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(mailReceived.get(uuid), localAddress, false).setDeleate());
        mailReceived.get(uuid).setDelete();
        backup();
    }
    public void deleteMailSentList() {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(null, localAddress, true).setDeleateAll());
        mailSent.values().forEach(Mail::setDelete);
        backup();
    }
    public void deleteMailReceivedList() {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(null, localAddress, false).setDeleateAll());
        mailReceived.values().forEach(Mail::setDelete);
        backup();
    }

    public void setMailReceivedRead(String uuid) {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(mailReceived.get(uuid), localAddress, false).setReaded());
        mailReceived.get(uuid).setRead();
        backup();
    }

    // -- CSV --

    private static synchronized void backup() {
        writeCSVMailSender(mailSent);
        writeCSVMailReceiver(mailReceived);
    }
    private static synchronized void loadBackup() {
        mailSent = readCSVMailSender();
        mailReceived = readCSVMailReceiver();
    }

    private static String getLastConnectionDataTime(String username) {
        String str = readCSVInfo().get(username);
        if(str == null || str.equals("Only Offline"))
            return null;
        else
            return str;
    }
    public static Map<String, String> readCSVInfo() {
        Map<String, String> records = new HashMap<>();
        if(FileExist(CSV_INFO_PATH)) {
            try (Reader reader = new FileReader(CSV_INFO_PATH);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
                for (CSVRecord csvRecord : csvParser)
                    records.put(csvRecord.get("Username"), csvRecord.get("LastConnectionDataTime"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return records;
    }
    private static void writeCSVInfo(String username, String lastConnectionDataTime) {
        Map<String, String> records = readCSVInfo();
        if(lastConnectionDataTime == null)
            records.putIfAbsent(username, "Only Offline");
        else
            records.put(username, lastConnectionDataTime);
        try (Writer writer = new FileWriter(CSV_INFO_PATH, false);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(INF_HEADER))) {
            for (Map.Entry<String, String> record : records.entrySet())
                csvPrinter.printRecord(record.getKey(), record.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.addAllRowToTable(records);
    }

    private static Map<String, Mail> readCSVMail(String path) {
        createFileIfNotExists(path, MAIL_HEADER);

        Map<String, Mail> mailMap = new HashMap<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(path), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String r0 = csvRecord.get(MAIL_HEADER[0]); // uuid
                String r1 = csvRecord.get(MAIL_HEADER[1]); // sender
                String r2 = csvRecord.get(MAIL_HEADER[2]); // recipients
                String r3 = csvRecord.get(MAIL_HEADER[3]); // object
                String r4 = csvRecord.get(MAIL_HEADER[4]); // text
                String r5 = csvRecord.get(MAIL_HEADER[5]); // creationDateTime
                String r6 = csvRecord.get(MAIL_HEADER[6]); // lastModifyDateTime
                boolean r7 = Boolean.parseBoolean(csvRecord.get(MAIL_HEADER[7])); // read

                mailMap.put(r0 , new Mail(r1, r2, r3, r4, r5, r6, r7, r0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mailMap;
    }
    private static Map<String, Mail> readCSVMailSender() {
        return readCSVMail(CSV_MAIL_SEND_PATH);
    }
    private static Map<String, Mail> readCSVMailReceiver() {
        return readCSVMail(CSV_MAIL_RECEIVED_PATH);
    }

    private static void writeCSVMail(String path, Map<String, Mail> mailList){
        Map<String, Mail> mailMap = readCSVMail(path);
        mailList.values().forEach(mail -> mailMap.put(mail.getUuid(), mail));

        try (Writer writer = new FileWriter(path, false);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(MAIL_HEADER))) {
            for (Mail mail : mailMap.values())
                csvPrinter.printRecord(
                        mail.getUuid(),
                        mail.getSender(),
                        mail.getRecipients(),
                        mail.getObject(),
                        mail.getText(),
                        mail.getCreationDateTime(),
                        mail.getLastModify(),
                        mail.getRead());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeCSVMailSender(Map<String, Mail> mailList){
        writeCSVMail(CSV_MAIL_SEND_PATH, mailList);
    }
    private static void writeCSVMailReceiver(Map<String, Mail> mailList){
        writeCSVMail(CSV_MAIL_RECEIVED_PATH, mailList);
    }

    private static boolean FileExist(String path) {
        return new File(path).exists();
    }
    private static void createFileIfNotExists(String path, String[] headers) {
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();
        if (!FileExist(path)) {
            createFileAndWriteHeader(path, headers);
        }
    }
    private static void createFileAndWriteHeader(String path, String[] headers) {

        try (Writer writer = new FileWriter(path, false);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            csvPrinter.printRecord();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String pathConstructor(String username, String type) {
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();
        if(type.equals("data") || type.equals("log"))
            return directoryPath + File.separator  + type + ".csv";
        else
            return directoryPath + File.separator + username + "-" + type + ".csv";

    }

    public void stop() {
        disconnectToServer();
        clientMessageServerThread.interrupt();
        serverBroadcastThread.interrupt();
    }

    public void clearBackupMail() {
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory())
            for (File file : Objects.requireNonNull(directory.listFiles()))
                if (file.isFile() && ( file.getName().contains("-received.csv") || file.getName().contains("-sender.csv")))
                    file.delete();
    }
    public void clearBackupData() {
        new File(CSV_INFO_PATH).delete();
        controller.clearTable();
    }

    public void clearLocalMailsSent() {
        mailSent.clear();
    }
    public void clearLocalMailsReceived() {
        mailReceived.clear();
    }

    public String getMailPort() {
        return Integer.toString(CLIENT_PORT_MAIL);
    }

    public String getBroadcastPort() {
        return Integer.toString(CLIENT_PORT_BRAODCAST);
    }
}
