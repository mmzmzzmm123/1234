package com.zeroone.crm.teacher.controller;

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
import com.zeroone.crm.teacher.domain.Teacher;
import com.zeroone.crm.teacher.service.ITeacherService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 老师信息Controller
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
@RestController
@RequestMapping("/teacher/teacher")
public class TeacherController extends BaseController
{
    @Autowired
    private ITeacherService teacherService;

    /**
     * 查询老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('teacher:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(Teacher teacher)
    {
        startPage();
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        return getDataTable(list);
    }

    /**
     * 导出老师信息列表
     */
    @PreAuthorize("@ss.hasPermi('teacher:teacher:export')")
    @Log(title = "老师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Teacher teacher)
    {
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        ExcelUtil<Teacher> util = new ExcelUtil<Teacher>(Teacher.class);
        util.exportExcel(response, list, "老师信息数据");
    }

    /**
     * 获取老师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('teacher:teacher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(teacherService.selectTeacherById(id));
    }

    /**
     * 新增老师信息
     */
    @PreAuthorize("@ss.hasPermi('teacher:teacher:add')")
    @Log(title = "老师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Teacher teacher)
    {
        return toAjax(teacherService.insertTeacher(teacher));
    }

    /**
     * 修改老师信息
     */
    @PreAuthorize("@ss.hasPermi('teacher:teacher:edit')")
    @Log(title = "老师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Teacher teacher)
    {
        return toAjax(teacherService.updateTeacher(teacher));
    }

    /**
     * 删除老师信息
     */
    @PreAuthorize("@ss.hasPermi('teacher:teacher:remove')")
    @Log(title = "老师信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(teacherService.deleteTeacherByIds(ids));
    }
}
