package com.mark.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

/**
 * Author: Mark
 * Date  : 2017/2/13
 */
public class IOTest1 {

    public static void main(String[] args) throws IOException {
//                demo1();
//        demo2();
//        demo3();

//        calcSHA256();

        Thread t1 = new Thread(() -> {
            System.out.println("start");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("done");
        });
        t1.start();
        System.out.println("Main done");

    }

    private static void calcSHA256() throws IOException {
        try {
            InputStream in = new FileInputStream("pom.xml");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            in = new DigestInputStream(in, sha);
            int b;
            while ((b = in.read()) != -1);
            in.close();
            final byte[] digest = sha.digest();
            System.out.println(DatatypeConverter.printHexBinary(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
