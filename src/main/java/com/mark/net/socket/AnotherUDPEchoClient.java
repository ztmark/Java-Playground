package com.mark.net.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Author: Mark
 * Date  : 2017/5/1
 */
public class AnotherUDPEchoClient {

    public final static int PORT = 8680;

    public static void main(String[] args) {
        String host = "localhost";
        try {
            InetAddress ia = InetAddress.getByName(host);
            DatagramSocket socket = new DatagramSocket();
            SenderThread sender = new SenderThread(socket, ia, PORT);
            sender.start();
            Thread receiver = new ReceiverThread(socket);
            receiver.start();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

    }

}


class SenderThread extends Thread {
    private InetAddress server;
    private DatagramSocket socket;
    private int port;
    private volatile boolean stopped = false;

    SenderThread(DatagramSocket socket, InetAddress address, int port) {
        this.server = address;
        this.port = port;
        this.socket = socket;
        this.socket.connect(server, port);
    }

    public void halt() {
        this.stopped = true;
    }

    @Override
    public void run() {
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (stopped) {
                    return;
                }
                String theLine = userInput.readLine();
                if (theLine.equals(".")) {
                    break;
                }
                byte[] data = theLine.getBytes(StandardCharsets.UTF_8);
                DatagramPacket output = new DatagramPacket(data, data.length, server, port);
                socket.send(output);
                Thread.yield();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class ReceiverThread extends Thread {
    private DatagramSocket socket;
    private volatile boolean stopped = false;

    ReceiverThread(DatagramSocket socket) {
        this.socket = socket;
    }

    public void halt() {
        this.stopped = true;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[65507];
        while (true) {
            if (stopped) {
                return;
            }
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(dp);
                String s = new String(dp.getData(), 0, dp.getLength(), StandardCharsets.UTF_8);
                System.out.println(s);
                Thread.yield();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}