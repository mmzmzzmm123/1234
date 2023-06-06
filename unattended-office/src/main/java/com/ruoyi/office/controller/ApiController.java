package com.ruoyi.office.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.GetRoomPriceVo;
import com.ruoyi.office.domain.vo.WxPayCallback;
import com.ruoyi.office.service.ITRoomOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/office/api")
public class ApiController {

    @Autowired
    ITRoomOrderService roomOrderService;

    /**
     * 预定成功 api 回调
     */
    @ApiOperation("预定成功 api 回调")
    @Log(title = "预定成功 api 回调", businessType = BusinessType.INSERT)
    @PostMapping("/order")
    public AjaxResult order(@RequestBody WxPayCallback callback) {
        return AjaxResult.success(roomOrderService.orderRoomWxCallback(callback));
    }


}
