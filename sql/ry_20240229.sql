-- ----------------------------
-- 预约管理
-- ----------------------------
CREATE TABLE `sys_appoint` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `customer_name` varchar(255) DEFAULT NULL COMMENT '客户名字',
  `phone_number` int(11) DEFAULT NULL COMMENT '客户联系方式',
  `date` datetime DEFAULT NULL COMMENT '预约时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(100) DEFAULT NULL COMMENT '预约状态(0，1，2 )待确认、已确认、已取消',
  `Steel_plate_type` varchar(255) DEFAULT NULL COMMENT '钢板类型(下拉框选择)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;