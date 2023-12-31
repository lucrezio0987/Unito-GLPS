package com.example.prova_server.model;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    String address;
    private List<Mail> mailSent;
    private List<Mail> mailReceived;
    private List<MailModifyInfo> mailSentOfflineModify;
    private List<MailModifyInfo> mailReceivedOfflineModify;

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

    public List<Mail> getMailSent() {
        return mailSent;
    }

    public void addMailSent(Mail mail) {
        this.mailSent.add(mail);
    }

    public List<Mail> getMailReceived() {
        return mailReceived;
    }

    public void addMailReceived(Mail mail) {
        this.mailReceived.add(mail);
    }

    public List<MailModifyInfo> getMailSentOfflineModify() {
        return mailSentOfflineModify;
    }

    public void addMailSentOfflineModify(MailModifyInfo modifyInfo) {
        this.mailSentOfflineModify.add(modifyInfo);
    }

    public List<MailModifyInfo> getMailReceivedOfflineModify() {
        return mailReceivedOfflineModify;
    }

    public void addMailReceivedOfflineModify(MailModifyInfo modifyInfo) {
        this.mailReceivedOfflineModify.add(modifyInfo);
    }
}
