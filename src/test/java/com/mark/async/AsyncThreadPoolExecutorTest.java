package com.mark.async;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Author: Mark
 * Date  : 2017/5/25
 */
public class AsyncThreadPoolExecutorTest {


    @Test
    public void testNormal() throws Exception {
        final AsyncExecutor asyncExecutor = new AsyncThreadPoolExecutor();
        final AsyncResult<String> result = asyncExecutor.start(() -> "what");
        assertThat(result.get(), is("what"));
    }

    @Test(expected = RuntimeException.class)
    public void testException() throws Exception {
        final AsyncExecutor asyncExecutor = new AsyncThreadPoolExecutor();
        final AsyncResult<String> result = asyncExecutor.start(() -> {throw new RuntimeException("oops");});
        result.get();
    }

    @Test(timeout = 3100)
    public void testTimeout() throws Exception {
        final AsyncExecutor asyncExecutor = new AsyncThreadPoolExecutor();
        final AsyncResult<String> result = asyncExecutor.start(() -> {
            TimeUnit.SECONDS.sleep(3);
            return "done";
        });
        assertThat("done", is(result.get()));
    }

    @Test
    public void testCallback() {
        final AsyncExecutor asyncExecutor = new AsyncThreadPoolExecutor();
        final AsyncResult<String> result = asyncExecutor.start(() -> {
            return "done";
        }, (value, e) -> {
            assertNull(e);
            assertThat("done", is(value));
        });
    }

    @Test
    public void testCallbackError() {
        final AsyncExecutor asyncExecutor = new AsyncThreadPoolExecutor();
        final AsyncResult<String> result = asyncExecutor.start(() -> {
            throw new RuntimeException("Oops");
        }, (value, e) -> {
            assertNotNull(e);
            assertNull(value);
        });
    }

}