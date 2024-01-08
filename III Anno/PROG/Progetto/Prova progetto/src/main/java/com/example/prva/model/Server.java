package com.example.prva.model;

import org.apache.commons.csv.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.concurrent.*;

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.io.FileReader;
import java.io.FileWriter;


public class Server {
    private static final String SERVER_ADDRESS = "localhost";
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

    private  static List<MailModifyInfo> mailSentOfflineModify;
    private  static List<MailModifyInfo> mailReceivedOfflineModify;

    private boolean connected = false;
    private static String localAddress = null;
    Thread clientMessageServerThread = null;
    Thread serverConnectionThread = null;

    public Server(ClientController controller){
        this.controller = controller;

        CSV_INFO_PATH = pathConstructor(null, "data");

        mailSent = new HashMap<>();
        mailReceived = new HashMap<>();
        mailSentOfflineModify = new CopyOnWriteArrayList<>();
        mailReceivedOfflineModify = new CopyOnWriteArrayList<>();

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

                            //ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                            //Mail mail = (Mail) inputStream.readObject();

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
    void disconnectToServer() {
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
        }
    }
    void connectToServer() {
        setConnected(false);
        loadBackup();
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_CONNECTION);
            ConnectionInfo connectionInfo = new ConnectionInfo(true, localAddress, readCSVInfo().get(localAddress));

            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonConnectionInfo = new Gson().toJson(connectionInfo);
                outputStream.writeObject(jsonConnectionInfo);
                outputStream.flush();
                setConnected(true);

                mailSentOfflineModify.clear();
                mailReceivedOfflineModify.clear();

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
            System.out.println("Connessione al Server Fallita");
        }
    }

    public ArrayList<Mail> getMailSent() {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return new ArrayList<>(mailSent.values().stream()
                .filter(mail -> !mail.isDelete())
                .sorted(Comparator.comparing(mail -> {
                    try {
                        return formatDateTime.parse(mail.getCreationDateTime());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .toList());
    }
    public ArrayList<Mail> getMailReceived() {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return new ArrayList<>(mailReceived.values().stream()
                .filter(mail -> !mail.isDelete())
                .sorted(Comparator.comparing(mail -> {
                    try {
                        return formatDateTime.parse(mail.getCreationDateTime());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .toList());
    }

    public void addMailSent(Mail mail){
        if(isConnected()) {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_MAIL);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonMail = new Gson().toJson(mail);
                outputStream.writeObject(jsonMail);
                outputStream.flush();

                socket.close();

                System.out.println("Email inviata a: " + mail.getRecipients());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("invio server fallita");
            }
        } else {
            mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, true).setCreated());
        }
        mailSent.put(mail.getUuid(), mail);
        controller.createCardSent(mail);
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
        MailModifyInfo MailModifyInfo =
                new MailModifyInfo(mailSent.remove(uuid), localAddress, true).setDeleate();

        if(isConnected())
            notifyModifyToServer(MailModifyInfo);
        else
            mailReceivedOfflineModify.add(MailModifyInfo);
    }
    public void deleteMailReceived(String uuid) {
        MailModifyInfo MailModifyInfo =
                new MailModifyInfo(mailReceived.remove(uuid), localAddress, false).setDeleate();

        if(isConnected())
            notifyModifyToServer(MailModifyInfo);
        else
            mailReceivedOfflineModify.add(MailModifyInfo);
    }
    public void deleteMailSentList() {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(null, localAddress, true).setDeleateAll());
        else
            mailSent.values().forEach(mail ->
                    mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, true).setDeleate())
            );
        mailSent.clear();
        backup();
    }
    public void deleteMailReceivedList() {
        if(isConnected())
            notifyModifyToServer(new MailModifyInfo(null, localAddress, false).setDeleateAll());
        else
            mailReceived.values().forEach(mail ->
                    mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, false).setDeleate())
            );
        mailReceived.clear();

        backup();
    }

    public void setMailReceivedRead(String uuid) {
        mailReceived.get(uuid).setRead();

        MailModifyInfo MailModifyInfo = new MailModifyInfo(mailReceived.get(uuid), localAddress, false).setReaded();
        if(isConnected())
            notifyModifyToServer(MailModifyInfo);
        else
            mailReceivedOfflineModify.add(MailModifyInfo);
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

    private static Map<String, String> readCSVInfo() {
        createFileIfNotExists(CSV_INFO_PATH, INF_HEADER);

        Map<String, String> records = new HashMap<>();
        try (Reader reader = new FileReader(CSV_INFO_PATH);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord csvRecord : csvParser)
                records.put(csvRecord.get("Username"), csvRecord.get("LastConnectionDataTime"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
    private static void writeCSVInfo(String username, String lastConnectionDataTime) {
        Map<String, String> records = readCSVInfo();
        records.put(username, lastConnectionDataTime);
        try (Writer writer = new FileWriter(CSV_INFO_PATH, false);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(INF_HEADER))) {
            for (Map.Entry<String, String> record : records.entrySet())
                csvPrinter.printRecord(record.getKey(), record.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(type.equals("data"))
            return directoryPath + File.separator  + type + ".csv";
        else
            return directoryPath + File.separator + username + "-" + type + ".csv";
    }

    public void stop() {
        disconnectToServer();
        clientMessageServerThread.interrupt();
        serverConnectionThread.interrupt();
    }

}
