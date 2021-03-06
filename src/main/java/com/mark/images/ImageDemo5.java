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
        BufferedImage bufferedImage = coverImage("template.png", "icon.jpg", 273, 38, 96, 96);

        File coverFile = new File("qrcode.png"); //覆盖层
        BufferedImage coverImg = ImageIO.read(coverFile);

        bufferedImage = coverImage(bufferedImage, coverImg, 145, 1628, 177, 177);

        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("FangZhengLanTingHeiJianTi.ttf")).deriveFont(28f);
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("用户昵称", 380, 120);
        g.dispose();

        ImageIO.write(bufferedImage, "png", new File("test1.png"));
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
