-- ----------------------------
-- Records of sys_job
-- ----------------------------
DELETE FROM `sys_job` WHERE job_id = 4;
INSERT INTO `sys_job` VALUES (4, '学生状态变更', 'DEFAULT', 'studentTask.changeStudentTag()', '0 0 0 * * ? ', '1', '1', '0', 'admin', '2022-11-25 01:04:12', 'admin', '2022-11-25 01:20:39', '');
