package com.example.prva.model;

import org.apache.commons.csv.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.io.FileReader;
import java.io.FileWriter;


public class Server {
    private static String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT_CONNECTION = 8000;
    private static final int SERVER_PORT_MAIL = 8001;
    private static final int SERVER_PORT_MODIFY = 8002;
    private static final int CLIENT_PORT_MAIL = 8003;
    private static final int CLIENT_PORT_CONNECTION = 8004;
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
    Thread serverConnectionThread = null;

    public Server(ClientController controller, String serveHost){
        this.controller = controller;
        SERVER_ADDRESS = serveHost;

        CSV_INFO_PATH = pathConstructor(null, "data");

        mailSent = new HashMap<>();
        mailReceived = new HashMap<>();

        controller.addAllRowToTable(readCSVInfo());

        startListening();
    }

    public boolean isConnected() {
        return connected;
    }
    private void setConnected(boolean connected) {
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

    private void startListening() {

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
                            String jsonMail = (String) inputStream.readObject();
                            Mail mail = new Gson().fromJson(jsonMail, Mail.class);

                            System.out.println("MAIL ricevuta da " + mail.getSender() + ": " + mail.getText());
                            addMailReceived(mail);

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

        serverConnectionThread = new Thread(() -> {
            try {
                ServerSocket serverConnectionSocket = new ServerSocket(CLIENT_PORT_CONNECTION);
                System.out.println("Client in ascolto sulla porta " + CLIENT_PORT_CONNECTION + " per i messaggi di disconnessione del server...");
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
        serverConnectionThread.start();

    }
    public void disconnectToServer() {
        setConnected(false);
        backup();
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_CONNECTION);
            ConnectionInfo connectionInfo = new ConnectionInfo(false, localAddress);
            writeCSVInfo(localAddress, connectionInfo.getLastConnectionDateTime());

            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonConnectionInfo = new Gson().toJson(connectionInfo);
                outputStream.writeObject(jsonConnectionInfo);
                outputStream.flush();

                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    String jsonSenderCSV = (String) inputStream.readObject();
                    if(jsonSenderCSV.equals("Null"))
                        System.out.println("Disconnessione: " + localAddress);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Disconnessione al Server Fallita");
            writeCSVInfo(localAddress, null);
        }
    }
    public void connectToServer() {
        setConnected(false);
        loadBackup();
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT_CONNECTION), 1000);
            ConnectionInfo connectionInfo = new ConnectionInfo(true, localAddress, getLastConnectionDataTime(localAddress));

            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonConnectionInfo = new Gson().toJson(connectionInfo);
                outputStream.writeObject(jsonConnectionInfo);
                outputStream.flush();
                setConnected(true);

                    try {
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        String jsonSenderCSV = (String) inputStream.readObject();

                        Type type = new TypeToken<Map<String, Map<String, Mail>>>() {}.getType();
                        Map<String, Map<String, Mail>> map = new Gson().fromJson(jsonSenderCSV, type);

                        System.out.println("Server: (" + mailSent.size()+ ") mailSent nuove: " + map.get("sent").size());
                        System.out.println("Server: (" + mailReceived.size() + ") mailReceived nuove: " + map.get("received").size());

                        if(!map.get("sent").isEmpty() && !map.get("received").isEmpty()) {
                            map.get("sent").values().forEach(mail -> mailSent.put(mail.getUuid(), mail));
                            map.get("received").values().forEach(mail -> mailReceived.put(mail.getUuid(), mail));
                            backup();
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Connessione al Server Fallita (connectToServer)");
        }
    }

    public ArrayList<Mail> getMails(Map<String, Mail> mailMap) {
        return new ArrayList<>(mailMap.values().stream()
                .filter(mail -> !mail.isDelete())
                .sorted(Comparator.comparing(mail -> {
                    try {
                        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(mail.getCreationDateTime());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }))
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
                backup();
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
        return ret;
    }
    public void addMailReceived(Mail mail) {
        mailReceived.put(mail.getUuid(), mail);
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
    }
    public void deleteMailReceived(String uuid) {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(mailReceived.get(uuid), localAddress, false).setDeleate());
        mailReceived.get(uuid).setDelete();
    }
    public void deleteMailSentList() {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(null, localAddress, true).setDeleateAll());
        mailSent.values().forEach(Mail::setDelete);
    }
    public void deleteMailReceivedList() {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(null, localAddress, false).setDeleateAll());
        mailReceived.values().forEach(Mail::setDelete);
    }

    public void setMailReceivedRead(String uuid) {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(mailReceived.get(uuid), localAddress, false).setReaded());
        mailReceived.get(uuid).setRead();
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
        createFileIfNotExists(path, MAIL_HEADER);
        Map<String, Mail> mailMap = readCSVMail(path);
        mailList.values().stream()
                .filter(mail -> !mail.isDelete())
                .forEach(mail -> mailMap.put(mail.getUuid(), mail));

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
        if(!FileExist(path))
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
        serverConnectionThread.interrupt();
    }

    public void clearAllBackup() {
        clearBackupMail();
        clearBackupData();
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
}
