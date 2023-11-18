package com.example.prva.model;
import java.util.List;
import java.util.UUID;

public class Mail{
    private String uuid;
    private String sender;
    private String recipients;
    private String text;
    private String object;
    private String date;
    private String time;

    Mail(String sender, String recipients, String object, String text, String date, String time){
        this.sender = sender;
        this.recipients = recipients;
        this.object = object;
        this.text = text;
        this.date = date;
        this.time = time;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getSender()       { return sender;        }
    public String getRecipients()   { return recipients;    }
    public String getText()         { return text;          }
    public String getObject()       { return object;        }
    public String getDate()         { return date;          }
    public String getTime()         { return time;          }
    public String getUuid()         { return uuid;          }

    public List<String> getRecipientsList()   { return List.of(recipients.split("\\s*,\\s*")); }

}