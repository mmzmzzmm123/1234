/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : ry-vue

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2022-08-07 16:16:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for work_concentrate
-- ----------------------------
DROP TABLE IF EXISTS `work_concentrate`;
CREATE TABLE `work_concentrate` (
  `concentrate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_code` varchar(64) DEFAULT NULL,
  `standing` varchar(64) DEFAULT NULL,
  `arg1` varchar(64) DEFAULT NULL,
  `arg2` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`concentrate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_concentrate
-- ----------------------------
INSERT INTO `work_concentrate` VALUES ('1', '400c8a8a-4e3f-4daa-903a-10081278a6de', '100', null, null);
INSERT INTO `work_concentrate` VALUES ('2', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '100', null, null);

-- ----------------------------
-- Table structure for work_concentrate_record
-- ----------------------------
DROP TABLE IF EXISTS `work_concentrate_record`;
CREATE TABLE `work_concentrate_record` (
  `concentrate_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `standing` varchar(64) DEFAULT NULL,
  `arg1` varchar(64) DEFAULT NULL,
  `arg2` varchar(64) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1过程设定，2结果',
  PRIMARY KEY (`concentrate_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_concentrate_record
-- ----------------------------
INSERT INTO `work_concentrate_record` VALUES ('6', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string1', 'string1', 'string1', '1');
INSERT INTO `work_concentrate_record` VALUES ('7', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', 'string', '1');

-- ----------------------------
-- Table structure for work_extraction
-- ----------------------------
DROP TABLE IF EXISTS `work_extraction`;
CREATE TABLE `work_extraction` (
  `extraction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_code` varchar(64) DEFAULT NULL,
  `standing` varchar(64) DEFAULT NULL,
  `arg1` varchar(64) DEFAULT NULL,
  `arg2` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`extraction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_extraction
-- ----------------------------
INSERT INTO `work_extraction` VALUES ('1', '400c8a8a-4e3f-4daa-903a-10081278a6de', '100', null, null);
INSERT INTO `work_extraction` VALUES ('2', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '100', null, null);

-- ----------------------------
-- Table structure for work_extraction_record
-- ----------------------------
DROP TABLE IF EXISTS `work_extraction_record`;
CREATE TABLE `work_extraction_record` (
  `extraction_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `standing` varchar(64) DEFAULT NULL,
  `arg1` varchar(64) DEFAULT NULL,
  `arg2` varchar(64) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1过程设定，2结果',
  PRIMARY KEY (`extraction_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_extraction_record
-- ----------------------------
INSERT INTO `work_extraction_record` VALUES ('5', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string1', 'string1', 'string1', '1');
INSERT INTO `work_extraction_record` VALUES ('6', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', 'string', '1');

-- ----------------------------
-- Table structure for work_feeding
-- ----------------------------
DROP TABLE IF EXISTS `work_feeding`;
CREATE TABLE `work_feeding` (
  `feeding_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_code` varchar(64) DEFAULT NULL,
  `mode` varchar(32) DEFAULT NULL,
  `interval` varchar(32) DEFAULT NULL,
  `material` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`feeding_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_feeding
-- ----------------------------
INSERT INTO `work_feeding` VALUES ('1', '400c8a8a-4e3f-4daa-903a-10081278a6de', '连续/手动', '10', '材料1:10；材料2:0.4');
INSERT INTO `work_feeding` VALUES ('2', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '连续/手动', '10', '材料1:10；材料2:0.4');

-- ----------------------------
-- Table structure for work_feeding_record
-- ----------------------------
DROP TABLE IF EXISTS `work_feeding_record`;
CREATE TABLE `work_feeding_record` (
  `feeding_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `mode` varchar(32) DEFAULT NULL,
  `interval` varchar(32) DEFAULT NULL,
  `material` varchar(2000) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1过程设定，2结果',
  PRIMARY KEY (`feeding_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_feeding_record
-- ----------------------------
INSERT INTO `work_feeding_record` VALUES ('5', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', 'string', '1');
INSERT INTO `work_feeding_record` VALUES ('6', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', 'string', '1');

-- ----------------------------
-- Table structure for work_gel
-- ----------------------------
DROP TABLE IF EXISTS `work_gel`;
CREATE TABLE `work_gel` (
  `gel_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_code` varchar(64) DEFAULT NULL,
  `standing` varchar(64) DEFAULT NULL,
  `material` varchar(2000) DEFAULT NULL,
  `arg1` varchar(64) DEFAULT NULL,
  `arg2` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`gel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_gel
-- ----------------------------
INSERT INTO `work_gel` VALUES ('1', '400c8a8a-4e3f-4daa-903a-10081278a6de', '10', '材料1:10；材料2:0.4', null, null);
INSERT INTO `work_gel` VALUES ('2', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '10', '材料1:10；材料2:0.4', null, null);

-- ----------------------------
-- Table structure for work_gel_record
-- ----------------------------
DROP TABLE IF EXISTS `work_gel_record`;
CREATE TABLE `work_gel_record` (
  `gel_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `standing` varchar(64) DEFAULT NULL,
  `material` varchar(2000) DEFAULT NULL,
  `arg1` varchar(64) DEFAULT NULL,
  `arg2` varchar(64) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1过程设定，2结果',
  PRIMARY KEY (`gel_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_gel_record
-- ----------------------------
INSERT INTO `work_gel_record` VALUES ('5', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', 'string', 'string', '1');
INSERT INTO `work_gel_record` VALUES ('6', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', 'string', 'string', '1');

-- ----------------------------
-- Table structure for work_heating
-- ----------------------------
DROP TABLE IF EXISTS `work_heating`;
CREATE TABLE `work_heating` (
  `heating_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_code` varchar(64) DEFAULT NULL,
  `mode` varchar(64) DEFAULT NULL,
  `interval` varchar(64) DEFAULT NULL,
  `temperature` int(11) DEFAULT NULL,
  `temperature_duration` int(11) DEFAULT NULL,
  `stir_duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`heating_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_heating
-- ----------------------------
INSERT INTO `work_heating` VALUES ('1', '400c8a8a-4e3f-4daa-903a-10081278a6de', '自动', '次数：2；间隔1:10；间隔2:20', '0', '0', '0');
INSERT INTO `work_heating` VALUES ('2', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '自动', '次数：2；间隔1:10；间隔2:20', '0', '0', '0');

-- ----------------------------
-- Table structure for work_heating_record
-- ----------------------------
DROP TABLE IF EXISTS `work_heating_record`;
CREATE TABLE `work_heating_record` (
  `heating_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `mode` varchar(64) DEFAULT NULL,
  `interval` varchar(64) DEFAULT NULL,
  `temperature` int(11) DEFAULT NULL,
  `temperature_duration` int(11) DEFAULT NULL,
  `stir_duration` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1过程设定，2结果',
  PRIMARY KEY (`heating_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_heating_record
-- ----------------------------
INSERT INTO `work_heating_record` VALUES ('5', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', '0', '0', '0', '1');
INSERT INTO `work_heating_record` VALUES ('6', '20220807144408', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'string', 'string', '0', '0', '0', '1');

-- ----------------------------
-- Table structure for work_process
-- ----------------------------
DROP TABLE IF EXISTS `work_process`;
CREATE TABLE `work_process` (
  `process_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_name` varchar(64) DEFAULT NULL,
  `process_code` varchar(64) DEFAULT NULL,
  `precondition` varchar(128) DEFAULT NULL,
  `param` varchar(128) DEFAULT NULL,
  `endif` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`process_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of work_process
-- ----------------------------
INSERT INTO `work_process` VALUES ('1', '22', '22', null, '22', null);

-- ----------------------------
-- Table structure for work_process_record
-- ----------------------------
DROP TABLE IF EXISTS `work_process_record`;
CREATE TABLE `work_process_record` (
  `process_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`process_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_process_record
-- ----------------------------

-- ----------------------------
-- Table structure for work_put_record
-- ----------------------------
DROP TABLE IF EXISTS `work_put_record`;
CREATE TABLE `work_put_record` (
  `put_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`put_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_put_record
-- ----------------------------

-- ----------------------------
-- Table structure for work_reaction
-- ----------------------------
DROP TABLE IF EXISTS `work_reaction`;
CREATE TABLE `work_reaction` (
  `reaction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_name` varchar(64) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`reaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_reaction
-- ----------------------------
INSERT INTO `work_reaction` VALUES ('3', '化学反应名称0806', '400c8a8a-4e3f-4daa-903a-10081278a6de', 'wjp', '2022-08-06 18:02:03', '化学反应备注11');
INSERT INTO `work_reaction` VALUES ('4', '化学反应名称', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', 'wjp', '2022-08-06 18:02:38', '化学反应备注');

-- ----------------------------
-- Table structure for work_reaction_batch
-- ----------------------------
DROP TABLE IF EXISTS `work_reaction_batch`;
CREATE TABLE `work_reaction_batch` (
  `batch_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_name` varchar(64) DEFAULT NULL,
  `batch_no` varchar(64) DEFAULT NULL,
  `reaction_sort` int(11) DEFAULT NULL,
  `reaction_code` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `process_name` varchar(64) DEFAULT NULL,
  `process_code` varchar(64) DEFAULT NULL,
  `precondition` varchar(128) DEFAULT NULL,
  `param` varchar(128) DEFAULT NULL,
  `endif` varchar(128) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_reaction_batch
-- ----------------------------
INSERT INTO `work_reaction_batch` VALUES ('67', '化学反应批次名称0807', '20220807144408', '1', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '1', '投料', '1', '前置条件1', '发送指令1', '结束条件1', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('68', '化学反应批次名称0807', '20220807144408', '1', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '搅拌加热', '2', '前置条件11', '发送指令11', '结束条件11', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('69', '化学反应批次名称0807', '20220807144408', '1', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '取样', '2-1', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('70', '化学反应批次名称0807', '20220807144408', '1', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '稀释', '2-2', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('71', '化学反应批次名称0807', '20220807144408', '1', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '失败退出', '2-3', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('72', '化学反应批次名称0807', '20220807144408', '1', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '完成移液', '2-4', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('73', '化学反应批次名称0807', '20220807144408', '2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '1', '投料', '1', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('74', '化学反应批次名称0807', '20220807144408', '2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '搅拌加热', '2', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('75', '化学反应批次名称0807', '20220807144408', '2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '取样', '2-1', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('76', '化学反应批次名称0807', '20220807144408', '2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '稀释', '2-2', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('77', '化学反应批次名称0807', '20220807144408', '2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '失败退出', '2-3', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');
INSERT INTO `work_reaction_batch` VALUES ('78', '化学反应批次名称0807', '20220807144408', '2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '完成移液', '2-4', '前置条件', '发送指令', '结束条件', '1', 'wjp', '2022-08-07 16:15:47');

-- ----------------------------
-- Table structure for work_reaction_detail
-- ----------------------------
DROP TABLE IF EXISTS `work_reaction_detail`;
CREATE TABLE `work_reaction_detail` (
  `reaction_detail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_code` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `process_name` varchar(64) DEFAULT NULL,
  `process_code` varchar(64) DEFAULT NULL,
  `precondition` varchar(128) DEFAULT NULL,
  `param` varchar(128) DEFAULT NULL,
  `endif` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`reaction_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_reaction_detail
-- ----------------------------
INSERT INTO `work_reaction_detail` VALUES ('1', '400c8a8a-4e3f-4daa-903a-10081278a6de', '1', '投料', '1', '前置条件1', '发送指令1', '结束条件1');
INSERT INTO `work_reaction_detail` VALUES ('2', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '搅拌加热', '2', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('3', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '取样', '2-1', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('4', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '稀释', '2-2', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('5', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '失败退出', '2-3', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('6', '400c8a8a-4e3f-4daa-903a-10081278a6de', '2', '完成移液', '2-4', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('7', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '1', '投料', '1', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('8', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '搅拌加热', '2', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('9', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '取样', '2-1', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('10', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '稀释', '2-2', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('11', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '失败退出', '2-3', '前置条件', '发送指令', '结束条件');
INSERT INTO `work_reaction_detail` VALUES ('12', '42a756df-7b16-4b1c-ad58-d6e30e97cc42', '2', '完成移液', '2-4', '前置条件', '发送指令', '结束条件');

-- ----------------------------
-- Table structure for work_read_result
-- ----------------------------
DROP TABLE IF EXISTS `work_read_result`;
CREATE TABLE `work_read_result` (
  `result_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `slave_id` int(11) DEFAULT NULL,
  `address` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  PRIMARY KEY (`result_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of work_read_result
-- ----------------------------
INSERT INTO `work_read_result` VALUES ('1', '1', '0', '0');
INSERT INTO `work_read_result` VALUES ('2', '1', '1', '3');
INSERT INTO `work_read_result` VALUES ('3', '1', '2', '0');
INSERT INTO `work_read_result` VALUES ('4', '1', '3', '0');
INSERT INTO `work_read_result` VALUES ('5', '1', '4', '4');
INSERT INTO `work_read_result` VALUES ('6', '1', '5', '0');
INSERT INTO `work_read_result` VALUES ('7', '1', '6', '0');
INSERT INTO `work_read_result` VALUES ('8', '1', '7', '5');
INSERT INTO `work_read_result` VALUES ('9', '1', '8', '0');
INSERT INTO `work_read_result` VALUES ('10', '1', '9', '0');

-- ----------------------------
-- Table structure for work_stock
-- ----------------------------
DROP TABLE IF EXISTS `work_stock`;
CREATE TABLE `work_stock` (
  `stock_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_type` int(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `brand` varchar(32) DEFAULT NULL,
  `param` varchar(64) DEFAULT NULL,
  `unit` varchar(16) DEFAULT NULL,
  `quantity` double(32,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stock_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of work_stock
-- ----------------------------
INSERT INTO `work_stock` VALUES ('1', '1', 'sss', 'sss', 'ss', 'ss', '31.00', '2022-08-06 18:08:34', '13');
INSERT INTO `work_stock` VALUES ('2', '1', 'www', 'ww', 'ww', 'ww', '314.00', '2022-08-06 18:08:40', '32');
