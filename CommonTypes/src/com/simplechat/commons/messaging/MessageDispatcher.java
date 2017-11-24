package com.simplechat.commons.messaging;

import java.io.IOException;

public interface MessageDispatcher<T extends Thread & IMessageSender> {
    default void onMessageReceived(BaseMessage message) throws IOException{}
    default void onMessageReceived(BaseMessage message, T client) throws IOException{}
    default void killMessageSender(){}
    default void killMessageSender(T messageSender){}
    default void registerClientThread(T clientThread, boolean isOnServer){}
}
