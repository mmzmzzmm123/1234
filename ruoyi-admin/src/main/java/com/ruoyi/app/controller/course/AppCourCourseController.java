package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.service.IPsyGaugeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程Controller
 *
 * @author luzijun
 * @date 2023-03-15
 */
@RestController
@RequestMapping("/app/course/course")
@Api(value = "AppCourCourseController" ,tags = {"课程API"})
public class AppCourCourseController extends BaseController
{
    @Autowired
    private ICourCourseService courCourseService;

    /**
     * 查询课程列表
     */
//    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程列表")
    public TableDataInfo list(@RequestBody CourCourse courCourse)
    {
        startPage();
        List<CourCourse> list = courCourseService.selectCourCourseList(courCourse);
        return getDataTable(list);
    }

    /**
     * 查询课程详情
     */
//    @PreAuthorize("@ss.hasPermi('course:course:query')")
    @PostMapping(value = "/getInfo")
    @ApiOperation("查询课程详情")
    public AjaxResult getInfo(@RequestParam(value = "id") Long id)
    {
        return AjaxResult.success(courCourseService.selectCourCourseById(id));
    }

}
