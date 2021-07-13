-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业贷款信息', '3', '1', 'loan', 'system/loan/index', 1, 0, 'C', '0', '0', 'system:loan:list', '#', 'admin', sysdate(), '', null, '企业贷款信息菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业贷款信息查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:loan:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业贷款信息新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:loan:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业贷款信息修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:loan:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业贷款信息删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:loan:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业贷款信息导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:loan:export',       '#', 'admin', sysdate(), '', null, '');