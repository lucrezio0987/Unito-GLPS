package com.example.prva.model;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Mail implements Serializable {
    private String uuid;
    private String sender;
    private String recipients;
    private String text;
    private String object;
    private String date;
    private String time;
    private boolean read;

    Mail(String sender, String recipients, String object, String text, String date, String time, boolean read){
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.date = date;
        this.time = time;
        this.read = read;
        this.uuid = UUID.randomUUID().toString();
    }
    Mail(String sender, String recipients, String object, String text, String date, String time, boolean read, String uuid){
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.date = date;
        this.time = time;
        this.read = read;
        this.uuid = uuid;
    }

    public String getSender()       { return sender;        }
    public String getRecipients()   { return recipients;    }
    public String getText()         { return text;          }
    public String getObject()       { return object;        }
    public String getDate()         { return date;          }
    public String getTime()         { return time;          }
    public String getUuid()         { return uuid;          }
    public boolean getRead()        { return read;          }

    public List<String> getRecipientsList()   { return List.of(recipients.split("\\s*,\\s*")); }

    public void setRead(boolean read) { this.read = read; }

}