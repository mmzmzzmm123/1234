-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人订单', '3', '1', 'position', 'udef/position/index', 1, 0, 'C', '0', '0', 'udef:position:list', '#', 'admin', sysdate(), '', null, '机器人订单菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人订单查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'udef:position:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人订单新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'udef:position:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人订单修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'udef:position:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人订单删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'udef:position:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机器人订单导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'udef:position:export',       '#', 'admin', sysdate(), '', null, '');