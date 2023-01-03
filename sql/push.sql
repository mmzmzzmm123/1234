-- 推送消息清理任务
insert into sys_job values(default , '推送消息清理', 'DEFAULT', 'PushTask.cleanHistory',  '0/30 * * * * ?', '3', '1', '0', '1', 'admin', sysdate(), '', null, '');
