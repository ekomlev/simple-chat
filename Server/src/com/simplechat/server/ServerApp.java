package com.simplechat.server;


import com.simplechat.server.threading.Server;

import java.io.IOException;
import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) {
        ServerApp serverApp = new ServerApp();
        System.out.println("Server is ready to start...");
        serverApp.runTheApp(serverApp.readUserInput());
    }

    private void runTheApp(String portStringValue) {
        int port = Integer.parseInt(portStringValue);
        if (port > 1024 && port < 65536) {
            startServer(port);
        }
        else {
            System.out.println("The value of Port should be between 1025 and 65535");
        }
    }

    private void startServer(int port) {
        try {
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readUserInput () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Port from 1025 to 65535: ");
        return scanner.next();

    }
}
