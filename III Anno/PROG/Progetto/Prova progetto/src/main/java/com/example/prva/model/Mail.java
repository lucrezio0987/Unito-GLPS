package com.example.prva.model;
import java.util.UUID;

public class Mail{
    private String uuid;
    private String address;
    private String text;
    private String object;
    private String date;
    private String time;

    Mail(String address, String object, String text, String date, String time){
        this.address = address;
        this.object = object;
        this.text = text;
        this.date = date;
        this.time = time;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getAddress()  { return address;   }
    public String getText()     { return text;      }
    public String getObject()   { return object;    }
    public String getDate()     { return date;      }
    public String getTime()     { return time;      }
    public String getUuid()     { return uuid;      }
}