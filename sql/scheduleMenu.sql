-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('计划', '3', '1', 'schedule', 'udef/schedule/index', 1, 0, 'C', '0', '0', 'udef:schedule:list', '#', 'admin', sysdate(), '', null, '计划菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('计划查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'udef:schedule:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('计划新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'udef:schedule:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('计划修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'udef:schedule:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('计划删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'udef:schedule:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('计划导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'udef:schedule:export',       '#', 'admin', sysdate(), '', null, '');