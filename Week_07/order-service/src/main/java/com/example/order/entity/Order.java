package com.example.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:07 on 2020/12/1
 * @version V0.1
 * @classNmae Order
 */
@NoArgsConstructor
@Data
public class Order {

    private int id;
    private String no;
    private int productId;
    private int productAmount;
    private double productUnitPrice;
    private double orderTotalPrice;
    private int userId;
    private int status;
    private String desc;
    private long createTime;
    private long updateTime;
}
