package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class PrepayReq {

    /**
     * 房间id
     */
    private Long roomId;

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
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 使用卡券
     */
    private Long couponId;
}
