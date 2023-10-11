package com.ruoyi.office.domain.vo;

import com.ruoyi.common.core.domain.entity.SysUser;
import lombok.Data;

@Data
public class StoreUserVo extends SysUser {
    /**
     * StoreUser.id
     */
    private Long id;

    private Long storeId;

    private String storeName;

    private String roleName;

    private String password;
}
