package com.simplechat.client.threading;

import com.simplechat.client.utils.User;
import com.simplechat.commons.messaging.IMessageSender;
import com.simplechat.commons.messaging.BaseMessage;
import com.simplechat.commons.messaging.MessageDispatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread implements IMessageSender {
    private MessageDispatcher listener;
    private boolean isRunning;
    private Socket socket;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public ClientThread(User user) throws IOException {
        socket = new Socket(user.getHostIp(), user.getPort());
        os = new ObjectOutputStream(socket.getOutputStream()); // ?десериализует входящий поток
        is = new ObjectInputStream(socket.getInputStream()); // ?десериализует исходящий поток
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning){
            if (is != null) {
                try {
                    BaseMessage message = (BaseMessage) is.readObject();
                    if (listener != null){
                        listener.onMessageReceived(message);
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                    isRunning = false;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.out.println("couldn't convert to Message");
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isRunning = false;
                }
            } else {
                isRunning = false;
            }
        }
        try {
            if (is != null){
                is.close();
            }
            if (os != null){
                os.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(BaseMessage message) throws IOException {
        if (os != null)
            os.writeObject(message);  //сериализуем отправляемое на сервер сообщение
    }

    @Override
    public void setMessageEventListener(MessageDispatcher listener) {
        this.listener = listener;
    } //в пере

    @Override
    public void finish() {
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = false;
    }
}
