package com.mark.nio;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Author: Mark
 * Date  : 15/11/13.
 */
public class BigFile {

    public static void main(String[] args) {
//        File f = new File("src/main/resources/big.txt");
//        System.out.println(f.length());
//        System.out.println(f.getAbsolutePath());
        String path = "src/main/resources/big.txt";
        File file = new File(path);
        System.out.println(file.length() / 1024.0 + " KB");
        readByFileInputStream(path);
        readByBufferedInputStream(path);
        readByMappedByteBuffer(path);
    }

    public static void readByFileInputStream(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            int sum = 0;
            int n;
            long start = System.nanoTime();
            long ss = System.currentTimeMillis();
            while ((n = fis.read()) != -1) {
                sum += n;
            }
            long duration = System.nanoTime() - start;
            long dd = System.currentTimeMillis() - ss;
            System.out.println("sum = " + sum + " takes " + duration + " nanoseconds" + "  or " + dd + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readByBufferedInputStream(String file) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int sum = 0;
            int n;
            long start = System.nanoTime();
            long ss = System.currentTimeMillis();
            while ((n = bis.read()) != -1) {
                sum += n;
            }
            long duration = System.nanoTime() - start;
            long dd = System.currentTimeMillis() - ss;
            System.out.println("sum = " + sum + " takes " + duration + " nanoseconds" + "  or " + dd + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readByMappedByteBuffer(String file) {
        try {
            File f = new File(file);
            long length = f.length();
            MappedByteBuffer mbb = new RandomAccessFile(f, "rw").getChannel()
                    .map(FileChannel.MapMode.READ_WRITE, 0, length);
            int sum = 0;
            int n;
            long start = System.nanoTime();
            long ss = System.currentTimeMillis();
            for (int i = 0; i < length; i++) {
                n = mbb.get(i) & 0xff;
                sum += n;
            }
            long duration = System.nanoTime() - start;
            long dd = System.currentTimeMillis() - ss;
            System.out.println("sum = " + sum + " takes " + duration + " nanoseconds" + "  or " + dd + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
