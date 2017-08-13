package com.mark.kafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * Author: Mark
 * Date  : 2017/8/13
 */
public class ConsumerDemo {

    public static void main(String[] args) {
        Properties consumerProp = new Properties();
        consumerProp.put("bootstrap.servers", "localhost:9092");
        consumerProp.put("group.id", "test");
        consumerProp.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProp.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProp)) {

            while (true) {
                consumer.subscribe(Collections.singleton("test"));
                final ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
