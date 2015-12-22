package com.mark.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 15/12/22.
 */
public class LoadWebPageUseSelector {

    public void load(Set<URL> urls) throws IOException {
        Map<SocketAddress, String> mapping = urlToSocketAddress(urls);
        Selector selector = Selector.open();
        for (SocketAddress socketAddress : mapping.keySet()) {
            register(selector, socketAddress);
        }
        int finished = 0;
        int total = mapping.size();
        ByteBuffer bb = ByteBuffer.allocate(10240);
        int len = -1;
        while (finished < total) {
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isValid() && key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    InetSocketAddress address = (InetSocketAddress) channel.getRemoteAddress();
                    FileChannel fileChannel = FileChannel.open(Paths.get(address.getHostName() + ".html"), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    bb.clear();
                    while ((len = channel.read(bb)) > 0 || bb.position() != 0) {
                        bb.flip();
                        fileChannel.write(bb);
                        bb.compact();
                    }
                    if (len == -1) {
                        finished++;
                        key.cancel();
                    }
                } else if (key.isValid() && key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    boolean success = channel.finishConnect();
                    if (!success) {
                        finished++;
                        key.cancel();
                    } else {
                        InetSocketAddress address = (InetSocketAddress) channel.getRemoteAddress();
                        String path = mapping.get(address);
                        String request = "GET " + path + " HTTP/1.1\r\n\r\nHost:" + address.getHostString() + "\r\n";
                        ByteBuffer header = ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8));
                        channel.write(header);
                    }
                }
            }
        }
    }

    private void register(Selector selector, SocketAddress socketAddress) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(socketAddress);
        channel.register(selector, SelectionKey.OP_CONNECT|SelectionKey.OP_READ);
    }

    private Map<SocketAddress, String> urlToSocketAddress(Set<URL> urls) {
        Map<SocketAddress, String> map = new HashMap<>();
        for (URL url : urls) {
            int port = url.getPort() != -1 ? url.getPort() : url.getDefaultPort();
            SocketAddress address = new InetSocketAddress(url.getHost(), port);
            String path = url.getPath();
            if (url.getQuery() != null) {
                path = path + "?" + url.getQuery();
            }
            map.put(address, path);
        }
        return map;
    }

}
