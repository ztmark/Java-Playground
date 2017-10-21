package com.mark.images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Mark
 * @date 2017/10/21
 */
public class ImageDemo2 {

    private static void createImage(String fileLocation, BufferedImage image) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void graphicsGeneration(String path) {

        int imageWidth = 1000;// 图片的宽度

        int imageHeight = 1000;// 图片的高度

        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.ORANGE);
        graphics.setFont(new Font("宋体", Font.BOLD, 20));

        int num = 50;
        graphics.drawString("手机号: 13112345678", 50, num);
        num += 50;
        graphics.drawString("登陆密码: 12345678", 50, num);
        num += 50;
        graphics.drawString("身份证号: 330123423243243", 50, num);
        num += 50;
        graphics.drawString("姓名: Mark", 50, num);
        num += 50;
        graphics.drawString("所在地区: 杭州", 50, num);
        num += 50;
        graphics.drawString("常住地址: 西湖:", 50, num);
        num += 50;
        graphics.drawString("微信号: 1234345", 50, num);
        num += 50;
        graphics.drawString("QQ号: 12321313", 50, num);
        num += 50;
        graphics.drawString("Email: 123123@qq.com", 50, num);
        num += 50;
        graphics.drawString("品牌: mark", 50, num);
        num += 50;
        graphics.drawString("型号: xxxx", 50, num);
        num += 50;
        graphics.drawString("商品编号: 234", 50, num);
        num += 50;
        graphics.drawString("购买日期: 2017-01-01", 50, num);
        num += 50;
        graphics.drawString("购买价格: 23", 50, num);
        num += 50;
        graphics.drawString("购买途径: web", 50, num);
        num += 50;
        graphics.drawString("报修电话: 12312312", 50, num);
        num += 50;
        graphics.drawString("延保单位: 323", 50, num);
        num += 50;
        graphics.drawString("延保电话: 3232323", 50, num);
        num += 50;
        graphics.drawString("发票编号: 2323232", 50, num);

        createImage(path, image);
    }

    public static void main(String[] args) {
        java.util.List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> mapTitle1 = new HashMap<>();
        mapTitle1.put("title", "使用人信息");
        list.add(mapTitle1);

        Map<String, String> map1 = new HashMap<>();
        map1.put("客户姓名", "张三");
        map1.put("手机号", "123123");
        map1.put("身份证号", "230302198811241234");
        list.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("送货地址", "北京市海淀区知春路113号银网中心B座1009室");
        list.add(map2);

        Map<String, String> map3 = new HashMap<>();
        map3.put("微信号码", "123123");
        map3.put("qq号码", "123123");
        map3.put("电子邮箱", "123123@qq.com");
        list.add(map3);

        Map<String, String> mapTitle2 = new HashMap<>();
        mapTitle2.put("title", "购买人信息");
        list.add(mapTitle2);

        Map<String, String> map4 = new HashMap<>();
        map4.put("姓名", "张三朋友");
        map4.put("手机号", "15612341122");
        map4.put("身份证号", "230302198811241234");
        list.add(map4);

        Map<String, String> mapTitle3 = new HashMap<>();
        mapTitle3.put("title", "产品信息");
        list.add(mapTitle3);

        Map<String, String> map5 = new HashMap<>();
        map5.put("产品型号", "ALI88");
        map5.put("凭证类型", "发票");
        map5.put("购买日期", "2014-12-02");
        list.add(map5);

        Map<String, String> map6 = new HashMap<>();
        map6.put("购买商城", "ALI88");
        map6.put("凭证编号", "发票");
        list.add(map6);

        int imageWidth = 1200;// 图片的宽度

        int imageHeight = 1000;// 图片的高度

        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.black);

        int high = 100;
        int wigth = 0;
        graphics.setFont(new Font("宋体", Font.BOLD, 50));
        graphics.drawString("注册保单", 500, high);
        graphics.setFont(new Font("宋体", Font.BOLD, 20));
        high += 10;
        graphics.drawLine(50, high, 1150, high);

        for (Map<String, String> rowMap : list) {
            high += 50;
            wigth = 50;
            for (Map.Entry<String, String> entry : rowMap.entrySet()) {
                String name = entry.getKey() + "：" + entry.getValue();
                if ("title".equals(entry.getKey())) {
                    high += 50;
                    graphics.setFont(new Font("黑体", Font.BOLD, 30));
                    graphics.drawString(entry.getValue(), wigth, high);
                    graphics.setFont(new Font("宋体", Font.BOLD, 20));
                } else {
                    graphics.drawString(name, wigth, high);
                    wigth += 400;
                }

            }
        }

        createImage("test1.jpg", image);

    }


}
