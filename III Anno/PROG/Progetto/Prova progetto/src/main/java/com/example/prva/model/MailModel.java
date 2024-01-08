package com.example.prva.model;

import java.util.ArrayList;
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

        server = new Server(controller);
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
    }
    public void deleteMailSentList(){ server.deleteMailSentList(); }
    public void deleteMailReceivedList(){ server.deleteMailReceivedList(); }

    public void deleteMailSent(String uuid){
        server.deleteMailSent(uuid);
    }
    public void deleteMailReceived(String uuid) {
        server.deleteMailReceived(uuid);
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

        if(!syntaxControll()) {
            addLog("Client", "ERRORE: Indirizzo inserito non valido, email NON inviata");
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

        server.addMailSent(mailSend);
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
            String localAddress = localAddressProperty.get();

            addLog("Client", "Connessione: " + localAddress);

            server.setAddress(localAddress);
            server.connectToServer();

            server.setAddress(localAddressProperty.get());

            controller.setCountMailSent();
            getListMailSent().forEach(controller::createCardSent);

            controller.setCountMailReceived();
            getListMailReceived().forEach(controller::createCardReceived);

        } else {
            addLog("Client", "ERRORE: Indirizzo inserito non valido, connessione NON eseguita");
        }
        return server.isConnected();
    }

    public boolean reconect() {
        if(syntaxControll()) {
            String localAddress = localAddressProperty.get();

            addLog("Client", "Riconnessione: " + localAddress);

            server.disconnectToServer();
            server.setAddress(localAddress);
            server.connectToServer();

            controller.setCountMailSent();
            getListMailSent().forEach(controller::createCardSent);

            controller.setCountMailReceived();
            getListMailReceived().forEach(controller::createCardReceived);

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

    public void stop() {
        server.stop();
    }

}