package com.mark.nio.okio;

import okio.*;

import java.io.File;
import java.io.IOException;

/**
 * Author: Mark
 * Date  : 16/3/7.
 */
public class OkioDemo {

    public static void main(String[] args) throws IOException {
//        demo1();
//        demo2();
//        demo3();

    }

    private static void demo3() throws IOException {
        BufferedSource source = Okio.buffer(Okio.source(new File("demo.txt")));
        System.out.println(source.readUtf8());
        source.close();
    }

    private static void demo2() throws IOException {
        BufferedSink sink = Okio.buffer(Okio.sink(new File("demo.txt")));
        sink.writeUtf8("hello world");
        sink.close();
    }

    private static void demo1() {
        try {
            Sink sink = Okio.sink(new File("demo.txt"));
            Buffer buffer = new Buffer();
            buffer.write("wwww".getBytes());
            sink.write(buffer, buffer.size());
            buffer.close();
            sink.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
