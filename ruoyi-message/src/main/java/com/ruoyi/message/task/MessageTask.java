package com.ruoyi.message.task;

import com.ruoyi.message.service.IMessageSiteRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author lixin
 * @Date 2022/12/27 10:29
 */

@Component("messageTask")
public class MessageTask {
    @Autowired
    private IMessageSiteRecordsService messageSiteRecordsService;

    public void cleanHistory() {
        messageSiteRecordsService.deleteHistory();
    }
}
