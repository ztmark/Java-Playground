package com.mark.net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: Mark
 * Date  : 2015/4/3
 * Time  : 20:40
 */
public class TCPEchoServerThread {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        int echoServPort = Integer.parseInt(args[0]); // Server port

        // Create a server socket to accept client connection requests
        ServerSocket serverSocket = new ServerSocket(echoServPort);

        // Run forever, accepting and spawning a thread for each connection
        while (true) {
            Socket clntSocket = serverSocket.accept(); // Block waiting for connection
            // Spawn thread to handle new connection
            Thread thread = new Thread(new EchoProtocol(clntSocket));
            thread.start();
            System.out.println("Created and started Thread " + thread.getName());  // logger
        }
    }

}
