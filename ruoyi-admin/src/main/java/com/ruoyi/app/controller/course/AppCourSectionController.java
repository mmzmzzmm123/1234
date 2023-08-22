package com.ruoyi.app.controller.course;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourSection;
import com.ruoyi.course.service.ICourCourseService;
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

    @Autowired
    private ICourCourseService courCourseService;

    /**
     * 查询课程章节列表
     */
//    @PreAuthorize("@ss.hasPermi('course:section:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程章节列表")
    @RateLimiter
    public TableDataInfo list(@RequestBody CourSection courSection)
    {
        startPage();
        List<CourSection> list = courSectionService.selectCourSectionList(courSection);

        // 判断课程是否已经下单支付
        if (courCourseService.getPaidCourseCount(courSection.getCourseId()) == 0) { // 未下单支付
            list.forEach(item -> {
                if (item.getType() == 0) { // 普通课程禁止学习
                    item.setContentUrl(null);
                }
            });
        };

        return getDataTable(list);
    }

    /**
     * 查询章节详情
     */
//    @PreAuthorize("@ss.hasPermi('course:section:query')")
    @PostMapping(value = "/getInfo")
    @ApiOperation("查询章节详情")
    @RateLimiter
    public AjaxResult getInfo(@RequestParam(value = "id") Integer id)
    {
        return AjaxResult.success(courSectionService.selectCourSectionById(id));
    }
}
