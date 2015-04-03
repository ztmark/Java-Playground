package com.mark.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Stack;

/**
 * Author: Mark
 * Date  : 2015/3/28
 * Time  : 19:51
 */
public class UDPEchoServer {

    private static final int ECHOMAX = 5; // Maximum size of echo datagram

    public static void main(String[] args) throws IOException {
        if (args.length != 1) { // Test for correct argument list
            throw new IllegalArgumentException();
        }

        int servPort = Integer.parseInt(args[0]);

        DatagramSocket socket = new DatagramSocket(servPort);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        Stack<Integer> stack = new Stack<>();
        stack.peek();

        while (true) { // Run forever, receiving and echoing datagrams
            socket.receive(packet); // Receive packet from client
            System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());

            socket.send(packet); // Send the same packet back to client
            packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
        }


    }

}
