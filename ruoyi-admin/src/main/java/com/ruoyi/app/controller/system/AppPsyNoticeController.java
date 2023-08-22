package com.ruoyi.app.controller.system;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.system.service.ISysNoticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 公告 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/app/system/notice")
public class AppPsyNoticeController extends BaseController
{
    @Resource
    private ISysNoticeService noticeService;

    /**
     * 根据通知公告编号获取详细信息
     */
    @GetMapping(value = "/{noticeId}")
    @RateLimiter(limitType = LimitType.IP)
    public AjaxResult getInfo(@PathVariable Long noticeId)
    {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

}