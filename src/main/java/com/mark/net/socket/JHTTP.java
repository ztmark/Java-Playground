package com.mark.net.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import scala.concurrent.java8.FuturesConvertersImpl;

/**
 * Author: Mark
 * Date  : 2017/4/23
 */
public class JHTTP {

    private static final Logger logger = Logger.getLogger(JHTTP.class.getCanonicalName());

    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";

    private final File rootDirectory;
    private final int port;

    public JHTTP(File rootDirectory, int port) throws IOException {
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + " does not exist as a directory");
        }
        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connection on port " + server.getLocalPort());
            logger.info("Document Root " + rootDirectory);
            while (true) {
                try {
                    final Socket request = server.accept();
                    Runnable r = new RequestHandler(rootDirectory, INDEX_FILE, request);
                    pool.submit(r);
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Error accepting connection", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        File docRoot;
        String param = "web";
        docRoot = new File(param);
        int port = 8680;
        try {
            JHTTP server = new JHTTP(docRoot, port);
            server.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server could not start", e);
        }
    }


    private static class RequestHandler implements Runnable {

        private final static Logger log = Logger.getLogger(RequestHandler.class.getCanonicalName());

        private File rootDirectory;
        private String indexFileName = "index.html";
        private Socket connection;

        public RequestHandler(File rootDirectory, String indexFile, Socket request) {
            if (rootDirectory.isFile()) {
                throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
            }
            try {
                rootDirectory = rootDirectory.getCanonicalFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.rootDirectory = rootDirectory;
            if (indexFile != null) {
                this.indexFileName = indexFile;
            }
            this.connection = request;
        }

        @Override
        public void run() {
            String root = rootDirectory.getPath();
            try {
                OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
                Writer out = new OutputStreamWriter(raw);
                Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
                StringBuilder requestLine = new StringBuilder();
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n') {
                        break;
                    }
                    requestLine.append((char) c);
                }
                final String line = requestLine.toString();
                log.info(connection.getRemoteSocketAddress() + " " + line);
                final String[] tokens = line.split("\\s+");
                String method = tokens[0];
                String version = "";
                if (method.equals("GET")) {
                    String fileName = tokens[1];
                    if (fileName.endsWith("/")) {
                        fileName += indexFileName;
                    }
                    final String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
                    if (tokens.length > 2) {
                        version = tokens[2];
                    }
                    File theFile = new File(rootDirectory, fileName.substring(1, fileName.length()));
                    if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
                        final byte[] theData = Files.readAllBytes(theFile.toPath());
                        sendHeader(out, "HTTP/1.1 200 OK", contentType, theData.length);
                        raw.write(theData);
                        raw.flush();
                    } else {
                        String header = "HTTP/1.1 404 Not Found\r\n";
                        String body = "404 NOT FOUND";
                        sendHeader(out, header, "text/html; charset=utf-8", body.getBytes().length);
                        out.write(body);
                        out.flush();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendHeader(Writer out, String responseCode, String contentType, int length) throws IOException {
            out.write(responseCode + "\r\n");
            out.write("Date: " + new Date() + "\r\n");
            out.write("Server: JHTTP\r\n");
            out.write("Content-length: " + length + "\r\n");
            out.write("Content-Type: " + contentType + "\r\n");
            out.write("\r\n");
            out.flush();
        }
    }


}
