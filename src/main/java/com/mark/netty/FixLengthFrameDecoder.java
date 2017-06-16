package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class FixLengthFrameDecoder extends ByteToMessageDecoder {

    private int length;

    public FixLengthFrameDecoder(int length) {
        this.length = length;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= length) {
            out.add(in.readBytes(length));
        }
    }
}
