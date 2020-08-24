package com.ruoyi.project.benyi.controller;

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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByThemeTermplanitem;
import com.ruoyi.project.benyi.service.IByThemeTermplanitemService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合学期计划明细Controller
 *
 * @author tsbz
 * @date 2020-08-24
 */
@RestController
@RequestMapping("/benyi/termplanitem")
public class ByThemeTermplanitemController extends BaseController {
    @Autowired
    private IByThemeTermplanitemService byThemeTermplanitemService;

    /**
     * 查询主题整合学期计划明细列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeTermplanitem byThemeTermplanitem) {
        startPage();
        List<ByThemeTermplanitem> list = byThemeTermplanitemService.selectByThemeTermplanitemList(byThemeTermplanitem);
        return getDataTable(list);
    }

    /**
     * 导出主题整合学期计划明细列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:export')")
    @Log(title = "主题整合学期计划明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeTermplanitem byThemeTermplanitem) {
        List<ByThemeTermplanitem> list = byThemeTermplanitemService.selectByThemeTermplanitemList(byThemeTermplanitem);
        ExcelUtil<ByThemeTermplanitem> util = new ExcelUtil<ByThemeTermplanitem>(ByThemeTermplanitem.class);
        return util.exportExcel(list, "termplanitem");
    }

    /**
     * 获取主题整合学期计划明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byThemeTermplanitemService.selectByThemeTermplanitemById(id));
    }

    /**
     * 新增主题整合学期计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:add')")
    @Log(title = "主题整合学期计划明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeTermplanitem byThemeTermplanitem) {
        return toAjax(byThemeTermplanitemService.insertByThemeTermplanitem(byThemeTermplanitem));
    }

    /**
     * 修改主题整合学期计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:edit')")
    @Log(title = "主题整合学期计划明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeTermplanitem byThemeTermplanitem) {
        return toAjax(byThemeTermplanitemService.updateByThemeTermplanitem(byThemeTermplanitem));
    }

    /**
     * 删除主题整合学期计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:remove')")
    @Log(title = "主题整合学期计划明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byThemeTermplanitemService.deleteByThemeTermplanitemByIds(ids));
    }
}
