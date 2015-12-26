package com.mark.net;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: Mark
 * Date  : 15/12/26.
 */
public class HttpClientDemo {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.github.com/users?since=0");
        try (CloseableHttpResponse response = client.execute(get)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            System.out.println(entity.getContentType());
            Files.copy(entity.getContent(), Paths.get("users.json"));
        }
    }

}
