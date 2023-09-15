package com.ruoyi.office.domain.vo;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.office.domain.TRoom;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantRoomListVo {
    private TRoom room;
    private Long sumHour;
    private BigDecimal sumAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date orderEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date nextOrderStart;
}
