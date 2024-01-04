package com.example.prva.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Mail implements Serializable {
    private String uuid;
    private String sender;
    private String recipients;
    private String text;
    private String object;
    private String creationDate;
    private String creationTime;
    private String lastModifyDate;
    private String lastModifyTime;

    private boolean delete;
    private boolean read;

    Mail(String sender, String recipients, String object, String text){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        String date = formatDate.format(now);
        String time = formatTime.format(now);
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.creationDate = date;   this.lastModifyDate = date;
        this.creationTime = time;   this.lastModifyTime = time;
        this.read = false;
        this.delete = false;
        this.uuid = UUID.randomUUID().toString();
    }

    Mail(String sender, String recipients, String object, String text, String creationDate, String creationTime, String  lastModifyDate, String  lastModifyTime, boolean read, String uuid){
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.creationDate = creationDate;
        this.creationTime = creationTime;
        this.lastModifyDate = lastModifyDate;
        this.lastModifyTime = lastModifyTime;
        this.read = read;
        this.uuid = uuid;
    }

    public String getSender()       { return sender;        }
    public String getRecipients()   { return recipients;    }
    public String getText()         { return text;          }
    public String getObject()       { return object;        }
    public String getDate()         { return creationDate;          }
    public String getTime()         { return creationTime;          }
    public String getLastModifyDate()         { return lastModifyDate;          }
    public String getLastModifyTime()         { return lastModifyTime;          }
    public String getUuid()         { return uuid;          }
    public boolean getRead()        { return read;          }

    public List<String> getRecipientsList()   { return List.of(recipients.split("\\s*,\\s*")); }

    public void setRead() {
        this.read = false;
        setLastModify();
    }
    public void setDelete() {
        this.sender = null;
        this.recipients = null;
        this.object = null;
        this.text = null;
        this.creationDate = null;
        this.creationTime = null;
        this.delete = true;
        setLastModify();
    }

    public boolean isDelete() {
        return delete;
    }

    public void setLastModify() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        this.lastModifyDate = formatDate.format(now);
        this.lastModifyTime = formatTime.format(now);
    }

    public boolean moreRecentlyOf(String lastConnectionData, String lastConnectionTime) {

        // TODO: controlla se questo oggetto è più recente dei lastConnectionData e lastConnectionTime
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return this.uuid.equals(mail.getUuid());
    }
}