CREATE TABLE `goods_classify`
(
    `classify_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
    `parent_id`   bigint      DEFAULT '0' COMMENT '父分类id',
    `ancestors`   varchar(50) DEFAULT '' COMMENT '祖级列表',
    `classify_name`   varchar(30) DEFAULT '' COMMENT '分类名称',
    `order_num`   int         DEFAULT '0' COMMENT '显示顺序',
    `status`      char(1)     DEFAULT '0' COMMENT '使用状态（0正常 1停用）',
    `create_by`   varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`classify_id`),
    KEY `id_ancestors` (`ancestors`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT COMMENT='货品分类表';


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('货品资料', '0' , '0', 'goods', 1, 0, 'M', '0', '0', '', 'goods', 'admin', sysdate(), '', null, '货品资料管理菜单');

-- 按钮父菜单ID
SELECT @parentMainId := LAST_INSERT_ID();

-- 子菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('货品分类', @parentMainId , '1', 'classify', 'goods/classify/index', 1, 0, 'C', '0', '0', 'goods:classify:list', 'tree-table', 'admin', sysdate(), '', null, '货品分类菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('货品分类查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'goods:classify:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('货品分类新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'goods:classify:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('货品分类修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'goods:classify:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('货品分类删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'goods:classify:remove',       '#', 'admin', sysdate(), '', null, '');
