package com.ruoyi.common.enums;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 16:27
 * @Description : 商家类型
 */
public enum MerchantType {

    ORDINARY(0,"普通")
    ,AGENT(1,"代理")
    ,OPERATE(2,"运营")

    ;

    private int type;

    private String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    MerchantType(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
