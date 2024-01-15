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
    private Map<String, Mail> mailSent;
    private Map<String, Mail> mailReceived;
    private boolean connected;
    private String address = null;
    private int broadcastPort = 0;
    private int mailPort = 0;

    private final String[] MAIL_HEADER = {"Uuid", "Sender", "Recipients", "Object", "Text", "CreationDateTime", "LastModifyDateTime", "read"};
    private String csvMailSendPath = null;
    private String csvMailReceivedPath = null;

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
    public UserData(String username, String address, int mailPort, int broadcastPort) {
        this.mailSent = new HashMap<>();
        this.mailReceived = new HashMap<>();
        this.connected = true;
        this.address = address;
        this.mailPort = mailPort;
        this.broadcastPort = broadcastPort;

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
    public String getLastModifyData() {
        return   mailSent.values().stream()
                .flatMap(mail -> Stream.of(mail, mailReceived.get(mail.getUuid())))
                .filter(Objects::nonNull)
                .sorted()
                .map(Mail::getLastModify)
                .findFirst()
                .orElse("01/01/0001 00:00:00");
    }
    public Map<String, Mail> getMailSent() {
        return mailSent;
    }
    public Map<String, Mail> getMailReceived() {
        return mailReceived;
    }
    public Map<String, Mail> getMailSentNotDelete() {
        return mailSent.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isDelete())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Map<String, Mail> getMailReceivedNotDelete() {
        return mailReceived.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isDelete())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public ArrayList<Mail> getMailsSent() {
        return new ArrayList<>(getMailSentNotDelete().values().stream()
                .sorted()
                .toList());
    }
    public ArrayList<Mail> getMailsReceived() {
        return new ArrayList<>(getMailReceivedNotDelete().values().stream()
                .sorted()
                .toList());
    }
    public Map<String, Mail> getMailSent(String lastConnectionDatatime) {
        return  mailSent.entrySet().stream()
                .filter(m -> m.getValue().moreRecentlyOf(lastConnectionDatatime))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Map<String, Mail> getMailReceived(String lastConnectionDatatime) {
        return  mailReceived.entrySet().stream()
                .filter(m -> m.getValue().moreRecentlyOf(lastConnectionDatatime))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Map<String, Mail> getMailSentListMoreRecentlyOf(String date) {
        return  mailSent.values().stream()
                .filter(m -> m.moreRecentlyOf(date))
                .collect(Collectors.toMap(Mail::getUuid, mail -> mail));
    }
    public Map<String, Mail> getMailReceivedListMoreRecentlyOf(String date) {
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
    public void setReadMailReceived(Mail mail) {
        mailReceived.get(mail.getUuid()).setRead(); backup();
    }
    public void setMailRead(String uuid) {
        mailReceived.get(uuid).setRead();
        backup();
    }
    public void loadSendMails() {
        mailSent.clear();
        loadBackup();
    }
    public void loadReceivedMails() {
        mailReceived.clear();
        loadBackup();
    }

    public void updateUsername(String newUsername) {
        if (this.username != null && !username.equals(newUsername))
            setUsername(newUsername);
    }
    public void updateMailSent(Map<String, Mail> updates) {
        mailSent.putAll(updates);
        backup();
    }
    public void updateMailReceived(Map<String, Mail> updates) {
        mailReceived.putAll(updates);
        backup();
    }
    public void addMailSent(Mail mail) {
        this.mailSent.put(mail.getUuid(), mail);
        backup();
    }
    public void addMailReceived(Mail mail) {
        this.mailReceived.put(mail.getUuid(), mail);
        backup();
    }

    public void clearMailListReceived() { mailReceived.clear(); }
    public void clearMailListSent() {
        mailSent.clear();
    }
    public void deleteMailListReceived() {
        mailReceived.values().forEach(Mail::setDelete);
        backup();
    }
    public void deleteMailListSent() { mailSent.values().forEach(Mail::setDelete); backup(); }
    public void removeMailReceived(Mail mail) {
        mailReceived.get(mail.getUuid()).setDelete(); backup();
    }
    public void removeMailSent(Mail mail) {
        mailSent.get(mail.getUuid()).setDelete(); backup();
    }
    public void deleteMailSentList() {
        getMailSentNotDelete().values().forEach(Mail::setDelete);
        backup();
    }
    public void deleteMailReceivedList() {
        getMailReceivedNotDelete().values().forEach(Mail::setDelete);
        backup();
    }
    public void deleteMailSent(String uuid) {
        getMailSentNotDelete().get(uuid).setDelete();
        backup();
    }
    public void deleteMailReceived(String uuid) {
        getMailReceivedNotDelete().get(uuid).setDelete();
        backup();
    }

    // BACKUP
    private synchronized void loadBackup() {
        if(this.username != null) {
            mailSent = readCSVMailSender();
            mailReceived = readCSVMailReceiver();
        }
    }
    private synchronized void backup() {
        if(this.username != null) {
            writeCSVMailSender();
            writeCSVMailReceiver();
        }
    }

    // CSV
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
