package com.mark.jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.ProxyConfig;
import com.machinepublishers.jbrowserdriver.RequestHeaders;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.UserAgent;
import com.mark.crawler.WeixinCrawler;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


/**
 * Author: Mark
 * Date  : 2017/9/5
 */
public class JsoupDemoTest {

    @Test
    public void ts() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://weixin.sogou.com/weixin?type=1&query=%E9%92%B1%E5%A0%82%E4%B8%80%E5%A7%90&ie=utf8&s_from=input&_sug_=y&_sug_type_=")
                .get()
                .addHeader("cookie", "SUV=001D01C573C802E05701CC1D34B1C320; SUID=FA4CEC7363138B0A58511E1E000CE4FF; wuid=AAF9mHuzFQAAAAqLE2NEdg4ApwM=; ABTEST=0|1504581511|v1; " +
                        "SNUID=5AEC4CD2A0A5F9C90F2EDBE5A1BC13C9; IPLOC=CN3301; weixinIndexVisited=1; ld=okllllllll2BrXjslllllVumt3DllllltMeGMkllll9lllllxylll5@@@@@@@@@@; " +
                        "usid=FA4CEC731418980A0000000059AE70B0; JSESSIONID=aaabdIhurDvwScKo-rr5v; PHPSESSID=ccvd593qtt3fu7tlfvbuboarq3; SUIR=5AEC4CD2A0A5F9C90F2EDBE5A1BC13C9; sct=5")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "91a7858f-3560-1e14-04d1-d096088e56f1")
                .build();

        Response response = client.newCall(request).execute();
        final String string = response.body().string();
        System.out.println(string);
    }


    @Test
    public void get_page_url() {
        final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME).requestHeaders(RequestHeaders.CHROME)
                                                                 .proxy(new ProxyConfig(ProxyConfig.Type.HTTP, "125.121.113.158", 808)).build());
//        final WebDriver.Options manage = driver.manage();
//        if (manage != null) {
//            final Cookie suv = new Cookie("SUV", "001D01C573C802E05701CC1D34B1C320");
//            suv.validate();
//            manage.addCookie(suv);
//            manage.addCookie(new Cookie("SUID", "FA4CEC7363138B0A58511E1E000CE4FF"));
//            manage.addCookie(new Cookie("wuid", "AAF9mHuzFQAAAAqLE2NEdg4ApwM="));
//            manage.addCookie(new Cookie("ABTEST", "0|1504581511|v1"));
//            manage.addCookie(new Cookie("SNUID", "5AEC4CD2A0A5F9C90F2EDBE5A1BC13C9"));
//            manage.addCookie(new Cookie("IPLOC", "CN3301"));
//            manage.addCookie(new Cookie("weixinIndexVisited", "1"));
//            manage.addCookie(new Cookie("ld", "okllllllll2BrXjslllllVumt3DllllltMeGMkllll9lllllxylll5@@@@@@@@@@"));
//            manage.addCookie(new Cookie("usid", "FA4CEC731418980A0000000059AE70B0"));
//            manage.addCookie(new Cookie("JSESSIONID", "aaabdIhurDvwScKo-rr5v"));
//            manage.addCookie(new Cookie("PHPSESSID", "ccvd593qtt3fu7tlfvbuboarq3"));
//            manage.addCookie(new Cookie("SUIR", "5AEC4CD2A0A5F9C90F2EDBE5A1BC13C9"));
//            manage.addCookie(new Cookie("sct", "5"));
//        }

        final List<String> urlLists = Arrays.asList("http://weixin.sogou.com/weixin?type=1&query=%E4%B8%89%E8%81%94%E7%94%9F%E6%B4%BB%E5%91%A8%E5%88%8A&ie=utf8&s_from=input&_sug_=n&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E5%87%A4%E5%87%B0%E7%A7%91%E6%8A%80&ie=utf8&s_from=input&_sug_=y&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&s_from=input&query=meiriyidu8&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=1533&sst0=1504669191351&lkt=0%2C0%2C0",
                "http://weixin.sogou.com/weixin?type=1&query=%E5%BC%A0%E9%80%97%E5%BC%A0%E8%8A%B1&ie=utf8&s_from=input&_sug_=n&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E4%B8%81%E9%A6%99%E5%8C%BB%E7%94%9F&ie=utf8&s_from=input&_sug_=n&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E9%92%B1%E6%B1%9F%E6%99%9A%E6%8A%A5&ie=utf8&s_from=input&_sug_=n&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E8%A7%82%E5%AF%9F%E8%80%85%E7%BD%91&ie=utf8&s_from=input&_sug_=n&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E7%B3%97%E4%BA%8B%E7%99%BE%E7%A7%91&ie=utf8&s_from=input&_sug_=y&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E7%AC%AC%E5%8D%81%E6%94%BE%E6%98%A0%E5%AE%A4&ie=utf8&s_from=input&_sug_=n&_sug_type_=",
                "http://weixin.sogou.com/weixin?type=1&query=%E6%B5%99%E6%B1%9F%E5%8F%91%E5%B8%83+&ie=utf8&s_from=input&_sug_=n&_sug_type_=");

        int count = 0;
        for (int i = 0; i < 200; i++) {
            final String url = urlLists.get(i % 10);
            driver.get(url);
            final Document document = Jsoup.parse(driver.getPageSource());
            count++;
            System.out.println(document);
            System.out.println("=================================");
            if (document.toString().contains("请输入图中的验证码")) {
                break;
            }
        }
        System.out.println(count);

    }

    @Test
    public void get_article_url() throws IOException {
        JBrowserDriver driver = new JBrowserDriver(Settings.builder().requestHeaders(RequestHeaders.CHROME).proxy(new ProxyConfig(ProxyConfig.Type.HTTP, "125.121.113.158", 808)).build());
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
        driver.get("https://mp.weixin.qq.com/s?timestamp=1504769886&src=3&ver=1&signature=GnIZgiifnMzG*KUjjQOKn*--YpHykH6zgU90b0q5MNQH1RMLtmKHXIq9ZGBaMSuwFnAB0xFqVPXHuboT4trHbxjGRJq0-xDB6bb8dLmIb17e2bTxO*Pexo7-ARCc36L8uuXmgBXopdha9QaZFn5tD8e8tIHkLuH1DBnUBSnaxp8=");
        final Document document = Jsoup.parse(driver.getPageSource());

        final Element meta_content = document.getElementById("meta_content");
        final Element content = document.getElementsByClass("rich_media_content").first();

        final String title = document.title();
        final HtmlToPlainText htmlToPlainText = new HtmlToPlainText();
        final String metaContent = htmlToPlainText.getPlainText(meta_content);
        final String plainText = htmlToPlainText.getPlainText(content);
        saveArticle(title, plainText);
    }

    @Test
    public void downloadImage() {
        final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME)
                                                                 .requestHeaders(RequestHeaders.CHROME).timezone(Timezone.ASIA_SHANGHAI).build());
        driver.get("https://mp.weixin.qq.com/s?timestamp=1504769886&src=3&ver=1&signature=GnIZgiifnMzG*KUjjQOKn*--YpHykH6zgU90b0q5MNQH1RMLtmKHXIq9ZGBaMSuwFnAB0xFqVPXHuboT4trHbxjGRJq0-xDB6bb8dLmIb17e2bTxO*Pexo7-ARCc36L8uuXmgBXopdha9QaZFn5tD8e8tIHkLuH1DBnUBSnaxp8=");
        final Document document = Jsoup.parse(driver.getPageSource());

        final Element content = document.getElementsByClass("rich_media_content").first();

        final Elements imgs = content.getElementsByTag("img");
        for (Element img : imgs) {
            final String imgUrl = img.attr("data-src");
            final Element parent = img.parent();
            parent.prepend("<div>[img]" + imgUrl + "[/img]</div>");
        }
        final String plainText = new HtmlToPlainText().getPlainText(content).replaceAll("\\n+", "\n\n");

        saveArticle("test", plainText);
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

    // 保存到文件
    private static void saveArticle(String title, String content) {
        if (content.contains("请输入图中的验证码")) {
            System.out.println(title + " 获取失败，原因：请求太频繁，需要输入验证码");
            return;
        }
        if (StringUtils.isBlank(title)) {
            title = "no title ";
        }
        File file = new File(title + ".txt");
        if (file.exists()) {
            System.err.println(file.getName() + " exists");
        }
        try {
            final boolean success = file.createNewFile();
            if (success) {
                try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                    writer.write(content);
                } catch (Exception e) {
                    System.err.println("write file error");
                }
            }
        } catch (IOException e) {
            System.err.println("create file error " + e.getMessage());
        }
    }


}
