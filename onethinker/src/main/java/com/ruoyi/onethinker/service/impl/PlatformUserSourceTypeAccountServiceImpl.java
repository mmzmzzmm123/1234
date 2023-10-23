package com.ruoyi.onethinker.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserResDTO;
import com.ruoyi.onethinker.service.IPlatformUserService;

/**
 * 平台用户Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Service
public class PlatformUserSourceTypeAccountServiceImpl implements IPlatformUserService {

    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeAccountServiceImpl.class);

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("账户密码登录");
        return null;
    }
}
