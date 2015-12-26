package com.mark.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

/**
 * Author: Mark
 * Date  : 15/12/26.
 */
public class URLDemo {


    public static void main(String[] args) throws IOException {
//        demo1();
//        demo2();
        demo3();
    }

    private static void demo3() throws IOException {
        URL url = new URL("http://www.baidu.com");

        String rawData = "q=java";
        String encodedData = URLEncoder.encode(rawData, StandardCharsets.UTF_8.displayName());
        String contentType = "application/x-www-form-urlencoded";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
        connection.setDoOutput(true);
        connection.getOutputStream().write(encodedData.getBytes(StandardCharsets.UTF_8));

        int response = connection.getResponseCode();
        if (response == HttpURLConnection.HTTP_MOVED_PERM || response == HttpURLConnection.HTTP_MOVED_TEMP) {
            System.out.println("Moved to :" + connection.getHeaderField("Location"));
        } else {
            try (InputStream input = connection.getInputStream()) {
                Files.copy(input, Paths.get("result.html"), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void demo2() throws IOException {
        URL url = new URL("http://www.baidu.com");
        URLConnection connection = url.openConnection();
        String type = connection.getContentType();
        System.out.println(type);
        String encoding = connection.getContentEncoding();
        System.out.println(encoding);
        int length = connection.getContentLength();
        System.out.println(length);
        Date lastModified = new Date(connection.getLastModified());
        System.out.println(lastModified);
        InputStream input = connection.getInputStream();
        Files.copy(input, Paths.get("baidu.html"));
    }

    private static void demo1() throws MalformedURLException {
        URL url = new URL("http://www.baidu.com");
        try (InputStream input = url.openStream()) {
            Files.copy(input, Paths.get("baidu.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
