package com.mark.net.socket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class UDPPoke {

    private int bufferSize;
    private int timeout;
    private InetAddress host;
    private int port;

    public UDPPoke(int bufferSize, int timeout, InetAddress host, int port) {
        this.bufferSize = bufferSize;
        this.timeout = timeout;
        this.host = host;
        this.port = port;
    }

    public UDPPoke(int bufferSize, InetAddress host, int port) {
        this(bufferSize, 30000, host, port);
    }

    public UDPPoke(InetAddress host, int port) {
        this(8192, host, port);
    }

    public byte[] poke() {
        try (DatagramSocket socket = new DatagramSocket(0)) {
            DatagramPacket outgoing = new DatagramPacket(new byte[1], 1, host, port);
            socket.connect(host, port);
            socket.setSoTimeout(timeout);
            socket.send(outgoing);
            DatagramPacket incoming = new DatagramPacket(new byte[bufferSize], bufferSize);
            socket.receive(incoming);
            int numBytes = incoming.getLength();
            byte[] response = new byte[numBytes];
            System.arraycopy(incoming.getData(), 0, response, 0, numBytes);
            return response;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        InetAddress host;
        int port = 0;
        try {
            host = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException | UnknownHostException e) {
            System.out.println("Usage: java UDPPoke host port");
            return;
        }

        UDPPoke poker = new UDPPoke(host, port);
        byte[] response = poker.poke();
        if (response == null) {
            System.out.println("No response within allotted time");
            return;
        }
        String result = new String(response, StandardCharsets.UTF_8);
        System.out.println(result);
    }

}
