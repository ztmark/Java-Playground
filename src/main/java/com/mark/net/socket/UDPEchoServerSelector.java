package com.mark.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Author: Mark
 * Date  : 2015/4/5
 * Time  : 16:09
 */
public class UDPEchoServerSelector {

    private static final int TIMEOUT = 3000; // Wait timeout (milliseconds)
    private static final int ECHOMAX = 255; // Maximum isze of echo datagram

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        int serverPort = Integer.parseInt(args[0]);

        // Create a selector to multiplex client connections
        Selector selector = Selector.open();

        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(8680));
        channel.register(selector, SelectionKey.OP_READ, new ClientRecord());

        while (true) {
            if (selector.select(TIMEOUT) == 0) {
                System.out.print(".");
                continue;
            }

            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                if (key.isReadable()) {
                    handleRead(key);
                }

                if (key.isValid() && key.isWritable()) {
                    handleWrite(key);
                }

                keyIter.remove();
            }
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientRecord record = (ClientRecord) key.attachment();
        record.buffer.flip();
        int bytesSent = channel.send(record.buffer, record.clientAddress);
        if (bytesSent != 0) {
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    private static void handleRead(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientRecord record = (ClientRecord) key.attachment();
        record.buffer.clear();
        record.clientAddress = channel.receive(record.buffer);
        if (record.clientAddress != null) {
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private static class ClientRecord {
        public SocketAddress clientAddress;
        public ByteBuffer buffer = ByteBuffer.allocate(ECHOMAX);
    }
}
