SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`   datetime              DEFAULT NULL COMMENT '添加时间',
    `update_time`   datetime              DEFAULT NULL COMMENT '更新时间',
    `enabled`       int                   DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
    `weight`        bigint                DEFAULT '0' COMMENT '权重',
    `start_time`    bigint                DEFAULT '0' COMMENT '活动开始时间',
    `end_time`      bigint                DEFAULT '0' COMMENT '活动结束时间',
    `title`         varchar(20)  NOT NULL DEFAULT '' COMMENT '活动标题',
    `bg_url`        varchar(256)          DEFAULT '' COMMENT '活动背景图',
    `batch_no`      varchar(100) NOT NULL DEFAULT '' COMMENT '批次号',
    `activity_type` int          NOT NULL COMMENT '活动类型id',
    `remark`        varchar(255)          DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 56
  DEFAULT CHARSET = utf8mb3 COMMENT ='活动表';


-- ----------------------------
-- Table structure for t_banner
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime              DEFAULT NULL COMMENT '添加时间',
    `update_time` datetime              DEFAULT NULL COMMENT '更新时间',
    `enabled`     int                   DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
    `weight`      bigint                DEFAULT '0' COMMENT '权重',
    `image_url`   longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '图片地址',
    `url`         varchar(100)          DEFAULT NULL COMMENT '跳转地址',
    `batch_no`    varchar(100) NOT NULL DEFAULT '000000' COMMENT '活动批次号,每个活动对应的轮播图',
    `remark`      varchar(255)          DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`),
    INDEX         `batch_no` (`batch_no`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  DEFAULT CHARSET = utf8mb3 COMMENT ='轮播图';

-- ----------------------------
-- Table structure for t_award_winner
-- ----------------------------
DROP TABLE IF EXISTS `t_award_winner`;
CREATE TABLE `t_award_winner`
(
    `id`                  bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`         datetime              DEFAULT NULL COMMENT '添加时间',
    `update_time`         datetime              DEFAULT NULL COMMENT '更新时间',
    `enabled`             int                   DEFAULT '1' COMMENT '是否可见，0为未领取，1为已领取 2：归档',
    `weight`              bigint                DEFAULT '0' COMMENT '权重',
    `batch_no`            varchar(100) NOT NULL DEFAULT '' COMMENT '活动批次号',
    `money`               bigint       NOT NULL DEFAULT '0' COMMENT '金额',
    `lucky_award_type`    int          NOT NULL DEFAULT '0' COMMENT '是否为幸运奖',
    `pu_user_id`          bigint       NOT NULL DEFAULT '0' COMMENT '平台获奖者id',
    `remark`              varchar(255)          DEFAULT '' COMMENT '备注',
    `red_envelope_dtl_id` bigint       NOT NULL DEFAULT '0' COMMENT '红包明细ID',
    `user_phone`          varchar(13)           DEFAULT '' COMMENT '手机号',
    `open_id`             varchar(30)           DEFAULT '' COMMENT 'openId',
    `nick_name`           varchar(255)          DEFAULT '' COMMENT '用户昵称',
    `activity_id`         bigint       NOT NULL DEFAULT '0' COMMENT '活动id',
    `activity_title`      varchar(255)          DEFAULT '' COMMENT '活动名',
    `batch`               varchar(255)          DEFAULT '' COMMENT '当前批次',
    PRIMARY KEY (`id`),
    UNIQUE KEY `un_red_envelop_dtl_id` (`red_envelope_dtl_id`) USING BTREE,
    INDEX                 `pu_user_id` (`pu_user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  DEFAULT CHARSET = utf8mb3 COMMENT ='领取明细表';

-- ----------------------------
-- Table structure for t_red_envelope_ctrl
-- ----------------------------
DROP TABLE IF EXISTS `t_red_envelope_ctrl`;
CREATE TABLE `t_red_envelope_ctrl`
(
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`       datetime              DEFAULT NULL COMMENT '添加时间',
    `update_time`       datetime              DEFAULT NULL COMMENT '更新时间',
    `enabled`           int                   DEFAULT '1' COMMENT '是否可见，0为禁用，1为启用 2：归档 4:删除',
    `weight`            bigint                DEFAULT '0' COMMENT '权重',
    `batch_no`          varchar(100) NOT NULL DEFAULT '' COMMENT '活动批次号',
    `total_money`       bigint       NOT NULL DEFAULT '0' COMMENT '生成总金额',
    `total_sum`         int          NOT NULL DEFAULT '0' COMMENT '生成总数量',
    `type`              int          NOT NULL DEFAULT '0' COMMENT '生成类型（1：固定生成、2:随机生成）',
    `draw_count`        int                   DEFAULT '0' COMMENT '领取人数',
    `draw_money`        bigint                DEFAULT '0' COMMENT '领取金额',
    `create_user_id`    bigint       NOT NULL DEFAULT '0' COMMENT '生成批次管理员id',
    `batch_sum`         int          NOT NULL DEFAULT '1' COMMENT '生成批次数',
    `lucky_award`       int          NOT NULL DEFAULT '0' COMMENT '是否生成幸运奖 0：不设置 1：设置',
    `lucky_award_count` int                   DEFAULT '0' COMMENT '每个批次幸运奖数',
    `lucky_award_money` bigint       NOT NULL DEFAULT '0' COMMENT '幸运奖金额',
    `remark`            varchar(255)          DEFAULT '' COMMENT '备注',
    `cr_qr_code_status` int                   DEFAULT '0' COMMENT '创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败',
    PRIMARY KEY (`id`),
    UNIQUE KEY `batch_no` (`batch_no`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  DEFAULT CHARSET = utf8mb3 COMMENT ='红包批控制表';

-- ----------------------------
-- Table structure for t_red_envelope_dtl
-- ----------------------------
DROP TABLE IF EXISTS `t_red_envelope_dtl`;
CREATE TABLE `t_red_envelope_dtl`
(
    `id`               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`      datetime              DEFAULT NULL COMMENT '添加时间',
    `update_time`      datetime              DEFAULT NULL COMMENT '更新时间',
    `enabled`          int                   DEFAULT '1' COMMENT '是否可见，0为未领取，1为已领取 2：归档',
    `weight`           bigint                DEFAULT '0' COMMENT '权重',
    `batch_no`         varchar(100) NOT NULL DEFAULT '' COMMENT '活动批次号',
    `money`            bigint       NOT NULL DEFAULT '0' COMMENT '金额',
    `lucky_award_type` int          NOT NULL DEFAULT '0' COMMENT '是否为幸运奖',
    `remark`           varchar(255)          DEFAULT '' COMMENT '备注',
    `batch`            int          NOT NULL DEFAULT '0' COMMENT '当前批数',
    `qr_code_url`      varchar(255)          DEFAULT '' COMMENT '二维码地址',
    `qr_code_status`   int          NOT NULL DEFAULT '0' COMMENT '创建二维码状态  0：初始化 1：正在执行 2:创建成功 3：创建失败',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4088
  DEFAULT CHARSET = utf8mb3 COMMENT ='红包明细表';
