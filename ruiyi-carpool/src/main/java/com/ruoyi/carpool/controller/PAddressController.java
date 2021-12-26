package com.ruoyi.carpool.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.carpool.domain.PAddress;
import com.ruoyi.carpool.service.IPAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 地址详情Controller
 * 
 * @author ruoyi
 * @date 2021-12-18
 */
@RestController
@RequestMapping("/carpool/address")
public class PAddressController extends BaseController
{
    @Autowired
    private IPAddressService pAddressService;

    /**
     * 查询地址详情列表
     */
    @PreAuthorize("@ss.hasPermi('address:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(PAddress pAddress)
    {
        startPage();
        List<PAddress> list = pAddressService.selectPAddressList(pAddress);
        return getDataTable(list);
    }

    /**
     * 查询地址详情列表
     */
    @PreAuthorize("@ss.hasPermi('address:address:list')")
    @PostMapping("/queryAll")
    public AjaxResult queryALl(PAddress pAddress)
    {
        List<PAddress> list = pAddressService.selectPAddressList(pAddress);
        return AjaxResult.success(list);
    }


    /**
     * 导出地址详情列表
     */
    @PreAuthorize("@ss.hasPermi('address:address:export')")
    @Log(title = "地址详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PAddress pAddress)
    {
        List<PAddress> list = pAddressService.selectPAddressList(pAddress);
        ExcelUtil<PAddress> util = new ExcelUtil<PAddress>(PAddress.class);
        util.exportExcel(response, list, "地址详情数据");
    }

    /**
     * 获取地址详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('address:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pAddressService.selectPAddressById(id));
    }

    /**
     * 新增地址详情
     */
    @PreAuthorize("@ss.hasPermi('address:address:add')")
    @Log(title = "地址详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PAddress pAddress)
    {
        return toAjax(pAddressService.insertPAddress(pAddress));
    }

    /**
     * 修改地址详情
     */
    @PreAuthorize("@ss.hasPermi('address:address:edit')")
    @Log(title = "地址详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PAddress pAddress)
    {
        return toAjax(pAddressService.updatePAddress(pAddress));
    }

    /**
     * 删除地址详情
     */
    @PreAuthorize("@ss.hasPermi('address:address:remove')")
    @Log(title = "地址详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pAddressService.deletePAddressByIds(ids));
    }
}
