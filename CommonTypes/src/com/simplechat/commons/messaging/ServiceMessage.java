package com.simplechat.commons.messaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceMessage extends BaseMessage {

    public ServiceMessage(MessageType messageType, String messageBody) {
        super(messageType, messageBody);
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss ");

        return String.format("%s %s: %s",
                simpleDateFormat.format(new Date(getCreationTime())),
                messageBody
        );
    }
}
