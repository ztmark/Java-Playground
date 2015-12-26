package com.mark.net.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Author: Mark
 * Date  : 2015/4/5
 * Time  : 14:39
 */
public class EchoSelectorProtocol implements TCPProtocol {

    private int bufSize; // Size of I/O buffer

    public EchoSelectorProtocol(int bufsize) {
        this.bufSize = bufsize;
    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
        clientChannel.configureBlocking(false); // Must be nonblocking to register
        // Register the selector with new channel for read and attach byte buffer
        clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        // Client socket channel has pending data
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = clientChannel.read(buf);
        if (bytesRead == -1) {
            clientChannel.close();
        } else if (bytesRead > 0){
            // Indicate via key that reading/writing are both of interest now
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        /*
         * Channel is available for writing, and key is valid (i.e. client channel not closed)
         */
        // Retrieve data read earlier
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip(); // prepared buffer for writing
        SocketChannel clientChannel = (SocketChannel) key.channel();
        clientChannel.write(buf);
        if (!buf.hasRemaining()) {
            // Nothing left, so no longer interested in writes
            key.interestOps(SelectionKey.OP_READ);
        }
        buf.compact(); // Make room for more data to be read on
    }

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort((short) 1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 1);
        buffer.flip();
        buffer.mark();
        short s = buffer.getShort();
        short s1 = buffer.getShort();
        System.out.println(s);
        System.out.println(s1);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.reset();
        s = buffer.getShort();
        s1 = buffer.getShort();
        System.out.println(s);
        System.out.println(s1);
    }
}
