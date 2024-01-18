package com.onethinker.user.factory;

import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.factory.service.IPlatformUserService;
import com.onethinker.user.factory.service.impl.PlatformUserSourceTypeAccountServiceImpl;
import com.onethinker.user.factory.service.impl.PlatformUserSourceTypeWxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 22:15
 * 实例化工厂: 根据用户来源不同实例化不同处理
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
        } else if (PlatformUserReqDTO.SOURCE_TYPE_ACCOUNT.equals(sourceType)) {
            return sourceTypeAccountService;
        } else {
            throw new RuntimeException("来源【" + sourceType + "】有误");
        }
    }
}
