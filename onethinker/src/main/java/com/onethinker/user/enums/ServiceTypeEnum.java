package com.onethinker.user.enums;

import com.ruoyi.common.enums.SysConfigKeyEnum;

import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2024/1/16 15:33
 */
public enum ServiceTypeEnum {

    /**
     * 博客业务
     */
    BK("bk","博客业务");

    String code;
    String msg;

    ServiceTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ServiceTypeEnum getSysConfigKeyEnumByCode(String code) {
        for (ServiceTypeEnum value : ServiceTypeEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
