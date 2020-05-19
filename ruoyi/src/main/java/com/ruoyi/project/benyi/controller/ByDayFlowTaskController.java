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
import com.ruoyi.project.benyi.domain.ByDayFlowTask;
import com.ruoyi.project.benyi.service.IByDayFlowTaskService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 一日流程任务Controller
 * 
 * @author tsbz
 * @date 2020-05-14
 */
@RestController
@RequestMapping("/benyi/dayflow/dayflowtask")
public class ByDayFlowTaskController extends BaseController
{
    @Autowired
    private IByDayFlowTaskService byDayFlowTaskService;

    /**
     * 查询一日流程任务列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayFlowTask byDayFlowTask)
    {
        startPage();
        List<ByDayFlowTask> list = byDayFlowTaskService.selectByDayFlowTaskList(byDayFlowTask);
        return getDataTable(list);
    }

    /**
     * 导出一日流程任务列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:export')")
    @Log(title = "一日流程任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayFlowTask byDayFlowTask)
    {
        List<ByDayFlowTask> list = byDayFlowTaskService.selectByDayFlowTaskList(byDayFlowTask);
        ExcelUtil<ByDayFlowTask> util = new ExcelUtil<ByDayFlowTask>(ByDayFlowTask.class);
        return util.exportExcel(list, "dayflowtask");
    }

    /**
     * 获取一日流程任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:query')")
    @GetMapping(value = "/{code}")
    public AjaxResult getInfo(@PathVariable("code") Long code)
    {
        return AjaxResult.success(byDayFlowTaskService.selectByDayFlowTaskByCode(code));
    }

    /**
     * 新增一日流程任务
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:add')")
    @Log(title = "一日流程任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayFlowTask byDayFlowTask)
    {
        byDayFlowTask.setCreateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayFlowTask.setUpdateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayFlowTask.setCreatetime(new Date());
        byDayFlowTask.setUpdatetime(new Date());
        return toAjax(byDayFlowTaskService.insertByDayFlowTask(byDayFlowTask));
    }

    /**
     * 修改一日流程任务
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:edit')")
    @Log(title = "一日流程任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayFlowTask byDayFlowTask)
    {
        byDayFlowTask.setUpdateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayFlowTask.setUpdatetime(new Date());
        return toAjax(byDayFlowTaskService.updateByDayFlowTask(byDayFlowTask));
    }

    /**
     * 删除一日流程任务
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowtask:remove')")
    @Log(title = "一日流程任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{codes}")
    public AjaxResult remove(@PathVariable Long[] codes)
    {
        return toAjax(byDayFlowTaskService.deleteByDayFlowTaskByCodes(codes));
    }
}
