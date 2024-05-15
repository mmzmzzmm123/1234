package com.onethinker.bk.enums;

import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2024/1/18 14:53
 */
public enum SortTypeEnum {
    /**
     * 分类类型
     */
    SORT_TYPE_BAR(0, "导航栏分类"),

    /**
     * 普通分类
     */
    SORT_TYPE_NORMAL(1, "普通分类");


    int code;
    String msg;

    SortTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SortTypeEnum getSysConfigKeyEnumByCode(String code) {
        for (SortTypeEnum value : SortTypeEnum.values()) {
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
