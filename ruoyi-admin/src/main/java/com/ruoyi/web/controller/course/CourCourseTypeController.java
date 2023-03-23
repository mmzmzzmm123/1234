package com.ruoyi.web.controller.course;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.course.domain.CourCourseType;
import com.ruoyi.course.service.ICourCourseTypeService;
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
 * 课程类型Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/course/type")
public class CourCourseTypeController extends BaseController
{
    @Autowired
    private ICourCourseTypeService courCourseTypeService;

    /**
     * 查询课程类型列表
     */
    @PreAuthorize("@ss.hasPermi('course:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourCourseType courCourseType)
    {
        startPage();
        List<CourCourseType> list = courCourseTypeService.selectCourCourseTypeList(courCourseType);
        return getDataTable(list);
    }

    /**
     * 导出课程类型列表
     */
    @PreAuthorize("@ss.hasPermi('course:type:export')")
    @Log(title = "课程类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourCourseType courCourseType)
    {
        List<CourCourseType> list = courCourseTypeService.selectCourCourseTypeList(courCourseType);
        ExcelUtil<CourCourseType> util = new ExcelUtil<CourCourseType>(CourCourseType.class);
        util.exportExcel(response, list, "课程类型数据");
    }

    /**
     * 获取课程类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:type:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Integer typeId)
    {
        return AjaxResult.success(courCourseTypeService.selectCourCourseTypeByTypeId(typeId));
    }

    /**
     * 新增课程类型
     */
    @PreAuthorize("@ss.hasPermi('course:type:add')")
    @Log(title = "课程类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourCourseType courCourseType)
    {
        return toAjax(courCourseTypeService.insertCourCourseType(courCourseType));
    }

    /**
     * 修改课程类型
     */
    @PreAuthorize("@ss.hasPermi('course:type:edit')")
    @Log(title = "课程类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourCourseType courCourseType)
    {
        return toAjax(courCourseTypeService.updateCourCourseType(courCourseType));
    }

    /**
     * 删除课程类型
     */
    @PreAuthorize("@ss.hasPermi('course:type:remove')")
    @Log(title = "课程类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Integer[] typeIds)
    {
        return toAjax(courCourseTypeService.deleteCourCourseTypeByTypeIds(typeIds));
    }
}
