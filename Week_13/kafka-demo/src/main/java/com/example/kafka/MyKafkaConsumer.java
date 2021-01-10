package com.example.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumer implements Consumer{


    private final String topic = "quickstart-events";


    @KafkaListener(topics =topic)
    public void receiveMessage (String messge) {
        System.out.println("receiveMessage :"+messge);
    }

    @Override
    public void consumeOrder() {

    }

    @Override
    public void close() {

    }

}
