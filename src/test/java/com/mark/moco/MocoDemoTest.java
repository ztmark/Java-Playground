package com.mark.moco;

import static com.github.dreamhead.moco.Moco.httpServer;
import static com.github.dreamhead.moco.Runner.running;
import com.github.dreamhead.moco.Runnable;
import com.github.dreamhead.moco.HttpServer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Test;


/**
 * Author: Mark
 * Date  : 2017/9/4
 */
public class MocoDemoTest {

    @Test
    public void should_response_as_expected() throws Exception {
        HttpServer server = httpServer(12306);
        server.response("foo");

        running(server, () -> {
            Content content = Request.Get("http://localhost:12306").execute().returnContent();
            assertThat(content.asString(), is("foo"));
        });
    }

}