SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leave_detail
-- ----------------------------
DROP TABLE IF EXISTS `leave_detail`;
CREATE TABLE `leave_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '请假编号',
  `student_id` bigint(20) NOT NULL COMMENT '学号',
  `student_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门/学院编号',
  `dept_name` varbinary(64) NULL DEFAULT NULL COMMENT '部门/学院名称',
  `holiday_tag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假类型:0报备，1请假',
  `destination_tag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的地类型:0市内,1省内市外,2省外',
  `destination` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假目的地',
  `holiday_duration` int(10) NULL DEFAULT NULL COMMENT '请假时长',
  `holiday_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '请假开始时间',
  `holiday_end_time` datetime(0) NULL DEFAULT NULL COMMENT '请假结束时间',
  `is_back_school` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否返校：请假后是否已返校',
  `is_contact` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假未返校，是否已经联系，0未1已',
  `back_school_time` datetime(0) NULL DEFAULT NULL COMMENT '返校日期',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请假明细' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
