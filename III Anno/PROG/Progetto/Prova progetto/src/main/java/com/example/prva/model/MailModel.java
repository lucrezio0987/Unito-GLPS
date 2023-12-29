package com.example.prva.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.prva.controller.ClientController;
import com.google.gson.Gson;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;

public class MailModel implements Observer {

    private final ArrayList<Mail> mailSent;
    private final ArrayList<Mail> mailReceived;
   // private Mail mail;

    private SimpleStringProperty textMailSendProperty = null; // testo mail da inviare
    private SimpleStringProperty textMailReceivedProperty = null; //testo mail ricevuta
    private SimpleStringProperty textMailSentProperty = null; // testo mail inviata
    private static SimpleStringProperty textLogProperty = null;

    private SimpleStringProperty addressMailSendProperty = null; // address mail da inviare
    private SimpleStringProperty addressMailReceivedProperty = null; // address mail ricevuta
    private SimpleStringProperty addressMailSentProperty = null; // address mail inviata

    private SimpleStringProperty objectMailSendProperty = null; // oggetto mail da inviare
    private SimpleStringProperty objectMailReceivedProperty = null; // oggetto mail ricevuta
    private SimpleStringProperty objectMailSentProperty = null; // oggetto mail inviata

    private SimpleStringProperty localAddressProperty = null;

    private Mail activeMailSent = null;
    private Mail activeMailReceived = null;

    private Server server = null;

    public MailModel(ClientController controller) {
        mailSent = new ArrayList<>();
        mailReceived = new ArrayList<>();

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

        server = new Server();
        server.addObserver(controller);
        server.addObserver(this);

        //setMailSent();
        //setMailReceived();
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

    public ArrayList<Mail> getListMailSent(){ setMailSent(); return mailSent; }
    public ArrayList<Mail> getListMailReceived(){ setMailReceived(); return mailReceived;
    }

    public void setMailSent(){
        mailSent.clear();
        mailSent.addAll(server.getMailSent());
    }
    public void setMailReceived(){
        mailReceived.clear();
        mailReceived.addAll(server.getMailReceived());
    }


    public void openMailReceived(String uuid){
        Mail mail;

        //TODO: capire cosa fa uuid.isEmpty()
        if(uuid.isEmpty() || mailReceived.isEmpty())
            mail = new Mail("","", "", "", "", "", false);
        else
            mail = mailReceived.stream()
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

        if(uuid.isEmpty() || mailSent.isEmpty())
            mail = new Mail("", "", "", "", "", "", false);
        else
            mail =  mailSent.stream()
                    .filter(m -> m.getUuid().equals(uuid))
                    .findFirst()
                    .orElse(null);

        activeMailSent = mail;
        addressMailSentProperty.set(mail.getRecipients());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());

    }

    public void setMailRead(String uuid, boolean read){

        mailSent.stream()
                .filter(m -> m.getUuid().equals(uuid))
                .findFirst()
                .ifPresent(mail -> {
                    mail.setRead(read);
                    server.setMailSentRead(uuid, read);
                });

        mailReceived.stream()
                .filter(m -> m.getUuid().equals(uuid))
                .findFirst()
                .ifPresent(mail -> {
                    mail.setRead(read);
                    server.setMailSentRead(uuid, read);
                });
    }

    public void deleteMailSentList(){ server.deleteMailSentList(); mailSent.clear(); }
    public void deleteMailReceivedList(){ server.deleteMailReceivedList(); mailReceived.clear(); }

    public void deleteMailSent(String uuid){
        mailSent.stream()
                .filter(mail -> mail.getUuid().equals(uuid))
                .findFirst()
                .ifPresent(mail -> {
                    mailSent.remove(mail);
                    server.deleteMailSent(mail);
                });
    }
    public void deleteMailReceived(String uuid) {
        mailReceived.stream()
                .filter(mail -> mail.getUuid().equals(uuid))
                .findFirst()
                .ifPresent(mail -> {
                    mailReceived.remove(mail);
                    server.deleteMailReceived(mail);
                });
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

    public Mail sendMail(){
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String data = formatDate.format(now);
        String time = formatTime.format(now);


        if(!syntaxControll()) {
            addLog("Client", "ERRORE: Indirizzo inserito non valido, email NON inviata");
            return null;
        }
        String sender       = localAddressProperty.get();
        String recipients   = addressMailSendProperty.get();
        String object       = objectMailSendProperty.get();
        String text         =   "------------------ All Recipients: ----------------\n" +
                                addressMailSendProperty.get() +
                                "\n\n----------------------- Text: -----------------------\n" +
                                textMailSendProperty.get();

        Mail mailSend = new Mail(sender ,recipients, object, text, data, time, false);
        mailSent.add(mailSend);

        textMailSendProperty.set("");
        addressMailSendProperty.set("");
        objectMailSendProperty.set("");

        server.addMailSent(mailSend);

        return mailSend;
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
        if(syntaxControll()) {
            addLog("Client", "Connessione: " + localAddressProperty.get());
            deleteMailSentList();
            deleteMailReceivedList();
            server.setAddress(localAddressProperty.get());
            setMailSent();
            setMailReceived();

        } else {
            addLog("Client", "ERRORE: Indirizzo inserito non valido, connessione NON eseguita");
        }
        return server.isConnected();
    }

    private boolean syntaxControll() {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$");
        Matcher matcher = pattern.matcher(localAddressProperty.get());
        return matcher.matches();
    }

    public void addLog(String type, String msg) {
        switch (type) {
            case "Server":
                appendToTextArea(type + ": "+ msg);
                break;
            case "Client":
                appendToTextArea(type + ": " + msg);
                break;
            default:
                appendToTextArea(type + ": " + msg);
                break;
        }
    }

    private static void appendToTextArea(String newLine) {
            String currentText = textLogProperty.getValue();
            if(currentText == null)
                textLogProperty.set(newLine);
            else
                textLogProperty.set(currentText + "\n" + newLine);
            System.out.println(newLine);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof Server) {
            Mail mail = ((Server) observable).getLastMail();
            mailReceived.add(mail);
        }
    }
}