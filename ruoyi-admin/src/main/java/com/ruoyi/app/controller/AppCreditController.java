package com.ruoyi.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.interceptor.impl.AppInterceptor;
import com.ruoyi.system.domain.model.CreditAuthorizationRecord;
import com.ruoyi.system.domain.model.FinanceProductApply;
import com.ruoyi.system.domain.model.FinanceProductQuery;
import com.ruoyi.system.domain.model.credit.DishonestOrBlack;
import com.ruoyi.system.service.IDataCompanyLoanService;
import com.ruoyi.system.service.impl.DataCreditApi;
import com.ruoyi.system.service.impl.ProdOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private IDataCompanyLoanService dataCompanyLoanService;

    @GetMapping("/blackOrDishonest")
    @ApiOperation(value = "验证是否在黑名单或失信执行人")
    public AjaxResult checkBlackOrIsDishonest(@RequestParam String creditCode) {
        DishonestOrBlack dishonestOrBlack = dataCreditApi.checkBlackOrIsDishonest(creditCode);
        return AjaxResult.success(dishonestOrBlack);
    }


    @PostMapping("/getFinanceProductList")
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

    @PostMapping("/getFinanceProductInfo")
    @ApiOperation(value = "查询金融产品详情")
    public AjaxResult getFinanceProductInfo(@RequestBody FinanceProductQuery query) {
        log.info("查询金融产品详情 param {}",query);
//        Object financeProduct = prodOpenApi.getFinanceProductInfo(query);

        return prodOpenApi.getFinanceProductInfo(query);
    }


//    @GetMapping("/getDict/{dictType}")
    @GetMapping("/getDict")
    @ApiOperation(value = "信贷直通车字典查询")
    public AjaxResult getDict(@RequestParam(name="dictType") String dictType) {
        log.info("信贷直通车字典查询 param dictType={}",dictType);

//        Object financeProductDict = prodOpenApi.getDictByDictType(dictType);

        return prodOpenApi.getDictByDictType(dictType);
    }

    @PostMapping("/financeProductApply")
    @ApiOperation(value = "金融产品申请")
    public AjaxResult financeProductApply(@RequestBody FinanceProductApply apply) {
        log.info("金融产品申请 param:{}",apply);

        Object result = prodOpenApi.financeProductApply(apply);

        return AjaxResult.success(result);
    }

    /**
     * 企业入驻--TODO 闽商通接口改造
     * @return
     */
    @PostMapping("/comUserReg")
    @ApiOperation(value = "企业入驻")
    public AjaxResult comUserReg() {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader(AppInterceptor.HEADER_MZT_USER_TOKEN);
        String userId = request.getHeader(AppInterceptor.HEADER_MZT_USER_ID);

        JSONObject jsonObject = dataCompanyLoanService.getUserInfo(userId,token);
        if (jsonObject.getBoolean("success")){
            return prodOpenApi.comUserReg(jsonObject.getJSONObject("data"));
        }else {
            return AjaxResult.error("获取用户信息失败");
        }
    }

    @GetMapping("/findAuthorization")
    @ApiOperation(value = "查询企业用户授权书")
    public AjaxResult findAuthorization() {

        return prodOpenApi.findAuthorization();
    }


    @PostMapping("/addAuthorizationRecord")
    @ApiOperation(value = "新增用户授权书同意记录")
    public AjaxResult addAuthorizationRecord(@RequestBody CreditAuthorizationRecord record) {
        log.info("新增用户授权书同意记录 param:{}",record);

        return prodOpenApi.addAuthorizationRecord(record);
    }

    @GetMapping("/isExistAuth")
    @ApiOperation(value = "首次登录授权书授权校验")
    public AjaxResult isExistAuth(@RequestParam(name="socialCode") String socialCode) {

        return prodOpenApi.isExistAuth(socialCode);
    }
}
