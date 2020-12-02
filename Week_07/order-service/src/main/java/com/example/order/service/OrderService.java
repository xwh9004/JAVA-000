package com.example.order.service;

import com.example.order.entity.Order;

public interface OrderService {

    void save(Order order);


    Order query(Integer orderId);
}
