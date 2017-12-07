package com.simplechat.commons.messaging;

import com.simplechat.commons.utils.OnlineUser;

import java.util.List;

public class OnlineUsersMessage extends BaseMessage {

    private static final long serialVersionUID = -3219780409213205236L;
    private List<OnlineUser> users;


    public OnlineUsersMessage() {
        super(MessageType.ONLINE_USERS_LIST_MESSAGE, "");
    }

    public OnlineUsersMessage(List<OnlineUser> users) {
        this();
        this.users = users;
    }

    public List<OnlineUser> getUsers() {
        return users;
    }

    public void setUsers(List<OnlineUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return messageBody;
    }
}
