package com.ruoyi.web.controller.course;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.course.domain.CourSection;
import com.ruoyi.course.service.ICourSectionService;
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
 * 章节Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/course/section")
public class CourSectionController extends BaseController
{
    @Autowired
    private ICourSectionService courSectionService;

    /**
     * 查询章节列表
     */
    @PreAuthorize("@ss.hasPermi('course:section:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourSection courSection)
    {
        startPage();
        List<CourSection> list = courSectionService.selectCourSectionList(courSection);
        return getDataTable(list);
    }

    /**
     * 导出章节列表
     */
    @PreAuthorize("@ss.hasPermi('course:section:export')")
    @Log(title = "章节", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourSection courSection)
    {
        List<CourSection> list = courSectionService.selectCourSectionList(courSection);
        ExcelUtil<CourSection> util = new ExcelUtil<CourSection>(CourSection.class);
        util.exportExcel(response, list, "章节数据");
    }

    /**
     * 获取章节详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:section:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(courSectionService.selectCourSectionById(id));
    }

    /**
     * 新增章节
     */
    @PreAuthorize("@ss.hasPermi('course:section:add')")
    @Log(title = "章节", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourSection courSection)
    {
        return toAjax(courSectionService.insertCourSection(courSection));
    }

    /**
     * 修改章节
     */
    @PreAuthorize("@ss.hasPermi('course:section:edit')")
    @Log(title = "章节", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourSection courSection)
    {
        return toAjax(courSectionService.updateCourSection(courSection));
    }

    /**
     * 删除章节
     */
    @PreAuthorize("@ss.hasPermi('course:section:remove')")
    @Log(title = "章节", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(courSectionService.deleteCourSectionByIds(ids));
    }
}
