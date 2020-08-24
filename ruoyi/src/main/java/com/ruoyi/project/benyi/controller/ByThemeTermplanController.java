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
import com.ruoyi.project.benyi.domain.ByThemeTermplan;
import com.ruoyi.project.benyi.service.IByThemeTermplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合学期计划Controller
 *
 * @author tsbz
 * @date 2020-08-24
 */
@RestController
@RequestMapping("/benyi/themetermplan")
public class ByThemeTermplanController extends BaseController
{
    @Autowired
    private IByThemeTermplanService byThemeTermplanService;

/**
 * 查询主题整合学期计划列表
 */
@PreAuthorize("@ss.hasPermi('benyi:themetermplan:list')")
@GetMapping("/list")
        public TableDataInfo list(ByThemeTermplan byThemeTermplan)
    {
        startPage();
        List<ByThemeTermplan> list = byThemeTermplanService.selectByThemeTermplanList(byThemeTermplan);
        return getDataTable(list);
    }
    
    /**
     * 导出主题整合学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:export')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeTermplan byThemeTermplan)
    {
        List<ByThemeTermplan> list = byThemeTermplanService.selectByThemeTermplanList(byThemeTermplan);
        ExcelUtil<ByThemeTermplan> util = new ExcelUtil<ByThemeTermplan>(ByThemeTermplan.class);
        return util.exportExcel(list, "themetermplan");
    }

    /**
     * 获取主题整合学期计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byThemeTermplanService.selectByThemeTermplanById(id));
    }

    /**
     * 新增主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:add')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeTermplan byThemeTermplan)
    {
        return toAjax(byThemeTermplanService.insertByThemeTermplan(byThemeTermplan));
    }

    /**
     * 修改主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:edit')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeTermplan byThemeTermplan)
    {
        return toAjax(byThemeTermplanService.updateByThemeTermplan(byThemeTermplan));
    }

    /**
     * 删除主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:remove')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byThemeTermplanService.deleteByThemeTermplanByIds(ids));
    }
}
