package com.simplechat.server.threading;


import com.simplechat.commons.messaging.MessageDispatcherImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private boolean isRunning;
    private ServerSocket serverSocket;
    private MessageDispatcherImpl messageDispatcher;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        messageDispatcher = new MessageDispatcherImpl<ClientThread>();
    }

    @Override
    public void run() {
        System.out.println("Server started at " + serverSocket.getLocalPort());
        isRunning = true;
        while (isRunning){
            try {
                if (serverSocket != null) {
                    Socket socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket);
                    messageDispatcher.registerClientThread(clientThread, true);
                    System.out.println("New connection received");
                }
                Thread.sleep(10);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
