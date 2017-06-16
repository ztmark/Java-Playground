package com.mark.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Author: Mark
 * Date  : 2017/6/16
 */
public class AbsIntegerEncoderTest {



    @Test
    public void testEncode() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));

        assertTrue(channel.finish());
        for (int i = 1; i < 10; i++) {
            assertTrue(Objects.equals(channel.readOutbound(), i));
        }
        assertNull(channel.readOutbound());
    }

}