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
import com.ruoyi.project.benyi.domain.ByThemeMonthplan;
import com.ruoyi.project.benyi.service.IByThemeMonthplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合月计划Controller
 *
 * @author tsbz
 * @date 2020-08-17
 */
@RestController
@RequestMapping("/benyi/thememonthplan")
public class ByThemeMonthplanController extends BaseController {
    @Autowired
    private IByThemeMonthplanService byThemeMonthplanService;

    /**
     * 查询主题整合月计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeMonthplan byThemeMonthplan) {
        startPage();
        List<ByThemeMonthplan> list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
        return getDataTable(list);
    }

    /**
     * 导出主题整合月计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:export')")
    @Log(title = "主题整合月计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeMonthplan byThemeMonthplan) {
        List<ByThemeMonthplan> list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
        ExcelUtil<ByThemeMonthplan> util = new ExcelUtil<ByThemeMonthplan>(ByThemeMonthplan.class);
        return util.exportExcel(list, "thememonthplan");
    }

    /**
     * 获取主题整合月计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byThemeMonthplanService.selectByThemeMonthplanById(id));
    }

    /**
     * 新增主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:add')")
    @Log(title = "主题整合月计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeMonthplan byThemeMonthplan) {
        return toAjax(byThemeMonthplanService.insertByThemeMonthplan(byThemeMonthplan));
    }

    /**
     * 修改主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:edit')")
    @Log(title = "主题整合月计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeMonthplan byThemeMonthplan) {
        return toAjax(byThemeMonthplanService.updateByThemeMonthplan(byThemeMonthplan));
    }

    /**
     * 删除主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:remove')")
    @Log(title = "主题整合月计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byThemeMonthplanService.deleteByThemeMonthplanByIds(ids));
    }
}
