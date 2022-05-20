-- ----------------------------
-- F1、密码强度设置表
-- ----------------------------
drop table if exists sys_password;
create table sys_password
(
    id                 bigint(20) not null auto_increment comment 'id号',

    max_failed         int(4)      default 5 comment '密码最大尝试次数',
    userlock_period    int(4)      default 10 comment '用户锁定后解锁时间，默认10分钟',
    expiration_period  int(4)      default 90 comment '密码过期时长，默认90天',
    notification_email varchar(50) default '' comment '用户锁定后通知邮箱',

    min_length         int(4)      default 5 comment '密码最小长度',
    max_length         int(4)      default 20 comment '密码最大长度',
    min_upper          int(4)      default 1 comment '大写字符最少数量',
    min_lower          int(4)      default 1 comment '小写字符最少数量',
    min_digits         int(4)      default 1 comment '数字字符最少数量',
    min_special        int(4)      default 1 comment '特殊字符最少数量',

    create_by          varchar(64) default '' comment '创建者',
    create_time        datetime comment '创建时间',
    update_by          varchar(64) default '' comment '更新者',
    update_time        datetime comment '更新时间',
    primary key (id)
) engine = innodb
  auto_increment = 200 comment = '密码策略表';

-- ----------------------------
-- 初始化-密码策略表数据
-- ----------------------------
insert into sys_password(id, max_failed, userlock_period, expiration_period, notification_email, min_length,
                          max_length, min_upper, min_lower, min_digits, min_special, create_by, create_time, update_by,
                          update_time)
values (1, 5, 10, 90, 'rq@qq.com', 8, 20, 1, 1, 1, 1, 'admin', sysdate(), '', null);

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('密码策略', '1', '7', 'password', 'system/password/index', 1, 0, 'C', '0', '0', 'system:password:list', '#',
        'admin', sysdate(), '', null, '密码策略菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('密码策略查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'system:password:query', '#', 'admin', sysdate(), '',
        null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('密码策略修改', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'system:password:edit', '#', 'admin', sysdate(), '',
        null, '');

commit;

-- 修改用户表，添加尝试次数和锁定时间字段
ALTER TABLE sys_user
    ADD COLUMN attempt_count int(4)   NULL COMMENT '尝试登录次数' AFTER `remark`,
    ADD COLUMN lock_time     datetime NULL COMMENT '锁定时间' AFTER `attempt_count`;

-- 添加安全管理员和审计管理员两个角色
insert into sys_role values('3', '安全管理员',    'security', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '安全管理员');
insert into sys_role values('4', '审计管理员',    'auditor', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '审计管理员');