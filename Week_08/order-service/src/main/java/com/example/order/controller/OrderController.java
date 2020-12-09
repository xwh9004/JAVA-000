package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.entity.Result;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 13:42 on 2020/12/1
 * @version V0.1
 * @classNmae ProductController
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/save")
    public Result saveOrder(@RequestBody Order order){
        orderService.save(order);
        return Result.buildSuccess();
    }
}
