package com.mark.misc;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Author: Mark
 * Date  : 2017/6/19
 */
public class CmdTest {


    public static void main(String[] args) throws IOException, URISyntaxException {
//        final Process ls = Runtime.getRuntime().exec("networksetup -setairportnetwork en0 'A&Z' 12345678");
//        BufferedReader input = new BufferedReader(new InputStreamReader(ls.getInputStream()));
//        String line = null;
//
//        try {
//            while ((line = input.readLine()) != null)
//                System.out.println(line);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        for (int j = 1; j <= 10; j++) {
            File file = new File("pwd" + j + ".txt");
            final PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
            for (int i = 10000000 * (j - 1); i < (10000000 * j); i++) {
                printWriter.println(i);
            }
            printWriter.flush();
            printWriter.close();
        }
//        final URL location = CmdTest.class.getProtectionDomain().getCodeSource().getLocation();
//        System.out.println(location.toURI());
//        final URL resource = CmdTest.class.getClassLoader().getResource("");
//        System.out.println(resource.toURI());
    }

}
