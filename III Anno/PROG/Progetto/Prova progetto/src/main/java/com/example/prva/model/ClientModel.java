package com.example.prva.model;

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

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
    private static final int SERVER_PORT_CONNECTION = 8000; // porta per i messaggi di connessione e disconnessione
    private static final int SERVER_PORT_MAIL = 8001; // porta per lo scambio di mail
    private static final int SERVER_PORT_MODIFY = 8002; // porta per lo scambio delle modifiche

    Thread clientMessageServerThread = null; // thread che resta in attesa di mail
    Thread serverBroadcastThread = null; // thread che resta in attesa di comunicazioni dal server
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

    // mail da inviare -> testo
    public SimpleStringProperty getTextMailSendProperty(){ return this.textMailSendProperty; }
    // mail ricevute -> testo
    public SimpleStringProperty getTextMailReceivedProperty(){ return this.textMailReceivedProperty; }
    // mail inviate -> testo
    public SimpleStringProperty getTextMailSentProperty(){ return this.textMailSentProperty; }
    // tabella dei log
    public SimpleStringProperty getTextLogProperty() { return this.textLogProperty; }
    // indirizzo destinatario mail da inviare
    public SimpleStringProperty getAddressMailSendProperty(){ return this.addressMailSendProperty; }
    // indirizzo mittente mail ricevuta
    public SimpleStringProperty getAddressMailReceivedProperty(){ return this.addressMailReceivedProperty; }
    // indirizzo destinatario mail inviata
    public SimpleStringProperty getAddressMailSentProperty(){ return this.addressMailSentProperty; }
    // oggetto mail inviata
    public SimpleStringProperty getObjectMailSentProperty(){ return this.objectMailSentProperty; }
    // oggetto mail ricevuta
    public SimpleStringProperty getObjectMailReceivedProperty(){ return this.objectMailReceivedProperty; }
    // oggetto mail da inviare
    public SimpleStringProperty getObjectMailSendProperty(){ return this.objectMailSendProperty; }
    // username
    public SimpleStringProperty getLocalAddressProperty(){ return this.localAddressProperty; }
    // indirizzo server
    public SimpleStringProperty getServeHostProperty() { return  this.serveHostProperty;}
    // porta scambio mail
    public SimpleStringProperty getMailPortProperty() {
        return mailPortProperty;
    }
    // porta scambio messaggi server
    public SimpleStringProperty getBroadcastPortProperty() { return broadcastPortProperty; }

    // array contenente la lista di mail inviata
    public ArrayList<Mail> getListMailSent(){ return new ArrayList<>(userData.getMailSentNotDelete().values()); }
    // array contenente la lista di mail ricevute
    public ArrayList<Mail> getListMailReceived(){ return new ArrayList<>(userData.getMailReceivedNotDelete().values()); }

    private void setPortInProperty() {
        mailPortProperty.set(Integer.toString(userData.getMailPort()));
        broadcastPortProperty.set(Integer.toString(userData.getBroadcastPort()));
    }
    void setServerAddress(String serverAddress) {
        serveHostProperty.set(serverAddress);
    }
    // apertura mail ricevuta -> impostazione interfaccia
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
    // apertura mail inviata -> impostazione interfaccia
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

    // imposta una mail letta chiamando notifyModifyServer per avvisare il server del cambiamento
    public void setMailRead(String uuid){
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(userData.getMailReceived().get(uuid), userData.getUsername(), false).setRead());
        userData.setMailRead(uuid);
        log("MAIL: Mail letta (" + uuid + ")");
    }
    // cancellazione di tutte le mail inviate e notifica del cambiamento al server
    public void deleteMailSentList(){
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(null, userData.getUsername(), true).setDeleteAll());
        userData.deleteMailListSent();
        log("MAIL: Cancellazione di tutte le mail inviate");
    }
    // cancellazione di tutte le mail ricevute e notifica del cambiamento al server
    public void deleteMailReceivedList(){
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(null, userData.getUsername(), false).setDeleteAll());
        userData.deleteMailListReceived();
        log("MAIL: Cancellazione di tutte le mail ricevute");
    }
    // cancellazione di una mail inviata e notifica del cambiamento al server
    public void deleteMailSent(String uuid){
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(userData.getMailSent().get(uuid), userData.getUsername(), true).setDelete());
        userData.deleteMailSent(uuid);
        log("MAIL: mail Inviata cancellata (" + uuid + ")");
    }
    // cancellazione di una mail ricevuta e notifica del cambiamento al server
    public void deleteMailReceived(String uuid) {
        if (isConnect())
            notifyModifyToServer(new MailModifyInfo(userData.getMailReceived().get(uuid), userData.getUsername(), false).setDelete());
        userData.deleteMailReceived(uuid);
        log("MAIL: mail Ricevuta cancellata (" + uuid + ")");
    }
    // invoca deleteMailSent per cancellare la mail impostata come "attiva" cioè quella visibile
    public String deleteActualMailSent(){
        String actual = activeMailSent.getUuid();
        deleteMailSent(actual);
        openMailSent("");
        return actual;
    }
    // invoca deleteMailReceived per cancellare la mail impostata come "attiva" cioè quella visibile
    public String deleteActualMailReceived(){
        String actual = activeMailReceived.getUuid();
        deleteMailReceived(actual);
        openMailReceived("");
        return actual;
    }
    // invio della mail se il syntax control non restituisce errore
    // creazione dell'oggetto mail e chiamata del metodo send, passando l'oggetto mail
    public void sendMail(){

        if(!syntaxControl(localAddressProperty.get())) {
            localAddressProperty.set("!! SYNTAX ERRORE !!");
            PauseTransition pause = new PauseTransition(Duration.millis(400));
            pause.setOnFinished(event -> {
                if (userData.getUsername() != null)
                    localAddressProperty.set(userData.getUsername());
                else
                    localAddressProperty.set("");
            });
            pause.play();

            log("ERROR: email non inviata (Mittente non valido)");
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

        if (mailSend.getRecipientsList().stream().anyMatch(r -> !syntaxControl(r))) {
            addressMailSendProperty.set("!! SYNTAX ERROR !!");
            PauseTransition pause = new PauseTransition(Duration.millis(700));
            pause.setOnFinished(event -> addressMailSendProperty.set(""));
            pause.play();

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
    // chiamato dal metodo sendMail
    // se connesso, apre il socket sulla porta per l'invio di mail
    // invia in formato json l'oggetto mail al server e richiude il socket
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
            } catch (IOException e) {
                e.printStackTrace();
                return ret;
            }
        }
        return ret;
    }
    // chiamato per gestire le modifiche
    // apre un socket sulla porta modify e invia un oggetto MailModifyInfo, richiude il socket
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
    // aggiunge una mail ricevuta alla mappa
    private void addMailReceived(Mail mail) {
        userData.addMailReceived(mail);
        log("MAIL: ricevuta da " + mail.getSender());
        Platform.runLater(() -> controller.createCardReceived(mail));
    }

    // svuota i campi dell'interfaccia per "ripulire"
    public void sendMailClear() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set("");
        textMailSendProperty.set("");
    }
    // imposta l'interfaccia per una risposta ad una mail
    public void reply() {
        addressMailSendProperty.set(activeMailReceived.getSender());
        objectMailSendProperty.set(activeMailReceived.getObject());
        textMailSendProperty.set("\n\n----------------------- Last Mail: -----------------------\n"
                + activeMailReceived.getText());
    }
    // imposta l'interfaccia impostando come destinatari tutti i destinatari della mail ricevuta
    public void replyAll() {
        StringBuilder addressList = new StringBuilder();
        addressList.append(activeMailReceived.getSender());

        activeMailReceived.getRecipientsList().forEach(el -> {
            if(!el.equals(userData.getUsername()))
                addressList.append(", " + el);
        });

        addressMailSendProperty.set(addressList.toString());
        objectMailSendProperty.set(activeMailReceived.getObject());
        textMailSendProperty.set("\n\n----------------------- Last Mail: -----------------------\n"
                + activeMailReceived.getText());
    }
    // imposta l'interfaccia per inoltrare una mail ricevuta a un altro destinatario
    public void forwardReceived() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set(activeMailReceived.getObject());
        textMailSendProperty.set(activeMailReceived.getText() + " \n\n"
                + "[ Mail Forwarded, original recipient:  " + localAddressProperty.getValue() + " ]"
        );
    }
    // imposta l'interfaccia per inoltrare una mail inviata a un altro destinatario
    public void forwardSent() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set(activeMailSent.getObject());
        textMailSendProperty.set(activeMailSent.getText() + " \n\n"
                + "[ Mail Forwarded, original recipient:  " + activeMailSent.getRecipients() + " ]"
        );
    }

    // se connected true, imposta la connessione, se no chiama stoplistening in caso di disconnessione
    private void setConnected(boolean connected) {
        if(!connected && userData.isOn())
            stopListening();
        userData.setOn(connected);
        controller.setConnection(connected);
    }
    // true = connesso, false = disconnesso
    private boolean isConnect() {
        return userData.isOn();
    }
    //chiama il metodo connectToServer e setPort, per avviare la connessione
    public boolean connect() {
        String localAddress = localAddressProperty.get();
        if(syntaxControl(localAddress)) {
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
        } else {
            localAddressProperty.set("!! SYNTAX ERRORE !!");
            PauseTransition pause = new PauseTransition(Duration.millis(400));
            pause.setOnFinished(event -> {
                if (userData.getUsername() != null)
                    localAddressProperty.set(userData.getUsername());
                else
                    localAddressProperty.set("");
            });
            pause.play();
        }

        return false;
    }
    // invoca prima la disconnessione e poi la connect per riconnettere
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
    // chiama il metodo disconnectToServer
    public boolean disconnect() {
        disconnectToServer();
        setPortInProperty();
        log("SERVER: Disconnesso");
        return isConnect();
    }

    // apre il socket sulla porta di connessione ed effettua una serie di scambi con il server
    public synchronized void connectToServer() {
        try {
            // apertura socket
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serveHostProperty.getValue(), SERVER_PORT_CONNECTION), 2000);
            socket.setSoTimeout(2000);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            ConnectionInfo connectionInfo = new ConnectionInfo(true, userData.getUsername(), userData.getLastModifyData());

            // INVIO: informazioni di connessione con data ultima modifica del client
            outputStream.writeObject(new Gson().toJson(connectionInfo));
            outputStream.flush();

            // RICEZIONE: data ultima modifica del server
            connectionInfo = new Gson().fromJson((String) inputStream.readObject(), ConnectionInfo.class);
            String lastModifyDataServer = connectionInfo.getLastConnectionDateTime();

            // creazione di una mappa con le mail inviate e ricevute con data più recente dell'ultima modifica del client
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

            // aggiornamento delle mail inviate e ricevute durante il periodo di disconnessione
            userData.updateMailSent(mapMailServer.get("sent"));
            userData.updateMailReceived(mapMailServer.get("received"));

            // inizio di ascolto sulle porte di ricezione mail e di ricezione dei messaggi
            startListening(connectionInfo.getMailPort(), connectionInfo.getBroadcastPort());
            setConnected(true);

        } catch (IOException | ClassNotFoundException e) {
            stopListening();
            setConnected(false);
        }
    }
    // chiamata dal metodo disconnect
    public synchronized void disconnectToServer() {
        if(isConnect())
            try {
                // apertura socket per la connessione disconnessione
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
    // inizio ascolto per ricezione mail e messaggi dal server sulle porte passate come parametri
    private synchronized void startListening(int newMailPort, int newBroadcastPort) {
        stopListening();

        userData.setMailPort(newMailPort);
        userData.setBroadcastPort(newBroadcastPort);

        clientMessageServerThread = new Thread(() -> {
            ServerSocket clientMessageServerSocket = null;
            try     {
                // apertura serverSocket
                clientMessageServerSocket = new ServerSocket(newMailPort);
                System.out.println("Client in ascolto sulla porta " + newMailPort + " per le mail...");
                clientMessageServerSocket.setSoTimeout(500);

                // ciclo infinito finchè il Thread non è interrotto, per ricevere eventuali mail
                while (!Thread.interrupted()) {
                    try (Socket socket = clientMessageServerSocket.accept()) {
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                        // RICEZIONE: mail
                        Mail mail = new Gson().fromJson((String) inputStream.readObject(), Mail.class);

                        // INVIO: conferma di ricezione
                        outputStream.writeObject(new Gson().toJson(true));
                        outputStream.flush();

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
                    // chiusura socket
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
                // apertura server socket
                serverBroadcastSocket = new ServerSocket(newBroadcastPort);
                System.out.println("Client in ascolto sulla porta " + newBroadcastPort + " per i messaggi di disconnessione del server...");
                serverBroadcastSocket.setSoTimeout(500);

                // ciclo infinito finchè il Thread non è interrotto, per ricevere eventuali mail
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
                    // chiusura socket
                    if(serverBroadcastSocket != null)
                        serverBroadcastSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        serverBroadcastThread.start();
    }
    // chiamata durante la disconnessione per "liberare" le porte usate
    private synchronized void stopListening() {
        userData.setMailPort(0);
        userData.setBroadcastPort(0);

        // interrompe il thread
        if (clientMessageServerThread != null) {
            clientMessageServerThread.interrupt();}
        // interrompe il thread
        if (serverBroadcastThread != null)
            serverBroadcastThread.interrupt();
    }

    // cancella tutti i file di backup
    public void clearAllBackup() {
        clearBackupLog();
        clearBackupMail();
        log("BACKUP: Rimossi tutti i file di backup");
    }
    // cancella i file di backup delle mail
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

    // svuota la text dei log
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

    // pulisce graficamente le mail inviate
    public void clearLocalMailSentList() { userData.clearMailListSent(); }
    // pulisce graficamente le mail ricevute
    public void clearLocalMailReceivedList() {
        userData.clearMailListReceived();
    }

    // verifica che l'indirizzo inserito sia valido
    private boolean syntaxControl(String address) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$");
        Matcher matcher = pattern.matcher(address);
        if(!matcher.matches()) {
            log("ERRORE: Errore di sintassi: " + address);
            return false;
        }
        return true;
    }
    // inserisce ogni volta una linea di log, con la newLine come contenuto
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
