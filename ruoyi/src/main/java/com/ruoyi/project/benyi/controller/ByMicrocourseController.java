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
import com.ruoyi.project.benyi.domain.ByMicrocourse;
import com.ruoyi.project.benyi.service.IByMicrocourseService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 微型课程Controller
 *
 * @author tsbz
 * @date 2021-05-13
 */
@RestController
@RequestMapping("/benyi/microcourse")
public class ByMicrocourseController extends BaseController {
    @Autowired
    private IByMicrocourseService byMicrocourseService;

    /**
     * 查询微型课程列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:microcourse:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByMicrocourse byMicrocourse) {
        startPage();
        List<ByMicrocourse> list = byMicrocourseService.selectByMicrocourseList(byMicrocourse);
        return getDataTable(list);
    }

    /**
     * 查询微型课程列表
     */
    @Log(title = "微型课程学习", businessType = BusinessType.QUERY)
    @GetMapping("/treeselect")
    public AjaxResult treeselect(ByMicrocourse byMicrocourse) {
        List<ByMicrocourse> list = byMicrocourseService.selectByMicrocourseListTree(byMicrocourse);
        return AjaxResult.success(byMicrocourseService.buildMicrocourseTreeSelect(list));
    }

    /**
     * 导出微型课程列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:microcourse:export')")
    @Log(title = "微型课程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByMicrocourse byMicrocourse) {
        List<ByMicrocourse> list = byMicrocourseService.selectByMicrocourseList(byMicrocourse);
        ExcelUtil<ByMicrocourse> util = new ExcelUtil<ByMicrocourse>(ByMicrocourse.class);
        return util.exportExcel(list, "microcourse");
    }

    /**
     * 获取微型课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:microcourse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byMicrocourseService.selectByMicrocourseById(id));
    }

    /**
     * 新增微型课程
     */
    @PreAuthorize("@ss.hasPermi('benyi:microcourse:add')")
    @Log(title = "微型课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByMicrocourse byMicrocourse) {
        return toAjax(byMicrocourseService.insertByMicrocourse(byMicrocourse));
    }

    /**
     * 修改微型课程
     */
    @PreAuthorize("@ss.hasPermi('benyi:microcourse:edit')")
    @Log(title = "微型课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByMicrocourse byMicrocourse) {
        return toAjax(byMicrocourseService.updateByMicrocourse(byMicrocourse));
    }

    /**
     * 删除微型课程
     */
    @PreAuthorize("@ss.hasPermi('benyi:microcourse:remove')")
    @Log(title = "微型课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byMicrocourseService.deleteByMicrocourseByIds(ids));
    }
}
