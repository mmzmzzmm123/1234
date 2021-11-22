package com.ruoyi.quartz.task;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.bookmark.domain.SqBackupBookmark;
import com.ruoyi.bookmark.service.ISqBackupBookmarkLogService;
import com.ruoyi.bookmark.service.ISqBackupBookmarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

/**
 * @Auther: Wang
 * @Date: 2021/11/22 23:09
 * 功能描述:
 */
@Component("backupBookmark")
public class BackupBookmark {
    private static final Logger log = LoggerFactory.getLogger(BackupBookmark.class);


    @Autowired
    private ISqBackupBookmarkLogService sqBackupBookmarkLog;

    @Autowired
    private ISqBackupBookmarkService sqBackupBookmark;

    public void backupBookmarkByUserList()
    {
        log.info(DateUtil.now()+"开始执行>>>backupBookmarkByUserList 备份");


        log.info(DateUtil.now()+"执行完毕>>>backupBookmarkByUserList 备份");
    }
}
