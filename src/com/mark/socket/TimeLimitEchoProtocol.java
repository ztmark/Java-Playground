package com.mark.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Mark
 * Date  : 2015/4/3
 * Time  : 21:49
 */
public class TimeLimitEchoProtocol implements Runnable {

    private static final int BUFSIZE = 32; // Size (bytes) of buffer
    private static final String TIMELIMIT = "10000"; // Default limit (ms)
    private static final String TIMELIMITPROP = "Timelimit"; // Property
    private static int timelimit;
    private Socket clientSocket;

    public TimeLimitEchoProtocol(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void handleEchoClient(Socket clientSocket) {
        try {
            // Get the input and output I/O streams from socket
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            int recvMsgSize;
            int totalBytesEchoed = 0;
            byte[] echoBuffer = new byte[BUFSIZE];
            long endTime = System.currentTimeMillis() + timelimit;
            int timeBoundMillis = timelimit;

            clientSocket.setSoTimeout(timeBoundMillis);

            while ((timeBoundMillis > 0) && ((recvMsgSize = in.read()) != -1)) {
                out.write(echoBuffer, 0, recvMsgSize);
                totalBytesEchoed += recvMsgSize;
                timeBoundMillis = (int) (endTime - System.currentTimeMillis());
                clientSocket.setSoTimeout(timeBoundMillis);
            }
            System.out.println("Client " + clientSocket.getRemoteSocketAddress() + ", echoed" + totalBytesEchoed + " bytes.");

        } catch (IOException e) {
            System.out.println("Exception in echo protocol " + e.getMessage());
        }
    }

    @Override
    public void run() {

    }
}
