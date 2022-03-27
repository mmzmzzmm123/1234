package com.jlt.csa.controller;

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
import com.jlt.csa.domain.TendTask;
import com.jlt.csa.service.ITendTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 打理任务Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/task")
public class TendTaskController extends BaseController
{
    @Autowired
    private ITendTaskService tendTaskService;

    /**
     * 查询打理任务列表
     */
    @PreAuthorize("@ss.hasPermi('csa:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(TendTask tendTask)
    {
        startPage();
        List<TendTask> list = tendTaskService.selectTendTaskList(tendTask);
        return getDataTable(list);
    }

    /**
     * 导出打理任务列表
     */
    @PreAuthorize("@ss.hasPermi('csa:task:export')")
    @Log(title = "打理任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TendTask tendTask)
    {
        List<TendTask> list = tendTaskService.selectTendTaskList(tendTask);
        ExcelUtil<TendTask> util = new ExcelUtil<TendTask>(TendTask.class);
        util.exportExcel(response, list, "打理任务数据");
    }

    /**
     * 获取打理任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:task:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tendTaskService.selectTendTaskById(id));
    }

    /**
     * 新增打理任务
     */
    @PreAuthorize("@ss.hasPermi('csa:task:add')")
    @Log(title = "打理任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TendTask tendTask)
    {
        return toAjax(tendTaskService.insertTendTask(tendTask));
    }

    /**
     * 修改打理任务
     */
    @PreAuthorize("@ss.hasPermi('csa:task:edit')")
    @Log(title = "打理任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TendTask tendTask)
    {
        return toAjax(tendTaskService.updateTendTask(tendTask));
    }

    /**
     * 删除打理任务
     */
    @PreAuthorize("@ss.hasPermi('csa:task:remove')")
    @Log(title = "打理任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tendTaskService.deleteTendTaskByIds(ids));
    }
}
