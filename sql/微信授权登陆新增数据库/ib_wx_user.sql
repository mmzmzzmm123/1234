/*
Navicat MySQL Data Transfer

Source Server         : aliyun_47.101.170.26
Source Server Version : 80029
Source Host           : 47.101.170.26:3306
Source Database       : ruoyi-ibaiqi-timetable

Target Server Type    : MYSQL
Target Server Version : 80029
File Encoding         : 65001

Date: 2022-08-22 21:59:21
*/

SET FOREIGN_KEY_CHECKS=0;

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
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='微信用户表';

-- ----------------------------
-- Records of ib_wx_user
-- ----------------------------
INSERT INTO `ib_wx_user` VALUES ('7', 'AbuCoder🕊', 'https://thirdwx.qlogo.cn/mmopen/vi_32/w9hLTEeERJD7A1tCGaMPQUYTxZMqmsmJuqWJzia21dFR6PtDhSqF7iceIegXNEeutHY5opJGSIalxThGBicglfKTw/132', 'o1Orf5W7Z7ErDzYXCbaxDquwToDg', '0', 'Abu', '2022-06-27 14:28:21', '', null, null);
INSERT INTO `ib_wx_user` VALUES ('8', 'AbuCoder🕊', 'https://thirdwx.qlogo.cn/mmopen/vi_32/C3fweXtXQBibtaibaGn3MI2b99kLhL0wELPedvWGUydM4Ghfww94Dtq1PeZqbexkrvHWGxfSeTXtc0nsexJhlf6w/132', 'o1O7_5Yuo7Zb_nsNQckMAEs75564', '0', 'Abu', '2022-06-28 15:06:43', '', null, null);
INSERT INTO `ib_wx_user` VALUES ('38', '码哥@码农之旅', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTInxGDaYcpnSA7zU0mmzhC0FmhibFKCdrBcGqZoibMHWXRRrt5egAO2jIxCCeOFRPI3IXib4nWkfjRTw/132', 'oLgdC5JniZnYKR4zyZP2lLEGjdRw', '0', '147', '2022-08-22 11:51:15', 'Abu', '2022-08-22 21:25:30', null);
