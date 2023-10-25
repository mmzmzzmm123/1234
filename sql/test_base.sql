create table sys_file_info (
    file_id          varchar(64) not null comment '文件id',
    file_name        varchar(50)          default '' comment '文件名称',
    server_file_id   varchar(255)         default '' comment '服务器文件id',
    suffix_name      varchar(8)           default '' comment '文件后缀名',
    size             bigint(20)           default '0' comment '文件大小，单位：Byte',
    create_time      bigint(20)  not null default '0' comment '创建时间',
    create_update_id bigint(20)  not null default '0' comment '上传人',
    source           bigint(20)  not null default '0' comment '上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务',
    file_url         bigint(20)  not null default '0' comment '文件访问地址',
    file_type        int(1)      not null default '1' comment '文件类型 1：图片 2：视频',
    primary key (file_id),
    unique key (server_file_id)
) engine = innodb  auto_increment = 1  default charset = utf8 comment = '文件信息表';

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