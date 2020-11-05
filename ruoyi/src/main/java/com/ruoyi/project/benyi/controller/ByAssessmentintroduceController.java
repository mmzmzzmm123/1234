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
import com.ruoyi.project.benyi.domain.ByAssessmentintroduce;
import com.ruoyi.project.benyi.service.IByAssessmentintroduceService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 评估体系介绍Controller
 *
 * @author tsbz
 * @date 2020-10-09
 */
@RestController
@RequestMapping("/benyi/assessmentintroduce")
public class ByAssessmentintroduceController extends BaseController {
    @Autowired
    private IByAssessmentintroduceService byAssessmentintroduceService;

    /**
     * 查询评估体系介绍列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentintroduce:list')" + "||@ss.hasPermi('benyi:assessmentcontent:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByAssessmentintroduce byAssessmentintroduce) {
        startPage();
        List<ByAssessmentintroduce> list = byAssessmentintroduceService.selectByAssessmentintroduceList(byAssessmentintroduce);
        return getDataTable(list);
    }

    /**
     * 导出评估体系介绍列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentintroduce:export')")
    @Log(title = "评估体系介绍", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAssessmentintroduce byAssessmentintroduce) {
        List<ByAssessmentintroduce> list = byAssessmentintroduceService.selectByAssessmentintroduceList(byAssessmentintroduce);
        ExcelUtil<ByAssessmentintroduce> util = new ExcelUtil<ByAssessmentintroduce>(ByAssessmentintroduce.class);
        return util.exportExcel(list, "assessmentintroduce");
    }

    /**
     * 获取评估体系介绍详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentintroduce:query')" + "||@ss.hasPermi('benyi:assessmentcontent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byAssessmentintroduceService.selectByAssessmentintroduceById(id));
    }

    /**
     * 新增评估体系介绍
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentintroduce:add')")
    @Log(title = "评估体系介绍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByAssessmentintroduce byAssessmentintroduce) {
        return toAjax(byAssessmentintroduceService.insertByAssessmentintroduce(byAssessmentintroduce));
    }

    /**
     * 修改评估体系介绍
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentintroduce:edit')")
    @Log(title = "评估体系介绍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAssessmentintroduce byAssessmentintroduce) {
        return toAjax(byAssessmentintroduceService.updateByAssessmentintroduce(byAssessmentintroduce));
    }

    /**
     * 删除评估体系介绍
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentintroduce:remove')")
    @Log(title = "评估体系介绍", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byAssessmentintroduceService.deleteByAssessmentintroduceByIds(ids));
    }
}
