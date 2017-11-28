package com.simplechat.server.threading;

import com.simplechat.commons.messaging.BaseMessage;
import com.simplechat.commons.interfaces.IMessageSender;
import com.simplechat.commons.interfaces.MessageDispatcher;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread implements IMessageSender {
    private boolean isRunning;
    private MessageDispatcher listener;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket socket;
    private String clientName;
    private String clientId;

    public ClientThread(MessageDispatcher listener, Socket socket) throws IOException, Exception {
        if (socket == null) {
            throw new Exception("socket is null");
        }
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
        this.listener = listener;
        this.clientId = String.valueOf(System.currentTimeMillis());
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientThread(Socket socket) throws IOException, Exception {
        if (socket == null) {
            throw new Exception("socket is null");
        }
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
        this.clientId = String.valueOf(System.currentTimeMillis());
    }

    @Override
    public void sendMessage(BaseMessage message) throws IOException {
        if (os != null){
            synchronized (os) {
                os.writeObject(message);
            }
        }
    }

    @Override
    public void setMessageEventListener(MessageDispatcher listener) {
        this.listener = listener;
    }

    @Override
    public void finish() {
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning){
            try {

                //NOTE: deserialization of input message from appropriate client thread of ClientThread class
                BaseMessage message = (BaseMessage) is.readObject();
                if (listener != null){
                    listener.onMessageReceived(message, this);
                }
                Thread.sleep(10);
            } catch (SocketException | EOFException | InterruptedException e) {
                e.printStackTrace();
                if (listener != null)
                    listener.killMessageSender(this);
                else {
                    isRunning = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
