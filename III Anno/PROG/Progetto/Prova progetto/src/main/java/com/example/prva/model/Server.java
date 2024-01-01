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

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;


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

        mailSent = new ArrayList<>();
        mailReceived = new ArrayList<>();
        mailSentOfflineModify = new ArrayList<>();
        mailReceivedOfflineModify = new ArrayList<>();

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


    private void connectToServer(String username) {
        // Connessione al server per notificare la connessione
        setConnected(false);
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_CONNECTION)) {
            ConnectionInfo connectionInfo = new ConnectionInfo(true, username, mailSentOfflineModify, mailReceivedOfflineModify);

            try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                String jsonConnectionInfo = new Gson().toJson(connectionInfo);
                outputStream.writeObject(jsonConnectionInfo);
                outputStream.flush();
                setConnected(true);

                mailSentOfflineModify.clear();
                mailReceivedOfflineModify.clear();

                try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
                    while (inputStream.available() > 0) {
                        String jsonSenderCSV = (String) inputStream.readObject();
                        mailSent.clear();
                        mailReceived.clear();
                        Type type = new TypeToken<HashMap<String, ArrayList<Mail>>>() {}.getType();
                        HashMap<String, String> map = new Gson().fromJson(jsonSenderCSV, type);

                        mailSent = readCSV(map.get("sent"));
                        mailReceived = readCSV(map.get("received"));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Connessione al Server Fallita");
        }

    }

    private static ArrayList<Mail> readCSV(String csvContent) throws IOException {
        ArrayList<Mail> mailList = new ArrayList<>();

        try (CSVParser csvParser = CSVParser.parse(new StringReader(csvContent), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
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

    public List<Mail> getMailSent() { return mailSent; }
    public List<Mail> getMailReceived() { return mailReceived; }

    public void addMailSent(Mail mail){
        if(isConnected()) {
            mailSent.add(mail);
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_MAIL);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonMail = new Gson().toJson(mail);
                outputStream.writeObject(jsonMail);
                outputStream.flush();
                //TODO: gestionre risposta dal server

                socket.close();

                Platform.runLater(() -> controller.createCardSent(mail));

                System.out.println("Email inviata a: " + mail.getRecipients());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("invio server fallita");
            }
        } else {
            mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, true).setCreated());
        }
        //TODO: salvataggio in locale della mail

    }
    public void addMailReceived(Mail mail) {
        mailReceived.add(mail);
        System.out.println("MESAGGI: " + mail.getText());
        lastMail = mail;
        Platform.runLater(() -> controller.createCardReceived(mail));
    }

    public Mail getLastMail() {
        return lastMail;
    }

    public void deleteMailSent(Mail mail) {
        mailSent.remove(mail);
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, true).setDeleate();
        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailSentOfflineModify.add(MailModifyInfo);
        }
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

    public void deleteMailReceived(Mail mail) {
        mailReceived.remove(mail);
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, false).setDeleate();
        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailReceivedOfflineModify.add(MailModifyInfo);
        }}

    public void deleteMailSentList() {
        if(isConnected()) {
            mailSent.forEach(mail -> notifyModifyToServer(new MailModifyInfo(mail, localAddress, true).setDeleate()));
        } else {
            mailSent.forEach(mail -> mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, true).setDeleate()));
        }
        mailSent.clear();
    }
    public void deleteMailReceivedList() {
        if(isConnected()) {
            mailReceived.forEach(mail -> notifyModifyToServer(new MailModifyInfo(mail, localAddress, false).setDeleate()));
        } else {
            mailReceived.forEach(mail -> mailReceivedOfflineModify.add(new MailModifyInfo(mail, localAddress, false).setDeleate()));
        }
        mailReceived.clear();
    }

    public void setMailSent() {
        //TODO: richeista al server della lista (localAddress)
/*
        mailSent.add(new Mail(null, "alice@example.com", "Oggetto 1", "Contenuto della mail 1", "28/03/2022", "11:30", false));
        mailSent.add(new Mail(null, "bob@example.com", "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "12:30", false));
        mailSent.add(new Mail(null, "charlie@example.com", "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "13:30", false));
        mailSent.add(new Mail(null, "dave@example.com", "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "14:30", false));
        mailSent.add(new Mail(null, "emma@example.com", "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "15:30", true));
        mailSent.add(new Mail(null, "frank@example.com", "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "16:30", true));
        mailSent.add(new Mail(null, "hannah@example.com", "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "17:30", true));
        mailSent.add(new Mail(null, "irene@example.com", "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "18:30", true));
        mailSent.add(new Mail(null, "jack@example.com", "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "19:30", true));
*/
        //TODO: salvataggio in locale della lsita
    }

    public void setMailReceived() {
        //TODO: richeista al server della lista (localAddress)
/*
        mailReceived.add(new Mail("kevin@example.com", null, "Oggetto 1", "Contenuto della mail 1", "28/03/2022", "11:30", false));
        mailReceived.add(new Mail("mary@example.com", null, "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "11:30", false));
        mailReceived.add(new Mail("peter@example.com", null, "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "12:30", false));
        mailReceived.add(new Mail("susan@example.com", null, "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "13:30", false));
        mailReceived.add(new Mail("tom@example.com", null, "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "14:30", false));
        mailReceived.add(new Mail("linda@example.com", null, "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "15:30", true));
        mailReceived.add(new Mail("kevin@example.com", null, "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "16:30", true));
        mailReceived.add(new Mail("natalie@example.com", null, "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "17:30", true));
        mailReceived.add(new Mail("alex@example.com", null, "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "18:30", true));
        mailReceived.add(new Mail("olivia@example.com", null, "Oggetto 10", "Contenuto della mail 10", "28/03/2022", "19:30", true));
*/
        //TODO: salvataggio in locale della lsita
    }

    public void setMailSentRead(String uuid, boolean read) {
        Mail mail = mailReceived.stream().filter(m -> m.getUuid().equals(uuid)).findFirst().orElse(null);
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, true).setReaded();
        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailSentOfflineModify.add(new MailModifyInfo(mail, localAddress, true).setReaded());
        }
        mailSent.forEach(m -> {if(m.getUuid().equals(uuid)) m.setRead(read);});
    }

    public void setMailReceivedRead(String uuid, boolean read) {
        Mail mail = mailReceived.stream().filter(m -> m.getUuid().equals(uuid)).findFirst().orElse(null);
        MailModifyInfo MailModifyInfo = new MailModifyInfo(mail, localAddress, false).setReaded();
        if(isConnected()) {
            notifyModifyToServer(MailModifyInfo);
        } else {
            mailReceivedOfflineModify.add(new MailModifyInfo(mail, localAddress, false).setReaded());
        }
        mailReceived.forEach(m -> {if(m.getUuid().equals(uuid)) m.setRead(read);});
    }

    public void stop() {
        clientMessageServerThread.interrupt();
        serverConnectionThread.interrupt();
    }
}
