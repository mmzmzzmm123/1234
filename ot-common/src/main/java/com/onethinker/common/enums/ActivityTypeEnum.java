package com.onethinker.common.enums;

import java.util.Objects;

/**
 * @author yangyouqi
 * @date : 2023/11/3 0003 11:49
 */
public enum ActivityTypeEnum {

    /**
     * 扬万科技
     */
    YWKJ(0, "扬万科技"),
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
    BET_SIZE(4, "押大小"),
    ;
    Integer code;
    String msg;

    ActivityTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ActivityTypeEnum getActivityTypeEnumByCode(Integer code) {
        for (ActivityTypeEnum value : ActivityTypeEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public static ActivityTypeEnum existsActivityTypeEnumByCode(Integer code) {
        for (ActivityTypeEnum value : ActivityTypeEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
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
