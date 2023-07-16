package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TWxUserCoupon;
import lombok.Data;

@Data
public class WxUserCouponReq  {

    private String storeName;

    private Long storeId;

    private String couponType;

}
