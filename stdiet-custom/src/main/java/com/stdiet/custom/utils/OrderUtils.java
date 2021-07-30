package com.stdiet.custom.utils;

import com.stdiet.custom.domain.SysOrder;

public class OrderUtils {

    private static final String[] orderTypeArray = {"普通单","比例拆分单","体验单","售中单","商务单","基因单"};

    private static final String[] orderCountTypeArray = {"一开单","二开单","一开单(拆分)","二开单(拆分)"};

    private static final String[] orderMoneyTypeArray = {"全款单","定金单","尾款单"};

    private static final String[] orderMainTypeArray = {"主单","副单"};

    /**
     * 根据订单对象获取订单类型名称
     * @param sysOrder
     * @return
     */
    public static final String getOrderTypeName(SysOrder sysOrder){
        String orderTypeName = "";
        orderTypeName += orderTypeArray[Integer.parseInt(sysOrder.getOrderType())];
        //比例拆分单需要确认出主单副单
        /*if("1".equals(sysOrder.getOrderType())){
            orderTypeName += "("+(sysOrder.getMainOrderId().intValue() == 0 ? orderMainTypeArray[0] : orderMainTypeArray[1])+")";
        }*/
        orderTypeName += "/"+orderCountTypeArray[Integer.parseInt(sysOrder.getOrderCountType())];
        orderTypeName += "/"+orderMoneyTypeArray[Integer.parseInt(sysOrder.getOrderMoneyType())];
        //判断是不是二开提成单
        if(sysOrder.getAfterSaleCommissOrder() != null && sysOrder.getAfterSaleCommissOrder().intValue() == 1){
            orderTypeName += "/提成单";
        }
        return orderTypeName;
    }

}
