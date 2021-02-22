package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.project.benyi.domain.ByDayflowassessment;
import com.ruoyi.project.benyi.service.IByDayflowassessmentService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿园一日流程评估Controller
 *
 * @author tsbz
 * @date 2021-02-05
 */
@RestController
@RequestMapping("/benyi/dayflowassessment")
public class ByDayflowassessmentController extends BaseController {
    @Autowired
    private IByDayflowassessmentService byDayflowassessmentService;

    /**
     * 查询幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayflowassessment byDayflowassessment) {
        startPage();
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment);
        return getDataTable(list);
    }

    /**
     * 导出幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:export')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayflowassessment byDayflowassessment) {
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment);
        ExcelUtil<ByDayflowassessment> util = new ExcelUtil<ByDayflowassessment>(ByDayflowassessment.class);
        return util.exportExcel(list, "dayflowassessment");
    }

    /**
     * 获取幼儿园一日流程评估详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byDayflowassessmentService.selectByDayflowassessmentById(id));
    }

    /**
     * 新增幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:add')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayflowassessment byDayflowassessment) {
        byDayflowassessment.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(byDayflowassessmentService.insertByDayflowassessment(byDayflowassessment));
    }

    /**
     * 修改幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:edit')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayflowassessment byDayflowassessment) {
        return toAjax(byDayflowassessmentService.updateByDayflowassessment(byDayflowassessment));
    }

    /**
     * 删除幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:remove')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byDayflowassessmentService.deleteByDayflowassessmentByIds(ids));
    }
}
