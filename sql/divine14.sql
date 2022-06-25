-- ----------------------------
-- 1、api key
-- ----------------------------
drop table if exists df_api_key;
create table df_api_key (
  id                bigint(20)      not null auto_increment    comment 'id',
  platform          varchar(30)     default null               comment 'platform',
  api_name          varchar(30)     default 'api'              comment 'api名称',
  api_key           varchar(64)     default null               comment 'apikey',
  api_secret        varchar(64)     default null               comment 'api秘钥',
  passphrase        varchar(30)     default null               comment 'passphrase',
  create_time 	    datetime                                   comment '创建时间',
  primary key (id)
) engine=innodb comment = 'API';


-- ----------------------------
-- 1、api robot
-- ----------------------------
drop table if exists df_api_robot;
create table df_api_robot (
  id                bigint(20)      not null auto_increment    comment 'id',
  robot_name        varchar(30)     not null                   comment '机器人',
  symbol            varchar(30)     not null                   comment '合约名',
  quantity_base     double           default null               comment '固定开仓比',
  quantity          int(10)         default null               comment '固定开仓张数',
  lit_interval      bigint(20)      not null                   comment '小周期',
  big_interval      bigint(20)      not null                   comment '大周期',
  margin            varchar(30)     default null               comment '持仓模式',
  leverage          double           not null                   comment '杠杆',
  status            varchar(15)     not null                   comment '状态',
  api_id            bigint(20)      not null                   comment 'api',
  create_time 	    datetime                                   comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb comment = '机器人';

-- ----------------------------
-- 1、api alarm
-- ----------------------------
drop table if exists df_robot_alarm;
create table df_robot_alarm (
  id                bigint(20)      not null auto_increment    comment 'id',
  long_code         varchar(10)     not null                   comment '开多编码',
  short_code        varchar(10)     not null                   comment '开空编码',
  phone             varchar(30)     not null                   comment '手机号',
  status            varchar(15)     not null                   comment '状态',
  robot_id          bigint(20)      not null                   comment '机器人',
  primary key (id)
) engine=innodb comment = '报警';


-- ----------------------------
-- 1、api schedule
-- ----------------------------
drop table if exists df_escape_schedule;
create table df_escape_schedule (
  id                bigint(20)      not null auto_increment    comment 'id',
  escape_type       varchar(30)     not null comment '计划类型',
  price_rate        double      not null comment '平仓价格比',
  quantity_rate     double      default null comment '平仓数量比',
  quantity          int(10)         default null comment '平仓数量',
  priority          tinyint(1)      not null comment '优先级',
  robot_id            bigint(20)      not null                   comment 'api',
  primary key (id)
) engine=innodb comment = '计划';


-- ----------------------------
-- 1、robot order
-- ----------------------------
drop table if exists df_robot_order;
create table df_robot_order (
  id                bigint(20)      not null auto_increment    comment 'id',
  predict_balance   varchar(15)     not null comment '前置余额',
  balance           varchar(15)     not null comment '当前余额',
  max_position      int(10)         not null comment '最大持仓',
  position          int(10)         not null comment '当前持仓',
  symbol            varchar(15)     not null        comment '交易对',
  open_side         varchar(15)     not null comment '开仓方向',
  status            varchar(15)     not null comment '状态',
  strategy          varchar(15)     not null comment '执行策略',
  robot_id          bigint(20)      not null                   comment '机器人',
  create_time       datetime                                   comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb comment = '机器人订单';


drop table if exists df_trade_order;
create table df_trade_order (
  id                bigint(20)      not null auto_increment    comment 'id',
  order_id         varchar(20)      not null comment 'orderId',
  price            varchar(15)     not null       comment '成交价',
  qty              varchar(15)     not null   comment '成交量',
  quote_qty         varchar(15)     default null           comment '成交额',
  realized_pnl      varchar(15)     default null                   comment '实现盈亏',
  side             varchar(15)     not null   comment '买卖方向',
  position_side     varchar(15)     not null             comment '持仓方向',
  symbol           varchar(15)     not null        comment '交易对',
  time             varchar(15)     not null         comment '时间',
  robot_id          bigint(20)      not null                   comment '机器人',
  primary key (id)
) engine=innodb comment = '成交订单';


delete from sys_dict_type where dict_id >=1000;
-- 字典
insert into sys_dict_type values(1000,  '交易所平台', 'udef_dict_platform',      '0', '0', 'admin', sysdate(), '', null, '交易所平台');
insert into sys_dict_type values(1001,  '交易所平台', 'udef_dict_margin',      '0', '0', 'admin', sysdate(), '', null, '保证金模式');
insert into sys_dict_type values(1002,  '机器人状态', 'udef_robot_status',      '0', '0', 'admin', sysdate(), '', null, '机器人状态');
insert into sys_dict_type values(1003,  '计划类型',   'udef_escape_type',      '0', '0', 'admin', sysdate(), '', null, '计划类型');
insert into sys_dict_type values(1004,  '优先级', 'udef_schedule_priority',      '0', '0', 'admin', sysdate(), '', null, '计划优先级');
insert into sys_dict_type values(1005,  '开仓方向', 'udef_open_side',      '0', '0', 'admin', sysdate(), '', null, '计划优先级');
insert into sys_dict_type values(1006,  '运行仓位', 'udef_position_status',      '0', '0', 'admin', sysdate(), '', null, '计划优先级');
insert into sys_dict_type values(1009,  '执行策略', 'udef_robot_strategy',      '0', '0', 'admin', sysdate(), '', null, '执行策略');

-- 字典数据
-- dict_code        bigint(20)      not null auto_increment    comment '字典编码',
--  dict_sort        int(4)          default 0                  comment '字典排序',
--  dict_label       varchar(100)    default ''                 comment '字典标签',
--  dict_value       varchar(100)    default ''                 comment '字典键值',
--  dict_type        varchar(100)    default ''                 comment '字典类型',
--  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
--  list_class       varchar(100)    default null               comment '表格回显样式',
--  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
--  status           char(1)         default '0'                comment '状态（0正常 1停用）',
--  create_by        varchar(64)     default ''                 comment '创建者',
--  create_time      datetime                                   comment '创建时间',
--  update_by        varchar(64)     default ''                 comment '更新者',
--  update_time      datetime                                   comment '更新时间',
--  remark           varchar(500)    default null               comment '备注',

delete from sys_dict_data where dict_code >=1000;

insert into sys_dict_data values(1000,  1,  '币安',      'binance',       'udef_dict_platform',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1001,  2,  '欧易',      'okex',       'udef_dict_platform',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

insert into sys_dict_data values(1002,  1,  '逐仓',      'ISOLATED',       'udef_dict_margin',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1003,  2,  '全仓',      'CROSSED',       'udef_dict_margin',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

insert into sys_dict_data values(1004,  1,  '初始化',     'init',         'udef_robot_status',       '',   '',     'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1005,  2,  '启动',      'running',       'udef_robot_status',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1006,  3,  '停止',      'stopped',       'udef_robot_status',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

insert into sys_dict_data values(1007,  1,  '限价单',      'LIMIT',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1008,  2,  '市价单',      'MARKET',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1009,  3,  '止损单',      'STOP',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1010,  4,  '止损市价单',      'STOP_MARKET',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1011,  5,  '止盈单',      'TAKE_PROFIT',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1012,  6,  '止盈暑市价单',      'TAKE_PROFIT_MARKET',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1013,  7,  '跟踪止损市价单',      'TRAILING_STOP_MARKET',       'udef_escape_type',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');


insert into sys_dict_data values(1014,  1,  '1',      '1',       'udef_schedule_priority',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1015,  2,  '2',      '2',       'udef_schedule_priority',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1016,  3,  '3',      '3',       'udef_schedule_priority',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1017,  4,  '4',      '4',       'udef_schedule_priority',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1018,  5,  '5',      '5',       'udef_schedule_priority',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

insert into sys_dict_data values(1019,  1,  '开多',      'LONG',       'udef_open_side',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1020,  2,  '开空',      'SHORT',      'udef_open_side',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

insert into sys_dict_data values(1021,  1,  '运行中',      'running',      'udef_position_status',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1022,  2,  '已关闭',      'closed',      'udef_position_status',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

insert into sys_dict_data values(1026,  1,  'EMA_KDJ双叉',      'ema_kdj_dbcross',      'udef_robot_strategy',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1027,  2,  'EMA金叉死叉',      'ema_cross',      'udef_robot_strategy',       '',   '',     'N', '0', 'admin', sysdate(), '', null, '');

