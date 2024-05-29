package com.onethinker.user.platform;

import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
public interface UserStorage {

    /**
     * 用户注册 保存用户信息
     * @param reqDTO 用户登录信息
     */
    void register(PlatformUserReqDTO reqDTO);

    /**
     * 用户登录 获取token
     * @param userReqDTO 用户登录
     * @return 用户信息
     */
    PlatformUserResDTO login(PlatformUserReqDTO userReqDTO);
}
