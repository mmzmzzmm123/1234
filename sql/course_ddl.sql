-- psychology.cour_course definition

CREATE TABLE `cour_course` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `course_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程编号',
                               `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
                               `type` tinyint NOT NULL COMMENT '课程类型，取值根据课程类型表',
                               `author` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程作者',
                               `url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程图片',
                               `price` decimal(10,2) DEFAULT NULL COMMENT '课程价格',
                               `detail` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程详情，富文本内容',
                               `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `update_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `cour_course_un` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程';


-- psychology.cour_course_banner_config definition

CREATE TABLE `cour_course_banner_config` (
                                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `banner_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'banner图片地址',
                                             `link_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '跳转url',
                                             `banner_type` tinyint NOT NULL COMMENT 'banner分类(0-首页一级banner页，1-限时福利，2-精选好课)',
                                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程banner配置表';


-- psychology.cour_course_label definition

CREATE TABLE `cour_course_label` (
                                     `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                     `course_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程编号',
                                     `course_label` tinyint NOT NULL COMMENT '课程标签(1-精选好课)',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程标签表';


-- psychology.cour_course_type definition

CREATE TABLE `cour_course_type` (
                                    `type_id` int NOT NULL COMMENT '类型编号',
                                    `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程类型表';


-- psychology.cour_order definition

CREATE TABLE `cour_order` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `order_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号',
                              `create_time` datetime DEFAULT NULL COMMENT '下单时间(创建时间)',
                              `status` tinyint DEFAULT NULL COMMENT '订单状态（0-创建 1-完成 2-关闭）',
                              `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号',
                              `amount` decimal(10,2) DEFAULT NULL COMMENT '应付金额',
                              `course_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程编号',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `cour_order_un` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程订单表';


-- psychology.cour_section definition

CREATE TABLE `cour_section` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `section_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节编号',
                                `topic` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节题目',
                                `duration` int DEFAULT NULL COMMENT '章节时长(单位：秒)',
                                `type` tinyint NOT NULL COMMENT '章节类型（0-普通，1-试听）',
                                `content` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '章节内容，富文本',
                                `content_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '章节内容链接',
                                `course_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程编号',
                                `content_type` tinyint DEFAULT NULL COMMENT '内容类型（0-视频，1-音频）',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='章节';


-- psychology.cour_user_course_section definition

CREATE TABLE `cour_user_course_section` (
                                            `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                            `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号',
                                            `course_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程编号',
                                            `section_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节编号',
                                            `end_time` int DEFAULT NULL COMMENT '上次结束时间（单位：秒）',
                                            `finish_status` tinyint(1) DEFAULT NULL COMMENT '完成状态（0-未完成， 1-已完成）',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户-课程-章节关系表';