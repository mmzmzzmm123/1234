package com.ruoyi.system.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.system.domain.SysProject;
import com.ruoyi.system.service.ISysProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 項目Controller
 * 
 * @author ruoyi
 * @date 2021-03-01
 */
@RestController
@RequestMapping("/system/project")
public class SysProjectController extends BaseController
{
    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询項目列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProject sysProject)
    {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        for (SysProject project: list) {
            SysDept sysDept = deptService.selectDeptById(project.getDeptId());
            project.setDeptName(sysDept.getDeptName());
            SysUser user = userService.selectUserById(project.getUserId());
            project.setUserName(user.getUserName() + "(" + user.getNickName() + ")");
        }
        return getDataTable(list);
    }

    /**
     * 导出項目列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:export')")
    @Log(title = "項目", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysProject sysProject)
    {
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        return util.exportExcel(list, "project");
    }

    /**
     * 获取項目详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysProjectService.selectSysProjectById(id));
    }

    /**
     * 新增項目
     */
    @PreAuthorize("@ss.hasPermi('system:project:add')")
    @Log(title = "項目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProject sysProject)
    {
        return toAjax(sysProjectService.insertSysProject(sysProject));
    }

    /**
     * 修改項目
     */
    @PreAuthorize("@ss.hasPermi('system:project:edit')")
    @Log(title = "項目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProject sysProject)
    {
        return toAjax(sysProjectService.updateSysProject(sysProject));
    }

    /**
     * 删除項目
     */
    @PreAuthorize("@ss.hasPermi('system:project:remove')")
    @Log(title = "項目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProjectService.deleteSysProjectByIds(ids));
    }
}
