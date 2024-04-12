-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('建筑物管理', '2000', '1', 'building', 'autoee/building/index', 1, 0, 'C', '0', '0', 'autoee:building:list', '#', 'admin', sysdate(), '', null, '建筑物管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('建筑物管理查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'autoee:building:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('建筑物管理新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'autoee:building:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('建筑物管理修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'autoee:building:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('建筑物管理删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'autoee:building:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('建筑物管理导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'autoee:building:export',       '#', 'admin', sysdate(), '', null, '');