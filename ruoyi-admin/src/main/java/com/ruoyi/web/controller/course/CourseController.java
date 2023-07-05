package com.ruoyi.web.controller.course;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.service.ICoursePlanService;
import com.ruoyi.stu.service.ICourseService;
import com.ruoyi.stu.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/base")
public class CourseController extends BaseController{

    @Autowired
    private ICourseService courseService;

    @Anonymous
    @GetMapping("list")
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = courseService.selectAllCourses(course);
        return getDataTable(list);
    }

    @Anonymous
    @GetMapping("all")
    public AjaxResult all(Course course)
    {
        List<Course> list = courseService.selectAllCourses(course);
        return success(list);
    }

    @Anonymous
    @Log(title = "修改课程信息", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public AjaxResult update(@RequestBody Course course){
        return toAjax(courseService.updateCourse(course));
    }

    @Anonymous
    @Log(title = "添加课程信息", businessType = BusinessType.UPDATE)
    @PostMapping("add")
    public AjaxResult add(@RequestBody Course course){
        return toAjax(courseService.addCourse(course));
    }

    @Anonymous
    @Log(title = "修改课程信息", businessType = BusinessType.UPDATE)
    @PostMapping("del")
    public AjaxResult del(@RequestBody int[] ids){
        return toAjax(courseService.delCourse(ids));
    }
}
