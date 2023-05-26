/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.110.11
 Source Server Type    : MySQL
 Source Server Version : 80024 (8.0.24)
 Source Host           : 192.168.110.11:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 80024 (8.0.24)
 File Encoding         : 65001

 Date: 20/05/2023 18:19:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1, 'stu_data', '资料大全', NULL, NULL, 'StuData', 'crud', 'com.ruoyi.system', 'system', 'data', '资料大全', 'ruoyi', '0', '/', NULL, 'admin', '2023-05-19 11:24:51', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (3, 'stu_tutorial', '教程', NULL, NULL, 'StuTutorial', 'crud', 'com.ruoyi.system', 'system', 'tutorial', '教程', 'ruoyi', '0', '/', NULL, 'admin', '2023-05-19 11:24:51', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (4, 'stu_info', '学生信息', NULL, NULL, 'StuInfo', 'crud', 'com.ruoyi.stu', 'stu', 'info', '学生信息', 'ruoyi', '0', '/', '{\"parentMenuId\":\"2000\"}', 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13', NULL);
INSERT INTO `gen_table` VALUES (5, 'stu_material', '毕业时间提交材料情况表', NULL, NULL, 'StuMaterial', 'crud', 'com.ruoyi.stu', 'stu', 'material', '提交材料参数', 'ruoyi', '0', '/', '{\"parentMenuId\":2000}', 'admin', '2023-05-19 15:21:39', '', '2023-05-19 15:22:26', NULL);
INSERT INTO `gen_table` VALUES (6, 'stu_info_material', '', NULL, NULL, 'StuInfoMaterial', 'crud', 'com.ruoyi.system', 'system', 'material', NULL, 'ruoyi', '0', '/', NULL, 'admin', '2023-05-19 15:46:04', '', NULL, NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (1, '1', 'id', NULL, 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (2, '1', 'name', '资料名', 'varchar(255)', 'String', 'name', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (3, '1', 'link', '链接', 'varchar(255)', 'String', 'link', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (4, '1', 'code', '提取码', 'varchar(255)', 'String', 'code', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (14, '3', 'id', '主键id', 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (15, '3', 'name', '教程名', 'varchar(255)', 'String', 'name', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (16, '3', 'link', '教程链接', 'varchar(255)', 'String', 'link', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (17, '3', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 4, 'admin', '2023-05-19 11:24:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (18, '4', 'stu_id', '主键id', 'int', 'Long', 'stuId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (19, '4', 'stu_no', '学号', 'varchar(255)', 'String', 'stuNo', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (20, '4', 'stu_name', '姓名', 'varchar(255)', 'String', 'stuName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (21, '4', 'stu_cls', '班级', 'varchar(255)', 'String', 'stuCls', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (22, '4', 'stu_cls_year', '年级', 'varchar(50)', 'String', 'stuClsYear', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (23, '4', 'stu_sex', '性别', 'varchar(255)', 'String', 'stuSex', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 6, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (24, '4', 'stu_type', '学生类型', 'varchar(255)', 'String', 'stuType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 7, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (25, '4', 'stu_major', '录取专业', 'varchar(255)', 'String', 'stuMajor', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (26, '4', 'stu_address', '家庭住址', 'varchar(255)', 'String', 'stuAddress', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 9, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (27, '4', 'stu_tel', '联系电话', 'varchar(255)', 'String', 'stuTel', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-05-19 14:29:09', '', '2023-05-19 14:39:13');
INSERT INTO `gen_table_column` VALUES (28, '5', 'id', 'id', 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-05-19 15:21:39', '', '2023-05-19 15:22:26');
INSERT INTO `gen_table_column` VALUES (29, '5', 'name', '材料名', 'varchar(255)', 'String', 'name', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2023-05-19 15:21:39', '', '2023-05-19 15:22:26');
INSERT INTO `gen_table_column` VALUES (30, '5', 'flag', '提交状态', 'int', 'Long', 'flag', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-05-19 15:21:39', '', '2023-05-19 15:22:26');
INSERT INTO `gen_table_column` VALUES (31, '5', 'url', '提交地址', 'varchar(255)', 'String', 'url', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-05-19 15:21:39', '', '2023-05-19 15:22:26');
INSERT INTO `gen_table_column` VALUES (32, '6', 'id', 'id', 'int', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-05-19 15:46:04', '', NULL);
INSERT INTO `gen_table_column` VALUES (33, '6', 'stu_id', '学生id', 'int', 'Long', 'stuId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-05-19 15:46:04', '', NULL);
INSERT INTO `gen_table_column` VALUES (34, '6', 'materual_id', '材料id', 'int', 'Long', 'materualId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-05-19 15:46:04', '', NULL);
INSERT INTO `gen_table_column` VALUES (35, '6', 'flag', '提交状态', 'int', 'Long', 'flag', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-05-19 15:46:04', '', NULL);
INSERT INTO `gen_table_column` VALUES (36, '6', 'url', '提交地址', 'varchar(255)', 'String', 'url', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2023-05-19 15:46:04', '', NULL);

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name` ASC, `job_name` ASC, `job_group` ASC) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for stu_course
-- ----------------------------
DROP TABLE IF EXISTS `stu_course`;
CREATE TABLE `stu_course`  (
  `course_id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程名称',
  `course_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程类别',
  `course_plan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '计划学时',
  `course_exec` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '实践学时',
  `course_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程学分',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_course
-- ----------------------------
INSERT INTO `stu_course` VALUES (1, NULL, '计算机基础', '公共基础课', '44+8', '26', '3');
INSERT INTO `stu_course` VALUES (2, 'F100122', 'PhotoShop', '专业基础课', '48+8', '30', '3');
INSERT INTO `stu_course` VALUES (3, 'F100132', 'HTML网页设计', '专业基础课', '44+8', '28', '3');
INSERT INTO `stu_course` VALUES (4, 'F090012', 'Java程序设计基础', '专业基础课', '64+10', '38', '4');
INSERT INTO `stu_course` VALUES (5, 'F100212', 'MySQL 数据操作与查询', '专业基础课', '40+7', '25', '2.5');
INSERT INTO `stu_course` VALUES (6, 'F100413', 'Java 面向对象程序设计', '专业基础课', '88+16', '48', '5.5');

-- ----------------------------
-- Table structure for stu_course_info
-- ----------------------------
DROP TABLE IF EXISTS `stu_course_info`;
CREATE TABLE `stu_course_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stu_id` int NULL DEFAULT NULL COMMENT '学生id',
  `course_id` int NULL DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1211 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_course_info
-- ----------------------------
INSERT INTO `stu_course_info` VALUES (731, 2, 0);
INSERT INTO `stu_course_info` VALUES (732, 2, 1);
INSERT INTO `stu_course_info` VALUES (733, 2, 2);
INSERT INTO `stu_course_info` VALUES (734, 2, 3);
INSERT INTO `stu_course_info` VALUES (735, 2, 4);
INSERT INTO `stu_course_info` VALUES (736, 2, 5);
INSERT INTO `stu_course_info` VALUES (737, 3, 0);
INSERT INTO `stu_course_info` VALUES (738, 3, 1);
INSERT INTO `stu_course_info` VALUES (739, 3, 2);
INSERT INTO `stu_course_info` VALUES (740, 3, 3);
INSERT INTO `stu_course_info` VALUES (741, 3, 4);
INSERT INTO `stu_course_info` VALUES (742, 3, 5);
INSERT INTO `stu_course_info` VALUES (743, 4, 0);
INSERT INTO `stu_course_info` VALUES (744, 4, 1);
INSERT INTO `stu_course_info` VALUES (745, 4, 2);
INSERT INTO `stu_course_info` VALUES (746, 4, 3);
INSERT INTO `stu_course_info` VALUES (747, 4, 4);
INSERT INTO `stu_course_info` VALUES (748, 4, 5);
INSERT INTO `stu_course_info` VALUES (749, 5, 0);
INSERT INTO `stu_course_info` VALUES (750, 5, 1);
INSERT INTO `stu_course_info` VALUES (751, 5, 2);
INSERT INTO `stu_course_info` VALUES (752, 5, 3);
INSERT INTO `stu_course_info` VALUES (753, 5, 4);
INSERT INTO `stu_course_info` VALUES (754, 5, 5);
INSERT INTO `stu_course_info` VALUES (755, 6, 0);
INSERT INTO `stu_course_info` VALUES (756, 6, 1);
INSERT INTO `stu_course_info` VALUES (757, 6, 2);
INSERT INTO `stu_course_info` VALUES (758, 6, 3);
INSERT INTO `stu_course_info` VALUES (759, 6, 4);
INSERT INTO `stu_course_info` VALUES (760, 6, 5);
INSERT INTO `stu_course_info` VALUES (761, 7, 0);
INSERT INTO `stu_course_info` VALUES (762, 7, 1);
INSERT INTO `stu_course_info` VALUES (763, 7, 2);
INSERT INTO `stu_course_info` VALUES (764, 7, 3);
INSERT INTO `stu_course_info` VALUES (765, 7, 4);
INSERT INTO `stu_course_info` VALUES (766, 7, 5);
INSERT INTO `stu_course_info` VALUES (767, 8, 0);
INSERT INTO `stu_course_info` VALUES (768, 8, 1);
INSERT INTO `stu_course_info` VALUES (769, 8, 2);
INSERT INTO `stu_course_info` VALUES (770, 8, 3);
INSERT INTO `stu_course_info` VALUES (771, 8, 4);
INSERT INTO `stu_course_info` VALUES (772, 8, 5);
INSERT INTO `stu_course_info` VALUES (773, 9, 0);
INSERT INTO `stu_course_info` VALUES (774, 9, 1);
INSERT INTO `stu_course_info` VALUES (775, 9, 2);
INSERT INTO `stu_course_info` VALUES (776, 9, 3);
INSERT INTO `stu_course_info` VALUES (777, 9, 4);
INSERT INTO `stu_course_info` VALUES (778, 9, 5);
INSERT INTO `stu_course_info` VALUES (779, 10, 0);
INSERT INTO `stu_course_info` VALUES (780, 10, 1);
INSERT INTO `stu_course_info` VALUES (781, 10, 2);
INSERT INTO `stu_course_info` VALUES (782, 10, 3);
INSERT INTO `stu_course_info` VALUES (783, 10, 4);
INSERT INTO `stu_course_info` VALUES (784, 10, 5);
INSERT INTO `stu_course_info` VALUES (785, 11, 0);
INSERT INTO `stu_course_info` VALUES (786, 11, 1);
INSERT INTO `stu_course_info` VALUES (787, 11, 2);
INSERT INTO `stu_course_info` VALUES (788, 11, 3);
INSERT INTO `stu_course_info` VALUES (789, 11, 4);
INSERT INTO `stu_course_info` VALUES (790, 11, 5);
INSERT INTO `stu_course_info` VALUES (791, 12, 0);
INSERT INTO `stu_course_info` VALUES (792, 12, 1);
INSERT INTO `stu_course_info` VALUES (793, 12, 2);
INSERT INTO `stu_course_info` VALUES (794, 12, 3);
INSERT INTO `stu_course_info` VALUES (795, 12, 4);
INSERT INTO `stu_course_info` VALUES (796, 12, 5);
INSERT INTO `stu_course_info` VALUES (797, 13, 0);
INSERT INTO `stu_course_info` VALUES (798, 13, 1);
INSERT INTO `stu_course_info` VALUES (799, 13, 2);
INSERT INTO `stu_course_info` VALUES (800, 13, 3);
INSERT INTO `stu_course_info` VALUES (801, 13, 4);
INSERT INTO `stu_course_info` VALUES (802, 13, 5);
INSERT INTO `stu_course_info` VALUES (803, 14, 0);
INSERT INTO `stu_course_info` VALUES (804, 14, 1);
INSERT INTO `stu_course_info` VALUES (805, 14, 2);
INSERT INTO `stu_course_info` VALUES (806, 14, 3);
INSERT INTO `stu_course_info` VALUES (807, 14, 4);
INSERT INTO `stu_course_info` VALUES (808, 14, 5);
INSERT INTO `stu_course_info` VALUES (809, 15, 0);
INSERT INTO `stu_course_info` VALUES (810, 15, 1);
INSERT INTO `stu_course_info` VALUES (811, 15, 2);
INSERT INTO `stu_course_info` VALUES (812, 15, 3);
INSERT INTO `stu_course_info` VALUES (813, 15, 4);
INSERT INTO `stu_course_info` VALUES (814, 15, 5);
INSERT INTO `stu_course_info` VALUES (815, 16, 0);
INSERT INTO `stu_course_info` VALUES (816, 16, 1);
INSERT INTO `stu_course_info` VALUES (817, 16, 2);
INSERT INTO `stu_course_info` VALUES (818, 16, 3);
INSERT INTO `stu_course_info` VALUES (819, 16, 4);
INSERT INTO `stu_course_info` VALUES (820, 16, 5);
INSERT INTO `stu_course_info` VALUES (821, 17, 0);
INSERT INTO `stu_course_info` VALUES (822, 17, 1);
INSERT INTO `stu_course_info` VALUES (823, 17, 2);
INSERT INTO `stu_course_info` VALUES (824, 17, 3);
INSERT INTO `stu_course_info` VALUES (825, 17, 4);
INSERT INTO `stu_course_info` VALUES (826, 17, 5);
INSERT INTO `stu_course_info` VALUES (827, 18, 0);
INSERT INTO `stu_course_info` VALUES (828, 18, 1);
INSERT INTO `stu_course_info` VALUES (829, 18, 2);
INSERT INTO `stu_course_info` VALUES (830, 18, 3);
INSERT INTO `stu_course_info` VALUES (831, 18, 4);
INSERT INTO `stu_course_info` VALUES (832, 18, 5);
INSERT INTO `stu_course_info` VALUES (833, 19, 0);
INSERT INTO `stu_course_info` VALUES (834, 19, 1);
INSERT INTO `stu_course_info` VALUES (835, 19, 2);
INSERT INTO `stu_course_info` VALUES (836, 19, 3);
INSERT INTO `stu_course_info` VALUES (837, 19, 4);
INSERT INTO `stu_course_info` VALUES (838, 19, 5);
INSERT INTO `stu_course_info` VALUES (839, 20, 0);
INSERT INTO `stu_course_info` VALUES (840, 20, 1);
INSERT INTO `stu_course_info` VALUES (841, 20, 2);
INSERT INTO `stu_course_info` VALUES (842, 20, 3);
INSERT INTO `stu_course_info` VALUES (843, 20, 4);
INSERT INTO `stu_course_info` VALUES (844, 20, 5);
INSERT INTO `stu_course_info` VALUES (845, 21, 0);
INSERT INTO `stu_course_info` VALUES (846, 21, 1);
INSERT INTO `stu_course_info` VALUES (847, 21, 2);
INSERT INTO `stu_course_info` VALUES (848, 21, 3);
INSERT INTO `stu_course_info` VALUES (849, 21, 4);
INSERT INTO `stu_course_info` VALUES (850, 21, 5);
INSERT INTO `stu_course_info` VALUES (851, 22, 0);
INSERT INTO `stu_course_info` VALUES (852, 22, 1);
INSERT INTO `stu_course_info` VALUES (853, 22, 2);
INSERT INTO `stu_course_info` VALUES (854, 22, 3);
INSERT INTO `stu_course_info` VALUES (855, 22, 4);
INSERT INTO `stu_course_info` VALUES (856, 22, 5);
INSERT INTO `stu_course_info` VALUES (857, 23, 0);
INSERT INTO `stu_course_info` VALUES (858, 23, 1);
INSERT INTO `stu_course_info` VALUES (859, 23, 2);
INSERT INTO `stu_course_info` VALUES (860, 23, 3);
INSERT INTO `stu_course_info` VALUES (861, 23, 4);
INSERT INTO `stu_course_info` VALUES (862, 23, 5);
INSERT INTO `stu_course_info` VALUES (863, 24, 0);
INSERT INTO `stu_course_info` VALUES (864, 24, 1);
INSERT INTO `stu_course_info` VALUES (865, 24, 2);
INSERT INTO `stu_course_info` VALUES (866, 24, 3);
INSERT INTO `stu_course_info` VALUES (867, 24, 4);
INSERT INTO `stu_course_info` VALUES (868, 24, 5);
INSERT INTO `stu_course_info` VALUES (869, 25, 0);
INSERT INTO `stu_course_info` VALUES (870, 25, 1);
INSERT INTO `stu_course_info` VALUES (871, 25, 2);
INSERT INTO `stu_course_info` VALUES (872, 25, 3);
INSERT INTO `stu_course_info` VALUES (873, 25, 4);
INSERT INTO `stu_course_info` VALUES (874, 25, 5);
INSERT INTO `stu_course_info` VALUES (875, 26, 0);
INSERT INTO `stu_course_info` VALUES (876, 26, 1);
INSERT INTO `stu_course_info` VALUES (877, 26, 2);
INSERT INTO `stu_course_info` VALUES (878, 26, 3);
INSERT INTO `stu_course_info` VALUES (879, 26, 4);
INSERT INTO `stu_course_info` VALUES (880, 26, 5);
INSERT INTO `stu_course_info` VALUES (881, 27, 0);
INSERT INTO `stu_course_info` VALUES (882, 27, 1);
INSERT INTO `stu_course_info` VALUES (883, 27, 2);
INSERT INTO `stu_course_info` VALUES (884, 27, 3);
INSERT INTO `stu_course_info` VALUES (885, 27, 4);
INSERT INTO `stu_course_info` VALUES (886, 27, 5);
INSERT INTO `stu_course_info` VALUES (887, 28, 0);
INSERT INTO `stu_course_info` VALUES (888, 28, 1);
INSERT INTO `stu_course_info` VALUES (889, 28, 2);
INSERT INTO `stu_course_info` VALUES (890, 28, 3);
INSERT INTO `stu_course_info` VALUES (891, 28, 4);
INSERT INTO `stu_course_info` VALUES (892, 28, 5);
INSERT INTO `stu_course_info` VALUES (893, 29, 0);
INSERT INTO `stu_course_info` VALUES (894, 29, 1);
INSERT INTO `stu_course_info` VALUES (895, 29, 2);
INSERT INTO `stu_course_info` VALUES (896, 29, 3);
INSERT INTO `stu_course_info` VALUES (897, 29, 4);
INSERT INTO `stu_course_info` VALUES (898, 29, 5);
INSERT INTO `stu_course_info` VALUES (899, 30, 0);
INSERT INTO `stu_course_info` VALUES (900, 30, 1);
INSERT INTO `stu_course_info` VALUES (901, 30, 2);
INSERT INTO `stu_course_info` VALUES (902, 30, 3);
INSERT INTO `stu_course_info` VALUES (903, 30, 4);
INSERT INTO `stu_course_info` VALUES (904, 30, 5);
INSERT INTO `stu_course_info` VALUES (905, 31, 0);
INSERT INTO `stu_course_info` VALUES (906, 31, 1);
INSERT INTO `stu_course_info` VALUES (907, 31, 2);
INSERT INTO `stu_course_info` VALUES (908, 31, 3);
INSERT INTO `stu_course_info` VALUES (909, 31, 4);
INSERT INTO `stu_course_info` VALUES (910, 31, 5);
INSERT INTO `stu_course_info` VALUES (911, 32, 0);
INSERT INTO `stu_course_info` VALUES (912, 32, 1);
INSERT INTO `stu_course_info` VALUES (913, 32, 2);
INSERT INTO `stu_course_info` VALUES (914, 32, 3);
INSERT INTO `stu_course_info` VALUES (915, 32, 4);
INSERT INTO `stu_course_info` VALUES (916, 32, 5);
INSERT INTO `stu_course_info` VALUES (917, 33, 0);
INSERT INTO `stu_course_info` VALUES (918, 33, 1);
INSERT INTO `stu_course_info` VALUES (919, 33, 2);
INSERT INTO `stu_course_info` VALUES (920, 33, 3);
INSERT INTO `stu_course_info` VALUES (921, 33, 4);
INSERT INTO `stu_course_info` VALUES (922, 33, 5);
INSERT INTO `stu_course_info` VALUES (923, 34, 0);
INSERT INTO `stu_course_info` VALUES (924, 34, 1);
INSERT INTO `stu_course_info` VALUES (925, 34, 2);
INSERT INTO `stu_course_info` VALUES (926, 34, 3);
INSERT INTO `stu_course_info` VALUES (927, 34, 4);
INSERT INTO `stu_course_info` VALUES (928, 34, 5);
INSERT INTO `stu_course_info` VALUES (929, 35, 0);
INSERT INTO `stu_course_info` VALUES (930, 35, 1);
INSERT INTO `stu_course_info` VALUES (931, 35, 2);
INSERT INTO `stu_course_info` VALUES (932, 35, 3);
INSERT INTO `stu_course_info` VALUES (933, 35, 4);
INSERT INTO `stu_course_info` VALUES (934, 35, 5);
INSERT INTO `stu_course_info` VALUES (935, 36, 0);
INSERT INTO `stu_course_info` VALUES (936, 36, 1);
INSERT INTO `stu_course_info` VALUES (937, 36, 2);
INSERT INTO `stu_course_info` VALUES (938, 36, 3);
INSERT INTO `stu_course_info` VALUES (939, 36, 4);
INSERT INTO `stu_course_info` VALUES (940, 36, 5);
INSERT INTO `stu_course_info` VALUES (941, 37, 0);
INSERT INTO `stu_course_info` VALUES (942, 37, 1);
INSERT INTO `stu_course_info` VALUES (943, 37, 2);
INSERT INTO `stu_course_info` VALUES (944, 37, 3);
INSERT INTO `stu_course_info` VALUES (945, 37, 4);
INSERT INTO `stu_course_info` VALUES (946, 37, 5);
INSERT INTO `stu_course_info` VALUES (947, 38, 0);
INSERT INTO `stu_course_info` VALUES (948, 38, 1);
INSERT INTO `stu_course_info` VALUES (949, 38, 2);
INSERT INTO `stu_course_info` VALUES (950, 38, 3);
INSERT INTO `stu_course_info` VALUES (951, 38, 4);
INSERT INTO `stu_course_info` VALUES (952, 38, 5);
INSERT INTO `stu_course_info` VALUES (953, 39, 0);
INSERT INTO `stu_course_info` VALUES (954, 39, 1);
INSERT INTO `stu_course_info` VALUES (955, 39, 2);
INSERT INTO `stu_course_info` VALUES (956, 39, 3);
INSERT INTO `stu_course_info` VALUES (957, 39, 4);
INSERT INTO `stu_course_info` VALUES (958, 39, 5);
INSERT INTO `stu_course_info` VALUES (959, 40, 0);
INSERT INTO `stu_course_info` VALUES (960, 40, 1);
INSERT INTO `stu_course_info` VALUES (961, 40, 2);
INSERT INTO `stu_course_info` VALUES (962, 40, 3);
INSERT INTO `stu_course_info` VALUES (963, 40, 4);
INSERT INTO `stu_course_info` VALUES (964, 40, 5);
INSERT INTO `stu_course_info` VALUES (965, 41, 0);
INSERT INTO `stu_course_info` VALUES (966, 41, 1);
INSERT INTO `stu_course_info` VALUES (967, 41, 2);
INSERT INTO `stu_course_info` VALUES (968, 41, 3);
INSERT INTO `stu_course_info` VALUES (969, 41, 4);
INSERT INTO `stu_course_info` VALUES (970, 41, 5);
INSERT INTO `stu_course_info` VALUES (971, 46, 0);
INSERT INTO `stu_course_info` VALUES (972, 46, 1);
INSERT INTO `stu_course_info` VALUES (973, 46, 2);
INSERT INTO `stu_course_info` VALUES (974, 46, 3);
INSERT INTO `stu_course_info` VALUES (975, 46, 4);
INSERT INTO `stu_course_info` VALUES (976, 46, 5);
INSERT INTO `stu_course_info` VALUES (977, 47, 0);
INSERT INTO `stu_course_info` VALUES (978, 47, 1);
INSERT INTO `stu_course_info` VALUES (979, 47, 2);
INSERT INTO `stu_course_info` VALUES (980, 47, 3);
INSERT INTO `stu_course_info` VALUES (981, 47, 4);
INSERT INTO `stu_course_info` VALUES (982, 47, 5);
INSERT INTO `stu_course_info` VALUES (983, 48, 0);
INSERT INTO `stu_course_info` VALUES (984, 48, 1);
INSERT INTO `stu_course_info` VALUES (985, 48, 2);
INSERT INTO `stu_course_info` VALUES (986, 48, 3);
INSERT INTO `stu_course_info` VALUES (987, 48, 4);
INSERT INTO `stu_course_info` VALUES (988, 48, 5);
INSERT INTO `stu_course_info` VALUES (989, 49, 0);
INSERT INTO `stu_course_info` VALUES (990, 49, 1);
INSERT INTO `stu_course_info` VALUES (991, 49, 2);
INSERT INTO `stu_course_info` VALUES (992, 49, 3);
INSERT INTO `stu_course_info` VALUES (993, 49, 4);
INSERT INTO `stu_course_info` VALUES (994, 49, 5);
INSERT INTO `stu_course_info` VALUES (995, 50, 0);
INSERT INTO `stu_course_info` VALUES (996, 50, 1);
INSERT INTO `stu_course_info` VALUES (997, 50, 2);
INSERT INTO `stu_course_info` VALUES (998, 50, 3);
INSERT INTO `stu_course_info` VALUES (999, 50, 4);
INSERT INTO `stu_course_info` VALUES (1000, 50, 5);
INSERT INTO `stu_course_info` VALUES (1001, 51, 0);
INSERT INTO `stu_course_info` VALUES (1002, 51, 1);
INSERT INTO `stu_course_info` VALUES (1003, 51, 2);
INSERT INTO `stu_course_info` VALUES (1004, 51, 3);
INSERT INTO `stu_course_info` VALUES (1005, 51, 4);
INSERT INTO `stu_course_info` VALUES (1006, 51, 5);
INSERT INTO `stu_course_info` VALUES (1007, 52, 0);
INSERT INTO `stu_course_info` VALUES (1008, 52, 1);
INSERT INTO `stu_course_info` VALUES (1009, 52, 2);
INSERT INTO `stu_course_info` VALUES (1010, 52, 3);
INSERT INTO `stu_course_info` VALUES (1011, 52, 4);
INSERT INTO `stu_course_info` VALUES (1012, 52, 5);
INSERT INTO `stu_course_info` VALUES (1013, 53, 0);
INSERT INTO `stu_course_info` VALUES (1014, 53, 1);
INSERT INTO `stu_course_info` VALUES (1015, 53, 2);
INSERT INTO `stu_course_info` VALUES (1016, 53, 3);
INSERT INTO `stu_course_info` VALUES (1017, 53, 4);
INSERT INTO `stu_course_info` VALUES (1018, 53, 5);
INSERT INTO `stu_course_info` VALUES (1019, 54, 0);
INSERT INTO `stu_course_info` VALUES (1020, 54, 1);
INSERT INTO `stu_course_info` VALUES (1021, 54, 2);
INSERT INTO `stu_course_info` VALUES (1022, 54, 3);
INSERT INTO `stu_course_info` VALUES (1023, 54, 4);
INSERT INTO `stu_course_info` VALUES (1024, 54, 5);
INSERT INTO `stu_course_info` VALUES (1025, 55, 0);
INSERT INTO `stu_course_info` VALUES (1026, 55, 1);
INSERT INTO `stu_course_info` VALUES (1027, 55, 2);
INSERT INTO `stu_course_info` VALUES (1028, 55, 3);
INSERT INTO `stu_course_info` VALUES (1029, 55, 4);
INSERT INTO `stu_course_info` VALUES (1030, 55, 5);
INSERT INTO `stu_course_info` VALUES (1031, 56, 0);
INSERT INTO `stu_course_info` VALUES (1032, 56, 1);
INSERT INTO `stu_course_info` VALUES (1033, 56, 2);
INSERT INTO `stu_course_info` VALUES (1034, 56, 3);
INSERT INTO `stu_course_info` VALUES (1035, 56, 4);
INSERT INTO `stu_course_info` VALUES (1036, 56, 5);
INSERT INTO `stu_course_info` VALUES (1037, 57, 0);
INSERT INTO `stu_course_info` VALUES (1038, 57, 1);
INSERT INTO `stu_course_info` VALUES (1039, 57, 2);
INSERT INTO `stu_course_info` VALUES (1040, 57, 3);
INSERT INTO `stu_course_info` VALUES (1041, 57, 4);
INSERT INTO `stu_course_info` VALUES (1042, 57, 5);
INSERT INTO `stu_course_info` VALUES (1043, 58, 0);
INSERT INTO `stu_course_info` VALUES (1044, 58, 1);
INSERT INTO `stu_course_info` VALUES (1045, 58, 2);
INSERT INTO `stu_course_info` VALUES (1046, 58, 3);
INSERT INTO `stu_course_info` VALUES (1047, 58, 4);
INSERT INTO `stu_course_info` VALUES (1048, 58, 5);
INSERT INTO `stu_course_info` VALUES (1049, 59, 0);
INSERT INTO `stu_course_info` VALUES (1050, 59, 1);
INSERT INTO `stu_course_info` VALUES (1051, 59, 2);
INSERT INTO `stu_course_info` VALUES (1052, 59, 3);
INSERT INTO `stu_course_info` VALUES (1053, 59, 4);
INSERT INTO `stu_course_info` VALUES (1054, 59, 5);
INSERT INTO `stu_course_info` VALUES (1055, 60, 0);
INSERT INTO `stu_course_info` VALUES (1056, 60, 1);
INSERT INTO `stu_course_info` VALUES (1057, 60, 2);
INSERT INTO `stu_course_info` VALUES (1058, 60, 3);
INSERT INTO `stu_course_info` VALUES (1059, 60, 4);
INSERT INTO `stu_course_info` VALUES (1060, 60, 5);
INSERT INTO `stu_course_info` VALUES (1061, 61, 0);
INSERT INTO `stu_course_info` VALUES (1062, 61, 1);
INSERT INTO `stu_course_info` VALUES (1063, 61, 2);
INSERT INTO `stu_course_info` VALUES (1064, 61, 3);
INSERT INTO `stu_course_info` VALUES (1065, 61, 4);
INSERT INTO `stu_course_info` VALUES (1066, 61, 5);
INSERT INTO `stu_course_info` VALUES (1067, 62, 0);
INSERT INTO `stu_course_info` VALUES (1068, 62, 1);
INSERT INTO `stu_course_info` VALUES (1069, 62, 2);
INSERT INTO `stu_course_info` VALUES (1070, 62, 3);
INSERT INTO `stu_course_info` VALUES (1071, 62, 4);
INSERT INTO `stu_course_info` VALUES (1072, 62, 5);
INSERT INTO `stu_course_info` VALUES (1073, 63, 0);
INSERT INTO `stu_course_info` VALUES (1074, 63, 1);
INSERT INTO `stu_course_info` VALUES (1075, 63, 2);
INSERT INTO `stu_course_info` VALUES (1076, 63, 3);
INSERT INTO `stu_course_info` VALUES (1077, 63, 4);
INSERT INTO `stu_course_info` VALUES (1078, 63, 5);
INSERT INTO `stu_course_info` VALUES (1079, 64, 0);
INSERT INTO `stu_course_info` VALUES (1080, 64, 1);
INSERT INTO `stu_course_info` VALUES (1081, 64, 2);
INSERT INTO `stu_course_info` VALUES (1082, 64, 3);
INSERT INTO `stu_course_info` VALUES (1083, 64, 4);
INSERT INTO `stu_course_info` VALUES (1084, 64, 5);
INSERT INTO `stu_course_info` VALUES (1085, 65, 0);
INSERT INTO `stu_course_info` VALUES (1086, 65, 1);
INSERT INTO `stu_course_info` VALUES (1087, 65, 2);
INSERT INTO `stu_course_info` VALUES (1088, 65, 3);
INSERT INTO `stu_course_info` VALUES (1089, 65, 4);
INSERT INTO `stu_course_info` VALUES (1090, 65, 5);
INSERT INTO `stu_course_info` VALUES (1091, 66, 0);
INSERT INTO `stu_course_info` VALUES (1092, 66, 1);
INSERT INTO `stu_course_info` VALUES (1093, 66, 2);
INSERT INTO `stu_course_info` VALUES (1094, 66, 3);
INSERT INTO `stu_course_info` VALUES (1095, 66, 4);
INSERT INTO `stu_course_info` VALUES (1096, 66, 5);
INSERT INTO `stu_course_info` VALUES (1097, 67, 0);
INSERT INTO `stu_course_info` VALUES (1098, 67, 1);
INSERT INTO `stu_course_info` VALUES (1099, 67, 2);
INSERT INTO `stu_course_info` VALUES (1100, 67, 3);
INSERT INTO `stu_course_info` VALUES (1101, 67, 4);
INSERT INTO `stu_course_info` VALUES (1102, 67, 5);
INSERT INTO `stu_course_info` VALUES (1103, 68, 0);
INSERT INTO `stu_course_info` VALUES (1104, 68, 1);
INSERT INTO `stu_course_info` VALUES (1105, 68, 2);
INSERT INTO `stu_course_info` VALUES (1106, 68, 3);
INSERT INTO `stu_course_info` VALUES (1107, 68, 4);
INSERT INTO `stu_course_info` VALUES (1108, 68, 5);
INSERT INTO `stu_course_info` VALUES (1109, 69, 0);
INSERT INTO `stu_course_info` VALUES (1110, 69, 1);
INSERT INTO `stu_course_info` VALUES (1111, 69, 2);
INSERT INTO `stu_course_info` VALUES (1112, 69, 3);
INSERT INTO `stu_course_info` VALUES (1113, 69, 4);
INSERT INTO `stu_course_info` VALUES (1114, 69, 5);
INSERT INTO `stu_course_info` VALUES (1115, 70, 0);
INSERT INTO `stu_course_info` VALUES (1116, 70, 1);
INSERT INTO `stu_course_info` VALUES (1117, 70, 2);
INSERT INTO `stu_course_info` VALUES (1118, 70, 3);
INSERT INTO `stu_course_info` VALUES (1119, 70, 4);
INSERT INTO `stu_course_info` VALUES (1120, 70, 5);
INSERT INTO `stu_course_info` VALUES (1121, 71, 0);
INSERT INTO `stu_course_info` VALUES (1122, 71, 1);
INSERT INTO `stu_course_info` VALUES (1123, 71, 2);
INSERT INTO `stu_course_info` VALUES (1124, 71, 3);
INSERT INTO `stu_course_info` VALUES (1125, 71, 4);
INSERT INTO `stu_course_info` VALUES (1126, 71, 5);
INSERT INTO `stu_course_info` VALUES (1127, 72, 0);
INSERT INTO `stu_course_info` VALUES (1128, 72, 1);
INSERT INTO `stu_course_info` VALUES (1129, 72, 2);
INSERT INTO `stu_course_info` VALUES (1130, 72, 3);
INSERT INTO `stu_course_info` VALUES (1131, 72, 4);
INSERT INTO `stu_course_info` VALUES (1132, 72, 5);
INSERT INTO `stu_course_info` VALUES (1133, 73, 0);
INSERT INTO `stu_course_info` VALUES (1134, 73, 1);
INSERT INTO `stu_course_info` VALUES (1135, 73, 2);
INSERT INTO `stu_course_info` VALUES (1136, 73, 3);
INSERT INTO `stu_course_info` VALUES (1137, 73, 4);
INSERT INTO `stu_course_info` VALUES (1138, 73, 5);
INSERT INTO `stu_course_info` VALUES (1139, 74, 0);
INSERT INTO `stu_course_info` VALUES (1140, 74, 1);
INSERT INTO `stu_course_info` VALUES (1141, 74, 2);
INSERT INTO `stu_course_info` VALUES (1142, 74, 3);
INSERT INTO `stu_course_info` VALUES (1143, 74, 4);
INSERT INTO `stu_course_info` VALUES (1144, 74, 5);
INSERT INTO `stu_course_info` VALUES (1145, 75, 0);
INSERT INTO `stu_course_info` VALUES (1146, 75, 1);
INSERT INTO `stu_course_info` VALUES (1147, 75, 2);
INSERT INTO `stu_course_info` VALUES (1148, 75, 3);
INSERT INTO `stu_course_info` VALUES (1149, 75, 4);
INSERT INTO `stu_course_info` VALUES (1150, 75, 5);
INSERT INTO `stu_course_info` VALUES (1151, 76, 0);
INSERT INTO `stu_course_info` VALUES (1152, 76, 1);
INSERT INTO `stu_course_info` VALUES (1153, 76, 2);
INSERT INTO `stu_course_info` VALUES (1154, 76, 3);
INSERT INTO `stu_course_info` VALUES (1155, 76, 4);
INSERT INTO `stu_course_info` VALUES (1156, 76, 5);
INSERT INTO `stu_course_info` VALUES (1157, 77, 0);
INSERT INTO `stu_course_info` VALUES (1158, 77, 1);
INSERT INTO `stu_course_info` VALUES (1159, 77, 2);
INSERT INTO `stu_course_info` VALUES (1160, 77, 3);
INSERT INTO `stu_course_info` VALUES (1161, 77, 4);
INSERT INTO `stu_course_info` VALUES (1162, 77, 5);
INSERT INTO `stu_course_info` VALUES (1163, 78, 0);
INSERT INTO `stu_course_info` VALUES (1164, 78, 1);
INSERT INTO `stu_course_info` VALUES (1165, 78, 2);
INSERT INTO `stu_course_info` VALUES (1166, 78, 3);
INSERT INTO `stu_course_info` VALUES (1167, 78, 4);
INSERT INTO `stu_course_info` VALUES (1168, 78, 5);
INSERT INTO `stu_course_info` VALUES (1169, 79, 0);
INSERT INTO `stu_course_info` VALUES (1170, 79, 1);
INSERT INTO `stu_course_info` VALUES (1171, 79, 2);
INSERT INTO `stu_course_info` VALUES (1172, 79, 3);
INSERT INTO `stu_course_info` VALUES (1173, 79, 4);
INSERT INTO `stu_course_info` VALUES (1174, 79, 5);
INSERT INTO `stu_course_info` VALUES (1175, 80, 0);
INSERT INTO `stu_course_info` VALUES (1176, 80, 1);
INSERT INTO `stu_course_info` VALUES (1177, 80, 2);
INSERT INTO `stu_course_info` VALUES (1178, 80, 3);
INSERT INTO `stu_course_info` VALUES (1179, 80, 4);
INSERT INTO `stu_course_info` VALUES (1180, 80, 5);
INSERT INTO `stu_course_info` VALUES (1181, 81, 0);
INSERT INTO `stu_course_info` VALUES (1182, 81, 1);
INSERT INTO `stu_course_info` VALUES (1183, 81, 2);
INSERT INTO `stu_course_info` VALUES (1184, 81, 3);
INSERT INTO `stu_course_info` VALUES (1185, 81, 4);
INSERT INTO `stu_course_info` VALUES (1186, 81, 5);
INSERT INTO `stu_course_info` VALUES (1187, 82, 0);
INSERT INTO `stu_course_info` VALUES (1188, 82, 1);
INSERT INTO `stu_course_info` VALUES (1189, 82, 2);
INSERT INTO `stu_course_info` VALUES (1190, 82, 3);
INSERT INTO `stu_course_info` VALUES (1191, 82, 4);
INSERT INTO `stu_course_info` VALUES (1192, 82, 5);
INSERT INTO `stu_course_info` VALUES (1193, 83, 0);
INSERT INTO `stu_course_info` VALUES (1194, 83, 1);
INSERT INTO `stu_course_info` VALUES (1195, 83, 2);
INSERT INTO `stu_course_info` VALUES (1196, 83, 3);
INSERT INTO `stu_course_info` VALUES (1197, 83, 4);
INSERT INTO `stu_course_info` VALUES (1198, 83, 5);
INSERT INTO `stu_course_info` VALUES (1199, 84, 0);
INSERT INTO `stu_course_info` VALUES (1200, 84, 1);
INSERT INTO `stu_course_info` VALUES (1201, 84, 2);
INSERT INTO `stu_course_info` VALUES (1202, 84, 3);
INSERT INTO `stu_course_info` VALUES (1203, 84, 4);
INSERT INTO `stu_course_info` VALUES (1204, 84, 5);
INSERT INTO `stu_course_info` VALUES (1205, 85, 0);
INSERT INTO `stu_course_info` VALUES (1206, 85, 1);
INSERT INTO `stu_course_info` VALUES (1207, 85, 2);
INSERT INTO `stu_course_info` VALUES (1208, 85, 3);
INSERT INTO `stu_course_info` VALUES (1209, 85, 4);
INSERT INTO `stu_course_info` VALUES (1210, 85, 5);

-- ----------------------------
-- Table structure for stu_data
-- ----------------------------
DROP TABLE IF EXISTS `stu_data`;
CREATE TABLE `stu_data`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资料名',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '链接',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '提取码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '资料大全' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_data
-- ----------------------------
INSERT INTO `stu_data` VALUES (1, '机房软件', 'https://pan.baidu.com/s/1rae0_xZQocOxwudmhFtvbQ?pwd=ovii', 'ovii');
INSERT INTO `stu_data` VALUES (2, '笔记地址', 'https://www.yuque.com/sd7i8f/bok6q9?# 《技术知识库》', '');
INSERT INTO `stu_data` VALUES (3, '综合实践地址', 'https://www.yuque.com/sd7i8f/bok6q9/mec77zp6gqr7zzak?singleDoc# 《2023毕业综合实践操作指南》', '');

-- ----------------------------
-- Table structure for stu_entrance_exam
-- ----------------------------
DROP TABLE IF EXISTS `stu_entrance_exam`;
CREATE TABLE `stu_entrance_exam`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `one_score` int NULL DEFAULT NULL COMMENT '大一升学考试',
  `two_score` int NULL DEFAULT NULL COMMENT '大二升学考试',
  `three_score` int NULL DEFAULT NULL COMMENT '大三升学考试',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '升学考试' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_entrance_exam
-- ----------------------------

-- ----------------------------
-- Table structure for stu_info
-- ----------------------------
DROP TABLE IF EXISTS `stu_info`;
CREATE TABLE `stu_info`  (
  `stu_id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `stu_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学号',
  `stu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `stu_cls` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '班级',
  `stu_cls_year` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '年级',
  `stu_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `stu_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学生类型',
  `stu_major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '录取专业',
  `stu_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '家庭住址',
  `stu_tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '学生信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_info
-- ----------------------------
INSERT INTO `stu_info` VALUES (3, '202103071001', '陈伊蕊', '2101', '21级', '女', '普通类', '计算机网络技术', '杭州市下城区长庆街道柏枝巷15号1单元501室', '15336508169');
INSERT INTO `stu_info` VALUES (4, '202103071002', '何劲涛', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省杭州市萧山区红山农场四分场90号', '19817571370');
INSERT INTO `stu_info` VALUES (5, '202103071003', '谢晓杰', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省杭州市临安区清凉峰镇刘家5号', '13285718979');
INSERT INTO `stu_info` VALUES (6, '202103071004', '奚文斌', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省宁波市奉化区松岙镇岩家塘51号', '15990524991');
INSERT INTO `stu_info` VALUES (7, '202103071005', '叶宣辰', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省宁海县跃龙街道园丁路8弄3幢102号', '15068253309');
INSERT INTO `stu_info` VALUES (8, '202103071006', '周涛涛', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省宁波市宁海县跃龙街道中山东路1幢29弄19号', '15372868606');
INSERT INTO `stu_info` VALUES (9, '202103071007', '郭应榜', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省瓯海区鑫汇家园5栋1602', '13867706043');
INSERT INTO `stu_info` VALUES (10, '202103071008', '张伟', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省温州市乐清市宁康西路259弄', '15381581691');
INSERT INTO `stu_info` VALUES (11, '202103071009', '林日华', '2101', '21级', '男', '普通类', '计算机网络技术', '温州市瑞安市飞云街道新垟村5栋', '13506567285');
INSERT INTO `stu_info` VALUES (12, '202103071010', '陈金宬', '2101', '21级', '女', '普通类', '计算机网络技术', '浙江省温州市鹿城区汤家桥街道桃源花园6-704', '18058873939');
INSERT INTO `stu_info` VALUES (13, '202103071011', '缪茂国', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省温州市龙港市威龙商标城9栋3单元505', '18358731165');
INSERT INTO `stu_info` VALUES (14, '202103071012', '林杰', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省温州市苍南县钱库镇环城西路3号', '15058740554');
INSERT INTO `stu_info` VALUES (15, '202103071013', '钱朱昱', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省湖州市吴兴区余家漾月漾苑34幢101室', '13867277336');
INSERT INTO `stu_info` VALUES (16, '202103071014', '费盛垚', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省湖州市德清县阜溪街道三桥集镇胶辊实业公司', '18267283021');
INSERT INTO `stu_info` VALUES (17, '202103071015', '赵仕林', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省绍兴市越城区南都新村17栋2单元403室', '13989504830');
INSERT INTO `stu_info` VALUES (18, '202103071016', '陈旸', '2101', '21级', '男', '普通类', '计算机网络技术', '温州市玉苍西路景绣湾花园1幢2单元503', '13868659913');
INSERT INTO `stu_info` VALUES (19, '202103071017', '田宇佚', '2101', '21级', '女', '普通类', '计算机网络技术', '浙江省丽水市缙云县东方镇皋头村105', '17521210468');
INSERT INTO `stu_info` VALUES (20, '202103071018', '朱喆轩', '2101', '21级', '男', '普通类', '计算机网络技术', '浙江省台州市温岭市石塘镇上马工业区海景名苑19幢第3单元305', '15058629060');
INSERT INTO `stu_info` VALUES (21, '202103071019', '赵慧芳', '2101', '21级', '女', '普通类', '计算机网络技术', '浙江省台州市温岭市泽国镇印象小区2404', '15258606100');
INSERT INTO `stu_info` VALUES (22, '202103071020', '楼志天', '2101', '21级', '男', '普通类', '计算机网络技术', '义乌市大陈镇红旗村上山19号（陈凤花收）', '13373835979');
INSERT INTO `stu_info` VALUES (24, '202103071022', '金梦翎', '2101', '21级', '女', '高职提前招生', '计算机网络技术', '浙江省金华市金东区枫树小区3幢1号(321000)', '13157950881');
INSERT INTO `stu_info` VALUES (25, '202103071023', '章泽桦', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '杭州市滨江区滨兴北苑 10幢2单元302(310052)', '19805816654');
INSERT INTO `stu_info` VALUES (26, '202103071024', '张嘉弘', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省三门县浦坝港镇小雄村(317100)', '19174210046');
INSERT INTO `stu_info` VALUES (27, '202103071025', '蔡时进', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省台州市椒江区东环大道85号品牌汇(318000)', '13989698159');
INSERT INTO `stu_info` VALUES (28, '202103071026', '沈铭瑜', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省杭州市滨江区滨兴小区八幢一单元401(310051)', '13185069328');
INSERT INTO `stu_info` VALUES (29, '202103071027', '华睿', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省温州市瓯海区新桥街道金铭佳苑14幢801室(325006)', '13515776791');
INSERT INTO `stu_info` VALUES (30, '202103071028', '池雅明', '2101', '21级', '女', '高职提前招生', '计算机网络技术', '浙江省丽水市景宁畲族自治县红星街道人民中路233号原野花艺(323500)', '15058680994');
INSERT INTO `stu_info` VALUES (31, '202103071029', '陈民铖', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省温州市龙港市九小花苑第一单元第二栋201室', '13858765957');
INSERT INTO `stu_info` VALUES (32, '202103071030', '林煌', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市平阳县水头镇中街70号', '13587825732');
INSERT INTO `stu_info` VALUES (33, '202103071031', '赵航宇', '2101', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省金华市浦江县浦南街道文溪新区二区216号(322200)', '18757656609');
INSERT INTO `stu_info` VALUES (34, '202103071032', '卫宏宇', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '杭州市余杭区星海路崇贤新城佳源名城6号楼1单元402室', '18072805611');
INSERT INTO `stu_info` VALUES (35, '202103071033', '沈佳晨', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '慈溪市观海卫镇沈师桥村黄家漕39号', '15258358701');
INSERT INTO `stu_info` VALUES (36, '202103071034', '李博昊', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省宁波市余姚市凤山街道新建北路711-2号', '15658382083');
INSERT INTO `stu_info` VALUES (37, '202103071035', '夏荀', '2101', '21级', '女', '单独考试招生', '计算机网络技术', '浙江省温州市乐清市城南街道兴达路盖竹村菜鸟驿站 夏荀收', '19884626164');
INSERT INTO `stu_info` VALUES (38, '202103071037', '缪诗俊', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市龙港市海头村87号', '17858082615');
INSERT INTO `stu_info` VALUES (39, '202103071038', '陈艳丽', '2101', '21级', '女', '单独考试招生', '计算机网络技术', '浙江省温州市苍南县灵溪镇观美镇府路73号', '15058340211');
INSERT INTO `stu_info` VALUES (40, '202103071039', '陈坤', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市苍南县灵溪镇东仓路38号', '15868756955');
INSERT INTO `stu_info` VALUES (41, '202103071040', '陈庆冬', '2101', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市苍南县炎亭镇沿溪路23号', '18806771934');
INSERT INTO `stu_info` VALUES (46, '202103070901', '马细雨', '2102', '21级', '女', '普通类', '计算机网络技术', '浙江省杭州市临安区锦北街道集贤村152号', '18814853981');
INSERT INTO `stu_info` VALUES (47, '202103070902', '金源', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江杭州余杭宝塔星苑164号', '18158443931');
INSERT INTO `stu_info` VALUES (48, '202103070903', '许思剑', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省温州市鹿城区垟儿路口118号四幢108室', '15606877010');
INSERT INTO `stu_info` VALUES (49, '202103070904', '宋永杰', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省温州市乐清市清江镇江沿村申华电子科技有限公司', '13616823749');
INSERT INTO `stu_info` VALUES (50, '202103070905', '黄达鹏', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省瑞安市宁益徐发村花园坦', '13967787169');
INSERT INTO `stu_info` VALUES (51, '202103070906', '程昌鑫', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省平阳县昆阳镇平塔小区a901', '13757883926');
INSERT INTO `stu_info` VALUES (52, '202103070907', '蒋志豪', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省嘉兴市秀洲区王店镇御锦湾2幢503室', '19817059161');
INSERT INTO `stu_info` VALUES (53, '202103070908', '忻佳奇', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省湖州市德清县乾元镇联合村忻家墩17号', '18768362179');
INSERT INTO `stu_info` VALUES (54, '202103070909', '周炜浩', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省长兴县吕山乡金村座山湾104号', '15906721723');
INSERT INTO `stu_info` VALUES (55, '202103070910', '张鹏宇', '2102', '21级', '男', '普通类', '计算机网络技术', '绍兴市越城区大树江小区南区15幢2单元103室', '15068258125');
INSERT INTO `stu_info` VALUES (56, '202103070911', '陈恩宽', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省永嘉县桥头镇白垟村阳光路15号', '13634226161');
INSERT INTO `stu_info` VALUES (57, '202103070912', '吴小爽', '2102', '21级', '女', '普通类', '计算机网络技术', '浙江省金华市兰溪市永昌街道两头门村', '18757961573');
INSERT INTO `stu_info` VALUES (58, '202103070913', '张智凯', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省江山市雅林西院7B502室', '13095709556');
INSERT INTO `stu_info` VALUES (59, '202103070914', '刘叶思宇', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省衢州市江山市双塔街道缸甫底村50号', '18167000523');
INSERT INTO `stu_info` VALUES (60, '202103070915', '丁奕帆', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省丽水市龙泉市上垟镇双源村', '15024615124');
INSERT INTO `stu_info` VALUES (61, '202103070916', '汤众杰', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省丽水市龙泉市南秦文化礼堂b10栋4单元601', '13157867501');
INSERT INTO `stu_info` VALUES (62, '202103070917', '陈苏鑫', '2102', '21级', '女', '普通类', '计算机网络技术', '浙江省丽水市松阳县古市镇筏铺新村8区25号', '18268913766');
INSERT INTO `stu_info` VALUES (63, '202103070918', '王吉', '2102', '21级', '男', '普通类', '计算机网络技术', '台州市天台县赤城街道天桐路百步洋村下潘组75号', '18072541953');
INSERT INTO `stu_info` VALUES (64, '202103070919', '董桢锋', '2102', '21级', '男', '普通类', '计算机网络技术', '浙江省玉环市环东村中路58号', '15397397025');
INSERT INTO `stu_info` VALUES (65, '202103070920', '周槐桢', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省建德市寿昌镇绿荷塘村号(311612)', '15990060217');
INSERT INTO `stu_info` VALUES (66, '202103070921', '郑倩倩', '2102', '21级', '女', '高职提前招生', '计算机网络技术', '浙江省江山市长台镇出溪港3号(324106)', '15700045385');
INSERT INTO `stu_info` VALUES (67, '202103070922', '沈煜阳', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省湖州市吴兴区天际花园17幢A单元801室(313000)', '18157276592');
INSERT INTO `stu_info` VALUES (68, '202103070923', '董芊含', '2102', '21级', '女', '高职提前招生', '计算机网络技术', '浙江省温州市鹿城区锦绣路银都花苑浙瓯二幢102室(325000)', '13819730863');
INSERT INTO `stu_info` VALUES (69, '202103070924', '陈芝任', '2102', '21级', '女', '高职提前招生', '计算机网络技术', '浙江省诸暨市大唐镇合溪口新村42幢二单元(311800)', '15257526452');
INSERT INTO `stu_info` VALUES (70, '202103070925', '黄伟锋', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省兰溪市上华街道高畈村557号(321100)', '18757996916');
INSERT INTO `stu_info` VALUES (71, '202103070926', '吴江涛', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '宁波市鄞州区姜山镇南大中路47号姜山中学(315191)', '13336864653');
INSERT INTO `stu_info` VALUES (72, '202103070927', '黄挺', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省台州市椒江区海门街道岩头西苑21幢3-4(318000)', '13396937669');
INSERT INTO `stu_info` VALUES (73, '202103070928', '贺子鸣', '2102', '21级', '男', '文理科高职(专科)', '计算机网络技术', '安徽省宿州市埇桥区解集乡泉井村', '17719333751');
INSERT INTO `stu_info` VALUES (74, '202103070929', '徐潘圣', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省湖州市吴兴区康山街道三洋阳光海岸7幢104(313000)', '13506518554');
INSERT INTO `stu_info` VALUES (75, '202103070930', '姚思源', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省杭州市余杭区乔司街道朝阳村2组232号(311101)', '15658060629');
INSERT INTO `stu_info` VALUES (76, '202103070931', '陈佳慧', '2102', '21级', '女', '高职提前招生', '计算机网络技术', '浙江省温州市龙港市港河路70号3单元202(325802)', '15868720681');
INSERT INTO `stu_info` VALUES (77, '202103070932', '朱振令', '2102', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市瑞安区马屿镇梅屿村', '19817770107');
INSERT INTO `stu_info` VALUES (78, '202103070933', '林楷迪', '2102', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市瓯海区梧田街道大堡底庆安路22号', '17867875477');
INSERT INTO `stu_info` VALUES (79, '202103070934', '王书亚', '2102', '21级', '男', '单独考试招生', '计算机网络技术', '浙江省温州市乐清市城东街道文明中路43号景湾花园A区18栋1401室', '15869492607');
INSERT INTO `stu_info` VALUES (80, '202103070935', '陈特豪', '2102', '21级', '男', '单独考试招生', '计算机网络技术', '温州市乐清市翁垟街道镇西中路99号', '19817519863');
INSERT INTO `stu_info` VALUES (81, '202103070936', '徐鑫竹', '2102', '21级', '男', '高职提前招生', '计算机网络技术', '浙江省温州市乐清市淡溪镇马岙村', '13388591516');
INSERT INTO `stu_info` VALUES (82, '202103070937', '郭馨怡', '2102', '21级', '女', '单独考试招生', '计算机网络技术', '浙江省温州市苍南县灵溪镇城中南路187号', '15058760529');
INSERT INTO `stu_info` VALUES (83, '202103070938', '苏安琪', '2102', '21级', '女', '单独考试招生', '计算机网络技术', '浙江省温州市苍南县 望里镇 望里街2-21', '13057958236');
INSERT INTO `stu_info` VALUES (84, '202103070939', '朱家骅', '2102', '21级', '女', '单独考试招生', '计算机网络技术', '浙江省诸暨市王家井镇宜联村879号', '18157577803');
INSERT INTO `stu_info` VALUES (85, '202103070940', '季振杰', '2102', '21级', '男', '单独考试招生', '计算机网络技术', '丽水市庆元县竹口镇黄坦村大路20号', '15068253090');

-- ----------------------------
-- Table structure for stu_info_material
-- ----------------------------
DROP TABLE IF EXISTS `stu_info_material`;
CREATE TABLE `stu_info_material`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stu_id` int NULL DEFAULT NULL COMMENT '学生id',
  `material_id` int NULL DEFAULT NULL COMMENT '材料id',
  `flag` int NULL DEFAULT NULL COMMENT '提交状态',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交地址',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_info_material
-- ----------------------------
INSERT INTO `stu_info_material` VALUES (1, 3, 1, 1, '1', '1', '2023-05-19 22:40:47', '11', '2023-05-19 22:41:21');
INSERT INTO `stu_info_material` VALUES (2, 3, 2, 1, '1', '1', '2023-05-19 22:41:14', '1', '2023-05-19 22:41:27');

-- ----------------------------
-- Table structure for stu_material
-- ----------------------------
DROP TABLE IF EXISTS `stu_material`;
CREATE TABLE `stu_material`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材料名',
  `flag` int NULL DEFAULT NULL COMMENT '提交状态',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '毕业时间提交材料情况表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_material
-- ----------------------------
INSERT INTO `stu_material` VALUES (1, '开题报告', NULL, NULL);
INSERT INTO `stu_material` VALUES (2, '毕业论文', NULL, NULL);
INSERT INTO `stu_material` VALUES (3, '毕业成果', NULL, NULL);
INSERT INTO `stu_material` VALUES (4, '顶岗实习报告', NULL, NULL);
INSERT INTO `stu_material` VALUES (5, '企业实习报告', NULL, NULL);
INSERT INTO `stu_material` VALUES (6, '顶岗实习承诺书', NULL, NULL);
INSERT INTO `stu_material` VALUES (7, '顶岗实习考核表', NULL, NULL);
INSERT INTO `stu_material` VALUES (8, '顶岗实习申请表', NULL, NULL);
INSERT INTO `stu_material` VALUES (9, '实习三方协议', NULL, NULL);
INSERT INTO `stu_material` VALUES (10, '在实习单位公司的照片', NULL, NULL);

-- ----------------------------
-- Table structure for stu_score
-- ----------------------------
DROP TABLE IF EXISTS `stu_score`;
CREATE TABLE `stu_score`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `daily_score` int NULL DEFAULT NULL COMMENT '日常分',
  `finally_score` int NULL DEFAULT NULL COMMENT '综合成绩',
  `course_id` int NULL DEFAULT NULL COMMENT '课程id',
  `stu_id` int NULL DEFAULT NULL COMMENT '学生id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 813 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '学生成绩表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_score
-- ----------------------------
INSERT INTO `stu_score` VALUES (573, NULL, 86, 1, 2);
INSERT INTO `stu_score` VALUES (574, NULL, 86, 1, 3);
INSERT INTO `stu_score` VALUES (575, NULL, 70, 1, 4);
INSERT INTO `stu_score` VALUES (576, NULL, 70, 1, 5);
INSERT INTO `stu_score` VALUES (577, NULL, 78, 1, 6);
INSERT INTO `stu_score` VALUES (578, NULL, 83, 1, 7);
INSERT INTO `stu_score` VALUES (579, NULL, 88, 1, 8);
INSERT INTO `stu_score` VALUES (580, NULL, 91, 1, 9);
INSERT INTO `stu_score` VALUES (581, NULL, 87, 1, 10);
INSERT INTO `stu_score` VALUES (582, NULL, 77, 1, 11);
INSERT INTO `stu_score` VALUES (583, NULL, 86, 1, 12);
INSERT INTO `stu_score` VALUES (584, NULL, 96, 1, 13);
INSERT INTO `stu_score` VALUES (585, NULL, 90, 1, 14);
INSERT INTO `stu_score` VALUES (586, NULL, 91, 1, 15);
INSERT INTO `stu_score` VALUES (587, NULL, 77, 1, 16);
INSERT INTO `stu_score` VALUES (588, NULL, 86, 1, 17);
INSERT INTO `stu_score` VALUES (589, NULL, 84, 1, 18);
INSERT INTO `stu_score` VALUES (590, NULL, 70, 1, 19);
INSERT INTO `stu_score` VALUES (591, NULL, 85, 1, 20);
INSERT INTO `stu_score` VALUES (592, NULL, 75, 1, 21);
INSERT INTO `stu_score` VALUES (593, NULL, 84, 1, 22);
INSERT INTO `stu_score` VALUES (594, NULL, 69, 1, 23);
INSERT INTO `stu_score` VALUES (595, NULL, 88, 1, 24);
INSERT INTO `stu_score` VALUES (596, NULL, 86, 1, 25);
INSERT INTO `stu_score` VALUES (597, NULL, 68, 1, 26);
INSERT INTO `stu_score` VALUES (598, NULL, 66, 1, 27);
INSERT INTO `stu_score` VALUES (599, NULL, 78, 1, 28);
INSERT INTO `stu_score` VALUES (600, NULL, 91, 1, 29);
INSERT INTO `stu_score` VALUES (601, NULL, 90, 1, 30);
INSERT INTO `stu_score` VALUES (602, NULL, 75, 1, 31);
INSERT INTO `stu_score` VALUES (603, NULL, 60, 1, 32);
INSERT INTO `stu_score` VALUES (604, NULL, 73, 1, 33);
INSERT INTO `stu_score` VALUES (605, NULL, 78, 1, 34);
INSERT INTO `stu_score` VALUES (606, NULL, 85, 1, 35);
INSERT INTO `stu_score` VALUES (607, NULL, 81, 1, 36);
INSERT INTO `stu_score` VALUES (608, NULL, 78, 1, 37);
INSERT INTO `stu_score` VALUES (609, NULL, 65, 1, 38);
INSERT INTO `stu_score` VALUES (610, NULL, 73, 1, 39);
INSERT INTO `stu_score` VALUES (611, NULL, 90, 1, 40);
INSERT INTO `stu_score` VALUES (612, NULL, 90, 1, 41);
INSERT INTO `stu_score` VALUES (613, NULL, 90, 2, 2);
INSERT INTO `stu_score` VALUES (614, NULL, 90, 2, 3);
INSERT INTO `stu_score` VALUES (615, NULL, 70, 2, 4);
INSERT INTO `stu_score` VALUES (616, NULL, 80, 2, 5);
INSERT INTO `stu_score` VALUES (617, NULL, 0, 2, 6);
INSERT INTO `stu_score` VALUES (618, NULL, 80, 2, 7);
INSERT INTO `stu_score` VALUES (619, NULL, 85, 2, 8);
INSERT INTO `stu_score` VALUES (620, NULL, 80, 2, 9);
INSERT INTO `stu_score` VALUES (621, NULL, 90, 2, 10);
INSERT INTO `stu_score` VALUES (622, NULL, 60, 2, 11);
INSERT INTO `stu_score` VALUES (623, NULL, 90, 2, 12);
INSERT INTO `stu_score` VALUES (624, NULL, 90, 2, 13);
INSERT INTO `stu_score` VALUES (625, NULL, 80, 2, 14);
INSERT INTO `stu_score` VALUES (626, NULL, 90, 2, 15);
INSERT INTO `stu_score` VALUES (627, NULL, 90, 2, 16);
INSERT INTO `stu_score` VALUES (628, NULL, 80, 2, 17);
INSERT INTO `stu_score` VALUES (629, NULL, 90, 2, 18);
INSERT INTO `stu_score` VALUES (630, NULL, 80, 2, 19);
INSERT INTO `stu_score` VALUES (631, NULL, 80, 2, 20);
INSERT INTO `stu_score` VALUES (632, NULL, 80, 2, 21);
INSERT INTO `stu_score` VALUES (633, NULL, 80, 2, 22);
INSERT INTO `stu_score` VALUES (634, NULL, 75, 2, 23);
INSERT INTO `stu_score` VALUES (635, NULL, 90, 2, 24);
INSERT INTO `stu_score` VALUES (636, NULL, 90, 2, 25);
INSERT INTO `stu_score` VALUES (637, NULL, 75, 2, 26);
INSERT INTO `stu_score` VALUES (638, NULL, 50, 2, 27);
INSERT INTO `stu_score` VALUES (639, NULL, 80, 2, 28);
INSERT INTO `stu_score` VALUES (640, NULL, 80, 2, 29);
INSERT INTO `stu_score` VALUES (641, NULL, 90, 2, 30);
INSERT INTO `stu_score` VALUES (642, NULL, 70, 2, 31);
INSERT INTO `stu_score` VALUES (643, NULL, 80, 2, 32);
INSERT INTO `stu_score` VALUES (644, NULL, 90, 2, 33);
INSERT INTO `stu_score` VALUES (645, NULL, 90, 2, 34);
INSERT INTO `stu_score` VALUES (646, NULL, 90, 2, 35);
INSERT INTO `stu_score` VALUES (647, NULL, 90, 2, 36);
INSERT INTO `stu_score` VALUES (648, NULL, 80, 2, 37);
INSERT INTO `stu_score` VALUES (649, NULL, 60, 2, 38);
INSERT INTO `stu_score` VALUES (650, NULL, 80, 2, 39);
INSERT INTO `stu_score` VALUES (651, NULL, 80, 2, 40);
INSERT INTO `stu_score` VALUES (652, NULL, 90, 2, 41);
INSERT INTO `stu_score` VALUES (653, NULL, 55, 3, 2);
INSERT INTO `stu_score` VALUES (654, NULL, 82, 3, 3);
INSERT INTO `stu_score` VALUES (655, NULL, 46, 3, 4);
INSERT INTO `stu_score` VALUES (656, NULL, 79, 3, 5);
INSERT INTO `stu_score` VALUES (657, NULL, 71, 3, 6);
INSERT INTO `stu_score` VALUES (658, NULL, 61, 3, 7);
INSERT INTO `stu_score` VALUES (659, NULL, 68, 3, 8);
INSERT INTO `stu_score` VALUES (660, NULL, 76, 3, 9);
INSERT INTO `stu_score` VALUES (661, NULL, 73, 3, 10);
INSERT INTO `stu_score` VALUES (662, NULL, 73, 3, 11);
INSERT INTO `stu_score` VALUES (663, NULL, 79, 3, 12);
INSERT INTO `stu_score` VALUES (664, NULL, 77, 3, 13);
INSERT INTO `stu_score` VALUES (665, NULL, 69, 3, 14);
INSERT INTO `stu_score` VALUES (666, NULL, 84, 3, 15);
INSERT INTO `stu_score` VALUES (667, NULL, 61, 3, 16);
INSERT INTO `stu_score` VALUES (668, NULL, 74, 3, 17);
INSERT INTO `stu_score` VALUES (669, NULL, 73, 3, 18);
INSERT INTO `stu_score` VALUES (670, NULL, 61, 3, 19);
INSERT INTO `stu_score` VALUES (671, NULL, 76, 3, 20);
INSERT INTO `stu_score` VALUES (672, NULL, 48, 3, 21);
INSERT INTO `stu_score` VALUES (673, NULL, 81, 3, 22);
INSERT INTO `stu_score` VALUES (674, NULL, 70, 3, 23);
INSERT INTO `stu_score` VALUES (675, NULL, 69, 3, 24);
INSERT INTO `stu_score` VALUES (676, NULL, 55, 3, 25);
INSERT INTO `stu_score` VALUES (677, NULL, 61, 3, 26);
INSERT INTO `stu_score` VALUES (678, NULL, 48, 3, 27);
INSERT INTO `stu_score` VALUES (679, NULL, 0, 3, 28);
INSERT INTO `stu_score` VALUES (680, NULL, 65, 3, 29);
INSERT INTO `stu_score` VALUES (681, NULL, 73, 3, 30);
INSERT INTO `stu_score` VALUES (682, NULL, 73, 3, 31);
INSERT INTO `stu_score` VALUES (683, NULL, 65, 3, 32);
INSERT INTO `stu_score` VALUES (684, NULL, 61, 3, 33);
INSERT INTO `stu_score` VALUES (685, NULL, 73, 3, 34);
INSERT INTO `stu_score` VALUES (686, NULL, 0, 3, 35);
INSERT INTO `stu_score` VALUES (687, NULL, 73, 3, 36);
INSERT INTO `stu_score` VALUES (688, NULL, 73, 3, 37);
INSERT INTO `stu_score` VALUES (689, NULL, 48, 3, 38);
INSERT INTO `stu_score` VALUES (690, NULL, 61, 3, 39);
INSERT INTO `stu_score` VALUES (691, NULL, 65, 3, 40);
INSERT INTO `stu_score` VALUES (692, NULL, 76, 3, 41);
INSERT INTO `stu_score` VALUES (693, NULL, 75, 3, 46);
INSERT INTO `stu_score` VALUES (694, NULL, 46, 3, 47);
INSERT INTO `stu_score` VALUES (695, NULL, 70, 3, 48);
INSERT INTO `stu_score` VALUES (696, NULL, 70, 3, 49);
INSERT INTO `stu_score` VALUES (697, NULL, 70, 3, 50);
INSERT INTO `stu_score` VALUES (698, NULL, 61, 3, 51);
INSERT INTO `stu_score` VALUES (699, NULL, 73, 3, 52);
INSERT INTO `stu_score` VALUES (700, NULL, 73, 3, 53);
INSERT INTO `stu_score` VALUES (701, NULL, 75, 3, 54);
INSERT INTO `stu_score` VALUES (702, NULL, 68, 3, 55);
INSERT INTO `stu_score` VALUES (703, NULL, 68, 3, 56);
INSERT INTO `stu_score` VALUES (704, NULL, 61, 3, 57);
INSERT INTO `stu_score` VALUES (705, NULL, 74, 3, 58);
INSERT INTO `stu_score` VALUES (706, NULL, 73, 3, 59);
INSERT INTO `stu_score` VALUES (707, NULL, 73, 3, 60);
INSERT INTO `stu_score` VALUES (708, NULL, 78, 3, 61);
INSERT INTO `stu_score` VALUES (709, NULL, 78, 3, 62);
INSERT INTO `stu_score` VALUES (710, NULL, 73, 3, 63);
INSERT INTO `stu_score` VALUES (711, NULL, 75, 3, 64);
INSERT INTO `stu_score` VALUES (712, NULL, 64, 3, 65);
INSERT INTO `stu_score` VALUES (713, NULL, 64, 3, 66);
INSERT INTO `stu_score` VALUES (714, NULL, 73, 3, 67);
INSERT INTO `stu_score` VALUES (715, NULL, 70, 3, 68);
INSERT INTO `stu_score` VALUES (716, NULL, 74, 3, 69);
INSERT INTO `stu_score` VALUES (717, NULL, 68, 3, 70);
INSERT INTO `stu_score` VALUES (718, NULL, 49, 3, 71);
INSERT INTO `stu_score` VALUES (719, NULL, 64, 3, 72);
INSERT INTO `stu_score` VALUES (720, NULL, 64, 3, 73);
INSERT INTO `stu_score` VALUES (721, NULL, 64, 3, 74);
INSERT INTO `stu_score` VALUES (722, NULL, 75, 3, 75);
INSERT INTO `stu_score` VALUES (723, NULL, 74, 3, 76);
INSERT INTO `stu_score` VALUES (724, NULL, 75, 3, 77);
INSERT INTO `stu_score` VALUES (725, NULL, 64, 3, 78);
INSERT INTO `stu_score` VALUES (726, NULL, 73, 3, 79);
INSERT INTO `stu_score` VALUES (727, NULL, 73, 3, 80);
INSERT INTO `stu_score` VALUES (728, NULL, 88, 3, 81);
INSERT INTO `stu_score` VALUES (729, NULL, 61, 3, 82);
INSERT INTO `stu_score` VALUES (730, NULL, 79, 3, 83);
INSERT INTO `stu_score` VALUES (731, NULL, 75, 3, 84);
INSERT INTO `stu_score` VALUES (732, NULL, 76, 3, 85);
INSERT INTO `stu_score` VALUES (733, NULL, 80, 2, 46);
INSERT INTO `stu_score` VALUES (734, NULL, 60, 2, 47);
INSERT INTO `stu_score` VALUES (735, NULL, 85, 2, 48);
INSERT INTO `stu_score` VALUES (736, NULL, 88, 2, 49);
INSERT INTO `stu_score` VALUES (737, NULL, 86, 2, 50);
INSERT INTO `stu_score` VALUES (738, NULL, 88, 2, 51);
INSERT INTO `stu_score` VALUES (739, NULL, 78, 2, 52);
INSERT INTO `stu_score` VALUES (740, NULL, 87, 2, 53);
INSERT INTO `stu_score` VALUES (741, NULL, 88, 2, 54);
INSERT INTO `stu_score` VALUES (742, NULL, 90, 2, 55);
INSERT INTO `stu_score` VALUES (743, NULL, 85, 2, 56);
INSERT INTO `stu_score` VALUES (744, NULL, 71, 2, 57);
INSERT INTO `stu_score` VALUES (745, NULL, 70, 2, 58);
INSERT INTO `stu_score` VALUES (746, NULL, 80, 2, 59);
INSERT INTO `stu_score` VALUES (747, NULL, 90, 2, 60);
INSERT INTO `stu_score` VALUES (748, NULL, 87, 2, 61);
INSERT INTO `stu_score` VALUES (749, NULL, 85, 2, 62);
INSERT INTO `stu_score` VALUES (750, NULL, 75, 2, 63);
INSERT INTO `stu_score` VALUES (751, NULL, 87, 2, 64);
INSERT INTO `stu_score` VALUES (752, NULL, 86, 2, 65);
INSERT INTO `stu_score` VALUES (753, NULL, 85, 2, 66);
INSERT INTO `stu_score` VALUES (754, NULL, 86, 2, 67);
INSERT INTO `stu_score` VALUES (755, NULL, 90, 2, 68);
INSERT INTO `stu_score` VALUES (756, NULL, 90, 2, 69);
INSERT INTO `stu_score` VALUES (757, NULL, 80, 2, 70);
INSERT INTO `stu_score` VALUES (758, NULL, 91, 2, 71);
INSERT INTO `stu_score` VALUES (759, NULL, 85, 2, 72);
INSERT INTO `stu_score` VALUES (760, NULL, 82, 2, 73);
INSERT INTO `stu_score` VALUES (761, NULL, 86, 2, 74);
INSERT INTO `stu_score` VALUES (762, NULL, 88, 2, 75);
INSERT INTO `stu_score` VALUES (763, NULL, 87, 2, 76);
INSERT INTO `stu_score` VALUES (764, NULL, 84, 2, 77);
INSERT INTO `stu_score` VALUES (765, NULL, 86, 2, 78);
INSERT INTO `stu_score` VALUES (766, NULL, 85, 2, 79);
INSERT INTO `stu_score` VALUES (767, NULL, 88, 2, 80);
INSERT INTO `stu_score` VALUES (768, NULL, 83, 2, 81);
INSERT INTO `stu_score` VALUES (769, NULL, 83, 2, 82);
INSERT INTO `stu_score` VALUES (770, NULL, 88, 2, 83);
INSERT INTO `stu_score` VALUES (771, NULL, 86, 2, 84);
INSERT INTO `stu_score` VALUES (772, NULL, 85, 2, 85);
INSERT INTO `stu_score` VALUES (773, NULL, 86, 1, 46);
INSERT INTO `stu_score` VALUES (774, NULL, 90, 1, 47);
INSERT INTO `stu_score` VALUES (775, NULL, 90, 1, 48);
INSERT INTO `stu_score` VALUES (776, NULL, 86, 1, 49);
INSERT INTO `stu_score` VALUES (777, NULL, 80, 1, 50);
INSERT INTO `stu_score` VALUES (778, NULL, 85, 1, 51);
INSERT INTO `stu_score` VALUES (779, NULL, 80, 1, 52);
INSERT INTO `stu_score` VALUES (780, NULL, 90, 1, 53);
INSERT INTO `stu_score` VALUES (781, NULL, 76, 1, 54);
INSERT INTO `stu_score` VALUES (782, NULL, 92, 1, 55);
INSERT INTO `stu_score` VALUES (783, NULL, 83, 1, 56);
INSERT INTO `stu_score` VALUES (784, NULL, 79, 1, 57);
INSERT INTO `stu_score` VALUES (785, NULL, 80, 1, 58);
INSERT INTO `stu_score` VALUES (786, NULL, 83, 1, 59);
INSERT INTO `stu_score` VALUES (787, NULL, 94, 1, 60);
INSERT INTO `stu_score` VALUES (788, NULL, 98, 1, 61);
INSERT INTO `stu_score` VALUES (789, NULL, 92, 1, 62);
INSERT INTO `stu_score` VALUES (790, NULL, 94, 1, 63);
INSERT INTO `stu_score` VALUES (791, NULL, 38, 1, 64);
INSERT INTO `stu_score` VALUES (792, NULL, 90, 1, 65);
INSERT INTO `stu_score` VALUES (793, NULL, 86, 1, 66);
INSERT INTO `stu_score` VALUES (794, NULL, 98, 1, 67);
INSERT INTO `stu_score` VALUES (795, NULL, 91, 1, 68);
INSERT INTO `stu_score` VALUES (796, NULL, 86, 1, 69);
INSERT INTO `stu_score` VALUES (797, NULL, 83, 1, 70);
INSERT INTO `stu_score` VALUES (798, NULL, 80, 1, 71);
INSERT INTO `stu_score` VALUES (799, NULL, 98, 1, 72);
INSERT INTO `stu_score` VALUES (800, NULL, 72, 1, 73);
INSERT INTO `stu_score` VALUES (801, NULL, 80, 1, 74);
INSERT INTO `stu_score` VALUES (802, NULL, 72, 1, 75);
INSERT INTO `stu_score` VALUES (803, NULL, 86, 1, 76);
INSERT INTO `stu_score` VALUES (804, NULL, 88, 1, 77);
INSERT INTO `stu_score` VALUES (805, NULL, 86, 1, 78);
INSERT INTO `stu_score` VALUES (806, NULL, 75, 1, 79);
INSERT INTO `stu_score` VALUES (807, NULL, 76, 1, 80);
INSERT INTO `stu_score` VALUES (808, NULL, 93, 1, 81);
INSERT INTO `stu_score` VALUES (809, NULL, 63, 1, 82);
INSERT INTO `stu_score` VALUES (810, NULL, 65, 1, 83);
INSERT INTO `stu_score` VALUES (811, NULL, 80, 1, 84);
INSERT INTO `stu_score` VALUES (812, NULL, 78, 1, 85);

-- ----------------------------
-- Table structure for stu_tutorial
-- ----------------------------
DROP TABLE IF EXISTS `stu_tutorial`;
CREATE TABLE `stu_tutorial`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教程名',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教程链接',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '教程' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_tutorial
-- ----------------------------
INSERT INTO `stu_tutorial` VALUES (1, 'jdk的安装与配置', 'https://url.cy/ZKrQc4', NULL);
INSERT INTO `stu_tutorial` VALUES (2, 'mysql安装步骤', 'https://url.cy/09EgNf', NULL);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2023-05-19 09:14:47', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-05-19 09:14:47', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2023-05-19 09:14:47', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2023-05-19 09:14:47', '', NULL, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2023-05-19 09:14:47', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2023-05-19 09:14:47', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-05-19 09:14:46', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '年级                   ', 'stu_cls_year', '0', 'admin', '2023-05-19 14:33:58', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-05-19 09:14:47', '', NULL, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`login_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-19 11:12:04');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-19 13:32:39');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-19 13:49:15');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '127.0.0.1', '内网IP', 'Firefox 11', 'Windows 10', '0', '登录成功', '2023-05-19 21:53:22');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-19 21:53:49');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '1', '验证码错误', '2023-05-19 22:51:48');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-19 22:51:52');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '127.0.0.1', '内网IP', 'Firefox 11', 'Windows 10', '0', '登录成功', '2023-05-20 12:32:15');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-20 12:50:56');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '127.0.0.1', '内网IP', 'Firefox 11', 'Windows 10', '0', '登录成功', '2023-05-20 15:25:30');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-05-20 15:25:48');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2022 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-05-19 09:14:47', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2023-05-19 09:14:47', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2023-05-19 09:14:47', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, '若依官网', 0, 5, 'http://ruoyi.vip', NULL, '', 0, 0, 'M', '0', '0', '', 'guide', 'admin', '2023-05-19 09:14:47', 'admin', '2023-05-19 13:34:05', '若依官网地址');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2023-05-19 09:14:47', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2023-05-19 09:14:47', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2023-05-19 09:14:47', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2023-05-19 09:14:47', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2023-05-19 09:14:47', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2023-05-19 09:14:47', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2023-05-19 09:14:47', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2023-05-19 09:14:47', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2023-05-19 09:14:47', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2023-05-19 09:14:47', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2023-05-19 09:14:47', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2023-05-19 09:14:47', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2023-05-19 09:14:47', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2023-05-19 09:14:47', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2023-05-19 09:14:47', '', NULL, '缓存列表菜单');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2023-05-19 09:14:47', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2023-05-19 09:14:47', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2023-05-19 09:14:47', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2023-05-19 09:14:47', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2023-05-19 09:14:47', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '学生管理', 0, 4, 'stu', NULL, NULL, 1, 0, 'M', '0', '0', '', 'qq', 'admin', '2023-05-19 13:33:38', 'admin', '2023-05-19 13:33:58', '');
INSERT INTO `sys_menu` VALUES (2009, '学生信息', 2000, 2, 'info', 'stu/info/index', NULL, 1, 0, 'C', '0', '0', 'stu:info:list', 'row', 'admin', '2023-05-19 14:10:31', 'admin', '2023-05-19 14:45:06', '学生信息菜单');
INSERT INTO `sys_menu` VALUES (2010, '学生信息查询', 2009, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:info:query', '#', 'admin', '2023-05-19 14:10:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2011, '学生信息新增', 2009, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:info:add', '#', 'admin', '2023-05-19 14:10:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2012, '学生信息修改', 2009, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:info:edit', '#', 'admin', '2023-05-19 14:10:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2013, '学生信息删除', 2009, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:info:remove', '#', 'admin', '2023-05-19 14:10:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2014, '学生信息导出', 2009, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:info:export', '#', 'admin', '2023-05-19 14:10:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2015, '提交材料参数', 2000, 3, 'material', 'stu/material/index', NULL, 1, 0, 'C', '0', '0', 'stu:material:list', 'drag', 'admin', '2023-05-19 15:23:52', 'admin', '2023-05-19 15:48:36', '提交材料参数菜单');
INSERT INTO `sys_menu` VALUES (2016, '提交材料参数查询', 2015, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:material:query', '#', 'admin', '2023-05-19 15:23:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2017, '提交材料参数新增', 2015, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:material:add', '#', 'admin', '2023-05-19 15:23:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2018, '提交材料参数修改', 2015, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:material:edit', '#', 'admin', '2023-05-19 15:23:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2019, '提交材料参数删除', 2015, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:material:remove', '#', 'admin', '2023-05-19 15:23:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2020, '提交材料参数导出', 2015, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'stu:material:export', '#', 'admin', '2023-05-19 15:23:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '毕业实践', 2000, 1, 'stu', 'stu/biye/index', NULL, 1, 0, 'C', '0', '0', 'stu:biye:index', 'checkbox', 'admin', '2023-05-19 15:48:28', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2023-05-19 09:14:47', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2023-05-19 09:14:47', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"stu_data,stu_info,stu_tutorial\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 11:24:51', 120);
INSERT INTO `sys_oper_log` VALUES (101, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"qq\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"学生管理\",\"menuType\":\"M\",\"orderNum\":5,\"params\":{},\"parentId\":0,\"path\":\"stu\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:33:38', 15);
INSERT INTO `sys_oper_log` VALUES (102, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-05-19 13:33:38\",\"icon\":\"qq\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2000,\"menuName\":\"学生管理\",\"menuType\":\"M\",\"orderNum\":4,\"params\":{},\"parentId\":0,\"path\":\"stu\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:33:58', 11);
INSERT INTO `sys_oper_log` VALUES (103, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-05-19 09:14:47\",\"icon\":\"guide\",\"isCache\":\"0\",\"isFrame\":\"0\",\"menuId\":4,\"menuName\":\"若依官网\",\"menuType\":\"M\",\"orderNum\":5,\"params\":{},\"parentId\":0,\"path\":\"http://ruoyi.vip\",\"perms\":\"\",\"query\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:34:05', 9);
INSERT INTO `sys_oper_log` VALUES (104, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/biye/index\",\"createBy\":\"admin\",\"icon\":\"email\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"综合实践\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"stu\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:36:20', 9);
INSERT INTO `sys_oper_log` VALUES (105, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/biye/index\",\"createTime\":\"2023-05-19 13:36:20\",\"icon\":\"email\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2001,\"menuName\":\"综合实践\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"stu\",\"perms\":\"stu:biye:index\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:36:53', 10);
INSERT INTO `sys_oper_log` VALUES (106, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"redis-list\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"学生信息\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"stu:info:index\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:46:57', 9);
INSERT INTO `sys_oper_log` VALUES (107, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/info/index\",\"createTime\":\"2023-05-19 13:46:57\",\"icon\":\"redis-list\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2002,\"menuName\":\"学生信息\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"stu\",\"perms\":\"stu:info:index\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:47:41', 9);
INSERT INTO `sys_oper_log` VALUES (108, '字典类型', 5, 'com.ruoyi.web.controller.system.SysDictTypeController.export()', 'POST', 1, 'admin', NULL, '/system/dict/type/export', '127.0.0.1', '内网IP', '{\"pageSize\":\"10\",\"pageNum\":\"1\"}', NULL, 0, NULL, '2023-05-19 13:48:01', 458);
INSERT INTO `sys_oper_log` VALUES (109, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 13:51:24', 161);
INSERT INTO `sys_oper_log` VALUES (110, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 13:51:35', 42);
INSERT INTO `sys_oper_log` VALUES (111, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"info\",\"className\":\"StuInfo\",\"columns\":[{\"capJavaField\":\"StuId\",\"columnId\":5,\"columnName\":\"stu_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"stuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StuNo\",\"columnComment\":\"学生姓名\",\"columnId\":6,\"columnName\":\"stu_no\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StuName\",\"columnComment\":\"基本信息\",\"columnId\":7,\"columnName\":\"stu_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StuCls\",\"columnComment\":\"所在班级\",\"columnId\":8,\"columnName\":\"stu_cls\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuCls\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"req', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:52:52', 38);
INSERT INTO `sys_oper_log` VALUES (112, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"info\",\"className\":\"StuInfo\",\"columns\":[{\"capJavaField\":\"StuId\",\"columnId\":5,\"columnName\":\"stu_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"stuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 13:52:52\",\"usableColumn\":false},{\"capJavaField\":\"StuNo\",\"columnComment\":\"学生姓名\",\"columnId\":6,\"columnName\":\"stu_no\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 13:52:52\",\"usableColumn\":false},{\"capJavaField\":\"StuName\",\"columnComment\":\"基本信息\",\"columnId\":7,\"columnName\":\"stu_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 13:52:52\",\"usableColumn\":false},{\"capJavaField\":\"StuCls\",\"columnComment\":\"所在班级\",\"columnId\":8,\"columnName\":\"stu_cls\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"java', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 13:57:41', 73);
INSERT INTO `sys_oper_log` VALUES (113, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 13:57:45', 171);
INSERT INTO `sys_oper_log` VALUES (114, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/info/index\",\"createTime\":\"2023-05-19 13:46:57\",\"icon\":\"redis-list\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2002,\"menuName\":\"学生信息\",\"menuType\":\"C\",\"orderNum\":2,\"params\":{},\"parentId\":2000,\"path\":\"stu\",\"perms\":\"stu:info:index\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:05:38', 26);
INSERT INTO `sys_oper_log` VALUES (115, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"info\",\"className\":\"StuInfo\",\"columns\":[{\"capJavaField\":\"StuId\",\"columnId\":5,\"columnName\":\"stu_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"stuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 13:57:41\",\"usableColumn\":false},{\"capJavaField\":\"StuNo\",\"columnComment\":\"学生姓名\",\"columnId\":6,\"columnName\":\"stu_no\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 13:57:41\",\"usableColumn\":false},{\"capJavaField\":\"StuName\",\"columnComment\":\"基本信息\",\"columnId\":7,\"columnName\":\"stu_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 13:57:41\",\"usableColumn\":false},{\"capJavaField\":\"StuCls\",\"columnComment\":\"所在班级\",\"columnId\":8,\"columnName\":\"stu_cls\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 11:24:51\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"java', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:09:41', 56);
INSERT INTO `sys_oper_log` VALUES (116, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 14:09:46', 157);
INSERT INTO `sys_oper_log` VALUES (117, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/info/index\",\"createTime\":\"2023-05-19 14:10:31\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2009,\"menuName\":\"学生信息\",\"menuType\":\"C\",\"orderNum\":2,\"params\":{},\"parentId\":2000,\"path\":\"info\",\"perms\":\"stu:info:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:16:12', 9);
INSERT INTO `sys_oper_log` VALUES (118, '代码生成', 3, 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', 1, 'admin', NULL, '/tool/gen/2', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:29:06', 29);
INSERT INTO `sys_oper_log` VALUES (119, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:29:09', 64);
INSERT INTO `sys_oper_log` VALUES (120, '字典类型', 9, 'com.ruoyi.web.controller.system.SysDictTypeController.refreshCache()', 'DELETE', 1, 'admin', NULL, '/system/dict/type/refreshCache', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:33:06', 23);
INSERT INTO `sys_oper_log` VALUES (121, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"年级                   \",\"dictType\":\"stu_cls_year\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:33:58', 13);
INSERT INTO `sys_oper_log` VALUES (122, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"info\",\"className\":\"StuInfo\",\"columns\":[{\"capJavaField\":\"StuId\",\"columnComment\":\"主键id\",\"columnId\":18,\"columnName\":\"stu_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"stuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StuNo\",\"columnComment\":\"学号\",\"columnId\":19,\"columnName\":\"stu_no\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StuName\",\"columnComment\":\"姓名\",\"columnId\":20,\"columnName\":\"stu_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"StuCls\",\"columnComment\":\"班级\",\"columnId\":21,\"columnName\":\"stu_cls\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuCls\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":tru', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:36:33', 47);
INSERT INTO `sys_oper_log` VALUES (123, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 14:36:37', 163);
INSERT INTO `sys_oper_log` VALUES (124, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"info\",\"className\":\"StuInfo\",\"columns\":[{\"capJavaField\":\"StuId\",\"columnComment\":\"主键id\",\"columnId\":18,\"columnName\":\"stu_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"stuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 14:36:33\",\"usableColumn\":false},{\"capJavaField\":\"StuNo\",\"columnComment\":\"学号\",\"columnId\":19,\"columnName\":\"stu_no\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 14:36:33\",\"usableColumn\":false},{\"capJavaField\":\"StuName\",\"columnComment\":\"姓名\",\"columnId\":20,\"columnName\":\"stu_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 14:36:33\",\"usableColumn\":false},{\"capJavaField\":\"StuCls\",\"columnComment\":\"班级\",\"columnId\":21,\"columnName\":\"stu_cls\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:38:30', 48);
INSERT INTO `sys_oper_log` VALUES (125, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 14:38:33', 48);
INSERT INTO `sys_oper_log` VALUES (126, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"info\",\"className\":\"StuInfo\",\"columns\":[{\"capJavaField\":\"StuId\",\"columnComment\":\"主键id\",\"columnId\":18,\"columnName\":\"stu_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"stuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 14:38:30\",\"usableColumn\":false},{\"capJavaField\":\"StuNo\",\"columnComment\":\"学号\",\"columnId\":19,\"columnName\":\"stu_no\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 14:38:30\",\"usableColumn\":false},{\"capJavaField\":\"StuName\",\"columnComment\":\"姓名\",\"columnId\":20,\"columnName\":\"stu_name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"stuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2023-05-19 14:38:30\",\"usableColumn\":false},{\"capJavaField\":\"StuCls\",\"columnComment\":\"班级\",\"columnId\":21,\"columnName\":\"stu_cls\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 14:29:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:39:13', 34);
INSERT INTO `sys_oper_log` VALUES (127, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 14:39:16', 49);
INSERT INTO `sys_oper_log` VALUES (128, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info\"}', NULL, 0, NULL, '2023-05-19 14:39:20', 44);
INSERT INTO `sys_oper_log` VALUES (129, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/info/index\",\"createTime\":\"2023-05-19 14:10:31\",\"icon\":\"row\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2009,\"menuName\":\"学生信息\",\"menuType\":\"C\",\"orderNum\":2,\"params\":{},\"parentId\":2000,\"path\":\"info\",\"perms\":\"stu:info:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 14:45:06', 25);
INSERT INTO `sys_oper_log` VALUES (130, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"stu_material\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:21:39', 51);
INSERT INTO `sys_oper_log` VALUES (131, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"material\",\"className\":\"StuMaterial\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"id\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 15:21:39\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"材料名\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 15:21:39\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Flag\",\"columnComment\":\"提交状态\",\"columnId\":30,\"columnName\":\"flag\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 15:21:39\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"flag\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Url\",\"columnComment\":\"提交地址\",\"columnId\":31,\"columnName\":\"url\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2023-05-19 15:21:39\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"url\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:22:26', 30);
INSERT INTO `sys_oper_log` VALUES (132, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"stu_material\"}', NULL, 0, NULL, '2023-05-19 15:22:29', 136);
INSERT INTO `sys_oper_log` VALUES (133, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":1,\"name\":\"开题报告\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:28:51', 22);
INSERT INTO `sys_oper_log` VALUES (134, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":2,\"name\":\"毕业论文\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:29:02', 6);
INSERT INTO `sys_oper_log` VALUES (135, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":3,\"name\":\"毕业成果\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:29:08', 7);
INSERT INTO `sys_oper_log` VALUES (136, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":4,\"name\":\"顶岗实习报告\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:29:13', 7);
INSERT INTO `sys_oper_log` VALUES (137, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":5,\"name\":\"企业实习报告\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:29:19', 7);
INSERT INTO `sys_oper_log` VALUES (138, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":6,\"name\":\"顶岗实习承诺书\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:01', 6);
INSERT INTO `sys_oper_log` VALUES (139, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":7,\"name\":\"顶岗实习承诺书\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:05', 6);
INSERT INTO `sys_oper_log` VALUES (140, '提交材料参数', 2, 'com.ruoyi.stu.controller.StuMaterialController.edit()', 'PUT', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":7,\"name\":\"顶岗实习申请表\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:12', 9);
INSERT INTO `sys_oper_log` VALUES (141, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":8,\"name\":\"顶岗实习申请表\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:17', 6);
INSERT INTO `sys_oper_log` VALUES (142, '提交材料参数', 2, 'com.ruoyi.stu.controller.StuMaterialController.edit()', 'PUT', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":7,\"name\":\"顶岗实习考核表\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:27', 6);
INSERT INTO `sys_oper_log` VALUES (143, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":9,\"name\":\"实习三方协议\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:38', 7);
INSERT INTO `sys_oper_log` VALUES (144, '提交材料参数', 1, 'com.ruoyi.stu.controller.StuMaterialController.add()', 'POST', 1, 'admin', NULL, '/stu/material', '127.0.0.1', '内网IP', '{\"id\":10,\"name\":\"在实习单位公司的照片\",\"params\":{}}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:30:45', 7);
INSERT INTO `sys_oper_log` VALUES (145, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/material/index\",\"createTime\":\"2023-05-19 15:23:52\",\"icon\":\"drag\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2015,\"menuName\":\"提交材料参数\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"material\",\"perms\":\"stu:material:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:32:07', 18);
INSERT INTO `sys_oper_log` VALUES (146, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2001', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:45:43', 15);
INSERT INTO `sys_oper_log` VALUES (147, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"stu_info_material\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:46:04', 60);
INSERT INTO `sys_oper_log` VALUES (148, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/biye/index\",\"createBy\":\"admin\",\"icon\":\"checkbox\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"毕业实践\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"stu\",\"perms\":\"stu:biye:index\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:48:28', 11);
INSERT INTO `sys_oper_log` VALUES (149, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"stu/material/index\",\"createTime\":\"2023-05-19 15:23:52\",\"icon\":\"drag\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2015,\"menuName\":\"提交材料参数\",\"menuType\":\"C\",\"orderNum\":3,\"params\":{},\"parentId\":2000,\"path\":\"material\",\"perms\":\"stu:material:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-05-19 15:48:36', 11);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2023-05-19 09:14:47', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-05-19 09:14:47', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2023-05-19 09:14:47', '', NULL, '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-05-20 15:25:49', 'admin', '2023-05-19 09:14:47', '', '2023-05-20 15:25:48', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-05-19 09:14:47', 'admin', '2023-05-19 09:14:47', '', NULL, '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
