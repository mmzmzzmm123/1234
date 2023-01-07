package com.ruoyi.common.interfaces;

import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * @Author lixin
 * @Date 2022/12/14 17:24
 */
public interface ILoadIUserInfoService {

    /**
     * 加载用户信息
     *
     * @param loginUser
     */
    public void loadUserInfo(LoginUser loginUser);
}
