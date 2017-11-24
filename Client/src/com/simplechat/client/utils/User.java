package com.simplechat.client.utils;

public class User {
    private String userName;
    private String hostIp;
    private int port;

    public User(String userName, String hostIp, int port) {
        this.userName = userName;
        this.hostIp = hostIp;
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
