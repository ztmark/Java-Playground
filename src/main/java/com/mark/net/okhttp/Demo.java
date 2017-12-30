package com.mark.net.okhttp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: Mark
 * Date  : 2017/12/29
 */
public class Demo {

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    public static void main(String[] args) throws URISyntaxException, IOException {
        final Request request = new Request.Builder().url("http://httpbin.org/headers").header("Name", "Jim").header("Name", "Mark").get().build();
        final Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        } else {
            System.out.println(response.message());
        }
    }

    private static void postFile() throws URISyntaxException, IOException {
        final MediaType mediaType = MediaType.parse("application/octet-stream");
        final File file = new File(Demo.class.getResource("/image.jpg").toURI());
        final RequestBody requestBody = RequestBody.create(mediaType, file);
        final Request post = new Request.Builder().url("http://httpbin.org/post").post(requestBody).build();
        final Response response = client.newCall(post).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        } else {
            System.out.println(response.message());
        }
    }

}
