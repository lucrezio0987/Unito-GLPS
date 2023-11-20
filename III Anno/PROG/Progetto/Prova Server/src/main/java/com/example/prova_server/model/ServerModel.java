package com.example.prova_server.model;

import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModel extends Thread{
    private static SimpleStringProperty textAreaProperty = null;
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private int port = 8000;

    public ServerModel() {
        textAreaProperty = new SimpleStringProperty();
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

                Object receivedObject = inputStream.readObject();
                System.out.println("Received object from client: " + receivedObject);
                textAreaProperty.set(textAreaProperty.get() + "Inviata Mail con Oggetto: " + receivedObject + "\n");

                // if (receivedObject instanceof Mail) {
                //     Mail dataObject = (Mail) receivedObject;
                //     System.out.println("Received data: " + dataObject);
                // }

                outputStream.writeObject("RICEVUTA");

            } catch (IOException | ClassNotFoundException e) {
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
}
