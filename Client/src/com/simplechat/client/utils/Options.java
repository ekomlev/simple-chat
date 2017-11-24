package com.simplechat.client.utils;

import com.simplechat.client.threading.ClientThread;

public class Options {
    private User user = null;
    private ClientThread clientThread;
    private static volatile Options instance = null; //volatile keyword is used to mark a Java variable as "being stored in main memory".

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

    public static Options getInstance() { //Double-Checked Locking. При первом обращении к методу несколько потоков могут пройти первую проверку на null, но во второй блок зайдет и инициализирует объект только один единственный поток! И синхронизация здесь может понадобиться лишь при первом обращении к методу.
        if (instance == null) {
            synchronized (Options.class) {// not null object of type Class<Options> clazz
                if (instance == null) {
                    instance = new Options();
                }
            }
        }

        return instance;
    }
}
