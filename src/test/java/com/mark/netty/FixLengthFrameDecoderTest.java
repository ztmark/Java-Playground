package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class FixLengthFrameDecoderTest {


    @Test
    public void testDecode() {
        final ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        final ByteBuf input = buffer.duplicate();

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixLengthFrameDecoder(3));
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());
        ByteBuf read = embeddedChannel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        assertNull(embeddedChannel.readInbound());
        buffer.release();
    }

    @Test
    public void testFramesDecoded() {
        final ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        final ByteBuf input = buffer.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixLengthFrameDecoder(3));
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));

        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buffer.release();
    }

}