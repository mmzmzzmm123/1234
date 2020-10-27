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
import com.ruoyi.project.benyi.domain.ByAssessmentchild;
import com.ruoyi.project.benyi.service.IByAssessmentchildService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿评估Controller
 *
 * @author tsbz
 * @date 2020-10-27
 */
@RestController
@RequestMapping("/benyi/assessmentchild")
public class ByAssessmentchildController extends BaseController {
    @Autowired
    private IByAssessmentchildService byAssessmentchildService;

    /**
     * 查询幼儿评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByAssessmentchild byAssessmentchild) {
        startPage();
        List<ByAssessmentchild> list = byAssessmentchildService.selectByAssessmentchildList(byAssessmentchild);
        return getDataTable(list);
    }

    /**
     * 导出幼儿评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:export')")
    @Log(title = "幼儿评估", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAssessmentchild byAssessmentchild) {
        List<ByAssessmentchild> list = byAssessmentchildService.selectByAssessmentchildList(byAssessmentchild);
        ExcelUtil<ByAssessmentchild> util = new ExcelUtil<ByAssessmentchild>(ByAssessmentchild.class);
        return util.exportExcel(list, "assessmentchild");
    }

    /**
     * 获取幼儿评估详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byAssessmentchildService.selectByAssessmentchildById(id));
    }

    /**
     * 新增幼儿评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:add')")
    @Log(title = "幼儿评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByAssessmentchild byAssessmentchild) {
        return toAjax(byAssessmentchildService.insertByAssessmentchild(byAssessmentchild));
    }

    /**
     * 修改幼儿评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:edit')")
    @Log(title = "幼儿评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAssessmentchild byAssessmentchild) {
        return toAjax(byAssessmentchildService.updateByAssessmentchild(byAssessmentchild));
    }

    /**
     * 删除幼儿评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:remove')")
    @Log(title = "幼儿评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byAssessmentchildService.deleteByAssessmentchildByIds(ids));
    }
}
