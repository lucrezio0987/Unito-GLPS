package com.example.prva.model;

import java.text.SimpleDateFormat;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;

public class MailModel {
    private Map<String, Mail> mailSent = new HashMap<>();
    private Map<String, Mail> mailReceived = new HashMap<>();
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

    private String activeMailSent = null;
    private String activeMailRecived  = null;

    public ArrayList<Mail> getListMailSent(){
        return new ArrayList<Mail>(mailSent.values());
    }

    public void setMailSent(){}

    public ArrayList<Mail> getListMailReceived(){
        return new ArrayList<Mail>(mailReceived.values());
    }

    public void setMailReceived(){}

 /*   public Mail getMail(){
        return this.mail;
    }
    */

    public SimpleStringProperty getTextMailSendProperty(){ return this.textMailSendProperty; }
    public SimpleStringProperty getTextMailReceivedProperty(){ return this.textMailReceivedProperty; }
    public SimpleStringProperty getTextMailSentProperty(){ return this.textMailSentProperty; }

    public SimpleStringProperty getAddressMailSendProperty(){ return this.addressMailSendProperty; }
    public SimpleStringProperty getAddressMailReceivedProperty(){ return this.addressMailReceivedProperty; }
    public SimpleStringProperty getAddressMailSentProperty(){ return this.addressMailSentProperty; }

    public SimpleStringProperty getObjectMailSentProperty(){ return this.objectMailSentProperty; }
    public SimpleStringProperty getObjectMailReceivedProperty(){ return this.objectMailReceivedProperty; }
    public SimpleStringProperty getObjectMailSendProperty(){ return this.objectMailSendProperty; }


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

        /*
        mailSent.add(new Mail("mario@example.com", "Oggetto 1", "Contenuto della mail 1", "15/03/2022", "08:45"));
        mailSent.add(new Mail("giovanna@example.com", "Oggetto 3", "Contenuto della mail 3", "17/03/2022", "12:15"));
        mailSent.add(new Mail("lisa@example.com", "Oggetto 5", "Contenuto della mail 5", "19/03/2022", "16:30"));
        mailSent.add(new Mail("sara@example.com", "Oggetto 7", "Contenuto della mail 7", "21/03/2022", "21:00"));
        mailSent.add(new Mail("anna@example.com", "Oggetto 9", "Contenuto della mail 9", "23/03/2022", "02:30"));
        mailSent.add(new Mail("chiara@example.com", "Oggetto 11", "Contenuto della mail 11", "25/03/2022", "08:45"));
        mailSent.add(new Mail("giuseppe@example.com", "Oggetto 12", "Contenuto della mail 12", "26/03/2022", "12:15"));
        mailSent.add(new Mail("elena@example.com", "Oggetto 13", "Contenuto della mail 13", "27/03/2022", "16:30"));
        */

        Mail mail;

        mail = new Mail("gabriele@example.com", "Oggetto 14", "Contenuto della mail 14", "28/03/2022", "10:30");
        mailReceived.put(mail.getUuid(), mail);
        mail = new Mail("ilaria@example.com", "Oggetto 15", "Contenuto della mail 15", "29/03/2022", "14:00");
        mailReceived.put(mail.getUuid(), mail);
        mail = new Mail("lorenzo@example.com", "Oggetto 16", "Contenuto della mail 16", "30/03/2022", "18:45");
        mailReceived.put(mail.getUuid(), mail);
    }

    public void openMailReceived(String uuid){
        Mail mail;
        activeMailRecived = uuid;

        if(uuid.equals("") || mailReceived.isEmpty())
            mail = new Mail("", "", "", "", "");
        else
            mail = mailReceived.get(uuid);

        activeMailRecived = uuid;
        addressMailReceivedProperty.set(mail.getAddress());
        objectMailReceivedProperty.set(mail.getObject());
        textMailReceivedProperty.set(mail.getText());
    }

    // DA RIVEDERE
    // v v v v v v v v v v v
    public void openMailSent(String uuid){
        Mail mail;
        activeMailSent = uuid;

        if(uuid.equals("") || mailSent.isEmpty())
            mail = new Mail("", "", "", "", "");
        else
            mail = mailSent.get(uuid);

        addressMailSentProperty.set(mail.getAddress());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());

    }

    public void deleteMailSentList(){ mailSent.clear(); }

    public void deleteMailReceivedList(){ mailReceived.clear(); }

    public void deleteMailSent(String uuid){
        if(mailSent.isEmpty())
            return;
        mailSent.remove(uuid);
    }

    public void deleteMailReceved(String uuid){
        if(mailReceived.isEmpty())
            return;
        mailReceived.remove(uuid);
    }

    public String deleteActualMailSent(){
        String actual = activeMailSent;
        deleteMailSent(actual);
        openMailSent("");
        return actual;
    }
    public String deleteActualMailRecived(){
        String actual = activeMailRecived;
        deleteMailReceved(actual);
        openMailReceived("");
        return actual;
    }

    public Mail sendMail(){
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String data = formatDate.format(now);
        String time = formatTime.format(now);

        String address = addressMailSendProperty.get();
        String object = objectMailSendProperty.get();
        String text = textMailSendProperty.get();

        Mail mailSend = new Mail(address, object, text, data, time);
        mailSent.put(mailSend.getUuid(), mailSend);

        textMailSentProperty.set("");
        addressMailSendProperty.set("");
        objectMailSendProperty.set("");

        return mailSend;
    }
}
