package com.example.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaProducer implements Producer{

    private final KafkaTemplate kafkaTemplate;

    private final String topic = "quickstart-events";

    @Autowired
    public MyKafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void send(Order order) {
        ProducerRecord record = new ProducerRecord(topic, order.getId().toString(), JSON.toJSONString(order));
        kafkaTemplate.send(record);
    }

    @Override
    public void close() {

    }

}
