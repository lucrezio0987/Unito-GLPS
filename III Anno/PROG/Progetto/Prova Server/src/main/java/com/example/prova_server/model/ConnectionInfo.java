package com.example.prova_server.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ConnectionInfo implements Serializable {
    private boolean connected;
    private String username;
    private String lastConnectionDateTime;
    private int broadcastPort;
    private int mailPort;

    public ConnectionInfo(boolean connected, String username, String lastConnectionDateTime, int broadcastPort, int mailPort){
        this.connected = connected;
        this.username = username;
        this.lastConnectionDateTime = lastConnectionDateTime;
        this.broadcastPort = broadcastPort;
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
    public ConnectionInfo(int broadcastPort, int mailPort, String lastConnectionDateTime){
        this.broadcastPort = broadcastPort;
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
    public void setBroadcastPort(int broadcastPort) {
        this.broadcastPort = broadcastPort;
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
    public int getBroadcastPort() {
        return broadcastPort;
    }
    public int getMailPort() {
        return mailPort;
    }

}
