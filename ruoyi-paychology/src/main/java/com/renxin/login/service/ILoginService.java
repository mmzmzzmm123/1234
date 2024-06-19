package com.renxin.login.service;

import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.domain.dto.LoginDTO;

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
    AjaxResult verifyCode(LoginDTO loginDTO);

}
