-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户记录', '2039', '1', 'log', 'custom/wxUserLog/index', 1, 0, 'C', '0', '0', 'custom:log:list', 'log', 'wonder', '2020-11-28', 'wonder', '2020-11-28', '微信用户记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户记录查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'custom:log:query',        '#', 'wonder', '2020-11-28', 'wonder', '2020-11-28', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户记录新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'custom:log:add',          '#', 'wonder', '2020-11-28', 'wonder', '2020-11-28', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户记录修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'custom:log:edit',         '#', 'wonder', '2020-11-28', 'wonder', '2020-11-28', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户记录删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'custom:log:remove',       '#', 'wonder', '2020-11-28', 'wonder', '2020-11-28', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户记录导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'custom:log:export',       '#', 'wonder', '2020-11-28', 'wonder', '2020-11-28', '');