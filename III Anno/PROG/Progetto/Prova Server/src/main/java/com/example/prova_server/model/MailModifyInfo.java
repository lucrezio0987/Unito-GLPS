package com.example.prova_server.model;

import java.io.Serializable;

public class MailModifyInfo implements Serializable {
    private String username;
    private Mail mail;
    private boolean sent = false;
    private boolean delete = false;
    private boolean read = false;
    private boolean deleteAll = false;

    public MailModifyInfo(Mail mail, String username, boolean sent){
        this.mail = mail;
        this.username = username;
        this.sent = sent;
    }

    public MailModifyInfo(Mail mail, String username, boolean sent, boolean delete,  boolean read, boolean deleteAll){
        this.mail = mail;
        this.username = username;
        this.sent = sent;
        this.delete = delete;
        this.read = read;
        this.deleteAll = deleteAll;
    }

    public MailModifyInfo setRead() {
        this.read = true;
        return this;
    }
    public MailModifyInfo setDelete() {
        this.delete = true;
        return this;
    }
    public MailModifyInfo setDeleteAll() {
        this.deleteAll = true;
        return this;
    }

    public boolean isDelete() {
        return delete;
    }
    public boolean isDeleteAll() {
        return deleteAll;
    }
    public boolean isRead() {
        return read;
    }

    public String getUsername(){
        return username;
    }
    public Mail getMail() {
        return mail;
    }
    public boolean getSent() {
        return sent;
    }

}
