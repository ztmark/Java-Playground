package com.mark.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Mark
 * Date  : 2015/4/3
 * Time  : 20:32
 */
public class EchoProtocol implements Runnable {

    private static final int BUFSIZE = 32; // Size (int bytes) of I/O buffer
    private Socket clntSocket;            // Socket connect to client

    public EchoProtocol(Socket clntSocket) {
        this.clntSocket = clntSocket;
    }

    public static void handleEchoClient(Socket clntSocket) {
        try {
            // Get the input and output I/O streams from socket
            InputStream in = clntSocket.getInputStream();
            OutputStream out = clntSocket.getOutputStream();

            int recvMsgSize; // Size of received message
            int totalBytesEchoed = 0; // Bytes received from client
            byte[] echoBuffer = new byte[BUFSIZE]; // Received Buffer
            // Receive until client closes connection, indicated by -1
            while ((recvMsgSize = in.read(echoBuffer)) != -1) {
                out.write(echoBuffer, 0, recvMsgSize);
                totalBytesEchoed += recvMsgSize;
            }

            // logger
            System.out.println("Client " + clntSocket.getRemoteSocketAddress() + ", echoed " + totalBytesEchoed + " bytes");
        } catch (IOException e) {
            System.out.println("Exception in echo protocol " + e.getMessage()); // logger
        } finally {
            try {
                clntSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        handleEchoClient(clntSocket);
    }
}
