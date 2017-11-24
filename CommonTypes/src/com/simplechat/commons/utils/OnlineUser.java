package com.simplechat.commons.utils;

import java.io.Serializable;

public class OnlineUser implements Serializable {
    private static final long serialVersionUID = -5008259079852052521L;
    public String userName;
    public String userId;

    public OnlineUser(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public OnlineUser() {
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return userName;
    }
}
