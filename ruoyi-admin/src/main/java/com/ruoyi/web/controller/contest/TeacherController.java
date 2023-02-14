package com.ruoyi.web.controller.contest;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.Teacher;
import com.ruoyi.contest.service.ITeacherService;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教师信息Controller
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contest/teacher")
public class TeacherController extends BaseController
{
    private final ITeacherService teacherService;

    /**
     * 查询教师信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(Teacher teacher)
    {
        startPage();
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        return getDataTable(list);
    }

    /**
     * 导出教师信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:teacher:export')")
    @Log(title = "教师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Teacher teacher)
    {
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        ExcelUtil<Teacher> util = new ExcelUtil<Teacher>(Teacher.class);
        util.exportExcel(response, list, "教师信息数据");
    }

    /**
     * 获取教师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('contest:teacher:query')")
    @GetMapping(value = "/{teacherId}")
    public AjaxResult getInfo(@PathVariable("teacherId") Long teacherId)
    {
        return success(teacherService.getById(teacherId));
    }

    /**
     * 新增教师信息
     */
    @PreAuthorize("@ss.hasPermi('contest:teacher:add')")
    @Log(title = "教师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Teacher teacher)
    {
        return toAjax(teacherService.save(teacher));
    }

    /**
     * 修改教师信息
     */
    @PreAuthorize("@ss.hasPermi('contest:teacher:edit')")
    @Log(title = "教师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Teacher teacher)
    {
        return toAjax(teacherService.updateById(teacher));
    }

    /**
     * 删除教师信息
     */
    @PreAuthorize("@ss.hasPermi('contest:teacher:remove')")
    @Log(title = "教师信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{teacherIds}")
    public AjaxResult remove(@PathVariable Long[] teacherIds)
    {
        return toAjax(teacherService.removeByIds(Arrays.asList(teacherIds)));
    }
}
