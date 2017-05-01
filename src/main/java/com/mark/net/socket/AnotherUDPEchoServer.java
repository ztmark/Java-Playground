package com.mark.net.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Author: Mark
 * Date  : 2017/5/1
 */
public class AnotherUDPEchoServer extends UDPServer{

    private final static int DEFAULT_PORT = 8680;

    public AnotherUDPEchoServer() {
        super(DEFAULT_PORT);
    }

    @Override
    protected void response(DatagramSocket socket, DatagramPacket incoming) throws IOException {
        DatagramPacket outgoing = new DatagramPacket(incoming.getData(), incoming.getLength(), incoming.getAddress(), incoming.getPort());
        socket.send(outgoing);
    }

    public static void main(String[] args) {
        UDPServer server = new AnotherUDPEchoServer();
        Thread t = new Thread(server);
        t.start();
    }
}
