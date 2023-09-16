package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PackPrepayReq {

    /**
     * 套餐id
     */
    private Long packId;

    /**
     * 使用人id
     */
    private Long userId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;


    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 使用卡券
     */
    private Long couponId;

}
