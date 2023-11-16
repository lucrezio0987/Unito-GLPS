package com.example.prva.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

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

    public void openMailReceived(int index){
    Mail mail = mailReceived.get(index);

    addressMailReceivedProperty.set(mail.address);
    objectMailReceivedProperty.set(mail.object);
    textMailReceivedProperty.set(mail.text);
    }

    // DA RIVEDERE
    // v v v v v v v v v v v
    public void openMailSent(int index){
        Mail mail = null;
        try{
            mail = mailSent.get(index);
        } catch (IndexOutOfBoundsException e) {
            addressMailSentProperty.set("");
            objectMailSentProperty.set("");
            textMailSentProperty.set("");
        }
        assert mail != null;
        addressMailSentProperty.set(mail.address);
        objectMailSentProperty.set(mail.object);
        textMailSentProperty.set(mail.text);
    }

    public void deleteMailSentList(){ mailSent.clear(); }

    public void deleteMailReceivedList(){ mailReceived.clear(); }

    public void deleteMailSent(int index){
        mailSent.remove(index);
        openMailSent(index);
    }

    public void deleteMailReceived(int index){
        mailReceived.remove(index);
        openMailReceived(index);
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

    private static class Mail{
        private String address;
        private String text;
        private String object;
        private String date;
        private String time;

        Mail(String address, String object, String text, String date, String time){

            this.address = address;
            this.object = object;
            this.text = text;
            this.date = date;
            this.time = time;

        }
    }
}
