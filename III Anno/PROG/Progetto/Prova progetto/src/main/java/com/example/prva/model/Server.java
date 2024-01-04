package com.example.prva.model;

import org.apache.commons.csv.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;

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
    private ClientController controller;

    private List<Mail> mailSent;
    private List<Mail> mailReceived;

    private List<MailModifyInfo> mailSentOfflineModify;
    private List<MailModifyInfo> mailReceivedOfflineModify;

    private Mail lastMail = null;

    private boolean connected = false;
    private String localAddress = null;
    Thread clientMessageServerThread = null;
    Thread serverConnectionThread = null;

    public Server(ClientController controller){
        this.controller = controller;

        mailSent = new CopyOnWriteArrayList<>();
        mailReceived = new CopyOnWriteArrayList<>();
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

        disconnectToServer(this.localAddress);
        this.localAddress = localAddress;
        connectToServer(localAddress);

        setMailSent();
        setMailReceived();
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
    private void disconnectToServer(String username) {
        setConnected(false);
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_CONNECTION);
            ConnectionInfo connectionInfo = new ConnectionInfo(false, username);

            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonConnectionInfo = new Gson().toJson(connectionInfo);
                outputStream.writeObject(jsonConnectionInfo);
                outputStream.flush();

                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    String jsonSenderCSV = (String) inputStream.readObject();
                    if(jsonSenderCSV.equals("Null"))
                        System.out.println("Disconnessione: " + username);

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
    private void connectToServer(String username) {
        // Connessione al server per notificare la connessione
        setConnected(false);
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_CONNECTION);
            ConnectionInfo connectionInfo = new ConnectionInfo(true, username);

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
                        mailSent.clear();
                        mailReceived.clear();

                        Type type = new TypeToken<HashMap<String, ArrayList<Mail>>>() {}.getType();
                        HashMap<String, ArrayList<Mail>> map = new Gson().fromJson(jsonSenderCSV, type);

                        mailSent.addAll(map.get("sent"));
                        mailReceived.addAll(map.get("received"));
                        mailReceived.forEach(Server::WriterReceiver);

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
        return new ArrayList<>(mailSent.stream().filter(m -> !m.isDelete()).toList());
    }
    public ArrayList<Mail> getMailReceived() {
        return new ArrayList<>(mailReceived.stream().filter(m -> !m.isDelete()).toList());
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
        mailSent.add(mail);
        controller.createCardSent(mail);
        WriterSender(mail);


    }
    public void addMailReceived(Mail mail) {
        mailReceived.add(mail);
        System.out.println("MESSAGGI: " + mail.getText());
        lastMail = mail;
        Platform.runLater(() -> controller.createCardReceived(mail));
    }

    public Mail getLastMail() {
        return lastMail;
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

    public void deleteMailReceived(String uuid) {
        mailReceived.removeIf(mail -> mail.getUuid().equals(uuid));
        Mail mail = mailReceived.stream().filter(m -> m.getUuid().equals(uuid)).findFirst().orElse(null);
        mailReceived.remove(mail);
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, false).setDeleate();

        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailReceivedOfflineModify.add(MailModifyInfo);
        }}
    public void deleteMailSent(String uuid) {
        mailSent.removeIf(mail -> mail.getUuid().equals(uuid));
        Mail mail = mailSent.stream().filter(m -> m.getUuid().equals(uuid)).findFirst().orElse(null);
        mailSent.remove(mail);
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, true).setDeleate();

        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailSentOfflineModify.add(MailModifyInfo);
        }
    }
    public void deleteMailSentList() {
        if(isConnected()) {
            notifyModifyToServer(new MailModifyInfo(null, localAddress, true).setDeleateAll());
        } else {
            mailSent.forEach(mail -> mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, true).setDeleate()));
        }
        mailSent.clear();
    }
    public void deleteMailReceivedList() {
        if(isConnected()) {
            notifyModifyToServer(new MailModifyInfo(null, localAddress, false).setDeleateAll());
        } else {
            mailReceived.forEach(mail -> mailReceivedOfflineModify.add(new MailModifyInfo(mail, localAddress, false).setDeleate()));
        }
        mailReceived.clear();
    }

    public void setMailSent() {
        String path = pathConstructor(localAddress, "sent");
        if(!isConnected()) {
            mailSent.clear();
            if(FileExist(path)) {
                try {
                    mailSent.addAll(readCSV(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }/*else{

        }*/
    }
    public void setMailReceived() {
        String path = pathConstructor(localAddress, "received");
        if(!isConnected()) {
            mailReceived.clear();
            if(FileExist(path)) {
                try {
                    mailReceived.addAll(readCSV(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }/*else{

        }*/

    }
    public void setMailReceivedRead(String uuid) {
        Mail mail = mailReceived.stream().filter(m -> m.getUuid().equals(uuid)).findFirst().orElse(null);
        mailReceived.stream().filter(m -> m.getUuid().equals(uuid)).findFirst().ifPresent(m -> m.setRead());
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, false).setReaded();
        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailReceivedOfflineModify.add(new MailModifyInfo(mail, localAddress, false).setReaded());
        }
        mailReceived.forEach(m -> {if(m.getUuid().equals(uuid)) m.setRead();});
    }

    private static void writeOrUpdateCSVInfo(String username, String lastConnectionDataTime) throws IOException {
        String path = pathConstructor(null, username);
        File file = new File(path);

        // Se il file non esiste, crea un nuovo file e scrivi le intestazioni
        if (!file.exists()) {
            try (FileWriter fileWriter = new FileWriter(path);
                 CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
                csvPrinter.printRecord("Username", "LastConnectionDataTime");
            }
        }

        // Leggi tutte le righe dal file
        List<String> lines = Files.readAllLines(Paths.get(path));

        boolean found = false;

        // Cerca e modifica la riga corrispondente all'username
        for (int i = 1; i < lines.size(); i++) {  // Inizia da 1 per evitare l'intestazione
            String[] parts = lines.get(i).split(",");
            if (parts.length > 0 && parts[0].equals(username)) {
                lines.set(i, username + "," + lastConnectionDataTime);
                found = true;
                break;
            }
        }

        // Se l'username non è stato trovato, aggiungi una nuova riga
        if (!found) {
            lines.add(username + "," + lastConnectionDataTime);
        }

        // Scrivi tutte le righe nel file
        try (FileWriter fileWriter = new FileWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            for (String line : lines) {
                csvPrinter.printRecord((Object) line.split(","));
            }
        }
    }
    private static ArrayList<Mail> readCSV(String path) throws IOException {
        ArrayList<Mail> mailList = new ArrayList<>();

        try (CSVParser csvParser = CSVParser.parse(new FileReader(path), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String sender = csvRecord.get("Sender");
                String recipients = csvRecord.get("Recipients");
                String object = csvRecord.get("Object");
                String text = csvRecord.get("Text");
                String creationDate = csvRecord.get("CreationDate");
                String creationTime = csvRecord.get("CreationTime");
                String lastModifyDateTime = csvRecord.get("LastModifyDateTime");
                String uuid = csvRecord.get("Uuid");
                boolean read = Boolean.parseBoolean(csvRecord.get("Read"));

                mailList.add(new Mail(sender, recipients, object, text, creationDate, creationTime, lastModifyDateTime, read, uuid));
            }
        }
        return mailList;
    }
    private static void WriteCSV(ArrayList<Mail> mailList, String path) throws IOException {

        if (!mailList.isEmpty())
            try (FileWriter fileWriter = new FileWriter(path);
                 CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

                // Intestazioni del CSV
                csvPrinter.printRecord(
                        "Sender",
                        "Recipients",
                        "Object",
                        "Text",
                        "CreationDate",
                        "CreationTime",
                        "LastModifyDateTime",
                        "Uuid",
                        "Read");

                // Scrivi i dati nel CSV
                for (Mail mail : mailList)
                    csvPrinter.printRecord(
                            mail.getSender(),
                            mail.getRecipients(),
                            mail.getObject(),
                            mail.getText(),
                            mail.getDate(),
                            mail.getTime(),
                            mail.getLastModify(),
                            mail.getUuid(),
                            mail.getRead());
            }
    }
    private static void WriterSender(Mail mail) {
        String path = pathConstructor(mail.getSender(), "sent");

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
        String path = pathConstructor(mail.getRecipients(), "received");

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
        disconnectToServer(localAddress);
        clientMessageServerThread.interrupt();
        serverConnectionThread.interrupt();
    }
}
