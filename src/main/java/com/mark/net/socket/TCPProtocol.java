package com.mark.net.socket;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Author: Mark
 * Date  : 2015/4/5
 * Time  : 14:21
 */
public interface TCPProtocol {

    void handleAccept(SelectionKey key) throws IOException;

    void handleRead(SelectionKey key) throws IOException;

    void handleWrite(SelectionKey key) throws IOException;

}
