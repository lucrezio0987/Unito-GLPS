package com.example.prova_server.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.csv.*;

import java.io.FileReader;
import java.io.FileWriter;

public class ServerModel_2 {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT_CONNECTION = 8000;
    private static final int SERVER_PORT_MESSAGES = 8001;
    private static final int SERVER_PORT_MODIFY = 8002;
    private static final int CLIENT_PORT_MAIL = 8003;
    private static final int CLIENT_PORT_CONNECTION = 8004;
    private static final int THREAD_POOL_SIZE = 10;

    private static SimpleStringProperty textAreaProperty = null;
    private static SimpleStringProperty countProperty = null;

    private static ServerSocket clientServerSocket = null;
    private static ServerSocket mailServerSocket  = null;
    private static ServerSocket modifySocket  = null;

    private static ExecutorService executorService;

    private static Map<String, String> clients;
    private static Map<String, UserData> userDataList;


    private static boolean isStarted = false;

    public ServerModel_2() {
        textAreaProperty = new SimpleStringProperty();
        countProperty = new SimpleStringProperty();
        countProperty.set(Integer.toString(0));

        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        clients = new ConcurrentHashMap<>();
        userDataList = new ConcurrentHashMap<>();

    }

    private Thread clientThread;
    private Thread mailThread;
    private Thread modifyThread;

    public void start() {
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
            log("Server: STARTED");
        } else {
            log("ERRORE: Server not started");
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

                clients.values().stream()
                        .filter(Objects::nonNull)
                        .forEach(this::clientBrodcastStopMessage);

            } catch (InterruptedException e) {
                e.printStackTrace();
                log("ERROR: Errore durante l'interruzione del server");
            }
    }

    private void clientBrodcastStopMessage(String address) {
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

    public SimpleStringProperty getTextAreaProperty() {
        return textAreaProperty;
    }

    public SimpleStringProperty getCountProperty() { return countProperty; }

    private static class ConnectionHandler implements Runnable {
        private Socket socket;

        public ConnectionHandler(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonConnectionInfo = (String) inputStream.readObject();
                ConnectionInfo connectionInfo = new Gson().fromJson(jsonConnectionInfo, ConnectionInfo.class);

                if (connectionInfo.isConnected()) {
                    addUser(connectionInfo.getUsername(), socket.getInetAddress().getHostAddress());

                    //TODO: Invia conferma di connessione al client

                    Map<String, String> map = new HashMap<>();
                    map.put("sent", stringCSV(pathCostructor(connectionInfo.getUsername(), "sender")));
                    map.put("received", stringCSV(pathCostructor(connectionInfo.getUsername(), "received")));

                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    String jsonList = new Gson().toJson(map);
                    outputStream.writeObject(jsonList);
                    outputStream.flush();

                } else {
                    removeUser(connectionInfo.getUsername());
                }
                socket.close();
            } catch (JsonSyntaxException | IOException | ClassNotFoundException e) {
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
                    if (modifyInfo.isDeleate())
                        userDataList.get(username).removeMailSent(mail);
                    if (modifyInfo.isRead())
                        userDataList.get(username).setReadMailSent(mail);
                    if (modifyInfo.isCreate())
                        userDataList.get(username).addMailSent(mail);
                } else {
                    if (modifyInfo.isDeleate())
                        userDataList.get(username).removeMailRecived(mail);
                    if (modifyInfo.isRead())
                        userDataList.get(username).setReadMailRecived(mail);
                    if (modifyInfo.isCreate())
                        userDataList.get(username).addMailReceived(mail);
                }

                if (modifyInfo.isDeleate())
                    log("MODIFY: ("+username+") deleate");
                if (modifyInfo.isRead())
                    log("MODIFY: ("+username+") read");
                if (modifyInfo.isCreate())
                    log("MODIFY: ("+username+") created");



                socket.close();
            } catch (JsonSyntaxException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private static int getClientNumber() {
        return (int) clients.values().stream()
                .filter(Objects::nonNull)
                .count();
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
                WriterSender(mail);
                WriterReceiver(mail);

                mail.getRecipientsList().forEach(recipient -> {
                    try {
                        sendMail(getAddressForUser(recipient), mail);
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

    private static void sendMail(String destAddress, Mail mail) throws IOException {

        if (destAddress != null) {
            log("Inoltro a: " + destAddress);
            Socket socket = new Socket(destAddress, CLIENT_PORT_MAIL);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            String jsonMail = new Gson().toJson(mail);
            outputStream.writeObject(jsonMail);
            outputStream.flush();

            socket.close();
        }
    }

    public static synchronized void addUser(String username, String address) {
        clients.put(username, address);
        log("Client: " + username + " connesso da " + address);
        Platform.runLater(() -> { countProperty.set(Integer.toString(getClientNumber()));});
    }

    public static synchronized void removeUser(String username) {
        clients.put(username, null);
        log("Client:  " + username + " disconnesso.");
    }

    public static synchronized String getAddressForUser(String username) {
        return clients.get(username);
    }

    private static synchronized void log(String newLine) {
        if (newLine.isEmpty())
            newLine = "ALLERT: String is NULL";

        String currentText = textAreaProperty.getValue();
        if (currentText == null)
            textAreaProperty.set(newLine);
        else
            textAreaProperty.set(currentText + "\n" + newLine);
        System.out.println(newLine);
    }

    private static void WriterSender(Mail mail) {
        String path = pathCostructor(mail.getSender(), "sender");

        ArrayList<Mail> mailList = new ArrayList<>();

        try {
            if (FileExist(path))
                mailList = readCSV(path);
            mailList.add(mail);
            WriteCSV(mailList, path);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void WriterReceiver(Mail mail) {
        String path = pathCostructor(mail.getRecipients(), "received");

        ArrayList<Mail> mailList = new ArrayList<>();

        try {
            if (FileExist(path))
                mailList = readCSV(path);
            mailList.add(mail);
            WriteCSV(mailList, path);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static boolean FileExist(String path) {
        return new File(path).exists();
    }

    private static ArrayList<Mail> readCSV(String path) throws IOException {
        ArrayList<Mail> mailList = new ArrayList<>();

        try (CSVParser csvParser = CSVParser.parse(new FileReader(path), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String sender = csvRecord.get("Sender");
                String recipients = csvRecord.get("Recipients");
                String object = csvRecord.get("Object");
                String text = csvRecord.get("Text");
                String date = csvRecord.get("Date");
                String time = csvRecord.get("Time");
                boolean read = Boolean.parseBoolean(csvRecord.get("Read"));

                mailList.add(new Mail(sender, recipients, object, text, date, time, read));
            }
        }
        return mailList;
    }

    public static String stringCSV(String path) {
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private static void WriteCSV(ArrayList<Mail> mailList, String path) throws IOException {

        if (!FileExist(path))
            new File(path).createNewFile();

        try (FileWriter fileWriter = new FileWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

            // Intestazioni del CSV
            csvPrinter.printRecord("Sender", "Recipients", "Text", "Object", "Date", "Time", "Read");

            // Scrivi i dati nel CSV
            for (Mail mail : mailList)
                csvPrinter.printRecord(mail.getSender(),
                        mail.getRecipients(), mail.getText(),
                        mail.getObject(), mail.getDate(), mail.getTime(), mail.getRead());
        }
    }

    private static ArrayList<Mail> sendCSV(String path){
        ArrayList<Mail> mailList = new ArrayList<>();

        try{
            boolean exist = FileExist(path);
            if(exist)
                mailList = readCSV(path);
        } catch(IOException e){
            System.err.println(e);
        }
        return mailList;
    }

    private static String pathCostructor(String username, String type){
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();

        return directoryPath + File.separator + username + "-" + type + ".csv";

//        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup" +
//                File.separator + username + "-" + type + ".csv";
    }
}
