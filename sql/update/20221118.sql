-- ----------------------------
-- Records of sys_user
-- ----------------------------
DELETE FROM `sys_user`;
INSERT INTO `sys_user` VALUES (1, 100, 'admin', '超级管理员', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2022-11-18 22:34:31', 'admin', '2022-11-18 09:03:45', '', '2022-11-18 14:34:28', '管理员');
INSERT INTO `sys_user` VALUES (2, 100, 'ry', '管理员', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2022-11-18 09:03:45', 'admin', '2022-11-18 09:03:45', 'admin', '2022-11-18 14:16:24', '测试员');
INSERT INTO `sys_user` VALUES (3, 100, 'ac0', '用户0', '00', '', '', '0', '', '$2a$10$URmOEgD4wLXAVzGwPEp3zejU2r5PQNi.bMmCE8EcxAeWt1mVtSAL6', '0', '0', '127.0.0.1', '2022-11-18 22:34:20', 'admin', '2022-11-18 14:32:53', 'admin', '2022-11-18 14:35:30', '测试账号：最高级别');

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
DELETE FROM `sys_dept`;
INSERT INTO `sys_dept` VALUES (100, 0, '0', '陕西师范大学', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '历史文化学院', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '计算机科学学院', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '一级部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2022-11-18 09:03:45', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '一级部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-11-18 09:03:45', 'admin', '2022-11-18 14:17:40');
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2022-11-18 09:03:45', '', NULL);


-- ----------------------------
-- Records of sys_post
-- ----------------------------
DELETE FROM `sys_post`;
INSERT INTO `sys_post` VALUES (1, 'school_leader', '校领导', 1, '0', 'admin', '2022-11-18 09:03:45', 'admin', '2022-11-18 14:28:45', '');
INSERT INTO `sys_post` VALUES (2, 'institute_leader', '院领导', 2, '0', 'admin', '2022-11-18 09:03:45', 'admin', '2022-11-18 14:29:22', '');
INSERT INTO `sys_post` VALUES (3, 'instructor', '辅导员', 3, '0', 'admin', '2022-11-18 09:03:45', 'admin', '2022-11-18 14:29:51', '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通用户', 4, '0', 'admin', '2022-11-18 09:03:45', 'admin', '2022-11-18 14:30:10', '');


-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
DELETE FROM `sys_user_role`;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 2);