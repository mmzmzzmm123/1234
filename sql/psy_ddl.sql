
CREATE TABLE `psy_consultant_management` (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `name` varchar(25) DEFAULT NULL COMMENT '咨询师名称',
                                             `city` varchar(50) DEFAULT NULL COMMENT '所在城市',
                                             `sex` int(11) DEFAULT NULL COMMENT '性别',
                                             `consulting_direction` varchar(50) DEFAULT NULL COMMENT '咨询方向',
                                             `summary` varchar(200) DEFAULT NULL COMMENT '简介',
                                             `consulting_num` int(5) DEFAULT NULL COMMENT '咨询人数',
                                             `service_duration` int(5) DEFAULT NULL COMMENT '服务时长',
                                             `employment_time` datetime DEFAULT NULL COMMENT '从业年限（或者存从业时间）',
                                             `consultation_methods` varchar(50) DEFAULT NULL COMMENT '咨询服务（只存id）',
                                             `introduce` varchar(200) DEFAULT NULL COMMENT '老师介绍',
                                             `head_portrait` varchar(200) DEFAULT NULL COMMENT '头像',
                                             `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
                                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                             `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
                                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='咨询师管理';
CREATE TABLE `psy_consulting_order` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
                                        `consultant_id` int(25) DEFAULT NULL COMMENT '咨询师id',
                                        `type` tinyint(1) DEFAULT NULL COMMENT '0不限 1面对面 2视频 3语音',
                                        `consulting_time` int(10) DEFAULT NULL COMMENT '咨询时长',
                                        `subscribe_time` datetime DEFAULT NULL COMMENT '预约时间',
                                        `price` float DEFAULT NULL COMMENT '应付费用',
                                        `status` tinyint(1) DEFAULT NULL COMMENT '订单状态0待预约1代付款 2代咨询 3已完成',
                                        `order_num` varchar(80) DEFAULT NULL COMMENT '订单编号',
                                        `order_time` datetime DEFAULT NULL COMMENT '下单时间',
                                        `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
                                        `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='咨询订单';
CREATE TABLE `psy_consulting_service` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `consultant_id` int(25) DEFAULT NULL COMMENT '咨询师id',
                                          `service_id` int(50) DEFAULT NULL COMMENT '咨询服务id',
                                          `type` tinyint(1) DEFAULT NULL COMMENT '咨询类型0不限 1面对面 2视频 3语音',
                                          `price` varchar(10) DEFAULT NULL COMMENT '价格',
                                          `describes` varchar(200) DEFAULT NULL COMMENT '描述',
                                          `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
                                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                          `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
                                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='咨询服务';
CREATE TABLE `psy_gauge` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `title` varchar(255) DEFAULT NULL COMMENT '测评标题',
                             `subtitle` varchar(255) DEFAULT NULL COMMENT '子标题',
                             `head_picture` varchar(255) DEFAULT NULL COMMENT '头部图片',
                             `list_show_picture` varchar(255) DEFAULT NULL COMMENT '列表展示图',
                             `introduce` varchar(255) DEFAULT NULL COMMENT '测评介绍',
                             `gauge_class` int(2) DEFAULT NULL COMMENT '测评分类',
                             `gauge_num` int(11) DEFAULT NULL COMMENT '测评题数',
                             `gauge_des` varchar(255) DEFAULT NULL COMMENT '测评简介',
                             `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
                             `num` int(11) DEFAULT NULL COMMENT '测评次数',
                             `type` int(1) DEFAULT NULL COMMENT '测评结果类型 1：普通  2：多维',
                             `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评表';
CREATE TABLE `psy_gauge_banner_config` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `banner_url` varchar(100) NOT NULL COMMENT 'banner图片地址',
                                           `link_url` varchar(100) DEFAULT NULL COMMENT '跳转url',
                                           `banner_type` int(1) NOT NULL COMMENT 'banner分类(0-首页一级banner页，1-首页二级banner页，2-限时福利，3-全面评估)',
                                           `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='测评banner配置表';
CREATE TABLE `psy_gauge_class` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(255) DEFAULT NULL,
                                   `sort` int(11) DEFAULT NULL COMMENT '排序',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评分类表';
CREATE TABLE `psy_gauge_label` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `gauge_id` int(11) NOT NULL COMMENT '量表id',
                                   `label` int(11) NOT NULL COMMENT '量表标签(0-精选测评，1-热门推荐)',
                                   `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='测评标签表';
CREATE TABLE `psy_gauge_multi_setting` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `gauge_id` int(11) NOT NULL COMMENT '测评表单ID',
                                           `name` varchar(255) DEFAULT NULL COMMENT '纬度名',
                                           `question_ids` varchar(255) DEFAULT NULL COMMENT '绑定问题ID',
                                           `score_setting_ids` varchar(255) DEFAULT NULL COMMENT '得分设置ID',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评多维设置表';
CREATE TABLE `psy_gauge_questions` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `gauge_id` int(11) NOT NULL COMMENT '测评表单ID',
                                       `title` varchar(255) DEFAULT NULL,
                                       `select_type` int(1) NOT NULL DEFAULT '0' COMMENT '选择类型(0-单选，1-多选)',
                                       `no` int(11) DEFAULT NULL COMMENT '序号',
                                       `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                       `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评问题表';
CREATE TABLE `psy_gauge_questions_options` (
                                               `id` int(11) NOT NULL AUTO_INCREMENT,
                                               `gauge_questions_id` int(11) NOT NULL COMMENT '测评表单题目ID',
                                               `name` varchar(255) DEFAULT NULL COMMENT '选项名',
                                               `value` int(11) DEFAULT NULL COMMENT '分值',
                                               `img_url` varchar(255) DEFAULT NULL COMMENT '选项图片',
                                               `type` int(1) DEFAULT NULL COMMENT '类型 1、文字 2、图片',
                                               `sort` int(11) DEFAULT NULL COMMENT '排序',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=652 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评问题选项表';
CREATE TABLE `psy_gauge_questions_result` (
                                              `id` int(11) NOT NULL AUTO_INCREMENT,
                                              `user_id` varchar(255) DEFAULT NULL,
                                              `setting_id` int(11) DEFAULT NULL,
                                              `question_id` int(11) DEFAULT NULL,
                                              `option_id` int(11) DEFAULT NULL,
                                              `value` int(11) DEFAULT NULL COMMENT '得分',
                                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                              `order_id` varchar(40) DEFAULT NULL COMMENT '订单id',
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评用户问题明细表';
CREATE TABLE `psy_gauge_score_setting` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `gauge_id` int(11) NOT NULL COMMENT '测评ID',
                                           `start` int(11) DEFAULT NULL COMMENT '得分范围start',
                                           `end` int(11) DEFAULT NULL COMMENT '得分范围end',
                                           `proposal` varchar(255) DEFAULT NULL COMMENT '建议',
                                           `result` varchar(500) DEFAULT NULL COMMENT '结论',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评普通设置表';
CREATE TABLE `psy_order` (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `order_id` varchar(50) NOT NULL COMMENT '订单编号',
                             `gauge_id` int(11) NOT NULL COMMENT '测评编号',
                             `order_status` tinyint(4) NOT NULL COMMENT '订单状态(1-创建,2-完成,3-关闭)',
                             `amount` decimal(10,2) NOT NULL COMMENT '应付金额',
                             `gauge_status` tinyint(4) DEFAULT NULL COMMENT '测评完成情况(1-已完成，2-未完成)',
                             `score` varchar(30) DEFAULT NULL COMMENT '测评结果（多维度，分割）',
                             `result_url` varchar(100) DEFAULT NULL COMMENT '测评结果图片地址',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `create_by` varchar(64) NOT NULL COMMENT '创建人',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `psy_order_order_id_uk` (`order_id`) USING BTREE COMMENT '订单编号'
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理测评订单信息表';
CREATE TABLE `psy_order_pay` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `order_id` int(11) NOT NULL COMMENT '订单号',
                                 `pay_status` int(1) DEFAULT '1' COMMENT '支付状态(1-待支付,2-已支付，3-取消支付)',
                                 `amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
                                 `pay_id` varchar(255) DEFAULT '' COMMENT '支付ID',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `create_by` varchar(64) NOT NULL COMMENT '创建人',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `psy_order_pay_pay_id_uk` (`pay_id`) USING BTREE COMMENT '支付id'
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理咨询订单支付信息表';
CREATE TABLE `psy_user` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                            `name` varchar(255) DEFAULT NULL,
                            `phone` varchar(255) DEFAULT NULL,
                            `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
                            `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
                            `wx_openid` varchar(255) DEFAULT NULL,
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `psy_user_phone_uk` (`phone`) USING BTREE COMMENT '用户手机号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理咨询用户表';
CREATE TABLE `psy_work_management` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `consultant_id` int(25) DEFAULT NULL COMMENT '咨询师id',
                                       `shift` varchar(50) DEFAULT NULL COMMENT '可选班次0全天1上午2下午 3晚上',
                                       `time_interval` int(11) DEFAULT NULL COMMENT '咨询时段',
                                       `time` int(200) DEFAULT NULL COMMENT '咨询时长(分钟)',
                                       `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
                                       `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='排班管理';
CREATE TABLE `psy_appointment` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
                                   `consultant_id` int(11) DEFAULT NULL COMMENT '咨询师ID',
                                   `day` varchar(255) DEFAULT NULL COMMENT '那一天',
                                   `time_interval` varchar(128) DEFAULT NULL COMMENT '时段',
                                   `create_time` datetime DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='咨询师预约表';
CREATE TABLE `psy_consultant` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `name` varchar(255) DEFAULT NULL COMMENT '名字',
                                  `style` varchar(255) DEFAULT NULL COMMENT '风格标签',
                                  `experience` varchar(255) DEFAULT NULL COMMENT '受训经历',
                                  `certificate` varchar(255) DEFAULT NULL COMMENT '证书',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `avatar` varchar(255) DEFAULT '' COMMENT '头像地址',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='心理咨询师表';