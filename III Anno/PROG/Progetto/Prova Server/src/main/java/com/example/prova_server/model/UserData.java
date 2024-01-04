package com.example.prova_server.model;

import java.util.ArrayList;

public class UserData {
    String username;
    private ArrayList<Mail> mailSent;
    private ArrayList<Mail> mailReceived;
    private boolean connected;
    private String clientAddress;

    public UserData(String username) {
        this.username = username;
        this.mailSent = new ArrayList<>();
        this.mailReceived = new ArrayList<>();
        this.connected = false;
    }

    public UserData(String username, String clientAddress) {
        this.username = username;
        this.mailSent = new ArrayList<>();
        this.mailReceived = new ArrayList<>();
        this.connected = true;
        this.clientAddress = clientAddress;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Mail> getMailSent() {
        return mailSent;
    }
    public ArrayList<Mail> getMailReceived() {
        return mailReceived;
    }

    public void addMailSent(Mail mail) {
        this.mailSent.add(mail);
    }
    public void addMailReceived(Mail mail) {
        this.mailReceived.add(mail);
    }

    public void removeMailRecived(Mail mail) {
        mailReceived.stream()
                .filter(m -> m.getUuid().equals(mail.getUuid()))
                .forEach(Mail::setDelete);
    }
    public void removeMailSent(Mail mail) {
        mailSent.stream()
            .filter(m -> m.getUuid().equals(mail.getUuid()))
            .forEach(Mail::setDelete);
    }

    public void setReadMailRecived(Mail mail) {
        mailReceived.stream().filter(m -> m.equals(mail)).forEach(Mail::setRead);
    }
    public void setReadMailSent(Mail mail) {
        mailSent.stream().filter(m -> m.equals(mail)).forEach(Mail::setRead);
    }

    public void loadSendMails(ArrayList<Mail> sender) {
        mailSent.clear();
        mailSent.addAll(sender);
    }
    public void loadReceivedMails(ArrayList<Mail> received) {
        mailReceived.clear();
        mailReceived.addAll(received);
    }

    public void setOn(boolean connected) {
        this.connected = connected;
    }
    public boolean isOn() {
        return connected;
    }

    public void clearMailListRecived() {
        mailReceived.clear();
    }
    public void clearMailListSent() {
        mailSent.clear();
    }

    public ArrayList<Mail> getMailSent(String lastConnectionDatatime) {
        return new ArrayList<> (mailSent.stream().filter(m -> m.moreRecentlyOf(lastConnectionDatatime)).toList());
    }
    public ArrayList<Mail> getMailReceived(String lastConnectionDatatime) {
        return new ArrayList<> (mailReceived.stream().filter(m -> m.moreRecentlyOf(lastConnectionDatatime)).toList());
    }

    public String getClientAddress() {
        return clientAddress;
    }
}
