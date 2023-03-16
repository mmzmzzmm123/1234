package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourSection;
import com.ruoyi.course.service.ICourSectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/course/section")
@Api(value = "AppCourSectionController" ,tags = {"课程章节API"})
public class AppCourSectionController extends BaseController
{
    @Autowired
    private ICourSectionService courSectionService;

    /**
     * 查询课程章节列表
     */
//    @PreAuthorize("@ss.hasPermi('course:section:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程章节列表")
    public TableDataInfo list(CourSection courSection)
    {
        startPage();
        List<CourSection> list = courSectionService.selectCourSectionList(courSection);
        return getDataTable(list);
    }

    /**
     * 查询章节详情
     */
//    @PreAuthorize("@ss.hasPermi('course:section:query')")
    @PostMapping(value = "/getInfo")
    @ApiOperation("查询章节详情")
    public AjaxResult getInfo(@RequestParam(value = "id") Long id)
    {
        return AjaxResult.success(courSectionService.selectCourSectionById(id));
    }
}
