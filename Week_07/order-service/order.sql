CREATE TABLE `t_user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_uuid` varchar(64) DEFAULT NULL COMMENT '用户唯一标识',
  `user_name` varchar(16) NOT NULL COMMENT '用户名称',
  `portrait_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `login_pwd` varchar(256) DEFAULT NULL COMMENT '用户登入密码', 
  `mobile` varchar(14) DEFAULT NULL COMMENT '用户手机号码',
  `status` tinyint(1) DEFAULT NULL COMMENT '用户状态',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `user_point` bigint(11) DEFAULT NULL COMMENT '用户积分',
  `user_level` bigint(11) DEFAULT NULL COMMENT '用户等级',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';


CREATE TABLE `t_product` (
  `product_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_amount` int(10) NOT NULL COMMENT '商品数量',
  `product_img_url` varchar(255) DEFAULT NULL COMMENT '商品地址',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `status` tinyint(1) DEFAULT NULL COMMENT '商品状态',
  `product_desc` varchar(256) DEFAULT NULL COMMENT '商品描述',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';

CREATE TABLE `t_order` (
  `order_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `product_id` bigint(11) NOT NULL COMMENT '商品id',
  `product_amount` bigint(11) NOT NULL COMMENT '商品数量',
  `product_unit_price` decimal(10,2) DEFAULT NULL COMMENT '商品下单时的价格',
  `order_total_price` decimal(10,2) DEFAULT NULL COMMENT '订单总价',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id', 
  `status` tinyint(1) DEFAULT NULL COMMENT '订单状态',
  `order_desc` varchar(256) DEFAULT NULL COMMENT '订单描述',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `idx_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单信息表';
