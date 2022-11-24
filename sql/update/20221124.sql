-- ----------------------------
-- Records of sys_menu
-- ----------------------------
DELETE FROM `sys_menu` WHERE menu_id = 1082;
INSERT INTO `sys_menu` VALUES (1082, '台账', 1081, 1, 'student', 'student/account/index', NULL, 1, 0, 'C', '0', '0', 'student:accout:list', 'chart', 'admin', '2022-11-22 06:03:48', 'admin', '2022-11-22 06:56:33', '');


-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
DELETE FROM `sys_role_menu` WHERE role_id = 1082;
INSERT INTO `sys_role_menu` VALUES (3, 1082);
INSERT INTO `sys_role_menu` VALUES (4, 1082);

