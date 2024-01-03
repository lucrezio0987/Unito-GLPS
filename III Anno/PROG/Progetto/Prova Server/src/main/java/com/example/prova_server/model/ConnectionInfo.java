package com.example.prova_server.model;

import java.io.Serializable;


public class ConnectionInfo implements Serializable {
    private boolean connected;
    private String username;
    private String lastConnectionDate;
    private String lastConnectionTime;

    public ConnectionInfo(boolean connected, String username, String lastConnectionDate, String lastConnectionTime){
        this.connected = connected;
        this.username = username;
        this.lastConnectionDate = lastConnectionDate;
        this.lastConnectionTime = lastConnectionTime;
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
    public String getLastConnectionDate() {
        return lastConnectionDate;
    }
    public String getLastConnectionTime() {
        return lastConnectionTime;
    }
}
