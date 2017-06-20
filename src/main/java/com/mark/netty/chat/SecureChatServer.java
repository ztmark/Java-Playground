package com.mark.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * Author: Mark
 * Date  : 2017/6/20
 */
public class SecureChatServer extends ChatServer {

    private SslContext sslContext;

    public SecureChatServer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group, sslContext);
    }

    public static void main(String[] args) throws Exception {
        final SelfSignedCertificate cert = new SelfSignedCertificate();
        final SslContext context = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
        final SecureChatServer secureChatServer = new SecureChatServer(context);
        final ChannelFuture future = secureChatServer.start(new InetSocketAddress(8680));
        Runtime.getRuntime().addShutdownHook(new Thread(secureChatServer::destroy));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
