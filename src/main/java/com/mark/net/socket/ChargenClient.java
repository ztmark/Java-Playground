package com.mark.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Author: Mark
 * Date  : 2017/4/30
 */
public class ChargenClient {

    public static void main(String[] args) {

        int port = 8680;

        try {
            final InetSocketAddress address = new InetSocketAddress("localhost", port);
            final SocketChannel client = SocketChannel.open(address);

            final ByteBuffer buffer = ByteBuffer.allocate(74);
            final WritableByteChannel out = Channels.newChannel(System.out);

            while (client.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
