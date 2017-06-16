package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {

    private int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final int size = in.readableBytes();
        if (size > maxFrameSize) {
            in.clear();
            throw new TooLongFrameException();
        }
        out.add(in.readBytes(size));
    }
}
