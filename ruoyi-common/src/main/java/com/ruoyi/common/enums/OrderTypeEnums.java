package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/5 21:35
 */
@Getter
public enum OrderTypeEnums {

    APPOINT("0","指定订单"),
    RANDOM("1", "随机订单"),
    GIFT("2", "礼物订单"),
    REWARD("3", "打赏订单"),
    RECHARGE("4", "充值订单");

    private String code;
    private String desc;

    OrderTypeEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取对象
     *
     * @param code 编码
     * @return 结果
     * */
    public static OrderTypeEnums getByCode(String code){
        for (OrderTypeEnums temp : OrderTypeEnums.values()){
            if (temp.getCode().equals(code)){
                return temp;
            }
        }
        return null;
    }
}
