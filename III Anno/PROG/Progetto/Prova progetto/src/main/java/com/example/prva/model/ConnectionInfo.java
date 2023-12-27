package com.example.prva.model;

import java.io.Serializable;

public class ConnectionInfo implements Serializable {

    private boolean connected;
    private String username;

    public ConnectionInfo(String username, boolean connected){
        this.connected = connected;
        this.username = username;
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
}
