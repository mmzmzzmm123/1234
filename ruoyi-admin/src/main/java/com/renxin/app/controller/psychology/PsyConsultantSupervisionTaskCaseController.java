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
import com.renxin.psychology.domain.PsyConsultantSupervisionTaskCase;
import com.renxin.psychology.service.IPsyConsultantSupervisionTaskCaseService;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.common.core.page.TableDataInfo;

/**
 * 督导任务个案Controller
 * 
 * @author renxin
 * @date 2024-06-26
 */
@RestController
@RequestMapping("/system/case")
public class PsyConsultantSupervisionTaskCaseController extends BaseController
{
    @Autowired
    private IPsyConsultantSupervisionTaskCaseService psyConsultantSupervisionTaskCaseService;

    /**
     * 查询督导任务个案列表
     */
    @PreAuthorize("@ss.hasPermi('system:case:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        startPage();
        List<PsyConsultantSupervisionTaskCase> list = psyConsultantSupervisionTaskCaseService.selectPsyConsultantSupervisionTaskCaseList(psyConsultantSupervisionTaskCase);
        return getDataTable(list);
    }

    /**
     * 导出督导任务个案列表
     */
    @PreAuthorize("@ss.hasPermi('system:case:export')")
    @Log(title = "督导任务个案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        List<PsyConsultantSupervisionTaskCase> list = psyConsultantSupervisionTaskCaseService.selectPsyConsultantSupervisionTaskCaseList(psyConsultantSupervisionTaskCase);
        ExcelUtil<PsyConsultantSupervisionTaskCase> util = new ExcelUtil<PsyConsultantSupervisionTaskCase>(PsyConsultantSupervisionTaskCase.class);
        util.exportExcel(response, list, "督导任务个案数据");
    }

    /**
     * 获取督导任务个案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:case:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(psyConsultantSupervisionTaskCaseService.selectPsyConsultantSupervisionTaskCaseByTaskId(taskId));
    }

    /**
     * 新增督导任务个案
     */
    @PreAuthorize("@ss.hasPermi('system:case:add')")
    @Log(title = "督导任务个案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        return toAjax(psyConsultantSupervisionTaskCaseService.insertPsyConsultantSupervisionTaskCase(psyConsultantSupervisionTaskCase));
    }

    /**
     * 修改督导任务个案
     */
    @PreAuthorize("@ss.hasPermi('system:case:edit')")
    @Log(title = "督导任务个案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        return toAjax(psyConsultantSupervisionTaskCaseService.updatePsyConsultantSupervisionTaskCase(psyConsultantSupervisionTaskCase));
    }

    /**
     * 删除督导任务个案
     */
    @PreAuthorize("@ss.hasPermi('system:case:remove')")
    @Log(title = "督导任务个案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(psyConsultantSupervisionTaskCaseService.deletePsyConsultantSupervisionTaskCaseByTaskIds(taskIds));
    }
}
