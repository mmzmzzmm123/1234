drop table if exists sys_project;
create table sys_project(
  id           bigint(20)      not null auto_increment    comment '项目id',
  dept_id           bigint(20)      default 0   comment '部门id',
  user_id           bigint(20)      default 0   comment '用户id',
  name         varchar(30)     default ''                 comment '项目名称',
  type         varchar(30)     default ''                 comment '项目类型',
  description varchar(255)          default ''                  comment '项目描述',
  total int     default 0 comment '项目总金额',
  receive int     default 0 comment '到账金额',
  phone             varchar(11)     default null               comment '负责人电话',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=200 comment = '項目表';
