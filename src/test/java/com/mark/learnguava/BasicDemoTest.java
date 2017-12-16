package com.mark.learnguava;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Author: Mark
 * Date  : 2017/12/16
 */
class BasicDemoTest {



    @Test
    public void test() {
        final ImmutableMap<String, String> map = ImmutableMap.of("1", "one", "2", "two", "3", "three", "4", "four", "5", "five");
        assertThat(map.size(), is(5));
    }

    @Test
    void testFuture() {
        final ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<String> future = listeningExecutorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "done";
        });
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("on success");
                assertThat(result, is("done"));
            }

            @Override
            public void onFailure(Throwable t) {
                Assert.fail();
            }
        });
        try {
            listeningExecutorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testOrdering() {
        final Ordering<String> orderByLen = new Ordering<String>() {
            @Override
            public int compare(@Nullable String left, @Nullable String right) {
                return Objects.requireNonNull(left).length() - Objects.requireNonNull(right).length();
            }
        };
        final boolean ordered = orderByLen.isOrdered(Lists.newArrayList( "are", "you", "what","doing"));
        assertTrue(ordered);
    }
}