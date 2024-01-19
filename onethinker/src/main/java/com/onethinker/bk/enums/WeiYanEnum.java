package com.onethinker.bk.enums;

import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2024/1/19 10:00
 */
public enum WeiYanEnum {
    /**
     * 公开
     */
    PUBLIC(1, "所有人可见"),

    /**
     * 仅自己可见
     */
    PRIVATE(0, "仅自己可见");

    int code;
    String msg;

    WeiYanEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static WeiYanEnum getSysConfigKeyEnumByCode(String code) {
        for (WeiYanEnum value : WeiYanEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
