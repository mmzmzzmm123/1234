package com.ruoyi.onethinker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.service.impl.PlatformUserSourceTypeAccountServiceImpl;
import com.ruoyi.onethinker.service.impl.PlatformUserSourceTypeWxServiceImpl;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 22:15
 * 实例化工厂
 */
@Component
public class PlatformUserFactory {
    @Autowired
    private PlatformUserSourceTypeWxServiceImpl sourceTypeWxService;

    @Autowired
    private PlatformUserSourceTypeAccountServiceImpl sourceTypeAccountService;
    public IPlatformUserService queryPlatformUserServiceBySourceType(String sourceType) {
        if (PlatformUserReqDTO.SOURCE_TYPE_WX.equals(sourceType)) {
            return sourceTypeWxService;
        } else if (PlatformUserReqDTO.SOURCE_TYPE_ACCOUNT.equals(sourceType)){
            return sourceTypeAccountService;
        } else {
            throw new RuntimeException("来源【" + sourceType + "】有误");
        }
    }
}
