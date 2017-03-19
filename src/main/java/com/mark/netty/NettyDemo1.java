package com.mark.netty;

import io.netty.bootstrap.ServerBootstrap;

/**
 * Author: Mark
 * Date  : 2017/2/18
 */
public class NettyDemo1 {

    public static void main(String[] args) {
//        ServerBootstrap serverBootstrap = new ServerBootstrap();
        String s = "a,b,, ";
        final String[] split = s.split(",");
        System.out.println(split);
    }


}
