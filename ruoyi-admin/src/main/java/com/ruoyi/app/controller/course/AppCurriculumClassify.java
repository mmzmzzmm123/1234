package com.ruoyi.app.controller.course;


import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourseClass;
import com.ruoyi.course.service.ICourCourseClassService;
import com.ruoyi.web.controller.common.CommonCosController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/app/course")
public class AppCurriculumClassify extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonCosController.class);

    @Autowired
    private ICourCourseClassService courCourseClassService;


    @GetMapping("/list")
    @ApiOperation("查询课程分类列表")
    @RateLimiter
    public TableDataInfo list(CourCourseClass courCourseType)
    {
        startPage();
        List<CourCourseClass> list = courCourseClassService.selectCourCourseClassList(courCourseType);
        return getDataTable(list);
    }

}
