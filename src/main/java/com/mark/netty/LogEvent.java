package com.mark.netty;

import java.net.InetSocketAddress;

/**
 * Author: Mark
 * Date  : 2017/6/21
 */
public class LogEvent {

    public static final byte SEPARATOR = ':';
    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;

    public LogEvent(String logFile, String msg) {
        this(null, logFile, msg, 0);
    }

    public LogEvent(InetSocketAddress source, String logFile, String msg, long received) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceivedTimestamp() {
        return received;
    }
}
