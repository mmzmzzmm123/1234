package com.stdiet.custom.utils;

import com.stdiet.custom.domain.SysOrder;

public class OrderUtils {

    private static final String[] orderTypeArray = {"普通单","比例拆分单","体验单"};

    private static final String[] orderCountTypeArray = {"一开单","二开单"};

    private static final String[] orderMoneyTypeArray = {"全款单","定金单","尾款单"};

    /**
     * 根据订单对象获取订单类型名称
     * @param sysOrder
     * @return
     */
    public static final String getOrderTypeName(SysOrder sysOrder){
        String orderTypeName = "";
        orderTypeName += orderTypeArray[Integer.parseInt(sysOrder.getOrderType())];
        orderTypeName += "/"+orderCountTypeArray[Integer.parseInt(sysOrder.getOrderCountType())];
        orderTypeName += "/"+orderMoneyTypeArray[Integer.parseInt(sysOrder.getOrderMoneyType())];
        return orderTypeName;
    }

}
