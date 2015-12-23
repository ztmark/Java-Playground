package com.mark.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * Author: Mark
 * Date  : 15/12/23.
 */
public class NIO2Demo {

    public static void main(String[] args) throws IOException {
//        pathDemo1();
//        pathDemo2();
//        directoryStreamDemo();

//        listAllJavaFile();

//        fileAttribute();

    }

    private static void fileAttribute() throws IOException {
        Path path = Paths.get("pom.xml");
        PosixFileAttributeView fav = Files.getFileAttributeView(path, PosixFileAttributeView.class);
        PosixFileAttributes attributes = fav.readAttributes();
        System.out.println(attributes.group());
        System.out.println(attributes.owner());
        System.out.println(attributes.permissions());
        System.out.println(attributes.creationTime());
        System.out.println(attributes.isRegularFile());
        System.out.println(attributes.lastAccessTime());
        System.out.println(attributes.lastModifiedTime());
        System.out.println(attributes.size());
    }

    private static void listAllJavaFile() throws IOException {
        Path start = Paths.get("");
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(".java")) {
                    System.out.println(file.toAbsolutePath());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // skip .git and .idea dir
                if (".git".equals(dir.getFileName().toString()) || ".idea".equals(dir.getFileName().toString())) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    private static void directoryStreamDemo() throws IOException {
        Path path = Paths.get("");
        DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*.java"); //
        for (Path d : ds) {
            System.out.println(d);
        }
    }

    private static void pathDemo2() {
        Path path = Paths.get("src/main/java/", "com/mark/nio");
        System.out.println(path); // src/main/java/com/mark/nio

        System.out.println(path.subpath(0, 3)); // src/main/java

        System.out.println(path.resolve("test")); // src/main/java/com/mark/nio/test
        System.out.println(path.resolveSibling("io")); // src/main/java/com/mark/io


        Path other = Paths.get("src/main/resources");
        System.out.println(other.toAbsolutePath());  // /Users/Mark/Project/idea/javademo/src/main/resources
        path = path.relativize(other);
        System.out.println(path); // ../../../../resources
        System.out.println(path.toAbsolutePath()); // /Users/Mark/Project/idea/javademo/../../../../resources
        System.out.println(path.toAbsolutePath().normalize()); // /Users/resources
    }

    private static void pathDemo1() throws IOException {
        Path path = Paths.get("/Users/Mark/Project", "Base.java");
        System.out.println(path); // /Users/Mark/Project/Base.java
        System.out.println(path.toAbsolutePath()); // /Users/Mark/Project/Base.java
        System.out.println(path.toRealPath()); // /Users/Mark/Project/Base.java
        System.out.println(path.getFileName()); // Base.java
        System.out.println(path.getNameCount()); // 4
        System.out.println(path.getRoot()); // /
        System.out.println(path.getParent()); // /Users/Mark/Project
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println(path.getName(i)); // Users, Mark, Project, Base.java
        }
    }

}
