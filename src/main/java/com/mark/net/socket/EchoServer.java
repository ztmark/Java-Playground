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
 * Date  : 2017/4/10
 */
public class EchoServer {

    public void start0() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8680));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Listening in localhost 8086");
        while (true) {
            selector.select();
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                final SelectionKey selectionKey = iterator.next();
                iterator.remove();
                try {
                    if (selectionKey.isAcceptable()) {
                        final ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        final SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        if (client != null) {
                            client.configureBlocking(false);
                            final SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            clientKey.attach(ByteBuffer.allocate(100));
                        }
                    }
                    if (selectionKey.isReadable()) {
                        System.out.println("reading");
                        final SocketChannel client = (SocketChannel) selectionKey.channel();
                        final ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        client.read(buffer);
                    }
                    if (selectionKey.isWritable()) {
                        System.out.println("writing");
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        buffer.flip();
                        client.write(buffer);
                        buffer.compact();
                    }
                } catch (IOException e) {
                    selectionKey.cancel();
                    selectionKey.channel().close();
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new EchoServer().start0();
    }


}
