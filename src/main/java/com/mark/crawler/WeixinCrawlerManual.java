package com.mark.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.RequestHeaders;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author: Mark
 * Date  : 2017/9/7
 */
public class WeixinCrawlerManual {

    private static final Logger log = LoggerFactory.getLogger(WeixinCrawlerManual.class);

    private static final String searchUrl = "http://weixin.sogou.com/weixin?type=1&query={}&ie=utf8&s_from=input&_sug_=y&_sug_type_=";

    private static List<String> names = Arrays.asList(/*"jiemacaishang",*/ "darenshuoqian");

    private static int count = 1;

    private static final OkHttpClient client = new OkHttpClient.Builder().readTimeout(5000, TimeUnit.SECONDS).build();

    public static void main(String[] args) {


        final String pageUrl = weixinPage("darenshuoqian");
        System.out.println(pageUrl);


//        for (String name : names) {
//            long start = System.currentTimeMillis();
//
//            System.out.println("Begin crawl " + name);
//            final String pageUrl = weixinPage(name);
//            System.out.println("get " + name + " url => " + pageUrl);
//            final List<Tuple<String, String>> urls = extractArticleUrl(pageUrl);
//            System.out.println("get article urls " + urls.size());
//            for (Tuple<String, String> tuple : urls) {
//                System.out.println("begin extract content " + tuple.first + " url=" + tuple.second);
//                final Tuple<String, String> content = extractArticleContent(tuple.second);
//                System.out.println("done get " + content.first + " content");
//                saveArticle(content);
//                System.out.println("done save article " + content.first);
//            }
//
//            long duration = System.currentTimeMillis() - start;
//            System.out.println("done crawl " + name + " it takes " + duration + " milliseconds");
//
//        }

    }

    // 搜索公众号并获取文章列表地址
    private static String weixinPage(String weixinName) {

        final String url = StringUtils.replaceOnce(searchUrl, "{}", weixinName);
        final Request request = new Request.Builder().url(url).build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.info("weixinPage request failed code={} ", response.code());
            }
            final ResponseBody body = response.body();
            if ( body == null) {
                log.info("weixinPage response body is null");
                return null;
            }
            final Document document = Jsoup.parse(body.string());
            final Elements elements = document.getElementsByAttributeValue("uigs", "account_name_0");
            if (elements != null && elements.size() > 0) {
                final Element element = elements.get(0);
                return element.attr("href");
            }
        } catch (Exception e) {
            System.err.println("get wei xin page error " + e.getMessage());
        }
        return null;
    }

    // 获取每一个文章的 URL
    private static List<Tuple<String, String>> extractArticleUrl(String articleListUrl) {
        try {
            final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME).requestHeaders(RequestHeaders.CHROME).build());
            driver.get(articleListUrl);
            final Document document = Jsoup.parse(driver.getPageSource());
            final Elements list = document.getElementsByTag("h4");
            List<Tuple<String, String>> pageUrls = new ArrayList<>();
            String https = "https://mp.weixin.qq.com";
            String http = "http://mp.weixin.qq.com";
            for (Element element : list) {
                String hrefs = element.attr("hrefs");
                if (StringUtils.isBlank(hrefs)) {
                    continue;
                }
                if (!hrefs.startsWith(http) && !hrefs.startsWith(https)) { // 没有前缀的加上前缀
                    hrefs = https + hrefs;
                }

                String title;
                final Element logo = element.getElementById("copyright_logo");
                if (logo != null) { // 如果有 原创 标签
                    final Node titleNode = logo.nextSibling();
                    title = titleNode.toString();
                } else { // 没有原创标签
                    title = element.text();
                }
                pageUrls.add(new Tuple<>(title, hrefs));
            }
            driver.quit();
            return pageUrls;
        } catch (Exception e) {
            System.err.println("extract article url " + articleListUrl + " error " + e.getMessage());
        }
        return Collections.emptyList();
    }

    // 获取文章标题和内容
    private static Tuple<String, String> extractArticleContent(String articleUrl) {
        try {
            final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME).requestHeaders(RequestHeaders.CHROME).build());
            driver.get(articleUrl);
            final Document document = Jsoup.parse(driver.getPageSource());
            final String title = document.title();
            Element contentElem = document.getElementsByClass("rich_media_content").first();
            contentElem = replaceImg(contentElem);
            final String content = new HtmlToPlainText().getPlainText(contentElem).replaceAll("\\n+", "\n\n");
            driver.quit();
            return new Tuple<>(title, content);
        } catch (Exception e) {
            System.err.println("extract article content error " + articleUrl + e.getMessage());
        }
        return new Tuple<>(null, null);
    }

    // 转换图片
    private static Element replaceImg(Element content) {
        final Elements imgs = content.getElementsByTag("img");
        for (Element img : imgs) {
            try {
                final Element parent = img.parent();
                final String imgUrl = img.attr("data-src");
                String url = convertImgUrl(imgUrl);
                if (StringUtils.isBlank(url)) {
                    System.err.println("save image error " + imgUrl);
                    url = imgUrl;
                }
                parent.prepend("<div>[img]" + url + "[/img]</div>");
            } catch (Exception e) {
                System.err.println("replace img error " + img);
            }
        }
        return content;
    }

    private static String convertImgUrl(String imgUrl) {
        // TODO:  save img
        return imgUrl;
    }

    // 保存到文件
    private static void saveArticle(Tuple<String, String> tuple) {
        if (StringUtils.isBlank(tuple.second)) {
            System.out.println("saveArticle content is blank title = " + tuple.first);
            return;
        }
        String title = tuple.first;
        if (tuple.second.contains("请输入图中的验证码")) {
            System.out.println(title + " 获取失败，原因：请求太频繁，需要输入验证码");
            return;
        }
        if (StringUtils.isBlank(title)) {
            title = "no title " + count;
            count++;
        }
        File file = new File(title + ".txt");
        if (file.exists()) {
            System.err.println(file.getName() + " exists");
        }
        try {
            final boolean success = file.createNewFile();
            if (success) {
                try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                    writer.write(tuple.second);
                } catch (Exception e) {
                    System.err.println("write file error");
                }
            }
        } catch (IOException e) {
            System.err.println("create file error " + e.getMessage());
        }
    }

    static class Tuple<F, S> implements Serializable {
        private static final long serialVersionUID = 6099113305190167880L;

        public final F first;
        public final S second;

        public Tuple(F first, S second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Tuple{");
            sb.append("first=").append(first);
            sb.append(", second=").append(second);
            sb.append('}');
            return sb.toString();
        }
    }

}
