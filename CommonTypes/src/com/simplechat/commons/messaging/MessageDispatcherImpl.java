package com.simplechat.commons.messaging;

import com.simplechat.commons.interfaces.IMessageSender;
import com.simplechat.commons.interfaces.MessageDispatcher;
import com.simplechat.commons.utils.OnlineUser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NOTE:
 * allows to work only with those types,
 * that extend Thread class and at the same moment implements IMessageSender interface.
 * This class implements MessageDispatcher interface.
 * @param <T> universal parameter instead of which should input required types that are need during executing
 */
public class MessageDispatcherImpl<T extends Thread & IMessageSender> implements MessageDispatcher<T> { //
    private Map<String, T> clientsMap;

    public MessageDispatcherImpl() {

        //NOTE: ConcurrentHashMap can work with threads
        clientsMap = new ConcurrentHashMap<>();
    }


    @Override
    public void onMessageReceived(BaseMessage message, T clientThread) throws IOException {
        switch (message.getMessageType()) {
            case INTRODUCE_MESSAGE:
                IntroduceMessage im = (IntroduceMessage)message;
                clientThread.setClientName(im.getSenderName());
                introduceClient(clientThread);
                for (T t : clientsMap.values()) {
                    t.sendMessage(message);
                }
                break;
            case CLIENT_MESSAGE:
                message.setUserName(clientThread.getClientName());
                if (((ClientMessage) message).getRecipientId() == null) {
                    for (T t : clientsMap.values()) {
                        t.sendMessage(message);
                    }
                }
                else {
                    if (!((ClientMessage) message).getRecipientId().equals(clientThread.getClientId())) {
                        clientsMap.get(((ClientMessage) message).getRecipientId()).sendMessage(message);
                    }
                    clientThread.sendMessage(message);
                }
        }
    }

    @Override
    public void killMessageSender(T messageSender) {

        //NOTE: remove from map
        clientsMap.remove(messageSender.getClientId());

        new Thread(() -> {
            try {

                //NOTE: sent new map to remaining users
                introduceClient(messageSender);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //NOTE: isRunning = false - this is clientThread on server
        messageSender.finish();
    }

    @Override
    public void registerClientThread(T clientThread, boolean isOnServer) {
        clientThread.setMessageEventListener(this);
        //clients.add(clientThread);
        clientsMap.put(clientThread.getClientId(), clientThread);
        clientThread.start();
        if (isOnServer) {
            new Thread(() -> {
                try {
                    introduceClient(clientThread);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void introduceClient(T client)  throws IOException{
        List<OnlineUser> users = new LinkedList<>();
        OnlineUsersMessage msg = new OnlineUsersMessage();

        //NOTE: going through clientsMap keys
        for (String s : clientsMap.keySet()) {
                OnlineUser usr = new OnlineUser();

                /*NOTE: methods getClientId() & getClientName() are overrode in server/ClientThread class. This class
                implements ImessageSender interface. ImessageSender contains this 2 methods by default.
                Input argument of introduceClient() method is client of ClientThread type, so this two overrode methods
                are called in clientThread*/
                usr.userId = clientsMap.get(s).getClientId();
                usr.userName = clientsMap.get(s).getClientName();

                //NOTE: putting in List usr with userId and userName fields
                users.add(usr);
        }

        //NOTE: sending list of users to all users
        msg.setUsers(users);
        msg.setUserName(client.getClientName());
        for (T t : clientsMap.values()) {

                //NOTE: this method is overrode in server/ClientThread class
                t.sendMessage(msg);
        }
    }
}
