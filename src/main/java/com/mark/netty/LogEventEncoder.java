package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Author: Mark
 * Date  : 2017/6/21
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remote;

    public LogEventEncoder(InetSocketAddress remote) {
        this.remote = remote;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {
        final byte[] file = logEvent.getLogFile().getBytes(CharsetUtil.UTF_8);
        final byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        final ByteBuf buffer = ctx.alloc().buffer(file.length + msg.length + 1);
        buffer.writeBytes(file);
        buffer.writeByte(LogEvent.SEPARATOR);
        buffer.writeBytes(msg);
        out.add(new DatagramPacket(buffer, remote));
    }
}
