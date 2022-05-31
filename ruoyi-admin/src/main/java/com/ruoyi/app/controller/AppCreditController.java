package com.ruoyi.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.model.ComUserRegister;
import com.ruoyi.system.domain.model.FinanceProductApply;
import com.ruoyi.system.domain.model.FinanceProductQuery;
import com.ruoyi.system.domain.model.credit.DishonestOrBlack;
import com.ruoyi.system.domain.model.credit.FinanceProductResponse;
import com.ruoyi.system.service.impl.DataCreditApi;
import com.ruoyi.system.service.impl.ProdOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public AjaxResult getFinanceProductList(@RequestBody FinanceProductQuery query) {
        Object financeProductList = prodOpenApi.getFinanceProductList(query);

        return AjaxResult.success(financeProductList);
    }


    @PostMapping("/getFinanceProductHotList")
    @ApiOperation(value = "热门产品列表查询")
    public AjaxResult getFinanceProductHotList(@RequestBody FinanceProductQuery query) {
        log.info("热门产品列表查询 param {}",query);
        Object financeProductList = prodOpenApi.getFinanceProductHotList(query);

        return AjaxResult.success(financeProductList);
    }


//    @GetMapping("/getDict/{dictType}")
    @GetMapping("/getDict")
    @ApiOperation(value = "信贷直通车字典查询")
    public AjaxResult getDict(@RequestParam(name="dictType") String dictType) {
        log.info("信贷直通车字典查询 param dictType={}",dictType);

        Object financeProductDict = prodOpenApi.getDictByDictType(dictType);

        return AjaxResult.success(financeProductDict);
    }

    @PostMapping("/financeProductApply")
    @ApiOperation(value = "金融产品申请")
    public AjaxResult financeProductApply(@RequestBody FinanceProductApply apply) {
        log.info("金融产品申请 param:{}",apply);

        Object result = prodOpenApi.financeProductApply(apply);

        return AjaxResult.success(result);
    }

    @PostMapping("/comUserReg")
    @ApiOperation(value = "企业入驻")
    public AjaxResult comUserReg(@RequestBody ComUserRegister register) {
        log.info("企业入驻 param:{}",register);

        Object result = prodOpenApi.comUserReg(register);

        return AjaxResult.success(result);
    }

}
