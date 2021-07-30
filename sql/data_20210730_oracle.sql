-- ----------------------------
-- 1、企业贷款信息表
-- ----------------------------
create table company_loan (
  company_id             number(20)       not null
  mzt_user_id            varchar2(20)     not null
  company_name           varchar2(64)     not null
  company_credit_code    varchar2(32)     default ''
  company_type           varchar2(32)     default ''
  company_industry       varchar2(64)     default ''
  company_business       varchar2(128)    default ''
  loan_band              varchar2(64)     default ''
  loan_amount            number(12,2)     not null
  loan_first             char(1)          default '0'
  loan_purpose           varchar2(64)     default ''
  remark                 varchar2(128)    default ''
  contact_name           varchar2(16)     default ''
  contact_phone          varchar2(16)     default ''
  create_time 	         date
  update_time            date
)

alter table company_loan add constraint pk_company_loan primary key (company_id);

comment on table  company_loan            is '企业贷款信息';
comment on column company_id              is '企业id';
comment on column mzt_user_id             is '闽政通用户ID';
comment on column company_name            is '企业名称';
comment on column company_credit_code     is '统一社会信用代码';
comment on column company_type            is '企业划型';
comment on column company_industry        is '所在行业';
comment on column company_business        is '主营业务';
comment on column loan_amount             is '贷款金额，单位万';
comment on column loan_first              is '是否首次贷款（0是 1不是）';
comment on column loan_purpose            is '贷款用途';
comment on column remark                  is '备注说明';
comment on column contact_name            is '联系人姓名';
comment on column contact_phone           is '联系人手机';
comment on column create_time 	          is '创建时间';
comment on column update_time             is '更新时间';