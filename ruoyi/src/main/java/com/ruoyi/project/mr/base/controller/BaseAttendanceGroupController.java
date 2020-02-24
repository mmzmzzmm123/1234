package com.ruoyi.project.mr.base.controller;

import java.util.List;

import com.ruoyi.common.utils.IdUtils;
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
import com.ruoyi.project.mr.base.domain.BaseAttendanceGroup;
import com.ruoyi.project.mr.base.service.IBaseAttendanceGroupService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 考勤组管理Controller
 *
 * @author mr
 * @date 2020-02-03
 */
@RestController
@RequestMapping("/base/group")
public class BaseAttendanceGroupController extends BaseController
{
    @Autowired
    private IBaseAttendanceGroupService baseAttendanceGroupService;

    /**
     * 查询考勤组管理列表
     */
    @PreAuthorize("@ss.hasPermi('base:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseAttendanceGroup baseAttendanceGroup)
    {
        startPage();
        List<BaseAttendanceGroup> list = baseAttendanceGroupService.selectBaseAttendanceGroupList(baseAttendanceGroup);
        return getDataTable(list);
    }

    /**
     * 导出考勤组管理列表
     */
    @PreAuthorize("@ss.hasPermi('base:group:export')")
    @Log(title = "考勤组管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BaseAttendanceGroup baseAttendanceGroup)
    {
        List<BaseAttendanceGroup> list = baseAttendanceGroupService.selectBaseAttendanceGroupList(baseAttendanceGroup);
        ExcelUtil<BaseAttendanceGroup> util = new ExcelUtil<BaseAttendanceGroup>(BaseAttendanceGroup.class);
        return util.exportExcel(list, "group");
    }

    /**
     * 获取考勤组管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:group:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(baseAttendanceGroupService.selectBaseAttendanceGroupById(id));
    }

    /**
     * 新增考勤组管理
     */
    @PreAuthorize("@ss.hasPermi('base:group:add')")
    @Log(title = "考勤组管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseAttendanceGroup baseAttendanceGroup)
    {
        baseAttendanceGroup.setId(IdUtils.fastUUID());
        return toAjax(baseAttendanceGroupService.insertBaseAttendanceGroup(baseAttendanceGroup));
    }

    /**
     * 修改考勤组管理
     */
    @PreAuthorize("@ss.hasPermi('base:group:edit')")
    @Log(title = "考勤组管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseAttendanceGroup baseAttendanceGroup)
    {
        return toAjax(baseAttendanceGroupService.updateBaseAttendanceGroup(baseAttendanceGroup));
    }

    /**
     * 删除考勤组管理
     */
    @PreAuthorize("@ss.hasPermi('base:group:remove')")
    @Log(title = "考勤组管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(baseAttendanceGroupService.deleteBaseAttendanceGroupByIds(ids));
    }
}
