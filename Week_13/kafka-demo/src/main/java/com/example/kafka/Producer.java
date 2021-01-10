package com.example.kafka;

import com.example.kafka.Order;

public interface Producer {

    void send(Order order);

    void close();

    // add your interface method here

    // and then implement it

}
