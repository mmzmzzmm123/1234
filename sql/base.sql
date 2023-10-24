/*
 Navicat Premium Data Transfer

 Source Server         : 个人测试服
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 114.132.69.190:3306
 Source Schema         : ruoyi

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 24/10/2023 14:47:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_platform_user
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_user`;
CREATE TABLE `t_platform_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `enabled` int(11) NULL DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
  `weight` bigint(20) NULL DEFAULT 0 COMMENT '权重',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `avatar_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_platform_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_user_detail`;
CREATE TABLE `t_platform_user_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `enabled` int(11) NULL DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
  `weight` bigint(20) NULL DEFAULT 0 COMMENT '权重',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '电话',
  `avatar_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '用户类型: 1：微信用户',
  `data_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '抽象id，针对不同平台标识唯一标识',
  `login_end_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '最后登录时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户昵称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `un_data_id`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台用户详情信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `sys_config` VALUES (7, '用户注册-默认头像', 'default_avatar_url', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Fae175b96-62b4-4c48-ae36-a278a8a27e2c%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1700712553&t=66ab6321cb2fe9f92f3e8e846d602f54', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '注册默认头像');
INSERT INTO `sys_config` VALUES (8, '用户注册-默认昵称', 'default_nick_name', '平台用户_', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '注册默认昵称');
INSERT INTO `sys_config` VALUES (9, '用户注册-默认密码', 'default_password', '123456', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '注册默认密码');