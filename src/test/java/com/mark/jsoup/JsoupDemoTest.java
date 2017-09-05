package com.mark.jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.RequestHeaders;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.UserAgent;

/**
 * Author: Mark
 * Date  : 2017/9/5
 */
public class JsoupDemoTest {

    @Test
    public void testDemo1() throws IOException {
        JBrowserDriver driver = new JBrowserDriver(Settings.builder().timezone(Timezone.ASIA_SHANGHAI).build());
        driver.get("https://mp.weixin.qq.com/profile?src=3&timestamp=1504583131&ver=1&signature=33uh3zsZYPHNwW6fGu*vFp3FE0rOMtA82o8ZUHSy4Gy1MFWxOnh437UsYVAN7pDgllQ2F5HrwCg2RETEyBaxXg==");
        final Document document = Jsoup.parse(driver.getPageSource());
        final Elements list = document.getElementsByTag("h4");
        Set<String> pageUrls = new LinkedHashSet<>();
        String url = "https://mp.weixin.qq.com";
        for (Element element : list) {
            pageUrls.add(url + element.attr("hrefs"));
        }
        System.out.println(pageUrls.size());
        pageUrls.forEach(System.out::println);
    }

    @Test
    public void get_full_article() throws IOException {
        long start = System.currentTimeMillis();
        JBrowserDriver driver = new JBrowserDriver(Settings.builder().timezone(Timezone.ASIA_SHANGHAI).build());
        driver.get("https://mp.weixin.qq.com/profile?src=3&timestamp=1504583131&ver=1&signature=33uh3zsZYPHNwW6fGu*vFp3FE0rOMtA82o8ZUHSy4Gy1MFWxOnh437UsYVAN7pDgllQ2F5HrwCg2RETEyBaxXg==");
        Document document = Jsoup.parse(driver.getPageSource());
        final Elements list = document.getElementsByTag("h4");
        Set<String> pageUrls = new LinkedHashSet<>();
        String url = "https://mp.weixin.qq.com";
        for (Element element : list) {
            pageUrls.add(url + element.attr("hrefs"));
        }
        for (String pageUrl : pageUrls) {
            driver.reset();
            driver.get(pageUrl);
            document = Jsoup.parse(driver.getPageSource());
            final Element article = document.getElementsByClass("rich_media_content").first();
            File file = new File(document.title() + ".html");
            final boolean newFile = file.createNewFile();
            if (newFile) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                    Jsoup.parse(article.toString()).text();
                    writer.write(article.toString());
                    System.out.println("write to " + file.getName());
                }
            }
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("it takes " + duration + " millis");
    }

    @Test
    public void get_one_article_text() {
        final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME)
                                                                 .requestHeaders(RequestHeaders.CHROME).timezone(Timezone.ASIA_SHANGHAI).build());
        driver.get("https://mp.weixin.qq.com/s?timestamp=1504602128&src=3&ver=1&signature=OtHbNzOh6VuPwsT6trxMdGi3LA-o3JHaSpDd4yCnED9e50FdOjyFzv*Yv9j8KupGRy5Cy6*ctNFwwOUI9Ks-bZdXQwkAp5XCKlMt3zdj60qR9mgij30-N4E3ylMERktexsDykFduPBaNpxcWIzme*C2BVhQl2V4Otbka0Jp2i3c=");
        final Document document = Jsoup.parse(driver.getPageSource());
        final Element content = document.getElementsByClass("rich_media_content").first();

        final String plainText = new HtmlToPlainText().getPlainText(content);
        System.out.println(plainText);

        System.out.println("==============");
        System.out.println("==============");
        System.out.println("==============");
        System.out.println("==============");

        final String s = delHTMLTag(content.toString());
        System.out.println(s);
    }

    private static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }


}
