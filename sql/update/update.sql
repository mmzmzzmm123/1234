ALTER TABLE psy_consult_work ADD COLUMN consult_type CHAR(4) NOT NULL DEFAULT '1' COMMENT '1 咨询 2倾听 3督导'  AFTER consult_id;
update psy_consult_work set consult_type = '1' where consult_type is null;

ALTER TABLE psy_consult_order ADD COLUMN consult_type CHAR(4) NOT NULL DEFAULT '1' COMMENT '1 咨询 2倾听 3督导' AFTER nick_name;