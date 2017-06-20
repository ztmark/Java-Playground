package com.mark.netty.chat;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Author: Mark
 * Date  : 2017/6/20
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    static {
        final URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "index.html";
            path = path.contains("file") ? path.substring(5) : path;
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable locate index.html", e);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (wsUri.equalsIgnoreCase(msg.uri())) {
            ctx.fireChannelRead(msg.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(msg)) {
                send100Continue(ctx);
            }
            final RandomAccessFile indexFile = new RandomAccessFile(INDEX, "r");
            HttpResponse response = new DefaultHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
//            response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.);
            final boolean keepAlive = HttpUtil.isKeepAlive(msg);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, indexFile.length());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(response);
            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(indexFile.getChannel(), 0, indexFile.length()));
            } else {
                ctx.write(new ChunkedNioFile(indexFile.getChannel()));
            }
            final ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void send100Continue(ChannelHandlerContext ctx) {
        HttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(httpResponse);
    }
}
