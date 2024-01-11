package com.example.prova_server.model;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Mail implements Serializable, Comparable<Mail> {
    private String uuid;
    private String sender;
    private String recipients;
    private String text;
    private String object;
    private String creationDateTime;
    private String lastModifyDateTime;

    private boolean delete;
    private boolean read;

    Mail(String sender, String recipients, String object, String text){
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.creationDateTime = formatDateTime.format(now);
        this.lastModifyDateTime = formatDateTime.format(now);
        this.read = false;
        this.delete = false;
        this.uuid = UUID.randomUUID().toString();
    }

    Mail(String sender, String recipients, String object, String text, String creationDateTime, String  lastModifyDateTime, boolean read, String uuid){
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.creationDateTime = creationDateTime;
        this.lastModifyDateTime = lastModifyDateTime;
        this.read = read;
        this.uuid = uuid;
    }

    public String getSender()       { return sender;        }
    public String getRecipients()   { return recipients;    }
    public String getText()         { return text;          }
    public String getObject()       { return object;        }
    public String getCreationDateTime(){ return creationDateTime;  }
    public String getDate(){
        return Arrays.asList(creationDateTime.split(" ")).get(0);
    }
    public String getTime(){
        return Arrays.asList(creationDateTime.split(" ")).get(1);
    }
    public String getLastModify()   { return lastModifyDateTime;}
    public String getUuid()         { return uuid;          }
    public boolean getRead()        { return read;          }
    public List<String> getRecipientsList()   {
        return List.of(recipients.split("\\s*,\\s*"));
    }

    public void setRead() {
        this.read = false;
        setLastModify();
    }
    public void setDelete() {
        this.sender = null;
        this.recipients = null;
        this.object = null;
        this.text = null;
        this.creationDateTime = null;
        this.delete = true;
        setLastModify();
    }
    public void setLastModify() {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lastModifyDateTime = formatDateTime.format(new Date());
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean moreRecentlyOf(String DateTime) {
        if(DateTime == null)
            return true;
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date lastConnectionDateTime = formatDateTime.parse(DateTime);
            Date currentDateTime = formatDateTime.parse(lastModifyDateTime);
            return currentDateTime.after(lastConnectionDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int compareTo(Mail otherMail) {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date otherDateTime = formatDateTime.parse(otherMail.getLastModify());
            Date thisDateTime = formatDateTime.parse(lastModifyDateTime);
            return otherDateTime.compareTo(thisDateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return this.uuid.equals(mail.getUuid());
    }
}