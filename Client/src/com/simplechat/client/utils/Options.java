package com.simplechat.client.utils;

import com.simplechat.client.threading.ClientThread;

public class Options {
    private User user = null;
    private ClientThread clientThread;

    //NOTE: volatile keyword is used to mark a Java variable as "being stored in main memory"
    private static volatile Options instance = null;

    public Options() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }


    public static Options getInstance() {

        //NOTE: Double-Checked Locking. At first calling method a few threads can pass the first condition (is null)
        if (instance == null) {

            //NOTE: not null object of type Class<Options> clazz
            synchronized (Options.class) {

                /*NOTE: At second time only one single thread come in block {} and initialize object Options
                 Synchronization may be needed  only at first calling the method*/
                if (instance == null) {
                    instance = new Options();
                }
            }
        }

        return instance;
    }
}
