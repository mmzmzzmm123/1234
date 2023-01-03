package com.ruoyi.web.controller.message;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.message.domain.MessageSiteRecords;
import com.ruoyi.message.service.IMessageSiteRecordsService;
import com.ruoyi.push.service.IPushService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * 站内信消息Controller
 *
 * @author lixin
 * @date 2022-12-24
 */
@RestController
@RequestMapping("/message/siteRecords")
public class MessageSiteRecordsController extends BaseController {
    @Autowired
    private IMessageSiteRecordsService messageSiteRecordsService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IPushService pushService;

    /**
     * 查询站内信消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:siteRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(MessageSiteRecords messageSiteRecords) {
        startPage();
        List<MessageSiteRecords> list = messageSiteRecordsService.selectMessageSiteRecordsList(messageSiteRecords);
        return getDataTable(list);
    }

    /**
     * 导出站内信消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:siteRecords:export')")
    @Log(title = "站内信消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MessageSiteRecords messageSiteRecords) {
        List<MessageSiteRecords> list = messageSiteRecordsService.selectMessageSiteRecordsList(messageSiteRecords);
        ExcelUtil<MessageSiteRecords> util = new ExcelUtil<MessageSiteRecords>(MessageSiteRecords.class);
        util.exportExcel(response, list, "站内信消息数据");
    }

    /**
     * 获取站内信消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:siteRecords:query')")
    @GetMapping(value = "/{msgSiteId}")
    public AjaxResult getInfo(@PathVariable("msgSiteId") Long msgSiteId) {
        return success(messageSiteRecordsService.selectMessageSiteRecordsByMsgSiteId(msgSiteId));
    }

    /**
     * 新增站内信消息
     */
    @PreAuthorize("@ss.hasPermi('message:siteRecords:add')")
    @Log(title = "站内信消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageSiteRecords messageSiteRecords) {

        SysUser sysUser = userService.selectUserById(messageSiteRecords.getToUserId());
        if (sysUser == null) {
            return error("接受用户不存在");
        }
        LoginUser loginUser = getLoginUser();
        messageSiteRecords.setToUserName(sysUser.getNickName());
        messageSiteRecords.setSendUserId(loginUser.getUserId());
        messageSiteRecords.setSendUserName(loginUser.getNickName());
        messageSiteRecords.setHasRead("0");
        messageSiteRecords.setCreateTime(DateUtils.getNowDate());
        int count = messageSiteRecordsService.insertMessageSiteRecords(messageSiteRecords);
        if (count > 0) {
            // 发送推送消息
            pushService.addPullSitemsg(sysUser.getUserId(), messageSiteRecords);
        }
        return toAjax(count);
    }

    /**
     * 删除站内信消息
     */
    @PreAuthorize("@ss.hasPermi('message:siteRecords:remove')")
    @Log(title = "站内信消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{msgSiteIds}")
    public AjaxResult remove(@PathVariable Long[] msgSiteIds) {
        Set<Long> userids = messageSiteRecordsService.deleteMessageSiteRecordsByMsgSiteIds(msgSiteIds);
        if (userids.size() > 0) {
            for (Long userid : userids) {
                // 更新读取消息
                pushService.addPullSitecount(userid);
            }
        }
        return toAjax(userids.size() > 0 ? msgSiteIds.length : 0);
    }


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
