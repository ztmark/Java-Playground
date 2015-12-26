package com.mark.net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: Mark
 * Date  : 2015/4/3
 * Time  : 20:52
 */
public class TCPEchoServerPool {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        int echoServerPort = Integer.parseInt(args[0]);
        int threadPoolSize = Integer.parseInt(args[1]);

        // Create a server socket to accept client connection requests
        final ServerSocket serverSocket = new ServerSocket(echoServerPort);

        // Spawn a fixed number of threads to service clients
        for (int i = 0; i < threadPoolSize; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Socket clientSocket = serverSocket.accept();
                            EchoProtocol.handleEchoClient(clientSocket);
                        } catch (IOException e) {
                            System.out.println("Client accept failded " + e.getMessage());
                        }
                    }
                }
            };
            thread.start();
            System.out.println("Created and started Thread = " + thread.getName());
        }
    }

}
