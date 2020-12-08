-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-社会职务', '2155', '1', 'gbshzw', 'gbxxgl/gbshzw/index', 1, 'C', '0', '0', 'gbxxgl:gbshzw:list', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '干部任职情况-社会职务菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-社会职务查询', @parentId, '1',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbshzw:query',        '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-社会职务新增', @parentId, '2',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbshzw:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-社会职务修改', @parentId, '3',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbshzw:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-社会职务删除', @parentId, '4',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbshzw:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部任职情况-社会职务导出', @parentId, '5',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbshzw:export',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');