package com.github.ztmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Author: Mark
 * Date  : 2017/8/15
 */
@Component
public class DataGeneratorService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void doSomething(String message) {
        kafkaTemplate.send("test", message);
    }
}
