-- ----------------------------
-- 1、企业贷款信息表
-- ----------------------------
create sequence seq_data_company_loan
    increment by 1
    start with 200
    nomaxvalue
    nominvalue
    cache 20;

create table data_company_loan (
  id                     number(20)       not null,
  company_id             number(20)       not null,
  mzt_user_id            varchar2(20)     not null,
  company_name           varchar2(128)    not null,
  company_credit_code    varchar2(32)     default '',
  company_type           varchar2(32)     default '',
  company_industry       varchar2(64)     default '',
  company_business       varchar2(1024)    default '',
  loan_band              varchar2(64)     default '',
  loan_amount            number(12,2)     not null,
  loan_first             char(1)          default '0',
  loan_purpose           varchar2(64)     default '',
  remark                 varchar2(128)    default '',
  contact_name           varchar2(16)     default '',
  contact_phone          varchar2(16)     default '',
  create_time 	         date,
  update_time            date
);

alter table data_company_loan add constraint pk_data_company_loan primary key (id);

comment on table  data_company_loan                         is '企业贷款信息';
comment on column data_company_loan.id                      is 'id主键seq_data_company_loan.nextval,';
comment on column data_company_loan.company_id              is '企业id，贷款企业数据来源的id';
comment on column data_company_loan.mzt_user_id             is '闽政通用户ID';
comment on column data_company_loan.company_name            is '企业名称';
comment on column data_company_loan.company_credit_code     is '统一社会信用代码';
comment on column data_company_loan.company_type            is '企业划型';
comment on column data_company_loan.company_industry        is '所在行业';
comment on column data_company_loan.company_business        is '主营业务';
comment on column data_company_loan.loan_amount             is '贷款金额，单位万';
comment on column data_company_loan.loan_first              is '是否首次贷款（0是 1不是）';
comment on column data_company_loan.loan_purpose            is '贷款用途';
comment on column data_company_loan.remark                  is '备注说明';
comment on column data_company_loan.contact_name            is '联系人姓名';
comment on column data_company_loan.contact_phone           is '联系人手机';
comment on column data_company_loan.create_time             is '创建时间';
comment on column data_company_loan.update_time             is '更新时间';
