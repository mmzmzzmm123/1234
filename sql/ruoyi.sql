/*
 Navicat Premium Data Transfer

 Source Server         : 个人测试服
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 114.132.69.190:3306
 Source Schema         : Onethinker

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 04/11/2023 17:19:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `table_id`          bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_name`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '表名称',
    `table_comment`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '表描述',
    `sub_table_name`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '关联子表的表名',
    `sub_table_fk_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '子表关联的外键名',
    `class_name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '实体类名称',
    `tpl_category`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
    `package_name`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '生成包路径',
    `module_name`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成模块名',
    `business_name`     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成业务名',
    `function_name`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成功能名',
    `function_author`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '生成功能作者',
    `gen_type`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci       NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    `gen_path`          varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    `options`           varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
    `create_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '创建者',
    `create_time`       datetime                                                 NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '更新者',
    `update_time`       datetime                                                 NULL DEFAULT NULL COMMENT '更新时间',
    `remark`            varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '代码生成业务表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table`
VALUES (1, 't_platform_user_integral_history', '平台用户积分流水记录表', NULL, NULL, 'PlatformUserIntegralHistory',
        'crud', 'com.onethinker.onethinker', 'onethinker', 'history', '平台用户积分流水记录', 'yangyouqi', '0', '/', NULL,
        'onethinker', '2023-10-30 22:05:35', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (2, 't_activity', '活动表', NULL, NULL, 'Activity', 'crud', 'com.onethinker.onethinker', 'onethinker', 'activity',
        '活动', 'yangyouqi', '0', '/', NULL, 'onethinker', '2023-11-04 12:17:20', '', NULL, NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`
(
    `column_id`      bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_id`       bigint(20)                                              NULL DEFAULT NULL COMMENT '归属表编号',
    `column_name`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名称',
    `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
    `column_type`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型',
    `java_type`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
    `java_field`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
    `is_pk`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否主键（1是）',
    `is_increment`   char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否自增（1是）',
    `is_required`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否必填（1是）',
    `is_insert`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
    `is_edit`        char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
    `is_list`        char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
    `is_query`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
    `query_type`     varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    `html_type`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    `dict_type`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `sort`           int(11)                                                 NULL DEFAULT NULL COMMENT '排序',
    `create_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 23
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '代码生成业务表字段'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column`
VALUES (1, 1, 'id', '表主键', 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input',
        '', 1, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (2, 1, 'create_time', '添加时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'datetime', '', 2, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (3, 1, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'datetime', '', 3, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (4, 1, 'enabled', '是否可见，0为审核中，1为已处理 2：审核不通过', 'int(11)', 'Long', 'enabled', '0', '0', NULL, '1',
        '1', '1', '1', 'EQ', 'input', '', 4, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (5, 1, 'integral', '积分', 'bigint(20)', 'Long', 'integral', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input',
        '', 5, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (6, 1, 'type', '1:充值 2:活动获得奖励 3：参与活动扣除门槛 4：提现', 'int(11)', 'Long', 'type', '0', '0', '1', '1',
        '1', '1', '1', 'EQ', 'select', '', 6, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (7, 1, 'pu_user_id', '平台用户', 'bigint(20)', 'Long', 'puUserId', '0', '0', '1', '1', '1', '1', '1', 'EQ',
        'input', '', 7, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (8, 1, 'batch_no', '活动批次号', 'varchar(64)', 'String', 'batchNo', '0', '0', '1', '1', '1', '1', '1', 'EQ',
        'input', '', 8, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (9, 1, 'sys_user_id', '审批操作人员', 'varchar(64)', 'String', 'sysUserId', '0', '0', '1', '1', '1', '1', '1',
        'EQ', 'input', '', 9, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (10, 1, 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input',
        '', 10, 'onethinker', '2023-10-30 22:05:35', '', NULL);
INSERT INTO `gen_table_column`
VALUES (11, 2, 'id', '表主键', 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 1, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (12, 2, 'create_time', '添加时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'datetime', '', 2, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (13, 2, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'datetime', '', 3, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (14, 2, 'enabled', '是否可见，0为不可见，1为可见', 'int(11)', 'Long', 'enabled', '0', '0', NULL, '1', '1', '1',
        '1', 'EQ', 'input', '', 4, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (15, 2, 'weight', '权重', 'bigint(20)', 'Long', 'weight', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '',
        5, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (16, 2, 'start_time', '活动开始时间', 'bigint(20)', 'Long', 'startTime', '0', '0', NULL, '1', '1', '1', '1',
        'EQ', 'input', '', 6, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (17, 2, 'end_time', '活动结束时间', 'bigint(20)', 'Long', 'endTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ',
        'input', '', 7, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (18, 2, 'title', '活动标题', 'varchar(20)', 'String', 'title', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input',
        '', 8, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (19, 2, 'bg_url', '活动背景图', 'varchar(256)', 'String', 'bgUrl', '0', '0', NULL, '1', '1', '1', '1', 'EQ',
        'input', '', 9, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (20, 2, 'batch_no', '批次号', 'varchar(100)', 'String', 'batchNo', '0', '0', '1', '1', '1', '1', '1', 'EQ',
        'input', '', 10, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (21, 2, 'activity_type_id', '活动类型id', 'bigint(20)', 'Long', 'activityTypeId', '0', '0', '1', '1', '1', '1',
        '1', 'EQ', 'input', '', 11, 'onethinker', '2023-11-04 12:17:21', '', NULL);
INSERT INTO `gen_table_column`
VALUES (22, 2, 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input',
        '', 12, 'onethinker', '2023-11-04 12:17:21', '', NULL);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `config_id`    int(5)                                                  NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `config_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
    `config_key`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
    `config_value` text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '参数键值',
    `config_type`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    `create_by`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '参数配置表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config`
VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2023-10-22 11:58:41', '',
        NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config`
VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '初始化密码 123456');
INSERT INTO `sys_config`
VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config`
VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config`
VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2023-10-22 11:58:41',
        '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config`
VALUES (6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
INSERT INTO `sys_config`
VALUES (7, '用户注册-默认头像', 'default_avatar_url',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Fae175b96-62b4-4c48-ae36-a278a8a27e2c%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1700712553&t=66ab6321cb2fe9f92f3e8e846d602f54',
        'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '注册默认头像');
INSERT INTO `sys_config`
VALUES (8, '用户注册-默认昵称', 'default_nick_name', '平台用户_', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '注册默认昵称');
INSERT INTO `sys_config`
VALUES (9, '用户注册-默认密码', 'default_password', '123456', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '注册默认密码');
INSERT INTO `sys_config`
VALUES (10, '平台用户账号', 'pu_user_name', 'pu_user', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '平台用户账号');
INSERT INTO `sys_config`
VALUES (11, '平台用户密码', 'pu_user_password', '123456', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL,
        '平台用户密码');
INSERT INTO `sys_config`
VALUES (12, '小程序APPId', 'appId', 'wx233b15667b7c0e12', 'Y', 'admin', '2023-10-22 11:58:41', 'admin',
        '2023-10-28 21:06:50', '小程序APPId');
INSERT INTO `sys_config`
VALUES (13, '小程序APPSECRET', 'appsecret', 'a5fc38d3a4ad2a762041833acb453945', 'Y', 'admin', '2023-10-22 11:58:41',
        'admin', '2023-10-28 21:06:59', '小程序APPSECRET');
INSERT INTO `sys_config`
VALUES (14, '默认上传图片目录', 'detail_file_path', '/onethinker/pic/', 'Y', 'admin', '2023-10-22 11:58:41', 'admin',
        '2023-10-28 21:07:26', '默认图片地址');
INSERT INTO `sys_config`
VALUES (15, '创建二维码私钥', 'qr_code_rsa_private_key',
        'MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHc1DRaBLkptKPCKQ/sbmRHeN0kC6XkkTIHAO07mKqQsZKhIHUkMO+bOOeLsm6a7sQapBJQ/PudAZd+k5b3AKYM9oMs+sONToW/v8Ge24XlsQqRyAjicIaA8BezwbH/AQkJd4utD5Ay2Ec0wqeTqwV49J4M6M+y8EH3ejL2QgYlYfAlXfO8+/2A1HoYTupZKEtN4yY/zciW7VNsw3oF8XDVWsGExpAImXpWUw74q63WTPmQEBXoC4P1nuycEaEjF/rhPhiYnE/uU1GqMlCMAIyMXsyoP/y8BUE61OBXDfUqVwZT22LgKLho1AiQuPqR8ZBDKVHi7Zx0s3rEhtdQ8U9AgMBAAECggEAfMqk/XDdqVzLj5z7j7K2hZqqzyrLTCDDkmUc2J7y8s623LcNuJ8fKk1dGYLyywZO09WpE35F6KOFvFU5o/YDVZHT03DbGFerUeoYTMj7uByn/j/kVvgBj19bhLaJ6qS/PpLG8nfqH4Zg67UMBsL4nvWdoYHFmSvJSg/JcYoIq8AAnZJmCGXMuC4uVoynardjNzxfwcDDNmN477RzO8+jFZiZGuURN0RbYc4HcjEBKTaNxuAYIb8psziEKY5mM+zGMOI/elKBH2vW3ARsO8tkdbuQgvnkUBDd8ypjdQZVr/9TfUf9fV1Vqi1cdYG/SP1L+wpxFnKCgABNqaioJVFygQKBgQDiJ6eBHTQf3uJvrkLDRlgRYGF7L04Zk5CYP+wHl+lq2EaisBFIJkW8eH7/aaads9WRou44QQA/WuSU4OQTu5ER/Wb2hhg7XpvGz26qf4VBA9pjEpIEp9z9qPN522Pi0kVPP7l1FwH9TM9EzJPNOAjEwYuJ+Nawcq84YBn+usQS3QKBgQDhxXycAfsdn8EEXCaC+q1t3WISDIJ97QxO/3JvzUtfuVVGloKagPa/dzMOqCpgn/mveh4GtBGUbbjJeBYPmMKA64YI0wTYrQw64YPGcY72/WgEkxeVNkCo6vpJkQgna3tFikUcXQcgv8S50zhAc7lCXLidFy7iNS/l0UCiJpZl4QKBgQDd4R3b9PTw19G81HE3+Mnqsm6xVuX+/eH9HanKgWZGp04VyzwJV1NP58Bhghwv2QIN0pZ5pnxBrEFbN5FNMtbPmZftAf7AMMAQu+7NZDMuaENYtB2CZ9bPH3VQ9sf2/2l0qQahNCOFilhIHZZc3z7rGyB2CydHEh2K5+5ZFo/MGQKBgHZgD5fUyg+cRZ29m0utl+U04EjKWlkgQwXgleXc5BpKmFWscMoFuFpna1PSBHYpKsdA5iFppJYIeYzY8+g1ljX2R4605aOeB/2PZIJV5DnIYBOV9BV+HqTyn0yXSIkyp62sc9QNSsF3CvTLtIDGrGoJJ5rmHuToMjbzYkoipVTBAoGBAK9Dbo22JlA7pj59rB+i7JI8ZuxijwceSIbcx4RumaVXAKYkMsYN1WCVfPzNbC4QKqpkqAn6g0XxvIUU1QckgSYxEHwC4fLp9zZpdkgJuGQvJpWl5P03I+3ITBmaUaVsmMfej/wMm3KV/CQ0YswjJ3wj77qzcm/1LkFI8qr+44WQ',
        'Y', 'admin', '2023-11-04 17:11:58', 'admin', '2023-11-04 17:12:04', '创建二维码私钥');
INSERT INTO `sys_config`
VALUES (16, '创建二维码公钥', 'qr_code_rsa_public_key',
        'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx3NQ0WgS5KbSjwikP7G5kR3jdJAul5JEyBwDtO5iqkLGSoSB1JDDvmzjni7Jumu7EGqQSUPz7nQGXfpOW9wCmDPaDLPrDjU6Fv7/BntuF5bEKkcgI4nCGgPAXs8Gx/wEJCXeLrQ+QMthHNMKnk6sFePSeDOjPsvBB93oy9kIGJWHwJV3zvPv9gNR6GE7qWShLTeMmP83Ilu1TbMN6BfFw1VrBhMaQCJl6VlMO+Kut1kz5kBAV6AuD9Z7snBGhIxf64T4YmJxP7lNRqjJQjACMjF7MqD/8vAVBOtTgVw31KlcGU9ti4Ci4aNQIkLj6kfGQQylR4u2cdLN6xIbXUPFPQIDAQAB',
        'Y', 'onethinker', '2023-11-04 17:12:54', 'onethinker', '2023-11-04 17:13:15', '创建二维码公钥');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `dept_id`     bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `parent_id`   bigint(20)                                             NULL DEFAULT 0 COMMENT '父部门id',
    `ancestors`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
    `dept_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
    `order_num`   int(4)                                                 NULL DEFAULT 0 COMMENT '显示顺序',
    `leader`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `email`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 112
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '部门表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept`
VALUES (100, 0, '0', '扬万科技', 0, '杨友奇', '', 'yoki1997@qq.com', '0', '0', 'admin', '2023-10-22 11:58:41',
        'onethinker', '2023-10-28 21:53:31');
INSERT INTO `sys_dept`
VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin',
        '2023-10-22 11:58:41', '', NULL);
INSERT INTO `sys_dept`
VALUES (110, 100, '0,100', '平台管理员', 1, '甲一', NULL, NULL, '0', '0', 'onethinker', '2023-10-28 21:54:04', '',
        NULL);
INSERT INTO `sys_dept`
VALUES (111, 100, '0,100', '平台用户', 2, '乙二', NULL, NULL, '0', '0', 'onethinker', '2023-10-28 21:54:37', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    `dict_code`   bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort`   int(4)                                                  NULL DEFAULT 0 COMMENT '字典排序',
    `dict_label`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `css_class`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
    `is_default`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '字典数据表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data`
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '性别男');
INSERT INTO `sys_dict_data`
VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '性别女');
INSERT INTO `sys_dict_data`
VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '性别未知');
INSERT INTO `sys_dict_data`
VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '显示菜单');
INSERT INTO `sys_dict_data`
VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '隐藏菜单');
INSERT INTO `sys_dict_data`
VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '停用状态');
INSERT INTO `sys_dict_data`
VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '停用状态');
INSERT INTO `sys_dict_data`
VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '默认分组');
INSERT INTO `sys_dict_data`
VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '系统分组');
INSERT INTO `sys_dict_data`
VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '系统默认是');
INSERT INTO `sys_dict_data`
VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data`
VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '通知');
INSERT INTO `sys_dict_data`
VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '公告');
INSERT INTO `sys_dict_data`
VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '关闭状态');
INSERT INTO `sys_dict_data`
VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '其他操作');
INSERT INTO `sys_dict_data`
VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '新增操作');
INSERT INTO `sys_dict_data`
VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '修改操作');
INSERT INTO `sys_dict_data`
VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '删除操作');
INSERT INTO `sys_dict_data`
VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '授权操作');
INSERT INTO `sys_dict_data`
VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '导出操作');
INSERT INTO `sys_dict_data`
VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '导入操作');
INSERT INTO `sys_dict_data`
VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '强退操作');
INSERT INTO `sys_dict_data`
VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '生成操作');
INSERT INTO `sys_dict_data`
VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '清空操作');
INSERT INTO `sys_dict_data`
VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '正常状态');
INSERT INTO `sys_dict_data`
VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-10-22 11:58:41', '', NULL,
        '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`
(
    `dict_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
    `dict_type`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_id`) USING BTREE,
    UNIQUE INDEX `dict_type` (`dict_type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '字典类型表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type`
VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type`
VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type`
VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type`
VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type`
VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type`
VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type`
VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type`
VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type`
VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type`
VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '登录状态列表');

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`
(
    `file_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '文件id',
    `file_name`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '文件名称',
    `server_file_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '服务器文件id',
    `suffix_name`    varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL     DEFAULT '' COMMENT '文件后缀名',
    `size`           bigint(20)                                              NULL     DEFAULT 0 COMMENT '文件大小，单位：Byte',
    `create_time`    bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '创建时间',
    `create_user_id` bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '上传人',
    `source`         bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务',
    `file_url`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '0' COMMENT '文件访问地址',
    `file_type`      int(1)                                                  NOT NULL DEFAULT 1 COMMENT '文件类型 1：图片 2：视频',
    PRIMARY KEY (`file_id`) USING BTREE,
    UNIQUE INDEX `server_file_id` (`server_file_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '文件信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`
(
    `job_id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `job_name`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '任务名称',
    `job_group`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    `invoke_target`   varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用目标字符串',
    `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT 'cron执行表达式',
    `misfire_policy`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    `concurrent`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL     DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
    `status`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL     DEFAULT '0' COMMENT '状态（0正常 1暂停）',
    `create_by`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                                NULL     DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `remark`          varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注信息',
    PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '定时任务调度表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job`
VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_job`
VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_job`
VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?',
        '3', '1', '1', 'admin', '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_job`
VALUES (4, '创建红包明细数据', 'DEFAULT', 'redEnvelopeDtlTask.createRedEnvelopDtlTask()', '3 0/5 * * * ?', '1', '0',
        '0', 'onethinker', '2023-11-04 16:10:25', 'onethinker', '2023-11-04 17:08:12', '');
INSERT INTO `sys_job`
VALUES (5, '创建二维码数据', 'DEFAULT', 'redEnvelopeDtlTask.createQrCode()', '7 0/3 * * * ?', '1', '0', '0',
        'onethinker', '2023-11-04 16:11:31', 'onethinker', '2023-11-04 17:15:36', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`
(
    `job_log_id`     bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
    `job_name`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '任务名称',
    `job_group`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '任务组名',
    `invoke_target`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '调用目标字符串',
    `job_message`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '日志信息',
    `status`         char(1) CHARACTER SET utf8 COLLATE utf8_general_ci       NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '异常信息',
    `create_time`    datetime                                                 NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2931
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '定时任务调度日志表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`
(
    `info_id`        bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    `user_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '用户账号',
    `ipaddr`         varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
    `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
    `browser`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '浏览器类型',
    `os`             varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '操作系统',
    `status`         char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
    `msg`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
    `login_time`     datetime                                                NULL DEFAULT NULL COMMENT '访问时间',
    PRIMARY KEY (`info_id`) USING BTREE,
    INDEX `idx_sys_logininfor_s` (`status`) USING BTREE,
    INDEX `idx_sys_logininfor_lt` (`login_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 137
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '系统访问记录'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor`
VALUES (130, 'pu_user', '127.0.0.1', '内网IP', 'Unknown', 'Unknown', '0', '登录成功', '2023-11-04 11:55:27');
INSERT INTO `sys_logininfor`
VALUES (131, 'onethinker', '113.111.9.113', 'XX XX', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-04 11:58:22');
INSERT INTO `sys_logininfor`
VALUES (132, 'pu_user', '127.0.0.1', '内网IP', 'Unknown', 'Unknown', '0', '登录成功', '2023-11-04 11:59:11');
INSERT INTO `sys_logininfor`
VALUES (133, 'onethinker', '113.111.9.113', 'XX XX', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-04 13:03:34');
INSERT INTO `sys_logininfor`
VALUES (134, 'onethinker', '113.111.9.113', 'XX XX', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-04 15:42:06');
INSERT INTO `sys_logininfor`
VALUES (135, 'onethinker', '113.111.9.113', 'XX XX', 'Chrome 11', 'Windows 10', '1', '验证码错误',
        '2023-11-04 16:13:27');
INSERT INTO `sys_logininfor`
VALUES (136, 'onethinker', '113.111.9.113', 'XX XX', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-04 16:13:30');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint(20)                                              NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int(4)                                                  NULL DEFAULT 0 COMMENT '显示顺序',
    `path`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `query`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
    `is_frame`    int(1)                                                  NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    `is_cache`    int(1)                                                  NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type`   char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1087
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '菜单权限表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-10-22 11:58:41', '',
        NULL, '系统管理目录');
INSERT INTO `sys_menu`
VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2023-10-22 11:58:41',
        '', NULL, '系统监控目录');
INSERT INTO `sys_menu`
VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2023-10-22 11:58:41', '',
        NULL, '系统工具目录');
INSERT INTO `sys_menu`
VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user',
        'admin', '2023-10-22 11:58:41', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu`
VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples',
        'admin', '2023-10-22 11:58:41', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu`
VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table',
        'admin', '2023-10-22 11:58:41', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu`
VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree',
        'admin', '2023-10-22 11:58:41', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu`
VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post',
        'admin', '2023-10-22 11:58:41', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu`
VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict',
        'admin', '2023-10-22 11:58:41', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu`
VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit',
        'admin', '2023-10-22 11:58:41', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu`
VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list',
        'message', 'admin', '2023-10-22 11:58:41', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu`
VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2023-10-22 11:58:41', '', NULL,
        '日志管理菜单');
INSERT INTO `sys_menu`
VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list',
        'online', 'admin', '2023-10-22 11:58:41', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu`
VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin',
        '2023-10-22 11:58:41', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu`
VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid',
        'admin', '2023-10-22 11:58:41', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu`
VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list',
        'server', 'admin', '2023-10-22 11:58:41', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu`
VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis',
        'admin', '2023-10-22 11:58:41', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu`
VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',
        'redis-list', 'admin', '2023-10-22 11:58:41', '', NULL, '缓存列表菜单');
INSERT INTO `sys_menu`
VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build',
        'admin', '2023-10-22 11:58:41', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu`
VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin',
        '2023-10-22 11:58:41', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu`
VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger',
        'admin', '2023-10-22 11:58:41', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu`
VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',
        'form', 'admin', '2023-10-22 11:58:41', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu`
VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0',
        'monitor:logininfor:list', 'logininfor', 'admin', '2023-10-22 11:58:41', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu`
VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1016, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1017, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1018, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1019, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1020, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1021, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1022, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1023, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1024, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1035, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1036, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1037, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1038, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1054, '任务导出', 110, 6, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1055, '生成查询', 116, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1056, '生成修改', 116, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1057, '生成删除', 116, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1058, '导入代码', 116, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1059, '预览代码', 116, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1060, '生成代码', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1061, '查询当前用户信息', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'onethinker:user:query', '#', 'admin',
        '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1062, '文件信息查询', 1068, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:file:query', '#', 'admin',
        '2023-10-28 20:50:06', 'admin', '2023-10-28 21:06:07', '');
INSERT INTO `sys_menu`
VALUES (1063, '文件信息新增', 1068, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:file:add', '#', 'admin',
        '2023-10-28 20:50:06', 'admin', '2023-10-28 21:06:03', '');
INSERT INTO `sys_menu`
VALUES (1064, '文件信息修改', 1068, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:file:edit', '#', 'admin',
        '2023-10-28 20:50:06', 'admin', '2023-10-28 21:05:58', '');
INSERT INTO `sys_menu`
VALUES (1065, '文件信息删除', 1068, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:file:remove', '#', 'admin',
        '2023-10-28 20:50:06', 'admin', '2023-10-28 21:05:54', '');
INSERT INTO `sys_menu`
VALUES (1066, '文件信息导出', 1068, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:file:export', '#', 'admin',
        '2023-10-28 20:50:06', 'admin', '2023-10-28 21:05:43', '');
INSERT INTO `sys_menu`
VALUES (1067, '接口权限', 0, 6, '/', NULL, NULL, 1, 0, 'M', '1', '0', '', 'button', 'admin', '2023-10-28 21:04:37',
        'admin', '2023-10-28 21:04:55', '');
INSERT INTO `sys_menu`
VALUES (1068, '文件权限', 1067, 1, '/', NULL, NULL, 1, 0, 'M', '1', '0', NULL, 'button', 'admin', '2023-10-28 21:05:26',
        '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1069, '积分权限', 1067, 2, '/', NULL, NULL, 1, 0, 'M', '1', '0', NULL, 'button', 'admin', '2023-10-28 21:20:32',
        '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1070, '积分添加', 1069, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'onethinker:integral:add', '#', 'admin',
        '2023-10-28 21:20:48', 'admin', '2023-10-28 21:21:01', '');
INSERT INTO `sys_menu`
VALUES (1071, '平台活动', 0, 4, 'onethinker', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'build', 'onethinker',
        '2023-10-28 23:11:06', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1072, '添加积分', 1071, 1, 'integral', 'onethinker/integral/index', NULL, 1, 0, 'C', '0', '0',
        'onethinker:integral:list', 'shopping', 'admin', '2023-10-28 23:12:28', 'onethinker', '2023-10-30 22:30:59',
        '平台用户积分菜单');
INSERT INTO `sys_menu`
VALUES (1073, '提现积分', 1069, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'onethinker:integral:update', 'button',
        'onethinker', '2023-10-29 20:52:45', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1074, '提现审核', 1071, 1, 'history', 'onethinker/history/index', NULL, 1, 0, 'C', '0', '0',
        'onethinker:history:list', 'exit-fullscreen', 'admin', '2023-10-30 22:07:43', 'onethinker',
        '2023-10-30 22:13:54', '平台用户积分流水记录菜单');
INSERT INTO `sys_menu`
VALUES (1075, '平台用户积分流水记录查询', 1074, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:history:query', '#',
        'admin', '2023-10-30 22:07:45', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1076, '平台用户积分流水记录新增', 1074, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:history:add', '#',
        'admin', '2023-10-30 22:07:45', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1077, '平台用户积分流水记录修改', 1074, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:history:edit', '#',
        'admin', '2023-10-30 22:07:46', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1078, '平台用户积分流水记录删除', 1074, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:history:remove', '#',
        'admin', '2023-10-30 22:07:46', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1079, '平台用户积分流水记录导出', 1074, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:history:export', '#',
        'admin', '2023-10-30 22:07:46', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1080, '用户权限', 1067, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'onethinker:user:query', 'build', 'onethinker',
        '2023-11-04 12:16:14', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1081, '活动管理', 1071, 1, 'activity', 'onethinker/activity/index', NULL, 1, 0, 'C', '0', '0',
        'onethinker:activity:list', '#', 'admin', '2023-11-04 12:18:17', 'onethinker', '2023-11-04 12:18:48',
        '活动菜单');
INSERT INTO `sys_menu`
VALUES (1082, '活动查询', 1081, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:activity:query', '#', 'admin',
        '2023-11-04 12:18:17', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1083, '活动新增', 1081, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:activity:add', '#', 'admin',
        '2023-11-04 12:18:17', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1084, '活动修改', 1081, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:activity:edit', '#', 'admin',
        '2023-11-04 12:18:17', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1085, '活动删除', 1081, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:activity:remove', '#', 'admin',
        '2023-11-04 12:18:17', '', NULL, '');
INSERT INTO `sys_menu`
VALUES (1086, '活动导出', 1081, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'onethinker:activity:export', '#', 'admin',
        '2023-11-04 12:18:17', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `notice_id`      int(4)                                                  NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `notice_title`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '公告标题',
    `notice_type`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_content` longblob                                                NULL COMMENT '公告内容',
    `status`         char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    `create_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '通知公告表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice`
VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2023-10-22 11:58:41',
        '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`
(
    `oper_id`        bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '模块标题',
    `business_type`  int(2)                                                   NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '请求方式',
    `operator_type`  int(1)                                                   NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '操作人员',
    `dept_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '' COMMENT '部门名称',
    `oper_url`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '请求URL',
    `oper_ip`        varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '主机地址',
    `oper_location`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '操作地点',
    `oper_param`     varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
    `json_result`    varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
    `status`         int(1)                                                   NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    `error_msg`      varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
    `oper_time`      datetime                                                 NULL DEFAULT NULL COMMENT '操作时间',
    `cost_time`      bigint(20)                                               NULL DEFAULT 0 COMMENT '消耗时间',
    PRIMARY KEY (`oper_id`) USING BTREE,
    INDEX `idx_sys_oper_log_bt` (`business_type`) USING BTREE,
    INDEX `idx_sys_oper_log_s` (`status`) USING BTREE,
    INDEX `idx_sys_oper_log_ot` (`oper_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 216
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '操作日志记录'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `post_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `post_code`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '岗位编码',
    `post_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '岗位名称',
    `post_sort`   int(4)                                                  NOT NULL COMMENT '显示顺序',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '岗位信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post`
VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_post`
VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-10-22 11:58:41', '', NULL, '');
INSERT INTO `sys_post`
VALUES (5, 'pu_user', '平台用户', 5, '0', 'admin', '2023-10-24 17:37:32', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`             bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`           varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色名称',
    `role_key`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort`           int(4)                                                  NOT NULL COMMENT '显示顺序',
    `data_scope`          char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` tinyint(1)                                              NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
    `dept_check_strictly` tinyint(1)                                              NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
    `status`              char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`            char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time`         datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time`         datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`              varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2023-10-22 11:58:41', '', NULL, '超级管理员');
INSERT INTO `sys_role`
VALUES (2, '平台管理员', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2023-10-22 11:58:41', 'onethinker',
        '2023-10-28 21:58:24', '普通角色');
INSERT INTO `sys_role`
VALUES (3, '平台用户角色', 'pu_user', 3, '1', 1, 1, '0', '0', 'admin', '2023-10-24 17:35:36', 'onethinker',
        '2023-10-29 20:52:54', NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色和部门关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept`
VALUES (2, 100);
INSERT INTO `sys_role_dept`
VALUES (2, 101);
INSERT INTO `sys_role_dept`
VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (2, 1);
INSERT INTO `sys_role_menu`
VALUES (2, 2);
INSERT INTO `sys_role_menu`
VALUES (2, 100);
INSERT INTO `sys_role_menu`
VALUES (2, 107);
INSERT INTO `sys_role_menu`
VALUES (2, 108);
INSERT INTO `sys_role_menu`
VALUES (2, 109);
INSERT INTO `sys_role_menu`
VALUES (2, 500);
INSERT INTO `sys_role_menu`
VALUES (2, 501);
INSERT INTO `sys_role_menu`
VALUES (2, 1000);
INSERT INTO `sys_role_menu`
VALUES (2, 1001);
INSERT INTO `sys_role_menu`
VALUES (2, 1002);
INSERT INTO `sys_role_menu`
VALUES (2, 1003);
INSERT INTO `sys_role_menu`
VALUES (2, 1004);
INSERT INTO `sys_role_menu`
VALUES (2, 1005);
INSERT INTO `sys_role_menu`
VALUES (2, 1006);
INSERT INTO `sys_role_menu`
VALUES (2, 1035);
INSERT INTO `sys_role_menu`
VALUES (2, 1036);
INSERT INTO `sys_role_menu`
VALUES (2, 1037);
INSERT INTO `sys_role_menu`
VALUES (2, 1038);
INSERT INTO `sys_role_menu`
VALUES (2, 1039);
INSERT INTO `sys_role_menu`
VALUES (2, 1040);
INSERT INTO `sys_role_menu`
VALUES (2, 1041);
INSERT INTO `sys_role_menu`
VALUES (2, 1042);
INSERT INTO `sys_role_menu`
VALUES (2, 1043);
INSERT INTO `sys_role_menu`
VALUES (2, 1044);
INSERT INTO `sys_role_menu`
VALUES (2, 1045);
INSERT INTO `sys_role_menu`
VALUES (2, 1046);
INSERT INTO `sys_role_menu`
VALUES (2, 1047);
INSERT INTO `sys_role_menu`
VALUES (2, 1048);
INSERT INTO `sys_role_menu`
VALUES (3, 1062);
INSERT INTO `sys_role_menu`
VALUES (3, 1063);
INSERT INTO `sys_role_menu`
VALUES (3, 1064);
INSERT INTO `sys_role_menu`
VALUES (3, 1065);
INSERT INTO `sys_role_menu`
VALUES (3, 1066);
INSERT INTO `sys_role_menu`
VALUES (3, 1067);
INSERT INTO `sys_role_menu`
VALUES (3, 1068);
INSERT INTO `sys_role_menu`
VALUES (3, 1069);
INSERT INTO `sys_role_menu`
VALUES (3, 1070);
INSERT INTO `sys_role_menu`
VALUES (3, 1073);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `dept_id`     bigint(20)                                              NULL DEFAULT NULL COMMENT '部门ID',
    `user_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户账号',
    `nick_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户昵称',
    `user_type`   varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '用户邮箱',
    `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '手机号码',
    `sex`         char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
    `password`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
    `status`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip`    varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
    `login_date`  datetime                                                NULL DEFAULT NULL COMMENT '最后登录时间',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 100, 'onethinker', '超级管理员', '00', '', '18102236625', '0',
        '/profile/avatar/2023/10/28/blob_20231028215539A001.png',
        '$2a$10$yb0lVengPdH.hFjzHjpu8OW6T4Zctr4WI9p3boKwJpU.LEt2CZIq.', '0', '0', '113.111.9.113',
        '2023-11-04 16:13:31', 'admin', '2023-10-28 21:47:52', '', '2023-11-04 16:13:30', NULL);
INSERT INTO `sys_user`
VALUES (3, 111, 'pu_user', '平台用户', '00', '', '', '2', '',
        '$2a$10$ejOA4VT98kvssxUgqIGTyOguJ1SPO/zCS3gx3achiHDOloFC1N9f2', '0', '0', '127.0.0.1', '2023-11-04 11:59:11',
        'admin', '2023-10-24 17:33:49', 'onethinker', '2023-11-04 11:59:11', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post`
VALUES (1, 1);
INSERT INTO `sys_user_post`
VALUES (3, 5);
INSERT INTO `sys_user_post`
VALUES (4, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户和角色关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1);
INSERT INTO `sys_user_role`
VALUES (3, 3);

-- ----------------------------
-- Table structure for t_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity`
(
    `id`            bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`   datetime                                                NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time`   datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `enabled`       int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
    `weight`        bigint(20)                                              NULL     DEFAULT 0 COMMENT '权重',
    `start_time`    bigint(20)                                              NULL     DEFAULT 0 COMMENT '活动开始时间',
    `end_time`      bigint(20)                                              NULL     DEFAULT 0 COMMENT '活动结束时间',
    `title`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '活动标题',
    `bg_url`        varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '活动背景图',
    `batch_no`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '批次号',
    `activity_type` int(11)                                                 NOT NULL COMMENT '活动类型id',
    `remark`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 66
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '活动表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_activity
-- ----------------------------

-- ----------------------------
-- Table structure for t_award_winner
-- ----------------------------
DROP TABLE IF EXISTS `t_award_winner`;
CREATE TABLE `t_award_winner`
(
    `id`                  bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`         datetime                                                NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time`         datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `enabled`             int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为未领取，1为已领取 2：归档',
    `weight`              bigint(20)                                              NULL     DEFAULT 0 COMMENT '权重',
    `batch_no`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '活动批次号',
    `money`               bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '金额',
    `lucky_award_type`    int(11)                                                 NOT NULL DEFAULT 0 COMMENT '是否为幸运奖',
    `pu_user_id`          bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '平台获奖者id',
    `remark`              varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    `red_envelope_dtl_id` bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '红包明细ID',
    `user_phone`          varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT '手机号',
    `open_id`             varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT 'openId',
    `nick_name`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '用户昵称',
    `activity_id`         bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '活动id',
    `activity_title`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '活动名',
    `batch`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '当前批次',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `un_red_envelop_dtl_id` (`red_envelope_dtl_id`) USING BTREE,
    INDEX `pu_user_id` (`pu_user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '领取明细表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_award_winner
-- ----------------------------

-- ----------------------------
-- Table structure for t_banner
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner`
(
    `id`          bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime                                                NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time` datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `enabled`     int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
    `weight`      bigint(20)                                              NULL     DEFAULT 0 COMMENT '权重',
    `image_url`   longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NULL COMMENT '图片地址',
    `url`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '跳转地址',
    `batch_no`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '000000' COMMENT '活动批次号,每个活动对应的轮播图',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `batch_no` (`batch_no`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '轮播图'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_banner
-- ----------------------------

-- ----------------------------
-- Table structure for t_platform_user
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_user`;
CREATE TABLE `t_platform_user`
(
    `id`          bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime                                                NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime                                                NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `enabled`     int(11)                                                 NULL DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
    `weight`      bigint(20)                                              NULL DEFAULT 0 COMMENT '权重',
    `nick_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '电话',
    `avatar_url`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_phone` (`phone`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '平台用户'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_platform_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_platform_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_user_detail`;
CREATE TABLE `t_platform_user_detail`
(
    `id`             bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`    datetime                                                      NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`    datetime                                                      NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `enabled`        int(11)                                                       NULL     DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
    `weight`         bigint(20)                                                    NULL     DEFAULT 0 COMMENT '权重',
    `phone`          varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci        NULL     DEFAULT '' COMMENT '电话',
    `avatar_url`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci       NULL     DEFAULT NULL COMMENT '用户头像',
    `type`           int(11)                                                       NOT NULL DEFAULT 0 COMMENT '用户类型: 1：微信用户',
    `data_id`        varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL DEFAULT '0' COMMENT '抽象id，针对不同平台标识唯一标识',
    `login_end_time` bigint(20)                                                    NOT NULL DEFAULT 0 COMMENT '最后登录时间',
    `remark`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       NULL     DEFAULT '' COMMENT '备注',
    `username`       varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci        NULL     DEFAULT '' COMMENT '用户账号',
    `password`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci        NULL     DEFAULT '' COMMENT '用户密码',
    `nick_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '用户昵称',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `un_data_id` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '平台用户详情信息'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_platform_user_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_platform_user_integral
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_user_integral`;
CREATE TABLE `t_platform_user_integral`
(
    `id`                bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`       datetime                                                NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`       datetime                                                NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `enabled`           int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
    `total_integral`    bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '总积分',
    `residual_integral` bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '剩余积分',
    `pu_user_id`        bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '平台用户',
    `batch_no`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '000000' COMMENT '活动批次号',
    `remark`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '平台用户积分表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_platform_user_integral
-- ----------------------------

-- ----------------------------
-- Table structure for t_platform_user_integral_history
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_user_integral_history`;
CREATE TABLE `t_platform_user_integral_history`
(
    `id`          bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime                                                NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` datetime                                                NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `enabled`     int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为审核中，1为已处理 2：审核不通过',
    `integral`    bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '积分',
    `type`        int(11)                                                 NOT NULL DEFAULT 0 COMMENT '1:充值 2:活动获得奖励 3：参与活动扣除门槛 4：提现',
    `pu_user_id`  bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '平台用户',
    `batch_no`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '000000' COMMENT '活动批次号',
    `sys_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '' COMMENT '审批操作人员',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '平台用户积分流水记录表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_platform_user_integral_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_red_envelope_ctrl
-- ----------------------------
DROP TABLE IF EXISTS `t_red_envelope_ctrl`;
CREATE TABLE `t_red_envelope_ctrl`
(
    `id`                bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`       datetime                                                NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time`       datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `enabled`           int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为禁用，1为启用 2：归档 4:删除',
    `weight`            bigint(20)                                              NULL     DEFAULT 0 COMMENT '权重',
    `batch_no`          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '活动批次号',
    `total_money`       bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '生成总金额',
    `total_sum`         int(11)                                                 NOT NULL DEFAULT 0 COMMENT '生成总数量',
    `type`              int(11)                                                 NOT NULL DEFAULT 0 COMMENT '生成类型（1：固定生成、2:随机生成）',
    `draw_count`        int(11)                                                 NULL     DEFAULT 0 COMMENT '领取人数',
    `draw_money`        bigint(20)                                              NULL     DEFAULT 0 COMMENT '领取金额',
    `create_user_id`    bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '生成批次管理员id',
    `batch_sum`         int(11)                                                 NOT NULL DEFAULT 1 COMMENT '生成批次数',
    `lucky_award`       int(11)                                                 NOT NULL DEFAULT 0 COMMENT '是否生成幸运奖 0：不设置 1：设置',
    `lucky_award_count` int(11)                                                 NULL     DEFAULT 0 COMMENT '每个批次幸运奖数',
    `lucky_award_money` bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '幸运奖金额',
    `remark`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    `status`            int(1)                                                  NULL     DEFAULT 0 COMMENT '当前批次创建明细状态 0：未生成明细数据 1：生成明细数据中 2：已生成明细数据',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `batch_no` (`batch_no`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 60
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '红包批控制表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_red_envelope_ctrl
-- ----------------------------

-- ----------------------------
-- Table structure for t_red_envelope_dtl
-- ----------------------------
DROP TABLE IF EXISTS `t_red_envelope_dtl`;
CREATE TABLE `t_red_envelope_dtl`
(
    `id`               bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`      datetime                                                NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time`      datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `enabled`          int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0为未领取，1为已领取 2：归档',
    `weight`           bigint(20)                                              NULL     DEFAULT 0 COMMENT '权重',
    `batch_no`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '活动批次号',
    `money`            bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '金额',
    `lucky_award_type` int(11)                                                 NOT NULL DEFAULT 0 COMMENT '是否为幸运奖',
    `remark`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    `batch`            int(11)                                                 NOT NULL DEFAULT 0 COMMENT '当前批数',
    `qr_code_url`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '二维码地址',
    `qr_code_status`   int(11)                                                 NOT NULL DEFAULT 0 COMMENT '创建二维码状态  0：初始化 1：正在执行 2:创建成功 3：创建失败',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4098
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '红包明细表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_red_envelope_dtl
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;


DROP TABLE IF EXISTS `t_subscribe_msg_ctrl`;
CREATE TABLE `t_subscribe_msg_ctrl`
(
    `id`                bigint(20) UNSIGNED                                     NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time`       datetime                                                NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time`       datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    `create_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT '创建者',
    `update_by`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT '更新者',
    `enabled`           int(11)                                                 NULL     DEFAULT 1 COMMENT '是否可见，0不可用，1为可用',
    `weight`            bigint(20)                                              NULL     DEFAULT 0 COMMENT '权重',
    `template_id`       varchar(100)                                            NOT NULL DEFAULT '' COMMENT '小程序模版id',
    `page`              varchar(256)                                            NULL     DEFAULT '' COMMENT '模版消息跳转链接',
    `miniprogram_state` varchar(10)                                             NOT NULL DEFAULT '' COMMENT '跳转小程序类型 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版',
    `remark`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '备注',
    `data`              varchar(255)                                            NOT NULL DEFAULT '' COMMENT '模板内容，不填则下发空模板,模版格式化遵循{key:{value:"xxxx"}}格式',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4098
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '订阅消息控制表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `t_subscribe_msg_send_result`;
CREATE TABLE `t_subscribe_msg_send_result`
(
    `id`          bigint(20) UNSIGNED                                    NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime                                               NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '创建者',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '更新者',
    `enabled`     int(11)                                                NULL     DEFAULT 1 COMMENT '是否可见，0发送失败，1发送成功',
    `weight`      bigint(20)                                             NULL     DEFAULT 0 COMMENT '权重',
    `template_id` varchar(100)                                           NOT NULL DEFAULT '' COMMENT '小程序模版id',
    `pu_user_id`  bigint(20)                                             NOT NULL DEFAULT 0 COMMENT '平台用户',
    `send_msg`    varchar(255)                                           NOT NULL DEFAULT '' COMMENT '请求内容',
    `send_result` varchar(256)                                           NOT NULL DEFAULT '' COMMENT '请求结果',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4098
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '订阅消息发送结果'
  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `t_weather_info`;
CREATE TABLE `t_weather_info`
(
    `id`          bigint(20) UNSIGNED                                    NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` datetime                                               NULL     DEFAULT NULL COMMENT '添加时间',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '创建者',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT '' COMMENT '更新者',
    `enabled`     int(11)                                                NULL     DEFAULT 1 COMMENT '是否可见，0发送失败，1发送成功',
    `weight`      bigint(20)                                             NULL     DEFAULT 0 COMMENT '权重',
    `key`         varchar(100)                                           NOT NULL DEFAULT '' COMMENT '请求服务权限标识',
    `city`        varchar(20)                                            NOT NULL DEFAULT '' COMMENT '城市编码',
    `extensions`  varchar(255)                                           NOT NULL DEFAULT '' COMMENT '气象类型',
    `send_result` text COMMENT '请求结果',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '天气预报最新结果'
  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
    `create_time` bigint       DEFAULT '0' COMMENT '添加时间',
    `update_time` bigint       DEFAULT '0' COMMENT '更新时间',
    `enabled`     int          DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
    `weight`      bigint       DEFAULT '0' COMMENT '权重',
    `image_url`   longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '图片地址',
    `url`         varchar(100) DEFAULT NULL COMMENT '跳转地址',
    `remark`      varchar(255) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='轮播图';
