-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('API', '3', '1', 'key', 'udef/key/index', 1, 0, 'C', '0', '0', 'udef:key:list', '#', 'admin', sysdate(), '', null, 'API菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('API查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'udef:key:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('API新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'udef:key:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('API修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'udef:key:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('API删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'udef:key:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('API导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'udef:key:export',       '#', 'admin', sysdate(), '', null, '');