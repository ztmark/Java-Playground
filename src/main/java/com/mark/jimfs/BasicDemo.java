package com.mark.jimfs;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Author: Mark
 * Date  : 15/12/26.
 */
public class BasicDemo {

    public static void main(String[] args) throws IOException {
        FileSystem fs = Jimfs.newFileSystem(Configuration.osX());
        Path foo = fs.getPath("/foo");
        Files.createDirectories(foo);
        Path hello = foo.resolve("hello.txt");
        Files.write(hello, ImmutableList.of("Hello World"), StandardCharsets.UTF_8);
    }

}
