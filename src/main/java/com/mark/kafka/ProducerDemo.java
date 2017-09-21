package com.mark.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Author: Mark
 * Date  : 2017/8/13
 */
public class ProducerDemo {

    public static void main(String[] args) throws InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "just a message");
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        final KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProperties);

        try {
//            final RecordMetadata recordMetadata = producer.send(record).get();
//            System.out.println(recordMetadata);

            // callback
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.out.println(exception.getMessage());
                } else {
                    System.out.println(metadata);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*final Map<MetricName, ? extends Metric> metrics = producer.metrics();
        for (MetricName metricName : metrics.keySet()) {
            System.out.println(metricName);
            System.out.println(metrics.get(metricName).value());
        }*/

        Thread.sleep(1000L);
    }


}
