package com.mark.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: Mark
 * Date  : 16/5/24
 */
public class WriteBinDate {


    public static void main(String[] args) throws IOException {
        File file = new File("time");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = new FileOutputStream(file);
        long t = System.currentTimeMillis() - 24 * 60 * 60 * 1000;

        out.write(String.valueOf(t).getBytes());
        out.close();
    }

}
