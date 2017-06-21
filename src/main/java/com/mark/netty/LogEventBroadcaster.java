package com.mark.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2017/6/21
 */
public class LogEventBroadcaster {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() throws InterruptedException, IOException {
        final Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        while (true) {
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            } else if (len > pointer) {
                final RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(pointer);
                String line = null;
                while ((line = raf.readLine()) != null) {
                    channel.writeAndFlush(new LogEvent(null, file.getAbsolutePath(), line, -1));
                }
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        final LogEventBroadcaster broadcaster = new LogEventBroadcaster(new InetSocketAddress("255.255.255.255", 8680), new File("logs/app.log"));
        try {
            broadcaster.run();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            broadcaster.stop();
        }

    }
}
