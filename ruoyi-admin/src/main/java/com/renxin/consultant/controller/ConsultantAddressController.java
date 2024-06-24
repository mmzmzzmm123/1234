package com.renxin.consultant.controller;

import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.psychology.domain.PsyConsultantAddress;
import com.renxin.psychology.service.IPsyConsultantAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consultant/address")
@Api(value = "ConsultantAddressController" ,tags = {"咨询师地址管理Api"})
public class ConsultantAddressController extends BaseController {

    @Resource
    private IPsyConsultantAddressService psyConsultantAddressService;

    @ApiOperation(value = "查询咨询师地址列表")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantAddress psyConsultantAddress)
    {
        startPage();
        List<PsyConsultantAddress> list = psyConsultantAddressService.selectPsyConsultantAddressList(psyConsultantAddress);
        return getDataTable(list);
    }

    @ApiOperation(value = "获取咨询师地址详细信息")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId)
    {
        return AjaxResult.success(psyConsultantAddressService.selectPsyConsultantAddressByAddressId(addressId));
    }

    @ApiOperation(value = "新增咨询师地址")
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultantAddress psyConsultantAddress)
    {
        return toAjax(psyConsultantAddressService.insertPsyConsultantAddress(psyConsultantAddress));
    }

    @ApiOperation(value = "修改咨询师地址")
    @PostMapping
    public AjaxResult edit(@RequestBody PsyConsultantAddress psyConsultantAddress)
    {
        return toAjax(psyConsultantAddressService.updatePsyConsultantAddress(psyConsultantAddress));
    }

    /**
     * 删除咨询师地址
     */
    @ApiOperation(value = "删除咨询师地址")
    @PostMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds)
    {
        return toAjax(psyConsultantAddressService.deletePsyConsultantAddressByAddressIds(addressIds));
    }

}
