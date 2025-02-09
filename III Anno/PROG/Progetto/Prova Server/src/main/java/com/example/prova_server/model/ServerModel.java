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
    private static final int SERVER_PORT_CONNECTION = 8000; // porta per le connessioni
    private static final int SERVER_PORT_MESSAGES = 8001; // porta per lo scambio di mail
    private static final int SERVER_PORT_MODIFY = 8002; // porta per le modifiche
    private static final int THREAD_POOL_SIZE = 10; // numero massimo di client che si possono connettere contepmoraneamente
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

    // restituisce il numero di client connessi
    private static int getClientNumber() {
        return (int) userDataList.values().stream()
                .filter(UserData::isOn)
                .count();
    }
    // costruttore
    public ServerModel() {
        textAreaProperty = new SimpleStringProperty();
        countProperty = new SimpleStringProperty();
        serveHostTextProperty = new SimpleStringProperty();

        countProperty.set(Integer.toString(0));

        logList = new ArrayList<>();

        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        userDataList = new ConcurrentHashMap<>();

    }
    // chiama il metodo di backup dei log, e avvia il thread per gestire la connessione e la ricezione di mail
    public void start() {
        loadBackupLog();
        getSetUsernamesSaved().forEach(u -> userDataList.putIfAbsent(u, new UserData(u)));

        if(isStarted)
            return;
        // thread per gestire la connessione dei client e chiamata a connectionHandler
        clientThread = new Thread(() -> {
            try {
                // apertura del serversocket sulla porta di connessione
                clientServerSocket = new ServerSocket(SERVER_PORT_CONNECTION);
                clientServerSocket.setSoTimeout(1000);
                log("Socket: clientServerSocket OPENED ("+ SERVER_PORT_CONNECTION +")");

                // il thread resta in attesa di nuove connessioni da parte di client
                while (!Thread.interrupted()) {
                    try {
                        Socket socket = clientServerSocket.accept();
                        log("[ executorService ] <-- " + socket.toString() + " (Connection)");
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
                        // chiude il socket
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

        // Thread per gestire i messaggi dei client e chiamata a messageHandler
        mailThread = new Thread(() -> {
            try {
                // apertura server socket
                mailServerSocket = new ServerSocket(SERVER_PORT_MESSAGES);
                mailServerSocket.setSoTimeout(1000);
                log("Socket: mailServerSocket OPENED ("+ SERVER_PORT_MESSAGES +")");

                // thread resta in attesa di mail inviate dai client connessi
                while (!Thread.interrupted()) {
                    try {
                        Socket socket = mailServerSocket.accept();
                        log("[ executorService ] <-- " + socket.toString() + " (Mail)");
                        executorService.submit(new MessageHandler(socket));
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // chiusura server socket
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

        // Thread per gestire le modifiche e chiamata a modifyHandler
        modifyThread = new Thread(() -> {
            try {
                // apertura serverSocket
                ServerSocket modifySocket = new ServerSocket(SERVER_PORT_MODIFY);
                modifySocket.setSoTimeout(1000);
                log("Socket: modifySocket OPENED ("+ SERVER_PORT_MODIFY +")");

                // resta in ascolto in attesa di modifiche
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
                    // chiusura socket
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

        // server avviato
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
    // interrompe i thread attivi
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

                // invio messaggio di disconnessione nel metodo clientBroadcastStopMessage
                userDataList.values().stream()
                        .filter(UserData::isOn)
                        .forEach( u -> clientBroadcastStopMessage(u.getAddress(), u.getBroadcastPort()));

                backupLog();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log("ERROR: Errore durante l'interruzione del server");
            }
    }

    // thread di connessione attivo = true else false
    public boolean ConnSocketIsOn(){
        return clientThread.isAlive();
    }
    // thread mail attivo = true else false
    public boolean MailSocketIsOn(){
        return mailThread.isAlive();
    }
    // thread delle modifiche attivo = true else false
    public boolean ModSocketIsOn(){
        return modifyThread.isAlive();
    }

    // ottiene la mappa dei client connessi
    public Map<String, UserData> getClientMap() {
        return userDataList;
    }
    // indirizzo dei client connessi
    public static synchronized String getAddressForUser(String username) {
        return userDataList.get(username).getAddress();
    }

    // handler delle connessioni e disconnessioni
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
                System.out.println(":::::::   FINE -> RICEZIONE: informazioni di connessione == isConnected " + connectionInfo.isConnected());

                String username = connectionInfo.getUsername();
                String LastConnectionDatatime = connectionInfo.getLastConnectionDateTime();
                String address = socket.getInetAddress().getHostAddress();

                if (connectionInfo.isConnected()) {
                    List<Integer> ports = selectPort(address);

                    userDataList.putIfAbsent(username, new UserData(username));
                    userDataList.get(username).setAddress(address);
                    userDataList.get(username).setMailPort(ports.get(0));
                    userDataList.get(username).setBroadcastPort(ports.get(1));
                    userDataList.get(username).setOn(true);
                    log("UTENTE: " + username +" (mp:" + ports.get(0) + ", bp:" +ports.get(1)+")");

                    connectionInfo = new ConnectionInfo(ports.get(0), ports.get(1), userDataList.get(username).getLastModifyData());

                    // INVIO: data ultima modifica del server più le porte su cui inviare mail e messaggi
                    System.out.println(":::::::   INIZIO -> INVIO: data ultima modifica del server + porte == lastModifyData: " + userDataList.get(username).getLastModifyData());
                    outputStream.writeObject(new Gson().toJson(connectionInfo));
                    outputStream.flush();
                    System.out.println(":::::::   FINE -> INVIO: data ultima modifica del server + porte");

                    // RICEZIONE: modifiche del client offline
                    System.out.println(":::::::   INIZIO -> RICEZIONE: modifiche del client offline");
                    Type type = new TypeToken<Map<String, Map<String, Mail>>>() {}.getType();
                    Map<String, Map<String, Mail>> mapMailClient = new Gson().fromJson((String) inputStream.readObject(), type);
                    System.out.println(":::::::   FINE -> RICEZIONE: modifiche del client offline == mapMailClient: " + mapMailClient.get("sent").size() + " " + mapMailClient.get("received").size());

                    // creazione mappe mail ricevute e inviate quando il client era offline
                    Map<String, Mail> mapMailSentClient = mapMailClient.get("sent");
                    Map<String, Mail> mapMailReceivedClient = mapMailClient.get("received");
                    sendNotSent(mapMailSentClient);

                    // creazione mappe mail ricevute e inviate dall'ultima modifica del server
                    Map<String, Mail> mapMailSentServer = userDataList.get(username).getMailSent(LastConnectionDatatime);
                    Map<String, Mail> mapMailReceivedServer = userDataList.get(username).getMailReceived(LastConnectionDatatime);

                    // Crea delle liste combinando le modifiche del client e del server
                    Map<String, Mail> combinedSentMap = combineMailMaps(mapMailSentClient, mapMailSentServer);
                    Map<String, Mail> combinedReceivedMap = combineMailMaps(mapMailReceivedClient, mapMailReceivedServer);

                    // Aggiornamento delle liste con le modifiche combinate
                    userDataList.get(username).updateMailSent(combinedSentMap);
                    userDataList.get(username).updateMailReceived(combinedReceivedMap);

                    // crea le liste combinate
                    Map<String, Map<String, Mail>> mapMailServer = new HashMap<>();
                    mapMailServer.put("sent", combinedSentMap);
                    mapMailServer.put("received", combinedReceivedMap);

                    // INVIO: lista modifiche client-server combinate
                    System.out.println(":::::::   INIZIO -> INVIO: lista modifiche client-server combinate == mapMailClient: " + mapMailServer.get("sent").size() + " " + mapMailServer.get("received").size());
                    outputStream.writeObject(new Gson().toJson(mapMailServer));
                    System.out.println(":::::::   FINE -> INVIO: lista modifiche client-server combinate");
                    outputStream.flush();

                    log("Client: " + username + " connesso da " + userDataList.get(username).getAddress() + " | porte: " + userDataList.get(username).getBroadcastPort() + "/" + userDataList.get(username).getMailPort());
                } else {
                    userDataList.get(username).setOn(false);

                    log("Client:  " + username + " disconnesso. (" + userDataList.get(username).getAddress()+ ")");
                }

                Platform.runLater(() -> { countProperty.set(Integer.toString(getClientNumber()));});

                outputStream.close();
                inputStream.close();
                // chiusura socket
                socket.close();
            } catch (JsonSyntaxException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    // handler per la ricezione di mail dai client, invoca il sendMail per inviare la mail
    private static class MessageHandler implements Runnable {
        private Socket socket;

        public MessageHandler(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // ricezione di una mail da parte di un client
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonMail = (String) inputStream.readObject();
                Mail mail = new Gson().fromJson(jsonMail, Mail.class);
                String sender = mail.getSender();

                log("MAIL: " + sender + " -> " + mail.getRecipients() + "[Obj: " + mail.getObject() +"]");
                log("-- lista mail caricate: " + sender);
                userDataList.get(sender).addMailSent(mail);
                log("-- lista mail salvata: " + sender);

                mail.getRecipientsList().forEach(recipient -> sendMail(recipient, mail));

                // chiusura socket
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
                // ricezione modifiche da parte di un client
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonModifyInfo = (String) inputStream.readObject();
                MailModifyInfo modifyInfo = new Gson().fromJson(jsonModifyInfo, MailModifyInfo.class);
                Mail mail = modifyInfo.getMail();
                String username = modifyInfo.getUsername();

                // aggiorna la cancellazione di una o tutte le mail dalla lista
                if(modifyInfo.getSent()) {
                    if (modifyInfo.isDeleteAll())
                        userDataList.get(username).deleteMailListSent();
                    if (modifyInfo.isDelete())
                        userDataList.get(username).deleteMailSent(mail.getUuid());
                } else {
                    if (modifyInfo.isDeleteAll())
                        userDataList.get(username).deleteMailListReceived();
                    if (modifyInfo.isDelete())
                        userDataList.get(username).deleteMailReceived(mail.getUuid());
                    if (modifyInfo.isRead())
                        userDataList.get(username).setMailRead(mail.getUuid());
                }

                if (modifyInfo.isDeleteAll())
                    log("MODIFY: ("+username+") delete ALL");
                if (modifyInfo.isDelete())
                    log("MODIFY: ("+username+") delete");
                if (modifyInfo.isRead())
                    log("MODIFY: ("+username+") read");

                // chiusura socket
                socket.close();
            } catch (JsonSyntaxException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    // invia le mail non ancora inviate, alla riconnessione
    private static void sendNotSent(Map<String, Mail> mapMailSentClient) {
        mapMailSentClient.values().stream()
                .filter(mail -> !mail.isDelete() && !mail.getRead())
                .forEach(mail -> mail.getRecipientsList().forEach(recipient -> sendMail(recipient, mail)));
    }
    // invia la mail al destinatario
    private static void sendMail(String recipient, Mail mail) {
        boolean success;
        int i;

        if (mail.isDelete())
            return;

        userDataList.putIfAbsent(recipient, new UserData(recipient));
        userDataList.get(recipient).addMailReceived(mail);

        if (userDataList.get(recipient).isOn()) {
            for (i = 0, success = false; i < 5 && !success; ++i)
                try {
                    // apre un socket sulla porta di invio delle mail
                    Socket socket = new Socket(userDataList.get(recipient).getAddress(), userDataList.get(recipient).getMailPort());

                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                    // INVIO: invio della mail
                    outputStream.writeObject(new Gson().toJson(mail));
                    outputStream.flush();

                    // RICEZIONE: conferma di invio
                    success = new Gson().fromJson((String) inputStream.readObject(), boolean.class);

                    log("MAIL INVIATA CON SUCCESSO: [" + mail.getSender() + " -> " + recipient + "]");

                    inputStream.close();
                    outputStream.close();
                    // chiusura socket
                    socket.close();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            if (!success)
                log("ERRORE: Mail non inviata a " + recipient + " dopo 5 tentativi falliti (Caso non gestito)");
            else
                log("Inoltro a: " + recipient + " (" + getAddressForUser(recipient) + ") - CLIENT ONLINE");
        } else
            log("Inoltro a: " + recipient + " - CLIENT OFFLINE (Mail Salvata)");
    }
    // invio messaggio di stop
    private void clientBroadcastStopMessage(String address, int broadcastPort) {
        try (Socket socket = new Socket(address, broadcastPort)) {
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
    // restituisce una mappa combinata della client e server
    // se entrambe sono delete = true o delete = false, restituisce quella con l'ultima modifica più recente
    // in caso contrario, restituisce quella con la delete = true
    public static Map<String, Mail> combineMailMaps(Map<String, Mail> mapClient, Map<String, Mail> mapServer) {
        log("  [+] MAPPA 1 Client: " + mapClient.values().size() + " ( di cui " + mapClient.values().stream().filter(Mail::isDelete).count() + " cancellate)");
        log("  [+] MAPPA 2 Server: " + mapServer.values().size() + " ( di cui " + mapServer.values().stream().filter(Mail::isDelete).count() + " cancellate)");
        Map<String, Mail> combMap = Stream.concat(
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
        log("  [+] MAPPA 1+2 Comb: " + combMap.values().size() + " ( di cui " + combMap.values().stream().filter(m -> m.isDelete()).count() + " cancellate)");
        return combMap;
    }
    // metodo di scelta delle porte per lo scambio di mail e di messaggi
    // sceglie la prima libera dalla 8010 alla 9000, sull'indirizzo passato come parametro
    private synchronized static List<Integer> selectPort(String address) {

        List<Integer> occupiedPorts = userDataList.values().stream()
                .filter(UserData::isOn)
                .filter(d -> d.getAddress().equals(address))
                .flatMap(d -> Stream.of(d.getBroadcastPort(), d.getMailPort()))
                .toList();

        List<Integer> ports = new ArrayList<>();

        for (Integer port = 8010; port <= 9000 && ports.size() != 2; ++port)
            if (!occupiedPorts.contains(port))
                ports.add(port);

        return ports;
    }

    // crea newLine che vengono stampate nella text dei log
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

    // effettua la lettura dei backup di log
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
    // scrive il log.csv
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
    // svuota i file di backup
    public void clearAllBackup() {
        clearBackupLog();
        clearBackupMail();
        log("BACKUP: Rimossi tutti i file di backup");
    }
    // svuota i backup delle mail
    public void clearBackupMail() {
        Arrays.stream(Objects.requireNonNull(new File(
                        System.getProperty("user.dir") +
                                File.separator + "src" +
                                File.separator + "backup").listFiles()))
                .filter(f -> f.getName().contains("-received.csv") ||
                        f.getName().contains("-sender.csv"))
                .forEach(File::delete);

        userDataList.clear();

        log("BACKUP: Rimossi i file csv di backup delle mail (Inviate e Ricevute)");
    }
    // svuota i backup dei log
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
    // aggiorna l'elenco di utenti che si sono collegati al server
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
    private static boolean FileExist(String path) {
        return Files.exists(Path.of(path));
    }
    // crea il percorso dei file
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

}
