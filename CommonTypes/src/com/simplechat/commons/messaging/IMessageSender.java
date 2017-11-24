package com.simplechat.commons.messaging;

import java.io.IOException;

public interface IMessageSender {
    void sendMessage(BaseMessage message) throws IOException;
    void setMessageEventListener(MessageDispatcher listener);
    void finish();
    default String getClientName() {
         return null;
     }

    default void setClientName(String clientName) {

    }

    default String getClientId() {
        return null;
    }

    default void setClientId (String clientId) {}
}
