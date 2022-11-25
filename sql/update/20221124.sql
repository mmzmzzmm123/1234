-- ----------------------------
-- Records of sys_menu
-- ----------------------------
DELETE FROM `sys_menu` WHERE menu_id = 1082;
INSERT INTO `sys_menu` VALUES (1082, '台账', 1081, 1, 'student', 'student/account/index', NULL, 1, 0, 'C', '0', '0', 'student:accout:list', 'chart', 'admin', '2022-11-22 06:03:48', 'admin', '2022-11-22 06:56:33', '');


-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
DELETE FROM `sys_role_menu` WHERE menu_id = 1082;
INSERT INTO `sys_role_menu` VALUES (3, 1082);
INSERT INTO `sys_role_menu` VALUES (4, 1082);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
DELETE FROM `sys_role` WHERE role_id = 5;
INSERT INTO `sys_role` VALUES (5, '学生状态管理', 'stu_state', 4, '1', 1, 1, '0', '0', 'admin', '2022-11-24 14:00:19', '', NULL, NULL);

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
DELETE FROM `sys_role_menu` WHERE role_id = 5;
INSERT INTO `sys_role_menu` VALUES (5, 1075);
INSERT INTO `sys_role_menu` VALUES (5, 1076);
INSERT INTO `sys_role_menu` VALUES (5, 1077);
INSERT INTO `sys_role_menu` VALUES (5, 1080);
INSERT INTO `sys_role_menu` VALUES (5, 1081);
INSERT INTO `sys_role_menu` VALUES (5, 1082);
INSERT INTO `sys_role_menu` VALUES (5, 1083);
