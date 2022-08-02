/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 02/08/2022 17:56:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for work_process
-- ----------------------------
DROP TABLE IF EXISTS `work_process`;
CREATE TABLE `work_process`  (
  `process_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`process_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work_process
-- ----------------------------
INSERT INTO `work_process` VALUES (1, '22', '22', '22');

-- ----------------------------
-- Table structure for work_read_result
-- ----------------------------
DROP TABLE IF EXISTS `work_read_result`;
CREATE TABLE `work_read_result`  (
  `result_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `slave_id` int(11) NULL DEFAULT NULL,
  `address` int(11) NULL DEFAULT NULL,
  `value` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`result_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work_read_result
-- ----------------------------
INSERT INTO `work_read_result` VALUES (1, 1, 0, 0);
INSERT INTO `work_read_result` VALUES (2, 1, 1, 3);
INSERT INTO `work_read_result` VALUES (3, 1, 2, 0);
INSERT INTO `work_read_result` VALUES (4, 1, 3, 0);
INSERT INTO `work_read_result` VALUES (5, 1, 4, 4);
INSERT INTO `work_read_result` VALUES (6, 1, 5, 0);
INSERT INTO `work_read_result` VALUES (7, 1, 6, 0);
INSERT INTO `work_read_result` VALUES (8, 1, 7, 5);
INSERT INTO `work_read_result` VALUES (9, 1, 8, 0);
INSERT INTO `work_read_result` VALUES (10, 1, 9, 0);

-- ----------------------------
-- Table structure for work_stock
-- ----------------------------
DROP TABLE IF EXISTS `work_stock`;
CREATE TABLE `work_stock`  (
  `stock_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_type` int(11) NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `brand` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `quantity` double(32, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`stock_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work_stock
-- ----------------------------
INSERT INTO `work_stock` VALUES (1, 1, 'sss', 'sss', 'ss', 'ss', 31.00);
INSERT INTO `work_stock` VALUES (2, 1, 'www', 'ww', 'ww', 'ww', 314.00);

SET FOREIGN_KEY_CHECKS = 1;
