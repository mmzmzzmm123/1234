/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : mydemo

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 20/06/2024 18:30:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for psy_consultant_account
-- ----------------------------
DROP TABLE IF EXISTS `psy_consultant_account`;
CREATE TABLE `psy_consultant_account`  (
  `consultant_id` bigint NOT NULL COMMENT '账户ID',
  `account_number` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '数字账号ID',
  `withdrawal_password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '提现密码',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '1' COMMENT '1 正常 0 未激活',
  `account_amount` decimal(10, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '账户余额',
  `del_flag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '0 未删除 1 删除',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`consultant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for psy_consultant_account_record
-- ----------------------------
DROP TABLE IF EXISTS `psy_consultant_account_record`;
CREATE TABLE `psy_consultant_account_record`  (
  `record_id` bigint NOT NULL COMMENT '记录ID',
  `consultant_id` bigint NOT NULL COMMENT '账户ID',
  `status` char(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '1' COMMENT '1 成功 0 失败',
  `pay_type` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '0 分成 1 提现',
  `order_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '订单ID ：提现订单ID',
  `account_amount` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '支付前账户余额',
  `pay_amount` decimal(10, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '支付金额',
  `pay_message` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '支付消息结果',
  `del_flag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '0 未删除 1 删除',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '账户明细流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for psy_consultant_address
-- ----------------------------
DROP TABLE IF EXISTS `psy_consultant_address`;
CREATE TABLE `psy_consultant_address`  (
  `address_id` bigint NOT NULL COMMENT '地址ID',
  `consultant_id` bigint NOT NULL COMMENT '咨询师ID',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '1 默认 0 未默认',
  `contact_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '联系人名',
  `contact_telephone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '联系电话',
  `contact_address` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '联系地址',
  `del_flag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '0 未删除 1 删除',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '咨询师地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for psy_consultant_debitcard
-- ----------------------------
DROP TABLE IF EXISTS `psy_consultant_debitcard`;
CREATE TABLE `psy_consultant_debitcard`  (
  `card_number` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '借记卡ID',
  `consultant_id` bigint NOT NULL COMMENT '咨询师ID',
  `card_banck` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '借记卡所属银行',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '1' COMMENT '1 正常 0 未激活',
  `del_flag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '0 未删除 1 删除',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`card_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '客户银行卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for psy_consultant_referral_order
-- ----------------------------
DROP TABLE IF EXISTS `psy_consultant_referral_order`;
CREATE TABLE `psy_consultant_referral_order`  (
  `referral_id` bigint NOT NULL COMMENT '转介ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `consultant_id` bigint NOT NULL COMMENT '转入咨询师ID',
  `from_consult_id` bigint NOT NULL COMMENT '来着咨询师ID',
  `referral_remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '1' COMMENT '1 成功  0 处理中 4失败',
  `del_flag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '0 未删除 1 删除',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`referral_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '转介订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for psy_consultant_withdrawal_order
-- ----------------------------
DROP TABLE IF EXISTS `psy_consultant_withdrawal_order`;
CREATE TABLE `psy_consultant_withdrawal_order`  (
  `withdrawal_no` bigint NOT NULL COMMENT '提现ID',
  `consultant_id` bigint NOT NULL COMMENT '咨询师ID',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '1 成功  0 处理中 4失败',
  `withdrawal_amount` decimal(10, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '提现金额',
  `del_flag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '0' COMMENT '0 未删除 1 删除',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`withdrawal_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '提现订单' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
