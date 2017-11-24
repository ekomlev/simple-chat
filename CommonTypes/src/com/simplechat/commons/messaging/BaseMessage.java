package com.simplechat.commons.messaging;

import java.io.Serializable;

public abstract class BaseMessage implements Serializable {
    private static final long serialVersionUID = 5565915059799107227L;
    private MessageType messageType;
    protected long creationTime;
    protected String messageBody;
    protected String userName;

    private BaseMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public BaseMessage(MessageType messageType, String messageBody) {
        this(messageType);
        this.creationTime = System.currentTimeMillis();
        this.messageBody = messageBody;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
