package com.example.prova_server.model;

import java.util.ArrayList;

public class UserData {
    String address;
    private ArrayList<Mail> mailSent;
    private ArrayList<Mail> mailReceived;
    private boolean connected;

    public UserData(String address) {
        this.address = address;
        this.mailSent = new ArrayList<>();
        this.mailReceived = new ArrayList<>();
    }

    public String getAddress() {
        return address;
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
        mailReceived.stream().filter(m -> m.getUuid().equals(mail.getUuid())).forEach(Mail::setRead);
    }
    public void setReadMailSent(Mail mail) {
        mailSent.stream().filter(m -> m.getUuid().equals(mail.getUuid())).forEach(Mail::setRead);
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

    public ArrayList<Mail> getMailSent(String lastConnectionData, String lastConnectionTime) {
        return new ArrayList<> (mailSent.stream().filter(m -> m.moreRecentlyOf(lastConnectionData, lastConnectionTime)).toList());
    }

    public ArrayList<Mail> getMailReceived(String lastConnectionData, String lastConnectionTime) {
        return new ArrayList<> (mailReceived.stream().filter(m -> m.moreRecentlyOf(lastConnectionData, lastConnectionTime)).toList());
    }
}
