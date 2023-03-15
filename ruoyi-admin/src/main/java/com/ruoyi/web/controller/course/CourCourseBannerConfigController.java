package com.ruoyi.web.controller.course;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.course.domain.CourCourseBannerConfig;
import com.ruoyi.course.service.ICourCourseBannerConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课程banner配置Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/course/config")
public class CourCourseBannerConfigController extends BaseController
{
    @Autowired
    private ICourCourseBannerConfigService courCourseBannerConfigService;

    /**
     * 查询课程banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('course:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourCourseBannerConfig courCourseBannerConfig)
    {
        startPage();
        List<CourCourseBannerConfig> list = courCourseBannerConfigService.selectCourCourseBannerConfigList(courCourseBannerConfig);
        return getDataTable(list);
    }

    /**
     * 导出课程banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('course:config:export')")
    @Log(title = "课程banner配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourCourseBannerConfig courCourseBannerConfig)
    {
        List<CourCourseBannerConfig> list = courCourseBannerConfigService.selectCourCourseBannerConfigList(courCourseBannerConfig);
        ExcelUtil<CourCourseBannerConfig> util = new ExcelUtil<CourCourseBannerConfig>(CourCourseBannerConfig.class);
        util.exportExcel(response, list, "课程banner配置数据");
    }

    /**
     * 获取课程banner配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(courCourseBannerConfigService.selectCourCourseBannerConfigById(id));
    }

    /**
     * 新增课程banner配置
     */
    @PreAuthorize("@ss.hasPermi('course:config:add')")
    @Log(title = "课程banner配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourCourseBannerConfig courCourseBannerConfig)
    {
        return toAjax(courCourseBannerConfigService.insertCourCourseBannerConfig(courCourseBannerConfig));
    }

    /**
     * 修改课程banner配置
     */
    @PreAuthorize("@ss.hasPermi('course:config:edit')")
    @Log(title = "课程banner配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourCourseBannerConfig courCourseBannerConfig)
    {
        return toAjax(courCourseBannerConfigService.updateCourCourseBannerConfig(courCourseBannerConfig));
    }

    /**
     * 删除课程banner配置
     */
    @PreAuthorize("@ss.hasPermi('course:config:remove')")
    @Log(title = "课程banner配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courCourseBannerConfigService.deleteCourCourseBannerConfigByIds(ids));
    }
}
