package com.xinyu.idol.web.controller.system;

import com.xinyu.idol.common.core.controller.BaseController;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.core.domain.model.RegisterBody;
import com.xinyu.idol.common.utils.StringUtils;
import com.xinyu.idol.framework.web.service.SysRegisterService;
import com.xinyu.idol.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}