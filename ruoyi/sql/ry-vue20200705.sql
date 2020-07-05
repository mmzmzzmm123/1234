/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.48.32
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : 192.168.48.32:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 05/07/2020 15:24:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `options` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '代码生成业务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `blob_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', '0/10 * * * * ?', 'America/New_York');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', '0/15 * * * * ?', 'America/New_York');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', '0/20 * * * * ?', 'America/New_York');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', NULL, 'com.ruoyi.common.utils.job.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720027636F6D2E72756F79692E70726F6A6563742E6D6F6E69746F722E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720029636F6D2E72756F79692E6672616D65776F726B2E7765622E646F6D61696E2E42617365456E7469747900000000000000010200094C0009626567696E54696D6571007E00094C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0007656E6454696D6571007E00094C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C78707074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E07870707400007070707400013174000E302F3130202A202A202A202A203F74001172795461736B2E72794E6F506172616D7374000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000001740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', NULL, 'com.ruoyi.common.utils.job.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720027636F6D2E72756F79692E70726F6A6563742E6D6F6E69746F722E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720029636F6D2E72756F79692E6672616D65776F726B2E7765622E646F6D61696E2E42617365456E7469747900000000000000010200094C0009626567696E54696D6571007E00094C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0007656E6454696D6571007E00094C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C78707074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E07870707400007070707400013174000E302F3135202A202A202A202A203F74001572795461736B2E7279506172616D7328277279272974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000002740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E69C89E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', NULL, 'com.ruoyi.common.utils.job.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720027636F6D2E72756F79692E70726F6A6563742E6D6F6E69746F722E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720029636F6D2E72756F79692E6672616D65776F726B2E7765622E646F6D61696E2E42617365456E7469747900000000000000010200094C0009626567696E54696D6571007E00094C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0007656E6454696D6571007E00094C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C78707074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E07870707400007070707400013174000E302F3230202A202A202A202A203F74003872795461736B2E72794D756C7469706C65506172616D7328277279272C20747275652C20323030304C2C203331362E3530442C203130302974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000003740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E5A49AE58F82EFBC8974000133740001317800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler', 'DESKTOP-XI402H1593921298923', 1593933873706, 15000);
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler', 'fxintco.ocserv20201593921381783', 1593933871404, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `int_prop_1` int(11) NULL DEFAULT NULL,
  `int_prop_2` int(11) NULL DEFAULT NULL,
  `long_prop_1` bigint(20) NULL DEFAULT NULL,
  `long_prop_2` bigint(20) NULL DEFAULT NULL,
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL,
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `next_fire_time` bigint(13) NULL DEFAULT NULL,
  `prev_fire_time` bigint(13) NULL DEFAULT NULL,
  `priority` int(11) NULL DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) NULL DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `misfire_instr` smallint(2) NULL DEFAULT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', 'TASK_CLASS_NAME1', 'DEFAULT', NULL, 1593921390000, -1, 5, 'PAUSED', 'CRON', 1593921381000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', 'TASK_CLASS_NAME2', 'DEFAULT', NULL, 1593921390000, -1, 5, 'PAUSED', 'CRON', 1593921382000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', 'TASK_CLASS_NAME3', 'DEFAULT', NULL, 1593921400000, -1, 5, 'PAUSED', 'CRON', 1593921382000, 0, NULL, 2, '');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '参数配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '深色主题theme-dark，浅色主题theme-light');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 202 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '部门表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '英科', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-17 13:46:59');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '上海英科实业', 1, '', '', '', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-17 13:46:59');
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 9, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-17 13:46:42');
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (200, 101, '0,100,101', '信息自动化部', 2, NULL, NULL, NULL, '0', '0', 'admin', '2020-05-17 13:46:35', '', NULL);
INSERT INTO `sys_dept` VALUES (201, 101, '0,100,101', '工艺部', 3, NULL, NULL, NULL, '0', '0', 'admin', '2020-05-17 13:46:52', 'admin', '2020-05-17 13:46:59');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录状态列表');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '定时任务调度表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 164 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 09:16:27');
INSERT INTO `sys_logininfor` VALUES (2, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 10:16:40');
INSERT INTO `sys_logininfor` VALUES (3, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码已失效', '2020-05-16 10:35:58');
INSERT INTO `sys_logininfor` VALUES (4, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 10:35:58');
INSERT INTO `sys_logininfor` VALUES (5, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 11:42:07');
INSERT INTO `sys_logininfor` VALUES (6, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 12:19:41');
INSERT INTO `sys_logininfor` VALUES (7, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 13:06:06');
INSERT INTO `sys_logininfor` VALUES (8, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 19:41:48');
INSERT INTO `sys_logininfor` VALUES (9, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 19:43:13');
INSERT INTO `sys_logininfor` VALUES (10, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 19:44:36');
INSERT INTO `sys_logininfor` VALUES (11, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 21:02:57');
INSERT INTO `sys_logininfor` VALUES (12, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 22:15:08');
INSERT INTO `sys_logininfor` VALUES (13, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-16 22:49:30');
INSERT INTO `sys_logininfor` VALUES (14, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-17 09:36:37');
INSERT INTO `sys_logininfor` VALUES (15, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-17 10:19:40');
INSERT INTO `sys_logininfor` VALUES (16, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 10:53:29');
INSERT INTO `sys_logininfor` VALUES (17, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-17 13:08:28');
INSERT INTO `sys_logininfor` VALUES (18, '邰宝顺', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-17 13:51:30');
INSERT INTO `sys_logininfor` VALUES (19, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 20:46:55');
INSERT INTO `sys_logininfor` VALUES (20, 'admin', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 22:05:40');
INSERT INTO `sys_logininfor` VALUES (21, 'admin', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '退出成功', '2020-05-17 22:06:56');
INSERT INTO `sys_logininfor` VALUES (22, 'admin', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 22:08:46');
INSERT INTO `sys_logininfor` VALUES (23, 'admin', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '退出成功', '2020-05-17 22:09:41');
INSERT INTO `sys_logininfor` VALUES (24, '邰宝顺', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 22:09:51');
INSERT INTO `sys_logininfor` VALUES (25, '邰宝顺', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '退出成功', '2020-05-17 22:10:36');
INSERT INTO `sys_logininfor` VALUES (26, '邰宝顺', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 22:10:46');
INSERT INTO `sys_logininfor` VALUES (27, '邰宝顺', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '退出成功', '2020-05-17 22:15:58');
INSERT INTO `sys_logininfor` VALUES (28, 'admin', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '1', '用户不存在/密码错误', '2020-05-17 22:16:37');
INSERT INTO `sys_logininfor` VALUES (29, 'admin', '192.168.48.24', '内网IP', 'Mobile Safari', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-17 22:56:40');
INSERT INTO `sys_logininfor` VALUES (30, '邰宝顺', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 09:19:43');
INSERT INTO `sys_logininfor` VALUES (31, 'admin', '192.168.56.67', '内网IP', 'Chrome Mobile', 'Mac OS X (iPad)', '0', '登录成功', '2020-05-18 11:00:26');
INSERT INTO `sys_logininfor` VALUES (32, 'admin', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 13:21:02');
INSERT INTO `sys_logininfor` VALUES (33, 'admin', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '退出成功', '2020-05-18 13:50:57');
INSERT INTO `sys_logininfor` VALUES (34, 'admin', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 16:32:02');
INSERT INTO `sys_logininfor` VALUES (35, 'admin', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '退出成功', '2020-05-18 16:32:15');
INSERT INTO `sys_logininfor` VALUES (36, '邰宝顺', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 16:32:24');
INSERT INTO `sys_logininfor` VALUES (37, 'admin', '180.165.246.36', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 17:36:50');
INSERT INTO `sys_logininfor` VALUES (38, 'admin', '180.165.246.36', 'XX XX', 'Chrome Mobile', 'Android 6.x', '0', '登录成功', '2020-05-18 17:37:00');
INSERT INTO `sys_logininfor` VALUES (39, 'admin', '114.82.239.59', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 19:02:17');
INSERT INTO `sys_logininfor` VALUES (40, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-18 19:14:13');
INSERT INTO `sys_logininfor` VALUES (41, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome Mobile', 'Android 6.x', '0', '登录成功', '2020-05-19 08:52:27');
INSERT INTO `sys_logininfor` VALUES (42, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码已失效', '2020-05-19 20:04:29');
INSERT INTO `sys_logininfor` VALUES (43, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-05-19 20:04:29');
INSERT INTO `sys_logininfor` VALUES (44, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-05-19 20:04:34');
INSERT INTO `sys_logininfor` VALUES (45, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-19 20:04:39');
INSERT INTO `sys_logininfor` VALUES (46, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 10:34:12');
INSERT INTO `sys_logininfor` VALUES (47, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 10:36:59');
INSERT INTO `sys_logininfor` VALUES (48, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 10:37:10');
INSERT INTO `sys_logininfor` VALUES (49, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 10:44:21');
INSERT INTO `sys_logininfor` VALUES (50, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 16:04:30');
INSERT INTO `sys_logininfor` VALUES (51, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome', 'Windows 7', '1', '用户不存在/密码错误', '2020-05-20 16:05:15');
INSERT INTO `sys_logininfor` VALUES (52, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-05-20 16:05:33');
INSERT INTO `sys_logininfor` VALUES (53, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome', 'Windows 7', '0', '登录成功', '2020-05-20 16:05:40');
INSERT INTO `sys_logininfor` VALUES (54, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 16:13:09');
INSERT INTO `sys_logininfor` VALUES (55, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-20 16:34:54');
INSERT INTO `sys_logininfor` VALUES (56, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-21 11:40:50');
INSERT INTO `sys_logininfor` VALUES (57, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-21 15:39:46');
INSERT INTO `sys_logininfor` VALUES (58, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-05-22 12:19:26');
INSERT INTO `sys_logininfor` VALUES (59, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-05-22 12:19:34');
INSERT INTO `sys_logininfor` VALUES (60, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-23 20:48:38');
INSERT INTO `sys_logininfor` VALUES (61, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-25 08:55:36');
INSERT INTO `sys_logininfor` VALUES (62, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-26 09:10:01');
INSERT INTO `sys_logininfor` VALUES (63, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-27 14:23:39');
INSERT INTO `sys_logininfor` VALUES (64, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-05-27 15:43:10');
INSERT INTO `sys_logininfor` VALUES (65, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-27 15:44:47');
INSERT INTO `sys_logininfor` VALUES (66, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-27 17:26:49');
INSERT INTO `sys_logininfor` VALUES (67, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-27 19:42:56');
INSERT INTO `sys_logininfor` VALUES (68, 'admin', '114.82.239.59', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-27 19:52:54');
INSERT INTO `sys_logininfor` VALUES (69, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-27 20:26:10');
INSERT INTO `sys_logininfor` VALUES (70, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-28 09:04:31');
INSERT INTO `sys_logininfor` VALUES (71, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-28 12:27:47');
INSERT INTO `sys_logininfor` VALUES (72, 'admin', '180.174.113.191', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-05-28 21:48:53');
INSERT INTO `sys_logininfor` VALUES (73, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-28 21:53:20');
INSERT INTO `sys_logininfor` VALUES (74, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-29 09:00:25');
INSERT INTO `sys_logininfor` VALUES (75, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-05-29 12:45:41');
INSERT INTO `sys_logininfor` VALUES (76, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-05-29 12:45:48');
INSERT INTO `sys_logininfor` VALUES (77, 'admin', '180.174.113.191', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-29 20:07:42');
INSERT INTO `sys_logininfor` VALUES (78, 'admin', '180.174.113.191', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-05-29 21:40:46');
INSERT INTO `sys_logininfor` VALUES (79, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-06-01 10:51:23');
INSERT INTO `sys_logininfor` VALUES (80, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2020-06-02 11:03:48');
INSERT INTO `sys_logininfor` VALUES (81, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-06-02 11:03:52');
INSERT INTO `sys_logininfor` VALUES (82, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-02 11:38:44');
INSERT INTO `sys_logininfor` VALUES (83, 'admin', '180.174.113.191', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-02 22:41:41');
INSERT INTO `sys_logininfor` VALUES (84, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-03 09:33:50');
INSERT INTO `sys_logininfor` VALUES (85, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-03 15:11:39');
INSERT INTO `sys_logininfor` VALUES (86, 'admin', '180.174.113.191', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-06-04 23:20:46');
INSERT INTO `sys_logininfor` VALUES (87, 'admin', '180.174.113.191', '上海市 上海市', 'Firefox Mobile', 'Android 1.x', '1', '验证码错误', '2020-06-04 23:21:14');
INSERT INTO `sys_logininfor` VALUES (88, 'admin', '180.174.113.191', '上海市 上海市', 'Firefox Mobile', 'Android 1.x', '0', '登录成功', '2020-06-04 23:21:19');
INSERT INTO `sys_logininfor` VALUES (89, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-05 11:04:37');
INSERT INTO `sys_logininfor` VALUES (90, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-08 13:47:56');
INSERT INTO `sys_logininfor` VALUES (91, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-08 15:20:14');
INSERT INTO `sys_logininfor` VALUES (92, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-08 16:24:41');
INSERT INTO `sys_logininfor` VALUES (93, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-08 21:12:00');
INSERT INTO `sys_logininfor` VALUES (94, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-09 09:06:50');
INSERT INTO `sys_logininfor` VALUES (95, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-06-10 09:04:12');
INSERT INTO `sys_logininfor` VALUES (96, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-10 09:04:21');
INSERT INTO `sys_logininfor` VALUES (97, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-10 12:38:28');
INSERT INTO `sys_logininfor` VALUES (98, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-11 10:27:45');
INSERT INTO `sys_logininfor` VALUES (99, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-06-11 14:59:36');
INSERT INTO `sys_logininfor` VALUES (100, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-11 14:59:43');
INSERT INTO `sys_logininfor` VALUES (101, '邰宝顺', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-12 09:00:46');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '180.165.246.36', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-12 15:29:12');
INSERT INTO `sys_logininfor` VALUES (103, '邰宝顺', '122.226.183.163', '浙江省 台州市', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-06-18 16:09:38');
INSERT INTO `sys_logininfor` VALUES (104, '邰宝顺', '122.226.183.166', '浙江省 台州市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-06-18 16:09:47');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-20 09:00:18');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-21 12:06:07');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2020-06-22 12:41:38');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2020-06-22 12:41:53');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2020-06-22 12:41:55');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2020-06-22 12:42:10');
INSERT INTO `sys_logininfor` VALUES (111, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-22 12:42:56');
INSERT INTO `sys_logininfor` VALUES (112, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '验证码已失效', '2020-06-22 12:45:39');
INSERT INTO `sys_logininfor` VALUES (113, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2020-06-22 12:45:44');
INSERT INTO `sys_logininfor` VALUES (114, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2020-06-22 12:45:50');
INSERT INTO `sys_logininfor` VALUES (115, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2020-06-22 12:45:58');
INSERT INTO `sys_logininfor` VALUES (116, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-06-22 12:46:13');
INSERT INTO `sys_logininfor` VALUES (117, '金喆', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-22 12:48:58');
INSERT INTO `sys_logininfor` VALUES (118, '金喆', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '退出成功', '2020-06-22 12:49:10');
INSERT INTO `sys_logininfor` VALUES (119, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-22 12:49:15');
INSERT INTO `sys_logininfor` VALUES (120, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '退出成功', '2020-06-22 12:50:04');
INSERT INTO `sys_logininfor` VALUES (121, '金喆', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-22 12:50:12');
INSERT INTO `sys_logininfor` VALUES (122, '金喆', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-22 12:50:16');
INSERT INTO `sys_logininfor` VALUES (123, 'admin', '180.165.247.151', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-23 16:17:49');
INSERT INTO `sys_logininfor` VALUES (124, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码已失效', '2020-06-30 20:02:41');
INSERT INTO `sys_logininfor` VALUES (125, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-30 20:02:41');
INSERT INTO `sys_logininfor` VALUES (126, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-30 20:06:31');
INSERT INTO `sys_logininfor` VALUES (127, '邰宝顺', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-07-01 14:52:13');
INSERT INTO `sys_logininfor` VALUES (128, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-01 15:04:14');
INSERT INTO `sys_logininfor` VALUES (129, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-01 16:31:59');
INSERT INTO `sys_logininfor` VALUES (130, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-01 16:32:04');
INSERT INTO `sys_logininfor` VALUES (131, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-01 21:45:10');
INSERT INTO `sys_logininfor` VALUES (132, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 10:50:56');
INSERT INTO `sys_logininfor` VALUES (133, '邰宝顺', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-07-02 11:13:23');
INSERT INTO `sys_logininfor` VALUES (134, 'admin', '192.168.50.190', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 15:00:15');
INSERT INTO `sys_logininfor` VALUES (135, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 17:24:59');
INSERT INTO `sys_logininfor` VALUES (136, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-07-02 17:40:12');
INSERT INTO `sys_logininfor` VALUES (137, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 19:04:27');
INSERT INTO `sys_logininfor` VALUES (138, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 23:47:18');
INSERT INTO `sys_logininfor` VALUES (139, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-03 08:52:07');
INSERT INTO `sys_logininfor` VALUES (140, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-03 08:52:10');
INSERT INTO `sys_logininfor` VALUES (141, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2020-07-03 09:56:46');
INSERT INTO `sys_logininfor` VALUES (142, '邰宝顺', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-07-03 09:58:06');
INSERT INTO `sys_logininfor` VALUES (143, 'admin', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-07-03 09:58:31');
INSERT INTO `sys_logininfor` VALUES (144, '吕俊', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '0', '登录成功', '2020-07-03 10:28:42');
INSERT INTO `sys_logininfor` VALUES (145, '胡家伟', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 10', '0', '登录成功', '2020-07-03 10:49:45');
INSERT INTO `sys_logininfor` VALUES (146, '唐加民', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '0', '登录成功', '2020-07-03 10:49:52');
INSERT INTO `sys_logininfor` VALUES (147, '唐力', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '1', '用户不存在/密码错误', '2020-07-03 10:51:59');
INSERT INTO `sys_logininfor` VALUES (148, '唐力', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-07-03 10:52:13');
INSERT INTO `sys_logininfor` VALUES (149, '唐力', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-07-03 10:52:20');
INSERT INTO `sys_logininfor` VALUES (150, '唐力', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '0', '登录成功', '2020-07-03 10:52:30');
INSERT INTO `sys_logininfor` VALUES (151, '吕俊', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-07-03 10:53:24');
INSERT INTO `sys_logininfor` VALUES (152, '吕俊', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-07-03 10:53:26');
INSERT INTO `sys_logininfor` VALUES (153, '吕俊', '180.165.244.98', '上海市 上海市', 'Chrome', 'Windows 7', '0', '登录成功', '2020-07-03 10:53:33');
INSERT INTO `sys_logininfor` VALUES (154, '唐加民', '180.165.244.98', '上海市 上海市', 'Chrome 8', 'Windows 7', '0', '登录成功', '2020-07-03 10:58:18');
INSERT INTO `sys_logininfor` VALUES (155, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-03 23:47:25');
INSERT INTO `sys_logininfor` VALUES (156, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-04 12:01:33');
INSERT INTO `sys_logininfor` VALUES (157, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-04 14:14:31');
INSERT INTO `sys_logininfor` VALUES (158, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-04 22:51:05');
INSERT INTO `sys_logininfor` VALUES (159, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-05 10:30:43');
INSERT INTO `sys_logininfor` VALUES (160, 'admin', '222.69.26.101', '上海市 上海市', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-05 10:30:47');
INSERT INTO `sys_logininfor` VALUES (161, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-05 11:33:51');
INSERT INTO `sys_logininfor` VALUES (162, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-05 11:52:40');
INSERT INTO `sys_logininfor` VALUES (163, 'admin', '222.69.26.101', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-05 11:56:36');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2025 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 10, 'system', NULL, 1, 'M', '0', '0', '', 'system', 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-16 09:27:38', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 11, 'monitor', NULL, 1, 'M', '0', '0', '', 'monitor', 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-16 09:27:44', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 12, 'tool', NULL, 1, 'M', '0', '0', '', 'tool', 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-16 09:27:47', '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', 1, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', 1, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', 1, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', 1, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', 1, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', 'system/log/index', 1, 'M', '0', '0', '', 'log', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 1, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', 1, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', 1, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', 1, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '表单构建', 3, 1, 'build', 'tool/build/index', 1, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `sys_menu` VALUES (114, '代码生成', 3, 2, 'gen', 'tool/gen/index', 1, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `sys_menu` VALUES (115, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', 1, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', 1, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', 1, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system:user:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system:role:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', 1, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', 1, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', 1, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', 1, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', 1, 'F', '0', '0', 'system:post:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', 1, 'F', '0', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', 1, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', 1, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', 1, 'F', '0', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', 1, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', 1, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', 1, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', 1, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', 1, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', 1, 'F', '0', '0', 'system:config:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', 1, 'F', '0', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', 1, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', 1, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', 1, 'F', '0', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', 1, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', 1, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', 1, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', 1, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', 1, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', 1, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', 1, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', 1, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', 1, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', 1, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', 1, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', 1, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', 1, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', 1, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', 1, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', 1, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', 1, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', 1, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 7, '#', '', 1, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 114, 1, '#', '', 1, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 114, 2, '#', '', 1, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 114, 3, '#', '', 1, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 114, 2, '#', '', 1, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 114, 4, '#', '', 1, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 114, 5, '#', '', 1, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (2003, '车间概况', 0, 1, 'fx_dashboard', NULL, 1, 'M', '0', '0', '', 'list', 'admin', '2020-05-15 23:04:45', 'admin', '2020-05-16 09:31:43', '');
INSERT INTO `sys_menu` VALUES (2004, '车间当班报表', 2003, 1, 'dashboard_group', 'fx_dashboard/dashboard_group', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 09:24:44', 'admin', '2020-05-16 09:32:30', '');
INSERT INTO `sys_menu` VALUES (2005, '车间24小时报表', 2003, 2, 'dashboard_lastday', 'fx_dashboard/dashboard_lastday', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 09:25:36', 'admin', '2020-05-16 09:32:21', '');
INSERT INTO `sys_menu` VALUES (2006, '车间本月报表', 2003, 3, 'dashboard_30day', 'fx_dashboard/dashboard_30day', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 09:26:31', 'admin', '2020-05-16 09:32:37', '');
INSERT INTO `sys_menu` VALUES (2007, '自定义车间报表', 2003, 4, 'dashboard_daterange', 'fx_dashboard/dashboard_daterange', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 09:27:05', 'admin', '2020-05-16 09:32:46', '');
INSERT INTO `sys_menu` VALUES (2008, '产能', 0, 2, 'fx_energy', NULL, 1, 'M', '0', '0', '', 'list', 'admin', '2020-05-16 09:29:10', 'admin', '2020-05-16 09:32:52', '');
INSERT INTO `sys_menu` VALUES (2009, '密度', 0, 3, 'fx_density', NULL, 1, 'M', '0', '0', '', 'list', 'admin', '2020-05-16 09:29:29', 'admin', '2020-05-16 09:33:17', '');
INSERT INTO `sys_menu` VALUES (2010, '正品率', 0, 4, 'fx_yield', NULL, 1, 'M', '0', '0', '', 'list', 'admin', '2020-05-16 09:30:11', 'admin', '2020-05-16 09:33:23', '');
INSERT INTO `sys_menu` VALUES (2011, '换模时间', 0, 5, 'fx_changemouldtime', NULL, 1, 'M', '0', '0', '', 'list', 'admin', '2020-05-16 09:30:34', 'admin', '2020-05-16 09:33:27', '');
INSERT INTO `sys_menu` VALUES (2012, '当班产能', 2008, 1, 'fx_energy_group', 'fx_energy/fx_energy_group', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 09:31:34', 'admin', '2020-05-16 09:33:10', '');
INSERT INTO `sys_menu` VALUES (2013, '当班密度', 2009, 1, 'fx_density_group', 'fx_density/fx_density_group', 1, 'C', '0', '0', NULL, 'chart', 'admin', '2020-05-16 10:17:22', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2014, '72小时密度', 2009, 2, 'fx_density_72h', 'fx_density/fx_density_72h', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 10:17:50', 'admin', '2020-05-16 10:18:05', '');
INSERT INTO `sys_menu` VALUES (2015, '30天密度', 2009, 3, 'fx_density_30d', 'fx_density/fx_density_30d', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 10:18:26', 'admin', '2020-05-16 10:25:37', '');
INSERT INTO `sys_menu` VALUES (2016, '当班正品率', 2010, 1, 'fx_yield_group', 'fx_yield/fx_yield_group', 1, 'C', '0', '0', NULL, 'chart', 'admin', '2020-05-16 10:19:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2017, '历史换模时间', 2011, 1, 'fx_changemouldtime_list', 'fx_changemouldtime/changemouldtime_list', 1, 'C', '0', '0', '', 'chart', 'admin', '2020-05-16 10:19:59', 'admin', '2020-05-17 20:47:08', '');
INSERT INTO `sys_menu` VALUES (2018, '测试', 0, 19, 'index_bak', 'index_bak', 1, 'C', '0', '0', NULL, '404', 'admin', '2020-05-27 20:02:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2019, '工艺部管理', 0, 6, 'fx_technologist', NULL, 1, 'M', '0', '0', '', 'server', 'admin', '2020-06-08 21:19:28', 'admin', '2020-06-30 21:29:03', '');
INSERT INTO `sys_menu` VALUES (2020, '打样确认率', 2019, 3, 'fx_sample_confirmation', 'fx_technologist/fx_sample_confirmation', 1, 'C', '0', '0', '', 'star', 'admin', '2020-06-30 20:10:31', 'admin', '2020-06-30 21:33:12', '');
INSERT INTO `sys_menu` VALUES (2021, '爆款', 2019, 4, 'fx_sample_best_selling', 'fx_technologist/fx_sample_best_selling', 1, 'C', '0', '0', NULL, 'shopping', 'admin', '2020-06-30 21:37:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2022, '打样天数报表', 2019, 2, 'fx_sample_sample_use_day', 'fx_technologist/fx_sample_sample_use_day', 1, 'C', '0', '0', NULL, 'guide', 'admin', '2020-07-01 23:01:16', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2023, '日报', 2019, 1, 'fx_sample_day_report', 'fx_technologist/fx_sample_day_report', 1, 'C', '0', '0', NULL, 'cascader', 'admin', '2020-07-02 17:18:04', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '总表', 2019, 5, 'fx_sample_all', 'fx_technologist/fx_sample_all', 1, 'C', '0', '0', NULL, 'checkbox', 'admin', '2020-07-04 12:46:19', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '通知公告表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (1, '操作日志', 9, 'com.ruoyi.project.monitor.controller.SysOperlogController.clean()', 'DELETE', 1, 'admin', NULL, '/monitor/operlog/clean', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-15 23:02:13');
INSERT INTO `sys_oper_log` VALUES (2, '登陆日志', 9, 'com.ruoyi.project.monitor.controller.SysLogininforController.clean()', 'DELETE', 1, 'admin', NULL, '/monitor/logininfor/clean', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-15 23:02:17');
INSERT INTO `sys_oper_log` VALUES (3, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"1\",\"menuName\":\"车间概况\",\"params\":{},\"parentId\":0,\"path\":\"fxintcodashboard\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-15 23:04:45');
INSERT INTO `sys_oper_log` VALUES (4, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"system\",\"orderNum\":\"2\",\"menuName\":\"系统管理\",\"params\":{},\"parentId\":0,\"path\":\"system\",\"children\":[],\"createTime\":1521171180000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":1,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-15 23:04:50');
INSERT INTO `sys_oper_log` VALUES (5, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"1\",\"menuName\":\"车间概况\",\"params\":{},\"parentId\":0,\"path\":\"fxdashboard\",\"children\":[],\"createTime\":1589555085000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2003,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:22:12');
INSERT INTO `sys_oper_log` VALUES (6, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"车间当班报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboardgroup\",\"component\":\"fxdashboard/dashboardgroup\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:24:44');
INSERT INTO `sys_oper_log` VALUES (7, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"2\",\"menuName\":\"车间24小时报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboardlastday\",\"component\":\"fxdashboard/dashboardlastday\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:25:36');
INSERT INTO `sys_oper_log` VALUES (8, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"车间本月报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboard30day\",\"component\":\"fxdashboard/dashboard30day\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:26:31');
INSERT INTO `sys_oper_log` VALUES (9, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"4\",\"menuName\":\"自定义车间报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboarddaterange\",\"component\":\"fxdashboard/dashboarddaterange\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:27:05');
INSERT INTO `sys_oper_log` VALUES (10, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"system\",\"orderNum\":\"10\",\"menuName\":\"系统管理\",\"params\":{},\"parentId\":0,\"path\":\"system\",\"children\":[],\"createTime\":1521171180000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":1,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:27:38');
INSERT INTO `sys_oper_log` VALUES (11, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"monitor\",\"orderNum\":\"11\",\"menuName\":\"系统监控\",\"params\":{},\"parentId\":0,\"path\":\"monitor\",\"children\":[],\"createTime\":1521171180000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:27:44');
INSERT INTO `sys_oper_log` VALUES (12, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"tool\",\"orderNum\":\"12\",\"menuName\":\"系统工具\",\"params\":{},\"parentId\":0,\"path\":\"tool\",\"children\":[],\"createTime\":1521171180000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":3,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:27:47');
INSERT INTO `sys_oper_log` VALUES (13, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"#\",\"orderNum\":\"13\",\"menuName\":\"test001\",\"params\":{},\"parentId\":0,\"path\":\"test001\",\"children\":[],\"createTime\":1589378623000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2000,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:27:49');
INSERT INTO `sys_oper_log` VALUES (14, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"2\",\"menuName\":\"产能\",\"params\":{},\"parentId\":0,\"path\":\"fxenergy\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:29:10');
INSERT INTO `sys_oper_log` VALUES (15, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"3\",\"menuName\":\"密度\",\"params\":{},\"path\":\"fxdensity\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:29:29');
INSERT INTO `sys_oper_log` VALUES (16, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"4\",\"menuName\":\"正品率\",\"params\":{},\"path\":\"fxyield\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:30:11');
INSERT INTO `sys_oper_log` VALUES (17, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"5\",\"menuName\":\"换模时间\",\"params\":{},\"parentId\":0,\"path\":\"fxchangemouldtime\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:30:34');
INSERT INTO `sys_oper_log` VALUES (18, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班产能\",\"params\":{},\"parentId\":2008,\"path\":\"fx_energy_group\",\"component\":\"fxenergy/group\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:31:34');
INSERT INTO `sys_oper_log` VALUES (19, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"1\",\"menuName\":\"车间概况\",\"params\":{},\"parentId\":0,\"path\":\"fx_dashboard\",\"children\":[],\"createTime\":1589555085000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2003,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:31:43');
INSERT INTO `sys_oper_log` VALUES (20, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"车间当班报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboard_group\",\"component\":\"fx_dashboard/dash_boardgroup\",\"children\":[],\"createTime\":1589592284000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:31:55');
INSERT INTO `sys_oper_log` VALUES (21, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"2\",\"menuName\":\"车间24小时报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboard_lastday\",\"component\":\"fx_dashboard/dashboard_lastday\",\"children\":[],\"createTime\":1589592336000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2005,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:32:21');
INSERT INTO `sys_oper_log` VALUES (22, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"车间当班报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboard_group\",\"component\":\"fx_dashboard/dashboard_group\",\"children\":[],\"createTime\":1589592284000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2004,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:32:30');
INSERT INTO `sys_oper_log` VALUES (23, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"车间本月报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboard_30day\",\"component\":\"fx_dashboard/dashboard_30day\",\"children\":[],\"createTime\":1589592391000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2006,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:32:37');
INSERT INTO `sys_oper_log` VALUES (24, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"4\",\"menuName\":\"自定义车间报表\",\"params\":{},\"parentId\":2003,\"path\":\"dashboard_daterange\",\"component\":\"fx_dashboard/dashboard_daterange\",\"children\":[],\"createTime\":1589592425000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2007,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:32:46');
INSERT INTO `sys_oper_log` VALUES (25, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"2\",\"menuName\":\"产能\",\"params\":{},\"parentId\":0,\"path\":\"fx_energy\",\"children\":[],\"createTime\":1589592550000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2008,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:32:52');
INSERT INTO `sys_oper_log` VALUES (26, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班产能\",\"params\":{},\"parentId\":2008,\"path\":\"fx_energy_group\",\"component\":\"fx_energy/group\",\"children\":[],\"createTime\":1589592694000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2012,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:33:00');
INSERT INTO `sys_oper_log` VALUES (27, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班产能\",\"params\":{},\"parentId\":2008,\"path\":\"fx_energy_group\",\"component\":\"fx_energy/fx_energy_group\",\"children\":[],\"createTime\":1589592694000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2012,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:33:10');
INSERT INTO `sys_oper_log` VALUES (28, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"3\",\"menuName\":\"密度\",\"params\":{},\"parentId\":0,\"path\":\"fx_density\",\"children\":[],\"createTime\":1589592569000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2009,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:33:17');
INSERT INTO `sys_oper_log` VALUES (29, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"4\",\"menuName\":\"正品率\",\"params\":{},\"parentId\":0,\"path\":\"fx_yield\",\"children\":[],\"createTime\":1589592611000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2010,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:33:23');
INSERT INTO `sys_oper_log` VALUES (30, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"list\",\"orderNum\":\"5\",\"menuName\":\"换模时间\",\"params\":{},\"parentId\":0,\"path\":\"fx_changemouldtime\",\"children\":[],\"createTime\":1589592634000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2011,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 09:33:27');
INSERT INTO `sys_oper_log` VALUES (31, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班密度\",\"params\":{},\"parentId\":2009,\"path\":\"fx_density_group\",\"component\":\"fx_density/fx_density_group\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:17:22');
INSERT INTO `sys_oper_log` VALUES (32, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"orderNum\":\"2\",\"menuName\":\"72小时密度\",\"params\":{},\"parentId\":2009,\"path\":\"fx_density_72h\",\"component\":\"fx_density/fx_density_72h\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:17:50');
INSERT INTO `sys_oper_log` VALUES (33, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"2\",\"menuName\":\"72小时密度\",\"params\":{},\"parentId\":2009,\"path\":\"fx_density_72h\",\"component\":\"fx_density/fx_density_72h\",\"children\":[],\"createTime\":1589595470000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2014,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:18:05');
INSERT INTO `sys_oper_log` VALUES (34, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"30天密度\",\"params\":{},\"parentId\":2009,\"path\":\"fx_density_72h\",\"component\":\"fx_density/fx_density_72h\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:18:26');
INSERT INTO `sys_oper_log` VALUES (35, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班正品率\",\"params\":{},\"parentId\":2010,\"path\":\"fx_yield_group\",\"component\":\"fx_yield/fx_yield_group\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:19:23');
INSERT INTO `sys_oper_log` VALUES (36, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班换模时间\",\"params\":{},\"parentId\":2011,\"path\":\"fx_changemouldtime_group\",\"component\":\"fx_changemouldtime/fx_changemouldtime_group\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:19:59');
INSERT INTO `sys_oper_log` VALUES (37, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"3\",\"menuName\":\"30天密度\",\"params\":{},\"parentId\":2009,\"path\":\"fx_density_30d\",\"component\":\"fx_density/fx_density_30d\",\"children\":[],\"createTime\":1589595506000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2015,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-16 10:25:37');
INSERT INTO `sys_oper_log` VALUES (38, '部门管理', 2, 'com.ruoyi.project.system.controller.SysDeptController.edit()', 'PUT', 1, 'admin', NULL, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"英科\",\"leader\":\"若依\",\"deptId\":100,\"orderNum\":\"0\",\"delFlag\":\"0\",\"params\":{},\"parentId\":0,\"createBy\":\"admin\",\"children\":[],\"createTime\":1521171180000,\"phone\":\"15888888888\",\"updateBy\":\"admin\",\"ancestors\":\"0\",\"email\":\"ry@qq.com\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:45:32');
INSERT INTO `sys_oper_log` VALUES (39, '部门管理', 2, 'com.ruoyi.project.system.controller.SysDeptController.edit()', 'PUT', 1, 'admin', NULL, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"上海英科实业\",\"leader\":\"\",\"deptId\":101,\"orderNum\":\"1\",\"delFlag\":\"0\",\"params\":{},\"parentId\":100,\"createBy\":\"admin\",\"children\":[],\"createTime\":1521171180000,\"phone\":\"\",\"updateBy\":\"admin\",\"ancestors\":\"0,100\",\"email\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:45:50');
INSERT INTO `sys_oper_log` VALUES (40, '部门管理', 3, 'com.ruoyi.project.system.controller.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/105', '127.0.0.1', '内网IP', '{deptId=105}', '{\"msg\":\"部门存在用户,不允许删除\",\"code\":500}', 0, NULL, '2020-05-17 13:45:58');
INSERT INTO `sys_oper_log` VALUES (41, '部门管理', 3, 'com.ruoyi.project.system.controller.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/107', '127.0.0.1', '内网IP', '{deptId=107}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:12');
INSERT INTO `sys_oper_log` VALUES (42, '部门管理', 3, 'com.ruoyi.project.system.controller.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/106', '127.0.0.1', '内网IP', '{deptId=106}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:13');
INSERT INTO `sys_oper_log` VALUES (43, '部门管理', 3, 'com.ruoyi.project.system.controller.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/103', '127.0.0.1', '内网IP', '{deptId=103}', '{\"msg\":\"部门存在用户,不允许删除\",\"code\":500}', 0, NULL, '2020-05-17 13:46:17');
INSERT INTO `sys_oper_log` VALUES (44, '部门管理', 3, 'com.ruoyi.project.system.controller.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/104', '127.0.0.1', '内网IP', '{deptId=104}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:20');
INSERT INTO `sys_oper_log` VALUES (45, '部门管理', 1, 'com.ruoyi.project.system.controller.SysDeptController.add()', 'POST', 1, 'admin', NULL, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"信息自动化部\",\"orderNum\":\"2\",\"params\":{},\"parentId\":101,\"createBy\":\"admin\",\"children\":[],\"ancestors\":\"0,100,101\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:35');
INSERT INTO `sys_oper_log` VALUES (46, '部门管理', 2, 'com.ruoyi.project.system.controller.SysDeptController.edit()', 'PUT', 1, 'admin', NULL, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"测试部门\",\"leader\":\"若依\",\"deptId\":105,\"orderNum\":\"9\",\"delFlag\":\"0\",\"params\":{},\"parentId\":101,\"createBy\":\"admin\",\"children\":[],\"createTime\":1521171180000,\"phone\":\"15888888888\",\"updateBy\":\"admin\",\"ancestors\":\"0,100,101\",\"email\":\"ry@qq.com\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:42');
INSERT INTO `sys_oper_log` VALUES (47, '部门管理', 1, 'com.ruoyi.project.system.controller.SysDeptController.add()', 'POST', 1, 'admin', NULL, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"工艺部\",\"orderNum\":\"3\",\"params\":{},\"parentId\":103,\"createBy\":\"admin\",\"children\":[],\"ancestors\":\"0,100,101,103\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:52');
INSERT INTO `sys_oper_log` VALUES (48, '部门管理', 2, 'com.ruoyi.project.system.controller.SysDeptController.edit()', 'PUT', 1, 'admin', NULL, '/system/dept', '127.0.0.1', '内网IP', '{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"delFlag\":\"0\",\"params\":{},\"parentId\":101,\"createBy\":\"admin\",\"children\":[],\"createTime\":1589694412000,\"updateBy\":\"admin\",\"ancestors\":\"0,100,101\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:46:59');
INSERT INTO `sys_oper_log` VALUES (49, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":1,\"admin\":true,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"1\",\"roleKey\":\"admin\",\"roleName\":\"管理员\",\"status\":\"0\"}],\"phonenumber\":\"15888888888\",\"admin\":true,\"loginDate\":1521171180000,\"remark\":\"管理员\",\"delFlag\":\"0\",\"password\":\"\",\"postIds\":[1],\"loginIp\":\"127.0.0.1\",\"email\":\"chenyu@intco.com.cn\",\"nickName\":\"小陈\",\"sex\":\"0\",\"deptId\":200,\"avatar\":\"\",\"dept\":{\"deptName\":\"研发部门\",\"leader\":\"若依\",\"deptId\":103,\"orderNum\":\"1\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"admin\",\"userId\":1,\"createBy\":\"admin\",\"roleIds\":[1],\"createTime\":1521171180000,\"status\":\"0\"}', 'null', 1, '不允许操作超级管理员用户', '2020-05-17 13:47:25');
INSERT INTO `sys_oper_log` VALUES (50, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":1,\"admin\":true,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"1\",\"roleKey\":\"admin\",\"roleName\":\"管理员\",\"status\":\"0\"}],\"phonenumber\":\"18019321071\",\"admin\":true,\"loginDate\":1521171180000,\"remark\":\"管理员\",\"delFlag\":\"0\",\"password\":\"\",\"postIds\":[1],\"loginIp\":\"127.0.0.1\",\"email\":\"chenyu@intco.com.cn\",\"nickName\":\"小陈\",\"sex\":\"0\",\"deptId\":103,\"avatar\":\"\",\"dept\":{\"deptName\":\"研发部门\",\"leader\":\"若依\",\"deptId\":103,\"orderNum\":\"1\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"admin\",\"userId\":1,\"createBy\":\"admin\",\"roleIds\":[1],\"createTime\":1521171180000,\"status\":\"0\"}', 'null', 1, '不允许操作超级管理员用户', '2020-05-17 13:47:46');
INSERT INTO `sys_oper_log` VALUES (51, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":1,\"admin\":true,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"1\",\"roleKey\":\"admin\",\"roleName\":\"管理员\",\"status\":\"0\"}],\"phonenumber\":\"18019321071\",\"admin\":true,\"loginDate\":1521171180000,\"remark\":\"管理员\",\"delFlag\":\"0\",\"password\":\"\",\"postIds\":[1],\"loginIp\":\"127.0.0.1\",\"email\":\"chenyu@intco.com.cn\",\"nickName\":\"小陈\",\"sex\":\"0\",\"deptId\":103,\"avatar\":\"\",\"dept\":{\"deptName\":\"研发部门\",\"leader\":\"若依\",\"deptId\":103,\"orderNum\":\"1\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"admin\",\"userId\":1,\"createBy\":\"admin\",\"roleIds\":[1],\"createTime\":1521171180000,\"status\":\"0\"}', 'null', 1, '不允许操作超级管理员用户', '2020-05-17 13:47:48');
INSERT INTO `sys_oper_log` VALUES (52, '个人信息', 2, 'com.ruoyi.project.system.controller.SysProfileController.updateProfile()', 'PUT', 1, 'admin', NULL, '/system/user/profile', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":1,\"admin\":true,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"1\",\"roleKey\":\"admin\",\"roleName\":\"管理员\",\"status\":\"0\"}],\"phonenumber\":\"18019321071\",\"admin\":true,\"loginDate\":1521171180000,\"remark\":\"管理员\",\"delFlag\":\"0\",\"password\":\"$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2\",\"loginIp\":\"127.0.0.1\",\"email\":\"chenyu@intco.com.cn\",\"nickName\":\"SuperAdmin\",\"sex\":\"0\",\"deptId\":103,\"avatar\":\"\",\"dept\":{\"deptName\":\"研发部门\",\"leader\":\"若依\",\"deptId\":103,\"orderNum\":\"1\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"admin\",\"userId\":1,\"createBy\":\"admin\",\"createTime\":1521171180000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:48:16');
INSERT INTO `sys_oper_log` VALUES (53, '用户管理', 3, 'com.ruoyi.project.system.controller.SysUserController.remove()', 'DELETE', 1, 'admin', NULL, '/system/user/2', '127.0.0.1', '内网IP', '{userIds=2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:48:37');
INSERT INTO `sys_oper_log` VALUES (54, '用户管理', 1, 'com.ruoyi.project.system.controller.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"phonenumber\":\"18988887222\",\"admin\":false,\"password\":\"$2a$10$iaQCG8JwhnMm5OVg0YuvE.v5pLHtH6BaLfVOYeTQT.DjeVbBD3Di.\",\"postIds\":[],\"email\":\"taibaoshun@intco.cm.cn\",\"nickName\":\"邰宝顺\",\"sex\":\"0\",\"deptId\":201,\"params\":{},\"userName\":\"邰宝顺\",\"userId\":100,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:49:52');
INSERT INTO `sys_oper_log` VALUES (55, '角色管理', 3, 'com.ruoyi.project.system.controller.SysRoleController.remove()', 'DELETE', 1, 'admin', NULL, '/system/role/2', '127.0.0.1', '内网IP', '{roleIds=2}', 'null', 1, '普通角色已分配,不能删除', '2020-05-17 13:49:59');
INSERT INTO `sys_oper_log` VALUES (56, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"updateBy\":\"admin\",\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"menuIds\":[2003,2004,2005,2006,2007,2008,2012,2009,2013,2014,2015,2010,2016,2011,2017],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:51:15');
INSERT INTO `sys_oper_log` VALUES (57, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.dataScope()', 'PUT', 1, 'admin', NULL, '/system/role/dataScope', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"deptIds\":[100,101,103,200,201,105],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:52:00');
INSERT INTO `sys_oper_log` VALUES (58, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[],\"phonenumber\":\"18988887222\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[],\"loginIp\":\"\",\"email\":\"taibaoshun@intco.cm.cn\",\"nickName\":\"邰宝顺\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"邰宝顺\",\"userId\":100,\"createBy\":\"admin\",\"roleIds\":[2],\"createTime\":1589694592000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 13:52:41');
INSERT INTO `sys_oper_log` VALUES (59, '菜单管理', 3, 'com.ruoyi.project.system.controller.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2000', '127.0.0.1', '内网IP', '{menuId=2000}', '{\"msg\":\"存在子菜单,不允许删除\",\"code\":500}', 0, NULL, '2020-05-17 16:27:38');
INSERT INTO `sys_oper_log` VALUES (60, '菜单管理', 3, 'com.ruoyi.project.system.controller.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2001', '127.0.0.1', '内网IP', '{menuId=2001}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 16:27:42');
INSERT INTO `sys_oper_log` VALUES (61, '菜单管理', 3, 'com.ruoyi.project.system.controller.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2002', '127.0.0.1', '内网IP', '{menuId=2002}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 16:27:43');
INSERT INTO `sys_oper_log` VALUES (62, '菜单管理', 3, 'com.ruoyi.project.system.controller.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2000', '127.0.0.1', '内网IP', '{menuId=2000}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 16:27:45');
INSERT INTO `sys_oper_log` VALUES (63, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.dataScope()', 'PUT', 1, 'admin', NULL, '/system/role/dataScope', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"deptIds\":[100,101,103,200,201,105],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 16:31:46');
INSERT INTO `sys_oper_log` VALUES (64, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"updateBy\":\"admin\",\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 16:32:14');
INSERT INTO `sys_oper_log` VALUES (65, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"当班换模时间\",\"params\":{},\"parentId\":2011,\"path\":\"fx_changemouldtime_list\",\"component\":\"fx_changemouldtime/changemouldtime_list\",\"children\":[],\"createTime\":1589595599000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2017,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 16:58:32');
INSERT INTO `sys_oper_log` VALUES (66, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"chart\",\"orderNum\":\"1\",\"menuName\":\"历史换模时间\",\"params\":{},\"parentId\":2011,\"path\":\"fx_changemouldtime_list\",\"component\":\"fx_changemouldtime/changemouldtime_list\",\"children\":[],\"createTime\":1589595599000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2017,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 20:47:08');
INSERT INTO `sys_oper_log` VALUES (67, '个人信息', 2, 'com.ruoyi.project.system.controller.SysProfileController.updatePwd()', 'PUT', 1, 'admin', NULL, '/system/user/profile/updatePwd', '192.168.48.24', '内网IP', 'admin Intco888', '{\"msg\":\"修改密码失败，旧密码错误\",\"code\":500}', 0, NULL, '2020-05-17 22:09:19');
INSERT INTO `sys_oper_log` VALUES (68, '个人信息', 2, 'com.ruoyi.project.system.controller.SysProfileController.updatePwd()', 'PUT', 1, 'admin', NULL, '/system/user/profile/updatePwd', '192.168.48.24', '内网IP', 'admin123 Intco888', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-17 22:09:27');
INSERT INTO `sys_oper_log` VALUES (69, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"404\",\"orderNum\":\"19\",\"menuName\":\"测试\",\"params\":{},\"parentId\":0,\"path\":\"index_bak\",\"component\":\"index_bak\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-05-27 20:02:07');
INSERT INTO `sys_oper_log` VALUES (70, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"server\",\"orderNum\":\"6\",\"menuName\":\"工艺部管理\",\"params\":{},\"parentId\":0,\"path\":\"technology\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-08 21:19:28');
INSERT INTO `sys_oper_log` VALUES (71, '用户管理', 1, 'com.ruoyi.project.system.controller.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '180.165.247.151', '上海市 上海市', '{\"phonenumber\":\"18918363168\",\"admin\":false,\"password\":\"$2a$10$F8mvdqbmCeRZ77CVMmAO1eIEHnWp7zpElw30j5UywWR7I5tK3Us9K\",\"postIds\":[],\"email\":\"jennyk@intco.com.cn\",\"nickName\":\"金喆\",\"deptId\":201,\"params\":{},\"userName\":\"金喆\",\"userId\":101,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-22 12:47:26');
INSERT INTO `sys_oper_log` VALUES (72, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.247.151', '上海市 上海市', '{\"roles\":[],\"phonenumber\":\"18918363168\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"jennyk@intco.com.cn\",\"nickName\":\"金喆\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"金喆\",\"userId\":101,\"createBy\":\"admin\",\"roleIds\":[2],\"createTime\":1592801245000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-22 12:50:00');
INSERT INTO `sys_oper_log` VALUES (73, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"star\",\"orderNum\":\"3\",\"menuName\":\"打样确认率\",\"params\":{},\"parentId\":2019,\"path\":\"fx_technologist/sample_confirmation\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-30 20:10:31');
INSERT INTO `sys_oper_log` VALUES (74, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"star\",\"orderNum\":\"3\",\"menuName\":\"打样确认率\",\"params\":{},\"parentId\":2019,\"path\":\"fx_sample_confirmation\",\"component\":\"fx_technologist/sample_confirmation\",\"children\":[],\"createTime\":1593519031000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2020,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-30 20:10:49');
INSERT INTO `sys_oper_log` VALUES (75, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"server\",\"orderNum\":\"6\",\"menuName\":\"工艺部管理\",\"params\":{},\"parentId\":0,\"path\":\"fx_technologist\",\"children\":[],\"createTime\":1591622368000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2019,\"menuType\":\"M\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-30 21:29:04');
INSERT INTO `sys_oper_log` VALUES (76, '菜单管理', 2, 'com.ruoyi.project.system.controller.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"star\",\"orderNum\":\"3\",\"menuName\":\"打样确认率\",\"params\":{},\"parentId\":2019,\"path\":\"fx_sample_confirmation\",\"component\":\"fx_technologist/fx_sample_confirmation\",\"children\":[],\"createTime\":1593519031000,\"updateBy\":\"admin\",\"isFrame\":\"1\",\"menuId\":2020,\"menuType\":\"C\",\"perms\":\"\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-30 21:33:12');
INSERT INTO `sys_oper_log` VALUES (77, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"shopping\",\"orderNum\":\"4\",\"menuName\":\"爆款\",\"params\":{},\"parentId\":2019,\"path\":\"fx_sample_best_selling\",\"component\":\"fx_technologist/fx_sample_best_selling\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-30 21:37:07');
INSERT INTO `sys_oper_log` VALUES (78, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"updateBy\":\"admin\",\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2020,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-06-30 21:57:07');
INSERT INTO `sys_oper_log` VALUES (79, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"guide\",\"orderNum\":\"2\",\"menuName\":\"打样天数报表\",\"params\":{},\"parentId\":2019,\"path\":\"fx_sample_sample_use_day\",\"component\":\"fx_technologist/fx_sample_sample_use_day\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-01 23:01:16');
INSERT INTO `sys_oper_log` VALUES (80, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '192.168.50.190', '内网IP', '{\"visible\":\"0\",\"icon\":\"cascader\",\"orderNum\":\"1\",\"menuName\":\"日报\",\"params\":{},\"parentId\":2019,\"path\":\"fx_sample_day_report\",\"component\":\"fx_technologist/fx_sample_day_report\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-02 17:18:04');
INSERT INTO `sys_oper_log` VALUES (81, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '180.165.244.98', '上海市 上海市', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"updateBy\":\"admin\",\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:11:50');
INSERT INTO `sys_oper_log` VALUES (82, '角色管理', 1, 'com.ruoyi.project.system.controller.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '180.165.244.98', '上海市 上海市', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"params\":{},\"roleSort\":\"3\",\"createBy\":\"admin\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"deptIds\":[],\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:14:02');
INSERT INTO `sys_oper_log` VALUES (83, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"roles\":[{\"flag\":false,\"roleId\":2,\"admin\":false,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"2\",\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"status\":\"0\"}],\"phonenumber\":\"18988887222\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[],\"loginIp\":\"\",\"email\":\"taibaoshun@intco.cm.cn\",\"nickName\":\"邰宝顺\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"邰宝顺\",\"userId\":100,\"createBy\":\"admin\",\"roleIds\":[3],\"createTime\":1589694592000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:14:14');
INSERT INTO `sys_oper_log` VALUES (84, '用户管理', 1, 'com.ruoyi.project.system.controller.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"phonenumber\":\"18918361111\",\"admin\":false,\"password\":\"$2a$10$D08M5EErz8HMwDRjyUx0S.mIXe4aPtkJWT3Us7G4S5vX93CE1sxsO\",\"postIds\":[],\"email\":\"lvjun@intco.com.cn\",\"nickName\":\"吕俊\",\"deptId\":201,\"params\":{},\"userName\":\"吕俊\",\"userId\":102,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:14:44');
INSERT INTO `sys_oper_log` VALUES (85, '用户管理', 1, 'com.ruoyi.project.system.controller.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"phonenumber\":\"18918361112\",\"admin\":false,\"password\":\"$2a$10$zyK4w0FgUTKuMc1J1Eic5uXe4yldL9ycD5RwYzDUu32D9Oa8XYtn6\",\"postIds\":[],\"email\":\"tangjiamin@intco.com.cn\",\"nickName\":\"唐加民\",\"deptId\":201,\"params\":{},\"userName\":\"唐加民\",\"userId\":103,\"createBy\":\"admin\",\"roleIds\":[3],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:15:40');
INSERT INTO `sys_oper_log` VALUES (86, '用户管理', 1, 'com.ruoyi.project.system.controller.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"phonenumber\":\"18918361113\",\"admin\":false,\"password\":\"$2a$10$vgzFtaO1y8hMCJ1jgMpleOD4A/r6Se2NZJvNmcAgsMgzDKfCPCLTu\",\"postIds\":[],\"email\":\"tangli@intco.com.cn\",\"nickName\":\"唐力\",\"deptId\":201,\"params\":{},\"userName\":\"唐力\",\"userId\":104,\"createBy\":\"admin\",\"roleIds\":[3],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:16:13');
INSERT INTO `sys_oper_log` VALUES (87, '用户管理', 1, 'com.ruoyi.project.system.controller.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"phonenumber\":\"18918361114\",\"admin\":false,\"password\":\"$2a$10$SvvshxkfaZyUMqAg1.eUUuzaZdyEz0MhIJR4SSoe/DaOTX2PTA0ES\",\"postIds\":[],\"email\":\"hujiawei@intco.com.cn\",\"nickName\":\"胡家伟\",\"deptId\":201,\"params\":{},\"userName\":\"胡家伟\",\"userId\":105,\"createBy\":\"admin\",\"roleIds\":[3],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:17:05');
INSERT INTO `sys_oper_log` VALUES (88, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '180.165.244.98', '上海市 上海市', '{\"admin\":false,\"password\":\"$2a$10$nKwNlpDwSRcwSnekXBXbteo9kSAzbJnlcS9AVUguv3e6YVJ9IqLjG\",\"updateBy\":\"admin\",\"params\":{},\"userId\":102}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:18:21');
INSERT INTO `sys_oper_log` VALUES (89, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '180.165.244.98', '上海市 上海市', '{\"admin\":false,\"password\":\"$2a$10$IqBev6siGGS1r5HFrSlx4uMYSxcZNVaMG1xsWh7r1i3zaFqUDSNP2\",\"updateBy\":\"admin\",\"params\":{},\"userId\":103}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:18:23');
INSERT INTO `sys_oper_log` VALUES (90, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '180.165.244.98', '上海市 上海市', '{\"admin\":false,\"password\":\"$2a$10$i6rLf/oA.9m53DIwSfsEu.VFht8YKk7VOuQIe5pD.c2HHUygFjqcy\",\"updateBy\":\"admin\",\"params\":{},\"userId\":104}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:18:25');
INSERT INTO `sys_oper_log` VALUES (91, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '180.165.244.98', '上海市 上海市', '{\"admin\":false,\"password\":\"$2a$10$/TLQx4eqGb4eFYtTs8ehl.ET1BhdaM3uc.fdKeHuBD9QgUPECqEji\",\"updateBy\":\"admin\",\"params\":{},\"userId\":105}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:18:27');
INSERT INTO `sys_oper_log` VALUES (92, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '180.165.244.98', '上海市 上海市', '{\"admin\":false,\"password\":\"$2a$10$MDFhd9frDJGf5StS/tFKWuysiMVqmy.OwnqnPNDYpzVpcebfFBOhW\",\"updateBy\":\"admin\",\"params\":{},\"userId\":101}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:18:31');
INSERT INTO `sys_oper_log` VALUES (93, '角色管理', 1, 'com.ruoyi.project.system.controller.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '180.165.244.98', '上海市 上海市', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"params\":{},\"roleSort\":\"4\",\"createBy\":\"admin\",\"roleKey\":\"technologistmaster\",\"roleName\":\"工艺主管\",\"deptIds\":[],\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:32:03');
INSERT INTO `sys_oper_log` VALUES (94, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '180.165.244.98', '上海市 上海市', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"3\",\"createTime\":1593742442000,\"updateBy\":\"admin\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2022,2020,2021,2019],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:32:12');
INSERT INTO `sys_oper_log` VALUES (95, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"roles\":[{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"3\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"status\":\"0\"}],\"phonenumber\":\"18988887222\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[],\"loginIp\":\"\",\"email\":\"taibaoshun@intco.cm.cn\",\"nickName\":\"邰宝顺\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"邰宝顺\",\"userId\":100,\"createBy\":\"admin\",\"roleIds\":[4],\"createTime\":1589694592000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:32:26');
INSERT INTO `sys_oper_log` VALUES (96, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '180.165.244.98', '上海市 上海市', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"3\",\"createTime\":1593742442000,\"updateBy\":\"admin\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:33:40');
INSERT INTO `sys_oper_log` VALUES (97, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '180.165.244.98', '上海市 上海市', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"4\",\"createTime\":1593743523000,\"updateBy\":\"admin\",\"roleKey\":\"technologistmaster\",\"roleName\":\"工艺主管\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:33:44');
INSERT INTO `sys_oper_log` VALUES (98, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"roles\":[{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"3\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"status\":\"0\"}],\"phonenumber\":\"18918361112\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"tangjiamin@intco.com.cn\",\"nickName\":\"唐加民\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"唐加民\",\"userId\":103,\"createBy\":\"admin\",\"roleIds\":[3],\"createTime\":1593742540000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:34:00');
INSERT INTO `sys_oper_log` VALUES (99, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"roles\":[{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"3\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"status\":\"0\"}],\"phonenumber\":\"18918361114\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"hujiawei@intco.com.cn\",\"nickName\":\"胡家伟\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"胡家伟\",\"userId\":105,\"createBy\":\"admin\",\"roleIds\":[3],\"createTime\":1593742625000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:34:04');
INSERT INTO `sys_oper_log` VALUES (100, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"roles\":[{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"params\":{},\"roleSort\":\"3\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"status\":\"0\"}],\"phonenumber\":\"18918361113\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"tangli@intco.com.cn\",\"nickName\":\"唐力\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"唐力\",\"userId\":104,\"createBy\":\"admin\",\"roleIds\":[3],\"createTime\":1593742573000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:34:07');
INSERT INTO `sys_oper_log` VALUES (101, '用户管理', 2, 'com.ruoyi.project.system.controller.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '180.165.244.98', '上海市 上海市', '{\"roles\":[],\"phonenumber\":\"18918361111\",\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"lvjun@intco.com.cn\",\"nickName\":\"吕俊\",\"sex\":\"0\",\"deptId\":201,\"avatar\":\"\",\"dept\":{\"deptName\":\"工艺部\",\"deptId\":201,\"orderNum\":\"3\",\"params\":{},\"parentId\":101,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"吕俊\",\"userId\":102,\"createBy\":\"admin\",\"roleIds\":[3],\"createTime\":1593742484000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-03 10:34:15');
INSERT INTO `sys_oper_log` VALUES (102, '菜单管理', 1, 'com.ruoyi.project.system.controller.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"checkbox\",\"orderNum\":\"5\",\"menuName\":\"总表\",\"params\":{},\"parentId\":2019,\"path\":\"fx_sample_all\",\"component\":\"fx_technologist/fx_sample_all\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"C\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-04 12:46:19');
INSERT INTO `sys_oper_log` VALUES (103, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '222.69.26.101', '上海市 上海市', '{\"flag\":false,\"roleId\":3,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"3\",\"createTime\":1593742442000,\"updateBy\":\"admin\",\"roleKey\":\"technologist\",\"roleName\":\"工艺员\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021,2024],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-04 14:15:30');
INSERT INTO `sys_oper_log` VALUES (104, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '222.69.26.101', '上海市 上海市', '{\"flag\":false,\"roleId\":4,\"admin\":false,\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"4\",\"createTime\":1593743523000,\"updateBy\":\"admin\",\"roleKey\":\"technologistmaster\",\"roleName\":\"工艺主管\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021,2024],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-04 14:15:35');
INSERT INTO `sys_oper_log` VALUES (105, '角色管理', 2, 'com.ruoyi.project.system.controller.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '222.69.26.101', '上海市 上海市', '{\"flag\":false,\"roleId\":2,\"admin\":false,\"remark\":\"普通角色\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"params\":{},\"roleSort\":\"2\",\"createTime\":1521171180000,\"updateBy\":\"admin\",\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"menuIds\":[2003,2004,2005,2006,2007,2009,2013,2014,2015,2011,2017,2019,2023,2022,2020,2021,2024],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2020-07-04 14:15:39');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-07-04 14:15:39', '普通角色');
INSERT INTO `sys_role` VALUES (3, '工艺员', 'technologist', 3, '1', '0', '0', 'admin', '2020-07-03 10:14:02', 'admin', '2020-07-04 14:15:30', NULL);
INSERT INTO `sys_role` VALUES (4, '工艺主管', 'technologistmaster', 4, '1', '0', '0', 'admin', '2020-07-03 10:32:03', 'admin', '2020-07-04 14:15:35', NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 103);
INSERT INTO `sys_role_dept` VALUES (2, 105);
INSERT INTO `sys_role_dept` VALUES (2, 200);
INSERT INTO `sys_role_dept` VALUES (2, 201);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 2003);
INSERT INTO `sys_role_menu` VALUES (2, 2004);
INSERT INTO `sys_role_menu` VALUES (2, 2005);
INSERT INTO `sys_role_menu` VALUES (2, 2006);
INSERT INTO `sys_role_menu` VALUES (2, 2007);
INSERT INTO `sys_role_menu` VALUES (2, 2009);
INSERT INTO `sys_role_menu` VALUES (2, 2011);
INSERT INTO `sys_role_menu` VALUES (2, 2013);
INSERT INTO `sys_role_menu` VALUES (2, 2014);
INSERT INTO `sys_role_menu` VALUES (2, 2015);
INSERT INTO `sys_role_menu` VALUES (2, 2017);
INSERT INTO `sys_role_menu` VALUES (2, 2019);
INSERT INTO `sys_role_menu` VALUES (2, 2020);
INSERT INTO `sys_role_menu` VALUES (2, 2021);
INSERT INTO `sys_role_menu` VALUES (2, 2022);
INSERT INTO `sys_role_menu` VALUES (2, 2023);
INSERT INTO `sys_role_menu` VALUES (2, 2024);
INSERT INTO `sys_role_menu` VALUES (3, 2003);
INSERT INTO `sys_role_menu` VALUES (3, 2004);
INSERT INTO `sys_role_menu` VALUES (3, 2005);
INSERT INTO `sys_role_menu` VALUES (3, 2006);
INSERT INTO `sys_role_menu` VALUES (3, 2007);
INSERT INTO `sys_role_menu` VALUES (3, 2009);
INSERT INTO `sys_role_menu` VALUES (3, 2011);
INSERT INTO `sys_role_menu` VALUES (3, 2013);
INSERT INTO `sys_role_menu` VALUES (3, 2014);
INSERT INTO `sys_role_menu` VALUES (3, 2015);
INSERT INTO `sys_role_menu` VALUES (3, 2017);
INSERT INTO `sys_role_menu` VALUES (3, 2019);
INSERT INTO `sys_role_menu` VALUES (3, 2020);
INSERT INTO `sys_role_menu` VALUES (3, 2021);
INSERT INTO `sys_role_menu` VALUES (3, 2022);
INSERT INTO `sys_role_menu` VALUES (3, 2023);
INSERT INTO `sys_role_menu` VALUES (3, 2024);
INSERT INTO `sys_role_menu` VALUES (4, 2003);
INSERT INTO `sys_role_menu` VALUES (4, 2004);
INSERT INTO `sys_role_menu` VALUES (4, 2005);
INSERT INTO `sys_role_menu` VALUES (4, 2006);
INSERT INTO `sys_role_menu` VALUES (4, 2007);
INSERT INTO `sys_role_menu` VALUES (4, 2009);
INSERT INTO `sys_role_menu` VALUES (4, 2011);
INSERT INTO `sys_role_menu` VALUES (4, 2013);
INSERT INTO `sys_role_menu` VALUES (4, 2014);
INSERT INTO `sys_role_menu` VALUES (4, 2015);
INSERT INTO `sys_role_menu` VALUES (4, 2017);
INSERT INTO `sys_role_menu` VALUES (4, 2019);
INSERT INTO `sys_role_menu` VALUES (4, 2020);
INSERT INTO `sys_role_menu` VALUES (4, 2021);
INSERT INTO `sys_role_menu` VALUES (4, 2022);
INSERT INTO `sys_role_menu` VALUES (4, 2023);
INSERT INTO `sys_role_menu` VALUES (4, 2024);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', 'SuperAdmin', '00', 'chenyu@intco.com.cn', '18019321071', '0', '', '$2a$10$yxybbp9Nb0.SOB/GOhxLQO22VvNhjO4Vlo57zxifAgV.VYjCYenTe', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-17 13:48:15', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '2', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '测试员');
INSERT INTO `sys_user` VALUES (100, 201, '邰宝顺', '邰宝顺', '00', 'taibaoshun@intco.cm.cn', '18988887222', '0', '', '$2a$10$iaQCG8JwhnMm5OVg0YuvE.v5pLHtH6BaLfVOYeTQT.DjeVbBD3Di.', '0', '0', '', NULL, 'admin', '2020-05-17 13:49:52', 'admin', '2020-07-03 10:32:26', NULL);
INSERT INTO `sys_user` VALUES (101, 201, '金喆', '金喆', '00', 'jennyk@intco.com.cn', '18918363168', '0', '', '$2a$10$MDFhd9frDJGf5StS/tFKWuysiMVqmy.OwnqnPNDYpzVpcebfFBOhW', '0', '0', '', NULL, 'admin', '2020-06-22 12:47:25', 'admin', '2020-07-03 10:18:30', NULL);
INSERT INTO `sys_user` VALUES (102, 201, '吕俊', '吕俊', '00', 'lvjun@intco.com.cn', '18918361111', '0', '', '$2a$10$nKwNlpDwSRcwSnekXBXbteo9kSAzbJnlcS9AVUguv3e6YVJ9IqLjG', '0', '0', '', NULL, 'admin', '2020-07-03 10:14:44', 'admin', '2020-07-03 10:34:15', NULL);
INSERT INTO `sys_user` VALUES (103, 201, '唐加民', '唐加民', '00', 'tangjiamin@intco.com.cn', '18918361112', '0', '', '$2a$10$IqBev6siGGS1r5HFrSlx4uMYSxcZNVaMG1xsWh7r1i3zaFqUDSNP2', '0', '0', '', NULL, 'admin', '2020-07-03 10:15:40', 'admin', '2020-07-03 10:34:00', NULL);
INSERT INTO `sys_user` VALUES (104, 201, '唐力', '唐力', '00', 'tangli@intco.com.cn', '18918361113', '0', '', '$2a$10$i6rLf/oA.9m53DIwSfsEu.VFht8YKk7VOuQIe5pD.c2HHUygFjqcy', '0', '0', '', NULL, 'admin', '2020-07-03 10:16:13', 'admin', '2020-07-03 10:34:07', NULL);
INSERT INTO `sys_user` VALUES (105, 201, '胡家伟', '胡家伟', '00', 'hujiawei@intco.com.cn', '18918361114', '0', '', '$2a$10$/TLQx4eqGb4eFYtTs8ehl.ET1BhdaM3uc.fdKeHuBD9QgUPECqEji', '0', '0', '', NULL, 'admin', '2020-07-03 10:17:05', 'admin', '2020-07-03 10:34:04', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);
INSERT INTO `sys_user_post` VALUES (101, 4);
INSERT INTO `sys_user_post` VALUES (102, 4);
INSERT INTO `sys_user_post` VALUES (103, 4);
INSERT INTO `sys_user_post` VALUES (104, 4);
INSERT INTO `sys_user_post` VALUES (105, 4);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (100, 4);
INSERT INTO `sys_user_role` VALUES (101, 2);
INSERT INTO `sys_user_role` VALUES (102, 3);
INSERT INTO `sys_user_role` VALUES (103, 3);
INSERT INTO `sys_user_role` VALUES (104, 3);
INSERT INTO `sys_user_role` VALUES (105, 3);

SET FOREIGN_KEY_CHECKS = 1;
