package com.ruoyi.common.enums;

import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 11:49
 */
public enum ActivityTypeEnum {
    /**
     * 扫码领红包
     */
    RED_ENVELOPE(1, "扫码领红包"),

    /**
     * 签到赢好礼
     */
    SIGN_IN(2, "签到赢好礼"),

    /**
     * 积分抽奖
     */
    POINT_LOTTERY(3, "积分抽奖"),

    /**
     * 押大小
     */
    BET_SIZE(4, "押大小");
    Integer code;
    String msg;

    ActivityTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SysConfigKeyEnum getActivityTypeEnumByCode(Integer code) {
        for (SysConfigKeyEnum value : SysConfigKeyEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public static void existsActivityTypeEnumByCode(Integer code) {
        for (SysConfigKeyEnum value : SysConfigKeyEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return;
            }
        }
        throw new RuntimeException("活动类型有误");
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
