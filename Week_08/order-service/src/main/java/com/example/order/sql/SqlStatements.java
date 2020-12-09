package com.example.order.sql;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:39 on 2020/11/19
 * @version V0.1
 * @classNmae SqlStatements
 */
public interface SqlStatements {

    final String INSERT_SQL = "insert into t_order(order_no,product_id,product_amount,product_unit_price,order_total_price, user_id,status,order_desc,create_time,update_time) values(?,?,?,?,?,?,0,null,?,?)";

    final String SELECT_SQL = "select order_id,order_no,product_id,product_amount,product_unit_price,order_total_price, user_id,status,order_desc,create_time,update_time from t_order where order_id =?";

}
