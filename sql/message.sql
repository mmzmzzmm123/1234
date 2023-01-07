drop table if exists message_template;
CREATE TABLE `message_template`
(
    `msg_temp_id`      int          NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `msg_temp_pcode`   varchar(50)  NOT NULL COMMENT '模板唯一编码',
    `msg_temp_type`    smallint     NOT NULL COMMENT '模板类型 1.站内信',
    `msg_temp_title`   varchar(100) NOT NULL COMMENT '模板标题',
    `msg_temp_content` text         NOT NULL COMMENT '模板内容',
    `create_by`        varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`           varchar(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`msg_temp_id`),
    UNIQUE KEY `ud_pcode` (`msg_temp_pcode`)
) ENGINE=InnoDB   COMMENT='消息模板';

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息管理', '0' , '1', 'message', 1, 0, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, '消息管理菜单');

-- 按钮父菜单ID
SELECT @parentMainId := LAST_INSERT_ID();

-- 子菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板', @parentMainId , '1', 'template', 'message/template/index', 1, 0, 'C', '0', '0', 'message:template:list', 'template', 'admin', sysdate(), '', null, '消息模板菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'message:template:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'message:template:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'message:template:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'message:template:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'message:template:export',       '#', 'admin', sysdate(), '', null, '');

-- 站内消息记录
drop table if exists message_site_records;
CREATE TABLE `message_site_records` (
    `msg_site_id` bigint NOT NULL AUTO_INCREMENT COMMENT '站内信流水id',
    `send_user_id` bigint NOT NULL COMMENT '发送者用户ID',
    `send_user_name` varchar(30) NOT NULL COMMENT '发送者用户名称',
    `to_user_id` bigint NOT NULL COMMENT '接收者用户ID',
    `to_user_name` varchar(30) NOT NULL COMMENT '接受者用户名称',
    `msg_site_title` varchar(100) NOT NULL COMMENT '站内信标题',
    `msg_site_content` text NOT NULL COMMENT '站内信内容',
    `read`             char(1)      NOT NULL COMMENT '是否已读',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`msg_site_id`)
) ENGINE=InnoDB COMMENT='站内信消息';

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('站内信消息', @parentMainId, '2', 'siteRecords', 'message/siteRecords/index', 1, 0, 'C', '0', '0', 'message:siteRecords:list', 'email', 'admin', sysdate(), '', null, '站内信消息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('站内信消息查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'message:siteRecords:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('站内信消息新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'message:siteRecords:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('站内信消息删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'message:siteRecords:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('站内信消息导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'message:siteRecords:export',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('站内信消息全体通知', @parentId, '6',  '#', '', 1, 0, 'F', '0', '0', 'message:siteRecords:sendAll',       '#', 'admin', sysdate(), '', null, '');

-- 站内消息清理任务
insert into sys_job values(default , '站内消息清理', 'DEFAULT', 'messageTask.cleanHistory',  '0 0 1 * * ?', '3', '1', '0', '0', 'admin', sysdate(), '', null, '');
