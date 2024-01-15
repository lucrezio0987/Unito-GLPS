package com.example.prva.model;

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientModel {
    private static final int SERVER_PORT_CONNECTION = 8000;
    private static final int SERVER_PORT_MAIL = 8001;
    private static final int SERVER_PORT_MODIFY = 8002;

    Thread clientMessageServerThread = null;
    Thread serverBroadcastThread = null;
    private UserData userData;
    private Mail activeMailSent = null;
    private Mail activeMailReceived = null;

    private static ClientController controller;
    private SimpleStringProperty textMailSendProperty = null; // testo mail da inviare
    private SimpleStringProperty textMailReceivedProperty = null; //testo mail ricevuta
    private SimpleStringProperty textMailSentProperty = null; // testo mail inviata
    private SimpleStringProperty textLogProperty = null;
    private SimpleStringProperty addressMailSendProperty = null; // address mail da inviare
    private SimpleStringProperty addressMailReceivedProperty = null; // address mail ricevuta
    private SimpleStringProperty addressMailSentProperty = null; // address mail inviata
    private SimpleStringProperty objectMailSendProperty = null; // oggetto mail da inviare
    private SimpleStringProperty objectMailReceivedProperty = null; // oggetto mail ricevuta
    private SimpleStringProperty objectMailSentProperty = null; // oggetto mail inviata
    private SimpleStringProperty localAddressProperty = null;
    private SimpleStringProperty serveHostProperty = null;
    private SimpleStringProperty mailPortProperty = null;
    private SimpleStringProperty broadcastPortProperty = null;

    public ClientModel(ClientController controller) {
        this.controller = controller;

        textMailSendProperty = new SimpleStringProperty();
        textMailReceivedProperty = new SimpleStringProperty();
        textMailSentProperty = new SimpleStringProperty();
        textLogProperty =  new SimpleStringProperty();

        addressMailSendProperty = new SimpleStringProperty();
        addressMailReceivedProperty = new SimpleStringProperty();
        addressMailSentProperty = new SimpleStringProperty();

        objectMailSendProperty = new SimpleStringProperty();
        objectMailReceivedProperty = new SimpleStringProperty();
        objectMailSentProperty = new SimpleStringProperty();

        localAddressProperty = new SimpleStringProperty();
        serveHostProperty = new SimpleStringProperty();
        mailPortProperty = new SimpleStringProperty();
        broadcastPortProperty = new SimpleStringProperty();

        userData = new UserData();
    }
    public void stop() {
        //TODO implementazione stop
        disconnectToServer();
    }

    public SimpleStringProperty getTextMailSendProperty(){ return this.textMailSendProperty; }
    public SimpleStringProperty getTextMailReceivedProperty(){ return this.textMailReceivedProperty; }
    public SimpleStringProperty getTextMailSentProperty(){ return this.textMailSentProperty; }
    public SimpleStringProperty getTextLogProperty() { return this.textLogProperty; }
    public SimpleStringProperty getAddressMailSendProperty(){ return this.addressMailSendProperty; }
    public SimpleStringProperty getAddressMailReceivedProperty(){ return this.addressMailReceivedProperty; }
    public SimpleStringProperty getAddressMailSentProperty(){ return this.addressMailSentProperty; }
    public SimpleStringProperty getObjectMailSentProperty(){ return this.objectMailSentProperty; }
    public SimpleStringProperty getObjectMailReceivedProperty(){ return this.objectMailReceivedProperty; }
    public SimpleStringProperty getObjectMailSendProperty(){ return this.objectMailSendProperty; }
    public SimpleStringProperty getLocalAddressProperty(){ return this.localAddressProperty; }
    public SimpleStringProperty getServeHostProperty() { return  this.serveHostProperty;}
    public SimpleStringProperty getMailPortProperty() {
        return mailPortProperty;
    }
    public SimpleStringProperty getBroadcastPortProperty() { return broadcastPortProperty; }

    public ArrayList<Mail> getListMailSent(){ return new ArrayList<>(userData.getMailSentNotDelete().values()); }
    public ArrayList<Mail> getListMailReceived(){ return new ArrayList<>(userData.getMailReceivedNotDelete().values()); }

    private void setPortInProperty() {
        mailPortProperty.set(Integer.toString(userData.getMailPort()));
        broadcastPortProperty.set(Integer.toString(userData.getBroadcastPort()));
    }
    void setServerAddress(String serverAddress) {
        serveHostProperty.set(serverAddress);
    }

    public void openMailReceived(String uuid){
        Mail mail;

        if(uuid.isEmpty() || userData.getMailReceivedNotDelete().isEmpty())
            mail = new Mail("","", "", "");
        else
            mail = userData.getMailReceivedNotDelete().get(uuid);

        activeMailReceived = mail;
        addressMailReceivedProperty.set(mail.getSender());
        objectMailReceivedProperty.set(mail.getObject());
        textMailReceivedProperty.set(mail.getText());
    }
    public void openMailSent(String uuid){
        Mail mail;

        if(uuid.isEmpty() || userData.getMailSentNotDelete().isEmpty()) {
            mail = new Mail("","", "", "");
        } else
            mail =  userData.getMailSentNotDelete().get(uuid);

        activeMailSent = mail;
        addressMailSentProperty.set(mail.getRecipients());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());
    }

    public void setMailRead(String uuid){
        // TODO: Notify to server
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(userData.getMailReceived().get(uuid), userData.getUsername(), false).setRead());
        userData.setMailRead(uuid);
        log("MAIL: Mail letta (" + uuid + ")");
    }
    public void deleteMailSentList(){
       // TODO: Notify to server
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(null, userData.getUsername(), true).setDeleteAll());
        userData.deleteMailSentList();
        log("MAIL: Cancellazione di tutte le mail inviate");
    }
    public void deleteMailReceivedList(){
        // TODO: Notify to server
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(null, userData.getUsername(), false).setDeleteAll());
        userData.deleteMailReceivedList();
        log("MAIL: Cancellazione di tutte le mail ricevute");
    }
    public void deleteMailSent(String uuid){
        // TODO: Notify to server
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(userData.getMailSent().get(uuid), userData.getUsername(), true).setDelete());
        userData.deleteMailSent(uuid);
        log("MAIL: mail Inviata cancellata (" + uuid + ")");
    }
    public void deleteMailReceived(String uuid) {
        // TODO: Notify to server
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(userData.getMailReceived().get(uuid), userData.getUsername(), false).setDelete());
        userData.deleteMailReceived(uuid);
        log("MAIL: mail Ricevuta cancellata (" + uuid + ")");
    }
    public String deleteActualMailSent(){
        String actual = activeMailSent.getUuid();
        deleteMailSent(actual);
        openMailSent("");
        return actual;
    }
    public String deleteActualMailReceived(){
        String actual = activeMailReceived.getUuid();
        deleteMailReceived(actual);
        openMailReceived("");
        return actual;
    }

    public void sendMail(){

        if(!syntaxControll(localAddressProperty.get())) {
            log("ERRORE: email non inviata (Mittente non valido)");
            return;
        }
        userData.updateUsername(localAddressProperty.get());

        String sender       = localAddressProperty.get();
        String recipients   = addressMailSendProperty.get();
        String object       = objectMailSendProperty.get();
        String text         =   "------------------ All Recipients: ----------------\n" +
                addressMailSendProperty.get() +
                "\n\n----------------------- Text: -----------------------\n" +
                textMailSendProperty.get();

        Mail mailSend = new Mail(sender ,recipients, object, text);

        if (mailSend.getRecipientsList().stream().anyMatch(r -> !syntaxControll(r))) {
            log("ERRORE: email non inviata (Uno dei destinatari non valido)");
            return;
        }

        textMailSendProperty.set("");
        addressMailSendProperty.set("");
        objectMailSendProperty.set("");

        userData.addMailSent(mailSend);
        controller.createCardSent(mailSend);
        if(send(mailSend))
            mailSend.getRecipientsList().forEach(r -> log("MAIL: mail inviata con successo [" + sender + " -> "+ r + "]"));
        else
            mailSend.getRecipientsList().forEach(r -> log("MAIL: invio non completato (Server Disconnesso) [" + sender + " -> "+ r + "]"));
    }
    private boolean send(Mail mailSend) {
        boolean ret = false;
        if(isConnect()) {
            try {
                Socket socket = new Socket(serveHostProperty.getValue(), SERVER_PORT_MAIL);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                String jsonMail = new Gson().toJson(mailSend);
                outputStream.writeObject(jsonMail);
                outputStream.flush();

                socket.close();

                ret = true;
                System.out.println("Email inviata a: " + mailSend.getRecipients());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("invio server fallita");
                return ret;
            }
        }
        return ret;
    }
    private void notifyModifyToServer(MailModifyInfo mailModifyInfo) {
        try {
            Socket socket = new Socket(serveHostProperty.getValue(), SERVER_PORT_MODIFY);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            String jsonModifyInfo = new Gson().toJson(mailModifyInfo);
            outputStream.writeObject(jsonModifyInfo);
            outputStream.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMailReceived(Mail mail) {
        userData.addMailReceived(mail);
        log("MAIL: ricevuta da " + mail.getSender());
        Platform.runLater(() -> controller.createCardReceived(mail));
    }

    public void sendMailClear() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set("");
        textMailSendProperty.set("");
    }
    public void reply() {
        addressMailSendProperty.set(activeMailReceived.getSender());
        objectMailSendProperty.set(activeMailReceived.getObject());
        textMailSendProperty.set("\n\n----------------------- Last Mail: -----------------------\n"
                + activeMailReceived.getText());
    }
    public void replyAll() {
        StringBuilder addressList = new StringBuilder();
        addressList.append(activeMailReceived.getSender());

        activeMailReceived.getRecipientsList().forEach(el -> {
            if(!el.equals(activeMailReceived.getSender()))
                addressList.append(", " + el);
        });

        addressMailSendProperty.set(addressList.toString());
        objectMailSendProperty.set(activeMailReceived.getObject());
        textMailSendProperty.set("\n\n----------------------- Last Mail: -----------------------\n"
                + activeMailReceived.getText());
    }
    public void forwardReceived() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set(activeMailReceived.getObject());
        textMailSendProperty.set(activeMailReceived.getText() + " \n\n"
                + "[ Mail Forwarded, original recipient:  " + localAddressProperty.getValue() + " ]"
        );
    }
    public void forwardSent() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set(activeMailSent.getObject());
        textMailSendProperty.set(activeMailSent.getText() + " \n\n"
                + "[ Mail Forwarded, original recipient:  " + activeMailSent.getRecipients() + " ]"
        );
    }

    private void setConnected(boolean connected) {
        if(!connected && userData.isOn())
            stopListening();
        userData.setOn(connected);
        controller.setConnection(connected);
    }
    private boolean isConnect() {
        return userData.isOn();
    }

    public boolean connect() {
        String localAddress = localAddressProperty.get();
        if(syntaxControll(localAddress)) {
            if(!(isConnect() && userData.getUsername().equals(localAddress))) {
                userData.updateUsername(localAddress);
                userData.setAddress(serveHostProperty.get());
                connectToServer();

                setPortInProperty();
                controller.clearMailView();
                controller.setCountMailSent();
                getListMailSent().forEach(controller::createCardSent);
                controller.setCountMailReceived();
                getListMailReceived().forEach(controller::createCardReceived);
            }
            if(isConnect()) {
                log("SERVER: Connesso");
                return true;
            }
            log("ERRORE: Connessione al server non riuscita (Backup caricati)");
        }
        return false;
    }
    public boolean reconnect() {
        if(isConnect())
            try {
                disconnect();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return connect();
    }
    public boolean disconnect() {
        disconnectToServer();
        setPortInProperty();
        log("SERVER: Disconnesso");
        return isConnect();
    }

    public synchronized void connectToServer() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serveHostProperty.getValue(), SERVER_PORT_CONNECTION), 2000);
            socket.setSoTimeout(2000);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            ConnectionInfo connectionInfo = new ConnectionInfo(true, userData.getUsername(), userData.getLastModifyData());

            // INVIO: informazioni di connessione
            outputStream.writeObject(new Gson().toJson(connectionInfo));
            outputStream.flush();

            // RICEZIONE: data ultima modifica del server
            connectionInfo = new Gson().fromJson((String) inputStream.readObject(), ConnectionInfo.class);
            String lastModifyDataServer = connectionInfo.getLastConnectionDateTime();

            Map<String, Map<String, Mail>> mapMailClient = new HashMap<>();
            mapMailClient.put("sent", userData.getMailSentListMoreRecentlyOf(lastModifyDataServer));
            mapMailClient.put("received", userData.getMailReceivedListMoreRecentlyOf(lastModifyDataServer));

            // INVIO: modifiche del client offline
            outputStream.writeObject(new Gson().toJson(mapMailClient));
            outputStream.flush();

            // RICEZIONE: lista modifiche client-server combinate
            Type type = new TypeToken<Map<String, Map<String, Mail>>>() {}.getType();
            Map<String, Map<String, Mail>> mapMailServer = new Gson().fromJson((String) inputStream.readObject(), type);

            outputStream.close();
            inputStream.close();
            socket.close();

            userData.updateMailSent(mapMailServer.get("sent"));
            userData.updateMailReceived(mapMailServer.get("received"));

            startListening(connectionInfo.getMailPort(), connectionInfo.getBroadcastPort());
            setConnected(true);

        } catch (IOException | ClassNotFoundException e) {
            stopListening();
            setConnected(false);
        }
    }
    public synchronized void disconnectToServer() {
        if(isConnect())
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(serveHostProperty.getValue(), SERVER_PORT_CONNECTION), 2000);
                socket.setSoTimeout(2000);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                ConnectionInfo connectionInfo = new ConnectionInfo(false, userData.getUsername());

                // INVIO: informazioni di disconnessione
                outputStream.writeObject(new Gson().toJson(connectionInfo));
                outputStream.flush();

                inputStream.close();
                outputStream.close();
                socket.close();

            } catch (IOException e) {
                System.out.println("Disconnessione al Server Fallita");
            } finally {
                setConnected(false);
            }
    }
    private synchronized void startListening(int newMailPort, int newBroadcastPort) {
        stopListening();

        userData.setMailPort(newMailPort);
        userData.setBroadcastPort(newBroadcastPort);

        clientMessageServerThread = new Thread(() -> {
            ServerSocket clientMessageServerSocket = null;
            try     {
                clientMessageServerSocket = new ServerSocket(newMailPort);
                System.out.println("Client in ascolto sulla porta " + newMailPort + " per le mail...");
                clientMessageServerSocket.setSoTimeout(500);

                while (!Thread.interrupted()) {
                    try (Socket socket = clientMessageServerSocket.accept()) {
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                        // RICEZIONE: invio della mail
                        Mail mail = new Gson().fromJson((String) inputStream.readObject(), Mail.class);

                        // INVIO: conferma di invio
                        outputStream.writeObject(new Gson().toJson(true));
                        outputStream.flush();

                        System.out.println("MAIL ricevuta da " + mail.getSender() + ": " + mail.getText());
                        addMailReceived(mail);

                        inputStream.close();
                        outputStream.close();
                    } catch (SocketTimeoutException ignored) {
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Errore durante la lettura della mail dal Server");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(clientMessageServerSocket != null)
                        clientMessageServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientMessageServerThread.start();

        serverBroadcastThread = new Thread(() -> {
            ServerSocket serverBroadcastSocket = null;
            try {
                serverBroadcastSocket = new ServerSocket(newBroadcastPort);
                System.out.println("Client in ascolto sulla porta " + newBroadcastPort + " per i messaggi di disconnessione del server...");
                serverBroadcastSocket.setSoTimeout(500);

                while (!Thread.interrupted()) {
                    try (Socket socket = serverBroadcastSocket.accept()) {
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        String jsonMail = (String) inputStream.readObject();
                        boolean connected = new Gson().fromJson(jsonMail, boolean.class);

                        setConnected(connected);
                    } catch (SocketTimeoutException ignored) {
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Errore durante la lettura del messaggio dal Server");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(serverBroadcastSocket != null)
                        serverBroadcastSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        serverBroadcastThread.start();
    }
    private synchronized void stopListening() {
        userData.setMailPort(0);
        userData.setBroadcastPort(0);

        if (clientMessageServerThread != null) {
            clientMessageServerThread.interrupt();}

        if (serverBroadcastThread != null)
            serverBroadcastThread.interrupt();
    }

    public void clearAllBackup() {
        clearBackupLog();
        clearBackupMail();
        clearBackupData();
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

        controller.clearLocalMail();
        log("BACKUP: Rimossi i file csv di backup delle mail (Inviate e Ricevute)");
    }
    public void clearBackupData() {

        Arrays.stream(Objects.requireNonNull(new File(
                        System.getProperty("user.dir") +
                                File.separator + "src" +
                                File.separator + "backup").listFiles()))
                .filter(f -> f.getName().equals("data.csv"))
                .forEach(File::delete);

        controller.clearTable();
        log("BACKUP: Rimosso il file data.csv (Utente, DataUltimaConnessione)");
    }
    public void clearBackupLog() {

        Arrays.stream(Objects.requireNonNull(new File(
                        System.getProperty("user.dir") +
                                File.separator + "src" +
                                File.separator + "backup").listFiles()))
                .filter(f -> f.getName().equals("log.csv"))
                .forEach(File::delete);

        textLogProperty.set(null);
        log("BACKUP: pulita la storiografia dei log (log.csv) [FILE DI LOG NON ANCORA IMPLEMENTATO]");
    }

    public void clearLocalMailSentList() { userData.clearMailListSent(); }
    public void clearLocalMailReceivedList() {
        userData.clearMailListReceived();
    }

    private boolean syntaxControll(String address) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$");
        Matcher matcher = pattern.matcher(address);
        if(!matcher.matches()) {
            log("ERRORE: Errore di sintassi: " + address);
            return false;
        }
        return true;
    }
    public synchronized void log(String newLine) {
        if (newLine.isEmpty())
            newLine = "ALLERT: String is NULL";

        newLine = "<" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ">  " + newLine;

        String currentText = textLogProperty.getValue();
        if (currentText == null)
            textLogProperty.set(newLine);
        else
            textLogProperty.set(currentText + "\n" + newLine);
        System.out.println(newLine);
    }

}
