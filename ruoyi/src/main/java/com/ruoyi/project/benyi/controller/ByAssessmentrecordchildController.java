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
import com.ruoyi.project.benyi.domain.ByAssessmentrecordchild;
import com.ruoyi.project.benyi.service.IByAssessmentrecordchildService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿评估记录Controller
 *
 * @author tsbz
 * @date 2020-10-27
 */
@RestController
@RequestMapping("/benyi/assessmentrecordchild")
public class ByAssessmentrecordchildController extends BaseController
{
    @Autowired
    private IByAssessmentrecordchildService byAssessmentrecordchildService;

/**
 * 查询幼儿评估记录列表
 */
@PreAuthorize("@ss.hasPermi('benyi:assessmentrecordchild:list')")
@GetMapping("/list")
        public TableDataInfo list(ByAssessmentrecordchild byAssessmentrecordchild)
    {
        startPage();
        List<ByAssessmentrecordchild> list = byAssessmentrecordchildService.selectByAssessmentrecordchildList(byAssessmentrecordchild);
        return getDataTable(list);
    }
    
    /**
     * 导出幼儿评估记录列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentrecordchild:export')")
    @Log(title = "幼儿评估记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAssessmentrecordchild byAssessmentrecordchild)
    {
        List<ByAssessmentrecordchild> list = byAssessmentrecordchildService.selectByAssessmentrecordchildList(byAssessmentrecordchild);
        ExcelUtil<ByAssessmentrecordchild> util = new ExcelUtil<ByAssessmentrecordchild>(ByAssessmentrecordchild.class);
        return util.exportExcel(list, "assessmentrecordchild");
    }

    /**
     * 获取幼儿评估记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentrecordchild:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byAssessmentrecordchildService.selectByAssessmentrecordchildById(id));
    }

    /**
     * 新增幼儿评估记录
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentrecordchild:add')")
    @Log(title = "幼儿评估记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByAssessmentrecordchild byAssessmentrecordchild)
    {
        return toAjax(byAssessmentrecordchildService.insertByAssessmentrecordchild(byAssessmentrecordchild));
    }

    /**
     * 修改幼儿评估记录
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentrecordchild:edit')")
    @Log(title = "幼儿评估记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAssessmentrecordchild byAssessmentrecordchild)
    {
        return toAjax(byAssessmentrecordchildService.updateByAssessmentrecordchild(byAssessmentrecordchild));
    }

    /**
     * 删除幼儿评估记录
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentrecordchild:remove')")
    @Log(title = "幼儿评估记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byAssessmentrecordchildService.deleteByAssessmentrecordchildByIds(ids));
    }
}
