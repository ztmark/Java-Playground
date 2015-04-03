package com.mark.designpattern.proxy;

/**
 * Author: Mark
 * Date  : 2015/4/2
 * Time  : 15:22
 */
public class ImageProxy implements Image {

    private Image readImage;
    private String imageName;

    public ImageProxy(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public void show() {
        if (readImage == null) {
            System.out.println("加载中...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readImage = new RealImage(imageName);
                    readImage.show();
                }
            }).start();
        } else {
            readImage.show();
        }
    }

}
