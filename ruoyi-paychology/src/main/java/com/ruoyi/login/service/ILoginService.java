package com.ruoyi.login.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;

/**
 * @User hogan
 * @Time 2022/10/18 15:58
 * @e-mail hkcugwh@163.com
 **/
public interface ILoginService {

    /**
     * 获取短信验证码
     * @param loginDTO
     * @return
     */
    AjaxResult getSmsCode(LoginDTO loginDTO);

    /**
     * 校验验证码
     * @param loginDTO
     */
    boolean verifyCode(LoginDTO loginDTO);

    /**
     * 登录
     * @param loginDTO
     * @return
     */
    String login(LoginDTO loginDTO);

}
