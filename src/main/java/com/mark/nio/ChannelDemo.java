package com.mark.nio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
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
//        loadWebPageUseSocket();
//        startSimpleServer();
//        copyInOut();
//        copyInOut2();

//        startNonblockingServer();


//        generateFile();
//        copyUseByteBuffer();
//        copyUseChannelTransfer();

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
        final long start = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.allocate(10240);
        try (FileChannel src = FileChannel.open(Paths.get("testfile"), StandardOpenOption.READ);
            FileChannel dest = FileChannel.open(Paths.get("testfile_backup_old"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)){
            while (src.read(bb) > 0 || bb.position() != 0) {
                bb.flip();
                dest.write(bb);
                bb.compact();
            }

        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration : " + duration + " millis");
    }

    public static void copyUseChannelTransfer() throws IOException {
        final long start = System.currentTimeMillis();

        try (FileChannel src = FileChannel.open(Paths.get("testfile"), StandardOpenOption.READ);
             FileChannel dest = FileChannel.open(Paths.get("testfile_backup_new"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            src.transferTo(0, src.size(), dest);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration : " + duration + " millis");
    }

    public static void generateFile() throws IOException {
        File file = new File("testfile");

        if (!file.exists()) {
            final boolean newFile = file.createNewFile();
            if (!newFile) {
                System.out.println("make file failed");
                return;
            }
        }

        try (final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))){
            for (int i = 0; i < 10000000; i++) {
                bufferedWriter.write("what are you doing hahahhahahahahahlallalalalal\n");
            }
            bufferedWriter.flush();
            final long length = file.length();
            System.out.println(length + " byte");
            System.out.println(length / 1024 / 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
