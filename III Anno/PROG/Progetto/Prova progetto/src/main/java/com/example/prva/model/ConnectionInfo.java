package com.example.prva.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConnectionInfo implements Serializable {
    private boolean connected;
    private String username;
    private String lastModifyDate;
    private String lastModifyTime;

    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

    public ConnectionInfo(boolean connected, String username, String lastModifyDate, String lastModifyTime){
        this.connected = connected;
        this.username = username;
        this.lastModifyDate = lastModifyDate;
        this.lastModifyTime = lastModifyTime;
    }

    public ConnectionInfo(boolean connected, String username){
        Date now = new Date();
        this.connected = connected;
        this.username = username;
        this.lastModifyDate = formatDate.format(now);
        this.lastModifyTime = formatTime.format(now);
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

    public String getUsername() {
        return username;
    }
    public String getLastModifyDate() {
        return lastModifyDate;
    }
    public String getLastModifyTime() {
        return lastModifyTime;
    }
}
