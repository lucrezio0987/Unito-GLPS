package com.example.prva.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionInfo implements Serializable {
    private boolean connected;
    private String username;
    private String lastConnectionDateTime;
    private int mailPort;

    public ConnectionInfo(boolean connected, String username, String lastConnectionDateTime, int mailPort){
        this.connected = connected;
        this.username = username;
        this.lastConnectionDateTime = lastConnectionDateTime;
        this.mailPort = mailPort;
    }
    public ConnectionInfo(boolean connected, String username, String lastConnectionDateTime){
        this.connected = connected;
        this.username = username;
        this.lastConnectionDateTime = lastConnectionDateTime;
    }
    public ConnectionInfo(boolean connected, String username){
        this.connected = connected;
        this.username = username;
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.lastConnectionDateTime = formatDateTime.format(new Date());
    }
    public ConnectionInfo(int mailPort, String lastConnectionDateTime){
        this.mailPort = mailPort;
        this.lastConnectionDateTime = lastConnectionDateTime;
    }

    public boolean isConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setMailPort(int mailPort) {
        this.mailPort = mailPort;
    }

    public String getUsername() {
        return username;
    }
    public String getLastConnectionDateTime() {
        return lastConnectionDateTime;
    }
    public int getMailPort() {
        return mailPort;
    }

}
