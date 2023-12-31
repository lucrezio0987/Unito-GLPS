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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;

public class ServerModel_2 {
    private static SimpleStringProperty textAreaProperty = null;
    private static SimpleStringProperty countProperty = null;

    private static ServerSocket clientServerSocket = null;
    private static ServerSocket mailServerSocket  = null;

    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService;

    private static Map<String, String> clients;


    private static boolean isStarted = false;

    public ServerModel_2() {
        textAreaProperty = new SimpleStringProperty();
        countProperty = new SimpleStringProperty();
        countProperty.set(Integer.toString(0));

        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        clients = new ConcurrentHashMap<>();

    }

    private Thread clientThread;
    private Thread mailThread;

    public void start() {
        if(isStarted)
            return;
            // Thread per gestire la connessione dei client
        clientThread = new Thread(() -> {
            try {
                clientServerSocket = new ServerSocket(8000);
                clientServerSocket.setSoTimeout(2000);
                log("Socket: clientServerSocket OPENED (8000)");

                while (!Thread.interrupted()) {
                    try (Socket socket = clientServerSocket.accept()) {
                        log("Connection: Connessione Client (8000):  " + socket.toString());
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
                ServerSocket mailServerSocket = new ServerSocket(8001);
                mailServerSocket.setSoTimeout(2000);
                log("Socket: mailServerSocket OPENED (8001)");

                while (!Thread.interrupted()) {
                    try (Socket socket = mailServerSocket.accept()) {
                        log("Mail: Ricevuta Mail (8001): " + socket.toString());
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

                executorService.shutdownNow();

                while (!executorService.isTerminated())
                    Thread.sleep(1);

                log("Server: STOPED");
                isStarted = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                log("ERROR: Errore durante l'interruzione del server");
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
            if (socket.isClosed()) {
                log("ERROR: Il socket è chiuso. Impossibile leggere dall'input stream.");
                return;
            }

            try {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonConnectionInfo = (String) inputStream.readObject();
                ConnectionInfo connectionInfo = new Gson().fromJson(jsonConnectionInfo, ConnectionInfo.class);

                if (connectionInfo.isConnected()) {
                    addUser(connectionInfo.getUsername(), socket.getInetAddress().getHostAddress());
                    //TODO: Invia conferma di connessione al client
                    HashMap<String, ArrayList<Mail>> map = new HashMap<>();
                    map.put("sent", sendCSV(pathCostructor(connectionInfo.getUsername(), "sender")));
                    map.put("received", sendCSV(pathCostructor(connectionInfo.getUsername(), "received")));
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
            Socket socket = new Socket(destAddress, 8002);

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
            boolean exist = FileExist(path);
            if (exist)
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
            boolean exist = FileExist(path);
            if (exist)
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

    private static void WriteCSV(ArrayList<Mail> mailList, String path) throws IOException {
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
        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup" +
                File.separator + username + "-" + type + ".csv";
    }
}
