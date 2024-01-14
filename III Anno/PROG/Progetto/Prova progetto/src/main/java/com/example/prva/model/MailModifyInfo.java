package com.example.prva.model;

import java.io.Serializable;

public class MailModifyInfo implements Serializable {
    private String username;
    private Mail mail;
    private boolean sent = false;
    private boolean create = false;
    private boolean delete = false;
    private boolean read = false;
    private boolean deleteAll = false;

    public MailModifyInfo(Mail mail, String username, boolean sent){
        this.mail = mail;
        this.username = username;
        this.sent = sent;
    }

    public MailModifyInfo(Mail mail, String username, boolean sent, boolean create, boolean delete,  boolean read, boolean deleateAll){
        this.mail = mail;
        this.username = username;
        this.sent = sent;
        this.create = create;
        this.delete = delete;
        this.read = read;
        this.deleteAll = deleateAll;
    }

    public MailModifyInfo setCreated() {
        this.create = true;
        return this;
    }
    public MailModifyInfo setReaded() {
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

    public boolean isCreate() {
        return create;
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
