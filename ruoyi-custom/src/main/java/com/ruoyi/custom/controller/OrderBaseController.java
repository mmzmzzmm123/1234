package com.ruoyi.custom.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.custom.page.OrderTableDataInfo;

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
