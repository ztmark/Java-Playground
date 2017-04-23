package com.mark.net.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.base.Strings;

/**
 * Author: Mark
 * Date  : 2017/4/23
 */
public class Redirector {

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public void start() {
        System.out.println("server starting");
        try (ServerSocket server = new ServerSocket(8680)) {
            System.out.println("server started");
            while (true) {
                final Socket connection = server.accept();
                pool.submit(new RedirectorHandler(connection));
            }
        } catch (IOException e) {
            System.out.println("error to start server");
        }
    }

    private class RedirectorHandler implements Runnable {

        private Socket connection;

        public RedirectorHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                final String line = reader.readLine();
                if (Strings.isNullOrEmpty(line)) {
                    return;
                }
                if (line.contains("HTTP/1")) {
                    final String[] split = line.split("\\s+");
                    if (split.length != 3) {
                        return;
                    }
                    String url = split[1];
                    writer.write("HTTP/1.1 302 FOUND\r\n");
                    writer.write("Date: " + new Date() + "\r\n");
                    writer.write("Location: http://www.v2ex.com" + url + "\r\n");
                    writer.write("Server: Redirector\r\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        Redirector redirector = new Redirector();
        redirector.start();
    }

}
