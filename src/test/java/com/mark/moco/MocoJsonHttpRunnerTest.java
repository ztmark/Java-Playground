package com.mark.moco;

import static com.github.dreamhead.moco.Moco.file;
import static com.github.dreamhead.moco.MocoJsonRunner.jsonHttpServer;
import static com.github.dreamhead.moco.Runner.running;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import com.github.dreamhead.moco.HttpServer;

/**
 * Author: Mark
 * Date  : 2017/9/4
 */
public class MocoJsonHttpRunnerTest {

    @Test
    public void should_return_expected_response() throws Exception {
        final HttpServer server = jsonHttpServer(12306, file("foo.json"));
        running(server, () -> {
            Content content = Request.Get("http://localhost:12306").execute().returnContent();
            assertThat(content.asString(), is("foo"));
        });
    }

}
