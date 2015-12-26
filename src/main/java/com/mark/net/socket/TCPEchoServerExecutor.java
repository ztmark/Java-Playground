package com.mark.net.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: Mark
 * Date  : 2015/4/3
 * Time  : 21:05
 */
public class TCPEchoServerExecutor {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        int echoServerPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket serverSocket = new ServerSocket(echoServerPort);

        Executor service = Executors.newCachedThreadPool(); // Dispatch svc

        while (true) {
            Socket clientSocket = serverSocket.accept();
            service.execute(new EchoProtocol(clientSocket));
        }
    }

}
