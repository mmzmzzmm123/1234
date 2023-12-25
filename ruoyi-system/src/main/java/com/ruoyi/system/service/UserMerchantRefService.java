package com.ruoyi.system.service;

import com.ruoyi.system.domain.UserMerchantRef;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.dto.CreateUserMerchantRefDTO;

/**
 *
 */
public interface UserMerchantRefService extends IService<UserMerchantRef> {

    void createUserMerchantRef(CreateUserMerchantRefDTO dto);

}
