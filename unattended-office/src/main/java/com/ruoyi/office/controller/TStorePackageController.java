package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.office.domain.TStorePackage;
import com.ruoyi.office.service.ITStorePackageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 店铺充值套餐Controller
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/office/storepackage")
public class TStorePackageController extends BaseController
{
    @Autowired
    private ITStorePackageService tStorePackageService;

    /**
     * 查询店铺充值套餐列表
     */
    @PreAuthorize("@ss.hasPermi('office:storepackage:list')")
    @GetMapping("/list")
    public TableDataInfo list(TStorePackage tStorePackage)
    {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tStorePackage.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TStorePackage> list = tStorePackageService.selectTStorePackageList(tStorePackage);
        return getDataTable(list);
    }

    /**
     * 导出店铺充值套餐列表
     */
    @PreAuthorize("@ss.hasPermi('office:storepackage:export')")
    @Log(title = "店铺充值套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStorePackage tStorePackage)
    {
        List<TStorePackage> list = tStorePackageService.selectTStorePackageList(tStorePackage);
        ExcelUtil<TStorePackage> util = new ExcelUtil<TStorePackage>(TStorePackage.class);
        util.exportExcel(response, list, "店铺充值套餐数据");
    }

    /**
     * 获取店铺充值套餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:storepackage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tStorePackageService.selectTStorePackageById(id));
    }

    /**
     * 新增店铺充值套餐
     */
    @PreAuthorize("@ss.hasPermi('office:storepackage:add')")
    @Log(title = "店铺充值套餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TStorePackage tStorePackage)
    {
        tStorePackage.setCreateBy(SecurityUtils.getUserId() + "");
        return toAjax(tStorePackageService.insertTStorePackage(tStorePackage));
    }

    /**
     * 修改店铺充值套餐
     */
    @PreAuthorize("@ss.hasPermi('office:storepackage:edit')")
    @Log(title = "店铺充值套餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TStorePackage tStorePackage)
    {
        return toAjax(tStorePackageService.updateTStorePackage(tStorePackage));
    }

    /**
     * 删除店铺充值套餐
     */
    @PreAuthorize("@ss.hasPermi('office:storepackage:remove')")
    @Log(title = "店铺充值套餐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tStorePackageService.deleteTStorePackageByIds(ids));
    }
}
