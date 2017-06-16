package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class FrameChunkDecoderTest {


    @Test
    public void testDecode() {
        final ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }

        final ByteBuf input = buffer.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
        assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            channel.writeInbound(input.readBytes(4));
            fail();
        } catch (TooLongFrameException ignored) {
        }

        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buffer.readSlice(2), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buffer.skipBytes(4).readSlice(3), read);
        read.release();

        buffer.release();
    }

}