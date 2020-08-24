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
import com.ruoyi.project.benyi.domain.ByPlanweek;
import com.ruoyi.project.benyi.service.IByPlanweekService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 周计划(家长和教育部门)Controller
 *
 * @author tsbz
 * @date 2020-08-24
 */
@RestController
@RequestMapping("/benyi/planweek")
public class ByPlanweekController extends BaseController {
    @Autowired
    private IByPlanweekService byPlanweekService;

    /**
     * 查询周计划(家长和教育部门)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByPlanweek byPlanweek) {
        startPage();
        List<ByPlanweek> list = byPlanweekService.selectByPlanweekList(byPlanweek);
        return getDataTable(list);
    }

    /**
     * 导出周计划(家长和教育部门)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:export')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByPlanweek byPlanweek) {
        List<ByPlanweek> list = byPlanweekService.selectByPlanweekList(byPlanweek);
        ExcelUtil<ByPlanweek> util = new ExcelUtil<ByPlanweek>(ByPlanweek.class);
        return util.exportExcel(list, "planweek");
    }

    /**
     * 获取周计划(家长和教育部门)详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byPlanweekService.selectByPlanweekById(id));
    }

    /**
     * 新增周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:add')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByPlanweek byPlanweek) {
        return toAjax(byPlanweekService.insertByPlanweek(byPlanweek));
    }

    /**
     * 修改周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:edit')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByPlanweek byPlanweek) {
        return toAjax(byPlanweekService.updateByPlanweek(byPlanweek));
    }

    /**
     * 删除周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:remove')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byPlanweekService.deleteByPlanweekByIds(ids));
    }
}
