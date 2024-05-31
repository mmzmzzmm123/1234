package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date : 2023/11/3 0003 11:49
 */
@Getter
public enum ActivityTypeEnum {

    /**
     * 扬万科技
     */
    YWKJ("扬万科技","YWKJActivityStorage"),
    /**
     * 扫码领红包
     */
    RED_ENVELOPE("扫码领红包","RedEnvelopeActivityStorage"),

    /**
     * 签到赢好礼
     */
    SIGN_IN("签到赢好礼","SignInActivityStorage"),

    /**
     * 积分抽奖
     */
    POINT_LOTTERY( "积分抽奖","PointLotteryActivityStorage"),

    /**
     * 押大小
     */
    BET_SIZE( "押大小","BetSizeActivityStorage"),
    ;
    String desc;

    String interfaceClass;

    ActivityTypeEnum(String desc,String interfaceClass) {
        this.desc = desc;
        this.interfaceClass = "com.onethinker.activity.platform.impl." + interfaceClass;
    }
}
