package com.renxin.app.controller.course;

import com.renxin.common.annotation.RateLimiter;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.course.domain.CourCourseClass;
import com.renxin.course.service.ICourCourseClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/course/class")
@Api(value = "AppCourCourseClassController" ,tags = {"课程类型API"})
public class AppCourCourseClassController extends BaseController
{
    @Autowired
    private ICourCourseClassService courCourseClassService;

    /**
     * 查询课程类型列表
     */
//    @PreAuthorize("@ss.hasPermi('course:type:list')")
    @GetMapping("/list")
    @ApiOperation("查询课程类型列表")
    @RateLimiter
    public TableDataInfo list(CourCourseClass courCourseClass)
    {
        startPage();
        List<CourCourseClass> list = courCourseClassService.selectCourCourseClassList(courCourseClass);
        return getDataTable(list);
    }
}