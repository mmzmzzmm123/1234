package com.onethinker.user.platform;

import com.onethinker.common.enums.CacheEnum;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
public interface UserStorage {

    /**
     * 用户注册 保存用户信息
     */
    public PlatformUserResDTO register(PlatformUserReqDTO reqDTO);

    /**
     * 用户登录 获取token
     */
    public PlatformUserResDTO login(PlatformUserReqDTO userReqDTO);
}
