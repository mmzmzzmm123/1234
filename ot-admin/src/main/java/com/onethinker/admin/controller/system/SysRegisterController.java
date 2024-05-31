package com.onethinker.admin.controller.system;

import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.domain.model.RegisterBody;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.framework.web.service.SysRegisterService;
import com.onethinker.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author yangyouqi
 */
@RestController
public class SysRegisterController extends BaseController {
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping( "/register")
    public AjaxResult register(@RequestBody RegisterBody user) {
        if (!Boolean.TRUE.toString().equals(configService.selectConfigByKey(SysConfigKeyEnum.SYS_ACCOUNT_REGISTERUSER))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
