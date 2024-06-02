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
import com.baoli.task.domain.BaoliBizYearTaskPlan;
import com.baoli.task.service.IBaoliBizYearTaskPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 年任务管理（T）Controller
 * 
 * @author niujs
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/task/anualTask")
public class BaoliBizYearTaskPlanController extends BaseController
{
    @Autowired
    private IBaoliBizYearTaskPlanService baoliBizYearTaskPlanService;

    /**
     * 查询年任务管理（T）列表
     */
    @PreAuthorize("@ss.hasPermi('task:anualTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        startPage();
        List<BaoliBizYearTaskPlan> list = baoliBizYearTaskPlanService.selectBaoliBizYearTaskPlanList(baoliBizYearTaskPlan);
        return getDataTable(list);
    }

    /**
     * 导出年任务管理（T）列表
     */
    @PreAuthorize("@ss.hasPermi('task:anualTask:export')")
    @Log(title = "年任务管理（T）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        List<BaoliBizYearTaskPlan> list = baoliBizYearTaskPlanService.selectBaoliBizYearTaskPlanList(baoliBizYearTaskPlan);
        ExcelUtil<BaoliBizYearTaskPlan> util = new ExcelUtil<BaoliBizYearTaskPlan>(BaoliBizYearTaskPlan.class);
        util.exportExcel(response, list, "年任务管理（T）数据");
    }

    /**
     * 获取年任务管理（T）详细信息
     */
    @PreAuthorize("@ss.hasPermi('task:anualTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizYearTaskPlanService.selectBaoliBizYearTaskPlanById(id));
    }

    /**
     * 新增年任务管理（T）
     */
    @PreAuthorize("@ss.hasPermi('task:anualTask:add')")
    @Log(title = "年任务管理（T）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        return toAjax(baoliBizYearTaskPlanService.insertBaoliBizYearTaskPlan(baoliBizYearTaskPlan));
    }

    /**
     * 修改年任务管理（T）
     */
    @PreAuthorize("@ss.hasPermi('task:anualTask:edit')")
    @Log(title = "年任务管理（T）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        return toAjax(baoliBizYearTaskPlanService.updateBaoliBizYearTaskPlan(baoliBizYearTaskPlan));
    }

    /**
     * 删除年任务管理（T）
     */
    @PreAuthorize("@ss.hasPermi('task:anualTask:remove')")
    @Log(title = "年任务管理（T）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizYearTaskPlanService.deleteBaoliBizYearTaskPlanByIds(ids));
    }
}
