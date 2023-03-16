package com.ruoyi.app.controller.course;



import java.util.List;

import com.ruoyi.course.domain.CourCourseType;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.course.service.ICourCourseTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.web.controller.common.CommonCosController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/app/course")
public class AppCurriculumClassify extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonCosController.class);

    @Autowired
    private ICourCourseTypeService courCourseTypeService;

    @Autowired
    private ICourCourseService courCourseService;


    @GetMapping("/list")
    @ApiOperation("查询课程分类列表")
    public TableDataInfo list(CourCourseType courCourseType)
    {
        startPage();
        List<CourCourseType> list = courCourseTypeService.selectCourCourseTypeList(courCourseType);
        return getDataTable(list);
    }

}
