package com.ruoyi.office.domain.vo;

import com.ruoyi.common.core.domain.entity.SysUser;
import lombok.Data;

@Data
public class StoreUserVo {
    /**
     * StoreUser.id
     */
    private Long id;

    /**
     * StoreUser.userId WxUser.id
     */
    private Long userId;

    private Long storeId;

    private String storeName;

    /**
     * StoreUser.remark
     */
    private String role;

    /**
     * WxUser.nickName
     */
    private String nickName;

    /**
     * WxUser.phone
     */
    private String phone;
}
