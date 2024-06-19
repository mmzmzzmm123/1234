ALTER TABLE psy_consult_work ADD COLUMN consult_type CHAR(255) NOT NULL DEFAULT '1' COMMENT '1 咨询 2倾听 3督导';
update psy_consult_work set consult_type = '1' where consult_type is null;