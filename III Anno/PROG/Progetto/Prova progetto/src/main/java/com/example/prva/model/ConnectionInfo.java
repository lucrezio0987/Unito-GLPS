package com.example.prva.model;

import java.io.Serializable;
import java.util.List;

public class ConnectionInfo implements Serializable {
    private boolean connected;
    private String username;

    private List<MailModifyInfo> mailSentOfflineModify;
    private List<MailModifyInfo> mailReceivedOfflineModify;
    private String lastUuidSent;
    private String lastUuidReceived;

    public ConnectionInfo(boolean connected, String username, List<MailModifyInfo> mailSentOfflineModify, List<MailModifyInfo> mailReceivedOfflineModify, String lastUuidSent, String lastUuidReceived){
        this.connected = connected;
        this.username = username;
        this.mailSentOfflineModify = mailSentOfflineModify;
        this.mailReceivedOfflineModify = mailReceivedOfflineModify;
        this.lastUuidSent = lastUuidSent;
        this.lastUuidReceived = lastUuidReceived;

    }

    public boolean isConnected() {
        return connected;
    }

    public String getUsername() {
        return username;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MailModifyInfo> getMailSentOfflineModify() {
        return mailSentOfflineModify;
    }
    public List<MailModifyInfo> getMailReceivedOfflineModify() {
        return mailReceivedOfflineModify;
    }

    public String getLastUuidSent() { return lastUuidSent; }

    public String getLastUuidReceived() { return lastUuidReceived; }
}
