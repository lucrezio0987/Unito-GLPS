package com.example.prva.model;

/*import java.text.SimpleDateFormat; DA UTILIZZARE PER DATA E ORA
import java.util.Date;*/

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class MailModel {
    private ArrayList<Mail> mailSent = new ArrayList<>();
    private ArrayList<Mail> mailReceived = new ArrayList<>();
    private Mail mail;

    private SimpleStringProperty textMailSendProperty = null; // testo mail da inviare
    private SimpleStringProperty textMailReceivedProperty = null; //testo mail ricevuta
    private SimpleStringProperty textMailSentProperty = null; // testo mail inviata

    private SimpleStringProperty addressMailSendProperty = null; // address mail da inviare
    private SimpleStringProperty addressMailReceivedProperty = null; // address mail ricevuta
    private SimpleStringProperty addressMailSentProperty = null; // address mail inviata

    private SimpleStringProperty objectMailSendProperty = null; // oggetto mail da inviare
    private SimpleStringProperty objectMailReceivedProperty = null; // oggetto mail ricevuta
    private SimpleStringProperty objectMailSentProperty = null; // oggetto mail inviata

    int indexMail_Ricevuta = 0;
    int indexMail_Inviata = 0;

    public ArrayList<Mail> getListMailSent(){
        return this.mailSent;
    }

    public void setMailSent(){}

    public ArrayList<Mail> getListMailReceived(){
        return this.mailReceived;
    }

    public void setMailReceived(){}

    public Mail getMail(){
        return this.mail;
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

    private static class Mail{
        private String address;
        private String message;
        private String object;
        private String time;
        private String date;

        Mail(String address, String object, String message, String time, String date){

            this.address = address;
            this.object = object;
            this.message = message;
            this.time = time;
            this.date = date;



        }
    }
}
