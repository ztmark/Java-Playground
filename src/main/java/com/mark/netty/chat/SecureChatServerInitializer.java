package com.mark.netty.chat;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;


/**
 * Author: Mark
 * Date  : 2017/6/20
 */
public class SecureChatServerInitializer extends ChatServerInitializer {

    private final SslContext sslContext;

    public SecureChatServerInitializer(ChannelGroup group, SslContext sslContext) {
        super(group);
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        final ByteBufAllocator alloc = ch.alloc();
        final SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        sslEngine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(sslEngine));
    }
}
