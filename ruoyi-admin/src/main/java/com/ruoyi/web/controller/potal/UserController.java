package com.ruoyi.web.controller.potal;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 14:14:19
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private TokenService tokenService;


    /**
     * 定期刷新用户登陆信息
     * @return
     */
    @RequestMapping("/refreshToken")
    public AjaxResult refreshToken() {
        tokenService.refreshToken(SecurityUtils.getLoginUser());
        return AjaxResult.success();
    }
}
