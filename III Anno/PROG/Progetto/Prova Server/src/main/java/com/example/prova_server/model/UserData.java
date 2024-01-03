package com.example.prova_server.model;

import java.util.ArrayList;

public class UserData {
    String address;
    private ArrayList<Mail> mailSent;
    private ArrayList<Mail> mailReceived;
    private ArrayList<MailModifyInfo> mailSentOfflineModify;
    private ArrayList<MailModifyInfo> mailReceivedOfflineModify;
    private boolean connected;

    public UserData(String address) {
        this.address = address;
        this.mailSent = new ArrayList<>();
        this.mailReceived = new ArrayList<>();
        this.mailSentOfflineModify = new ArrayList<>();
        this.mailReceivedOfflineModify = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Mail> getMailSent() {
        return mailSent;
    }

    public void addMailSent(Mail mail) {
        this.mailSent.add(mail);
    }
    public void addMailReceived(Mail mail) {
        this.mailReceived.add(mail);
    }

    public ArrayList<Mail> getMailReceived() {
        return mailReceived;
    }

    public void removeMailRecived(Mail mail) {mailReceived.remove(mail);}
    public void removeMailSent(Mail mail) {mailSent.remove(mail);}

    public void setReadMailRecived(Mail mail) {mailReceived.stream().filter(m -> m.getUuid().equals(mail.getUuid())).forEach(m -> m.setRead(true));}
    public void setReadMailSent(Mail mail) {mailSent.stream().filter(m -> m.getUuid().equals(mail.getUuid())).forEach(m -> m.setRead(true));}


    public ArrayList<MailModifyInfo> getMailSentOfflineModify() {
        return mailSentOfflineModify;
    }

    public void addMailSentOfflineModify(MailModifyInfo modifyInfo) {
        this.mailSentOfflineModify.add(modifyInfo);
    }

    public ArrayList<MailModifyInfo> getMailReceivedOfflineModify() {
        return mailReceivedOfflineModify;
    }

    public void addMailReceivedOfflineModify(MailModifyInfo modifyInfo) {
        this.mailReceivedOfflineModify.add(modifyInfo);
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
}
