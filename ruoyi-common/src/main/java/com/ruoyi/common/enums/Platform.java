package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * 平台类型
 *
 * @author Jing.Zhang
 */
@Getter
public enum Platform {

    TELEGRAM("TELEGRAM", 0);

    /**
     * 平台名称
     */
    private final String platformName;

    /**
     * 平台类型
     */
    private final Integer platformType;

    Platform(String platformName, Integer platformType) {
        this.platformName = platformName;
        this.platformType = platformType;
    }
}
