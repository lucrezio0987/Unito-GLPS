package com.example.prva.model;

import java.text.SimpleDateFormat;
import java.util.*;

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

    private String activeMailSent = null;
    private String activeMailReceived = null;

    private final Server server = new Server();

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

        setMailSent();
        setMailReceived();
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
            mail = new Mail("", "", "", "", "");
        else
            mail = mailReceived.get(uuid);

        activeMailReceived = uuid;
        addressMailReceivedProperty.set(mail.getAddress());
        objectMailReceivedProperty.set(mail.getObject());
        textMailReceivedProperty.set(mail.getText());
    }

    public void openMailSent(String uuid){
        Mail mail;

        if(uuid.isEmpty() || mailSent.isEmpty())
            mail = new Mail("", "", "", "", "");
        else
            mail = mailSent.get(uuid);

        activeMailSent = uuid;
        addressMailSentProperty.set(mail.getAddress());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());

    }

    public void deleteMailSentList(){ mailSent.clear(); }
    public void deleteMailReceivedList(){ mailReceived.clear(); }

    public void deleteMailSent(String uuid){ server.deleteMailSent(mailSent.remove(uuid));}
    public void deleteMailReceved(String uuid){ server.deleteMailReceived(mailReceived.remove(uuid)); }

    public String deleteActualMailSent(){
        String actual = activeMailSent;
        deleteMailSent(actual);
        openMailSent("");
        return actual;
    }
    public String deleteActualMailReceived(){
        String actual = activeMailReceived;
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

        server.addMailSent(mailSend);

        return mailSend;
    }

    private static class Server {
        private final List<Mail> mailSent;
        private final List<Mail> mailReceived;

        public Server() {
            mailSent = new ArrayList<>();
            mailReceived = new ArrayList<>();
            setMailSent();
            setMailReceived();
        }

        public List<Mail> getMailSent() { return mailSent; }
        public List<Mail> getMailReceived() { return mailReceived; }

        public boolean addMailSent(Mail mail) { return mailSent.add(mail); }
        public boolean addMailReceived(Mail mail) { return mailReceived.add(mail); }

        public boolean deleteMailSent(Mail mail) { return mailSent.remove(mail); }
        public boolean deleteMailReceived(Mail mail) { return mailReceived.remove(mail); }

        public void setMailSent() {
            mailSent.add(new Mail("alice@example.com", "Oggetto 1", "Contenuto della mail 1", "28/03/2022", "11:30"));
            mailSent.add(new Mail("bob@example.com", "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "12:30"));
            mailSent.add(new Mail("charlie@example.com", "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "13:30"));
            mailSent.add(new Mail("dave@example.com", "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "14:30"));
            mailSent.add(new Mail("emma@example.com", "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "15:30"));
            mailSent.add(new Mail("frank@example.com", "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "16:30"));
            mailSent.add(new Mail("hannah@example.com", "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "17:30"));
            mailSent.add(new Mail("irene@example.com", "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "18:30"));
            mailSent.add(new Mail("jack@example.com", "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "19:30"));
        }

        public void setMailReceived() {
            mailReceived.add(new Mail("mary@example.com", "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "11:30"));
            mailReceived.add(new Mail("peter@example.com", "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "12:30"));
            mailReceived.add(new Mail("susan@example.com", "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "13:30"));
            mailReceived.add(new Mail("tom@example.com", "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "14:30"));
            mailReceived.add(new Mail("linda@example.com", "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "15:30"));
            mailReceived.add(new Mail("kevin@example.com", "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "16:30"));
            mailReceived.add(new Mail("natalie@example.com", "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "17:30"));
            mailReceived.add(new Mail("alex@example.com", "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "18:30"));
            mailReceived.add(new Mail("olivia@example.com", "Oggetto 10", "Contenuto della mail 10", "28/03/2022", "19:30"));
        }

    }
}