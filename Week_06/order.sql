CREATE TABLE `t_user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_uuid` varchar(32) DEFAULT NULL COMMENT '用户唯一标识',
  `user_name` varchar(256) NOT NULL COMMENT '用户名称',
  `portrait_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `login_pwd` varchar(256) DEFAULT NULL COMMENT '用户登入密码', 
  `mobile` varchar(32) DEFAULT NULL COMMENT '用户手机号码',
  `status` varchar(20) DEFAULT NULL COMMENT '用户状态',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `user_point` bigint(11) DEFAULT NULL COMMENT '用户积分',
  `user_level` bigint(11) DEFAULT NULL COMMENT '用户等级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';


CREATE TABLE `t_goods` (
  `googs_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_name` varchar(64) NOT NULL COMMENT '商品名称',
  `goods_amount` varchar(64) NOT NULL COMMENT '商品数量',
  `goods_img_url` varchar(255) DEFAULT NULL COMMENT '商品地址',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格', 
  `status` varchar(20) DEFAULT NULL COMMENT '商品状态',
  `goods_desc` varchar(256) DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`googs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';

CREATE TABLE `t_order` (
  `order_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `goods_id` bigint(11) NOT NULL COMMENT '商品id',
  `goods_saled_price` varchar(255) DEFAULT NULL COMMENT '商品下单时的价格',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id', 
  `status` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `order_desc` varchar(256) DEFAULT NULL COMMENT '订单描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `idx_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单信息表';
