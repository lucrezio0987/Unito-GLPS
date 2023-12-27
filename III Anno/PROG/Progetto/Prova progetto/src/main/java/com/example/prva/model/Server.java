package com.example.prva.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


public class Server {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT_CONNECTION = 8000;
    private static final int SERVER_PORT_MESSAGES = 8001;
    private static final int CLIENT_PORT_MESSAGES = 8002;

    private List<Mail> mailSent;
    private List<Mail> mailReceived;

    private boolean connected = false;
    private String localAddress = null;

    public Server(){
        mailSent = new ArrayList<>();
        mailReceived = new ArrayList<>();

        startListening();
    }

    void setAddress(String localAddress) {
        //TODO: fare in modo che questo client venga associato all'indirizzo
        this.localAddress = localAddress;

        connectToServer(localAddress);

        setMailSent();
        setMailReceived();
    }

    private void startListening() {

        new Thread(() -> {
            try {
                ServerSocket clientMessageServerSocket = new ServerSocket(CLIENT_PORT_MESSAGES);
                System.out.println("Client in ascolto sulla porta " + CLIENT_PORT_MESSAGES + " per i messaggi...");

                while (true) {
                    Socket socket = clientMessageServerSocket.accept();
                    try {
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        String jsonMail = (String) inputStream.readObject();
                        Mail mail = new Gson().fromJson(jsonMail, Mail.class);

                        //ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        //Mail mail = (Mail) inputStream.readObject();

                        System.out.println("Messaggio ricevuto da " + mail.getSender() + ": " + mail.getText());
                        addMailReceived(mail);

                        socket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Errore durante la lettura del messaggio dal Server");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }


    private void connectToServer(String username) {
        // Connessione al server per notificare la connessione
        try {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_CONNECTION);
            ConnectionInfo connectionInfo = new ConnectionInfo(username, true);

            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonConnectionInfo = new Gson().toJson(connectionInfo);
                outputStream.writeObject(jsonConnectionInfo);
                outputStream.flush();
                connected = true;
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Connessione al Server Fallita");
        }
    }

    public List<Mail> getMailSent() { return mailSent; }
    public List<Mail> getMailReceived() { return mailReceived; }

    public void addMailSent(Mail mail){
        mailSent.add(mail);

        if(connected) {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_MESSAGES);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonMail = new Gson().toJson(mail);
                outputStream.writeObject(jsonMail);
                outputStream.flush();
                //TODO: gestionre risposta dal server

                socket.close();

                System.out.println("Email inviata a: " + mail.getRecipients());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("invio server fallita");
            }
        } else {
            System.out.println("Client: Server non connesso");
        }
        //TODO: salvataggio in locale della mail

    }
    public void addMailReceived(Mail mail) {
        mailReceived.add(mail);
    }

    public void deleteMailSent(Mail mail) { mailSent.remove(mail); }
    public void deleteMailReceived(Mail mail) { mailReceived.remove(mail); }

    public void deleteMailSentList() { mailSent.clear(); }
    public void deleteMailReceivedList() { mailReceived.clear(); }

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
        mailSent.forEach(email -> {if(email.getUuid().equals(uuid)) email.setRead(read);});
    }

    public void setMailReceivedRead(String uuid, boolean read) {
        mailReceived.forEach(email -> {if(email.getUuid().equals(uuid)) email.setRead(read);});
    }


}
