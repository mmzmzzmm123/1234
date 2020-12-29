package com.stdiet.web.controller.custom;

import java.util.List;
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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysIngredient;
import com.stdiet.custom.service.ISysIngredientService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 食材Controller
 * 
 * @author wonder
 * @date 2020-12-15
 */
@RestController
@RequestMapping("/custom/ingredient")
public class SysIngredientController extends BaseController
{
    @Autowired
    private ISysIngredientService sysIngredientService;

    /**
     * 查询食材列表
     */
    @PreAuthorize("@ss.hasPermi('custom:ingredient:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SysIngredient sysIngredient)
    {
        startPage();
        List<SysIngredient> list = sysIngredientService.selectSysIngredientList(sysIngredient);
        return getDataTable(list);
    }

    /**
     * 导出食材列表
     */
    @PreAuthorize("@ss.hasPermi('custom:ingredient:export')")
    @Log(title = "食材", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysIngredient sysIngredient)
    {
        List<SysIngredient> list = sysIngredientService.selectSysIngredientList(sysIngredient);
        ExcelUtil<SysIngredient> util = new ExcelUtil<SysIngredient>(SysIngredient.class);
        return util.exportExcel(list, "ingredient");
    }

    /**
     * 获取食材详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:ingredient:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysIngredientService.selectSysIngredientById(id));
    }

    /**
     * 新增食材
     */
    @PreAuthorize("@ss.hasPermi('custom:ingredient:add')")
    @Log(title = "食材", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysIngredient sysIngredient)
    {
        return toAjax(sysIngredientService.insertSysIngredient(sysIngredient));
    }

    /**
     * 修改食材
     */
    @PreAuthorize("@ss.hasPermi('custom:ingredient:edit')")
    @Log(title = "食材", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysIngredient sysIngredient)
    {
        return toAjax(sysIngredientService.updateSysIngredient(sysIngredient));
    }

    /**
     * 删除食材
     */
    @PreAuthorize("@ss.hasPermi('custom:ingredient:remove')")
    @Log(title = "食材", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysIngredientService.deleteSysIngredientByIds(ids));
    }
}