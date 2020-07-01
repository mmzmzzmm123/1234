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
import com.ruoyi.project.benyi.domain.ByTheme;
import com.ruoyi.project.benyi.service.IByThemeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合Controller
 *
 * @author tsbz
 * @date 2020-07-01
 */
@RestController
@RequestMapping("/benyi/theme")
public class ByThemeController extends BaseController {
    @Autowired
    private IByThemeService byThemeService;

    /**
     * 查询主题整合列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTheme byTheme) {
        startPage();
        List<ByTheme> list = byThemeService.selectByThemeList(byTheme);
        return getDataTable(list);
    }

    /**
     * 导出主题整合列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:export')")
    @Log(title = "主题整合", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTheme byTheme) {
        List<ByTheme> list = byThemeService.selectByThemeList(byTheme);
        ExcelUtil<ByTheme> util = new ExcelUtil<ByTheme>(ByTheme.class);
        return util.exportExcel(list, "theme");
    }

    /**
     * 获取主题整合详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byThemeService.selectByThemeById(id));
    }

    /**
     * 新增主题整合
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:add')")
    @Log(title = "主题整合", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTheme byTheme) {
        return toAjax(byThemeService.insertByTheme(byTheme));
    }

    /**
     * 修改主题整合
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:edit')")
    @Log(title = "主题整合", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByTheme byTheme) {
        return toAjax(byThemeService.updateByTheme(byTheme));
    }

    /**
     * 删除主题整合
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:remove')")
    @Log(title = "主题整合", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byThemeService.deleteByThemeByIds(ids));
    }
}
