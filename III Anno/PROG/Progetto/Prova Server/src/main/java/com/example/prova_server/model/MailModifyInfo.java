package com.example.prova_server.model;

import java.io.Serializable;

public class MailModifyInfo implements Serializable {

    private String username;
    private Mail mail;
    private boolean sent = false;
    private boolean create = false;
    private boolean delete = false;
    private boolean read = false;

    public MailModifyInfo(Mail mail, String username, boolean sent){
        this.mail = mail;
        this.username = username;
        this.sent = sent;
    }

    public Mail getMail() {
        return mail;
    }

    public MailModifyInfo setCreated() {
        this.create = true;
        return this;
    }
    public MailModifyInfo setReaded() {
        this.read = true;
        return this;
    }
    public MailModifyInfo setDeleate() {
        this.delete = true;
        return this;
    }
    public boolean isCreate() {
        return create;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isRead() {
        return read;
    }
    
    public String getUsername(){
        return username;
    }

    public boolean getSent() {
        return sent;
    }
}
