package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/10 2:48
 */
@Getter
public enum OrderStateEnums {

    /**
     *
     */
    CANCEL("-1", "已取消"),
    FINISH("0", "已完成"),
    WAIT_PAY("1", "待支付"),
    PAID("2", "已支付/待接单"),
    WAIT_SERVICE("3", "待服务"),
    SERVICE_ING("4", "服务中"),
    WAIT_COMMENT("5", "待评价"),
    REFUNDED("999","已退款"),
    REFUNDING("888","退款中");

    private String code;
    private String desc;

    OrderStateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取对应的枚举类型
     *
     * @param code 编码
     * @return 结果
     * */
    public static OrderStateEnums getByCode(String code){
        for (OrderStateEnums temp : OrderStateEnums.values()){
            if (temp.getCode().equals(code)){
                return temp;
            }
        }
        return null;
    }
}
