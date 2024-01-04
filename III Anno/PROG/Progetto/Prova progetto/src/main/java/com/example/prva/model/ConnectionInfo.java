package com.example.prva.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionInfo implements Serializable {
    private boolean connected;
    private String username;
    private String lastConnectionDateTime;

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
    public String getLastConnectionDateTime() {
        return lastConnectionDateTime;
    }

}
