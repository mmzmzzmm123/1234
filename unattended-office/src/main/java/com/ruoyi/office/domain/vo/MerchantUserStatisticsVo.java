package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.office.domain.TWxUser;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantUserStatisticsVo {
    private Integer count;
    private BigDecimal amount;
    private Integer hours;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date lastestDate;

    private TWxUser wxUser;
}
