package com.simplechat.commons.messaging;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = -5474992838024533871L;

    private String userName;
    private String messageBody;
    private long timeStamp;

    public Message(String userName, String messageBody) {
        this.userName = userName;
        this.messageBody = messageBody;
        timeStamp = System.currentTimeMillis();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");

        return String.format("%s %s:\n %s",
                simpleDateFormat.format(new Date(timeStamp)),
                userName,
                messageBody
        );
    }



}
