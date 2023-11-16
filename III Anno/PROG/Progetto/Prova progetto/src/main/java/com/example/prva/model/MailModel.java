package com.example.prva.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MailModel {
    private ArrayList<Mail> mailSent = new ArrayList<>();
    private ArrayList<Mail> mailReceived = new ArrayList<>();
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

   // int indexMail_Ricevuta = 0; DA VEDERE
   // int indexMail_Inviata = 0; DA VEDERE

    public ArrayList<Mail> getListMailSent(){
        return this.mailSent;
    }

    public void setMailSent(){}

    public ArrayList<Mail> getListMailReceived(){
        return this.mailReceived;
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
        //mailSent, mailReceived

        mailSent.add(new Mail("mario@example.com", "Oggetto 1", "Contenuto della mail 1", "15/03/2022", "08:45"));
        mailSent.add(new Mail("giovanna@example.com", "Oggetto 3", "Contenuto della mail 3", "17/03/2022", "12:15"));
        mailSent.add(new Mail("lisa@example.com", "Oggetto 5", "Contenuto della mail 5", "19/03/2022", "16:30"));
        mailSent.add(new Mail("sara@example.com", "Oggetto 7", "Contenuto della mail 7", "21/03/2022", "21:00"));
        mailSent.add(new Mail("anna@example.com", "Oggetto 9", "Contenuto della mail 9", "23/03/2022", "02:30"));
        mailSent.add(new Mail("chiara@example.com", "Oggetto 11", "Contenuto della mail 11", "25/03/2022", "08:45"));
        mailSent.add(new Mail("giuseppe@example.com", "Oggetto 12", "Contenuto della mail 12", "26/03/2022", "12:15"));
        mailSent.add(new Mail("elena@example.com", "Oggetto 13", "Contenuto della mail 13", "27/03/2022", "16:30"));

        mailReceived.add(new Mail("gabriele@example.com", "Oggetto 14", "Contenuto della mail 14", "28/03/2022", "10:30"));
        mailReceived.add(new Mail("ilaria@example.com", "Oggetto 15", "Contenuto della mail 15", "29/03/2022", "14:00"));
        mailReceived.add(new Mail("lorenzo@example.com", "Oggetto 16", "Contenuto della mail 16", "30/03/2022", "18:45"));
        mailReceived.add(new Mail("luca@example.com", "Oggetto 2", "Contenuto della mail 2", "16/03/2022", "10:30"));
        mailReceived.add(new Mail("andrea@example.com", "Oggetto 4", "Contenuto della mail 4", "18/03/2022", "14:00"));
        mailReceived.add(new Mail("marco@example.com", "Oggetto 6", "Contenuto della mail 6", "20/03/2022", "18:45"));
        mailReceived.add(new Mail("paolo@example.com", "Oggetto 8", "Contenuto della mail 8", "22/03/2022", "23:15"));
        mailReceived.add(new Mail("francesco@example.com", "Oggetto 10", "Contenuto della mail 10", "24/03/2022", "04:45"));


    }

    public void openMailReceived(String uuid){
    Mail mail = mailReceived.stream()
            .filter(el -> el.getUuid().equals(uuid))
            .collect(Collectors.toCollection(ArrayList::new))
            .getFirst();

    /*
    addressMailReceivedProperty.set(mail.getAddress());
    objectMailReceivedProperty.set(mail.getObject());
    textMailReceivedProperty.set(mail.getText());

     */
    }

    // DA RIVEDERE
    // v v v v v v v v v v v
    public void openMailSent(String uuid){
        Mail mail = mailSent.stream()
                    .filter(el -> el.getUuid().equals(uuid))
                    .collect(Collectors.toCollection(ArrayList::new))
                    .getFirst();
        /*
        addressMailSentProperty.set(mail.getAddress());
        objectMailSentProperty.set(mail.getObject());
        textMailSentProperty.set(mail.getText());

         */
    }

    public void deleteMailSentList(){ mailSent.clear(); }

    public void deleteMailReceivedList(){ mailReceived.clear(); }

    public void deleteMailSent(String uuid){
        mailSent = (ArrayList<Mail>) mailSent.stream()
                .filter(mail -> mail.getUuid().equals(uuid))
                .collect(Collectors.toCollection(ArrayList::new));
        openMailSent(uuid);
    }

    public void deleteMailReceived(String uuid){
        mailReceived = (ArrayList<Mail>) mailReceived.stream()
                .filter(mail -> mail.getUuid().equals(uuid))
                .collect(Collectors.toCollection(ArrayList::new));
        openMailReceived(uuid);
    }

    public void sendMail(){
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("gg/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

        String address = addressMailSendProperty.get();
        String object = objectMailSendProperty.get();
        String text = textMailSendProperty.get();

        Mail mailSend = new Mail(address, object, text, formatDate.format(now), formatTime.format(now));
        mailSent.add(mailSend);
    }
}
