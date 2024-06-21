package com.renxin.app.controller.psychology;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.renxin.psychology.domain.PsyConsultantAddress;
import com.renxin.psychology.service.IPsyConsultantAddressService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.common.core.page.TableDataInfo;

/**
 * 咨询师地址Controller
 * 
 * @author renxin
 * @date 2024-06-20
 */
@RestController
@RequestMapping("/finance/address")
public class PsyConsultantAddressController extends BaseController
{
    @Autowired
    private IPsyConsultantAddressService psyConsultantAddressService;

    /**
     * 查询咨询师地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantAddress psyConsultantAddress)
    {
        startPage();
        List<PsyConsultantAddress> list = psyConsultantAddressService.selectPsyConsultantAddressList(psyConsultantAddress);
        return getDataTable(list);
    }

    /**
     * 导出咨询师地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:export')")
    @Log(title = "咨询师地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultantAddress psyConsultantAddress)
    {
        List<PsyConsultantAddress> list = psyConsultantAddressService.selectPsyConsultantAddressList(psyConsultantAddress);
        ExcelUtil<PsyConsultantAddress> util = new ExcelUtil<PsyConsultantAddress>(PsyConsultantAddress.class);
        util.exportExcel(response, list, "咨询师地址数据");
    }

    /**
     * 获取咨询师地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:query')")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId)
    {
        return AjaxResult.success(psyConsultantAddressService.selectPsyConsultantAddressByAddressId(addressId));
    }

    /**
     * 新增咨询师地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:add')")
    @Log(title = "咨询师地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultantAddress psyConsultantAddress)
    {
        return toAjax(psyConsultantAddressService.insertPsyConsultantAddress(psyConsultantAddress));
    }

    /**
     * 修改咨询师地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:edit')")
    @Log(title = "咨询师地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultantAddress psyConsultantAddress)
    {
        return toAjax(psyConsultantAddressService.updatePsyConsultantAddress(psyConsultantAddress));
    }

    /**
     * 删除咨询师地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:remove')")
    @Log(title = "咨询师地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds)
    {
        return toAjax(psyConsultantAddressService.deletePsyConsultantAddressByAddressIds(addressIds));
    }
}
