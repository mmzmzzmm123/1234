package com.onethinker.user.platform;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onethinker.common.constant.Constants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.*;
import com.onethinker.common.exception.user.CaptchaException;
import com.onethinker.common.exception.user.CaptchaExpireException;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.MessageUtils;
import com.onethinker.common.utils.SecurityUtils;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.framework.manager.AsyncManager;
import com.onethinker.framework.manager.factory.AsyncFactory;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
