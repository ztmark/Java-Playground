package com.mark.images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Mark
 * @date 2017/10/21
 */
public class ImageDemo {

    public static void main(String[] args) throws IOException, FontFormatException {
//        makeImage();
        modifyImage();
    }

    private static void makeImage() throws FontFormatException, IOException {
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Bosk.ttf")).deriveFont(Font.BOLD, 60f);
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        BufferedImage image = new BufferedImage(500, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Mark", 100, 100);
        g.dispose();
        ImageIO.write(image, "PNG", new File("result.png"));
    }

    private static void modifyImage() throws FileNotFoundException {
        try {
            //构造Image对象
            Image src = javax.imageio.ImageIO.read(new File("result.png"));
            //得到源图宽
            int wideth = src.getWidth(null);
            //得到源图长
            int height = src.getHeight(null);
            //获得内存
            BufferedImage tag = new BufferedImage(wideth / 2, height / 2, BufferedImage.TYPE_INT_RGB);
            //得到图片上下文
            Graphics2D g = tag.createGraphics();
            //设置图片背景色
            g.setBackground(new Color(200, 250, 200));
            //画图片的边框使用背景色
            g.clearRect(0, 0, wideth, height);
            //设置画笔的颜色
            g.setColor(Color.RED);
            //向图片画另一个图片上去
            g.drawImage(src, 0, 0, wideth / 2, height / 2, null); //绘制缩小后的图
            for (int i = 10; i < wideth; i += 200) {
                for (int j = 10; j < height; j += 100) {
                    g.drawString("( " + i + "   ,   " + j + ") ", i, j);
                }
            }
            //向图片画一条线
            //g.drawLine(0,   0,   99,   199);
            //结束图片
            g.dispose();
            tag.flush();
            FileOutputStream out = new FileOutputStream("newfile.jpg "); //  如果想生成新的文件需要定义out
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag); //近JPEG编码
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
