package com.ruoyi.office.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WxUserCouponQryReq extends WxUserCouponReq{


    private Long userId;

    private Date endDate;

}
