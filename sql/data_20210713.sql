-- ----------------------------
-- 1、企业贷款信息表
-- ----------------------------
drop table if exists data_company_loan;
create table data_company_loan (
  company_id             bigint(20)      not null auto_increment    comment '企业id',
  mzt_user_id            varchar(20)     not null                   comment '闽政通用户ID',
  company_name           varchar(64)     not null                   comment '企业名称',
  company_credit_code    varchar(32)     default ''                 comment '统一社会信用代码',
  company_province       varchar(30)     default ''                 comment '省份',
  company_city           varchar(30)     default ''                 comment '市',
  company_area           varchar(30)     default ''                 comment '区或县',
  company_type           varchar(32)     default ''                 comment '企业划型',
  company_industry       varchar(64)     default ''                 comment '所在行业',
  company_business       varchar(128)    default ''                 comment '主营业务',
  loan_band              varchar(64)     default ''                 comment '意向银行（逗号隔开）',
  loan_amount            decimal(12,2)   not null                   comment '贷款金额，单位万',
  loan_first             char(1)         default '0'                comment '是否首次贷款（0是 1不是）',
  loan_purpose           varchar(64)     default ''                 comment '贷款用途',
  remark                 varchar(128)    default ''                 comment '备注说明',
  contact_name           varchar(16)     default ''                 comment '联系人姓名',
  contact_phone          varchar(16)     default ''                 comment '联系人手机',
  del_flag               char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_time 	         datetime                                   comment '创建时间',
  update_time            datetime                                   comment '更新时间',
  primary key (company_id)
) engine=innodb auto_increment=1 comment = '企业贷款信息表';
