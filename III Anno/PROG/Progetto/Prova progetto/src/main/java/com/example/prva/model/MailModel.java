package com.example.prva.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.Gson;


import javafx.beans.property.SimpleStringProperty;

public class MailModel {

    private final Map<String, Mail> mailSent = new HashMap<>();
    private final Map<String, Mail> mailReceived = new HashMap<>();
   // private Mail mail;

    private SimpleStringProperty textMailSendProperty = null; // testo mail da inviare
    private SimpleStringProperty textMailReceivedProperty = null; //testo mail ricevuta
    private SimpleStringProperty textMailSentProperty = null; // testo mail inviata

    private SimpleStringProperty addressMailSendProperty = null; // address mail da inviare
    private SimpleStringProperty addressMailReceivedProperty = null; // address mail ricevuta
    private SimpleStringProperty addressMailSentProperty = null; // address mail inviata

    private SimpleStringProperty objectMailSendProperty = null; // oggetto mail da inviare
    private SimpleStringProperty objectMailReceivedProperty = null; // oggetto mail ricevuta
    private SimpleStringProperty objectMailSentProperty = null; // oggetto mail inviata

    private SimpleStringProperty localAddressProperty = null;

    private String activeMailSent = null;
    private String activeMailReceived = null;

    private Server server = null;

    public MailModel() {
        textMailSendProperty = new SimpleStringProperty();
        textMailReceivedProperty = new SimpleStringProperty();
        textMailSentProperty = new SimpleStringProperty();

        addressMailSendProperty = new SimpleStringProperty();
        addressMailReceivedProperty = new SimpleStringProperty();
        addressMailSentProperty = new SimpleStringProperty();

        objectMailSendProperty = new SimpleStringProperty();
        objectMailReceivedProperty = new SimpleStringProperty();
        objectMailSentProperty = new SimpleStringProperty();

        localAddressProperty = new SimpleStringProperty();

        server = new Server();

        //setMailSent();
        //setMailReceived();
    }

    public SimpleStringProperty getTextMailSendProperty(){ return this.textMailSendProperty; }
    public SimpleStringProperty getTextMailReceivedProperty(){ return this.textMailReceivedProperty; }
    public SimpleStringProperty getTextMailSentProperty(){ return this.textMailSentProperty; }

    public SimpleStringProperty getAddressMailSendProperty(){ return this.addressMailSendProperty; }
    public SimpleStringProperty getAddressMailReceivedProperty(){ return this.addressMailReceivedProperty; }
    public SimpleStringProperty getAddressMailSentProperty(){ return this.addressMailSentProperty; }

    public SimpleStringProperty getObjectMailSentProperty(){ return this.objectMailSentProperty; }
    public SimpleStringProperty getObjectMailReceivedProperty(){ return this.objectMailReceivedProperty; }
    public SimpleStringProperty getObjectMailSendProperty(){ return this.objectMailSendProperty; }

    public SimpleStringProperty getLocalAddressProperty(){ return this.localAddressProperty; }

    public ArrayList<Mail> getListMailSent(){
        return new ArrayList<Mail>(mailSent.values());
    }
    public ArrayList<Mail> getListMailReceived(){
        return new ArrayList<Mail>(mailReceived.values());
    }

    public void setMailSent(){ server.getMailSent().forEach( mail -> mailSent.put(mail.getUuid(), mail)); }
    public void setMailReceived(){ server.getMailReceived().forEach(mail -> mailReceived.put(mail.getUuid(), mail));}


    public void openMailReceived(String uuid){
        Mail mail;

        if(uuid.isEmpty() || mailReceived.isEmpty())
            mail = new Mail("","", "", "", "", "", false);
        else
            mail = mailReceived.get(uuid);

        activeMailReceived = uuid;
        addressMailReceivedProperty.set(mail.getSender());
        objectMailReceivedProperty.set(mail.getObject());
        textMailReceivedProperty.set(mail.getText());
    }

    public void openMailSent(String uuid){
        Mail mail;

        if(uuid.isEmpty() || mailSent.isEmpty())
            mail = new Mail("", "", "", "", "", "", false);
        else
            mail = mailSent.get(uuid);

        activeMailSent = uuid;
        addressMailSentProperty.set(mail.getRecipients());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());

    }

    public void setMailRead(String uuid, boolean read){
        if(mailSent.containsKey(uuid)) {
            mailSent.get(uuid).setRead(read);
            server.setMailSentRead(uuid, read);
        }
        if(mailReceived.containsKey(uuid)) {
            mailReceived.get(uuid).setRead(read);
            server.setMailReceivedRead(uuid, read);
        }
    }

    public void deleteMailSentList(){ server.deleteMailSentList(); mailSent.clear(); }
    public void deleteMailReceivedList(){ server.deleteMailReceivedList(); mailReceived.clear(); }

    public void deleteMailSent(String uuid){ server.deleteMailSent(mailSent.remove(uuid));}
    public void deleteMailReceived(String uuid){ server.deleteMailReceived(mailReceived.remove(uuid)); }

    public String deleteActualMailSent(){
        String actual = activeMailSent;
        deleteMailSent(actual);
        openMailSent("");
        return actual;
    }
    public String deleteActualMailReceived(){
        String actual = activeMailReceived;
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

        // localAddressProperty

        String sender = localAddressProperty.get();
        String recipients = addressMailSendProperty.get();
        String object = objectMailSendProperty.get();
        String text = textMailSendProperty.get();

        Mail mailSend = new Mail(sender ,recipients, object, text, data, time, false);
        mailSent.put(mailSend.getUuid(), mailSend);

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
        addressMailSendProperty.set(mailReceived.get(activeMailReceived).getSender());
        objectMailSendProperty.set(mailReceived.get(activeMailReceived).getObject());
        textMailSendProperty.set("\n\n----------------------- Last Mail: -----------------------\n"
                + mailReceived.get(activeMailReceived).getText());
    }

    public void forward() {
        addressMailSendProperty.set("");
        objectMailSendProperty.set(mailSent.get(activeMailSent).getObject());
        textMailSendProperty.set(mailSent.get(activeMailSent).getText() + " \n\n"
                + "[ Mail Forwarded, original recipient:  " + mailSent.get(activeMailSent).getRecipients() + " ]"
        );
    }

    public void reconnect() {
        deleteMailSentList();
        deleteMailReceivedList();
        server.setAddress(localAddressProperty.get());
        setMailSent();
        setMailReceived();
    }

}