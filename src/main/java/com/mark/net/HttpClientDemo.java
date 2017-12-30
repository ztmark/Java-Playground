package com.mark.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Author: Mark
 * Date  : 15/12/26.
 */
public class HttpClientDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.github.com/users?since=0");
        try (CloseableHttpResponse response = client.execute(get)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            System.out.println(entity.getContentType());
            Files.copy(entity.getContent(), Paths.get("users.json"));
        }

        testSendPost();

    }

    private static void testSendPost() throws IOException, URISyntaxException {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(60000).setConnectionRequestTimeout(60000).build();
        final PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(200);
        manager.setDefaultMaxPerRoute(20);
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(manager).build();

        final URL url = new URL("http://httpbin.org/post");
        final URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), URLDecoder.decode(url.getPath(), "UTF-8"), "", url.getRef());
        final String urlStr = uri.toURL().toString();

        final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/formpost");
        httpPost.addHeader("user-agent", "unirest-java/1.3.11");
        httpPost.addHeader("accept-encoding", "gzip");

        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("info", new StringBody("Mark", ContentType.APPLICATION_FORM_URLENCODED));
        builder.addPart("getform", new StringBody("POST", ContentType.APPLICATION_FORM_URLENCODED));
        builder.addPart("myfile", new InputStreamBody(new FileInputStream(new File(HttpClientDemo.class.getResource("/image.jpg").toURI())), ContentType.APPLICATION_OCTET_STREAM, "image.jpg"));

        final HttpEntity httpEntity = builder.build();

        httpPost.setEntity(httpEntity);

        final CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response);
        response.getEntity().writeTo(System.out);
        response.close();


    }

}
