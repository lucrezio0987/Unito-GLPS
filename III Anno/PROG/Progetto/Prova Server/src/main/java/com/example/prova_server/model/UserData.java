package com.example.prova_server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserData {
    String username;
    private Map<String, Mail> mailSent;
    private Map<String, Mail> mailReceived;
    private boolean connected;
    private String clientAddress;
    private int broadcastPort = 0;
    private int mailPort = 0;

    public UserData(String username) {
        this.username = username;
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = false;
    }

    public UserData(String username, String clientAddress, int mailPort, int broadcastPort) {
        this.username = username;
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = true;
        this.clientAddress = clientAddress;
        this.mailPort = mailPort;
        this.broadcastPort = broadcastPort;
    }

    public Map<String, Mail> getMailSent() {
        return mailSent;
    }
    public Map<String, Mail> getMailReceived() {
        return mailReceived;
    }
    public Map<String, Mail> getMailSentNotDelete() {
        return mailSent.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isDelete())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Object, Object> getMailReceivedNotDelete() {
        return mailReceived.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isDelete())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        if(!connected){
            this.clientAddress = null;
            this.mailPort = 0;
            this.broadcastPort = 0;
        }
        this.connected = connected;
    }
    public boolean isOn() {
        return connected;
    }

    public void clearMailListRecived() {
        mailReceived.values().forEach(Mail::setDelete);
    }
    public void clearMailListSent() {
        mailSent.values().forEach(Mail::setDelete);
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

    public void updateMailSent(Map<String, Mail> uppdates) {
        mailSent.putAll(uppdates);
    }

    public void updateMailReceived(Map<String, Mail> uppdates) {
        mailReceived.putAll(uppdates);
    }

    public void setAddress(String address) {
        this.clientAddress = address;
    }

    public int getMailPort() {
        return mailPort;
    }
    public int getBroadcastPort() {
        return broadcastPort;
    }

    public void setBroadcastPort(int broadcastPort) {
        this.broadcastPort = broadcastPort;
    }
    public void setMailPort(int mailPort) {
        this.mailPort = mailPort;
    }

}
