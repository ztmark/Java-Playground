package com.mark.learnguava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * Author: Mark
 * Date  : 15/12/4.
 */
public class FilesDemo {

    public static void main(String[] args) {
//        jdk();
        ImmutableList<File> files = Files.fileTreeTraverser().breadthFirstTraversal(new File("."))
                .filter(input -> input.isFile() && input.getName().endsWith(".java"))
//                .<File>transform(input -> {
//                    System.out.println(input.getName());
//                    return input;
//                })
                .toList();
        System.out.println(files.size());
    }

    private static void jdk() {
        List<File> files = Lists.newArrayList();
        try {

            java.nio.file.Files.walkFileTree(Paths.get("."), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    File f = file.toFile();
                    if (f.isFile() && f.getName().endsWith(".java")) {
                        files.add(f);
                    }
                    return super.visitFile(file, attrs);
                }
            });
            System.out.println(files.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
