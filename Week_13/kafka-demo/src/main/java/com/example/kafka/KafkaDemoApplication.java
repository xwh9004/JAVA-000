package com.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(KafkaDemoApplication.class);
        MyKafkaProducer producer = application.getBean(MyKafkaProducer.class);
        for (int i = 0; i < 5; i++) {
            producer.send(new Order(1000L + i,System.currentTimeMillis(),"spring苹果".concat(String.valueOf(i)), 6.5d));
            producer.send(new Order(2000L + i,System.currentTimeMillis(),"spring apple", 6.51d));
        }
    }
}
