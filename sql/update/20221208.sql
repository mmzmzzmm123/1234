

DELETE FROM `sys_menu` WHERE menu_id =2014;

insert into `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
values('2014','修改日志','2013','1','department','department/dept',NULL,'1','0','C','0','0',NULL,'chart','',NULL,'',NULL,'');
