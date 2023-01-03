package com.ruoyi.push.task;

import com.ruoyi.push.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author lixin
 * @Date 2022/12/30 14:06
 */

@Component("PushTask")
public class PushTask {
    @Autowired
    private IPushService pushService;

    public void cleanHistory() {
        System.out.println("--清理缓存消息--");
        pushService.cleanExpirationAll();
    }
}
