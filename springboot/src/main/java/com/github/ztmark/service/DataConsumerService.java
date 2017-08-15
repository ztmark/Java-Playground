package com.github.ztmark.service;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Author: Mark
 * Date  : 2017/8/15
 */
@Component
@EnableKafka
public class DataConsumerService {

    @KafkaListener(topics = "test")
    public void processMessage(String message) {
        System.out.println("got message : " + message);
    }

}
