package com.mark.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author: Mark
 * Date  : 2015/4/5
 * Time  : 14:05
 */
public class TCPEchoClientNonblocking {

    public static void main(String[] args) throws IOException {
        if ((args.length < 2) || (args.length > 3)) {
            throw new IllegalArgumentException();
        }

        String server = args[0];
        // Convert input String to bytes using the default charset
        byte[] argument = args[1].getBytes();

        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 8680;

        // Create channel and set to nonblocking
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);

        // Initiate connection to server and repeatedly poll until complete
        if (!clientChannel.connect(new InetSocketAddress(server, serverPort))) {
            while (!clientChannel.finishConnect()) {
                System.out.print("."); // Do something else
            }
        }
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRcvd = 0; // Total bytes received so far
        int bytesRcvd; // Bytes received in last read
        while (totalBytesRcvd < argument.length) {
            if (writeBuf.hasRemaining()) {
                clientChannel.write(writeBuf);
            }
            if ((bytesRcvd = clientChannel.read(readBuf)) == -1) {
                throw new SocketException("Connection closed prematurely");
            }
            totalBytesRcvd += bytesRcvd;
            System.out.print(".");  // Do something else
        }

        System.out.println("Received: " + new String(readBuf.array(), 0, totalBytesRcvd));
        clientChannel.close();

    }


}
