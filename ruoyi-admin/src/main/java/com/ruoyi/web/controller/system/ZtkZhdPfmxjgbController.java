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
import com.ruoyi.system.domain.ZtkZhdPfmxjgb;
import com.ruoyi.system.service.IZtkZhdPfmxjgbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体工商户信贷_主题库_评分模型结果Controller
 * 
 * @author genius
 * @date 2021-11-30
 */
@RestController
@RequestMapping("/system/pfmxjgb")
public class ZtkZhdPfmxjgbController extends BaseController
{
    @Autowired
    private IZtkZhdPfmxjgbService ztkZhdPfmxjgbService;

    /**
     * 查询个体工商户信贷_主题库_评分模型结果列表
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxjgb:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        startPage();
        List<ZtkZhdPfmxjgb> list = ztkZhdPfmxjgbService.selectZtkZhdPfmxjgbList(ztkZhdPfmxjgb);
        return getDataTable(list);
    }

    /**
     * 导出个体工商户信贷_主题库_评分模型结果列表
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxjgb:export')")
    @Log(title = "个体工商户信贷_主题库_评分模型结果", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        List<ZtkZhdPfmxjgb> list = ztkZhdPfmxjgbService.selectZtkZhdPfmxjgbList(ztkZhdPfmxjgb);
        ExcelUtil<ZtkZhdPfmxjgb> util = new ExcelUtil<ZtkZhdPfmxjgb>(ZtkZhdPfmxjgb.class);
        return util.exportExcel(list, "个体工商户信贷_主题库_评分模型结果数据");
    }

    /**
     * 获取个体工商户信贷_主题库_评分模型结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxjgb:query')")
    @GetMapping(value = "/{tyshxydm}")
    public AjaxResult getInfo(@PathVariable("tyshxydm") String tyshxydm)
    {
        return AjaxResult.success(ztkZhdPfmxjgbService.selectZtkZhdPfmxjgbById(tyshxydm));
    }

    /**
     * 新增个体工商户信贷_主题库_评分模型结果
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxjgb:add')")
    @Log(title = "个体工商户信贷_主题库_评分模型结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        return toAjax(ztkZhdPfmxjgbService.insertZtkZhdPfmxjgb(ztkZhdPfmxjgb));
    }

    /**
     * 修改个体工商户信贷_主题库_评分模型结果
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxjgb:edit')")
    @Log(title = "个体工商户信贷_主题库_评分模型结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        return toAjax(ztkZhdPfmxjgbService.updateZtkZhdPfmxjgb(ztkZhdPfmxjgb));
    }

    /**
     * 删除个体工商户信贷_主题库_评分模型结果
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxjgb:remove')")
    @Log(title = "个体工商户信贷_主题库_评分模型结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tyshxydms}")
    public AjaxResult remove(@PathVariable String[] tyshxydms)
    {
        return toAjax(ztkZhdPfmxjgbService.deleteZtkZhdPfmxjgbByIds(tyshxydms));
    }
}
