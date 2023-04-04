package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourCourseLabel;
import com.ruoyi.course.service.ICourCourseLabelService;
import com.ruoyi.course.service.ICourCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程标签Controller
 *
 * @author luzijun
 * @date 2023-03-16
 */
@RestController
@RequestMapping("/app/course/label")
@Api(value = "AppCourCourseLabelController" ,tags = {"课程标签API"})
public class AppCourCourseLabelController extends BaseController
{
    @Autowired
    private ICourCourseLabelService courCourseLabelService;

    @Autowired
    private ICourCourseService courCourseService;

    /**
     * 根据标签查询课程列表
     */
//    @PreAuthorize("@ss.hasPermi('course:label:list')")
    @PostMapping("/list")
    @ApiOperation("根据标签查询课程列表")
    public TableDataInfo list(@RequestBody CourCourseLabel courCourseLabel)
    {
        startPage();
        List<CourCourseLabel> list = courCourseLabelService.selectCourCourseLabelList(courCourseLabel);
        List<CourCourse> courCourseList = new ArrayList<>();
        list.forEach(item -> {
            courCourseList.add(courCourseService.selectCourCourseById(item.getId()));
        });

        return getDataTable(courCourseList);
    }

}
