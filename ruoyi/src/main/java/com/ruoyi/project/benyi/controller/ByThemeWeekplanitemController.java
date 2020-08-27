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
import com.ruoyi.project.benyi.domain.ByThemeWeekplanitem;
import com.ruoyi.project.benyi.service.IByThemeWeekplanitemService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合周计划明细Controller
 *
 * @author tsbz
 * @date 2020-08-26
 */
@RestController
@RequestMapping("/benyi/themeweekplanitem")
public class ByThemeWeekplanitemController extends BaseController {
    @Autowired
    private IByThemeWeekplanitemService byThemeWeekplanitemService;

    /**
     * 查询主题整合周计划明细列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeWeekplanitem byThemeWeekplanitem) {
        startPage();
        List<ByThemeWeekplanitem> list = byThemeWeekplanitemService.selectByThemeWeekplanitemList(byThemeWeekplanitem);
        return getDataTable(list);
    }

    /**
     * 导出主题整合周计划明细列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:export')")
    @Log(title = "主题整合周计划明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeWeekplanitem byThemeWeekplanitem) {
        List<ByThemeWeekplanitem> list = byThemeWeekplanitemService.selectByThemeWeekplanitemList(byThemeWeekplanitem);
        ExcelUtil<ByThemeWeekplanitem> util = new ExcelUtil<ByThemeWeekplanitem>(ByThemeWeekplanitem.class);
        return util.exportExcel(list, "weekplanitem");
    }

    /**
     * 获取主题整合周计划明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byThemeWeekplanitemService.selectByThemeWeekplanitemById(id));
    }

    /**
     * 新增主题整合周计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:add')")
    @Log(title = "主题整合周计划明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeWeekplanitem byThemeWeekplanitem) {
        return toAjax(byThemeWeekplanitemService.insertByThemeWeekplanitem(byThemeWeekplanitem));
    }

    /**
     * 修改主题整合周计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:edit')")
    @Log(title = "主题整合周计划明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeWeekplanitem byThemeWeekplanitem) {
        return toAjax(byThemeWeekplanitemService.updateByThemeWeekplanitem(byThemeWeekplanitem));
    }

    /**
     * 删除主题整合周计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:remove')")
    @Log(title = "主题整合周计划明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byThemeWeekplanitemService.deleteByThemeWeekplanitemByIds(ids));
    }
}
