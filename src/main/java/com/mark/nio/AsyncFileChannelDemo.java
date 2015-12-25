package com.mark.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: Mark
 * Date  : 15/12/25.
 */
public class AsyncFileChannelDemo {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//        writeToFile();
        server();
    }

    private static void server() throws IOException {
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(10, Executors.defaultThreadFactory());
        AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(8680));
        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                channel.accept(null, this);
                try {
                    System.out.println(result.getRemoteAddress());
                    result.write(ByteBuffer.wrap("hi there!".getBytes(StandardCharsets.UTF_8)));
                    result.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                throw new RuntimeException(exc);
            }

        });
    }

    private static void writeToFile() throws IOException, InterruptedException, ExecutionException {
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(Paths.get("demo.txt"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        Future<Integer> future = afc.write(ByteBuffer.wrap("hello world".getBytes()), 0);
        System.out.println(future.get());
    }

}
