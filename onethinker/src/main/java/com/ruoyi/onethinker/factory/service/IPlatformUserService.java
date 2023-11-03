package com.ruoyi.onethinker.factory.service;

import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserResDTO;

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
}
