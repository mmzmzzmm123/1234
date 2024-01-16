package com.onethinker.user.factory.service;

import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;

/**
 * 平台用户Service接口
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
public interface IPlatformUserService {

    /**
     * 用户登录
     * @param reqDTO
     * @return
     */
    PlatformUserResDTO login(PlatformUserReqDTO reqDTO);

    /**
     * 注册
     */
    void register(PlatformUserReqDTO reqDTO);

    /**
     * 忘记密码，获取验证码
     * @param place
     * @param flag
     */
    void getCodeForForgetPassword(String place, String flag);
}
