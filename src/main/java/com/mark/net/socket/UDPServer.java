package com.mark.net.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Mark
 * Date  : 2017/5/1
 */
public abstract class UDPServer implements Runnable {

    private final int bufferSize;
    private final int port;
    private final Logger logger = Logger.getLogger(UDPServer.class.getCanonicalName());
    private volatile boolean isShutDown = false;

    public UDPServer(int bufferSize, int port) {
        this.bufferSize = bufferSize;
        this.port = port;
    }

    public UDPServer(int port) {
        this(8192, port);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[bufferSize];
        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setSoTimeout(10000);
            while (true) {
                if (isShutDown) {
                    return;
                }
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(incoming);
                    this.response(socket, incoming);
                } catch (SocketTimeoutException e) {
                    if (isShutDown) {
                        return;
                    }
                } catch (IOException e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
                }
            }
        } catch (SocketException e) {
            logger.log(Level.SEVERE, "Could not bind to port: " + port, e);
        }
    }

    protected abstract void response(DatagramSocket socket, DatagramPacket incoming) throws IOException;

    public void shutDown() {
        this.isShutDown = true;
    }
}
