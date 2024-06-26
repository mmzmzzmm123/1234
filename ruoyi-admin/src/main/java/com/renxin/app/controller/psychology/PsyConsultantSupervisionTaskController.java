package com.renxin.app.controller.psychology;

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
import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.enums.BusinessType;
import com.renxin.psychology.domain.PsyConsultantSupervisionTask;
import com.renxin.psychology.service.IPsyConsultantSupervisionTaskService;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.common.core.page.TableDataInfo;

/**
 * 咨询师团督任务Controller
 * 
 * @author renxin
 * @date 2024-06-26
 */
@RestController
@RequestMapping("/system/task")
public class PsyConsultantSupervisionTaskController extends BaseController
{
    @Autowired
    private IPsyConsultantSupervisionTaskService psyConsultantSupervisionTaskService;

    /**
     * 查询咨询师团督任务列表
     */
    @PreAuthorize("@ss.hasPermi('system:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantSupervisionTask psyConsultantSupervisionTask)
    {
        startPage();
        List<PsyConsultantSupervisionTask> list = psyConsultantSupervisionTaskService.selectPsyConsultantSupervisionTaskList(psyConsultantSupervisionTask);
        return getDataTable(list);
    }

    /**
     * 导出咨询师团督任务列表
     */
    @PreAuthorize("@ss.hasPermi('system:task:export')")
    @Log(title = "咨询师团督任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultantSupervisionTask psyConsultantSupervisionTask)
    {
        List<PsyConsultantSupervisionTask> list = psyConsultantSupervisionTaskService.selectPsyConsultantSupervisionTaskList(psyConsultantSupervisionTask);
        ExcelUtil<PsyConsultantSupervisionTask> util = new ExcelUtil<PsyConsultantSupervisionTask>(PsyConsultantSupervisionTask.class);
        util.exportExcel(response, list, "咨询师团督任务数据");
    }

    /**
     * 获取咨询师团督任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(psyConsultantSupervisionTaskService.selectPsyConsultantSupervisionTaskByTaskId(taskId));
    }

    /**
     * 新增咨询师团督任务
     */
    @PreAuthorize("@ss.hasPermi('system:task:add')")
    @Log(title = "咨询师团督任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultantSupervisionTask psyConsultantSupervisionTask)
    {
        return toAjax(psyConsultantSupervisionTaskService.insertPsyConsultantSupervisionTask(psyConsultantSupervisionTask));
    }

    /**
     * 修改咨询师团督任务
     */
    @PreAuthorize("@ss.hasPermi('system:task:edit')")
    @Log(title = "咨询师团督任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultantSupervisionTask psyConsultantSupervisionTask)
    {
        return toAjax(psyConsultantSupervisionTaskService.updatePsyConsultantSupervisionTask(psyConsultantSupervisionTask));
    }

    /**
     * 删除咨询师团督任务
     */
    @PreAuthorize("@ss.hasPermi('system:task:remove')")
    @Log(title = "咨询师团督任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(psyConsultantSupervisionTaskService.deletePsyConsultantSupervisionTaskByTaskIds(taskIds));
    }
}
