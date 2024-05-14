package com.baoli.task.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.baoli.task.domain.BaoliBizYearTaskPlanEvaluate;
import com.baoli.task.service.IBaoliBizYearTaskPlanEvaluateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 年任务考核Controller
 * 
 * @author niujs
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/task/anualEvaluateTask")
public class BaoliBizYearTaskPlanEvaluateController extends BaseController
{
    @Autowired
    private IBaoliBizYearTaskPlanEvaluateService baoliBizYearTaskPlanEvaluateService;

    /**
     * 查询年任务考核列表
     */
    @PreAuthorize("@ss.hasPermi('task:anualEvaluateTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        startPage();
        List<BaoliBizYearTaskPlanEvaluate> list = baoliBizYearTaskPlanEvaluateService.selectBaoliBizYearTaskPlanEvaluateList(baoliBizYearTaskPlanEvaluate);
        return getDataTable(list);
    }

    /**
     * 导出年任务考核列表
     */
    @PreAuthorize("@ss.hasPermi('task:anualEvaluateTask:export')")
    @Log(title = "年任务考核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        List<BaoliBizYearTaskPlanEvaluate> list = baoliBizYearTaskPlanEvaluateService.selectBaoliBizYearTaskPlanEvaluateList(baoliBizYearTaskPlanEvaluate);
        ExcelUtil<BaoliBizYearTaskPlanEvaluate> util = new ExcelUtil<BaoliBizYearTaskPlanEvaluate>(BaoliBizYearTaskPlanEvaluate.class);
        util.exportExcel(response, list, "年任务考核数据");
    }

    /**
     * 获取年任务考核详细信息
     */
    @PreAuthorize("@ss.hasPermi('task:anualEvaluateTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizYearTaskPlanEvaluateService.selectBaoliBizYearTaskPlanEvaluateById(id));
    }

    /**
     * 新增年任务考核
     */
    @PreAuthorize("@ss.hasPermi('task:anualEvaluateTask:add')")
    @Log(title = "年任务考核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        return toAjax(baoliBizYearTaskPlanEvaluateService.insertBaoliBizYearTaskPlanEvaluate(baoliBizYearTaskPlanEvaluate));
    }

    /**
     * 修改年任务考核
     */
    @PreAuthorize("@ss.hasPermi('task:anualEvaluateTask:edit')")
    @Log(title = "年任务考核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        return toAjax(baoliBizYearTaskPlanEvaluateService.updateBaoliBizYearTaskPlanEvaluate(baoliBizYearTaskPlanEvaluate));
    }

    /**
     * 删除年任务考核
     */
    @PreAuthorize("@ss.hasPermi('task:anualEvaluateTask:remove')")
    @Log(title = "年任务考核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizYearTaskPlanEvaluateService.deleteBaoliBizYearTaskPlanEvaluateByIds(ids));
    }
}
