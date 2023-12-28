package com.example.prova_server.model;

import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModel_2 {
    private static SimpleStringProperty textAreaProperty = null;
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService;

    private static Map<String, String> clients;

    public ServerModel_2() {
        textAreaProperty = new SimpleStringProperty();
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        clients = new ConcurrentHashMap<>();
    }

    public void start() {
        // Thread per gestire la connessione dei client
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                log("Server in ascolto sulla porta 8000 per le connessioni...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    log("\nSocket connessione ricevuto (8000): " + socket.toString());
                    executorService.submit(new ConnectionHandler(socket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Thread per gestire i messaggi dei client
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8001);
                log("Server in ascolto sulla porta 8001 per le mail...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    log("\nSocket mail ricevuto (8001): " + socket.toString());
                    executorService.submit(new MessageHandler(socket));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public SimpleStringProperty getTextAreaProperty() {
        return textAreaProperty;
    }

    private static class ConnectionHandler implements Runnable {
        private Socket socket;

        public ConnectionHandler(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            appendToTextArea("Socket connessione avviato (8000): " + socket.toString());
            try {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonConnectionInfo = (String) inputStream.readObject();
                log("JSON serializzato: " + jsonConnectionInfo);
                ConnectionInfo connectionInfo = new Gson().fromJson(jsonConnectionInfo, ConnectionInfo.class);
                log("JSON deserializzato: " + connectionInfo.getUsername() + ", " + connectionInfo.isConnected());

                if (connectionInfo.isConnected()) {
                    addUser(connectionInfo.getUsername(), socket.getInetAddress().getHostAddress());
                    //TODO: Invia conferma di connessione al client
                    //ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    //outputStream.writeObject("Connessione riuscita");
                    //outputStream.flush();
                } else {
                    removeUser(connectionInfo.getUsername());
                }

                log("Socket connessione chiuso (8000): " + socket.toString());
                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    private static class MessageHandler implements Runnable {
        private Socket socket;

        public MessageHandler(Socket clientSocket) {
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            log("Socket mail avviato (8001): " + socket.toString());
            try {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String jsonMail = (String) inputStream.readObject();
                Mail mail = new Gson().fromJson(jsonMail, Mail.class);

                mail.getRecipientsList().forEach(recipient -> {
                    try {
                        sendMail(getAddressForUser(recipient), mail);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                log("Socket mail chiuso (8001): " + socket.toString());
                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private static void sendMail(String destAddress, Mail mail) throws IOException {

        if (destAddress != null) {
            Socket socket = new Socket(destAddress, 8002);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            String jsonMail = new Gson().toJson(mail);
            outputStream.writeObject(jsonMail);
            outputStream.flush();

            socket.close();
        }
    }

    public static synchronized void addUser(String username, String address) {
        clients.put(username, address);
        log("Client " + username + " connesso da " + address);
    }

    public static synchronized void removeUser(String username) {
        clients.put(username, null);
        log("Client " + username + " disconnesso.");
    }

    public static synchronized String getAddressForUser(String username) {
        return clients.get(username);
    }

    private static void log(String newLine) {
        if (newLine == null)
            newLine = "ALLERT: String is NULL";

        String currentText = textAreaProperty.getValue();
        if (currentText == null)
            textAreaProperty.set(newLine);
        else
            textAreaProperty.set(currentText + "\n" + newLine);
        System.out.println(newLine);
    }

}
