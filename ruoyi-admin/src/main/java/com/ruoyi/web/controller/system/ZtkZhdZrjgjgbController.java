package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.ZtkZhdZrjgjgb;
import com.ruoyi.system.service.IZtkZhdZrjgjgbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体工商户信贷_主题库_准入监管结果Controller
 * 
 * @author genius
 * @date 2021-11-30
 */
@RestController
@RequestMapping("/system/zrjgjgb")
public class ZtkZhdZrjgjgbController extends BaseController
{
    @Autowired
    private IZtkZhdZrjgjgbService ztkZhdZrjgjgbService;

    /**
     * 查询个体工商户信贷_主题库_准入监管结果列表
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgjgb:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        startPage();
        List<ZtkZhdZrjgjgb> list = ztkZhdZrjgjgbService.selectZtkZhdZrjgjgbList(ztkZhdZrjgjgb);
        return getDataTable(list);
    }

    /**
     * 导出个体工商户信贷_主题库_准入监管结果列表
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgjgb:export')")
    @Log(title = "个体工商户信贷_主题库_准入监管结果", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        List<ZtkZhdZrjgjgb> list = ztkZhdZrjgjgbService.selectZtkZhdZrjgjgbList(ztkZhdZrjgjgb);
        ExcelUtil<ZtkZhdZrjgjgb> util = new ExcelUtil<ZtkZhdZrjgjgb>(ZtkZhdZrjgjgb.class);
        return util.exportExcel(list, "个体工商户信贷_主题库_准入监管结果数据");
    }

    /**
     * 获取个体工商户信贷_主题库_准入监管结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgjgb:query')")
    @GetMapping(value = "/{tyshxydm}")
    public AjaxResult getInfo(@PathVariable("tyshxydm") String tyshxydm)
    {
        return AjaxResult.success(ztkZhdZrjgjgbService.selectZtkZhdZrjgjgbById(tyshxydm));
    }

    /**
     * 新增个体工商户信贷_主题库_准入监管结果
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgjgb:add')")
    @Log(title = "个体工商户信贷_主题库_准入监管结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        return toAjax(ztkZhdZrjgjgbService.insertZtkZhdZrjgjgb(ztkZhdZrjgjgb));
    }

    /**
     * 修改个体工商户信贷_主题库_准入监管结果
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgjgb:edit')")
    @Log(title = "个体工商户信贷_主题库_准入监管结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        return toAjax(ztkZhdZrjgjgbService.updateZtkZhdZrjgjgb(ztkZhdZrjgjgb));
    }

    /**
     * 删除个体工商户信贷_主题库_准入监管结果
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgjgb:remove')")
    @Log(title = "个体工商户信贷_主题库_准入监管结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tyshxydms}")
    public AjaxResult remove(@PathVariable String[] tyshxydms)
    {
        return toAjax(ztkZhdZrjgjgbService.deleteZtkZhdZrjgjgbByIds(tyshxydms));
    }
}
