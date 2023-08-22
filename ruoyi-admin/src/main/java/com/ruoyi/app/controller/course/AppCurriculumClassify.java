package com.ruoyi.app.controller.course;



import java.util.List;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.course.domain.CourCourseClass;
import com.ruoyi.course.service.ICourCourseClassService;
import com.ruoyi.course.service.ICourCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.web.controller.common.CommonCosController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/app/course")
public class AppCurriculumClassify extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonCosController.class);

    @Autowired
    private ICourCourseClassService courCourseClassService;

    @Autowired
    private ICourCourseService courCourseService;


    @GetMapping("/list")
    @ApiOperation("查询课程分类列表")
    @RateLimiter(limitType = LimitType.IP)
    public TableDataInfo list(CourCourseClass courCourseType)
    {
        startPage();
        List<CourCourseClass> list = courCourseClassService.selectCourCourseClassList(courCourseType);
        return getDataTable(list);
    }

}
