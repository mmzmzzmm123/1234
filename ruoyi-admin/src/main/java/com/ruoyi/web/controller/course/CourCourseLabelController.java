package com.ruoyi.web.controller.course;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.course.domain.CourCourseLabel;
import com.ruoyi.course.service.ICourCourseLabelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * labelController
 * 
 * @author ruoyi
 * @date 2023-03-16
 */
@RestController
@RequestMapping("/course/label")
public class CourCourseLabelController extends BaseController
{
    @Autowired
    private ICourCourseLabelService courCourseLabelService;

    /**
     * 查询label列表
     */
    @PreAuthorize("@ss.hasPermi('course:label:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourCourseLabel courCourseLabel)
    {
        startPage();
        List<CourCourseLabel> list = courCourseLabelService.selectCourCourseLabelList(courCourseLabel);
        return getDataTable(list);
    }

    /**
     * 导出label列表
     */
    @PreAuthorize("@ss.hasPermi('course:label:export')")
    @Log(title = "label", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourCourseLabel courCourseLabel)
    {
        List<CourCourseLabel> list = courCourseLabelService.selectCourCourseLabelList(courCourseLabel);
        ExcelUtil<CourCourseLabel> util = new ExcelUtil<CourCourseLabel>(CourCourseLabel.class);
        util.exportExcel(response, list, "label数据");
    }

    /**
     * 获取label详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:label:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(courCourseLabelService.selectCourCourseLabelById(id));
    }

    /**
     * 新增label
     */
    @PreAuthorize("@ss.hasPermi('course:label:add')")
    @Log(title = "label", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourCourseLabel courCourseLabel)
    {
        return toAjax(courCourseLabelService.insertCourCourseLabel(courCourseLabel));
    }

    /**
     * 修改label
     */
    @PreAuthorize("@ss.hasPermi('course:label:edit')")
    @Log(title = "label", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourCourseLabel courCourseLabel)
    {
        return toAjax(courCourseLabelService.updateCourCourseLabel(courCourseLabel));
    }

    /**
     * 删除label
     */
    @PreAuthorize("@ss.hasPermi('course:label:remove')")
    @Log(title = "label", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courCourseLabelService.deleteCourCourseLabelByIds(ids));
    }
}
