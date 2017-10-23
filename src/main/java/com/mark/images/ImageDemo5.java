package com.mark.images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * @author Mark
 * @date 2017/10/23
 */
public class ImageDemo5 {


    public static void main(String[] args) throws Exception {
        final BufferedImage bufferedImage = coverImage("base.png", "first.png", 145, 1450, 180, 180);
        ImageIO.write(bufferedImage, "png", new File("he.png"));
    }

    /**
     * 图片覆盖（覆盖图压缩到width*height大小，覆盖到底图上）
     *
     * @param baseFilePath  底图
     * @param coverFilePath 覆盖图
     * @param x             起始x轴
     * @param y             起始y轴
     * @param width         覆盖宽度
     * @param height        覆盖长度度
     * @return
     * @throws Exception
     */
    public static BufferedImage coverImage(String baseFilePath, String coverFilePath, int x, int y, int width, int height) throws Exception {

        File baseFile = new File(baseFilePath);//底图
        BufferedImage buffImg = ImageIO.read(baseFile);

        File coverFile = new File(coverFilePath); //覆盖层
        BufferedImage coverImg = ImageIO.read(coverFile);

        buffImg = coverImage(buffImg, coverImg, x, y, width, height);

        return buffImg;
    }


    /**
     * 图片覆盖（覆盖图压缩到width*height大小，覆盖到底图上）
     *
     * @param baseBufferedImage  底图
     * @param coverBufferedImage 覆盖图
     * @param x                  起始x轴
     * @param y                  起始y轴
     * @param width              覆盖宽度
     * @param height             覆盖长度度
     * @return
     * @throws Exception
     */
    public static BufferedImage coverImage(BufferedImage baseBufferedImage, BufferedImage coverBufferedImage, int x, int y, int width, int height) throws Exception {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = baseBufferedImage.createGraphics();

        // 绘制
        g2d.drawImage(coverBufferedImage, x, y, width, height, null);
        g2d.dispose();// 释放图形上下文使用的系统资源

        return baseBufferedImage;
    }

}
