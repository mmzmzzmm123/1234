package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RefundAuditVo {
    private Long id;
    private Long orderNo;
    private String storeName;
    private String roomName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    private String unionId;
    private String nickName;
    private Long refundOrderNo;
    private BigDecimal refundAmount;
    private Integer status;

}
