package com.example.prva.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.prva.controller.ClientController;

import javafx.beans.property.SimpleStringProperty;

public class MailModel {

    private ClientController controller;
   // private Mail mail;

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

    private Mail activeMailSent = null;
    private Mail activeMailReceived = null;

    private Server server = null;


    public MailModel(ClientController controller) {
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

        server = new Server(controller, serveHostProperty.getValue());
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

    public ArrayList<Mail> getListMailSent(){ return new ArrayList<>(server.getMailsSent()); }
    public ArrayList<Mail> getListMailReceived(){ return new ArrayList<>(server.getMailsReceived());
    }

    public void openMailReceived(String uuid){
        Mail mail;

        //TODO: capire cosa fa uuid.isEmpty()
        if(uuid.isEmpty() || server.getMailsReceived().isEmpty())
            mail = new Mail("","", "", "");
        else
            mail = server.getMailsReceived().stream()
                    .filter(m -> m.getUuid().equals(uuid))
                    .findFirst()
                    .orElse(null);

        activeMailReceived = mail;
        addressMailReceivedProperty.set(mail.getSender());
        objectMailReceivedProperty.set(mail.getObject());
        textMailReceivedProperty.set(mail.getText());
    }
    public void openMailSent(String uuid){
        Mail mail;

        if(uuid.isEmpty() || server.getMailsSent().isEmpty()) {
            mail = new Mail("","", "", "");
        } else
            mail =  server.getMailsSent().stream()
                    .filter(m -> m.getUuid().equals(uuid))
                    .findFirst()
                    .orElse(null);

        activeMailSent = mail;
        addressMailSentProperty.set(mail.getRecipients());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());

    }

    public void setMailRead(String uuid){
        server.setMailReceivedRead(uuid);
        log("MAIL: Mail letta (" + uuid + ")");
    }
    public void deleteMailSentList(){
        server.deleteMailSentList();
        log("MAIL: Cancellazione di tutte le mail Inviate");
    }
    public void deleteMailReceivedList(){
        server.deleteMailReceivedList();
        log("MAIL: Cancellazione di tutte le mail Ricevute");
    }

    public void deleteMailSent(String uuid){
        server.deleteMailSent(uuid);
        log("MAIL: mail Inviata cancellata (" + uuid + ")");
    }
    public void deleteMailReceived(String uuid) {
        server.deleteMailReceived(uuid);
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
            log("ERRORE: email non inviata");
            return;
        }
        String sender       = localAddressProperty.get();
        String recipients   = addressMailSendProperty.get();
        String object       = objectMailSendProperty.get();
        String text         =   "------------------ All Recipients: ----------------\n" +
                                addressMailSendProperty.get() +
                                "\n\n----------------------- Text: -----------------------\n" +
                                textMailSendProperty.get();

        Mail mailSend = new Mail(sender ,recipients, object, text);

        textMailSendProperty.set("");
        addressMailSendProperty.set("");
        objectMailSendProperty.set("");

        if(server.addMailSent(mailSend))
            mailSend.getRecipientsList().forEach(r -> log("MAIL: mail inviata con successo [" + sender + " -> "+ r + "]"));
        else
            mailSend.getRecipientsList().forEach(r -> log("MAIL: invio non completato (Server Disconnesso) [" + sender + " -> "+ r + "]"));
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

    public boolean connect() {
        String localAddress = localAddressProperty.get();
        if(syntaxControll(localAddress)) {

            server.setAddress(localAddress);
            server.setServerAddress(serveHostProperty.get());
            server.connectToServer();

            server.setAddress(localAddressProperty.get());

            controller.setCountMailSent();
            getListMailSent().forEach(controller::createCardSent);

            controller.setCountMailReceived();
            getListMailReceived().forEach(controller::createCardReceived);

            if (server.isConnected()) {
                log("SERVER: Connessione (" + localAddress + ")");
                return true;
            }
        }
        log("ERRORE: Connessione al server non riuscita");
        return false;
    }
    public boolean reconnect() {
        String localAddress = localAddressProperty.get();
        if(syntaxControll(localAddress)) {

            server.disconnectToServer();
            server.setAddress(localAddress);
            server.setServerAddress(serveHostProperty.get());
            server.connectToServer();

            controller.setCountMailSent();
            getListMailSent().forEach(controller::createCardSent);

            controller.setCountMailReceived();
            getListMailReceived().forEach(controller::createCardReceived);
            if (server.isConnected()) {
                log("SERVER: Riconnessione (" + localAddress + ")");
                return true;
            }
        }
        log("ERRORE: Riconnessione al server non riuscita");
        return false;

    }
    public boolean disconnect() {
        log("SERVER: Disconnesso");
        server.disconnectToServer();
        return server.isConnected();
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

    public void stop() {
        server.stop();
    }

    public void clearAllBackup() {
        server.clearAllBackup();
        log("BACKUP: Rimossi tutti i file di backup");
    }
    public void clearBackupMail() {
        server.clearBackupMail();
        log("BACKUP: Rimossi i file csv di backup delle mail (Inviate e Ricevute)");
    }
    public void clearBackupData() {
        server.clearBackupData();
        log("BACKUP: Rimosso il file data.csv (Utente, DataUltimaConnessione)");
    }
}