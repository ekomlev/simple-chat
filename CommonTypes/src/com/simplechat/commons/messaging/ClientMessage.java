package com.simplechat.commons.messaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientMessage extends BaseMessage {

    private static final long serialVersionUID = 100031937726436971L;
    private String senderId;
    private String senderName;
    private String recipientId;

    private ClientMessage(String messageBody) {
        super(MessageType.CLIENT_MESSAGE, messageBody);
    }

    public ClientMessage(String messageBody, String recipientId) {
        this(messageBody);
        this.recipientId = recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss ");

        return String.format("%s %s: %s" + "\n",
                simpleDateFormat.format(new Date(getCreationTime())),
                userName,
                messageBody
        );
    }
}
