package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MerchantOrderChangeTimeReq {
    private Long orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
}
