package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Author: Mark
 * Date  : 2017/6/21
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        final ByteBuf content = msg.content();
        final int idx = content.indexOf(0, content.readableBytes(), LogEvent.SEPARATOR);
        final ByteBuf file = content.slice(0, idx);
        final ByteBuf data = content.slice(idx + 1, content.readableBytes());
        final LogEvent logEvent = new LogEvent(msg.sender(), file.toString(CharsetUtil.UTF_8), data.toString(CharsetUtil.UTF_8), System.currentTimeMillis());
        out.add(logEvent);
    }
}
