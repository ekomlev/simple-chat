package com.simplechat.commons.messaging;

import com.simplechat.commons.utils.OnlineUser;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;

import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * позволяет работать только с теми типами,
 * которые наследуют класс Thread  и в то же время реализуют интерфейс IMessageSender.
 * В свою очередь данный класс реализует интерфейс MessageDispatcher
 * @param <T> Универсальный параметр вместо которого будут подставляться необходимые типы во время выполнения
 */
public class MessageDispatcherImpl<T extends Thread & IMessageSender> implements MessageDispatcher <T> { //
    /*private List<T> clients;*/
    private Map<String, T> clientsMap;

    public MessageDispatcherImpl() {
        //clients = new LinkedList<>();
        clientsMap = new ConcurrentHashMap<>(); //ConcurrentHashMap умеет работать с потоками
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
        clientsMap.remove(messageSender.getClientId()); //убрали из мапы

        new Thread(() -> {
            try {
                introduceClient(messageSender);  //разослали новый вариант мапы оставшимся
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        messageSender.finish(); // там isRunning = false - это clientThread на сервере
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
        for (String s : clientsMap.keySet()) { //проходим по ключам clientsMap
                OnlineUser usr = new OnlineUser();
                usr.userId = clientsMap.get(s).getClientId(); //методы getClientId() и getClientName() переопределены в server/ClientThread который наследует интерфейс ImessageSender (в нем то и описаны эти два метода по дефолту). Входным параметром метода introduceClient служит client - тип этой ссылки ClientThread, именно поэтому вызываются переопределенные методы getClientId() и getClientName() в clientThread.
                usr.userName = clientsMap.get(s).getClientName();
                users.add(usr); //складываем в List usr с полями userId и userName
        }
        msg.setUsers(users); //рассылаем список пользователей всем пользователям
        msg.setUserName(client.getClientName());
        for (T t : clientsMap.values()) {
                t.sendMessage(msg);  //метод sendMessage переопределен server/ClientThread, который наследует интерфейс ImessageSender (в нем то и описан этот метод по дефолту)
        }
    }
}
