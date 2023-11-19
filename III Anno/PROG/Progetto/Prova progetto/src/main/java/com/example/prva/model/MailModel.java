package com.example.prva.model;

import java.text.SimpleDateFormat;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

        localAddressProperty = new SimpleStringProperty();

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

        public void addMailSent(Mail mail) { mailSent.add(mail); }
        public void addMailReceived(Mail mail) { mailReceived.add(mail); }

        public void deleteMailSent(Mail mail) { mailSent.remove(mail); }
        public void deleteMailReceived(Mail mail) { mailReceived.remove(mail); }

        public void deleteMailSentList() { mailSent.clear(); }
        public void deleteMailReceivedList() { mailReceived.clear(); }

        public void setMailSent() {
            mailSent.add(new Mail(null, "alice@example.com", "Oggetto 1", "Contenuto della mail 1", "28/03/2022", "11:30", false));
            mailSent.add(new Mail(null, "bob@example.com", "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "12:30", false));
            mailSent.add(new Mail(null, "charlie@example.com", "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "13:30", false));
            mailSent.add(new Mail(null, "dave@example.com", "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "14:30", false));
            mailSent.add(new Mail(null, "emma@example.com", "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "15:30", true));
            mailSent.add(new Mail(null, "frank@example.com", "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "16:30", true));
            mailSent.add(new Mail(null, "hannah@example.com", "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "17:30", true));
            mailSent.add(new Mail(null, "irene@example.com", "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "18:30", true));
            mailSent.add(new Mail(null, "jack@example.com", "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "19:30", true));
        }

        public void setMailReceived() {
            mailReceived.add(new Mail("mary@example.com", null, "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "11:30", false));
            mailReceived.add(new Mail("peter@example.com", null, "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "12:30", false));
            mailReceived.add(new Mail("susan@example.com", null, "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "13:30", false));
            mailReceived.add(new Mail("tom@example.com", null, "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "14:30", false));
            mailReceived.add(new Mail("linda@example.com", null, "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "15:30", true));
            mailReceived.add(new Mail("kevin@example.com", null, "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "16:30", true));
            mailReceived.add(new Mail("natalie@example.com", null, "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "17:30", true));
            mailReceived.add(new Mail("alex@example.com", null, "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "18:30", true));
            mailReceived.add(new Mail("olivia@example.com", null, "Oggetto 10", "Contenuto della mail 10", "28/03/2022", "19:30", true));
        }

        public void setMailSentRead(String uuid, boolean read) {
        }

        public void setMailReceivedRead(String uuid, boolean read) {
        }
    }
}