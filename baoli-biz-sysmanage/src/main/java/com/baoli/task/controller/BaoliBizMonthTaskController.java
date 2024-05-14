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
import com.baoli.task.domain.BaoliBizMonthTask;
import com.baoli.task.service.IBaoliBizMonthTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 月度任务Controller
 * 
 * @author niujs
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/task/monthlyTask")
public class BaoliBizMonthTaskController extends BaseController
{
    @Autowired
    private IBaoliBizMonthTaskService baoliBizMonthTaskService;

    /**
     * 查询月度任务列表
     */
    @PreAuthorize("@ss.hasPermi('task:monthlyTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizMonthTask baoliBizMonthTask)
    {
        startPage();
        List<BaoliBizMonthTask> list = baoliBizMonthTaskService.selectBaoliBizMonthTaskList(baoliBizMonthTask);
        return getDataTable(list);
    }

    /**
     * 导出月度任务列表
     */
    @PreAuthorize("@ss.hasPermi('task:monthlyTask:export')")
    @Log(title = "月度任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizMonthTask baoliBizMonthTask)
    {
        List<BaoliBizMonthTask> list = baoliBizMonthTaskService.selectBaoliBizMonthTaskList(baoliBizMonthTask);
        ExcelUtil<BaoliBizMonthTask> util = new ExcelUtil<BaoliBizMonthTask>(BaoliBizMonthTask.class);
        util.exportExcel(response, list, "月度任务数据");
    }

    /**
     * 获取月度任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('task:monthlyTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizMonthTaskService.selectBaoliBizMonthTaskById(id));
    }

    /**
     * 新增月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:monthlyTask:add')")
    @Log(title = "月度任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizMonthTask baoliBizMonthTask)
    {
        return toAjax(baoliBizMonthTaskService.insertBaoliBizMonthTask(baoliBizMonthTask));
    }

    /**
     * 修改月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:monthlyTask:edit')")
    @Log(title = "月度任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizMonthTask baoliBizMonthTask)
    {
        return toAjax(baoliBizMonthTaskService.updateBaoliBizMonthTask(baoliBizMonthTask));
    }

    /**
     * 删除月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:monthlyTask:remove')")
    @Log(title = "月度任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizMonthTaskService.deleteBaoliBizMonthTaskByIds(ids));
    }
}
