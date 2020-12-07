-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-现任职务', '3', '1', 'gbxrzw', 'gbxxgl/gbxrzw/index', 1, 'C', '0', '0', 'gbxxgl:gbxrzw:list', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '干部任职情况-现任职务菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-现任职务查询', @parentId, '1',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbxrzw:query',        '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-现任职务新增', @parentId, '2',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbxrzw:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-现任职务修改', @parentId, '3',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbxrzw:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-现任职务删除', @parentId, '4',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbxrzw:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-现任职务导出', @parentId, '5',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbxrzw:export',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');