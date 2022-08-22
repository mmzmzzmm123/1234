/*
Navicat MySQL Data Transfer

Source Server         : aliyun_47.101.170.26
Source Server Version : 80029
Source Host           : 47.101.170.26:3306
Source Database       : ruoyi_ibaiqi_wxlogin

Target Server Type    : MYSQL
Target Server Version : 80029
File Encoding         : 65001

Date: 2022-08-22 23:58:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES ('5', 'ib_timetable_course', '课程管理表', null, null, 'IbTimetableCourse', 'crud', 'com.ruoyi.ibaiqi.timetable', 'timetable', 'course', '课程管理', 'zhangxuDev', '0', '/', null, 'admin', '2022-08-09 14:56:53', '', null, null);
INSERT INTO `gen_table` VALUES ('6', 'ib_timetable_grade', '课程表班级管理表', 'ib_timetable_course', 'grade_id', 'IbTimetableGrade', 'sub', 'com.ruoyi.ibaiqi.timetable', 'timetable', 'grade', '课程班级管理', 'zhangxuDev', '0', '/', '{}', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49', null);
INSERT INTO `gen_table` VALUES ('7', 'ib_timetable_school', '学校信息表', 'ib_timetable_grade', 'school_id', 'IbTimetableSchool', 'sub', 'com.ruoyi.ibaiqi.timetable', 'timetable', 'school', '学校信息', 'zhangxuDev', '0', '/', '{}', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22', null);
INSERT INTO `gen_table` VALUES ('9', 'ib_wx_appconfig', '微信小程序配置表', null, null, 'IbWxAppconfig', 'crud', 'com.ruoyi.ibaiqi.wxUser', 'wxUser', 'appConfig', '微信小程序配置', 'zhangxuDev', '0', '/', '{}', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18', null);
INSERT INTO `gen_table` VALUES ('10', 'ib_wx_user', '微信用户表', null, null, 'IbWxUser', 'crud', 'com.ruoyi.system', 'system', 'user', '微信用户', 'ruoyi', '0', '/', null, 'admin', '2022-08-17 15:53:57', '', null, null);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb3 COMMENT='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES ('43', '5', 'course_id', '课程id', 'int', 'Long', 'courseId', '1', '1', null, '1', null, null, null, 'EQ', 'input', '', '1', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('44', '5', 'course_title', '课程名称', 'varchar(50)', 'String', 'courseTitle', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '2', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('45', '5', 'course_notes', '课程备注', 'varchar(50)', 'String', 'courseNotes', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '3', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('46', '5', 'week_begin', '课程开始周', 'int', 'Long', 'weekBegin', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '4', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('47', '5', 'week_end', '课程结束周', 'int', 'Long', 'weekEnd', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '5', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('48', '5', 'what_day', '星期几', 'int', 'Long', 'whatDay', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '6', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('49', '5', 'section_begin', '课程开始节次', 'int', 'Long', 'sectionBegin', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '7', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('50', '5', 'section_end', '课程结束节次', 'int', 'Long', 'sectionEnd', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '8', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('51', '5', 'schoolroom', '课程教室', 'varchar(50)', 'String', 'schoolroom', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '9', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('52', '5', 'teacher', '授课教师', 'varchar(50)', 'String', 'teacher', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '10', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('53', '5', 'grade_id', '班级表ID', 'int', 'Long', 'gradeId', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '11', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('54', '5', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', null, '1', null, null, null, 'EQ', 'input', '', '12', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('55', '5', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', null, '1', null, null, null, 'EQ', 'datetime', '', '13', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('56', '5', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', null, '1', '1', null, null, 'EQ', 'input', '', '14', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('57', '5', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', null, '1', '1', null, null, 'EQ', 'datetime', '', '15', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('58', '5', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', null, '1', '1', '1', null, 'EQ', 'input', '', '16', 'admin', '2022-08-09 14:56:53', '', null);
INSERT INTO `gen_table_column` VALUES ('59', '6', 'grade_id', '班级表ID', 'int', 'Long', 'gradeId', '1', '1', null, '1', null, null, null, 'EQ', 'input', '', '1', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49');
INSERT INTO `gen_table_column` VALUES ('60', '6', 'school_name', '所属学校名称', 'varchar(50)', 'String', 'schoolName', '0', '0', null, '1', '1', '1', '1', 'LIKE', 'input', '', '2', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49');
INSERT INTO `gen_table_column` VALUES ('61', '6', 'school_id', '所属学校id', 'bigint', 'Long', 'schoolId', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '3', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49');
INSERT INTO `gen_table_column` VALUES ('62', '6', 'grade_year', '班级年级', 'varchar(50)', 'String', 'gradeYear', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '4', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49');
INSERT INTO `gen_table_column` VALUES ('63', '6', 'class_title', '班级名称', 'varchar(50)', 'String', 'classTitle', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '5', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49');
INSERT INTO `gen_table_column` VALUES ('64', '6', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', null, '1', null, null, null, 'EQ', 'input', '', '6', 'admin', '2022-08-09 14:56:53', '', '2022-08-09 15:40:49');
INSERT INTO `gen_table_column` VALUES ('65', '6', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', null, '1', null, null, null, 'EQ', 'datetime', '', '7', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:40:50');
INSERT INTO `gen_table_column` VALUES ('66', '6', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', null, '1', '1', null, null, 'EQ', 'input', '', '8', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:40:50');
INSERT INTO `gen_table_column` VALUES ('67', '6', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', null, '1', '1', null, null, 'EQ', 'datetime', '', '9', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:40:50');
INSERT INTO `gen_table_column` VALUES ('68', '6', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', null, '1', '1', '1', null, 'EQ', 'input', '', '10', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:40:50');
INSERT INTO `gen_table_column` VALUES ('69', '7', 'school_id', '学校表ID', 'bigint', 'Long', 'schoolId', '1', '0', null, '1', null, null, null, 'EQ', 'input', '', '1', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('70', '7', 'school_name', '学校名称', 'varchar(50)', 'String', 'schoolName', '0', '0', null, '1', '1', '1', '1', 'LIKE', 'input', '', '2', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('71', '7', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', null, '1', null, null, null, 'EQ', 'input', '', '3', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('72', '7', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', null, '1', null, null, null, 'EQ', 'datetime', '', '4', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('73', '7', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', null, '1', '1', null, null, 'EQ', 'input', '', '5', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('74', '7', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', null, '1', '1', null, null, 'EQ', 'datetime', '', '6', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('75', '7', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', null, '1', '1', '1', null, 'EQ', 'input', '', '7', 'admin', '2022-08-09 14:56:54', '', '2022-08-09 15:41:22');
INSERT INTO `gen_table_column` VALUES ('86', '9', 'id', 'ID', 'int', 'Long', 'id', '1', '1', null, '1', null, null, null, 'EQ', 'input', '', '1', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('87', '9', 'appid', '小程序ID', 'varchar(32)', 'String', 'appid', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '2', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('88', '9', 'app_secret', '小程序密钥', 'varchar(64)', 'String', 'appSecret', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '3', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('89', '9', 'state', '状态', 'int', 'Long', 'state', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '4', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('90', '9', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', null, '1', null, null, null, 'EQ', 'input', '', '5', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('91', '9', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', null, '1', null, null, null, 'EQ', 'datetime', '', '6', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('92', '9', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', null, '1', '1', null, null, 'EQ', 'input', '', '7', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('93', '9', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', null, '1', '1', null, null, 'EQ', 'datetime', '', '8', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('94', '9', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', null, '1', '1', '1', null, 'EQ', 'input', '', '9', 'admin', '2022-08-17 11:15:37', '', '2022-08-17 11:29:18');
INSERT INTO `gen_table_column` VALUES ('95', '10', 'id', 'ID', 'int', 'Long', 'id', '1', '1', null, '1', null, null, null, 'EQ', 'input', '', '1', 'admin', '2022-08-17 15:53:57', '', null);
INSERT INTO `gen_table_column` VALUES ('96', '10', 'nickname', '微信名称', 'varchar(100)', 'String', 'nickname', '0', '0', null, '1', '1', '1', '1', 'LIKE', 'input', '', '2', 'admin', '2022-08-17 15:53:57', '', null);
INSERT INTO `gen_table_column` VALUES ('97', '10', 'avatar', '头像', 'varchar(255)', 'String', 'avatar', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '3', 'admin', '2022-08-17 15:53:57', '', null);
INSERT INTO `gen_table_column` VALUES ('98', '10', 'openid', '微信唯一标识符', 'varchar(100)', 'String', 'openid', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '4', 'admin', '2022-08-17 15:53:57', '', null);
INSERT INTO `gen_table_column` VALUES ('99', '10', 'gender', '性别', 'int', 'Long', 'gender', '0', '0', null, '1', '1', '1', '1', 'EQ', 'input', '', '5', 'admin', '2022-08-17 15:53:57', '', null);
INSERT INTO `gen_table_column` VALUES ('100', '10', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', null, '1', null, null, null, 'EQ', 'input', '', '6', 'admin', '2022-08-17 15:53:57', '', null);
INSERT INTO `gen_table_column` VALUES ('101', '10', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', null, '1', null, null, null, 'EQ', 'datetime', '', '7', 'admin', '2022-08-17 15:53:58', '', null);
INSERT INTO `gen_table_column` VALUES ('102', '10', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', null, '1', '1', null, null, 'EQ', 'input', '', '8', 'admin', '2022-08-17 15:53:58', '', null);
INSERT INTO `gen_table_column` VALUES ('103', '10', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', null, '1', '1', null, null, 'EQ', 'datetime', '', '9', 'admin', '2022-08-17 15:53:58', '', null);
INSERT INTO `gen_table_column` VALUES ('104', '10', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', null, '1', '1', '1', null, 'EQ', 'input', '', '10', 'admin', '2022-08-17 15:53:58', '', null);

-- ----------------------------
-- Table structure for ib_wx_appconfig
-- ----------------------------
DROP TABLE IF EXISTS `ib_wx_appconfig`;
CREATE TABLE `ib_wx_appconfig` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小程序ID',
  `app_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小程序密钥',
  `state` int DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='微信小程序配置表';

-- ----------------------------
-- Records of ib_wx_appconfig
-- ----------------------------
INSERT INTO `ib_wx_appconfig` VALUES ('1', 'wx7d229ccd6a2e64e5', 'f0b32157d12cde2b315fa65ad6465af1', '0', '', '2022-06-28 15:01:45', '', '2022-08-14 23:41:42', null);

-- ----------------------------
-- Table structure for ib_wx_user
-- ----------------------------
DROP TABLE IF EXISTS `ib_wx_user`;
CREATE TABLE `ib_wx_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信唯一标识符',
  `gender` int DEFAULT NULL COMMENT '性别',
  `user_id` bigint DEFAULT NULL COMMENT 'sys_user表user_id',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='微信用户表';

-- ----------------------------
-- Records of ib_wx_user
-- ----------------------------
INSERT INTO `ib_wx_user` VALUES ('7', 'AbuCoder🕊', 'https://thirdwx.qlogo.cn/mmopen/vi_32/w9hLTEeERJD7A1tCGaMPQUYTxZMqmsmJuqWJzia21dFR6PtDhSqF7iceIegXNEeutHY5opJGSIalxThGBicglfKTw/132', 'o1Orf5W7Z7ErDzYXCbaxDquwToDg', '0', null, 'Abu', '2022-06-27 14:28:21', '', null, null);
INSERT INTO `ib_wx_user` VALUES ('8', 'AbuCoder🕊', 'https://thirdwx.qlogo.cn/mmopen/vi_32/C3fweXtXQBibtaibaGn3MI2b99kLhL0wELPedvWGUydM4Ghfww94Dtq1PeZqbexkrvHWGxfSeTXtc0nsexJhlf6w/132', 'o1O7_5Yuo7Zb_nsNQckMAEs75564', '0', null, 'Abu', '2022-06-28 15:06:43', '', null, null);
INSERT INTO `ib_wx_user` VALUES ('41', '码哥@码农之旅', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTInxGDaYcpnSA7zU0mmzhC0FmhibFKCdrBcGqZoibMHWXRRrt5egAO2jIxCCeOFRPI3IXib4nWkfjRTw/132', 'oLgdC5JniZnYKR4zyZP2lLEGjdRw', '0', '150', 'Abu', '2022-08-22 23:56:13', 'Abu', '2022-08-22 23:56:36', null);

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', '0/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', '0/20 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='已触发的触发器表';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='任务详细信息表';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', null, 'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636F6D2E72756F79692E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636F6D2E72756F79692E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001824E10302078707400007070707400013174000E302F3130202A202A202A202A203F74001172795461736B2E72794E6F506172616D7374000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000001740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', null, 'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636F6D2E72756F79692E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636F6D2E72756F79692E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001824E10302078707400007070707400013174000E302F3135202A202A202A202A203F74001572795461736B2E7279506172616D7328277279272974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000002740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E69C89E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', null, 'com.ruoyi.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001E636F6D2E72756F79692E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720027636F6D2E72756F79692E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001824E10302078707400007070707400013174000E302F3230202A202A202A202A203F74003872795461736B2E72794D756C7469706C65506172616D7328277279272C20747275652C20323030304C2C203331362E3530442C203130302974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000003740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E5A49AE58F82EFBC8974000133740001317800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler', 'zhanxuPC1659192929327', '1659193173877', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', 'TASK_CLASS_NAME1', 'DEFAULT', null, '1659192930000', '-1', '5', 'PAUSED', 'CRON', '1659192929000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', 'TASK_CLASS_NAME2', 'DEFAULT', null, '1659192930000', '-1', '5', 'PAUSED', 'CRON', '1659192929000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', 'TASK_CLASS_NAME3', 'DEFAULT', null, '1659192940000', '-1', '5', 'PAUSED', 'CRON', '1659192929000', '0', null, '2', '');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3 COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2022-07-30 22:58:44', '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES ('2', '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2022-07-30 22:58:44', '', null, '初始化密码 123456');
INSERT INTO `sys_config` VALUES ('3', '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2022-07-30 22:58:44', '', null, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES ('4', '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2022-07-30 22:58:44', 'admin', '2022-08-07 09:17:34', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES ('5', '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'true', 'Y', 'admin', '2022-07-30 22:58:44', 'admin', '2022-08-18 23:53:21', '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb3 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('100', '0', '0', '若依科技', '0', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('101', '100', '0,100', '深圳总公司', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('102', '100', '0,100', '长沙分公司', '2', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('103', '101', '0,100,101', '研发部门', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('104', '101', '0,100,101', '市场部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('105', '101', '0,100,101', '测试部门', '3', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('106', '101', '0,100,101', '财务部门', '4', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('107', '101', '0,100,101', '运维部门', '5', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('108', '102', '0,100,102', '市场部门', '1', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);
INSERT INTO `sys_dept` VALUES ('109', '102', '0,100,102', '财务部门', '2', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-07-30 22:58:42', '', null);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES ('1', '1', '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '性别男');
INSERT INTO `sys_dict_data` VALUES ('2', '2', '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '性别女');
INSERT INTO `sys_dict_data` VALUES ('3', '3', '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '性别未知');
INSERT INTO `sys_dict_data` VALUES ('4', '1', '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '显示菜单');
INSERT INTO `sys_dict_data` VALUES ('5', '2', '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES ('6', '1', '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('7', '2', '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('8', '1', '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('9', '2', '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '停用状态');
INSERT INTO `sys_dict_data` VALUES ('10', '1', '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '默认分组');
INSERT INTO `sys_dict_data` VALUES ('11', '2', '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '系统分组');
INSERT INTO `sys_dict_data` VALUES ('12', '1', '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '系统默认是');
INSERT INTO `sys_dict_data` VALUES ('13', '2', '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '系统默认否');
INSERT INTO `sys_dict_data` VALUES ('14', '1', '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '通知');
INSERT INTO `sys_dict_data` VALUES ('15', '2', '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '公告');
INSERT INTO `sys_dict_data` VALUES ('16', '1', '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2022-07-30 22:58:44', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('17', '2', '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '关闭状态');
INSERT INTO `sys_dict_data` VALUES ('18', '1', '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '新增操作');
INSERT INTO `sys_dict_data` VALUES ('19', '2', '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '修改操作');
INSERT INTO `sys_dict_data` VALUES ('20', '3', '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '删除操作');
INSERT INTO `sys_dict_data` VALUES ('21', '4', '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '授权操作');
INSERT INTO `sys_dict_data` VALUES ('22', '5', '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '导出操作');
INSERT INTO `sys_dict_data` VALUES ('23', '6', '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '导入操作');
INSERT INTO `sys_dict_data` VALUES ('24', '7', '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '强退操作');
INSERT INTO `sys_dict_data` VALUES ('25', '8', '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '生成操作');
INSERT INTO `sys_dict_data` VALUES ('26', '9', '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '清空操作');
INSERT INTO `sys_dict_data` VALUES ('27', '1', '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '正常状态');
INSERT INTO `sys_dict_data` VALUES ('28', '2', '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2022-07-30 22:58:44', '', null, '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3 COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('1', '用户性别', 'sys_user_sex', '0', 'admin', '2022-07-30 22:58:44', '', null, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES ('2', '菜单状态', 'sys_show_hide', '0', 'admin', '2022-07-30 22:58:44', '', null, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES ('3', '系统开关', 'sys_normal_disable', '0', 'admin', '2022-07-30 22:58:44', '', null, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES ('4', '任务状态', 'sys_job_status', '0', 'admin', '2022-07-30 22:58:44', '', null, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES ('5', '任务分组', 'sys_job_group', '0', 'admin', '2022-07-30 22:58:44', '', null, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES ('6', '系统是否', 'sys_yes_no', '0', 'admin', '2022-07-30 22:58:44', '', null, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES ('7', '通知类型', 'sys_notice_type', '0', 'admin', '2022-07-30 22:58:44', '', null, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES ('8', '通知状态', 'sys_notice_status', '0', 'admin', '2022-07-30 22:58:44', '', null, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES ('9', '操作类型', 'sys_oper_type', '0', 'admin', '2022-07-30 22:58:44', '', null, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES ('10', '系统状态', 'sys_common_status', '0', 'admin', '2022-07-30 22:58:44', '', null, '登录状态列表');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2022-07-30 22:58:44', '', null, '');
INSERT INTO `sys_job` VALUES ('2', '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2022-07-30 22:58:44', '', null, '');
INSERT INTO `sys_job` VALUES ('3', '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2022-07-30 22:58:44', '', null, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=436 DEFAULT CHARSET=utf8mb3 COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES ('100', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-07-30 23:12:50');
INSERT INTO `sys_logininfor` VALUES ('101', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-07-30 23:28:17');
INSERT INTO `sys_logininfor` VALUES ('102', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-07-30 23:28:28');
INSERT INTO `sys_logininfor` VALUES ('103', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-07-31 07:19:39');
INSERT INTO `sys_logininfor` VALUES ('104', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-07-31 07:27:23');
INSERT INTO `sys_logininfor` VALUES ('105', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-07-31 07:29:43');
INSERT INTO `sys_logininfor` VALUES ('106', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码已失效', '2022-07-31 07:34:53');
INSERT INTO `sys_logininfor` VALUES ('107', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码错误', '2022-07-31 08:48:44');
INSERT INTO `sys_logininfor` VALUES ('108', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-07-31 08:48:53');
INSERT INTO `sys_logininfor` VALUES ('109', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 17:30:13');
INSERT INTO `sys_logininfor` VALUES ('110', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 18:04:57');
INSERT INTO `sys_logininfor` VALUES ('111', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-04 18:07:52');
INSERT INTO `sys_logininfor` VALUES ('112', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-04 18:24:16');
INSERT INTO `sys_logininfor` VALUES ('113', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-04 22:15:37');
INSERT INTO `sys_logininfor` VALUES ('114', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '验证码已失效', '2022-08-04 22:21:08');
INSERT INTO `sys_logininfor` VALUES ('115', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 22:21:14');
INSERT INTO `sys_logininfor` VALUES ('116', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-04 22:27:21');
INSERT INTO `sys_logininfor` VALUES ('117', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 22:27:29');
INSERT INTO `sys_logininfor` VALUES ('118', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-04 22:28:13');
INSERT INTO `sys_logininfor` VALUES ('119', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 22:28:29');
INSERT INTO `sys_logininfor` VALUES ('120', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 22:47:44');
INSERT INTO `sys_logininfor` VALUES ('121', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-04 22:50:50');
INSERT INTO `sys_logininfor` VALUES ('122', 'test', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '对不起，您的账号：test 已被删除', '2022-08-04 22:50:55');
INSERT INTO `sys_logininfor` VALUES ('123', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-04 22:51:08');
INSERT INTO `sys_logininfor` VALUES ('124', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-05 07:10:45');
INSERT INTO `sys_logininfor` VALUES ('125', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-05 07:12:47');
INSERT INTO `sys_logininfor` VALUES ('126', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-05 08:41:08');
INSERT INTO `sys_logininfor` VALUES ('127', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-05 09:52:41');
INSERT INTO `sys_logininfor` VALUES ('128', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-05 17:18:51');
INSERT INTO `sys_logininfor` VALUES ('129', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-05 18:02:48');
INSERT INTO `sys_logininfor` VALUES ('130', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-05 18:02:53');
INSERT INTO `sys_logininfor` VALUES ('131', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-05 18:04:48');
INSERT INTO `sys_logininfor` VALUES ('132', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-05 18:05:19');
INSERT INTO `sys_logininfor` VALUES ('133', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-05 18:46:01');
INSERT INTO `sys_logininfor` VALUES ('134', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-06 11:08:53');
INSERT INTO `sys_logininfor` VALUES ('135', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-06 11:09:44');
INSERT INTO `sys_logininfor` VALUES ('136', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-06 11:10:34');
INSERT INTO `sys_logininfor` VALUES ('137', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-06 11:10:39');
INSERT INTO `sys_logininfor` VALUES ('138', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-06 16:49:05');
INSERT INTO `sys_logininfor` VALUES ('139', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-06 16:50:09');
INSERT INTO `sys_logininfor` VALUES ('140', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-06 16:50:20');
INSERT INTO `sys_logininfor` VALUES ('141', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-06 18:09:51');
INSERT INTO `sys_logininfor` VALUES ('142', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-06 18:11:06');
INSERT INTO `sys_logininfor` VALUES ('143', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码错误', '2022-08-06 18:11:39');
INSERT INTO `sys_logininfor` VALUES ('144', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-06 18:11:42');
INSERT INTO `sys_logininfor` VALUES ('145', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-06 18:13:09');
INSERT INTO `sys_logininfor` VALUES ('146', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误1次', '2022-08-06 18:13:18');
INSERT INTO `sys_logininfor` VALUES ('147', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-06 18:13:18');
INSERT INTO `sys_logininfor` VALUES ('148', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误2次', '2022-08-06 18:13:36');
INSERT INTO `sys_logininfor` VALUES ('149', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-06 18:13:36');
INSERT INTO `sys_logininfor` VALUES ('150', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-06 18:13:41');
INSERT INTO `sys_logininfor` VALUES ('151', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误3次', '2022-08-06 18:13:41');
INSERT INTO `sys_logininfor` VALUES ('152', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-06 18:14:39');
INSERT INTO `sys_logininfor` VALUES ('153', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-06 18:15:12');
INSERT INTO `sys_logininfor` VALUES ('154', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-06 22:07:57');
INSERT INTO `sys_logininfor` VALUES ('155', 'admin3', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '密码输入错误1次', '2022-08-06 22:08:39');
INSERT INTO `sys_logininfor` VALUES ('156', 'admin3', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '用户不存在/密码错误', '2022-08-06 22:08:39');
INSERT INTO `sys_logininfor` VALUES ('157', 'admin3', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-06 22:08:52');
INSERT INTO `sys_logininfor` VALUES ('158', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-06 22:10:18');
INSERT INTO `sys_logininfor` VALUES ('159', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-07 09:16:05');
INSERT INTO `sys_logininfor` VALUES ('160', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-07 15:44:07');
INSERT INTO `sys_logininfor` VALUES ('161', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-07 15:54:10');
INSERT INTO `sys_logininfor` VALUES ('162', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-07 16:16:06');
INSERT INTO `sys_logininfor` VALUES ('163', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-07 16:40:56');
INSERT INTO `sys_logininfor` VALUES ('164', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-07 17:45:27');
INSERT INTO `sys_logininfor` VALUES ('165', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-08 07:58:54');
INSERT INTO `sys_logininfor` VALUES ('166', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-08 08:54:15');
INSERT INTO `sys_logininfor` VALUES ('167', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 7 (iPhone)', '0', '登录成功', '2022-08-08 08:55:47');
INSERT INTO `sys_logininfor` VALUES ('168', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-08 11:04:05');
INSERT INTO `sys_logininfor` VALUES ('169', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 7 (iPhone)', '0', '登录成功', '2022-08-08 11:14:27');
INSERT INTO `sys_logininfor` VALUES ('170', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-08 16:13:52');
INSERT INTO `sys_logininfor` VALUES ('171', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-08 16:36:36');
INSERT INTO `sys_logininfor` VALUES ('172', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-08 19:53:32');
INSERT INTO `sys_logininfor` VALUES ('173', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '验证码已失效', '2022-08-08 20:37:52');
INSERT INTO `sys_logininfor` VALUES ('174', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-08 20:37:59');
INSERT INTO `sys_logininfor` VALUES ('175', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-09 09:11:25');
INSERT INTO `sys_logininfor` VALUES ('176', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-09 14:48:47');
INSERT INTO `sys_logininfor` VALUES ('177', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-09 15:36:54');
INSERT INTO `sys_logininfor` VALUES ('178', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-09 17:27:33');
INSERT INTO `sys_logininfor` VALUES ('179', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-09 17:31:09');
INSERT INTO `sys_logininfor` VALUES ('180', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码错误', '2022-08-09 17:31:14');
INSERT INTO `sys_logininfor` VALUES ('181', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-09 17:31:17');
INSERT INTO `sys_logininfor` VALUES ('182', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-09 17:56:16');
INSERT INTO `sys_logininfor` VALUES ('183', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-09 18:14:10');
INSERT INTO `sys_logininfor` VALUES ('184', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-09 18:14:18');
INSERT INTO `sys_logininfor` VALUES ('185', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-09 18:28:18');
INSERT INTO `sys_logininfor` VALUES ('186', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 08:09:54');
INSERT INTO `sys_logininfor` VALUES ('187', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-10 08:17:25');
INSERT INTO `sys_logininfor` VALUES ('188', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 08:57:12');
INSERT INTO `sys_logininfor` VALUES ('189', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 08:58:07');
INSERT INTO `sys_logininfor` VALUES ('190', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-10 09:02:36');
INSERT INTO `sys_logininfor` VALUES ('191', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 09:02:41');
INSERT INTO `sys_logininfor` VALUES ('192', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-10 09:03:47');
INSERT INTO `sys_logininfor` VALUES ('193', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 10:06:09');
INSERT INTO `sys_logininfor` VALUES ('194', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-10 10:08:54');
INSERT INTO `sys_logininfor` VALUES ('195', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 7 (iPhone)', '0', '登录成功', '2022-08-10 11:26:44');
INSERT INTO `sys_logininfor` VALUES ('196', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 22:56:20');
INSERT INTO `sys_logininfor` VALUES ('197', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 7 (iPhone)', '0', '登录成功', '2022-08-10 23:09:52');
INSERT INTO `sys_logininfor` VALUES ('198', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-10 23:24:50');
INSERT INTO `sys_logininfor` VALUES ('199', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-11 10:40:33');
INSERT INTO `sys_logininfor` VALUES ('200', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-14 09:43:22');
INSERT INTO `sys_logininfor` VALUES ('201', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-14 09:43:49');
INSERT INTO `sys_logininfor` VALUES ('202', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-14 17:03:19');
INSERT INTO `sys_logininfor` VALUES ('203', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-15 23:14:49');
INSERT INTO `sys_logininfor` VALUES ('204', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-16 09:14:44');
INSERT INTO `sys_logininfor` VALUES ('205', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-16 09:17:00');
INSERT INTO `sys_logininfor` VALUES ('206', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-16 14:15:08');
INSERT INTO `sys_logininfor` VALUES ('207', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-16 15:14:26');
INSERT INTO `sys_logininfor` VALUES ('208', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-17 11:09:31');
INSERT INTO `sys_logininfor` VALUES ('209', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码错误', '2022-08-17 11:14:59');
INSERT INTO `sys_logininfor` VALUES ('210', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-17 11:15:03');
INSERT INTO `sys_logininfor` VALUES ('211', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-17 15:52:05');
INSERT INTO `sys_logininfor` VALUES ('212', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-17 17:12:53');
INSERT INTO `sys_logininfor` VALUES ('213', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-17 17:27:33');
INSERT INTO `sys_logininfor` VALUES ('214', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-17 17:40:53');
INSERT INTO `sys_logininfor` VALUES ('215', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-17 22:20:44');
INSERT INTO `sys_logininfor` VALUES ('216', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-18 12:02:37');
INSERT INTO `sys_logininfor` VALUES ('217', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-18 23:52:37');
INSERT INTO `sys_logininfor` VALUES ('218', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-18 23:53:26');
INSERT INTO `sys_logininfor` VALUES ('219', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-18 23:53:38');
INSERT INTO `sys_logininfor` VALUES ('220', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-18 23:55:06');
INSERT INTO `sys_logininfor` VALUES ('221', 'zhangxu2', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '注册成功', '2022-08-19 00:00:42');
INSERT INTO `sys_logininfor` VALUES ('222', 'zhangxu2', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 00:00:56');
INSERT INTO `sys_logininfor` VALUES ('223', 'zhangxu2', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-19 00:01:11');
INSERT INTO `sys_logininfor` VALUES ('224', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 00:27:20');
INSERT INTO `sys_logininfor` VALUES ('225', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', 'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2', '2022-08-19 00:28:33');
INSERT INTO `sys_logininfor` VALUES ('226', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 00:29:15');
INSERT INTO `sys_logininfor` VALUES ('227', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', 'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2', '2022-08-19 00:44:44');
INSERT INTO `sys_logininfor` VALUES ('228', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 00:45:42');
INSERT INTO `sys_logininfor` VALUES ('229', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 00:49:54');
INSERT INTO `sys_logininfor` VALUES ('230', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 00:59:27');
INSERT INTO `sys_logininfor` VALUES ('231', null, '127.0.0.1', '内网IP', 'Chrome 40', 'Windows 10', '1', '验证码已失效', '2022-08-19 07:02:02');
INSERT INTO `sys_logininfor` VALUES ('232', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 08:45:08');
INSERT INTO `sys_logininfor` VALUES ('233', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-19 08:45:39');
INSERT INTO `sys_logininfor` VALUES ('234', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误1次', '2022-08-19 08:45:52');
INSERT INTO `sys_logininfor` VALUES ('235', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-08-19 08:45:52');
INSERT INTO `sys_logininfor` VALUES ('236', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-08-19 08:45:58');
INSERT INTO `sys_logininfor` VALUES ('237', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误2次', '2022-08-19 08:45:58');
INSERT INTO `sys_logininfor` VALUES ('238', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 08:46:09');
INSERT INTO `sys_logininfor` VALUES ('239', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 09:36:21');
INSERT INTO `sys_logininfor` VALUES ('240', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', 'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2', '2022-08-19 10:25:38');
INSERT INTO `sys_logininfor` VALUES ('241', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', 'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2', '2022-08-19 10:26:04');
INSERT INTO `sys_logininfor` VALUES ('242', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 10:28:29');
INSERT INTO `sys_logininfor` VALUES ('243', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 10:40:07');
INSERT INTO `sys_logininfor` VALUES ('244', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-19 11:13:33');
INSERT INTO `sys_logininfor` VALUES ('245', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误1次', '2022-08-19 11:14:00');
INSERT INTO `sys_logininfor` VALUES ('246', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-08-19 11:14:00');
INSERT INTO `sys_logininfor` VALUES ('247', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-08-19 11:14:10');
INSERT INTO `sys_logininfor` VALUES ('248', 'admin3', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误2次', '2022-08-19 11:14:10');
INSERT INTO `sys_logininfor` VALUES ('249', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 11:14:57');
INSERT INTO `sys_logininfor` VALUES ('250', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 11:17:12');
INSERT INTO `sys_logininfor` VALUES ('251', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-19 11:25:46');
INSERT INTO `sys_logininfor` VALUES ('252', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 11:25:53');
INSERT INTO `sys_logininfor` VALUES ('253', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-19 11:26:50');
INSERT INTO `sys_logininfor` VALUES ('254', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 11:26:54');
INSERT INTO `sys_logininfor` VALUES ('255', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:00:18');
INSERT INTO `sys_logininfor` VALUES ('256', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:15:08');
INSERT INTO `sys_logininfor` VALUES ('257', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:16:20');
INSERT INTO `sys_logininfor` VALUES ('258', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:16:44');
INSERT INTO `sys_logininfor` VALUES ('259', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码已失效', '2022-08-19 14:18:45');
INSERT INTO `sys_logininfor` VALUES ('260', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:18:52');
INSERT INTO `sys_logininfor` VALUES ('261', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:21:18');
INSERT INTO `sys_logininfor` VALUES ('262', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-19 14:52:31');
INSERT INTO `sys_logininfor` VALUES ('263', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:16:21');
INSERT INTO `sys_logininfor` VALUES ('264', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:17:46');
INSERT INTO `sys_logininfor` VALUES ('265', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-19 15:18:55');
INSERT INTO `sys_logininfor` VALUES ('266', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:22:51');
INSERT INTO `sys_logininfor` VALUES ('267', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', 'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2', '2022-08-19 15:30:43');
INSERT INTO `sys_logininfor` VALUES ('268', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:32:19');
INSERT INTO `sys_logininfor` VALUES ('269', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:33:26');
INSERT INTO `sys_logininfor` VALUES ('270', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:34:42');
INSERT INTO `sys_logininfor` VALUES ('271', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:35:09');
INSERT INTO `sys_logininfor` VALUES ('272', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 15:37:55');
INSERT INTO `sys_logininfor` VALUES ('273', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 21:06:00');
INSERT INTO `sys_logininfor` VALUES ('274', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 21:13:28');
INSERT INTO `sys_logininfor` VALUES ('275', 'weixin123', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-19 21:13:43');
INSERT INTO `sys_logininfor` VALUES ('276', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '验证码错误', '2022-08-19 21:13:55');
INSERT INTO `sys_logininfor` VALUES ('277', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误1次', '2022-08-19 21:14:01');
INSERT INTO `sys_logininfor` VALUES ('278', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-08-19 21:14:01');
INSERT INTO `sys_logininfor` VALUES ('279', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '验证码错误', '2022-08-19 21:14:12');
INSERT INTO `sys_logininfor` VALUES ('280', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-08-19 21:14:16');
INSERT INTO `sys_logininfor` VALUES ('281', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误2次', '2022-08-19 21:14:16');
INSERT INTO `sys_logininfor` VALUES ('282', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 21:14:25');
INSERT INTO `sys_logininfor` VALUES ('283', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码错误', '2022-08-19 21:41:29');
INSERT INTO `sys_logininfor` VALUES ('284', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-19 21:41:53');
INSERT INTO `sys_logininfor` VALUES ('285', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-19 21:51:53');
INSERT INTO `sys_logininfor` VALUES ('286', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2022-08-19 21:52:42');
INSERT INTO `sys_logininfor` VALUES ('287', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2022-08-19 21:52:48');
INSERT INTO `sys_logininfor` VALUES ('288', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误1次', '2022-08-19 21:52:54');
INSERT INTO `sys_logininfor` VALUES ('289', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-19 21:52:54');
INSERT INTO `sys_logininfor` VALUES ('290', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误2次', '2022-08-19 21:53:07');
INSERT INTO `sys_logininfor` VALUES ('291', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-19 21:53:07');
INSERT INTO `sys_logininfor` VALUES ('292', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-19 21:53:26');
INSERT INTO `sys_logininfor` VALUES ('293', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '验证码错误', '2022-08-19 21:54:06');
INSERT INTO `sys_logininfor` VALUES ('294', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-19 21:54:10');
INSERT INTO `sys_logininfor` VALUES ('295', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-19 21:54:43');
INSERT INTO `sys_logininfor` VALUES ('296', 'admin3', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-19 21:54:57');
INSERT INTO `sys_logininfor` VALUES ('297', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '验证码已失效', '2022-08-19 21:59:06');
INSERT INTO `sys_logininfor` VALUES ('298', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 21:59:18');
INSERT INTO `sys_logininfor` VALUES ('299', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-19 22:02:18');
INSERT INTO `sys_logininfor` VALUES ('300', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 22:02:26');
INSERT INTO `sys_logininfor` VALUES ('301', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 22:21:36');
INSERT INTO `sys_logininfor` VALUES ('302', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-19 22:24:13');
INSERT INTO `sys_logininfor` VALUES ('303', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-20 09:01:04');
INSERT INTO `sys_logininfor` VALUES ('304', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 09:58:52');
INSERT INTO `sys_logininfor` VALUES ('305', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 14:20:36');
INSERT INTO `sys_logininfor` VALUES ('306', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 14:23:39');
INSERT INTO `sys_logininfor` VALUES ('307', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 14:37:10');
INSERT INTO `sys_logininfor` VALUES ('308', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-20 14:44:19');
INSERT INTO `sys_logininfor` VALUES ('309', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '验证码已失效', '2022-08-20 14:50:37');
INSERT INTO `sys_logininfor` VALUES ('310', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 14:52:39');
INSERT INTO `sys_logininfor` VALUES ('311', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 14:54:25');
INSERT INTO `sys_logininfor` VALUES ('312', 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-20 15:02:25');
INSERT INTO `sys_logininfor` VALUES ('313', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 22:17:05');
INSERT INTO `sys_logininfor` VALUES ('314', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 22:17:46');
INSERT INTO `sys_logininfor` VALUES ('315', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 22:21:47');
INSERT INTO `sys_logininfor` VALUES ('316', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 22:23:35');
INSERT INTO `sys_logininfor` VALUES ('317', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 22:25:42');
INSERT INTO `sys_logininfor` VALUES ('318', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 22:30:50');
INSERT INTO `sys_logininfor` VALUES ('319', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:00:37');
INSERT INTO `sys_logininfor` VALUES ('320', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:08:55');
INSERT INTO `sys_logininfor` VALUES ('321', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:10:11');
INSERT INTO `sys_logininfor` VALUES ('322', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:12:01');
INSERT INTO `sys_logininfor` VALUES ('323', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:13:56');
INSERT INTO `sys_logininfor` VALUES ('324', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:14:32');
INSERT INTO `sys_logininfor` VALUES ('325', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:15:30');
INSERT INTO `sys_logininfor` VALUES ('326', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:16:45');
INSERT INTO `sys_logininfor` VALUES ('327', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:20:40');
INSERT INTO `sys_logininfor` VALUES ('328', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:21:57');
INSERT INTO `sys_logininfor` VALUES ('329', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:22:10');
INSERT INTO `sys_logininfor` VALUES ('330', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:27:37');
INSERT INTO `sys_logininfor` VALUES ('331', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-20 23:29:01');
INSERT INTO `sys_logininfor` VALUES ('332', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:29:09');
INSERT INTO `sys_logininfor` VALUES ('333', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-20 23:30:57');
INSERT INTO `sys_logininfor` VALUES ('334', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:31:06');
INSERT INTO `sys_logininfor` VALUES ('335', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:38:41');
INSERT INTO `sys_logininfor` VALUES ('336', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:44:41');
INSERT INTO `sys_logininfor` VALUES ('337', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:47:23');
INSERT INTO `sys_logininfor` VALUES ('338', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:49:12');
INSERT INTO `sys_logininfor` VALUES ('339', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:52:35');
INSERT INTO `sys_logininfor` VALUES ('340', 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '验证码错误', '2022-08-20 23:56:08');
INSERT INTO `sys_logininfor` VALUES ('341', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-20 23:56:12');
INSERT INTO `sys_logininfor` VALUES ('342', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 00:03:53');
INSERT INTO `sys_logininfor` VALUES ('343', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 00:05:39');
INSERT INTO `sys_logininfor` VALUES ('344', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 14:55:42');
INSERT INTO `sys_logininfor` VALUES ('345', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-21 15:02:47');
INSERT INTO `sys_logininfor` VALUES ('346', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-21 15:03:03');
INSERT INTO `sys_logininfor` VALUES ('347', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-21 15:19:19');
INSERT INTO `sys_logininfor` VALUES ('348', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-21 15:19:29');
INSERT INTO `sys_logininfor` VALUES ('349', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '验证码已失效', '2022-08-21 15:25:22');
INSERT INTO `sys_logininfor` VALUES ('350', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '验证码错误', '2022-08-21 15:25:28');
INSERT INTO `sys_logininfor` VALUES ('351', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误1次', '2022-08-21 15:25:40');
INSERT INTO `sys_logininfor` VALUES ('352', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-21 15:25:40');
INSERT INTO `sys_logininfor` VALUES ('353', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误2次', '2022-08-21 15:25:47');
INSERT INTO `sys_logininfor` VALUES ('354', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '1', '用户不存在/密码错误', '2022-08-21 15:25:47');
INSERT INTO `sys_logininfor` VALUES ('355', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-21 15:25:55');
INSERT INTO `sys_logininfor` VALUES ('356', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-21 15:26:06');
INSERT INTO `sys_logininfor` VALUES ('357', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-21 15:26:12');
INSERT INTO `sys_logininfor` VALUES ('358', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-21 17:34:51');
INSERT INTO `sys_logininfor` VALUES ('359', 'weixin123', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '退出成功', '2022-08-21 17:35:03');
INSERT INTO `sys_logininfor` VALUES ('360', 'admin', '127.0.0.1', '内网IP', 'Chrome Mobile', 'Android 1.x', '0', '登录成功', '2022-08-21 17:35:09');
INSERT INTO `sys_logininfor` VALUES ('361', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：weixin123 不存在', '2022-08-21 22:05:49');
INSERT INTO `sys_logininfor` VALUES ('362', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：weixin123 不存在', '2022-08-21 22:05:55');
INSERT INTO `sys_logininfor` VALUES ('363', 'weixin123', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：weixin123 不存在', '2022-08-21 22:06:05');
INSERT INTO `sys_logininfor` VALUES ('364', '码哥@码农之旅28dcea9', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 22:11:40');
INSERT INTO `sys_logininfor` VALUES ('365', '码哥@码农之旅28dcea9', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-21 22:14:57');
INSERT INTO `sys_logininfor` VALUES ('366', '码哥@码农之旅5d71362', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 22:18:38');
INSERT INTO `sys_logininfor` VALUES ('367', '码哥@码农之旅5d71362', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-21 22:21:25');
INSERT INTO `sys_logininfor` VALUES ('368', '码哥@码农之旅389e7a9', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 22:21:31');
INSERT INTO `sys_logininfor` VALUES ('369', '码哥@码农之旅389e7a9', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-21 22:24:22');
INSERT INTO `sys_logininfor` VALUES ('370', '码哥@码农之旅0104392', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 22:24:31');
INSERT INTO `sys_logininfor` VALUES ('371', '码哥@码农之旅0104392', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-21 22:26:57');
INSERT INTO `sys_logininfor` VALUES ('372', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 22:27:54');
INSERT INTO `sys_logininfor` VALUES ('373', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-21 22:55:30');
INSERT INTO `sys_logininfor` VALUES ('374', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2022-08-21 22:56:07');
INSERT INTO `sys_logininfor` VALUES ('375', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-21 22:56:17');
INSERT INTO `sys_logininfor` VALUES ('376', '码哥@码农725d11f', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农725d11f 不存在', '2022-08-21 22:56:32');
INSERT INTO `sys_logininfor` VALUES ('377', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-21 22:57:56');
INSERT INTO `sys_logininfor` VALUES ('378', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '验证码已失效', '2022-08-21 23:01:06');
INSERT INTO `sys_logininfor` VALUES ('379', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-21 23:01:11');
INSERT INTO `sys_logininfor` VALUES ('380', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-21 23:01:35');
INSERT INTO `sys_logininfor` VALUES ('381', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-08-21 23:02:04');
INSERT INTO `sys_logininfor` VALUES ('382', '码哥@码农41d31f4', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '退出成功', '2022-08-21 23:06:22');
INSERT INTO `sys_logininfor` VALUES ('383', '码哥@码农7c0723b', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农7c0723b 不存在', '2022-08-21 23:09:23');
INSERT INTO `sys_logininfor` VALUES ('384', '码哥@码农e4ca6f5', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农e4ca6f5 不存在', '2022-08-21 23:09:29');
INSERT INTO `sys_logininfor` VALUES ('385', '码哥@码农cbea534', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农cbea534 不存在', '2022-08-21 23:16:02');
INSERT INTO `sys_logininfor` VALUES ('386', '码哥@码农023781b', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农023781b 不存在', '2022-08-21 23:20:45');
INSERT INTO `sys_logininfor` VALUES ('387', '码哥@码农dcbe1dd', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-21 23:21:00');
INSERT INTO `sys_logininfor` VALUES ('388', '码哥@码农3effb9c', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 08:54:37');
INSERT INTO `sys_logininfor` VALUES ('389', '码哥@码农3effb9c', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 08:54:55');
INSERT INTO `sys_logininfor` VALUES ('390', '码哥@码农6ae2327', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农6ae2327 不存在', '2022-08-22 08:58:08');
INSERT INTO `sys_logininfor` VALUES ('391', '码哥@码农bae4151', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 08:58:23');
INSERT INTO `sys_logininfor` VALUES ('392', '码哥@码农ac41709', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '登录用户：码哥@码农ac41709 不存在', '2022-08-22 10:35:09');
INSERT INTO `sys_logininfor` VALUES ('393', '码哥@码农7ba9685', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 10:41:28');
INSERT INTO `sys_logininfor` VALUES ('394', '码哥@码农0a88b8e', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 10:42:31');
INSERT INTO `sys_logininfor` VALUES ('395', '码哥@码农8d39973', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 10:45:38');
INSERT INTO `sys_logininfor` VALUES ('396', '码哥@码农57da277', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 10:46:37');
INSERT INTO `sys_logininfor` VALUES ('397', '码哥@码农ace8862', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 10:49:48');
INSERT INTO `sys_logininfor` VALUES ('398', '码哥@码农ace8862', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 11:00:33');
INSERT INTO `sys_logininfor` VALUES ('399', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 11:00:40');
INSERT INTO `sys_logininfor` VALUES ('400', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 11:03:41');
INSERT INTO `sys_logininfor` VALUES ('401', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '用户不存在/密码错误', '2022-08-22 11:18:22');
INSERT INTO `sys_logininfor` VALUES ('402', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '密码输入错误1次', '2022-08-22 11:18:22');
INSERT INTO `sys_logininfor` VALUES ('403', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '密码输入错误2次', '2022-08-22 11:24:52');
INSERT INTO `sys_logininfor` VALUES ('404', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '1', '用户不存在/密码错误', '2022-08-22 11:24:52');
INSERT INTO `sys_logininfor` VALUES ('405', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 11:29:55');
INSERT INTO `sys_logininfor` VALUES ('406', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 11:32:00');
INSERT INTO `sys_logininfor` VALUES ('407', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 11:32:13');
INSERT INTO `sys_logininfor` VALUES ('408', 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2022-08-22 11:45:07');
INSERT INTO `sys_logininfor` VALUES ('409', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 11:50:37');
INSERT INTO `sys_logininfor` VALUES ('410', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 11:50:46');
INSERT INTO `sys_logininfor` VALUES ('411', '码哥@码农e601485', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 11:51:08');
INSERT INTO `sys_logininfor` VALUES ('412', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 11:51:12');
INSERT INTO `sys_logininfor` VALUES ('413', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 15:10:22');
INSERT INTO `sys_logininfor` VALUES ('414', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 21:04:02');
INSERT INTO `sys_logininfor` VALUES ('415', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 21:05:12');
INSERT INTO `sys_logininfor` VALUES ('416', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 21:05:31');
INSERT INTO `sys_logininfor` VALUES ('417', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 21:05:46');
INSERT INTO `sys_logininfor` VALUES ('418', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 21:25:15');
INSERT INTO `sys_logininfor` VALUES ('419', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 21:25:27');
INSERT INTO `sys_logininfor` VALUES ('420', '码哥@码农b03f9bf', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 21:25:35');
INSERT INTO `sys_logininfor` VALUES ('421', null, '127.0.0.1', '内网IP', 'Firefox 7', 'Windows 10', '1', '验证码已失效', '2022-08-22 22:48:09');
INSERT INTO `sys_logininfor` VALUES ('422', '码哥@码农58a1e64', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:02:04');
INSERT INTO `sys_logininfor` VALUES ('423', '码哥@码农58a1e64', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 23:02:28');
INSERT INTO `sys_logininfor` VALUES ('424', '码哥@码农58a1e64', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:02:32');
INSERT INTO `sys_logininfor` VALUES ('425', '码哥@码农58a1e64', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 23:05:51');
INSERT INTO `sys_logininfor` VALUES ('426', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:05:56');
INSERT INTO `sys_logininfor` VALUES ('427', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 23:06:08');
INSERT INTO `sys_logininfor` VALUES ('428', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:07:56');
INSERT INTO `sys_logininfor` VALUES ('429', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:43:40');
INSERT INTO `sys_logininfor` VALUES ('430', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 23:43:50');
INSERT INTO `sys_logininfor` VALUES ('431', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:43:57');
INSERT INTO `sys_logininfor` VALUES ('432', '码哥@码农4076289', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 23:55:58');
INSERT INTO `sys_logininfor` VALUES ('433', '码哥@码农179ef67', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:56:10');
INSERT INTO `sys_logininfor` VALUES ('434', '码哥@码农179ef67', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '退出成功', '2022-08-22 23:56:26');
INSERT INTO `sys_logininfor` VALUES ('435', '码哥@码农179ef67', '127.0.0.1', '内网IP', 'Mobile Safari', 'iOS 11 (iPhone)', '0', '登录成功', '2022-08-22 23:56:33');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2042 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '1', 'system', null, '', '1', '0', 'M', '0', '0', '', 'system', 'admin', '2022-07-30 22:58:43', '', null, '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '系统监控', '0', '2', 'monitor', null, '', '1', '0', 'M', '0', '0', '', 'monitor', 'admin', '2022-07-30 22:58:43', '', null, '系统监控目录');
INSERT INTO `sys_menu` VALUES ('3', '系统工具', '0', '3', 'tool', null, '', '1', '0', 'M', '0', '0', '', 'tool', 'admin', '2022-07-30 22:58:43', '', null, '系统工具目录');
INSERT INTO `sys_menu` VALUES ('4', '若依官网', '0', '4', 'http://ruoyi.vip', null, '', '0', '0', 'M', '0', '0', '', 'guide', 'admin', '2022-07-30 22:58:43', '', null, '若依官网地址');
INSERT INTO `sys_menu` VALUES ('5', '师生系统管理', '0', '5', 'timetable', null, '', '1', '0', 'M', '0', '0', '', 'system', 'admin', '2022-07-30 22:58:43', '', null, '系统管理目录');
INSERT INTO `sys_menu` VALUES ('100', '用户管理', '1', '1', 'user', 'system/user/index', '', '1', '0', 'C', '0', '0', 'system:user:list', 'user', 'admin', '2022-07-30 22:58:43', '', null, '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('101', '角色管理', '1', '2', 'role', 'system/role/index', '', '1', '0', 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2022-07-30 22:58:43', '', null, '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('102', '菜单管理', '1', '3', 'menu', 'system/menu/index', '', '1', '0', 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2022-07-30 22:58:43', '', null, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('103', '部门管理', '1', '4', 'dept', 'system/dept/index', '', '1', '0', 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2022-07-30 22:58:43', '', null, '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('104', '岗位管理', '1', '5', 'post', 'system/post/index', '', '1', '0', 'C', '0', '0', 'system:post:list', 'post', 'admin', '2022-07-30 22:58:43', '', null, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES ('105', '字典管理', '1', '6', 'dict', 'system/dict/index', '', '1', '0', 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2022-07-30 22:58:43', '', null, '字典管理菜单');
INSERT INTO `sys_menu` VALUES ('106', '参数设置', '1', '7', 'config', 'system/config/index', '', '1', '0', 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2022-07-30 22:58:43', '', null, '参数设置菜单');
INSERT INTO `sys_menu` VALUES ('107', '通知公告', '1', '8', 'notice', 'system/notice/index', '', '1', '0', 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2022-07-30 22:58:43', '', null, '通知公告菜单');
INSERT INTO `sys_menu` VALUES ('108', '日志管理', '1', '9', 'log', '', '', '1', '0', 'M', '0', '0', '', 'log', 'admin', '2022-07-30 22:58:43', '', null, '日志管理菜单');
INSERT INTO `sys_menu` VALUES ('109', '在线用户', '2', '1', 'online', 'monitor/online/index', '', '1', '0', 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2022-07-30 22:58:43', '', null, '在线用户菜单');
INSERT INTO `sys_menu` VALUES ('110', '定时任务', '2', '2', 'job', 'monitor/job/index', '', '1', '0', 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2022-07-30 22:58:43', '', null, '定时任务菜单');
INSERT INTO `sys_menu` VALUES ('111', '数据监控', '2', '3', 'druid', 'monitor/druid/index', '', '1', '0', 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2022-07-30 22:58:43', '', null, '数据监控菜单');
INSERT INTO `sys_menu` VALUES ('112', '服务监控', '2', '4', 'server', 'monitor/server/index', '', '1', '0', 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2022-07-30 22:58:43', '', null, '服务监控菜单');
INSERT INTO `sys_menu` VALUES ('113', '缓存监控', '2', '5', 'cache', 'monitor/cache/index', '', '1', '0', 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2022-07-30 22:58:43', '', null, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES ('114', '缓存列表', '2', '6', 'cacheList', 'monitor/cache/list', '', '1', '0', 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2022-07-30 22:58:43', '', null, '缓存列表菜单');
INSERT INTO `sys_menu` VALUES ('115', '表单构建', '3', '1', 'build', 'tool/build/index', '', '1', '0', 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2022-07-30 22:58:43', '', null, '表单构建菜单');
INSERT INTO `sys_menu` VALUES ('116', '代码生成', '3', '2', 'gen', 'tool/gen/index', '', '1', '0', 'C', '0', '0', 'tool:gen:list', 'link', 'admin', '2022-07-30 22:58:43', 'admin', '2022-08-09 17:59:41', '代码生成菜单');
INSERT INTO `sys_menu` VALUES ('117', '系统接口', '3', '3', 'swagger', 'tool/swagger/index', '', '1', '0', 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2022-07-30 22:58:43', '', null, '系统接口菜单');
INSERT INTO `sys_menu` VALUES ('500', '操作日志', '108', '1', 'operlog', 'monitor/operlog/index', '', '1', '0', 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2022-07-30 22:58:43', '', null, '操作日志菜单');
INSERT INTO `sys_menu` VALUES ('501', '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '1', '0', 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2022-07-30 22:58:43', '', null, '登录日志菜单');
INSERT INTO `sys_menu` VALUES ('1000', '用户查询', '100', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:user:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1001', '用户新增', '100', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:user:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1002', '用户修改', '100', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:user:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1003', '用户删除', '100', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:user:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1004', '用户导出', '100', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:user:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1005', '用户导入', '100', '6', '', '', '', '1', '0', 'F', '0', '0', 'system:user:import', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1006', '重置密码', '100', '7', '', '', '', '1', '0', 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1007', '角色查询', '101', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:role:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1008', '角色新增', '101', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:role:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1009', '角色修改', '101', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:role:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1010', '角色删除', '101', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:role:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1011', '角色导出', '101', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:role:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1012', '菜单查询', '102', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1013', '菜单新增', '102', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1014', '菜单修改', '102', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1015', '菜单删除', '102', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1016', '部门查询', '103', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1017', '部门新增', '103', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1018', '部门修改', '103', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1019', '部门删除', '103', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1020', '岗位查询', '104', '1', '', '', '', '1', '0', 'F', '0', '0', 'system:post:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1021', '岗位新增', '104', '2', '', '', '', '1', '0', 'F', '0', '0', 'system:post:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1022', '岗位修改', '104', '3', '', '', '', '1', '0', 'F', '0', '0', 'system:post:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1023', '岗位删除', '104', '4', '', '', '', '1', '0', 'F', '0', '0', 'system:post:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1024', '岗位导出', '104', '5', '', '', '', '1', '0', 'F', '0', '0', 'system:post:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1025', '字典查询', '105', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1026', '字典新增', '105', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1027', '字典修改', '105', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1028', '字典删除', '105', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1029', '字典导出', '105', '5', '#', '', '', '1', '0', 'F', '0', '0', 'system:dict:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1030', '参数查询', '106', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1031', '参数新增', '106', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1032', '参数修改', '106', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1033', '参数删除', '106', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1034', '参数导出', '106', '5', '#', '', '', '1', '0', 'F', '0', '0', 'system:config:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1035', '公告查询', '107', '1', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1036', '公告新增', '107', '2', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1037', '公告修改', '107', '3', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1038', '公告删除', '107', '4', '#', '', '', '1', '0', 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1039', '操作查询', '500', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1040', '操作删除', '500', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1041', '日志导出', '500', '4', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1042', '登录查询', '501', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1043', '登录删除', '501', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1044', '日志导出', '501', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1045', '在线查询', '109', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1046', '批量强退', '109', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1047', '单条强退', '109', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1048', '任务查询', '110', '1', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1049', '任务新增', '110', '2', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1050', '任务修改', '110', '3', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1051', '任务删除', '110', '4', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1052', '状态修改', '110', '5', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1053', '任务导出', '110', '7', '#', '', '', '1', '0', 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1054', '生成查询', '115', '1', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1055', '生成修改', '115', '2', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1056', '生成删除', '115', '3', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1057', '导入代码', '115', '2', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1058', '预览代码', '115', '4', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('1059', '生成代码', '115', '5', '#', '', '', '1', '0', 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2022-07-30 22:58:43', '', null, '');
INSERT INTO `sys_menu` VALUES ('2012', '学校信息', '5', '1', 'school', 'timetable/school/index', null, '1', '0', 'C', '0', '0', 'timetable:school:list', '#', 'admin', '2022-08-09 17:29:34', 'admin', '2022-08-09 18:00:02', '学校信息菜单');
INSERT INTO `sys_menu` VALUES ('2013', '学校信息查询', '2012', '1', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:school:query', '#', 'admin', '2022-08-09 17:29:34', '', null, '');
INSERT INTO `sys_menu` VALUES ('2014', '学校信息新增', '2012', '2', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:school:add', '#', 'admin', '2022-08-09 17:29:34', '', null, '');
INSERT INTO `sys_menu` VALUES ('2015', '学校信息修改', '2012', '3', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:school:edit', '#', 'admin', '2022-08-09 17:29:34', '', null, '');
INSERT INTO `sys_menu` VALUES ('2016', '学校信息删除', '2012', '4', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:school:remove', '#', 'admin', '2022-08-09 17:29:34', '', null, '');
INSERT INTO `sys_menu` VALUES ('2017', '学校信息导出', '2012', '5', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:school:export', '#', 'admin', '2022-08-09 17:29:34', '', null, '');
INSERT INTO `sys_menu` VALUES ('2018', '班级管理', '5', '1', 'grade', 'timetable/grade/index', null, '1', '0', 'C', '0', '0', 'timetable:grade:list', '#', 'admin', '2022-08-10 08:59:45', 'admin', '2022-08-10 09:10:04', '课程班级管理菜单');
INSERT INTO `sys_menu` VALUES ('2019', '课程班级管理查询', '2018', '1', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:grade:query', '#', 'admin', '2022-08-10 08:59:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2020', '课程班级管理新增', '2018', '2', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:grade:add', '#', 'admin', '2022-08-10 08:59:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2021', '课程班级管理修改', '2018', '3', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:grade:edit', '#', 'admin', '2022-08-10 08:59:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2022', '课程班级管理删除', '2018', '4', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:grade:remove', '#', 'admin', '2022-08-10 08:59:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2023', '课程班级管理导出', '2018', '5', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:grade:export', '#', 'admin', '2022-08-10 08:59:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2024', '课程管理', '5', '1', 'course', 'timetable/course/index', null, '1', '0', 'C', '0', '0', 'timetable:course:list', '#', 'admin', '2022-08-10 09:10:44', 'admin', '2022-08-10 09:11:21', '课程管理菜单');
INSERT INTO `sys_menu` VALUES ('2025', '课程管理查询', '2024', '1', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:course:query', '#', 'admin', '2022-08-10 09:10:44', '', null, '');
INSERT INTO `sys_menu` VALUES ('2026', '课程管理新增', '2024', '2', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:course:add', '#', 'admin', '2022-08-10 09:10:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2027', '课程管理修改', '2024', '3', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:course:edit', '#', 'admin', '2022-08-10 09:10:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2028', '课程管理删除', '2024', '4', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:course:remove', '#', 'admin', '2022-08-10 09:10:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2029', '课程管理导出', '2024', '5', '#', '', null, '1', '0', 'F', '0', '0', 'timetable:course:export', '#', 'admin', '2022-08-10 09:10:45', '', null, '');
INSERT INTO `sys_menu` VALUES ('2030', '微信用户', '0', '6', 'wxUser', 'wxUser/wxUser/index', null, '1', '0', 'C', '0', '0', 'wxUser:wxUser:list', 'wechat', 'admin', '2022-08-17 11:34:25', 'admin', '2022-08-19 11:24:01', '微信用户菜单');
INSERT INTO `sys_menu` VALUES ('2031', '微信用户查询', '2030', '1', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:wxUser:query', '#', 'admin', '2022-08-17 11:34:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('2032', '微信用户新增', '2030', '2', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:wxUser:add', '#', 'admin', '2022-08-17 11:34:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('2033', '微信用户修改', '2030', '3', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:wxUser:edit', '#', 'admin', '2022-08-17 11:34:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('2034', '微信用户删除', '2030', '4', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:wxUser:remove', '#', 'admin', '2022-08-17 11:34:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('2035', '微信用户导出', '2030', '5', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:wxUser:export', '#', 'admin', '2022-08-17 11:34:25', '', null, '');
INSERT INTO `sys_menu` VALUES ('2036', '微信小程序配置', '0', '7', 'appConfig', 'wxUser/appConfig/index', null, '1', '0', 'C', '0', '0', 'wxUser:appConfig:list', 'wechat', 'admin', '2022-08-17 11:34:42', 'admin', '2022-08-19 11:25:00', '微信小程序配置菜单');
INSERT INTO `sys_menu` VALUES ('2037', '小程序配置查询', '2036', '1', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:appConfig:query', '#', 'admin', '2022-08-17 11:34:42', '', null, '');
INSERT INTO `sys_menu` VALUES ('2038', '小程序配置新增', '2036', '2', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:appConfig:add', '#', 'admin', '2022-08-17 11:34:42', '', null, '');
INSERT INTO `sys_menu` VALUES ('2039', '小程序配置修改', '2036', '3', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:appConfig:edit', '#', 'admin', '2022-08-17 11:34:42', '', null, '');
INSERT INTO `sys_menu` VALUES ('2040', '小程序配置删除', '2036', '4', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:appConfig:remove', '#', 'admin', '2022-08-17 11:34:42', '', null, '');
INSERT INTO `sys_menu` VALUES ('2041', '小程序配置导出', '2036', '5', '#', '', null, '1', '0', 'F', '0', '0', 'wxUser:appConfig:export', '#', 'admin', '2022-08-17 11:34:42', '', null, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1', '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2022-07-30 22:58:44', '', null, '管理员');
INSERT INTO `sys_notice` VALUES ('2', '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2022-07-30 22:58:44', '', null, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8mb3 COMMENT='操作日志记录';

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES ('100', '代码生成', '6', 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', '1', 'admin', null, '/tool/gen/importTable', '127.0.0.1', '内网IP', 'ib_timetable_grade', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-07-30 23:23:04');
INSERT INTO `sys_oper_log` VALUES ('101', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-07-30 23:23:10');
INSERT INTO `sys_oper_log` VALUES ('102', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-07-30 23:23:42');
INSERT INTO `sys_oper_log` VALUES ('103', '课程班级管理', '2', 'com.ruoyi.system.controller.IbTimetableGradeController.edit()', 'PUT', '1', 'admin', null, '/system/grade', '127.0.0.1', '内网IP', '{\"classTitle\":\"计算机应用2002\",\"createBy\":\"\",\"gradeId\":1,\"gradeYear\":\"2002\",\"params\":{},\"remark\":\"\",\"school\":\"宿迁泽达职业技术学院2\",\"updateBy\":\"\",\"updateTime\":\"2022-07-30T23:28:39.214+08:00\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-07-30 23:28:39');
INSERT INTO `sys_oper_log` VALUES ('104', '参数管理', '2', 'com.ruoyi.web.controller.system.SysConfigController.edit()', 'PUT', '1', 'admin', null, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"N\",\"configValue\":\"true\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:44\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:15:57');
INSERT INTO `sys_oper_log` VALUES ('105', '用户管理', '1', 'com.ruoyi.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"test\",\"params\":{},\"password\":\"$2a$10$IiQfhIlIul1MSeG6XGCtGu09hcHiwQOAJYNf3s.3qP7IdaRShXabO\",\"postIds\":[],\"roleIds\":[],\"status\":\"0\",\"userId\":100,\"userName\":\"test\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:20:55');
INSERT INTO `sys_oper_log` VALUES ('106', '用户管理', '2', 'com.ruoyi.web.controller.system.SysUserController.edit()', 'PUT', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2022-08-04 22:20:55\",\"delFlag\":\"0\",\"email\":\"\",\"loginDate\":\"2022-08-04 22:21:14\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"test\",\"params\":{},\"password\":\"\",\"phonenumber\":\"\",\"postIds\":[],\"roleIds\":[2],\"roles\":[],\"sex\":\"0\",\"status\":\"0\",\"updateBy\":\"admin\",\"userId\":100,\"userName\":\"test\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:22:04');
INSERT INTO `sys_oper_log` VALUES ('107', '角色管理', '1', 'com.ruoyi.web.controller.system.SysRoleController.add()', 'POST', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":true,\"deptIds\":[],\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[3,2000,2001,2002,2003,2004,2005,117],\"params\":{},\"roleId\":100,\"roleKey\":\"teacherStudentAdmin\",\"roleName\":\"师生易趣管理员\",\"roleSort\":\"3\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:27:05');
INSERT INTO `sys_oper_log` VALUES ('108', '用户管理', '2', 'com.ruoyi.web.controller.system.SysUserController.edit()', 'PUT', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2022-08-04 22:20:55\",\"delFlag\":\"0\",\"email\":\"\",\"loginDate\":\"2022-08-04 22:28:29\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"test\",\"params\":{},\"password\":\"\",\"phonenumber\":\"\",\"postIds\":[],\"roleIds\":[100],\"roles\":[{\"admin\":false,\"dataScope\":\"2\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":2,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"roleSort\":\"2\",\"status\":\"0\"}],\"sex\":\"0\",\"status\":\"0\",\"updateBy\":\"admin\",\"userId\":100,\"userName\":\"test\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:28:49');
INSERT INTO `sys_oper_log` VALUES ('109', '角色管理', '2', 'com.ruoyi.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2022-08-04 22:27:05\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[3,117,5,2000,2001,2002,2003,2004,2005],\"params\":{},\"roleId\":100,\"roleKey\":\"teacherStudentAdmin\",\"roleName\":\"师生易趣管理员\",\"roleSort\":\"3\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:46:12');
INSERT INTO `sys_oper_log` VALUES ('110', '用户管理', '3', 'com.ruoyi.web.controller.system.SysUserController.remove()', 'DELETE', '1', 'admin', null, '/system/user/100', '127.0.0.1', '内网IP', '{userIds=100}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:48:19');
INSERT INTO `sys_oper_log` VALUES ('111', '用户管理', '1', 'com.ruoyi.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"师生易趣管理\",\"params\":{},\"password\":\"$2a$10$w8PZdPS9P.GShapUlktva.stWkxJARPntp6Mi7Bjw4AU5gMgoeyNS\",\"postIds\":[],\"remark\":\"师生易趣管理系统管理员与开发人员\",\"roleIds\":[100],\"status\":\"0\",\"userId\":101,\"userName\":\"admin3\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-04 22:50:10');
INSERT INTO `sys_oper_log` VALUES ('112', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-05 17:18:59');
INSERT INTO `sys_oper_log` VALUES ('113', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-05 17:19:33');
INSERT INTO `sys_oper_log` VALUES ('114', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":1,\"columnName\":\"grade_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"School\",\"columnComment\":\"班级所在院校\",\"columnId\":2,\"columnName\":\"school\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"school\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":3,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"gradeYear\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"ClassTitle\",\"columnComment\":\"班级名称\",\"columnId\":4,\"columnName\":\"class_title\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"classTitle\",\"javaType\":\"String\",\"list\":t', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-05 17:25:55');
INSERT INTO `sys_oper_log` VALUES ('115', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-05 17:26:13');
INSERT INTO `sys_oper_log` VALUES ('116', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":1,\"columnName\":\"grade_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2022-08-05 17:25:55\",\"usableColumn\":false},{\"capJavaField\":\"School\",\"columnComment\":\"班级所在院校\",\"columnId\":2,\"columnName\":\"school\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"school\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2022-08-05 17:25:55\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":3,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"gradeYear\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2022-08-05 17:25:55\",\"usableColumn\":false},{\"capJavaField\":\"ClassTitle\",\"columnComment\":\"班级名称\",\"columnId\":4,\"columnName\":\"class_title\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-05 17:29:53');
INSERT INTO `sys_oper_log` VALUES ('117', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":1,\"columnName\":\"grade_id\",\"columnType\":\"int(11)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2022-08-05 17:29:53\",\"usableColumn\":false},{\"capJavaField\":\"School\",\"columnComment\":\"班级所在院校\",\"columnId\":2,\"columnName\":\"school\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"school\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2022-08-05 17:29:53\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":3,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"gradeYear\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2022-08-05 17:29:53\",\"usableColumn\":false},{\"capJavaField\":\"ClassTitle\",\"columnComment\":\"班级名称\",\"columnId\":4,\"columnName\":\"class_title\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 23:23:04\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-05 17:31:21');
INSERT INTO `sys_oper_log` VALUES ('118', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-05 17:31:58');
INSERT INTO `sys_oper_log` VALUES ('119', '课程班级管理', '1', 'com.ruoyi.system.controller.IbTimetableGradeController.add()', 'POST', '1', 'admin', null, '/timetable/grade', '127.0.0.1', '内网IP', '{\"classTitle\":\"11\",\"createTime\":\"2022-08-05T18:22:52.437+08:00\",\"gradeId\":3,\"gradeYear\":\"11\",\"params\":{},\"remark\":\"11\",\"school\":\"高三年级\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-05 18:22:52');
INSERT INTO `sys_oper_log` VALUES ('120', '课程班级管理', '3', 'com.ruoyi.system.controller.IbTimetableGradeController.remove()', 'DELETE', '1', 'admin', null, '/timetable/grade/3', '127.0.0.1', '内网IP', '{gradeIds=3}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-05 18:23:05');
INSERT INTO `sys_oper_log` VALUES ('121', '角色管理', '2', 'com.ruoyi.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2022-08-04 22:27:05\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[3,117,5,2006,2007,2008,2009,2010,2011],\"params\":{},\"roleId\":100,\"roleKey\":\"teacherStudentAdmin\",\"roleName\":\"师生易趣管理员\",\"roleSort\":\"3\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-06 11:10:13');
INSERT INTO `sys_oper_log` VALUES ('122', '个人信息', '2', 'com.ruoyi.web.controller.system.SysProfileController.updateProfile()', 'PUT', '1', 'admin', null, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":true,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:42\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{\"@type\":\"java.util.HashMap\"},\"parentId\":101,\"status\":\"0\"},\"deptId\":103,\"email\":\"ry@163.com\",\"loginDate\":\"2022-08-06 18:09:51\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"若依22\",\"params\":{\"@type\":\"java.util.HashMap\"},\"phonenumber\":\"15888888888\",\"remark\":\"管理员\",\"roles\":[{\"admin\":true,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{\"@type\":\"java.util.HashMap\"},\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":\"1\",\"status\":\"0\"}],\"sex\":\"1\",\"status\":\"0\",\"userId\":1,\"userName\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-06 18:11:15');
INSERT INTO `sys_oper_log` VALUES ('123', '个人信息', '2', 'com.ruoyi.web.controller.system.SysProfileController.updateProfile()', 'PUT', '1', 'admin', null, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":true,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:42\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{\"@type\":\"java.util.HashMap\"},\"parentId\":101,\"status\":\"0\"},\"deptId\":103,\"email\":\"ry@163.com\",\"loginDate\":\"2022-08-06 18:09:51\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"张许admin\",\"params\":{\"@type\":\"java.util.HashMap\"},\"phonenumber\":\"15888888888\",\"remark\":\"管理员\",\"roles\":[{\"admin\":true,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{\"@type\":\"java.util.HashMap\"},\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":\"1\",\"status\":\"0\"}],\"sex\":\"1\",\"status\":\"0\",\"userId\":1,\"userName\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-06 18:12:18');
INSERT INTO `sys_oper_log` VALUES ('124', '用户头像', '2', 'com.ruoyi.web.controller.system.SysProfileController.avatar()', 'POST', '1', 'admin', null, '/system/user/profile/avatar', '127.0.0.1', '内网IP', '', '{\"msg\":\"操作成功\",\"imgUrl\":\"/profile/avatar/2022/08/06/1659780756584_20220806181236A001.png\",\"code\":200}', '0', null, '2022-08-06 18:12:36');
INSERT INTO `sys_oper_log` VALUES ('125', '用户管理', '2', 'com.ruoyi.web.controller.system.SysUserController.resetPwd()', 'PUT', '1', 'admin', null, '/system/user/resetPwd', '127.0.0.1', '内网IP', '{\"admin\":false,\"params\":{},\"password\":\"$2a$10$4LB9g8R5D8hWtALrUcwe..upLIMbDcXCnRyoRjbaJpypl.fuLxz9O\",\"updateBy\":\"admin\",\"userId\":101}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-06 18:14:16');
INSERT INTO `sys_oper_log` VALUES ('126', '个人信息', '2', 'com.ruoyi.web.controller.system.SysProfileController.updateProfile()', 'PUT', '1', 'admin3', null, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2022-08-04 22:50:10\",\"delFlag\":\"0\",\"email\":\"ibaiqi@163.com\",\"loginDate\":\"2022-08-06 18:14:40\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"师生易趣管理\",\"params\":{\"@type\":\"java.util.HashMap\"},\"phonenumber\":\"15151163228\",\"remark\":\"师生易趣管理系统管理员与开发人员\",\"roles\":[{\"admin\":false,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{\"@type\":\"java.util.HashMap\"},\"roleId\":100,\"roleKey\":\"teacherStudentAdmin\",\"roleName\":\"师生易趣管理员\",\"roleSort\":\"3\",\"status\":\"0\"}],\"sex\":\"0\",\"status\":\"0\",\"userId\":101,\"userName\":\"admin3\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-06 22:12:39');
INSERT INTO `sys_oper_log` VALUES ('127', '参数管理', '2', 'com.ruoyi.web.controller.system.SysConfigController.edit()', 'PUT', '1', 'admin', null, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"Y\",\"configValue\":\"true\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:44\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\",\"updateTime\":\"2022-08-04 22:15:57\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 09:17:34');
INSERT INTO `sys_oper_log` VALUES ('128', '参数管理', '9', 'com.ruoyi.web.controller.system.SysConfigController.refreshCache()', 'DELETE', '1', 'admin', null, '/system/config/refreshCache', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 09:18:19');
INSERT INTO `sys_oper_log` VALUES ('129', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"classTitle\":\"string\",\"createBy\":\"string\",\"createTime\":\"2022-08-07T09:39:46.316+08:00\",\"gradeId\":4,\"params\":{},\"remark\":\"string\",\"school\":\"string\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 09:39:46');
INSERT INTO `sys_oper_log` VALUES ('130', '课程班级管理', '2', 'com.ruoyi.system.controller.timeTable2.IbTimetableGradeController.edit()', 'PUT', '1', 'admin', null, '/timetable/grade', '127.0.0.1', '内网IP', '{\"classTitle\":\"string\",\"createBy\":\"string\",\"createTime\":\"2022-08-07 09:39:46\",\"gradeId\":4,\"params\":{},\"remark\":\"string\",\"school\":\"宿迁师范学校\",\"updateBy\":\"\",\"updateTime\":\"2022-08-07T09:46:30.944+08:00\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 09:46:31');
INSERT INTO `sys_oper_log` VALUES ('131', '个人信息', '2', 'com.ruoyi.web.controller.system.SysProfileController.updateProfile()', 'PUT', '1', 'admin', null, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":true,\"avatar\":\"/profile/avatar/2022/08/06/1659780756584_20220806181236A001.png\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:42\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{\"@type\":\"java.util.HashMap\"},\"parentId\":101,\"status\":\"0\"},\"deptId\":103,\"email\":\"ry@163.com\",\"loginDate\":\"2022-08-07 15:54:10\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"张许admin\",\"params\":{\"@type\":\"java.util.HashMap\"},\"phonenumber\":\"15888888888\",\"remark\":\"管理员\",\"roles\":[{\"admin\":true,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{\"@type\":\"java.util.HashMap\"},\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":\"1\",\"status\":\"0\"}],\"sex\":\"1\",\"status\":\"0\",\"userId\":1,\"userName\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 16:19:02');
INSERT INTO `sys_oper_log` VALUES ('132', '个人信息', '2', 'com.ruoyi.web.controller.system.SysProfileController.updateProfile()', 'PUT', '1', 'admin', null, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":true,\"avatar\":\"/profile/avatar/2022/08/06/1659780756584_20220806181236A001.png\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:42\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{\"@type\":\"java.util.HashMap\"},\"parentId\":101,\"status\":\"0\"},\"deptId\":103,\"email\":\"ry@163.com\",\"loginDate\":\"2022-08-07 15:54:10\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"张许admin22\",\"params\":{\"@type\":\"java.util.HashMap\"},\"phonenumber\":\"15888888888\",\"remark\":\"管理员\",\"roles\":[{\"admin\":true,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{\"@type\":\"java.util.HashMap\"},\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":\"1\",\"status\":\"0\"}],\"sex\":\"1\",\"status\":\"0\",\"userId\":1,\"userName\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 16:19:06');
INSERT INTO `sys_oper_log` VALUES ('133', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T16:39:49.553+08:00\",\"gradeId\":5,\"params\":{},\"school\":\"11\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 16:39:49');
INSERT INTO `sys_oper_log` VALUES ('134', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T16:41:17.686+08:00\",\"gradeId\":6,\"params\":{},\"school\":\"添加学校名称二2\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 16:41:17');
INSERT INTO `sys_oper_log` VALUES ('135', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T17:45:37.191+08:00\",\"gradeId\":7,\"params\":{},\"school\":\"府苑小学\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 17:45:37');
INSERT INTO `sys_oper_log` VALUES ('136', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T18:07:07.724+08:00\",\"gradeId\":8,\"params\":{},\"school\":\"泗洪中学\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 18:07:07');
INSERT INTO `sys_oper_log` VALUES ('137', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T18:07:53.77+08:00\",\"gradeId\":9,\"params\":{},\"school\":\"府苑小学2\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 18:07:53');
INSERT INTO `sys_oper_log` VALUES ('138', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T18:08:24.611+08:00\",\"gradeId\":10,\"params\":{},\"school\":\"府苑小学3\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 18:08:24');
INSERT INTO `sys_oper_log` VALUES ('139', '课程班级管理', '1', 'com.ruoyi.system.controller.timeTable2.TimetableGradeController.add()', 'POST', '1', 'admin', null, '/api/v1/timetable/grade', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-07T18:08:56.153+08:00\",\"gradeId\":11,\"params\":{},\"school\":\"府苑小学4\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-07 18:08:56');
INSERT INTO `sys_oper_log` VALUES ('140', '用户管理', '1', 'com.ruoyi.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"test3\",\"params\":{},\"password\":\"$2a$10$YeYL4O.ToRHNpLYJCZ66G.ge3licIPph62kwzydxCtEC5yd9GfYwe\",\"postIds\":[],\"roleIds\":[],\"status\":\"0\",\"userId\":102,\"userName\":\"test3\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 09:29:24');
INSERT INTO `sys_oper_log` VALUES ('141', '用户管理', '1', 'com.ruoyi.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"111\",\"params\":{},\"password\":\"$2a$10$WrHdTFSE2EWPoDcowVA5N.4M4b9ifLr1PBG2K7.GPl7wu5Wy2ImnK\",\"phonenumber\":\"\",\"postIds\":[],\"roleIds\":[],\"status\":\"0\",\"userId\":103,\"userName\":\"222\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 10:16:55');
INSERT INTO `sys_oper_log` VALUES ('142', '用户管理', '1', 'com.ruoyi.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"6666\",\"params\":{},\"password\":\"$2a$10$GbEwINiLFBitR4.Ehi4yIOHskMviAXu7DCUUMJON0wGQv.3YByoQO\",\"postIds\":[],\"roleIds\":[],\"status\":\"0\",\"userId\":104,\"userName\":\"7777\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 10:41:12');
INSERT INTO `sys_oper_log` VALUES ('143', '代码生成', '3', 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', '1', 'admin', null, '/tool/gen/1', '127.0.0.1', '内网IP', '{tableIds=1}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:49:03');
INSERT INTO `sys_oper_log` VALUES ('144', '代码生成', '6', 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', '1', 'admin', null, '/tool/gen/importTable', '127.0.0.1', '内网IP', 'ib_timetable_course,ib_timetable_school,ib_timetable_grade', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:49:14');
INSERT INTO `sys_oper_log` VALUES ('145', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"course\",\"className\":\"IbTimetableCourse\",\"columns\":[{\"capJavaField\":\"CourseId\",\"columnComment\":\"课程id\",\"columnId\":10,\"columnName\":\"course_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"courseId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CourseTitle\",\"columnComment\":\"课程名称\",\"columnId\":11,\"columnName\":\"course_title\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"courseTitle\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CourseNotes\",\"columnComment\":\"课程备注\",\"columnId\":12,\"columnName\":\"course_notes\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"courseNotes\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"WeekBegin\",\"columnComment\":\"课程开始周\",\"columnId\":13,\"columnName\":\"week_begin\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"weekBegin\",\"javaType\":\"Lo', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:50:25');
INSERT INTO `sys_oper_log` VALUES ('146', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":26,\"columnName\":\"grade_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"所属学校名称\",\"columnId\":27,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SchoolId\",\"columnComment\":\"所属学校id\",\"columnId\":28,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":29,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"gradeYear\",\"javaType\":\"String\",\"lis', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:50:56');
INSERT INTO `sys_oper_log` VALUES ('147', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"course\",\"className\":\"IbTimetableCourse\",\"columns\":[{\"capJavaField\":\"CourseId\",\"columnComment\":\"课程id\",\"columnId\":10,\"columnName\":\"course_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"courseId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:50:25\",\"usableColumn\":false},{\"capJavaField\":\"CourseTitle\",\"columnComment\":\"课程名称\",\"columnId\":11,\"columnName\":\"course_title\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"courseTitle\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:50:25\",\"usableColumn\":false},{\"capJavaField\":\"CourseNotes\",\"columnComment\":\"课程备注\",\"columnId\":12,\"columnName\":\"course_notes\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"courseNotes\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:50:25\",\"usableColumn\":false},{\"capJavaField\":\"WeekBegin\",\"columnComment\":\"课程开始周\",\"columnId\":13,\"columnName\":\"week_begin\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isI', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:51:04');
INSERT INTO `sys_oper_log` VALUES ('148', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"school\",\"className\":\"IbTimetableSchool\",\"columns\":[{\"capJavaField\":\"SchoolId\",\"columnComment\":\"学校表ID\",\"columnId\":36,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"学校名称\",\"columnId\":37,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CreateBy\",\"columnComment\":\"创建者\",\"columnId\":38,\"columnName\":\"create_by\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaField\":\"createBy\",\"javaType\":\"String\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":true,\"tableId\":4,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CreateTime\",\"columnComment\":\"创建时间\",\"columnId\":39,\"columnName\":\"create_time\",\"columnType\":\"datetime\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"datetime\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaField\":\"createTime\",\"javaType\":\"Date\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"r', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:51:19');
INSERT INTO `sys_oper_log` VALUES ('149', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"course\",\"className\":\"IbTimetableCourse\",\"columns\":[{\"capJavaField\":\"CourseId\",\"columnComment\":\"课程id\",\"columnId\":10,\"columnName\":\"course_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"courseId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:51:03\",\"usableColumn\":false},{\"capJavaField\":\"CourseTitle\",\"columnComment\":\"课程名称\",\"columnId\":11,\"columnName\":\"course_title\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"courseTitle\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:51:03\",\"usableColumn\":false},{\"capJavaField\":\"CourseNotes\",\"columnComment\":\"课程备注\",\"columnId\":12,\"columnName\":\"course_notes\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"courseNotes\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:51:03\",\"usableColumn\":false},{\"capJavaField\":\"WeekBegin\",\"columnComment\":\"课程开始周\",\"columnId\":13,\"columnName\":\"week_begin\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:13\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isI', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:52:09');
INSERT INTO `sys_oper_log` VALUES ('150', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":26,\"columnName\":\"grade_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:50:55\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"所属学校名称\",\"columnId\":27,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:50:55\",\"usableColumn\":false},{\"capJavaField\":\"SchoolId\",\"columnComment\":\"所属学校id\",\"columnId\":28,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:50:55\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":29,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:52:31');
INSERT INTO `sys_oper_log` VALUES ('151', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":26,\"columnName\":\"grade_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:52:30\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"所属学校名称\",\"columnId\":27,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:52:30\",\"usableColumn\":false},{\"capJavaField\":\"SchoolId\",\"columnComment\":\"所属学校id\",\"columnId\":28,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:52:30\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":29,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:53:18');
INSERT INTO `sys_oper_log` VALUES ('152', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"school\",\"className\":\"IbTimetableSchool\",\"columns\":[{\"capJavaField\":\"SchoolId\",\"columnComment\":\"学校表ID\",\"columnId\":36,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:51:19\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"学校名称\",\"columnId\":37,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:51:19\",\"usableColumn\":false},{\"capJavaField\":\"CreateBy\",\"columnComment\":\"创建者\",\"columnId\":38,\"columnName\":\"create_by\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaField\":\"createBy\",\"javaType\":\"String\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":true,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 14:51:19\",\"usableColumn\":false},{\"capJavaField\":\"CreateTime\",\"columnComment\":\"创建时间\",\"columnId\":39,\"columnName\":\"create_time\",\"columnType\":\"datetime\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:49:14\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"datetime\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaFi', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:53:36');
INSERT INTO `sys_oper_log` VALUES ('153', '代码生成', '3', 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', '1', 'admin', null, '/tool/gen/2,3,4', '127.0.0.1', '内网IP', '{tableIds=2,3,4}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:56:41');
INSERT INTO `sys_oper_log` VALUES ('154', '代码生成', '6', 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', '1', 'admin', null, '/tool/gen/importTable', '127.0.0.1', '内网IP', 'ib_timetable_course,ib_timetable_school,ib_timetable_grade', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 14:56:54');
INSERT INTO `sys_oper_log` VALUES ('155', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"school\",\"className\":\"IbTimetableSchool\",\"columns\":[{\"capJavaField\":\"SchoolId\",\"columnComment\":\"学校表ID\",\"columnId\":69,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"学校名称\",\"columnId\":70,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CreateBy\",\"columnComment\":\"创建者\",\"columnId\":71,\"columnName\":\"create_by\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaField\":\"createBy\",\"javaType\":\"String\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":true,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CreateTime\",\"columnComment\":\"创建时间\",\"columnId\":72,\"columnName\":\"create_time\",\"columnType\":\"datetime\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"datetime\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaField\":\"createTime\",\"javaType\":\"Date\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"r', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 15:39:10');
INSERT INTO `sys_oper_log` VALUES ('156', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":59,\"columnName\":\"grade_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"所属学校名称\",\"columnId\":60,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SchoolId\",\"columnComment\":\"所属学校id\",\"columnId\":61,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":62,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"gradeYear\",\"javaType\":\"String\",\"lis', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 15:39:55');
INSERT INTO `sys_oper_log` VALUES ('157', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-09 15:40:01');
INSERT INTO `sys_oper_log` VALUES ('158', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"grade\",\"className\":\"IbTimetableGrade\",\"columns\":[{\"capJavaField\":\"GradeId\",\"columnComment\":\"班级表ID\",\"columnId\":59,\"columnName\":\"grade_id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"gradeId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 15:39:55\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"所属学校名称\",\"columnId\":60,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 15:39:55\",\"usableColumn\":false},{\"capJavaField\":\"SchoolId\",\"columnComment\":\"所属学校id\",\"columnId\":61,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 15:39:55\",\"usableColumn\":false},{\"capJavaField\":\"GradeYear\",\"columnComment\":\"班级年级\",\"columnId\":62,\"columnName\":\"grade_year\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:53\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 15:40:50');
INSERT INTO `sys_oper_log` VALUES ('159', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"school\",\"className\":\"IbTimetableSchool\",\"columns\":[{\"capJavaField\":\"SchoolId\",\"columnComment\":\"学校表ID\",\"columnId\":69,\"columnName\":\"school_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"schoolId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 15:39:10\",\"usableColumn\":false},{\"capJavaField\":\"SchoolName\",\"columnComment\":\"学校名称\",\"columnId\":70,\"columnName\":\"school_name\",\"columnType\":\"varchar(50)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"schoolName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 15:39:10\",\"usableColumn\":false},{\"capJavaField\":\"CreateBy\",\"columnComment\":\"创建者\",\"columnId\":71,\"columnName\":\"create_by\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaField\":\"createBy\",\"javaType\":\"String\",\"list\":false,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":true,\"tableId\":7,\"updateBy\":\"\",\"updateTime\":\"2022-08-09 15:39:10\",\"usableColumn\":false},{\"capJavaField\":\"CreateTime\",\"columnComment\":\"创建时间\",\"columnId\":72,\"columnName\":\"create_time\",\"columnType\":\"datetime\",\"createBy\":\"admin\",\"createTime\":\"2022-08-09 14:56:54\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"datetime\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"0\",\"javaFi', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 15:41:23');
INSERT INTO `sys_oper_log` VALUES ('160', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-09 15:41:30');
INSERT INTO `sys_oper_log` VALUES ('161', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-09 15:42:14');
INSERT INTO `sys_oper_log` VALUES ('162', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-09 15:42:33');
INSERT INTO `sys_oper_log` VALUES ('163', '学校信息', '1', 'com.ruoyi.web.controller.timetable.TimetableSchoolController.add()', 'POST', '1', 'admin', null, '/timetable/school', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-09T17:56:49.287+08:00\",\"ibTimetableGradeList\":[],\"params\":{},\"schoolName\":\"宿迁学院\"}', null, '1', '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'1\' for key \'ib_timetable_school.PRIMARY\'\r\n### The error may exist in file [C:\\Users\\Dell\\Desktop\\ruoyi-vue02\\ruoyi-ibaiqi-timetable\\target\\classes\\mapper\\timetable\\IbTimetableSchoolMapper.xml]\r\n### The error may involve com.ruoyi.ibaiqi.timetable.mapper.IbTimetableSchoolMapper.insertIbTimetableSchool-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into ib_timetable_school          ( school_name,                          create_time )           values ( ?,                          ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'1\' for key \'ib_timetable_school.PRIMARY\'\n; Duplicate entry \'1\' for key \'ib_timetable_school.PRIMARY\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'1\' for key \'ib_timetable_school.PRIMARY\'', '2022-08-09 17:56:49');
INSERT INTO `sys_oper_log` VALUES ('164', '学校信息', '1', 'com.ruoyi.web.controller.timetable.TimetableSchoolController.add()', 'POST', '1', 'admin', null, '/timetable/school', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-09T17:57:57.111+08:00\",\"ibTimetableGradeList\":[],\"params\":{},\"schoolName\":\"宿迁学院\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 17:57:57');
INSERT INTO `sys_oper_log` VALUES ('165', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"tool/gen/index\",\"createTime\":\"2022-07-30 22:58:43\",\"icon\":\"link\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":116,\"menuName\":\"代码生成\",\"menuType\":\"C\",\"orderNum\":2,\"params\":{},\"parentId\":3,\"path\":\"gen\",\"perms\":\"tool:gen:list\",\"query\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 17:59:41');
INSERT INTO `sys_oper_log` VALUES ('166', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"timetable/school/index\",\"createTime\":\"2022-08-09 17:29:34\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2012,\"menuName\":\"学校信息\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":5,\"path\":\"school\",\"perms\":\"timetable:school:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 18:00:02');
INSERT INTO `sys_oper_log` VALUES ('167', '学校信息', '1', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableSchoolController.add()', 'POST', '1', 'admin', null, '/timetable/school', '127.0.0.1', '内网IP', '{\"createTime\":\"2022-08-09T18:29:01.697+08:00\",\"ibTimetableGradeList\":[],\"params\":{},\"schoolName\":\"宿迁师范学校\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-09 18:29:01');
INSERT INTO `sys_oper_log` VALUES ('168', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-10 08:13:22');
INSERT INTO `sys_oper_log` VALUES ('169', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-10 08:17:36');
INSERT INTO `sys_oper_log` VALUES ('170', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"timetable/grade/index\",\"createTime\":\"2022-08-10 08:59:45\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2018,\"menuName\":\"课程班级管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":5,\"path\":\"grade\",\"perms\":\"timetable:grade:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-10 09:09:24');
INSERT INTO `sys_oper_log` VALUES ('171', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"timetable/grade/index\",\"createTime\":\"2022-08-10 08:59:45\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2018,\"menuName\":\"班级管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":5,\"path\":\"grade\",\"perms\":\"timetable:grade:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-10 09:10:04');
INSERT INTO `sys_oper_log` VALUES ('172', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"timetable/course/index\",\"createTime\":\"2022-08-10 09:10:44\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2024,\"menuName\":\"课程管理\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":5,\"path\":\"course\",\"perms\":\"timetable:course:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-10 09:11:21');
INSERT INTO `sys_oper_log` VALUES ('173', '课程管理', '1', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.add()', 'POST', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":15,\"courseTitle\":\"英语\",\"createTime\":\"2022-08-11T10:48:25.086+08:00\",\"gradeId\":1,\"params\":{},\"schoolroom\":\"弘德楼B201\",\"sectionBegin\":1,\"sectionEnd\":3,\"teacher\":\"李娟\",\"weekBegin\":1,\"weekEnd\":18,\"whatDay\":6}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-11 10:48:23');
INSERT INTO `sys_oper_log` VALUES ('174', '课程管理', '2', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.edit()', 'PUT', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":15,\"courseTitle\":\"英语\",\"createBy\":\"\",\"createTime\":\"2022-08-11 10:48:25\",\"gradeId\":1,\"params\":{},\"schoolroom\":\"弘德楼B201\",\"sectionBegin\":6,\"sectionEnd\":7,\"teacher\":\"李娟\",\"updateBy\":\"\",\"updateTime\":\"2022-08-11T10:49:16.218+08:00\",\"weekBegin\":1,\"weekEnd\":18,\"whatDay\":6}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-11 10:49:14');
INSERT INTO `sys_oper_log` VALUES ('175', '课程管理', '1', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.add()', 'POST', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":16,\"courseTitle\":\"语文\",\"createTime\":\"2022-08-11T10:51:24.196+08:00\",\"gradeId\":2,\"params\":{},\"schoolroom\":\"多媒体教室216\",\"sectionBegin\":8,\"sectionEnd\":9,\"teacher\":\"张秀\",\"weekBegin\":1,\"weekEnd\":18,\"whatDay\":7}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-11 10:51:22');
INSERT INTO `sys_oper_log` VALUES ('176', '课程管理', '2', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.edit()', 'PUT', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":15,\"courseTitle\":\"英语\",\"createBy\":\"\",\"createTime\":\"2022-08-11 10:48:25\",\"gradeId\":1,\"params\":{},\"schoolroom\":\"弘德楼B201\",\"sectionBegin\":3,\"sectionEnd\":7,\"teacher\":\"李娟\",\"updateBy\":\"\",\"updateTime\":\"2022-08-11T10:52:07.961+08:00\",\"weekBegin\":1,\"weekEnd\":18,\"whatDay\":6}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-11 10:52:06');
INSERT INTO `sys_oper_log` VALUES ('177', '课程管理', '1', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.add()', 'POST', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":17,\"courseTitle\":\"A灯\",\"createTime\":\"2022-08-14T17:08:43.256+08:00\",\"gradeId\":22,\"params\":{},\"remark\":\"22\",\"schoolroom\":\"22\",\"sectionBegin\":1,\"sectionEnd\":2,\"teacher\":\"22\",\"weekBegin\":1,\"weekEnd\":18,\"whatDay\":7}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-14 17:08:43');
INSERT INTO `sys_oper_log` VALUES ('178', '课程管理', '1', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.add()', 'POST', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":18,\"courseNotes\":\"11\",\"courseTitle\":\"11\",\"createTime\":\"2022-08-14T17:11:54.545+08:00\",\"gradeId\":3,\"params\":{},\"remark\":\"3\",\"schoolroom\":\"3\",\"sectionBegin\":8,\"sectionEnd\":9,\"teacher\":\"3\",\"weekBegin\":1,\"weekEnd\":17,\"whatDay\":6}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-14 17:11:54');
INSERT INTO `sys_oper_log` VALUES ('179', '课程管理', '2', 'com.ruoyi.ibaiqi.timetable.controller.IbTimetableCourseController.edit()', 'PUT', '1', 'admin', null, '/timetable/course', '127.0.0.1', '内网IP', '{\"courseId\":1,\"courseNotes\":\"\",\"courseTitle\":\"C#程序设计22\",\"createBy\":\"\",\"createTime\":\"2022-07-25 14:35:49\",\"gradeId\":1,\"params\":{},\"remark\":\"\",\"schoolroom\":\"崇德楼D201\",\"sectionBegin\":1,\"sectionEnd\":2,\"teacher\":\"胡居臣\",\"updateBy\":\"\",\"updateTime\":\"2022-08-14T17:14:03.111+08:00\",\"weekBegin\":1,\"weekEnd\":18,\"whatDay\":1}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-14 17:14:03');
INSERT INTO `sys_oper_log` VALUES ('180', '代码生成', '6', 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', '1', 'admin', null, '/tool/gen/importTable', '127.0.0.1', '内网IP', 'ib_wx_user', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:15:24');
INSERT INTO `sys_oper_log` VALUES ('181', '代码生成', '6', 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', '1', 'admin', null, '/tool/gen/importTable', '127.0.0.1', '内网IP', 'ib_wx_appconfig', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:15:37');
INSERT INTO `sys_oper_log` VALUES ('182', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-17 11:16:09');
INSERT INTO `sys_oper_log` VALUES ('183', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"wxUser\",\"className\":\"IbWxUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":76,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Nickname\",\"columnComment\":\"微信名称\",\"columnId\":77,\"columnName\":\"nickname\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"nickname\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Avatar\",\"columnComment\":\"头像\",\"columnId\":78,\"columnName\":\"avatar\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"avatar\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"微信唯一标识符\",\"columnId\":79,\"columnName\":\"openid\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"openid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:17:26');
INSERT INTO `sys_oper_log` VALUES ('184', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"wxUser\",\"className\":\"IbWxUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":76,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:17:26\",\"usableColumn\":false},{\"capJavaField\":\"Nickname\",\"columnComment\":\"微信名称\",\"columnId\":77,\"columnName\":\"nickname\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"nickname\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:17:26\",\"usableColumn\":false},{\"capJavaField\":\"Avatar\",\"columnComment\":\"头像\",\"columnId\":78,\"columnName\":\"avatar\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"avatar\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:17:26\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"微信唯一标识符\",\"columnId\":79,\"columnName\":\"openid\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:17:49');
INSERT INTO `sys_oper_log` VALUES ('185', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"appconfig\",\"className\":\"IbWxAppconfig\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":86,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Appid\",\"columnComment\":\"小程序ID\",\"columnId\":87,\"columnName\":\"appid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AppSecret\",\"columnComment\":\"小程序密钥\",\"columnId\":88,\"columnName\":\"app_secret\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appSecret\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"State\",\"columnComment\":\"状态\",\"columnId\":89,\"columnName\":\"state\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"state\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryTyp', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:18:41');
INSERT INTO `sys_oper_log` VALUES ('186', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"appconfig\",\"className\":\"IbWxAppconfig\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":86,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:18:41\",\"usableColumn\":false},{\"capJavaField\":\"Appid\",\"columnComment\":\"小程序ID\",\"columnId\":87,\"columnName\":\"appid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:18:41\",\"usableColumn\":false},{\"capJavaField\":\"AppSecret\",\"columnComment\":\"小程序密钥\",\"columnId\":88,\"columnName\":\"app_secret\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appSecret\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:18:41\",\"usableColumn\":false},{\"capJavaField\":\"State\",\"columnComment\":\"状态\",\"columnId\":89,\"columnName\":\"state\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQ', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:19:02');
INSERT INTO `sys_oper_log` VALUES ('187', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"wxUser\",\"className\":\"IbWxUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":76,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:17:49\",\"usableColumn\":false},{\"capJavaField\":\"Nickname\",\"columnComment\":\"微信名称\",\"columnId\":77,\"columnName\":\"nickname\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"nickname\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:17:49\",\"usableColumn\":false},{\"capJavaField\":\"Avatar\",\"columnComment\":\"头像\",\"columnId\":78,\"columnName\":\"avatar\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"avatar\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:17:49\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"微信唯一标识符\",\"columnId\":79,\"columnName\":\"openid\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:19:44');
INSERT INTO `sys_oper_log` VALUES ('188', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"appconfig\",\"className\":\"IbWxAppconfig\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":86,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:19:02\",\"usableColumn\":false},{\"capJavaField\":\"Appid\",\"columnComment\":\"小程序ID\",\"columnId\":87,\"columnName\":\"appid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:19:02\",\"usableColumn\":false},{\"capJavaField\":\"AppSecret\",\"columnComment\":\"小程序密钥\",\"columnId\":88,\"columnName\":\"app_secret\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appSecret\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:19:02\",\"usableColumn\":false},{\"capJavaField\":\"State\",\"columnComment\":\"状态\",\"columnId\":89,\"columnName\":\"state\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQ', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:20:23');
INSERT INTO `sys_oper_log` VALUES ('189', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"appConfig\",\"className\":\"IbWxAppconfig\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":86,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:20:22\",\"usableColumn\":false},{\"capJavaField\":\"Appid\",\"columnComment\":\"小程序ID\",\"columnId\":87,\"columnName\":\"appid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:20:22\",\"usableColumn\":false},{\"capJavaField\":\"AppSecret\",\"columnComment\":\"小程序密钥\",\"columnId\":88,\"columnName\":\"app_secret\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appSecret\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:20:22\",\"usableColumn\":false},{\"capJavaField\":\"State\",\"columnComment\":\"状态\",\"columnId\":89,\"columnName\":\"state\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQ', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:21:18');
INSERT INTO `sys_oper_log` VALUES ('190', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"wxUser\",\"className\":\"IbWxUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":76,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:19:44\",\"usableColumn\":false},{\"capJavaField\":\"Nickname\",\"columnComment\":\"微信名称\",\"columnId\":77,\"columnName\":\"nickname\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"nickname\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:19:44\",\"usableColumn\":false},{\"capJavaField\":\"Avatar\",\"columnComment\":\"头像\",\"columnId\":78,\"columnName\":\"avatar\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"avatar\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:19:44\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"微信唯一标识符\",\"columnId\":79,\"columnName\":\"openid\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:21:33');
INSERT INTO `sys_oper_log` VALUES ('191', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-17 11:22:07');
INSERT INTO `sys_oper_log` VALUES ('192', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"wxUser\",\"className\":\"IbWxUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":76,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:21:33\",\"usableColumn\":false},{\"capJavaField\":\"Nickname\",\"columnComment\":\"微信名称\",\"columnId\":77,\"columnName\":\"nickname\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"nickname\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:21:33\",\"usableColumn\":false},{\"capJavaField\":\"Avatar\",\"columnComment\":\"头像\",\"columnId\":78,\"columnName\":\"avatar\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"avatar\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:21:33\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"微信唯一标识符\",\"columnId\":79,\"columnName\":\"openid\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:29:05');
INSERT INTO `sys_oper_log` VALUES ('193', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"appConfig\",\"className\":\"IbWxAppconfig\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":86,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:21:17\",\"usableColumn\":false},{\"capJavaField\":\"Appid\",\"columnComment\":\"小程序ID\",\"columnId\":87,\"columnName\":\"appid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:21:17\",\"usableColumn\":false},{\"capJavaField\":\"AppSecret\",\"columnComment\":\"小程序密钥\",\"columnId\":88,\"columnName\":\"app_secret\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appSecret\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:21:17\",\"usableColumn\":false},{\"capJavaField\":\"State\",\"columnComment\":\"状态\",\"columnId\":89,\"columnName\":\"state\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:37\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQ', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 11:29:18');
INSERT INTO `sys_oper_log` VALUES ('194', '代码生成', '8', 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', '1', 'admin', null, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{}', null, '0', null, '2022-08-17 11:30:08');
INSERT INTO `sys_oper_log` VALUES ('195', '代码生成', '2', 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', '1', 'admin', null, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"wxUser\",\"className\":\"IbWxUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"ID\",\"columnId\":76,\"columnName\":\"id\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:29:05\",\"usableColumn\":false},{\"capJavaField\":\"Nickname\",\"columnComment\":\"微信名称\",\"columnId\":77,\"columnName\":\"nickname\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"nickname\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:29:05\",\"usableColumn\":false},{\"capJavaField\":\"Avatar\",\"columnComment\":\"头像\",\"columnId\":78,\"columnName\":\"avatar\",\"columnType\":\"varchar(255)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"avatar\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2022-08-17 11:29:05\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"微信唯一标识符\",\"columnId\":79,\"columnName\":\"openid\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2022-08-17 11:15:23\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 15:52:26');
INSERT INTO `sys_oper_log` VALUES ('196', '代码生成', '3', 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', '1', 'admin', null, '/tool/gen/8', '127.0.0.1', '内网IP', '{tableIds=8}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 15:53:52');
INSERT INTO `sys_oper_log` VALUES ('197', '代码生成', '6', 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', '1', 'admin', null, '/tool/gen/importTable', '127.0.0.1', '内网IP', 'ib_wx_user', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 15:53:58');
INSERT INTO `sys_oper_log` VALUES ('198', '微信用户', '3', 'com.ruoyi.ibaiqi.wxUser.controller.IbWxUserController.remove()', 'DELETE', '1', 'admin', null, '/wxUser/wxUser/11', '127.0.0.1', '内网IP', '{ids=11}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 15:56:24');
INSERT INTO `sys_oper_log` VALUES ('199', '用户管理', '1', 'com.ruoyi.web.controller.system.SysUserController.add()', 'POST', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"11111\",\"params\":{},\"password\":\"$2a$10$cl9CVPIRwKCXbsav/dyKEOAZiv8wmyOSK45gI78xdaF4rI8IeQYDW\",\"postIds\":[],\"roleIds\":[],\"status\":\"0\",\"userId\":105,\"userName\":\"22222\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-17 22:21:16');
INSERT INTO `sys_oper_log` VALUES ('200', '参数管理', '2', 'com.ruoyi.web.controller.system.SysConfigController.edit()', 'PUT', '1', 'admin', null, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":5,\"configKey\":\"sys.account.registerUser\",\"configName\":\"账号自助-是否开启用户注册功能\",\"configType\":\"Y\",\"configValue\":\"true\",\"createBy\":\"admin\",\"createTime\":\"2022-07-30 22:58:44\",\"params\":{},\"remark\":\"是否开启注册用户功能（true开启，false关闭）\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-18 23:53:21');
INSERT INTO `sys_oper_log` VALUES ('201', '微信用户', '3', 'com.ruoyi.ibaiqi.wxUser.controller.IbWxUserController.remove()', 'DELETE', '1', 'admin', null, '/wxUser/wxUser/9', '127.0.0.1', '内网IP', '{ids=9}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 10:08:58');
INSERT INTO `sys_oper_log` VALUES ('202', '用户管理', '2', 'com.ruoyi.web.controller.system.SysUserController.edit()', 'PUT', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"\",\"createTime\":\"2022-08-19 10:28:28\",\"delFlag\":\"0\",\"email\":\"\",\"loginDate\":\"2022-08-19 11:14:57\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"nickName22\",\"params\":{},\"password\":\"\",\"phonenumber\":\"\",\"postIds\":[],\"roleIds\":[100],\"roles\":[],\"sex\":\"0\",\"status\":\"0\",\"updateBy\":\"admin\",\"userId\":120,\"userName\":\"weixin123\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 11:17:34');
INSERT INTO `sys_oper_log` VALUES ('203', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"wxUser/wxUser/index\",\"createTime\":\"2022-08-17 11:34:25\",\"icon\":\"wechat\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2030,\"menuName\":\"微信用户\",\"menuType\":\"C\",\"orderNum\":6,\"params\":{},\"parentId\":0,\"path\":\"wxUser\",\"perms\":\"wxUser:wxUser:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 11:24:01');
INSERT INTO `sys_oper_log` VALUES ('204', '菜单管理', '2', 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', '1', 'admin', null, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"wxUser/appConfig/index\",\"createTime\":\"2022-08-17 11:34:42\",\"icon\":\"wechat\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2036,\"menuName\":\"微信小程序配置\",\"menuType\":\"C\",\"orderNum\":7,\"params\":{},\"parentId\":0,\"path\":\"appConfig\",\"perms\":\"wxUser:appConfig:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 11:25:00');
INSERT INTO `sys_oper_log` VALUES ('205', '角色管理', '2', 'com.ruoyi.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2022-08-04 22:27:05\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[3,117,5,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039,2040,2041],\"params\":{},\"roleId\":100,\"roleKey\":\"teacherStudentAdmin\",\"roleName\":\"师生易趣管理员\",\"roleSort\":\"3\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 11:25:28');
INSERT INTO `sys_oper_log` VALUES ('206', '角色管理', '2', 'com.ruoyi.web.controller.system.SysRoleController.edit()', 'PUT', '1', 'admin', null, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2022-08-04 22:27:05\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[3,117,5,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039,2040,2041],\"params\":{},\"roleId\":100,\"roleKey\":\"teacherStudentAdmin\",\"roleName\":\"师生易趣管理员\",\"roleSort\":\"3\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 11:26:21');
INSERT INTO `sys_oper_log` VALUES ('207', '用户管理', '2', 'com.ruoyi.web.controller.system.SysUserController.resetPwd()', 'PUT', '1', 'admin', null, '/system/user/resetPwd', '127.0.0.1', '内网IP', '{\"admin\":false,\"params\":{},\"password\":\"$2a$10$zUw/Mc8t2YHWle21KUghaOx1JGvDjHCWbUwXYGidI0qPLRiYLhSLe\",\"updateBy\":\"admin\",\"userId\":101}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-19 21:54:28');
INSERT INTO `sys_oper_log` VALUES ('208', '用户管理', '2', 'com.ruoyi.web.controller.system.SysUserController.edit()', 'PUT', '1', 'admin', null, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"\",\"createTime\":\"2022-08-21 22:27:53\",\"delFlag\":\"0\",\"email\":\"\",\"loginDate\":\"2022-08-21 23:02:07\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"nickName22\",\"params\":{},\"password\":\"\",\"phonenumber\":\"\",\"postIds\":[],\"roleIds\":[100],\"roles\":[],\"sex\":\"0\",\"status\":\"0\",\"updateBy\":\"admin\",\"userId\":137,\"userName\":\"码哥@码农41d31f4\"}', '{\"msg\":\"操作成功\",\"code\":200}', '0', null, '2022-08-21 23:03:40');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES ('1', 'ceo', '董事长', '1', '0', 'admin', '2022-07-30 22:58:42', '', null, '');
INSERT INTO `sys_post` VALUES ('2', 'se', '项目经理', '2', '0', 'admin', '2022-07-30 22:58:42', '', null, '');
INSERT INTO `sys_post` VALUES ('3', 'hr', '人力资源', '3', '0', 'admin', '2022-07-30 22:58:42', '', null, '');
INSERT INTO `sys_post` VALUES ('4', 'user', '普通员工', '4', '0', 'admin', '2022-07-30 22:58:42', '', null, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb3 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '1', '1', '1', '1', '0', '0', 'admin', '2022-07-30 22:58:42', '', null, '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '普通角色', 'common', '2', '2', '1', '1', '0', '0', 'admin', '2022-07-30 22:58:42', '', null, '普通角色');
INSERT INTO `sys_role` VALUES ('100', '师生易趣管理员', 'teacherStudentAdmin', '3', '1', '1', '1', '0', '0', 'admin', '2022-08-04 22:27:05', 'admin', '2022-08-19 11:26:21', null);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES ('2', '100');
INSERT INTO `sys_role_dept` VALUES ('2', '101');
INSERT INTO `sys_role_dept` VALUES ('2', '105');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '4');
INSERT INTO `sys_role_menu` VALUES ('2', '100');
INSERT INTO `sys_role_menu` VALUES ('2', '101');
INSERT INTO `sys_role_menu` VALUES ('2', '102');
INSERT INTO `sys_role_menu` VALUES ('2', '103');
INSERT INTO `sys_role_menu` VALUES ('2', '104');
INSERT INTO `sys_role_menu` VALUES ('2', '105');
INSERT INTO `sys_role_menu` VALUES ('2', '106');
INSERT INTO `sys_role_menu` VALUES ('2', '107');
INSERT INTO `sys_role_menu` VALUES ('2', '108');
INSERT INTO `sys_role_menu` VALUES ('2', '109');
INSERT INTO `sys_role_menu` VALUES ('2', '110');
INSERT INTO `sys_role_menu` VALUES ('2', '111');
INSERT INTO `sys_role_menu` VALUES ('2', '112');
INSERT INTO `sys_role_menu` VALUES ('2', '113');
INSERT INTO `sys_role_menu` VALUES ('2', '114');
INSERT INTO `sys_role_menu` VALUES ('2', '115');
INSERT INTO `sys_role_menu` VALUES ('2', '116');
INSERT INTO `sys_role_menu` VALUES ('2', '117');
INSERT INTO `sys_role_menu` VALUES ('2', '500');
INSERT INTO `sys_role_menu` VALUES ('2', '501');
INSERT INTO `sys_role_menu` VALUES ('2', '1000');
INSERT INTO `sys_role_menu` VALUES ('2', '1001');
INSERT INTO `sys_role_menu` VALUES ('2', '1002');
INSERT INTO `sys_role_menu` VALUES ('2', '1003');
INSERT INTO `sys_role_menu` VALUES ('2', '1004');
INSERT INTO `sys_role_menu` VALUES ('2', '1005');
INSERT INTO `sys_role_menu` VALUES ('2', '1006');
INSERT INTO `sys_role_menu` VALUES ('2', '1007');
INSERT INTO `sys_role_menu` VALUES ('2', '1008');
INSERT INTO `sys_role_menu` VALUES ('2', '1009');
INSERT INTO `sys_role_menu` VALUES ('2', '1010');
INSERT INTO `sys_role_menu` VALUES ('2', '1011');
INSERT INTO `sys_role_menu` VALUES ('2', '1012');
INSERT INTO `sys_role_menu` VALUES ('2', '1013');
INSERT INTO `sys_role_menu` VALUES ('2', '1014');
INSERT INTO `sys_role_menu` VALUES ('2', '1015');
INSERT INTO `sys_role_menu` VALUES ('2', '1016');
INSERT INTO `sys_role_menu` VALUES ('2', '1017');
INSERT INTO `sys_role_menu` VALUES ('2', '1018');
INSERT INTO `sys_role_menu` VALUES ('2', '1019');
INSERT INTO `sys_role_menu` VALUES ('2', '1020');
INSERT INTO `sys_role_menu` VALUES ('2', '1021');
INSERT INTO `sys_role_menu` VALUES ('2', '1022');
INSERT INTO `sys_role_menu` VALUES ('2', '1023');
INSERT INTO `sys_role_menu` VALUES ('2', '1024');
INSERT INTO `sys_role_menu` VALUES ('2', '1025');
INSERT INTO `sys_role_menu` VALUES ('2', '1026');
INSERT INTO `sys_role_menu` VALUES ('2', '1027');
INSERT INTO `sys_role_menu` VALUES ('2', '1028');
INSERT INTO `sys_role_menu` VALUES ('2', '1029');
INSERT INTO `sys_role_menu` VALUES ('2', '1030');
INSERT INTO `sys_role_menu` VALUES ('2', '1031');
INSERT INTO `sys_role_menu` VALUES ('2', '1032');
INSERT INTO `sys_role_menu` VALUES ('2', '1033');
INSERT INTO `sys_role_menu` VALUES ('2', '1034');
INSERT INTO `sys_role_menu` VALUES ('2', '1035');
INSERT INTO `sys_role_menu` VALUES ('2', '1036');
INSERT INTO `sys_role_menu` VALUES ('2', '1037');
INSERT INTO `sys_role_menu` VALUES ('2', '1038');
INSERT INTO `sys_role_menu` VALUES ('2', '1039');
INSERT INTO `sys_role_menu` VALUES ('2', '1040');
INSERT INTO `sys_role_menu` VALUES ('2', '1041');
INSERT INTO `sys_role_menu` VALUES ('2', '1042');
INSERT INTO `sys_role_menu` VALUES ('2', '1043');
INSERT INTO `sys_role_menu` VALUES ('2', '1044');
INSERT INTO `sys_role_menu` VALUES ('2', '1045');
INSERT INTO `sys_role_menu` VALUES ('2', '1046');
INSERT INTO `sys_role_menu` VALUES ('2', '1047');
INSERT INTO `sys_role_menu` VALUES ('2', '1048');
INSERT INTO `sys_role_menu` VALUES ('2', '1049');
INSERT INTO `sys_role_menu` VALUES ('2', '1050');
INSERT INTO `sys_role_menu` VALUES ('2', '1051');
INSERT INTO `sys_role_menu` VALUES ('2', '1052');
INSERT INTO `sys_role_menu` VALUES ('2', '1053');
INSERT INTO `sys_role_menu` VALUES ('2', '1054');
INSERT INTO `sys_role_menu` VALUES ('2', '1055');
INSERT INTO `sys_role_menu` VALUES ('2', '1056');
INSERT INTO `sys_role_menu` VALUES ('2', '1057');
INSERT INTO `sys_role_menu` VALUES ('2', '1058');
INSERT INTO `sys_role_menu` VALUES ('2', '1059');
INSERT INTO `sys_role_menu` VALUES ('100', '3');
INSERT INTO `sys_role_menu` VALUES ('100', '5');
INSERT INTO `sys_role_menu` VALUES ('100', '117');
INSERT INTO `sys_role_menu` VALUES ('100', '2012');
INSERT INTO `sys_role_menu` VALUES ('100', '2013');
INSERT INTO `sys_role_menu` VALUES ('100', '2014');
INSERT INTO `sys_role_menu` VALUES ('100', '2015');
INSERT INTO `sys_role_menu` VALUES ('100', '2016');
INSERT INTO `sys_role_menu` VALUES ('100', '2017');
INSERT INTO `sys_role_menu` VALUES ('100', '2018');
INSERT INTO `sys_role_menu` VALUES ('100', '2019');
INSERT INTO `sys_role_menu` VALUES ('100', '2020');
INSERT INTO `sys_role_menu` VALUES ('100', '2021');
INSERT INTO `sys_role_menu` VALUES ('100', '2022');
INSERT INTO `sys_role_menu` VALUES ('100', '2023');
INSERT INTO `sys_role_menu` VALUES ('100', '2024');
INSERT INTO `sys_role_menu` VALUES ('100', '2025');
INSERT INTO `sys_role_menu` VALUES ('100', '2026');
INSERT INTO `sys_role_menu` VALUES ('100', '2027');
INSERT INTO `sys_role_menu` VALUES ('100', '2028');
INSERT INTO `sys_role_menu` VALUES ('100', '2029');
INSERT INTO `sys_role_menu` VALUES ('100', '2030');
INSERT INTO `sys_role_menu` VALUES ('100', '2031');
INSERT INTO `sys_role_menu` VALUES ('100', '2032');
INSERT INTO `sys_role_menu` VALUES ('100', '2033');
INSERT INTO `sys_role_menu` VALUES ('100', '2034');
INSERT INTO `sys_role_menu` VALUES ('100', '2035');
INSERT INTO `sys_role_menu` VALUES ('100', '2036');
INSERT INTO `sys_role_menu` VALUES ('100', '2037');
INSERT INTO `sys_role_menu` VALUES ('100', '2038');
INSERT INTO `sys_role_menu` VALUES ('100', '2039');
INSERT INTO `sys_role_menu` VALUES ('100', '2040');
INSERT INTO `sys_role_menu` VALUES ('100', '2041');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb3 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '103', 'admin', '张许admin22', '00', 'ry@163.com', '15888888888', '1', '/profile/avatar/2022/08/06/1659780756584_20220806181236A001.png', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2022-08-22 11:45:10', 'admin', '2022-07-30 22:58:42', '', '2022-08-22 11:45:07', '管理员');
INSERT INTO `sys_user` VALUES ('2', '105', 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2022-07-30 22:58:42', 'admin', '2022-07-30 22:58:42', '', null, '测试员');
INSERT INTO `sys_user` VALUES ('101', null, 'admin3', '师生易趣管理', '00', 'ibaiqi@163.com', '15151163228', '0', '', '$2a$10$zUw/Mc8t2YHWle21KUghaOx1JGvDjHCWbUwXYGidI0qPLRiYLhSLe', '0', '0', '127.0.0.1', '2022-08-19 21:54:58', 'admin', '2022-08-04 22:50:10', 'admin', '2022-08-19 21:54:57', '师生易趣管理系统管理员与开发人员');
INSERT INTO `sys_user` VALUES ('102', null, 'test3', 'test3', '00', '', '', '0', '', '$2a$10$YeYL4O.ToRHNpLYJCZ66G.ge3licIPph62kwzydxCtEC5yd9GfYwe', '0', '0', '', null, 'admin', '2022-08-09 09:29:24', '', null, null);
INSERT INTO `sys_user` VALUES ('146', null, '码哥@码农e601485', '码哥@码农', '00', '', '', '0', '', '$2a$10$GmrHQyaRGi0gcZdGG68izuzScoCQ72gNpAXIfmiJjgA/xLBmnTFGe', '0', '0', '127.0.0.1', '2022-08-22 11:50:48', '', '2022-08-22 11:00:39', '', '2022-08-22 11:50:46', null);
INSERT INTO `sys_user` VALUES ('147', null, '码哥@码农b03f9bf', '码哥@码农', '00', '', '', '0', '', '$2a$10$JP9axlbKTMXiQafaYm8WVuyAkT26H0Le2auRUlESCR67mS9yoLMP6', '0', '0', '127.0.0.1', '2022-08-22 21:25:30', '', '2022-08-22 11:51:12', '', '2022-08-22 21:25:27', null);
INSERT INTO `sys_user` VALUES ('148', null, '码哥@码农58a1e64', '码哥@码农', '00', '', '', '0', '', '$2a$10$TDXsqKMN7P.oo5d6g35e/.eZJjElk7rTy6i.xgwCixovzcbn8yMR2', '0', '0', '127.0.0.1', '2022-08-22 23:02:36', '', '2022-08-22 23:02:04', '', '2022-08-22 23:02:32', null);
INSERT INTO `sys_user` VALUES ('149', null, '码哥@码农4076289', '码哥@码农', '00', '', '', '0', '', '$2a$10$qG8YmuF1ellFcYsWyXMrh./8LQSZ.oy9UI2en.GE0LIKfeT.GZsKW', '0', '0', '127.0.0.1', '2022-08-22 23:44:01', '', '2022-08-22 23:05:55', '', '2022-08-22 23:43:57', null);
INSERT INTO `sys_user` VALUES ('150', null, '码哥@码农179ef67', '码哥@码农', '00', '', '', '0', '', '$2a$10$uPWsdPD/ECRUHdB0fw9syeambtMG6rz77KseugYylYsQEROSPBK2W', '0', '0', '127.0.0.1', '2022-08-22 23:56:36', '', '2022-08-22 23:56:10', '', '2022-08-22 23:56:33', null);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES ('1', '1');
INSERT INTO `sys_user_post` VALUES ('2', '2');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('101', '100');
INSERT INTO `sys_user_role` VALUES ('120', '100');
INSERT INTO `sys_user_role` VALUES ('137', '100');
