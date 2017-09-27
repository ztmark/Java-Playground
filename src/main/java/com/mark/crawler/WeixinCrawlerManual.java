package com.mark.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
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

    private static List<String> names = Arrays.asList(
            "jiemacaishang", "darenshuoqian", "laoqianshuoqian", "kkmoney666", "weifusd", "mycaijing", "tancaijing", "qgq1818",
            "jinrongtegong", "housetencent", "p2pmoney", "cainiaolc", "jane7ducai", "licaitt", "fengyuhuangshan", "cainvdangjia",
            "cgbj518", "kongfuf", "qijunjie82", "licaizhishi8", "gaotiantalkshow", "mymoney888 ", "xueqiujinghua", "xuetouzilicai",
            "LicaiApp", "veekn365", "mumuxuecai", "lrgq2016", "sangongzi0906", "yangmaoyijie", "chinaetfs", "gh_9baf199c66f1", "buy-baoxian",
            "licai1999", "auto10bagger", "forcode2046", "chiotc88", "ZhangYinyin0903", "Linda20160101");

    private static int count = 1;

    private static boolean stop = false;

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
                log.info("WeixinCrawlerJob.weixinPage request {} failed code={} ", weixinName, response.code());
                return null;
            }
            final ResponseBody body = response.body();
            if ( body == null) {
                log.info("WeixinCrawlerJob.weixinPage {} response body is null", weixinName);
                return null;
            }


            final String content = body.string();

            // 可能网页需要输入验证码
            if (checkValid(content)) {
                log.info("WeixinCrawlerJob.weixinPage check valid need code");
                return null;
            }

            final Document document = Jsoup.parse(content);
            final Elements elements = document.getElementsByAttributeValue("uigs", "account_name_0");
            if (elements != null && elements.size() > 0) {
                final Element element = elements.get(0);
                result = element.attr("href");
            }
        } catch (Exception e) {
            log.error("WeixinCrawlerJob.weixinPage get wei xin page error weixin={}, {}", weixinName, e.getMessage());
        }
        return result;
    }

    // 检测是否需要验证码
    private static boolean checkValid(String pageSource) {
        if (pageSource.contains("请输入验证码") || pageSource.contains("验证码有误") || pageSource.contains("验证码")) {
            log.info("WeixinCrawlerJob.weixinPage 请求太频繁，需要输入验证码");
            stop = true;
            return true;
        }
        return false;
    }

    // 获取每一个文章的 URL
    private static List<Article> extractArticleUrl(String articleListUrl) {
        if (StringUtils.isBlank(articleListUrl)) {
            return Collections.emptyList();
        }
        final Request request = new Request.Builder().url(articleListUrl).build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.info("WeixinCrawlerJob.extractArticleUrl request failed code={} url={}", response.code(), articleListUrl);
                return Collections.emptyList();
            }
            final ResponseBody body = response.body();
            if ( body == null) {
                log.info("WeixinCrawlerJob.extractArticleUrl response body is null url={}", articleListUrl);
                return Collections.emptyList();
            }

            final String content = body.string();

            // 可能网页需要输入验证码
            if (checkValid(content)) {
                return Collections.emptyList();
            }

            final Document document = Jsoup.parse(content);

            String data = null;
            // 页面的中的格式为： var msgList = {...}; {}是内容
            final Pattern pattern = Pattern.compile("\\r\\n.*msgList\\s=\\s(\\{.*});.*");
            final Elements scripts = document.getElementsByTag("script");
            out:
            for (Element script : scripts) {
                for (DataNode dataNode : script.dataNodes()) {
                    final String wholeData = dataNode.getWholeData();
                    final Matcher matcher = pattern.matcher(wholeData);
                    if (matcher.find()) {
                        data = matcher.group(1);
                        break out;
                    }
                }
            }

            if (StringUtils.isBlank(data)) {
                log.info("WeixinCrawlerJob.extractArticleUrl not found msgList url={}", articleListUrl);
                return Collections.emptyList();
            }

            List<Article> articles = new ArrayList<>();
            final JSONObject jsonObject = JSON.parseObject(data);
            final JSONArray list = jsonObject.getJSONArray("list");
            final int size = list.size();
            log.info("WeixinCrawlerJob.extractArticleUrl get {} article url", size);
            for (int i = 0; i < size; i++) {
                final JSONObject articleObj = list.getJSONObject(i);
                if (articleObj != null) {
                    final JSONObject app_msg_ext_info = articleObj.getJSONObject("app_msg_ext_info");
                    final JSONObject comm_msg_info = articleObj.getJSONObject("comm_msg_info");

                    final Article article = new Article();
                    if (app_msg_ext_info != null) {

                        String content_url = app_msg_ext_info.getString("content_url");
                        if (StringUtils.isBlank(content_url)) {
                            continue;
                        }
                        if (!content_url.startsWith("https://mp.weixin.qq.com") || !content_url.startsWith("http://mp.weixin.qq.com")) {
                            content_url = "https://mp.weixin.qq.com" + content_url;
                        }
                        article.contentUrl = StringEscapeUtils.unescapeHtml4(content_url);
                        article.title = app_msg_ext_info.getString("title");
                    }
                    if (comm_msg_info != null) {
                        article.date = comm_msg_info.getLong("datetime");
                    }
                    articles.add(article);
                }
            }
            return articles;
        } catch (Exception e) {
            log.error("WeixinCrawlerJob.extractArticleUrl extract article url articleUrl={} error {}", articleListUrl, e.getMessage());
        }
        return Collections.emptyList();
    }

    // 获取文章标题和内容
    private static Article extractArticleContent(Article article) {
        final Request request = new Request.Builder().url(article.contentUrl).build();
        try (final Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                log.info("WeixinCrawlerJob.extractArticleContent request failed code={} article={}", response.code(), article);
            }
            final ResponseBody body = response.body();
            if (body == null) {
                log.info("WeixinCrawlerJob.extractArticleContent response body is null article={}", article);
                return article;
            }

            final Document document = Jsoup.parse(body.string());
            Element contentElem = document.getElementById("js_content");
            contentElem = replaceImg(contentElem);

            article.content = new HtmlToPlainText().getPlainText(contentElem).replaceAll("\\n+", "\n\n");
        } catch (Exception e) {
            log.error("WeixinCrawlerJob.extractArticleContent extract article content articleUrl={} error {}", article.contentUrl, e.getMessage());
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
                if (StringUtils.isBlank(imgUrl)) {
                    continue;
                }
                parent.prepend("<div>[img]" + imgUrl + "[/img]</div>");
            } catch (Exception e) {
                log.error("WeixinCrawlerJob.replaceImg replace img {} error {}", img, e.getMessage());
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

}
