package com.ruoyi.push.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.push.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息推送
 *
 * @Author lixin
 * @Date 2022/12/30 14:29
 */
@RequestMapping("/push")
public abstract class AbsPushController extends BaseController {
    @Autowired
    private IPushService pushService;

    /**
     * 拉去自己推送消息
     *
     * @param lastTime
     * @return
     */
    @GetMapping("/pullSelf")
    public AjaxResult pullSelf(long lastTime) {
        LoginUser loginUser = getLoginUser();
        return success(pushService.pullSelf(loginUser == null ? 0 : loginUser.getUserId(), lastTime));
    }
}
