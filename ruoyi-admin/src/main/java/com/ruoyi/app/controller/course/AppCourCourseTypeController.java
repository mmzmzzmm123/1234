package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourseType;
import com.ruoyi.course.service.ICourCourseTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/course/type")
@Api(value = "AppCourCourseTypeController" ,tags = {"课程类型API"})
public class AppCourCourseTypeController extends BaseController
{
    @Autowired
    private ICourCourseTypeService courCourseTypeService;

    /**
     * 查询课程类型列表
     */
//    @PreAuthorize("@ss.hasPermi('course:type:list')")
    @GetMapping("/list")
    @ApiOperation("查询课程类型列表")
    public TableDataInfo list(CourCourseType courCourseType)
    {
        startPage();
        List<CourCourseType> list = courCourseTypeService.selectCourCourseTypeList(courCourseType);
        return getDataTable(list);
    }
}
