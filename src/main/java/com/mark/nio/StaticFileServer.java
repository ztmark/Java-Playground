package com.mark.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mark
 * Date  : 15/12/25.
 */
public class StaticFileServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticFileServer.class);

    private static final Pattern PATH_EXTRACTOR = Pattern.compile("GET (.*?) HTTP");
    private static final String INDEX_PAGE = "index.html";


    public static void main(String[] args) throws IOException {
        new StaticFileServer().start(Paths.get(""));
    }


    public void start(Path root) throws IOException {
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(10, Executors.defaultThreadFactory());
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(8680));
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                server.accept(null, this);
                ByteBuffer buf = ByteBuffer.allocate(20140);
                try {
                    result.read(buf).get();
                    buf.flip();
                    String request = new String(buf.array());
                    String requestPath = extractPath(request);
                    Path file = getFilePath(root, requestPath);
                    if (!Files.exists(file)) {
                        String error404 = generateResponseMessage(404, "Not Found");
                        result.write(ByteBuffer.wrap(error404.getBytes(StandardCharsets.UTF_8)));
                    }
                    LOGGER.info("handle request: {}", requestPath);
                    String header = generateFileContentRepsonseHeader(file);
                    result.write(ByteBuffer.wrap(header.getBytes(StandardCharsets.UTF_8)));
                    Files.copy(file, Channels.newOutputStream(result));
                } catch (InterruptedException | ExecutionException | IOException e) {
                    String error500 = generateResponseMessage(500, "Not Found");
                    result.write(ByteBuffer.wrap(error500.getBytes(StandardCharsets.UTF_8)));
                    LOGGER.error("server error");
                } finally {
                    try {
                        result.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                LOGGER.error(exc.getMessage());
            }
        });
        LOGGER.info("server started!");
    }

    private String generateFileContentRepsonseHeader(Path file) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Type: ").append(getContentType(file)).append("\r\n");
        sb.append("Content-Length: ").append(Files.size(file)).append("\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    private String getContentType(Path file) throws IOException {
        return Files.probeContentType(file);
    }

    private String generateResponseMessage(int statusCode, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(msg).append("\r\n");
        sb.append("Content-Type: text/plain\r\n");
        sb.append("Content-Length: ").append(msg.length()).append("\r\n");
        sb.append("\r\n");
        sb.append(msg);
        return sb.toString();
    }

    private Path getFilePath(Path root, String requestPath) {
        if (requestPath == null || "/".equals(requestPath)) {
            requestPath = INDEX_PAGE;
        }
        if (requestPath.startsWith("/")) {
            requestPath = requestPath.substring(1);
        }
        int pos = requestPath.indexOf("?");
        if (pos != -1) {
            requestPath = requestPath.substring(0, pos);
        }
        return root.resolve(requestPath);
    }

    private String extractPath(String request) {
        Matcher matcher = PATH_EXTRACTOR.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
