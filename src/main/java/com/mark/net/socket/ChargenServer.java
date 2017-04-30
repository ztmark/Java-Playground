package com.mark.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 2017/4/30
 */
public class ChargenServer {

    public static void main(String[] args) {
        int port = 8680;

        byte[] rotation = new byte[95 * 2];
        for (byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverSocketChannel;
        Selector selector;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Listening for connection on port " + port);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                final SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        final SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        final SelectionKey key1 = client.register(selector, SelectionKey.OP_WRITE);
                        final ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotation, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key1.attach(buffer);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        final ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (!buffer.hasRemaining()) {
                            buffer.rewind();
                            final byte first = buffer.get();
                            buffer.rewind();
                            int position = first - ' ' + 1;
                            buffer.put(rotation, position, 72);
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            buffer.flip();
                        }
                        client.write(buffer);
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ignored) {
                    }
                }
            }

        }
    }

}
