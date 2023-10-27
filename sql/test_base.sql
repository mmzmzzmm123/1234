/*
Navicat MySQL Data Transfer

Source Server         : 114.132.69.190
Source Server Version : 50743
Source Host           : 114.132.69.190:3306
Source Database       : ruoyi

Target Server Type    : MYSQL
Target Server Version : 50743
File Encoding         : 65001

Date: 2023-10-25 23:15:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info` (
  `file_id` varchar(64) NOT NULL COMMENT '文件id',
  `file_name` varchar(100) DEFAULT '' COMMENT '文件名称',
  `server_file_id` varchar(255) DEFAULT '' COMMENT '服务器文件id',
  `suffix_name` varchar(8) DEFAULT '' COMMENT '文件后缀名',
  `size` bigint(20) DEFAULT '0' COMMENT '文件大小，单位：Byte',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上传人',
  `source` bigint(20) NOT NULL DEFAULT '0' COMMENT '上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务',
  `file_url` varchar(50) NOT NULL DEFAULT '0' COMMENT '文件访问地址',
  `file_type` int(1) NOT NULL DEFAULT '1' COMMENT '文件类型 1：图片 2：视频',
  PRIMARY KEY (`file_id`),
  UNIQUE KEY `server_file_id` (`server_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件信息表';


SET @parentId = '0';
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('文件信息查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'onethinker:file:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('文件信息新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'onethinker:file:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('文件信息修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'onethinker:file:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('文件信息删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'onethinker:file:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('文件信息导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'onethinker:file:export',       '#', 'admin', sysdate(), '', null, '');

insert into sys_role_menu (role_id, menu_id) VALUES ('2','1062');
insert into sys_role_menu (role_id, menu_id) VALUES ('2','1063');
insert into sys_role_menu (role_id, menu_id) VALUES ('2','1064');
insert into sys_role_menu (role_id, menu_id) VALUES ('2','1065');


INSERT INTO sys_config (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
    (12, '小程序APPId', 'appId', '123456', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '小程序APPId');
INSERT INTO sys_config (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
    (13, '小程序APPSECRET', 'appsecret', '123456', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '小程序APPSECRET');
INSERT INTO sys_config (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
    (14, '默认图片地址', 'detail_file_path', '/onethinker/pic/', 'Y', 'admin', '2023-10-22 11:58:41', '', NULL, '默认图片地址');