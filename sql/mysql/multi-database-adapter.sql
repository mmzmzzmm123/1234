-- ----------------------------
-- Table structure for sys_sequence
-- ----------------------------
CREATE TABLE `sys_sequence` (
                                `sequence_name` varchar(50) NOT NULL COMMENT '序列名称',
                                `sequence_value` int(11) NOT NULL DEFAULT '1' COMMENT '序列值',
                                `increment_value` int(11) NOT NULL DEFAULT '1' COMMENT '增量值',
                                PRIMARY KEY (`sequence_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='序列';

-- ----------------------------
-- Records of sys_sequence
-- ----------------------------

INSERT INTO `sys_sequence` VALUES ('seq_sys_config', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_dept', 200, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_dict_data', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_dict_type', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_job', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_job_log', 1, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_logininfor', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_menu', 2000, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_notice', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_oper_log', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_post', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_role', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_sys_user', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_gen_table', 100, 1);
INSERT INTO `sys_sequence` VALUES ('seq_gen_table_column', 100, 1);

-- ----------------------------
-- Function structure for nextval
-- ----------------------------
CREATE FUNCTION `nextval`(seq_name varchar (50))
    RETURNS int
(11)
begin declare
sequence int; set
sequence = (select sequence_value from sys_sequence where sequence_name = seq_name);
update sys_sequence
set sequence_value = sequence_value + increment_value
where sequence_name = seq_name;
return sequence;
end
