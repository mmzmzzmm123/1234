package com.ruoyi.common.vo;

import lombok.Data;

@Data
public class DateLimitUtilVO {
    public DateLimitUtilVO() {}
    public DateLimitUtilVO(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private String startTime; //开始时间

    private String endTime; //结束时间
}
