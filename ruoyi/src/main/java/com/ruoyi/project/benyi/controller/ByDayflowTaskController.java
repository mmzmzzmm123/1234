package com.ruoyi.project.benyi.controller;

import java.util.Date;
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
import com.ruoyi.project.benyi.domain.ByDayflowTask;
import com.ruoyi.project.benyi.service.IByDayflowTaskService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 一日流程任务Controller
 * 
 * @author tsbz
 * @date 2020-05-13
 */
@RestController
@RequestMapping("/benyi/dayflowtask")
public class ByDayflowTaskController extends BaseController
{
    @Autowired
    private IByDayflowTaskService byDayflowTaskService;

    /**
     * 查询一日流程任务列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayflowTask byDayflowTask)
    {
        startPage();
        List<ByDayflowTask> list = byDayflowTaskService.selectByDayflowTaskList(byDayflowTask);
        return getDataTable(list);
    }

    /**
     * 导出一日流程任务列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:export')")
    @Log(title = "一日流程任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayflowTask byDayflowTask)
    {
        List<ByDayflowTask> list = byDayflowTaskService.selectByDayflowTaskList(byDayflowTask);
        ExcelUtil<ByDayflowTask> util = new ExcelUtil<ByDayflowTask>(ByDayflowTask.class);
        return util.exportExcel(list, "dayflowtask");
    }

    /**
     * 获取一日流程任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byDayflowTaskService.selectByDayflowTaskById(id));
    }

    /**
     * 新增一日流程任务
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:add')")
    @Log(title = "一日流程任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayflowTask byDayflowTask)
    {
        byDayflowTask.setCreateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayflowTask.setCreatetime(new Date());
        return toAjax(byDayflowTaskService.insertByDayflowTask(byDayflowTask));
    }

    /**
     * 修改一日流程任务
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:edit')")
    @Log(title = "一日流程任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayflowTask byDayflowTask)
    {
        byDayflowTask.setUpdateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayflowTask.setUpdatetime(new Date());
        return toAjax(byDayflowTaskService.updateByDayflowTask(byDayflowTask));
    }

    /**
     * 删除一日流程任务
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:remove')")
    @Log(title = "一日流程任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byDayflowTaskService.deleteByDayflowTaskByIds(ids));
    }
}
