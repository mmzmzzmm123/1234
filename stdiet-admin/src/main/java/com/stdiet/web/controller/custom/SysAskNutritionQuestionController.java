package com.stdiet.web.controller.custom;

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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysAskNutritionQuestion;
import com.stdiet.custom.service.ISysAskNutritionQuestionService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 营养小知识提问Controller
 *
 * @author xzj
 * @date 2021-04-23
 */
@RestController
@RequestMapping("/custom/askQuestion")
public class SysAskNutritionQuestionController extends BaseController
{
    @Autowired
    private ISysAskNutritionQuestionService sysAskNutritionQuestionService;

    /**
     * 查询营养小知识提问列表
     */
    @PreAuthorize("@ss.hasPermi('custom:askQuestion:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        startPage();
        List<SysAskNutritionQuestion> list = sysAskNutritionQuestionService.selectSysAskNutritionQuestionList(sysAskNutritionQuestion);
        return getDataTable(list);
    }

    /**
     * 导出营养小知识提问列表
     */
    @PreAuthorize("@ss.hasPermi('custom:askQuestion:export')")
    @Log(title = "营养小知识提问", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        List<SysAskNutritionQuestion> list = sysAskNutritionQuestionService.selectSysAskNutritionQuestionList(sysAskNutritionQuestion);
        ExcelUtil<SysAskNutritionQuestion> util = new ExcelUtil<SysAskNutritionQuestion>(SysAskNutritionQuestion.class);
        return util.exportExcel(list, "askQuestion");
    }

    /**
     * 获取营养小知识提问详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:askQuestion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysAskNutritionQuestionService.selectSysAskNutritionQuestionById(id));
    }

    /**
     * 新增营养小知识提问
     */
    @PreAuthorize("@ss.hasPermi('custom:askQuestion:add')")
    @Log(title = "营养小知识提问", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        return toAjax(sysAskNutritionQuestionService.insertSysAskNutritionQuestion(sysAskNutritionQuestion));
    }

    /**
     * 修改营养小知识提问
     */
    @PreAuthorize("@ss.hasPermi('custom:askQuestion:edit')")
    @Log(title = "营养小知识提问", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        return toAjax(sysAskNutritionQuestionService.updateSysAskNutritionQuestion(sysAskNutritionQuestion));
    }

    /**
     * 删除营养小知识提问
     */
    @PreAuthorize("@ss.hasPermi('custom:askQuestion:remove')")
    @Log(title = "营养小知识提问", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysAskNutritionQuestionService.deleteSysAskNutritionQuestionByIds(ids));
    }
}