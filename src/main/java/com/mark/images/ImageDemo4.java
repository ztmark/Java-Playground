package com.mark.images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * @author Mark
 * @date 2017/10/23
 */
public class ImageDemo4 {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        ImageDemo4.makePicture();
    }

    private static void drawImage(int x, int y, BufferedImage ImageNew, BufferedImage ImageOne) throws IOException {
        int width = ImageOne.getWidth();//图片宽度
        int height = ImageOne.getHeight();//图片高度
        int[] ImageArrayTop = new int[width * height];
        ImageArrayTop = ImageOne.getRGB(0, 0, width, height, ImageArrayTop, 0, width);
        ImageNew.setRGB(x, y, width, height, ImageArrayTop, 0, width);//设置头部的RGB
    }

    public static byte[] makePicture() throws IOException {

//      String imagePath = ImageUtil.class.getResource("/").getPath();
//      System.out.println(imagePath);
//      int idx1 = imagePath.indexOf("WEB-INF");
//      imagePath = imagePath.substring(0, idx1).concat("images/shopping_basket/");

        String imagePath = "";

        File image_1_top = new File(imagePath.concat("food-cart---save---product_01.png"));
        File image_2_food = new File(imagePath.concat("food-cart---save---product_01-02.png"));

        File image_3_shadow = new File(imagePath.concat("food-cart---save---product_01-03.png"));
        File image_4_space = new File(imagePath.concat("food-cart---save---product_05.png"));
        File image_5_code = new File(imagePath.concat("food-cart---save---product_06.png"));


        BufferedImage bf_image_1_top = ImageIO.read(image_1_top);
        BufferedImage bf_image_2_food = ImageIO.read(image_2_food);
        BufferedImage bf_image_3_shadow = ImageIO.read(image_3_shadow);
        BufferedImage bf_image_4_space = ImageIO.read(image_4_space);
        BufferedImage bf_image_5_code = ImageIO.read(image_5_code);

        int width = bf_image_1_top.getWidth();

        int heightTotal = 1 + 1 + 1;//根据多个内容求和算总高度

        BufferedImage ImageNew = new BufferedImage(width, heightTotal, BufferedImage.TYPE_INT_RGB);

        int currentY = 0;
        //第一个图拼接
        drawImage(0, 0, ImageNew, bf_image_1_top);
        currentY += bf_image_1_top.getHeight();

        //第二个图拼接
//      for (int i = 0; i < 集合.size(); i++) {
//          drawProduct(0,currentY,ImageNew,bf_image_2_food,foods.get(i));
//          currentY+=bf_image_2_food.getHeight();
//      }


        Graphics g = ImageNew.getGraphics();
        Font f2 = new Font("", Font.BOLD, 12);
        g.setFont(f2);
        g.drawString(format.format(new Date()), width - 130, heightTotal - 30);
        g.dispose();

        //File outFile = new File(outFilePath);
        //ImageIO.write(ImageNew, "png", outFile);//写图片
        //
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        ImageIO.write(ImageNew, "JPEG", jpegOutputStream);
        byte[] captchaChallengeAsJpeg = null;
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        jpegOutputStream.close();

        //InputStream is = new ByteArrayInputStream(captchaChallengeAsJpeg);
        //return is;

        return captchaChallengeAsJpeg;
        //

    }

//  private static void drawTop(int x,int y,BufferedImage ImageNew,BufferedImage ImageOne) throws IOException{
//      int width = ImageOne.getWidth();//图片宽度
//      int height = ImageOne.getHeight();//图片高度
//      int[] ImageArrayTop = new int[width*height];
//      ImageArrayTop = ImageOne.getRGB(0,0,width,height,ImageArrayTop,0,width);
//      ImageNew.setRGB(x,y,width,height,ImageArrayTop,0,width);//设置头部的RGB
//  }

    private static void drawProduct(int x, int y, BufferedImage ImageNew, BufferedImage ImageTwo) throws IOException {
        int widthTwo = ImageTwo.getWidth();//图片高度
        int heightTwo = ImageTwo.getHeight();//图片高度
        int[] ImageArrayTwo = new int[widthTwo * heightTwo];
        ImageArrayTwo = ImageTwo.getRGB(0, 0, widthTwo, heightTwo, ImageArrayTwo, 0, widthTwo);
        ImageNew.setRGB(x, y, widthTwo, heightTwo, ImageArrayTwo, 0, widthTwo);


        URL url = new URL("图片");
        URLConnection conn = url.openConnection();
        InputStream inStream = conn.getInputStream();

        BufferedImage ImagePuduct = ImageIO.read(inStream);
        int widthPuduct = ImagePuduct.getWidth();
        int heightPuduct = ImagePuduct.getHeight();//图片高度

        Image image = ImagePuduct.getScaledInstance(100, 80, Image.SCALE_DEFAULT);//获取缩略图
        /*再创建一个BufferedImage对象 用于创建100*100大小的图像*/
        BufferedImage oimage;
        oimage = new BufferedImage(100, 80, Image.SCALE_DEFAULT);
        /*获取图像上下文对象，然后把刚才的Image对象画到BufferedImage中去
            切忌， drawImage()方法有很多重载方法，一定要选用下面的这个，它会95%的复制原图的图片质量。其他重载方法你也可以试试，可能生成出来的图片很丑噢~哈哈
         */
        oimage.getGraphics().drawImage(image, 0, 0, null);

        widthPuduct = 100;
        heightPuduct = 80;
        int[] ImageArrayPuduct = new int[widthPuduct * heightPuduct];
        ImageArrayPuduct = oimage.getRGB(0, 0, widthPuduct, heightPuduct, ImageArrayPuduct, 0, widthPuduct);
        ImageNew.setRGB(60, y + 20, widthPuduct, heightPuduct, ImageArrayPuduct, 0, widthPuduct);

        drawProductText(x, y, widthPuduct, heightPuduct, ImageNew);
    }

    private static void drawProductText(int x, int y, int widthProduct, int heightProduct, BufferedImage ImageNew) {
        Graphics g = ImageNew.getGraphics();
        Font f = new Font("", Font.BOLD, 24);
        g.setColor(Color.decode("#333"));
        g.setFont(f);
        g.drawString("内容", x + 60 + widthProduct + 20, y + 40);


        f = new Font("", Font.BOLD, 18);
        g.setFont(f);
        g.setColor(Color.decode("#F4CAC9"));
        g.drawString("内容", 60 + widthProduct + 59, y + 20 + 20 + 32);


//
        g.dispose();
    }
    /*
    private static void drawBottom(int x,int y,BufferedImage ImageNew,BufferedImage ImageBottom) throws IOException{
        int width = ImageBottom.getWidth();//图片高度
        int heightBottom = ImageBottom.getHeight();//图片高度
        int[] ImageArrayBottom = new int[width*heightBottom];
//      Graphics g = ImageNew.getGraphics();
//      int xx =10;
//      int yy =10;
//      Font f2 = new Font("",Font.BOLD,12);
//      g.setFont(f2);
//      g.drawString(format.format(new Date()),xx,yy);
//      g.dispose();
        ImageArrayBottom = ImageBottom.getRGB(0,0,width,heightBottom,ImageArrayBottom,0,width);
        ImageNew.setRGB(x,y,width,heightBottom,ImageArrayBottom,0,width);
    }
    */

}
