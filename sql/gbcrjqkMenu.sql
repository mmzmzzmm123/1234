-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部出入境情况', '3', '1', 'gbcrjqk', 'gbxxgl/gbcrjqk/index', 1, 'C', '0', '0', 'gbxxgl:gbcrjqk:list', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '干部出入境情况菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部出入境情况查询', @parentId, '1',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbcrjqk:query',        '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部出入境情况新增', @parentId, '2',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbcrjqk:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部出入境情况修改', @parentId, '3',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbcrjqk:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部出入境情况删除', @parentId, '4',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbcrjqk:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('干部出入境情况导出', @parentId, '5',  '#', '', 1,  'F', '0',  '0', 'gbxxgl:gbcrjqk:export',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');