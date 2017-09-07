package com.mark.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.RequestHeaders;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;

/**
 * Author: Mark
 * Date  : 2017/9/7
 */
public class WeixinCrawler {

    private static final String searchUrl = "http://weixin.sogou.com/weixin?type=1&query={}&ie=utf8&s_from=input&_sug_=y&_sug_type_=1&w=01015002&oq=&ri=1&sourceid=sugg&sut=0&sst0=1504750542211&lkt=0%2C0%2C0&p=40040108";

    private static List<String> names = Arrays.asList("力哥理财", "三公子的人生记录仪");

    private static int count = 1;

    public static void main(String[] args) {

//        final String qiantang = weixinPage("钱堂一姐");
//        if (StringUtils.isBlank(qiantang)) {
//            System.out.println("weixin page url is blank");
//            return;
//        }

//        String url = "https://mp.weixin.qq.com/profile?src=3&timestamp=1504750551&ver=1&signature=c6FTLlzgLtTffWpp3YwMXlCyP5ZoXSG7r9B0xPg0QvqhCzwIecYnYm1bz-1PbYU2ox3qUnCCXJQ*X4SeGr-eUw==";
//        final Set<String> strings = extractArticleUrl(url);
//        System.out.println(strings.size());
//        strings.forEach(System.out::println);

//        String url = "https://mp.weixin.qq.com/s?timestamp=1504751895&src=3&ver=1&signature=dGIRNLv8qJA8IWsaHmolwnsQlT4i0emF6ET6caclOhGL4Aarh9LHugxnrDPJ6YpY-qlvI0JaDFCtMmjddTpqAHHWFwNfZu3-vIaHBDCojUGclZ2L1J7OQ8QumK7pBZPp08S*xrOgsZRVIwbp1rAfhbS1MVWb5rOWMuc-ULEXaJs=";
//        final Tuple<String, String> tuple = extractArticleContent(url);
//        System.out.println(tuple);

//        final Tuple<String, String> tuple = new Tuple<>("test", "xxxxxxxxxx xxxxxxxxxxx");
//        saveArticle(tuple);


        for (String name : names) {
            long start = System.currentTimeMillis();

            System.out.println("Begin crawl " + name);
            final String pageUrl = weixinPage(name);
            System.out.println("get " + name + " url => " + pageUrl);
            final Set<String> urls = extractArticleUrl(pageUrl);
            System.out.println("get article urls " + urls.size());
            for (String url : urls) {
                System.out.println("begin extract content " + url);
                final Tuple<String, String> tuple = extractArticleContent(url);
                System.out.println("done get " + tuple.first + " content");
                saveArticle(tuple);
                System.out.println("done save article " + tuple.first);
            }

            long duration = System.currentTimeMillis() - start;
            System.out.println("done crawl " + name + " it takes " + duration + " milliseconds");

        }

    }

    // 搜索公众号并获取文章列表地址
    private static String weixinPage(String weixinName) {

        try {
            final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME).requestHeaders(RequestHeaders.CHROME).build());
            final String url = StringUtils.replaceOnce(searchUrl, "{}", weixinName);
            driver.get(url);
            final Document document = Jsoup.parse(driver.getPageSource());
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
    private static Set<String> extractArticleUrl(String articleListUrl) {
        try {
            final JBrowserDriver driver = new JBrowserDriver(Settings.builder().userAgent(UserAgent.CHROME).requestHeaders(RequestHeaders.CHROME).build());
            driver.get(articleListUrl);
            final Document document = Jsoup.parse(driver.getPageSource());
            final Elements list = document.getElementsByTag("h4");
            Set<String> pageUrls = new LinkedHashSet<>();
            String url = "https://mp.weixin.qq.com";
            for (Element element : list) {
                pageUrls.add(url + element.attr("hrefs"));
            }
            return pageUrls;
        } catch (Exception e) {
            System.err.println("extract article url " + articleListUrl + " error " + e.getMessage());
        }
        return Collections.emptySet();
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
