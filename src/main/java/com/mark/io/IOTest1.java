package com.mark.io;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Author: Mark
 * Date  : 2017/2/13
 */
public class IOTest1 {

    public static void main(String[] args) throws IOException {
                demo1();
//        demo2();
//        demo3();
    }

    private static void demo3() {
        final File[] roots = File.listRoots();
        for (File root : roots) {
            System.out.println(root);
            System.out.println("total space " + root.getTotalSpace() / 1024 / 1024 + "M");
            System.out.println("free space " + root.getFreeSpace() / 1024 / 1024 + "M");
            System.out.println("usable space " + root.getUsableSpace() / 1024 / 1024 + "M");
        }
    }

    private static void demo2() throws IOException {
        File file = new File(".gitignore");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getParent());
        System.out.println(file.getName());
        System.out.println(file.isAbsolute());
        System.out.println(file.exists());
        System.out.println(file.isFile());
        System.out.println(file.isHidden());
        System.out.println(file.lastModified());
        System.out.println(new Date(file.lastModified()));
        System.out.println(file.length());
    }

    private static void demo1() {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.io.tmpdir"));

        final String lineSeparator = System.lineSeparator();
        switch (lineSeparator) {
            case "\r\n":
                System.out.println("\\r\\n");
                break;
            case "\r":
                System.out.println("\\r");
                break;
            case "\n":
                System.out.println("\\n");
        }
        System.out.println(File.separator);
        System.out.println(File.separatorChar);
        System.out.println(File.pathSeparator);
        System.out.println(File.pathSeparatorChar);
    }

}
