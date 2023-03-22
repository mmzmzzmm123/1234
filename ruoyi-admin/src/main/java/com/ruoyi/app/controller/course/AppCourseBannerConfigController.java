package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourseBannerConfig;
import com.ruoyi.course.service.ICourCourseBannerConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测评banner配置Controller
 *
 * @author ruoyi
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/app/course/banner/config")
@Api(value = "AppCourseBannerConfigController" ,tags = {"课程banner页配置控制器"})
public class AppCourseBannerConfigController extends BaseController
{
    @Resource
    private ICourCourseBannerConfigService courCourseBannerConfigService;

    /**
     * 查询测评banner配置列表
     */
//    @PreAuthorize("@ss.hasPermi('banner:config:list')")
    @GetMapping("/list")
    @ApiOperation("查询课程banner配置列表")
    public TableDataInfo list(CourCourseBannerConfig courCourseBannerConfig)
    {
        startPage();
        List<CourCourseBannerConfig> list = courCourseBannerConfigService.selectCourCourseBannerConfigList(courCourseBannerConfig);
        return getDataTable(list);
    }
}
