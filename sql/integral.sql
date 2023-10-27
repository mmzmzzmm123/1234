DROP TABLE IF EXISTS `t_platform_user_integral`;
CREATE TABLE `t_platform_user`
(
    `id`                bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`       datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`       datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `enabled`           int(11) NULL DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
    `total_integral`    bigint      NOT NULL DEFAULT 0 COMMENT '总积分',
    `residual_integral` bigint      NOT NULL DEFAULT 0 COMMENT '剩余积分',
    `pu_user_id`        bigint      NOT NULL DEFAULT 0 COMMENT '平台用户',
    `batch_no`          varchar(64) NOT NULL DEFAULT '000000' COMMENT '活动批次号',
    `remark`            varchar(255) NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台用户积分表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `t_platform_user_integral_history`;
CREATE TABLE `t_platform_user_integral_history`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `enabled`     int(11) NULL DEFAULT 1 COMMENT '是否可见，0为审核中，1为已处理 2：审核不通过',
    `integral`    bigint      NOT NULL DEFAULT 0 COMMENT '积分',
    `type`        int         NOT NULL DEFAULT 0 COMMENT '1:充值 2:活动获得奖励 3：参与活动扣除门槛 4：提现',
    `pu_user_id`  bigint      NOT NULL DEFAULT 0 COMMENT '平台用户',
    `batch_no`    varchar(64) NOT NULL DEFAULT '000000' COMMENT '活动批次号',
    `sys_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '审批操作人员',
    `remark`      varchar(255) NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台用户积分流水记录表' ROW_FORMAT = Dynamic;