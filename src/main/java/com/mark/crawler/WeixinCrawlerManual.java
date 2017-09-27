package com.mark.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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

    private static List<String> names = Arrays.asList("jiemacaishang"/*, "darenshuoqian"*/);

    private static int count = 1;

    private static final OkHttpClient client = new OkHttpClient.Builder().readTimeout(5000, TimeUnit.SECONDS).build();

    public static void main(String[] args) {

        final String pageUrl = weixinPage("darenshuoqian");
        System.out.println(pageUrl);
        final List<Article> articles = extractArticleUrl(pageUrl);
        System.out.println(articles);
        for (Article article : articles) {
            final Article article1 = extractArticleContent(article);
            System.out.println(article1);
            saveArticle(article1);
        }


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
        String result = null;
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
                result = element.attr("href");
            }
        } catch (Exception e) {
            System.err.println("get wei xin page error " + e.getMessage());
        }
        return StringEscapeUtils.unescapeHtml(result);
    }

    // 获取每一个文章的 URL
    private static List<Article> extractArticleUrl(String articleListUrl) {
        final Request request = new Request.Builder().url(articleListUrl).build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.info("extractArticleUrl request failed code={} ", response.code());
            }
            final ResponseBody body = response.body();
            if ( body == null) {
                log.info("extractArticleUrl response body is null");
                return Collections.emptyList();
            }

            final String content = body.string();
            final Document document = Jsoup.parse(content);

            String data = null;
            // 页面的中的格式为： var msgList = {...}; {}是内容
            final Pattern pattern = Pattern.compile("\\r\\n.*msgList\\s=\\s(\\{.*});.*");
            final Elements scripts = document.getElementsByTag("script");
            for (Element script : scripts) {
                for (DataNode dataNode : script.dataNodes()) {
                    final String wholeData = dataNode.getWholeData();
                    final Matcher matcher = pattern.matcher(wholeData);
                    if (matcher.find()) {
                        data = matcher.group(1);
                    }
                }
            }

            if (StringUtils.isBlank(data)) {
                return Collections.emptyList();
            }

            List<Article> articles = new ArrayList<>();
            final JSONObject jsonObject = JSON.parseObject(data);
            final JSONArray list = jsonObject.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                final JSONObject articleObj = list.getJSONObject(i);
                final JSONObject app_msg_ext_info = articleObj.getJSONObject("app_msg_ext_info");
                final JSONObject comm_msg_info = articleObj.getJSONObject("comm_msg_info");
                String content_url = app_msg_ext_info.getString("content_url");
                if (StringUtils.isBlank(content_url)) {
                    continue;
                }
                if (!content_url.startsWith("https://mp.weixin.qq.com") || !content_url.startsWith("http://mp.weixin.qq.com")) {
                    content_url = "https://mp.weixin.qq.com" + content_url;
                }
                final Article article = new Article();
                article.contentUrl = StringEscapeUtils.unescapeHtml(content_url);
                article.title = app_msg_ext_info.getString("title");
                article.date = comm_msg_info.getLong("datetime");
                articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            System.err.println("extract article url " + articleListUrl + " error " + e.getMessage());
        }
        return Collections.emptyList();
    }

    // 获取文章标题和内容
    private static Article extractArticleContent(Article article) {
        final Request request = new Request.Builder().url(article.contentUrl).build();
        try (final Response response = client.newCall(request).execute();) {

            if (!response.isSuccessful()) {
                log.info("extractArticleContent request failed code={} ", response.code());
            }
            final ResponseBody body = response.body();
            if (body == null) {
                log.info("extractArticleContent response body is null");
                return article;
            }

            final Document document = Jsoup.parse(body.string());
            Element contentElem = document.getElementById("js_content");
            contentElem = replaceImg(contentElem);
            article.content = new HtmlToPlainText().getPlainText(contentElem).replaceAll("\\n+", "\n\n");
        } catch (Exception e) {
            log.error("extract article content {} error {}", article.contentUrl, e.getMessage());
        }
        return article;
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
    private static void saveArticle(Article article) {
        if (StringUtils.isBlank(article.content)) {
            System.out.println("saveArticle content is blank title = " + article.title);
            return;
        }
        String title = article.title;
        if (article.content.contains("请输入图中的验证码")) {
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
                try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8.toString()))) {
                    writer.write(article.content);
                } catch (Exception e) {
                    System.err.println("write file error");
                }
            }
        } catch (IOException e) {
            System.err.println("create file error " + e.getMessage());
        }
    }

    static class Article {
        String title;
        String contentUrl;
        String content;
        long date;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Article{");
            sb.append("title='").append(title).append('\'');
            sb.append(", contentUrl='").append(contentUrl).append('\'');
            sb.append(", content='").append(content).append('\'');
            sb.append(", date=").append(date);
            sb.append('}');
            return sb.toString();
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
