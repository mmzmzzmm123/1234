package com.ruoyi.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.model.credit.DishonestOrBlack;
import com.ruoyi.system.domain.model.credit.FinanceProductResponse;
import com.ruoyi.system.service.impl.DataCreditApi;
import com.ruoyi.system.service.impl.ProdOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 转发或组合信用三期的相关接口
 *
 * @author genius
 * @date 2021-07-13
 */
@RestController
@Api(value = "转发或组合信用三期的相关接口")
@RequestMapping("/app/credit")
public class AppCreditController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AppCreditController.class);

    @Autowired
    private DataCreditApi dataCreditApi;

    @Autowired
    private ProdOpenApi prodOpenApi;

    @GetMapping("/blackOrDishonest")
    @ApiOperation(value = "验证是否在黑名单或失信执行人")
    public AjaxResult checkBlackOrIsDishonest(@RequestParam String creditCode) {
        DishonestOrBlack dishonestOrBlack = dataCreditApi.checkBlackOrIsDishonest(creditCode);
        return AjaxResult.success(dishonestOrBlack);
    }


    @GetMapping("/getFinanceProductList")
    @ApiOperation(value = "信贷直通车金融产品列表查询")
    public AjaxResult getFinanceProductList(@RequestParam(name="pageNum") String pageNum,
                                            @RequestParam(name="pageSize") String pageSize) {
        log.info("pageNum:{}, pageSize:{}",pageNum,pageSize);

        Object financeProductList = prodOpenApi.getFinanceProductList(pageNum,pageSize);

        return AjaxResult.success(financeProductList);
    }

}
