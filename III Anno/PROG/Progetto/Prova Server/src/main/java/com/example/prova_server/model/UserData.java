package com.example.prova_server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserData {
    String username;
    private Map<String, Mail> mailSent;
    private Map<String, Mail> mailReceived;
    private boolean connected;
    private String clientAddress;

    public UserData(String username) {
        this.username = username;
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = false;
    }

    public UserData(String username, String clientAddress) {
        this.username = username;
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = true;
        this.clientAddress = clientAddress;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Mail> getMailSent() {
        return mailSent;
    }
    public Map<String, Mail> getMailReceived() {
        return mailReceived;
    }

    public void addMailSent(Mail mail) {
        this.mailSent.put(mail.getUuid(), mail);
    }
    public void addMailReceived(Mail mail) {
        this.mailReceived.put(mail.getUuid(), mail);
    }

    public void removeMailRecived(Mail mail) {
        mailReceived.get(mail.getUuid()).setDelete();
    }
    public void removeMailSent(Mail mail) {
        mailSent.get(mail.getUuid()).setDelete();
    }

    public void setReadMailRecived(Mail mail) {
        mailReceived.get(mail.getUuid()).setRead();
    }
    public void setReadMailSent(Mail mail) {
        mailSent.get(mail.getUuid()).setRead();
    }

    public void loadSendMails(Map<String, Mail> mailMap) {
        mailSent.clear();
        mailSent = mailMap;
    }
    public void loadReceivedMails(Map<String, Mail> mailMap) {
        mailReceived.clear();
        mailReceived = mailMap;
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

    public Map<String, Mail> getMailSent(String lastConnectionDatatime) {
        return  mailSent.entrySet().stream()
                .filter(m -> m.getValue().moreRecentlyOf(lastConnectionDatatime))
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Map<String, Mail> getMailReceived(String lastConnectionDatatime) {
        return  mailReceived.entrySet().stream()
                .filter(m -> m.getValue().moreRecentlyOf(lastConnectionDatatime))
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String getClientAddress() {
        return clientAddress;
    }
}
