package com.stdiet.custom.controller;

import java.util.List;

import org.aspectj.weaver.loadtime.Aj;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysNutritionQuestion;
import com.stdiet.custom.service.ISysNutritionQuestionService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 营养知识小问答Controller
 *
 * @author xzj
 * @date 2021-04-13
 */
@RestController
@RequestMapping("/custom/nutritionQuestion")
public class SysNutritionQuestionController extends BaseController
{
    @Autowired
    private ISysNutritionQuestionService sysNutritionQuestionService;

    /**
     * 查询营养知识小问答列表
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionQuestion:list')")
    @GetMapping("/list")
    public AjaxResult list(SysNutritionQuestion sysNutritionQuestion, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")int pageSize)
    {
        //startPage()
        return AjaxResult.success(sysNutritionQuestionService.getNutritionQuestionListByKey(sysNutritionQuestion, pageNum, pageSize));
    }

    /**
     * 导出营养知识小问答列表
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionQuestion:export')")
    @Log(title = "营养知识小问答", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysNutritionQuestion sysNutritionQuestion)
    {
        List<SysNutritionQuestion> list = sysNutritionQuestionService.selectSysNutritionQuestionList(sysNutritionQuestion);
        ExcelUtil<SysNutritionQuestion> util = new ExcelUtil<SysNutritionQuestion>(SysNutritionQuestion.class);
        return util.exportExcel(list, "nutritionQuestion");
    }

    /**
     * 获取营养知识小问答详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionQuestion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysNutritionQuestionService.selectSysNutritionQuestionById(id));
    }

    /**
     * 新增营养知识小问答
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionQuestion:add')")
    @Log(title = "营养知识小问答", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNutritionQuestion sysNutritionQuestion)
    {
        return toAjax(sysNutritionQuestionService.insertSysNutritionQuestion(sysNutritionQuestion));
    }

    /**
     * 修改营养知识小问答
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionQuestion:edit')")
    @Log(title = "营养知识小问答", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNutritionQuestion sysNutritionQuestion)
    {
        return toAjax(sysNutritionQuestionService.updateSysNutritionQuestion(sysNutritionQuestion));
    }

    /**
     * 删除营养知识小问答
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionQuestion:remove')")
    @Log(title = "营养知识小问答", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysNutritionQuestionService.deleteSysNutritionQuestionByIds(ids));
    }

    /**
     * 重新生成知识问答索引
     */
    @Log(title = "重新生成知识问答索引", businessType = BusinessType.UPDATE)
    @GetMapping("/regenerateNutritionQuestionIndex")
    public AjaxResult regenerateNutritionQuestionIndex()
    {
        return toAjax(sysNutritionQuestionService.regenerateNutritionQuestionIndex() ? 1 : 0);
    }
}