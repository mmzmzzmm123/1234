-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
DELETE FROM `sys_dict_type` WHERE dict_id BETWEEN 18 AND 24;
INSERT INTO `sys_dict_type` VALUES (18, '培养层次', 'training_level', '0', 'admin', '2022-11-22 04:38:33', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (19, '民族', 'nation', '0', 'admin', '2022-11-22 04:39:46', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (20, '校区', 'campus', '0', 'admin', '2022-11-22 04:40:42', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (21, '住宿地点', 'accommodation', '0', 'admin', '2022-11-22 04:41:54', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (22, '住宿园区', 'accommodation_park', '0', 'admin', '2022-11-22 04:43:24', 'admin', '2022-11-22 04:43:34', NULL);
INSERT INTO `sys_dict_type` VALUES (23, '密接类型', 'joint_type', '0', 'admin', '2022-11-23 06:57:30', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (24, '离校目的地', 'place_to_leave', '0', 'admin', '2022-11-23 14:56:50', '', NULL, NULL);



-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
DELETE FROM `sys_dict_data` WHERE dict_code BETWEEN 50 AND 67;
INSERT INTO `sys_dict_data` VALUES (50, 0, '硕士研究生', '0', 'training_level', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:39:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (51, 1, '博士研究生', '1', 'training_level', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:39:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (52, 0, '汉族', '0', 'nation', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:39:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (53, 1, '回族', '1', 'nation', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:40:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (54, 0, '雁塔校区', '0', 'campus', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:41:07', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (55, 1, '长安校区', '1', 'campus', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:41:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (56, 0, '教学区宿舍区', '0', 'accommodation', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:42:09', 'admin', '2022-11-23 14:32:31', NULL);
INSERT INTO `sys_dict_data` VALUES (57, 1, '教学区其他区', '1', 'accommodation', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:42:18', 'admin', '2022-11-23 14:32:45', NULL);
INSERT INTO `sys_dict_data` VALUES (58, 2, '家属区', '2', 'accommodation', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:42:30', 'admin', '2022-11-23 14:32:55', NULL);
INSERT INTO `sys_dict_data` VALUES (59, 0, '博士1号楼', '0', 'accommodation_park', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:43:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (60, 1, '博士2号楼', '1', 'accommodation_park', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:44:09', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (61, 1, '竹园1号楼', '2', 'accommodation_park', NULL, 'default', 'N', '0', 'admin', '2022-11-22 04:44:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (62, 1, 'B类密接', '1', 'joint_type', NULL, 'default', 'N', '0', 'admin', '2022-11-23 06:57:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (63, 2, 'C类密接', '2', 'joint_type', NULL, 'default', 'N', '0', 'admin', '2022-11-23 06:57:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (64, 3, '其他区', '3', 'accommodation', NULL, 'default', 'N', '0', 'admin', '2022-11-23 14:33:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (65, 0, '市内', '0', 'place_to_leave', NULL, 'default', 'N', '0', 'admin', '2022-11-23 14:57:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (66, 1, '省内市外', '1', 'place_to_leave', NULL, 'default', 'N', '0', 'admin', '2022-11-23 14:57:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (67, 2, '省外', '2', 'place_to_leave', NULL, 'default', 'N', '0', 'admin', '2022-11-23 14:57:36', '', NULL, NULL);

