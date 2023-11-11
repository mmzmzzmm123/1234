package com.ruoyi.api.payment.controller;

import com.ruoyi.api.payment.model.dto.ApiUserRechargeDto;
import com.ruoyi.api.payment.model.vo.ApiOrderPayInfoVo;
import com.ruoyi.api.payment.service.ApiPaymentService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LAM
 * @date 2023/10/5 21:13
 */
@Anonymous
@Api("api-支付相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class ApiPaymentController extends BaseController {

    private final ApiPaymentService service;
    private final PaymentOrderMapper paymentOrderMapper;

    @ApiOperation("充值金额")
    @PostMapping("/recharge")
    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    public R<ApiOrderPayInfoVo> recharge(ApiUserRechargeDto dto){
        return service.recharge(dto);
    }

    @ApiOperation("查询打赏支付单body数据")
    @GetMapping("/selectPaymentOrderBody")
    public R<List<String>> selectPaymentOrderBody(){
        startPage();
        return R.ok(paymentOrderMapper.selectBody());
    }
}
