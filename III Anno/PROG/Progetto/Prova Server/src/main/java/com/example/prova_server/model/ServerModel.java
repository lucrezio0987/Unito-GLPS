package com.example.prova_server.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.csv.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerModel {
    private static final int SERVER_PORT_CONNECTION = 8000;
    private static final int SERVER_PORT_MESSAGES = 8001;
    private static final int SERVER_PORT_MODIFY = 8002;
    private static final int CLIENT_PORT_MAIL = 8003;
    private static final int CLIENT_PORT_CONNECTION = 8004;
    private static final int THREAD_POOL_SIZE = 10;
    private static final String[] MAIL_HEADER = {"Uuid", "Sender", "Recipients", "Object", "Text", "CreationDateTime", "LastModifyDateTime", "read"};
    private static SimpleStringProperty textAreaProperty = null;
    private static SimpleStringProperty countProperty = null;
    private static SimpleStringProperty serveHostTextProperty = null;
    private static ServerSocket clientServerSocket = null;
    private static ServerSocket mailServerSocket  = null;
    private static ServerSocket modifySocket  = null;
    private static ArrayList<String> logList = null;
    private Thread clientThread;
    private Thread mailThread;
    private Thread modifyThread;
    private static ExecutorService executorService;
    private static Map<String, UserData> userDataList;
    private static boolean isStarted = false;

    public SimpleStringProperty getTextAreaProperty() {
        return textAreaProperty;
    }
    public SimpleStringProperty getCountProperty() { return countProperty; }
    public SimpleStringProperty getServeHostTextProperty() {
        return serveHostTextProperty;
    }

    private static int getClientNumber() {
        return (int) userDataList.values().stream()
                .filter(UserData::isOn)
                .count();
    }

    public ServerModel() {
        textAreaProperty = new SimpleStringProperty();
        countProperty = new SimpleStringProperty();
        serveHostTextProperty = new SimpleStringProperty();

        countProperty.set(Integer.toString(0));

        logList = new ArrayList<>();

        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        userDataList = new ConcurrentHashMap<>();

    }
    public void start() {
        loadBackupLog();
        getSetUsernamesSaved().forEach(ServerModel::loadBackup);

        if(isStarted)
            return;
            // Thread per gestire la connessione dei client
        clientThread = new Thread(() -> {
            try {
                clientServerSocket = new ServerSocket(SERVER_PORT_CONNECTION);
                clientServerSocket.setSoTimeout(1000);
                log("Socket: clientServerSocket OPENED ("+ SERVER_PORT_CONNECTION +")");

                while (!Thread.interrupted()) {
                    try {
                        Socket socket = clientServerSocket.accept();
                        log("Connection: Connessione Client ("+SERVER_PORT_CONNECTION+"):  " + socket.toString());
                        executorService.submit(new ConnectionHandler(socket));
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (clientServerSocket != null && !clientServerSocket.isClosed()) {
                        clientServerSocket.close();
                        log("Socket: clientServerSocket CLOSED");
                    } else {
                        log("ERROR: clientServerSocket already CLOSED");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();


        // Thread per gestire i messaggi dei client
        mailThread = new Thread(() -> {
            try {
                mailServerSocket = new ServerSocket(SERVER_PORT_MESSAGES);
                mailServerSocket.setSoTimeout(1000);
                log("Socket: mailServerSocket OPENED ("+ SERVER_PORT_MESSAGES +")");

                while (!Thread.interrupted()) {
                    try {
                        Socket socket = mailServerSocket.accept();
                        log("Mail: Ricevuta Mail ("+ SERVER_PORT_MESSAGES +"): " + socket.toString());
                        executorService.submit(new MessageHandler(socket));
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mailServerSocket != null && !mailServerSocket.isClosed()) {
                        mailServerSocket.close();
                        log("Socket: mailServerSocket CLOSED");
                    } else {
                        log("ERROR: mailServerSocket already CLOSED");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mailThread.start();

        modifyThread = new Thread(() -> {
            try {
                ServerSocket modifySocket = new ServerSocket(SERVER_PORT_MODIFY);
                modifySocket.setSoTimeout(1000);
                log("Socket: modifySocket OPENED ("+ SERVER_PORT_MODIFY +")");

                while (!Thread.interrupted()) {
                    try {
                        Socket socket = modifySocket.accept();
                        log("Mail: Ricevuta ModifyMail (" + SERVER_PORT_MODIFY + "): " + socket.toString());
                        executorService.submit(new ModifyHandler(socket));
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (modifySocket != null && !modifySocket.isClosed()) {
                        modifySocket.close();
                        log("Socket: modifySocket CLOSED");
                    } else {
                        log("ERROR: modifySocket already CLOSED");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        modifyThread.start();

        if(clientThread.isAlive() && mailThread.isAlive()) {
            isStarted = true;
            log("Server: STARTED ");
        } else {
            log("ERRORE: Server not started");
        }

        try {
            serveHostTextProperty.set(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if(isStarted)
            try {
                clientThread.interrupt();
                mailThread.interrupt();
                modifyThread.interrupt();

                executorService.shutdownNow();

                while (!executorService.isTerminated())
                    Thread.sleep(1);

                log("Server: STOPED");
                isStarted = false;

                userDataList.values().stream()
                        .filter(UserData::isOn)
                        .forEach( u -> clientBroadcastStopMessage(u.getClientAddress()));

                backupLog();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log("ERROR: Errore durante l'interruzione del server");
            }
    }

    public boolean ConnSocketIsOn(){
        return clientThread.isAlive();
    }
    public boolean MailSocketIsOn(){
        return mailThread.isAlive();
    }
    public boolean ModSocketIsOn(){
        return modifyThread.isAlive();
    }

    public Map<String, UserData> getClientMap() {
        return userDataList;
    }

    private static class ConnectionHandler implements Runnable  {
        private Socket socket;

        public ConnectionHandler(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                socket.setSoTimeout(2000);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                // RICEZIONE: informazioni di connessione
                System.out.println(":::::::   INIZIO -> RICEZIONE: informazioni di connessione");
                ConnectionInfo connectionInfo = new Gson().fromJson((String) inputStream.readObject(), ConnectionInfo.class);
                System.out.println(":::::::   FINE -> RICEZIONE: informazioni di connessione == connectionInfo " + connectionInfo.getLastConnectionDateTime());

                String username = connectionInfo.getUsername();
                String LastConnectionDatatime = connectionInfo.getLastConnectionDateTime();

                if (connectionInfo.isConnected()) {
                    addUser(username, socket.getInetAddress().getHostAddress());
                    loadBackup(username);

                    //String lastModifyData = String.valueOf(
                    //        Objects.requireNonNull(
                    //                        Stream.concat(
                    //                                        userDataList.get(username).getMailSent().values().stream(),
                    //                                        userDataList.get(username).getMailReceived().values().stream()
                    //                                )
                    //                                .sorted()
                    //                                .findFirst()
                    //                                .orElse(null))
                    //                .getLastModify()
                    //);

                    String lastModifyData = userDataList.get(username).getMailSent().values().stream()
                            .flatMap(mail -> Stream.of(mail, userDataList.get(username).getMailReceived().get(mail.getUuid())))
                            .filter(Objects::nonNull)
                            .sorted()
                            .map(Mail::getLastModify)
                            .findFirst()
                            .orElse(null);

                    // INVIO: data ultima modifica del server
                    System.out.println(":::::::   INIZIO -> INVIO: data ultima modifica del server == lastModifyData: " + lastModifyData);
                    outputStream.writeObject(new Gson().toJson(lastModifyData));
                    outputStream.flush();
                    System.out.println(":::::::   FINE -> INVIO: data ultima modifica del server");

                    // RICEZIONE: modifiche del client offline
                    System.out.println(":::::::   INIZIO -> RICEZIONE: modifiche del client offline");
                    Type type = new TypeToken<Map<String, Map<String, Mail>>>() {}.getType();
                    Map<String, Map<String, Mail>> mapMailClient = new Gson().fromJson((String) inputStream.readObject(), type);
                    System.out.println(":::::::   FINE -> RICEZIONE: modifiche del client offline == mapMailClient: " + mapMailClient.get("sent").size() + " " + mapMailClient.get("received").size());

                    Map<String, Mail> mapMailSentServer = userDataList.get(username).getMailSent(LastConnectionDatatime);
                    Map<String, Mail> mapMailReceivedServer = userDataList.get(username).getMailSent(LastConnectionDatatime);
                    Map<String, Mail> mapMailSentClient = mapMailClient.get("sent");
                    Map<String, Mail> mapMailReceivedClient = mapMailClient.get("received");

                    // Crea delle liste combinando le modifiche del client e del server
                    Map<String, Mail> combinedSentMap = combineMailMaps(mapMailSentClient, mapMailSentServer);
                    Map<String, Mail> combinedReceivedMap = combineMailMaps(mapMailReceivedClient, mapMailReceivedServer);

                    // Aggiornamento delle liste con le modifiche combinate
                    userDataList.get(username).updateMailSent(combinedSentMap);
                    userDataList.get(username).updateMailReceived(combinedReceivedMap);

                    Map<String, Map<String, Mail>> mapMailServer = new HashMap<>();
                    mapMailServer.put("sent", combinedSentMap);
                    mapMailServer.put("received", combinedReceivedMap);

                    // INVIO: lista modifiche client-server combinate
                    System.out.println(":::::::   INIZIO -> INVIO: lista modifiche client-server combinate == mapMailClient: " + mapMailServer.get("sent").size() + " " + mapMailServer.get("received").size());
                    outputStream.writeObject(new Gson().toJson(mapMailServer));
                    System.out.println(":::::::   FINE -> INVIO: lista modifiche client-server combinate");
                    outputStream.flush();

                    log("Client: " + username + " connesso da " + userDataList.get(username).getClientAddress());

                } else {

                    userDataList.get(username).setOn(false);
                    backup(username);
                    Platform.runLater(() -> { countProperty.set(Integer.toString(getClientNumber()));});

                    // INVIO: risposta "Disconnessione notificata"
                    System.out.println(":::::::   INVIO: risposta Disconnessione notificata");
                    outputStream.writeObject("Disconnessione notificata");
                    outputStream.flush();
                    System.out.println(":::::::   INVIO: risposta Disconnessione notificata");

                    log("Client:  " + username + " disconnesso. (" + userDataList.get(username).getClientAddress()+ ")");
                }

                outputStream.close();
                inputStream.close();

                socket.close();
            } catch (JsonSyntaxException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    private static class MessageHandler implements Runnable {
        private Socket socket;

        public MessageHandler(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonMail = (String) inputStream.readObject();
                Mail mail = new Gson().fromJson(jsonMail, Mail.class);
                String sender = mail.getSender();

                log("MAIL: " + sender + " -> " + mail.getRecipients() + "[Obj: " + mail.getObject() +"]");
                loadBackup(sender);
                log("-- lista mail caricate: " + sender);
                userDataList.get(sender).addMailSent(mail);
                backup(sender);
                log("-- lista mail salvata: " + sender);

                mail.getRecipientsList().forEach(recipient -> {
                    try {
                        sendMail(recipient, mail);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private static class ModifyHandler implements Runnable {
        private Socket socket;

        public ModifyHandler(Socket modifySocket) {
            this.socket = modifySocket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonModifyInfo = (String) inputStream.readObject();
                MailModifyInfo modifyInfo = new Gson().fromJson(jsonModifyInfo, MailModifyInfo.class);
                Mail mail = modifyInfo.getMail();
                String username = modifyInfo.getUsername();

                if(modifyInfo.getSent()) {
                    if (modifyInfo.isDeleteAll())
                        userDataList.get(username).clearMailListSent();
                    if (modifyInfo.isDelete())
                        userDataList.get(username).removeMailSent(mail);
                    if (modifyInfo.isCreate())
                        userDataList.get(username).addMailSent(mail);
                } else {
                    if (modifyInfo.isDeleteAll())
                        userDataList.get(username).clearMailListRecived();
                    if (modifyInfo.isDelete())
                        userDataList.get(username).removeMailRecived(mail);
                    if (modifyInfo.isRead())
                        userDataList.get(username).setReadMailRecived(mail);
                    if (modifyInfo.isCreate())
                        userDataList.get(username).addMailReceived(mail);
                }

                if (modifyInfo.isDeleteAll())
                    log("MODIFY: ("+username+") deleate ALL");
                if (modifyInfo.isDelete())
                    log("MODIFY: ("+username+") deleate");
                if (modifyInfo.isRead())
                    log("MODIFY: ("+username+") read");
                if (modifyInfo.isCreate())
                    log("MODIFY: ("+username+") created");

                backup(username);

                socket.close();
            } catch (JsonSyntaxException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendMail(String recipient, Mail mail) throws IOException {

        loadBackup(recipient);
        userDataList.get(recipient).addMailReceived(mail);
        backup(recipient);

        if (userDataList.get(recipient).isOn()) {
            Socket socket = new Socket(getAddressForUser(recipient), CLIENT_PORT_MAIL);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            String jsonMail = new Gson().toJson(mail);
            outputStream.writeObject(jsonMail);
            outputStream.flush();
            socket.close();
        }

        log("Inoltro a: " + recipient + " (" + getAddressForUser(recipient) + ") - ON:" + userDataList.get(recipient).isOn());
    }
    private void clientBroadcastStopMessage(String address) {
        try (Socket socket = new Socket(address, CLIENT_PORT_CONNECTION)) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                String jsonStartedInfo = new Gson().toJson(isStarted);
                outputStream.writeObject(jsonStartedInfo);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Invio fallito del messaggio di disattivazione del server");
        }
    }

    private static synchronized void backup(String username) {
        userDataList.putIfAbsent(username, new UserData(username));
        writeCSVMail(pathConstructor(username, "received"), userDataList.get(username).getMailReceived());
        writeCSVMail(pathConstructor(username, "sender"), userDataList.get(username).getMailSent());
    }
    private static synchronized void loadBackup(String username) {
        userDataList.putIfAbsent(username, new UserData(username));
        userDataList.get(username).loadSendMails(readCSVMail(pathConstructor(username, "sender")));
        userDataList.get(username).loadReceivedMails(readCSVMail(pathConstructor(username, "received")));
    }
    private void loadBackupLog() {
        if(FileExist(pathConstructor(null, "log")))
            try (Reader reader = new FileReader(pathConstructor(null, "log"));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
                for (CSVRecord csvRecord : csvParser){
                    String currentText = textAreaProperty.getValue();
                    if (currentText == null)
                        textAreaProperty.set(csvRecord.get(0));
                    else
                        textAreaProperty.set(currentText + "\n" + csvRecord.get(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    private void backupLog() {
        logList.add("------------------------------------------[ " +
                new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date())
                + " ]------------------------------------------");
        try (Writer writer = new FileWriter(pathConstructor(null, "log"), true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (String log : logList)
                csvPrinter.printRecord(log);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addUser(String username, String address) {
        userDataList.putIfAbsent(username, new UserData(username, address));
        userDataList.get(username).setAddress(address);
        userDataList.get(username).setOn(true);

        Platform.runLater(() -> { countProperty.set(Integer.toString(getClientNumber()));});
    }
    public static synchronized String getAddressForUser(String username) {
        return userDataList.get(username).getClientAddress();
    }
    private static synchronized void log(String newLine) {
        if (newLine.isEmpty())
            newLine = "ALLERT: String is NULL";
        newLine = "<" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ">  " + newLine;

        String currentText = textAreaProperty.getValue();
        if (currentText == null)
            textAreaProperty.set(newLine);
        else
            textAreaProperty.set(currentText + "\n" + newLine);
        System.out.println(newLine);

        logList.add(newLine);
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
    private static void writeCSVMail(String path, Map<String, Mail> mailList){
        createFileIfNotExists(path, MAIL_HEADER);
        if(mailList != null) {
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
    }
    private static boolean FileExist(String path) {
        return Files.exists(Path.of(path));
    }
    private static void createFileIfNotExists(String path, String[] headers) {
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
    private static String pathConstructor(String username, String type){
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();
        if(type.equals("log"))
            return directoryPath + File.separator  + type + ".csv";
        else
            return directoryPath + File.separator + username + "-" + type + ".csv";
    }

    public void clearAllBackup() {
        clearBackupLog();
        clearBackupMail();
        log("BACKUP: Rimossi tutti i file di backup");
    }
    public void clearBackupMail() {
        Arrays.stream(Objects.requireNonNull(new File(
                        System.getProperty("user.dir") +
                                File.separator + "src" +
                                File.separator + "backup").listFiles()))
                .filter(f -> f.getName().contains("-received.csv") ||
                        f.getName().contains("-sender.csv"))
                .forEach(File::delete);

        userDataList.forEach((u, data) -> {
            data.clearMailListRecived();
            data.clearMailListRecived();
        });

        log("BACKUP: Rimossi i file csv di backup delle mail (Inviate e Ricevute)");
    }
    public void clearBackupLog() {

        Arrays.stream(Objects.requireNonNull(new File(
                        System.getProperty("user.dir") +
                                File.separator + "src" +
                                File.separator + "backup").listFiles()))
                .filter(f -> f.getName().equals("log.csv"))
                .forEach(File::delete);

        textAreaProperty.set(null);
        log("BACKUP: pulita la storiografia dei log (log.csv)");
    }


    private Set<String> getSetUsernamesSaved() {
        Set<String> utentiConDati = new HashSet<>();

        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory() && directory.listFiles() != null)
            for (File file : directory.listFiles())
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.matches("(.+)-(received|sender)\\.csv")) {
                        String[] parts = fileName.split("-");
                        utentiConDati.add(parts[0]);
                    }
                }

        return utentiConDati;
    }
    private static String getPublicIP() {
        URL url = null;
        try {
            url = new URL("https://api64.ipify.org?format=json");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return parseIPFromResponse(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }
    private static String parseIPFromResponse(String response) {
        // Modifica questo metodo in base al formato della risposta ottenuta dal servizio
        // In questo esempio, si assume che la risposta sia in formato JSON
        // e l'indirizzo IP è ottenuto dall'oggetto JSON restituito
        // Ad esempio, se la risposta fosse {"ip":"123.456.789.012"}, questo metodo restituirebbe "123.456.789.012"
        // Considera l'utilizzo di una libreria JSON più avanzata in un contesto di produzione

        // Questo è solo un esempio di parsing e può non essere accurato a seconda del formato reale della risposta
        int startIndex = response.indexOf("\"ip\":\"") + 6;
        int endIndex = response.indexOf("\"", startIndex);
        return response.substring(startIndex, endIndex);
    }

    public static Map<String, Mail> combineMailMaps(Map<String, Mail> mapClient, Map<String, Mail> mapServer) {
        return Stream.concat(
                mapClient.entrySet().stream(),
                mapServer.entrySet().stream()
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (mailClient, mailServer) -> {
                    boolean isDeleteOnClient = mailClient.isDelete();
                    boolean isDeleteOnServer = mailServer.isDelete();
                    if (isDeleteOnClient ^ isDeleteOnServer)
                        return isDeleteOnClient ? mailClient : mailServer;
                    else
                        return mailClient.moreRecentlyOf(mailServer.getLastModify()) ? mailClient : mailServer;
                })
        );
    }
}
