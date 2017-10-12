package com.mark.redis;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * Author: Mark
 * Date  : 2017/10/12
 */
public class JedisDemoTest {

    private Jedis jedis;

    @Before
    public void setup() {
        jedis = new Jedis("localhost");
    }

    @Test
    public void testSet() {
        final String set = jedis.set("dd", "be4f8de7-894f-46c9-9c30-a8362b5ab2ed");
        assertThat(set, is("OK"));
    }

    @Test
    public void testEviction() {
        int i = 143;
        while (true) {
            try {
                jedis.set("uuid:" + i, String.valueOf(UUID.randomUUID()));
                i++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("i = " + i);
                break;
            }
        }
    }
}