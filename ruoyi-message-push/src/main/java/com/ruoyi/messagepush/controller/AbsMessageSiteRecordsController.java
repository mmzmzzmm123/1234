package com.ruoyi.messagepush.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.message.domain.MessageSiteRecords;
import com.ruoyi.message.service.IMessageSiteRecordsService;
import com.ruoyi.push.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 站内信消息Controller
 *
 * @author lixin
 * @date 2022-12-24
 */
@RequestMapping("/message/siteRecords")
public abstract class AbsMessageSiteRecordsController extends BaseController {
    @Autowired
    private IMessageSiteRecordsService messageSiteRecordsService;
    @Autowired
    private IPushService pushService;

    /**
     * 拉取自己的站内信消息列表
     */
    @GetMapping("/selfPullLast")
    public AjaxResult selfPullLast(long lastId) {
        List<MessageSiteRecords> list = messageSiteRecordsService.pullLast(getUserId(), lastId);
        return success(list);
    }

    /**
     * 拉取自己的站内信消息列表(倒序)
     */
    @GetMapping("/selfPullReLast")
    public AjaxResult selfPullReLast(long lastId) {
        lastId = lastId <= 0 ? Long.MAX_VALUE : lastId;
        List<MessageSiteRecords> list = messageSiteRecordsService.pullReLast(getUserId(), lastId);
        return success(list);
    }

    /**
     * 获得自未读的消息数量
     *
     * @return
     */
    @GetMapping("/selfUnReadCount")
    public AjaxResult selfUnReadCount() {
        long unReadCount = messageSiteRecordsService.getSelfUnReadCount(getUserId());
        return success(unReadCount);
    }

    /**
     * 读取一个消息
     *
     * @param id
     * @return
     */
    @PutMapping("/selfRead/{id}")
    public AjaxResult selfRead(@PathVariable long id) {
        long userid = getUserId();
        messageSiteRecordsService.readOne(id, userid);
        // 更新读取消息
        pushService.addPullSitecount(userid);
        return success();
    }

    /**
     * 删除一个消息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/selfRemove/{id}")
    public AjaxResult selfRemove(@PathVariable long id) {
        long userid = getUserId();
        messageSiteRecordsService.removeOne(id, userid);
        // 更新读取消息
        pushService.addPullSitecount(userid);
        return success();
    }
}
