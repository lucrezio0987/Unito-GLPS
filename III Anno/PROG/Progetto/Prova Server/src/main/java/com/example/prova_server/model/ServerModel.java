package com.example.prova_server.model;

import javafx.beans.property.SimpleStringProperty;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModel extends Thread{
    private static SimpleStringProperty textAreaProperty = null;
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private static final Map<String, ObjectOutputStream> clients = new ConcurrentHashMap<>();
    private static final Set<Client> clientsList = new HashSet<>();

    private static Gson gson;

    private int port = 8000;

    public ServerModel() {
        textAreaProperty = new SimpleStringProperty();
        gson = new Gson();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto sulla porta: " + port);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connesso: " + clientSocket.getInetAddress().getHostAddress());

                    executorService.submit(new ClientHandler(clientSocket));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

    public SimpleStringProperty getTextAreaProperty() {
        return textAreaProperty;
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
                try{
                    while (true) {
                        Mail mail = gson.fromJson((String) inputStream.readObject(), Mail.class);
                        clients.put(mail.getSender(), new ObjectOutputStream(clientSocket.getOutputStream()));

                        clientsList.add(new Client(mail.getSender()));

                        System.out.println("Nuovo client connesso: " + mail.getSender());

                        sendMail(mail);

                        outputStream.writeObject("RICEVUTA");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void sendMail(Mail mail) {
        ObjectOutputStream outputStream = clients.get(mail.getSender());
        try {
            if (outputStream != null) {
                outputStream.writeObject(mail);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
