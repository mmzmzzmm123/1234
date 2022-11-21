-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1075, '学生信息', 1081, 1, 'info', 'student/info/index', NULL, 1, 0, 'C', '0', '0', 'student:info:list', 'peoples', 'admin', '2022-11-21 06:01:43', 'admin', '2022-11-21 06:03:31', '学生信息菜单');
INSERT INTO `sys_menu` VALUES (1076, '学生信息查询', 1075, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'student:info:query', '#', 'admin', '2022-11-21 06:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1077, '学生信息新增', 1075, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'student:info:add', '#', 'admin', '2022-11-21 06:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1078, '学生信息修改', 1075, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'student:info:edit', '#', 'admin', '2022-11-21 06:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1079, '学生信息删除', 1075, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'student:info:remove', '#', 'admin', '2022-11-21 06:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1080, '学生信息导出', 1075, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'student:info:export', '#', 'admin', '2022-11-21 06:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1081, '学生管理', 0, 5, 'student', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'education', 'admin', '2022-11-21 06:02:56', '', NULL, '');

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (11, '学生标签', 'student_tag', '0', 'admin', '2022-11-21 05:04:09', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (12, '管控等级', 'control_level', '0', 'admin', '2022-11-21 05:06:32', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (13, '未返校原因', 'not_school_reason', '0', 'admin', '2022-11-21 05:08:09', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (14, '风险等级', 'risk_level', '0', 'admin', '2022-11-21 05:09:42', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (15, '返校出发地', 'place_to_school', '0', 'admin', '2022-11-21 05:10:33', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (16, '请假类型', 'holiday_type', '0', 'admin', '2022-11-21 05:11:19', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (17, '目的地类型', 'destination_type', '0', 'admin', '2022-11-21 05:12:03', '', NULL, NULL);


-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (30, 0, '在校', '0', 'student_tag', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:04:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (31, 1, '请假离校', '1', 'student_tag', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:05:08', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (32, 2, '未返校', '2', 'student_tag', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:05:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (33, 3, '当日返校', '3', 'student_tag', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:05:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (34, 0, '无管控', '0', 'control_level', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:06:47', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (35, 1, '校内居家监测', '1', 'control_level', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:07:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (36, 2, '校内居家隔离', '2', 'control_level', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:07:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (37, 3, '校外集中隔离', '3', 'control_level', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:07:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (38, 0, '因疫暂缓', '0', 'not_school_reason', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:08:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (39, 1, '因就业实习暂缓', '1', 'not_school_reason', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:08:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (40, 2, '因其他暂缓返', '2', 'not_school_reason', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:08:52', 'admin', '2022-11-21 05:09:03', NULL);
INSERT INTO `sys_dict_data` VALUES (41, 0, '低风险', '0', 'risk_level', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:09:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (42, 1, '高风险', '1', 'risk_level', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:10:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (43, 0, '省内', '0', 'place_to_school', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:10:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (44, 1, '省外', '1', 'place_to_school', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:10:50', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (45, 0, '报备', '0', 'holiday_type', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:11:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (46, 1, '请假', '1', 'holiday_type', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:11:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (47, 0, '市内', '0', 'destination_type', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:12:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (48, 1, '省内市外', '1', 'destination_type', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:12:37', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (49, 2, '省外', '2', 'destination_type', NULL, 'default', 'N', '0', 'admin', '2022-11-21 05:12:47', '', NULL, NULL);