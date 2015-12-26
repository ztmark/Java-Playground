package com.mark.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Author: Mark
 * Date  : 2015/3/23
 * Time  : 20:23
 */
public class TCPEchoServer {

    private static final int BUFSIZE = 32; // Size of receive buffer

    public static void main(String[] args) throws IOException {
        // Test for correct of args
        if (args.length != 1) {
            throw new IllegalArgumentException("Parameter(s): <Port>");
        }

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSocket = new ServerSocket(servPort);

        int recvMsgSize; // Size of received message
        byte[] receiveBuf = new byte[BUFSIZE]; // Receive buffer

        while (true) { // Run forever accepting and servicing connections
            Socket clntSock = servSocket.accept();

            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            System.out.println("Handling client at " + clientAddress);

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            //Receive until client closes connection, indicated by -1 return
            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf, 0, recvMsgSize);
            }
            clntSock.close(); // Close the socket, We are done with this client
        }
    }

}
