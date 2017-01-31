package com.mark.nio;

import sun.jvm.hotspot.oops.ByteField;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 2017/1/29
 */
public class BufferDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 'H');
        buffer.put((byte) 'e');
        buffer.put((byte) 'l');
        buffer.put((byte) 'l');
        buffer.put((byte) 'o');
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        byte[] bytes = new byte[10];
        final ByteBuffer wrap = ByteBuffer.wrap(bytes);
        wrap.put((byte) 'h');
        wrap.put((byte) 'e');
        wrap.put((byte) 'l');
        wrap.put((byte) 'l');
        System.out.println(wrap.arrayOffset());
        final ByteBuffer wrap1 = ByteBuffer.wrap(bytes, 3, 5);
        System.out.println(wrap1.arrayOffset());
    }

}
