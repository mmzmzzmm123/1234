package com.ruoyi.office.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.GetRoomPriceVo;
import com.ruoyi.office.domain.vo.WxPayCallback;
import com.ruoyi.office.service.ITRoomOrderService;
import com.ruoyi.office.service.ITWxUserService;
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
    @Log(title = "预定成功 api 回调")
    @PostMapping("/order")
    public AjaxResult order(@RequestBody WxPayCallback callback) {
        AjaxResult result = null;
        String res = roomOrderService.orderRoomWxCallback(callback, SecurityUtils.getLoginUser().getWxUser());
        if(res!="SUCCESS"){
            String[] errs = res.split(":");
            result.put("message",errs[1]);
            result.error(errs[0]);
        }
        return AjaxResult.success(res);
    }




}
