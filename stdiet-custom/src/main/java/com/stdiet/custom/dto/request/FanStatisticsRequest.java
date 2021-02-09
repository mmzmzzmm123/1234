package com.stdiet.custom.dto.request;

import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class FanStatisticsRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //销售用户ID
    private Long userId;

    //进粉统计日期
    private Date fanTime;

    //微信ID数组
    private Long[] wxId;

    //微信进粉量数组
    private Integer[] fanNum;

}
