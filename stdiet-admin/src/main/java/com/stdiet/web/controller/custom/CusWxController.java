package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.redis.RedisCache;
import com.stdiet.custom.service.ISysWxService;
import com.stdiet.custom.utils.WxTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx")
public class CusWxController extends BaseController {

    @Autowired
    public ISysWxService sysWxService;

    @Autowired
    public RedisCache redisCache;

    @GetMapping("/checkSign")
    public String wxCheckAuth(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        return sysWxService.wxCheckAuth(signature, timestamp, nonce, echostr);
    }

    @PostMapping("/checkSign")
    public String autoResponse(HttpServletRequest request) {
        return sysWxService.autoResponse(request);
    }

    @GetMapping("/accessToken")
    public AjaxResult getAccessToken() {
        return sysWxService.getAccessToken();
    }

    @GetMapping("/clearAccessToken")
    public AjaxResult clearAccessToken() {
        redisCache.deleteObject(WxTokenUtils.KEY_ACCESS_TOKEN);
        return AjaxResult.success();
    }
}
