package com.ruoyi.common.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * mq延时消费等级，mq不知道精准时间设置
 *
 * @author gideon
 * @date 2022/10/13
 */
@Slf4j
@Getter
public enum MqDelayLevelEnums {

    /**
     * 1s(一秒)
     */
    level_1(1,1),

    /**
     * 5s（五秒）
     */
    level_2(2,5),

    /**
     * 10s（十秒）
     */
    level_3(3,10),

    /**
     * 30s（三十秒）
     */
    level_4(4,30),

    /**
     * 1m（一分钟）
     */
    level_5(5,60),

    /**
     * 2m（两分钟）
     */
    level_6(6, 120),

    /**
     * 3m（三分钟）
     */
    level_7(7, 180),

    /**
     * 4m（四分种）
     */
    level_8(8, 240),

    /**
     * 5m（五分钟）
     */
    level_9(9, 300),

    /**
     * 6m（六分钟）
     */
    level_10(10, 360),

    /**
     * 7m（七分钟）
     */
    level_11(11, 420),

    /**
     * 8m（八分钟）
     */
    level_12(12, 480),

    /**
     * 9m（九分钟）
     */
    level_13(13, 540),

    /**
     * 10m（十分钟）
     */
    level_14(14, 600),

    /**
     * 20m（二十分钟）
     */
    level_15(15, 1200),

    /**
     * 30m（三十分钟）
     */
    level_16(16, 1800),

    /**
     * 1h（一小时）
     */
    level_17(17, 3600),

    /**
     * 2h（两小时）
     */
    level_18(18, 7200),

    /**
     * 1d（一天）
     */
    level_19(19, 86400),

    /**
     * 3d（三天）
     */
    level_20(20, 259200),

    /**
     * 5d（五天）
     */
    level_21(21, 432000),

    /**
     * 7d（七天）
     */
    level_22(22, 604800),
    ;

    private final int delayLevel;
    private final long second;

    MqDelayLevelEnums(int delayLevel, long second) {
        this.delayLevel = delayLevel;
        this.second = second;
    }

    public static MqDelayLevelEnums getByDelayLevel(int delayLevel) {
        for (MqDelayLevelEnums value : MqDelayLevelEnums.values()) {
            if (value.delayLevel == delayLevel) {
                return value;
            }
        }
        return null;
    }


}
