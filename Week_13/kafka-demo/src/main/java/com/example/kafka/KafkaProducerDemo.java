package com.example.kafka;

public class KafkaProducerDemo {

    public static void main(String[] args) {
        testProducer();
    }

    private static void testProducer() {
        ProducerImpl producer = new ProducerImpl();
        for (int i = 0; i < 5; i++) {
            producer.send(new Order(1000L + i,System.currentTimeMillis(),"苹果".concat(String.valueOf(i)), 6.5d));
            producer.send(new Order(2000L + i,System.currentTimeMillis(),"apple", 6.51d));
        }
    }
}
