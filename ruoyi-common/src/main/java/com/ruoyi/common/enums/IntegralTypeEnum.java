package com.ruoyi.common.enums;

import org.apache.poi.hssf.record.cf.Threshold;

/**
 * @author : yangyouqi
 * @date : 2023/10/27 0027 21:05
 */
public enum IntegralTypeEnum {
    //    1:充值 2:活动获得奖励 3：参与活动扣除门槛 4：提现
    RECHARGE(1, "充值"),
    ACTIVITY_REWARD(2, "活动获得奖励"),
    ACTIVITY_THRESHOLD_FEE(3, "活动门槛费"),
    CASH_WITHDRAWAL(4, "提现");
    Integer code;
    String msg;

    IntegralTypeEnum() {
    }

    IntegralTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
