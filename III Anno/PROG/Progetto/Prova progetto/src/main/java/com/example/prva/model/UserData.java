package com.example.prva.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserData {
    private String username;
    private Map<String, Mail> mailSent; // mappa mail inviate <uuid, mail>
    private Map<String, Mail> mailReceived; // mappa mail ricevute <uuid, mail>
    private boolean connected;
    private String address = null;
    private int broadcastPort = 0;
    private int mailPort = 0;

    private final String[] MAIL_HEADER = {"Uuid", "Sender", "Recipients", "Object", "Text", "CreationDateTime", "LastModifyDateTime", "read"};
    private String csvMailSendPath = null; // percorso file mail inviate
    private String csvMailReceivedPath = null; // percorso file mail ricevute

    // costruttori
    public UserData() {
        this.username = null;
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = false;
    }
    public UserData(String username) {
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = false;

        setUsername(username);
        loadBackup();
    }

    public boolean isOn() {
        return connected;
    }
    public String getAddress() {
        return address;
    }
    public int getMailPort() {
        return mailPort;
    }
    public int getBroadcastPort() {
        return broadcastPort;
    }
    public String getUsername() {
        return this.username;
    }

    // restituisce l'ultima modifica effettuata a una mail, 01/01/0001 se non ci sono mail e di conseguenza modifiche
    public String getLastModifyData() {
        return Stream.concat(mailSent.values().stream(), mailReceived.values().stream())
                .filter(Objects::nonNull)
                .sorted()
                .map(Mail::getLastModify)
                .findFirst()
                .orElse("01/01/0001 00:00:00");
    }

    // restituisce la mappa delle mail inviate con il parametro delete = false
    public synchronized Map<String, Mail> getMailSentNotDelete() {
        return mailSent.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isDelete())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // restituisce la mappa delle mail ricevute con il parametro delete = false
    public synchronized Map<String, Mail> getMailReceivedNotDelete() {
        return mailReceived.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isDelete())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // restituisce la mappa di mail inviate
    public synchronized Map<String, Mail> getMailSent() {
        return mailSent;
    }

    // restituisce la mappa di mail ricevute
    public synchronized Map<String, Mail> getMailReceived() {
        return mailReceived;
    }

    // restituisce le mail inviate (dopo la data passatagli come parametro) (usata lato server)
    public synchronized Map<String, Mail> getMailSent(String lastConnectionDatatime) {
        return  mailSent.entrySet().stream()
                .filter(m -> m.getValue().moreRecentlyOf(lastConnectionDatatime))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // restituisce le mail ricevute (dopo la data passatagli come parametro) (usata lato server)
    public synchronized Map<String, Mail> getMailReceived(String lastConnectionDatatime) {
        return  mailReceived.entrySet().stream()
                .filter(m -> m.getValue().moreRecentlyOf(lastConnectionDatatime))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // restituisce le mail ricevute più recenti della data passata come parametro
    public synchronized Map<String, Mail> getMailSentListMoreRecentlyOf(String date) {
        return  mailSent.values().stream()
                .filter(m -> m.moreRecentlyOf(date))
                .collect(Collectors.toMap(Mail::getUuid, mail -> mail));
    }
    // restituisce le mail ricevute più recenti della data passata come parametro
    public synchronized Map<String, Mail> getMailReceivedListMoreRecentlyOf(String date) {
        return  mailReceived.values().stream()
                .filter(m -> m.moreRecentlyOf(date))
                .collect(Collectors.toMap(Mail::getUuid, mail -> mail));
    }

    public void setOn(boolean connected) {
        if(username != null) {
            if (!connected) {
                backup();
                this.address = null;
                this.mailPort = 0;
                this.broadcastPort = 0;
            } else
                loadBackup();
        }
        this.connected = connected;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    private void setUsername(String username) {
        backup();
        this.username = username;
        mailSent.clear();
        mailReceived.clear();

        csvMailSendPath = pathConstructor(username, "sender");
        csvMailReceivedPath = pathConstructor(username, "received");

        loadBackup();
        //TODO: terminare implementazione
    }
    public void setBroadcastPort(int broadcastPort) {
        this.broadcastPort = broadcastPort;
    }
    public void setMailPort(int mailPort) {
        this.mailPort = mailPort;
    }
    public synchronized void setMailRead(String uuid) {
        mailReceived.get(uuid).setRead();
        backup();
    }

    // chiamato quando lo username del client viene aggiornato
    public void updateUsername(String newUsername) {
        if (this.username != null) {
            if(!username.equals(newUsername))
                setUsername(newUsername);
        } else
            setUsername(newUsername);
    }
    // aggiorna tutte le mail inviate, con le modifiche che ci sono state dall'ultima connessione (alla riconnessione)
    public synchronized void updateMailSent(Map<String, Mail> updates) {
        mailSent.putAll(updates);
        backup();
    }
    // aggiorna tutte le mail ricevute, con le modifiche che ci sono state dall'ultima connessione (alla riconnessione)
    public synchronized void updateMailReceived(Map<String, Mail> updates) {
        mailReceived.putAll(updates);
        backup();
    }
    // aggiunge la mail inviata alla mappa mailSent
    public synchronized void addMailSent(Mail mail) {
        this.mailSent.put(mail.getUuid(), mail);
        backup();
    }
    // aggiunge le mail ricevute alla mappa mailReceived
    public synchronized void addMailReceived(Mail mail) {
        this.mailReceived.put(mail.getUuid(), mail);
        backup();
    }
    // cancelli la lista locale, senza cancellare le mail, esempio quando si cambia utente
    public void clearMailListReceived() { mailReceived.clear(); }
    // cancelli la lista locale, senza cancellare le mail, esempio quando si cambia utente
    public void clearMailListSent() {
        mailSent.clear();
    }
    // cancella la lista di mail ricevute (delete all)
    public synchronized void deleteMailListReceived() {
        mailReceived.values().forEach(Mail::setDelete);
        backup();
    }
    // cancella la lista di mail inviate (delete all)
    public synchronized void deleteMailListSent() { mailSent.values().forEach(Mail::setDelete); backup(); }
    // cancella la mail inviata con parametro uuid
    public synchronized void deleteMailSent(String uuid) {
        getMailSentNotDelete().get(uuid).setDelete();
        backup();
    }
    // cancella la mail ricevuta con parametro uuid
    public synchronized void deleteMailReceived(String uuid) {
        getMailReceivedNotDelete().get(uuid).setDelete();
        backup();
    }

    // load BACKUP aggiorna la mail sent e la mail received leggendo il file
    private synchronized void loadBackup() {
        if(this.username != null) {
            mailSent = readCSVMailSender();
            mailReceived = readCSVMailReceiver();
        }
    }
    // backup aggiorna i file dopo ad esempio l'invio o la cancellazione di una mail
    private synchronized void backup() {
        if(this.username != null) {
            writeCSVMailSender();
            writeCSVMailReceiver();
        }
    }

    // CSV scrittura
    private void writeCSVMail(String path, Map<String, Mail> mailList){
        Map<String, Mail> mailMap = readCSVMail(path);
        mailList.values().forEach(mail -> mailMap.put(mail.getUuid(), mail));

        try (Writer writer = new FileWriter(path, false);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(MAIL_HEADER))) {
            for (Mail mail : mailMap.values())
                csvPrinter.printRecord(
                        mail.getUuid(),
                        mail.getSender(),
                        mail.getRecipients(),
                        mail.getObject(),
                        mail.getText(),
                        mail.getCreationDateTime(),
                        mail.getLastModify(),
                        mail.getRead());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeCSVMailSender(){
        writeCSVMail(csvMailSendPath, mailSent);
    }
    private void writeCSVMailReceiver(){
        writeCSVMail(csvMailReceivedPath, mailReceived);
    }

    // CSV lettura
    private Map<String, Mail> readCSVMail(String path) {
        createFileIfNotExists(path);

        Map<String, Mail> mailMap = new HashMap<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(path), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String r0 = csvRecord.get(MAIL_HEADER[0]); // uuid
                String r1 = csvRecord.get(MAIL_HEADER[1]); // sender
                String r2 = csvRecord.get(MAIL_HEADER[2]); // recipients
                String r3 = csvRecord.get(MAIL_HEADER[3]); // object
                String r4 = csvRecord.get(MAIL_HEADER[4]); // text
                String r5 = csvRecord.get(MAIL_HEADER[5]); // creationDateTime
                String r6 = csvRecord.get(MAIL_HEADER[6]); // lastModifyDateTime
                boolean r7 = Boolean.parseBoolean(csvRecord.get(MAIL_HEADER[7])); // read

                mailMap.put(r0 , new Mail(r1, r2, r3, r4, r5, r6, r7, r0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mailMap;
    }
    private Map<String, Mail> readCSVMailSender() {
        return readCSVMail(csvMailSendPath);
    }
    private Map<String, Mail> readCSVMailReceiver() {
        return readCSVMail(csvMailReceivedPath);
    }

    // FILE
    private boolean FileExist(String path) {
        return new File(path).exists();
    }
    private void createFileIfNotExists(String path) {
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();
        if (!FileExist(path)) {
            createFileAndWriteHeader(path, MAIL_HEADER);
        }
    }
    private void createFileAndWriteHeader(String path, String[] headers) {

        try (Writer writer = new FileWriter(path, false);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            csvPrinter.printRecord();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // genera il percorso del file
    public String pathConstructor(String username, String type) {
        String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "backup";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();
        if(type.equals("data") || type.equals("log"))
            return directoryPath + File.separator  + type + ".csv";
        else
            return directoryPath + File.separator + username + "-" + type + ".csv";

    }
}
