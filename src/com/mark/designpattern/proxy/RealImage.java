package com.mark.designpattern.proxy;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 15:12
 */
public class RealImage implements Image {

    private String imageName;

    public RealImage(String imageName) {
        this.imageName = imageName;
        loadFromServer();
    }

    private void loadFromServer() {
        //System.out.println("加载中...");
    }

    @Override
    public void show() {
        System.out.println("显示 " + imageName);
    }
}
