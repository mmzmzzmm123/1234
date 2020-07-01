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
import com.ruoyi.project.benyi.domain.ByThemeActivity;
import com.ruoyi.project.benyi.service.IByThemeActivityService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合活动Controller
 *
 * @author tsbz
 * @date 2020-07-01
 */
@RestController
@RequestMapping("/benyi/activity")
public class ByThemeActivityController extends BaseController {
    @Autowired
    private IByThemeActivityService byThemeActivityService;

    /**
     * 查询主题整合活动列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeActivity byThemeActivity) {
        startPage();
        List<ByThemeActivity> list = byThemeActivityService.selectByThemeActivityList(byThemeActivity);
        return getDataTable(list);
    }

    /**
     * 导出主题整合活动列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:export')")
    @Log(title = "主题整合活动", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeActivity byThemeActivity) {
        List<ByThemeActivity> list = byThemeActivityService.selectByThemeActivityList(byThemeActivity);
        ExcelUtil<ByThemeActivity> util = new ExcelUtil<ByThemeActivity>(ByThemeActivity.class);
        return util.exportExcel(list, "activity");
    }

    /**
     * 获取主题整合活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byThemeActivityService.selectByThemeActivityById(id));
    }

    /**
     * 新增主题整合活动
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:add')")
    @Log(title = "主题整合活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeActivity byThemeActivity) {
        return toAjax(byThemeActivityService.insertByThemeActivity(byThemeActivity));
    }

    /**
     * 修改主题整合活动
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:edit')")
    @Log(title = "主题整合活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeActivity byThemeActivity) {
        return toAjax(byThemeActivityService.updateByThemeActivity(byThemeActivity));
    }

    /**
     * 删除主题整合活动
     */
    @PreAuthorize("@ss.hasPermi('benyi:theme:remove')")
    @Log(title = "主题整合活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byThemeActivityService.deleteByThemeActivityByIds(ids));
    }
}
