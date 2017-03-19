package com.mark.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 15/12/22.
 */
public class ChannelDemo {

    public static void main(String[] args) throws IOException {
        /*FileChannel channel = FileChannel.open(Paths.get(".gitignore"), StandardOpenOption.READ);
        ByteBuffer bb = ByteBuffer.allocate(2048);
        channel.read(bb);
        bb.flip();
        String s = new String(bb.array(), StandardCharsets.UTF_8);
        System.out.println(s);*/
//        copyUseByteBuffer();
//        copyUserChannelTransfer();
//        loadWebPageUseSocket();
//        startSimpleServer();
//        copyInOut();
//        copyInOut2();

        startNonblockingServer();

    }


    private static void copyInOut() throws IOException {
        WritableByteChannel wchannel = Channels.newChannel(System.out);
        ReadableByteChannel rchannel = Channels.newChannel(System.in);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (rchannel.read(buffer) != -1) {
            buffer.flip();
            wchannel.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            wchannel.write(buffer);
        }
        wchannel.close();
        rchannel.close();
    }

    private static void copyInOut2() throws IOException {
        WritableByteChannel wchannel = Channels.newChannel(System.out);
        ReadableByteChannel rchannel = Channels.newChannel(System.in);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (rchannel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                wchannel.write(buffer);
            }
            buffer.clear();
        }
        wchannel.close();
        rchannel.close();
    }


    private static void loadWebPageUseSocket() throws IOException {
        try (FileChannel dest = FileChannel.open(Paths.get("pages.html"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80))) {
            String request = "GET / HTTP/1.1\r\n\r\nHost:www.baidu.com\r\n\r\n";
            ByteBuffer bb = ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8));
            channel.write(bb);
            dest.transferFrom(channel, 0, Integer.MAX_VALUE);
        }
    }

    private static void startSimpleServer() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("localhost", 8680));
        while (true) {
            try (SocketChannel channel = server.accept()) {
                channel.write(ByteBuffer.wrap("hello there".getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    private static void startNonblockingServer() throws IOException {
        final ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8680));
        server.configureBlocking(false);
        System.out.println("starting server " + server.getLocalAddress());
        String msg = "Hello there this is " + server.getLocalAddress().toString();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        while (true) {
            try (SocketChannel channel = server.accept()) {
                if (channel != null) {
                    buffer.rewind();
                    channel.write(buffer);
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void copyUseByteBuffer() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(10240);
        try (FileChannel src = FileChannel.open(Paths.get(".gitignore"), StandardOpenOption.READ);
            FileChannel dest = FileChannel.open(Paths.get("testgitignore"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)){
            while (src.read(bb) > 0 || bb.position() != 0) {
                bb.flip();
                dest.write(bb);
                bb.compact();
            }

        }
    }

    public static void copyUserChannelTransfer() throws IOException {
        try (FileChannel src = FileChannel.open(Paths.get(".gitignore"), StandardOpenOption.READ);
             FileChannel dest = FileChannel.open(Paths.get("testgitignore"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            src.transferTo(0, src.size(), dest);
        }
    }

}
