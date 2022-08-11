package com.ruoyi.classschedule.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.classschedule.domain.ClazzCourseTeacherVo;
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
import com.ruoyi.classschedule.domain.ClazzCourseTeacher;
import com.ruoyi.classschedule.service.IClazzCourseTeacherService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 老师_班级_课程Controller
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
@RestController
@RequestMapping("/class_schedule/class_schedule")
public class ClazzCourseTeacherController extends BaseController
{
    @Autowired
    private IClazzCourseTeacherService clazzCourseTeacherService;

    /**
     * 查询老师_班级_课程列表
     */
    @PreAuthorize("@ss.hasPermi('class_schedule:class_schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClazzCourseTeacherVo clazzCourseTeacher)
    {
        startPage();
        List<ClazzCourseTeacherVo> list = clazzCourseTeacherService.selectClazzCourseTeacherList(clazzCourseTeacher);
        return getDataTable(list);
    }

    /**
     * 导出老师_班级_课程列表
     */
    @PreAuthorize("@ss.hasPermi('class_schedule:class_schedule:export')")
    @Log(title = "老师_班级_课程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClazzCourseTeacherVo clazzCourseTeacher)
    {
        List<ClazzCourseTeacherVo> list = clazzCourseTeacherService.selectClazzCourseTeacherList(clazzCourseTeacher);
        ExcelUtil<ClazzCourseTeacherVo> util = new ExcelUtil<ClazzCourseTeacherVo>(ClazzCourseTeacherVo.class);
        util.exportExcel(response, list, "老师_班级_课程数据");
    }

    /**
     * 获取老师_班级_课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('class_schedule:class_schedule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(clazzCourseTeacherService.selectClazzCourseTeacherById(id));
    }

    /**
     * 新增老师_班级_课程
     */
    @PreAuthorize("@ss.hasPermi('class_schedule:class_schedule:add')")
    @Log(title = "老师_班级_课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClazzCourseTeacher clazzCourseTeacher)
    {
        return toAjax(clazzCourseTeacherService.insertClazzCourseTeacher(clazzCourseTeacher));
    }

    /**
     * 修改老师_班级_课程
     */
    @PreAuthorize("@ss.hasPermi('class_schedule:class_schedule:edit')")
    @Log(title = "老师_班级_课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClazzCourseTeacher clazzCourseTeacher)
    {
        return toAjax(clazzCourseTeacherService.updateClazzCourseTeacher(clazzCourseTeacher));
    }

    /**
     * 删除老师_班级_课程
     */
    @PreAuthorize("@ss.hasPermi('class_schedule:class_schedule:remove')")
    @Log(title = "老师_班级_课程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clazzCourseTeacherService.deleteClazzCourseTeacherByIds(ids));
    }
}
