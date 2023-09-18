package com.ruoyi.web.controller.course;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.dto.CourseQueryDTO;
import com.ruoyi.course.service.ICourCourseService;
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
 * 课程Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/course")
public class CourCourseController extends BaseController
{
    @Autowired
    private ICourCourseService courCourseService;

    /**
     * 根据条件询课程列表
     */
    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @GetMapping("/list")
    public TableDataInfo query(CourseQueryDTO courseQueryDTO)
    {
        startPage();
        List<CourCourse> list = courCourseService.queryCourCourseList(courseQueryDTO);
        return getDataTable(list);
    }

    /**
     * 导出课程列表
     */
    @PreAuthorize("@ss.hasPermi('course:course:export')")
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourCourse courCourse)
    {
        List<CourCourse> list = courCourseService.selectCourCourseList(courCourse);
        ExcelUtil<CourCourse> util = new ExcelUtil<CourCourse>(CourCourse.class);
        util.exportExcel(response, list, "课程数据");
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(courCourseService.selectCourCourseById(id));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('course:course:add')")
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourCourse courCourse)
    {
        try {
            int res = courCourseService.insertCourCourse(courCourse);
            return AjaxResult.success(res);
        } catch (Exception e) {
            return AjaxResult.error(500, "新增课程失败");
        }

    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('course:course:edit')")
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourCourse courCourse)
    {
        try {
            int res = courCourseService.updateCourCourse(courCourse);
            return AjaxResult.success(res);
        } catch (Exception e) {
            return AjaxResult.error(500, "修改课程失败");
        }
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('course:course:remove')")
    @Log(title = "课程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        try {
            int res = courCourseService.deleteCourCourseByIds(ids);
            return AjaxResult.success(res);
        } catch (Exception e) {
            return AjaxResult.error(500, "删除课程失败");
        }
    }
}
