/*
Navicat MySQL Data Transfer

Source Server         : aliyun_47.101.170.26
Source Server Version : 80029
Source Host           : 47.101.170.26:3306
Source Database       : ruoyi-ibaiqi-timetable

Target Server Type    : MYSQL
Target Server Version : 80029
File Encoding         : 65001

Date: 2022-08-22 21:58:42
*/

SET FOREIGN_KEY_CHECKS=0;

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
