package com.github.ztmark.service;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Author: Mark
 * Date  : 2017/8/15
 */
@Component
public class DataConsumerService {

    private CountDownLatch latch = new CountDownLatch(2);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void processMessage(String message) {
        System.out.println("got message : " + message);
        latch.countDown();
    }

    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactoryG1")
    public void processMessageG1(String message) {
        System.out.println("G1 got message : " + message);
        latch.countDown();
    }

}
