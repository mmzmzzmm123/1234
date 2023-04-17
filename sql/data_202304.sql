-- ----------------------------
-- 1、微信用户表
-- ----------------------------
drop table if exists data_wechat_user;
CREATE TABLE `data_wechat_user`
(
    `user_id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `nick_name`           varchar(30)  DEFAULT NULL COMMENT '用户昵称',
    `phone`               varchar(20)  DEFAULT NULL COMMENT '手机号码',
    `avatar`              varchar(100) DEFAULT NULL COMMENT '头像地址',
    `remark`              varchar(500) DEFAULT NULL COMMENT '备注',

    `open_id`             varchar(30)  NOT NULL COMMENT '微信openID',
    `company_name`        varchar(200) NOT NULL COMMENT '企业名称',
    `tyshxydm`            varchar(200) NOT NULL COMMENT '统一社会信用代码',
    `legal_person_name`   varchar(100) NOT NULL COMMENT '法人姓名',
    `legal_person_phone`  varchar(20)  NOT NULL COMMENT '法人手机号码',
    `legal_person_idcard` varchar(50)  NOT NULL COMMENT '法人身份证号码',

    `JBR_NAME`            varchar(50)  NOT NULL COMMENT '经办人姓名',
    `JBR_IDCARD`          varchar(50)  NOT NULL COMMENT '经办人身份证号码',
    `JBR_PHONE`           varchar(20)  NOT NULL COMMENT '经办人手机号码',


    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='微信用户信息表';


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('微信用户信息', '3', '1', 'wechatUser', 'system/wechatUser/index', 1, 0, 'C', '0', '0', 'system:wechatUser:list', '#',
        'admin', sysdate(), '', null, '微信用户信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('微信用户信息查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'system:wechatUser:query', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('微信用户信息新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'system:wechatUser:add', '#', 'admin', sysdate(), '',
        null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('微信用户信息修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'system:wechatUser:edit', '#', 'admin', sysdate(), '',
        null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('微信用户信息删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'system:wechatUser:remove', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('微信用户信息导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'system:wechatUser:export', '#', 'admin', sysdate(),
        '', null, '');