package com.example.prva.model;

import java.io.Serializable;

public class MailModifyInfo implements Serializable {

    private Mail mail;
    private boolean create = false;
    private boolean deleate = false;
    private boolean read = false;

    public MailModifyInfo(Mail mail){
        this.mail = mail;
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
        this.deleate = true;
        return this;
    }

    public boolean isCreate() {
        return create;
    }

    public boolean isDeleate() {
        return deleate;
    }

    public boolean isRead() {
        return read;
    }
}
