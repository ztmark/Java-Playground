package com.mark.socket;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: Mark
 * Date  : 2015/3/31
 * Time  : 15:40
 */
public interface Framer {
    void frameMsg(byte[] message, OutputStream out) throws IOException;
    byte[] nextMsg() throws IOException;
}
