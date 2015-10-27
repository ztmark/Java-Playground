package com.mark.socket;

import java.io.*;

/**
 * Author: Mark
 * Date  : 2015/3/31
 * Time  : 15:58
 */
public class LengthFramer implements Framer {

    private static final int MAXMESSAGELENGTH = 65535;
    private static final int BYTEMASK = 0xff;
    private static final int SHORTMASK = 0xffff;
    private static final int BYTESHIFT = 8;

    private DataInputStream in; // wrapper for data I/O

    public LengthFramer(InputStream in) {
        this.in = new DataInputStream(in);
    }

    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        if (message.length > MAXMESSAGELENGTH) {
            throw new IOException("message too long");
        }
        // write length prefix
        out.write((message.length >> BYTESHIFT) & BYTEMASK);
        out.write(message.length & BYTEMASK);
        //write message
        out.write(message);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        int length;
        try {
            length = in.readUnsignedShort(); // read 2 bytes
        } catch (EOFException e) { // no (or 1 byte) message
            return null;
        }
        // 0 <= length <= 65535
        byte[] msg = new byte[length];
        in.readFully(msg); // if exception, it's a framing error
        return msg;
    }
}
