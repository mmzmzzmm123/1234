-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人', '3', '1', 'robot', 'udef/robot/index', 1, 0, 'C', '0', '0', 'udef:robot:list', '#', 'admin', sysdate(), '', null, '机器人菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'udef:robot:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'udef:robot:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'udef:robot:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'udef:robot:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'udef:robot:export',       '#', 'admin', sysdate(), '', null, '');