package com.example.prva.model;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<Mail> mailSent;
    private final List<Mail> mailReceived;

    public Server() {
        mailSent = new ArrayList<>();
        mailReceived = new ArrayList<>();
        setMailSent();
        setMailReceived();
    }

    public List<Mail> getMailSent() { return mailSent; }
    public List<Mail> getMailReceived() { return mailReceived; }

    public void addMailSent(Mail mail) { mailSent.add(mail); }
    public void addMailReceived(Mail mail) { mailReceived.add(mail); }

    public void deleteMailSent(Mail mail) { mailSent.remove(mail); }
    public void deleteMailReceived(Mail mail) { mailReceived.remove(mail); }

    public void deleteMailSentList() { mailSent.clear(); }
    public void deleteMailReceivedList() { mailReceived.clear(); }

    public void setMailSent() {
        mailSent.add(new Mail(null, "alice@example.com", "Oggetto 1", "Contenuto della mail 1", "28/03/2022", "11:30", false));
        mailSent.add(new Mail(null, "bob@example.com", "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "12:30", false));
        mailSent.add(new Mail(null, "charlie@example.com", "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "13:30", false));
        mailSent.add(new Mail(null, "dave@example.com", "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "14:30", false));
        mailSent.add(new Mail(null, "emma@example.com", "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "15:30", true));
        mailSent.add(new Mail(null, "frank@example.com", "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "16:30", true));
        mailSent.add(new Mail(null, "hannah@example.com", "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "17:30", true));
        mailSent.add(new Mail(null, "irene@example.com", "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "18:30", true));
        mailSent.add(new Mail(null, "jack@example.com", "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "19:30", true));
    }

    public void setMailReceived() {
        mailReceived.add(new Mail("mary@example.com", null, "Oggetto 2", "Contenuto della mail 2", "28/03/2022", "11:30", false));
        mailReceived.add(new Mail("peter@example.com", null, "Oggetto 3", "Contenuto della mail 3", "28/03/2022", "12:30", false));
        mailReceived.add(new Mail("susan@example.com", null, "Oggetto 4", "Contenuto della mail 4", "28/03/2022", "13:30", false));
        mailReceived.add(new Mail("tom@example.com", null, "Oggetto 5", "Contenuto della mail 5", "28/03/2022", "14:30", false));
        mailReceived.add(new Mail("linda@example.com", null, "Oggetto 6", "Contenuto della mail 6", "28/03/2022", "15:30", true));
        mailReceived.add(new Mail("kevin@example.com", null, "Oggetto 7", "Contenuto della mail 7", "28/03/2022", "16:30", true));
        mailReceived.add(new Mail("natalie@example.com", null, "Oggetto 8", "Contenuto della mail 8", "28/03/2022", "17:30", true));
        mailReceived.add(new Mail("alex@example.com", null, "Oggetto 9", "Contenuto della mail 9", "28/03/2022", "18:30", true));
        mailReceived.add(new Mail("olivia@example.com", null, "Oggetto 10", "Contenuto della mail 10", "28/03/2022", "19:30", true));
    }

    public void setMailSentRead(String uuid, boolean read) {
        mailSent.forEach(email -> {if(email.getUuid().equals(uuid)) email.setRead(read);});
    }

    public void setMailReceivedRead(String uuid, boolean read) {
        mailReceived.forEach(email -> {if(email.getUuid().equals(uuid)) email.setRead(read);});
    }
}
