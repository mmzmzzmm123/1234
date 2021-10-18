package com.ruoyi.project.benyi.controller;

import java.util.List;

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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByAdmissioncourse;
import com.ruoyi.project.benyi.service.IByAdmissioncourseService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 入学准备课程Controller
 *
 * @author tsbz
 * @date 2021-10-17
 */
@RestController
@RequestMapping("/benyi/admissioncourse")
public class ByAdmissioncourseController extends BaseController {
    @Autowired
    private IByAdmissioncourseService byAdmissioncourseService;

    /**
     * 查询入学准备课程列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:admissioncourse:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByAdmissioncourse byAdmissioncourse) {
        startPage();
        List<ByAdmissioncourse> list = byAdmissioncourseService.selectByAdmissioncourseList(byAdmissioncourse);
        return getDataTable(list);
    }

    /**
     * 查询入学准备课程列表
     */
    @Log(title = "入学准备课程学习", businessType = BusinessType.QUERY)
    @GetMapping("/treeselect")
    public AjaxResult treeselect(ByAdmissioncourse byAdmissioncourse) {
        List<ByAdmissioncourse> list = byAdmissioncourseService.selectByAdmissioncourseListTree(byAdmissioncourse);
        return AjaxResult.success(byAdmissioncourseService.buildByAdmissioncourseTreeSelect(list));
    }

    /**
     * 导出入学准备课程列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:admissioncourse:export')")
    @Log(title = "入学准备课程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAdmissioncourse byAdmissioncourse) {
        List<ByAdmissioncourse> list = byAdmissioncourseService.selectByAdmissioncourseList(byAdmissioncourse);
        ExcelUtil<ByAdmissioncourse> util = new ExcelUtil<ByAdmissioncourse>(ByAdmissioncourse.class);
        return util.exportExcel(list, "admissioncourse");
    }

    /**
     * 获取入学准备课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:admissioncourse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byAdmissioncourseService.selectByAdmissioncourseById(id));
    }

    /**
     * 新增入学准备课程
     */
    @PreAuthorize("@ss.hasPermi('benyi:admissioncourse:add')")
    @Log(title = "入学准备课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByAdmissioncourse byAdmissioncourse) {
        return toAjax(byAdmissioncourseService.insertByAdmissioncourse(byAdmissioncourse));
    }

    /**
     * 修改入学准备课程
     */
    @PreAuthorize("@ss.hasPermi('benyi:admissioncourse:edit')")
    @Log(title = "入学准备课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAdmissioncourse byAdmissioncourse) {
        return toAjax(byAdmissioncourseService.updateByAdmissioncourse(byAdmissioncourse));
    }

    /**
     * 删除入学准备课程
     */
    @PreAuthorize("@ss.hasPermi('benyi:admissioncourse:remove')")
    @Log(title = "入学准备课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byAdmissioncourseService.deleteByAdmissioncourseByIds(ids));
    }
}
