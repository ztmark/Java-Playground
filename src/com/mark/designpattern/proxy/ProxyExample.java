package com.mark.designpattern.proxy;

import java.util.concurrent.TimeUnit;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 15:23
 */
public class ProxyExample {

    public static void main(String[] args) {
        Image image = new ImageProxy("Big_Image");
        image.show();
    }

}
