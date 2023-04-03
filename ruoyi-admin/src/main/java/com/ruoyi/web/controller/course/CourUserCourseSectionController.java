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
import com.ruoyi.course.domain.CourUserCourseSection;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户-课程-章节关系Controller
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
@RestController
@RequestMapping("/course/userSection")
public class CourUserCourseSectionController extends BaseController
{
    @Autowired
    private ICourUserCourseSectionService courUserCourseSectionService;

    /**
     * 查询用户-课程-章节关系列表
     */
    @PreAuthorize("@ss.hasPermi('course:userSection:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourUserCourseSection courUserCourseSection)
    {
        startPage();
        List<CourUserCourseSection> list = courUserCourseSectionService.selectCourUserCourseSectionList(courUserCourseSection);
        return getDataTable(list);
    }

    /**
     * 导出用户-课程-章节关系列表
     */
    @PreAuthorize("@ss.hasPermi('course:userSection:export')")
    @Log(title = "用户-课程-章节关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourUserCourseSection courUserCourseSection)
    {
        List<CourUserCourseSection> list = courUserCourseSectionService.selectCourUserCourseSectionList(courUserCourseSection);
        ExcelUtil<CourUserCourseSection> util = new ExcelUtil<CourUserCourseSection>(CourUserCourseSection.class);
        util.exportExcel(response, list, "用户-课程-章节关系数据");
    }

    /**
     * 获取用户-课程-章节关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:userSection:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(courUserCourseSectionService.selectCourUserCourseSectionById(id));
    }

    /**
     * 新增用户-课程-章节关系
     */
    @PreAuthorize("@ss.hasPermi('course:userSection:add')")
    @Log(title = "用户-课程-章节关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourUserCourseSection courUserCourseSection)
    {
        return toAjax(courUserCourseSectionService.insertCourUserCourseSection(courUserCourseSection));
    }

    /**
     * 修改用户-课程-章节关系
     */
    @PreAuthorize("@ss.hasPermi('course:userSection:edit')")
    @Log(title = "用户-课程-章节关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourUserCourseSection courUserCourseSection)
    {
        return toAjax(courUserCourseSectionService.updateCourUserCourseSection(courUserCourseSection));
    }

    /**
     * 删除用户-课程-章节关系
     */
    @PreAuthorize("@ss.hasPermi('course:userSection:remove')")
    @Log(title = "用户-课程-章节关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(courUserCourseSectionService.deleteCourUserCourseSectionByIds(ids));
    }
}