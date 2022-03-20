/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL5.7.31
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : ry-vue-3.8.1

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 21/02/2022 17:31:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pr_visitor
-- ----------------------------
DROP TABLE IF EXISTS `pr_visitor`;
CREATE TABLE `pr_visitor`  (
  `visitor_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访客Id',
  `visitor_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访客姓名',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访客电话',
  `matter` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访客事由',
  `backlog` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '待办事项',
  `visit_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`visitor_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '访客信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pr_visitor
-- ----------------------------
INSERT INTO `pr_visitor` VALUES (1, '张三', '13588552211', '参观', '发送资料', '2022-02-07 15:42:34', '0', 'admin', '2022-02-07 15:42:40', 'admin', '2022-02-07 15:42:40', NULL);
INSERT INTO `pr_visitor` VALUES (2, '李四', '13233668877', '参观', '喝酒', '2022-02-07 00:00:00', '0', 'admin', '2022-02-07 15:43:04', 'admin', '2022-02-08 16:04:02', NULL);
INSERT INTO `pr_visitor` VALUES (3, '王五', '13322226666', '太闲', NULL, '2022-02-08 00:00:00', '1', NULL, '2022-02-08 15:45:56', NULL, '2022-02-08 15:46:11', NULL);
INSERT INTO `pr_visitor` VALUES (4, '老五', '13566557788', '聊天', NULL, NULL, '0', NULL, '2022-02-09 10:59:29', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
