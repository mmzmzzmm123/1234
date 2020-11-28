package com.stdiet.custom.controller;

import com.github.pagehelper.PageInfo;
import com.stdiet.common.constant.HttpStatus;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.custom.page.OrderTableDataInfo;

import java.math.BigDecimal;
import java.util.List;

public class OrderBaseController extends BaseController {

    protected OrderTableDataInfo getOrderDataTable(List<?> list, BigDecimal totalAmount) {
        OrderTableDataInfo rspData = new OrderTableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotalAmount(totalAmount);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
