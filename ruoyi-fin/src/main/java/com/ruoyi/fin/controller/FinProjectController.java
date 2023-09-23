package com.ruoyi.fin.controller;

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
import com.ruoyi.fin.domain.FinProject;
import com.ruoyi.fin.service.IFinProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 记账项目Controller
 * 
 * @author ruoyi
 * @date 2023-09-23
 */
@RestController
@RequestMapping("/fin/project")
public class FinProjectController extends BaseController
{
    @Autowired
    private IFinProjectService finProjectService;

    /**
     * 查询记账项目列表
     */
    @PreAuthorize("@ss.hasPermi('fin:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinProject finProject)
    {
        startPage();
        List<FinProject> list = finProjectService.selectFinProjectList(finProject);
        return getDataTable(list);
    }

    /**
     * 导出记账项目列表
     */
    @PreAuthorize("@ss.hasPermi('fin:project:export')")
    @Log(title = "记账项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinProject finProject)
    {
        List<FinProject> list = finProjectService.selectFinProjectList(finProject);
        ExcelUtil<FinProject> util = new ExcelUtil<FinProject>(FinProject.class);
        util.exportExcel(response, list, "记账项目数据");
    }

    /**
     * 获取记账项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('fin:project:query')")
    @GetMapping(value = "/{projId}")
    public AjaxResult getInfo(@PathVariable("projId") Long projId)
    {
        return success(finProjectService.selectFinProjectByProjId(projId));
    }

    /**
     * 新增记账项目
     */
    @PreAuthorize("@ss.hasPermi('fin:project:add')")
    @Log(title = "记账项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinProject finProject)
    {
        return toAjax(finProjectService.insertFinProject(finProject));
    }

    /**
     * 修改记账项目
     */
    @PreAuthorize("@ss.hasPermi('fin:project:edit')")
    @Log(title = "记账项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinProject finProject)
    {
        return toAjax(finProjectService.updateFinProject(finProject));
    }

    /**
     * 删除记账项目
     */
    @PreAuthorize("@ss.hasPermi('fin:project:remove')")
    @Log(title = "记账项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projIds}")
    public AjaxResult remove(@PathVariable Long[] projIds)
    {
        return toAjax(finProjectService.deleteFinProjectByProjIds(projIds));
    }
}
