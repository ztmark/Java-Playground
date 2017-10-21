package com.mark.images;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

/**
 * @author Mark
 * @date 2017/10/21
 */
public class ImageDemo3 {

    public static void main(String[] args) throws IOException {
        createImage();
    }

    private static void createImage() throws IOException {
        int width = 100;
        int height = 100;
        String content = "你好";
        File file = new File("createImage.jpg");
        Font font = new Font("Serif", Font.BOLD, 10);

        BufferedImage bufferedImage = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0, 0, width, height);
        graphics2D.setPaint(Color.RED);
        FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(content, fontRenderContext);
        double x = (width - stringBounds.getWidth()) / 2;
        double y = (height - stringBounds.getHeight()) / 2;
        double ascent = -stringBounds.getY();
        double baseY = y + ascent;
        graphics2D.drawString(content, (int) x, (int) baseY);

        // 1.将图片写到实体图片里
        ImageIO.write(bufferedImage, "jpg", file);
        // 2.将图片写到流里
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        // 3.将图片以base64的形式展示
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        System.out.println("data:image/png;base64," + base64Encoder.encodeBuffer(byteArray).trim());
    }

}
