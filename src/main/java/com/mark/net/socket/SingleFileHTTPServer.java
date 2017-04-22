package com.mark.net.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Mark
 * Date  : 2017/4/22
 */
public class SingleFileHTTPServer {

    private final byte[] content;
    private final byte[] header;
    private final int port = 8680;
    private final String encoding = StandardCharsets.UTF_8.toString();

    public SingleFileHTTPServer(byte[] data, String mimeType) throws UnsupportedEncodingException {
        content = data;
        String hdr = "HTTP/1.1 200 OK\r\n" +
                "Server: OneFile 2.0\r\n" +
                "Content-length: " + this.content.length + "\r\n" +
                "Content-Type: " + mimeType + "; charset=" + encoding + "\r\n";
        this.header = hdr.getBytes(encoding);
    }

    private void start(){
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Acceting connections on port " + server.getLocalPort());
            while (true) {
                try {
                    final Socket connection = server.accept();
                    pool.submit(new HTTPHandler(connection));
                } catch (Exception e) {
                    System.out.println("Error occur " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception occur" + e);
        }
    }

    private class HTTPHandler implements Callable<Void> {

        private Socket connection;

        public HTTPHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public Void call() throws Exception {
            try {
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                final InputStream in = connection.getInputStream();
                StringBuilder request = new StringBuilder(100);
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) {
                        break;
                    }
                    request.append((char) c);
                }
                if (request.toString().contains("HTTP/")) {
                    out.write(header);
                }
                out.write(content);
                out.flush();
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                connection.close();
            }
            return null;
        }
    }


    public static void main(String[] args) {
        try {
            final Path path = Paths.get("pom.xml");
            System.out.println(path);
            final List<String> strings = Files.readAllLines(path);
            final String string = String.join("\r\n", strings);
            System.out.println(string);
            final byte[] data = Files.readAllBytes(path);
            final String contentType = URLConnection.getFileNameMap().getContentTypeFor("pom.xml");
            SingleFileHTTPServer server = new SingleFileHTTPServer(data, contentType);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
