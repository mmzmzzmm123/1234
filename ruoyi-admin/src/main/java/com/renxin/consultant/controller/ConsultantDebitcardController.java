package com.renxin.consultant.controller;

import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.psychology.domain.PsyConsultantDebitcard;
import com.renxin.psychology.service.IPsyConsultantDebitcardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consultant/debitcard")
@Api(value = "ConsultantDebitcardController" ,tags = {"咨询师银行卡管理Api"})
public class ConsultantDebitcardController extends BaseController {

    @Resource
    private IPsyConsultantDebitcardService psyConsultantDebitcardService;

    @ApiOperation(value = "查询客户银行卡列表")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantDebitcard psyConsultantDebitcard)
    {
        startPage();
        List<PsyConsultantDebitcard> list = psyConsultantDebitcardService.selectPsyConsultantDebitcardList(psyConsultantDebitcard);
        return getDataTable(list);
    }

    @ApiOperation(value = "获取客户银行卡详细信息")
    @GetMapping(value = "/{cardNumber}")
    public AjaxResult getInfo(@PathVariable("cardNumber") String cardNumber)
    {
        return AjaxResult.success(psyConsultantDebitcardService.selectPsyConsultantDebitcardByCardNumber(cardNumber));
    }

    @ApiOperation(value = "新增客户银行卡")
    @PostMapping(value = "/create")
    public AjaxResult add(@RequestBody PsyConsultantDebitcard psyConsultantDebitcard)
    {
        return toAjax(psyConsultantDebitcardService.insertPsyConsultantDebitcard(psyConsultantDebitcard));
    }

    @ApiOperation(value = "修改客户银行卡")
    @PostMapping(value = "/edit")
    public AjaxResult edit(@RequestBody PsyConsultantDebitcard psyConsultantDebitcard)
    {
        return toAjax(psyConsultantDebitcardService.updatePsyConsultantDebitcard(psyConsultantDebitcard));
    }

    @ApiOperation(value = "删除客户银行卡")
    @PostMapping("/{cardNumbers}")
    public AjaxResult remove(@PathVariable String[] cardNumbers)
    {
        return toAjax(psyConsultantDebitcardService.deletePsyConsultantDebitcardByCardNumbers(cardNumbers));
    }
}
