-- 菜单
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2113, '营销', 0, 2, 'marketing', NULL, NULL, 1, 0, 'M', '0', '0', '', 'shopping', 'admin', '2023-07-12 02:11:09', 'admin', '2023-07-12 02:12:40', '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2114, '积分日志', 2113, 1, 'marketing/integral', 'marketing/integral/index', NULL, 1, 0, 'C', '0', '0', 'marketing:integral:list', '#', 'admin', '2023-07-12 02:15:54', 'admin', '2023-07-12 02:16:31', '积分日志');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2115, '用户积分记录查询', 2114, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'marketing:integral:query', '#', 'admin', '2023-07-12 02:15:54', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2116, '用户积分记录新增', 2114, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'marketing:integral:add', '#', 'admin', '2023-07-12 02:15:54', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2117, '用户积分记录修改', 2114, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'marketing:integral:edit', '#', 'admin', '2023-07-12 02:15:54', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2118, '用户积分记录删除', 2114, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'marketing:integral:remove', '#', 'admin', '2023-07-12 02:15:54', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2119, '用户积分记录导出', 2114, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'marketing:integral:export', '#', 'admin', '2023-07-12 02:15:54', '', NULL, '');

-- 配置
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (102, '积分设置-开启积分获取功能', 'integral.give', '2', 'Y', 'admin', '2023-07-12 01:03:46', 'admin', '2023-07-12 01:12:46', '1-开启,2-关闭');
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (103, '积分设置-开启积分抵扣功能', 'integral.pay', '2', 'Y', 'admin', '2023-07-12 01:04:42', NULL, NULL, '1-开启,2-关闭');
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (104, '积分设置-下单支付金额按比例赠送积分', 'integral.give.order', '1', 'Y', 'admin', '2023-07-12 01:06:18', NULL, NULL, '实付1元获取1积分');
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (105, '积分设置-注册增送积分', 'integral.give.register', '100', 'Y', 'admin', '2023-07-12 01:07:17', 'admin', '2023-07-12 01:45:29', '上限500');
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (106, '积分设置-下单抵扣比率', 'integral.pay.rate', '0.1', 'Y', 'admin', '2023-07-12 01:08:37', NULL, NULL, '1积分抵多少金额');
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (107, '积分设置-整单可抵扣百分比', 'integral.pay.order', '100', 'Y', 'admin', '2023-07-12 01:10:01', NULL, NULL, '0-100之间数值,可抵扣百分比');

-- 表
CREATE TABLE `psy_user_integral_record` (
                                            `id` bigint NOT NULL COMMENT '主键',
                                            `uid` int NOT NULL DEFAULT '0' COMMENT '用户uid',
                                            `link_id` varchar(80) NOT NULL COMMENT '关联单号',
                                            `link_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '关联类型1-sign,2-order,3-course,4-gauge,0-system',
                                            `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '类型：1-增加，2-扣减',
                                            `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
                                            `integral` int NOT NULL DEFAULT '0' COMMENT '积分',
                                            `balance` int NOT NULL DEFAULT '0' COMMENT '剩余',
                                            `mark` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
                                            `frozen_time` int NOT NULL DEFAULT '0' COMMENT '冻结期时间（天）',
                                            `thaw_time` datetime DEFAULT NULL COMMENT '解冻时间',
                                            `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                                            `status` char(1) DEFAULT '0' COMMENT '状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款）',
                                            `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                            `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `openid` (`uid`) USING BTREE,
                                            KEY `status` (`status`) USING BTREE,
                                            KEY `add_time` (`create_time`) USING BTREE,
                                            KEY `type` (`type`) USING BTREE,
                                            KEY `type_link` (`type`,`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户积分记录表';

--增加字段
alter table psy_user Add column integral int DEFAULT '0' COMMENT '积分余额';