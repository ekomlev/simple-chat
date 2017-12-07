package com.simplechat.commons.messaging;

public class IntroduceMessage extends BaseMessage{
    private static final long serialVersionUID = -8621292673149327534L;
    private String senderName;

    public IntroduceMessage(String senderName) {
        this();
        this.senderName = senderName;
    }

    private IntroduceMessage() {
        super(MessageType.INTRODUCE_MESSAGE, "");
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
