package com.github.ztmark.service;

import java.util.concurrent.TimeUnit;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: Mark
 * Date  : 2017/8/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaMsgServiceTest {


    @Autowired
    private DataGeneratorService generatorService;

    @Autowired
    private DataConsumerService consumerService;

    @ClassRule
    public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1, true, "test");

    @Test
    public void test() {
        generatorService.doSomething("a test msg");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
