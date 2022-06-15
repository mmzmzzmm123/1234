-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成交订单', '3', '1', 'trade', 'udef/trade/index', 1, 0, 'C', '0', '0', 'udef:trade:list', '#', 'admin', sysdate(), '', null, '成交订单菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成交订单查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'udef:trade:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成交订单新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'udef:trade:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成交订单修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'udef:trade:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成交订单删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'udef:trade:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成交订单导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'udef:trade:export',       '#', 'admin', sysdate(), '', null, '');