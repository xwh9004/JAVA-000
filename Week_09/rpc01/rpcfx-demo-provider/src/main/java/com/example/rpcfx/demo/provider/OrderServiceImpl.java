package com.example.rpcfx.demo.provider;

import com.example.rpcfx.demo.api.Order;
import com.example.rpcfx.demo.api.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
