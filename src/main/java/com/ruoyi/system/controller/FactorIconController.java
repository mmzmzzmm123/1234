package com.ruoyi.system.controller;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.FactorIcon;
import com.ruoyi.system.service.IFactorIconService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 因子图标Controller
 * 
 * @author ruoyi
 * @date 2021-12-17
 */
@RestController
@RequestMapping("/system/icon")
public class FactorIconController extends BaseController
{
    @Autowired
    private IFactorIconService factorIconService;

    /**
     * 查询因子图标列表
     */
    @PreAuthorize("@ss.hasPermi('system:icon:list')")
    @GetMapping("/list")
    public TableDataInfo list(FactorIcon factorIcon)
    {
        startPage();
        List<FactorIcon> list = factorIconService.selectFactorIconList(factorIcon);
        return getDataTable(list);
    }

    /**
     * 导出因子图标列表
     */
    @PreAuthorize("@ss.hasPermi('system:icon:export')")
    @Log(title = "因子图标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FactorIcon factorIcon)
    {
        List<FactorIcon> list = factorIconService.selectFactorIconList(factorIcon);
        ExcelUtil<FactorIcon> util = new ExcelUtil<FactorIcon>(FactorIcon.class);
        return util.exportExcel(list, "因子图标数据");
    }

    /**
     * 获取因子图标详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:icon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(factorIconService.selectFactorIconById(id));
    }

    /**
     * 新增因子图标
     */
    @PreAuthorize("@ss.hasPermi('system:icon:add')")
    @Log(title = "因子图标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FactorIcon factorIcon)
    {
        return toAjax(factorIconService.insertFactorIcon(factorIcon));
    }

    /**
     * 修改因子图标
     */
    @PreAuthorize("@ss.hasPermi('system:icon:edit')")
    @Log(title = "因子图标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FactorIcon factorIcon)
    {
        return toAjax(factorIconService.updateFactorIcon(factorIcon));
    }

    /**
     * 删除因子图标
     */
    @PreAuthorize("@ss.hasPermi('system:icon:remove')")
    @Log(title = "因子图标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(factorIconService.deleteFactorIconByIds(ids));
    }
}
