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
import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentTeacher;
import com.ruoyi.project.benyi.service.IByChildLearndevelopmentTeacherService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 儿童学习与发展档案（教师）Controller
 *
 * @author tsbz
 * @date 2020-08-10
 */
@RestController
@RequestMapping("/benyi/learndevelopmentteacher")
public class ByChildLearndevelopmentTeacherController extends BaseController {
    @Autowired
    private IByChildLearndevelopmentTeacherService byChildLearndevelopmentTeacherService;

    /**
     * 查询儿童学习与发展档案（教师）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentteacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        startPage();
        List<ByChildLearndevelopmentTeacher> list = byChildLearndevelopmentTeacherService.selectByChildLearndevelopmentTeacherList(byChildLearndevelopmentTeacher);
        return getDataTable(list);
    }

    /**
     * 导出儿童学习与发展档案（教师）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentteacher:export')")
    @Log(title = "儿童学习与发展档案（教师）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        List<ByChildLearndevelopmentTeacher> list = byChildLearndevelopmentTeacherService.selectByChildLearndevelopmentTeacherList(byChildLearndevelopmentTeacher);
        ExcelUtil<ByChildLearndevelopmentTeacher> util = new ExcelUtil<ByChildLearndevelopmentTeacher>(ByChildLearndevelopmentTeacher.class);
        return util.exportExcel(list, "teacher");
    }

    /**
     * 获取儿童学习与发展档案（教师）详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentteacher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byChildLearndevelopmentTeacherService.selectByChildLearndevelopmentTeacherById(id));
    }

    /**
     * 新增儿童学习与发展档案（教师）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentteacher:add')")
    @Log(title = "儿童学习与发展档案（教师）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        return toAjax(byChildLearndevelopmentTeacherService.insertByChildLearndevelopmentTeacher(byChildLearndevelopmentTeacher));
    }

    /**
     * 修改儿童学习与发展档案（教师）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentteacher:edit')")
    @Log(title = "儿童学习与发展档案（教师）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        return toAjax(byChildLearndevelopmentTeacherService.updateByChildLearndevelopmentTeacher(byChildLearndevelopmentTeacher));
    }

    /**
     * 删除儿童学习与发展档案（教师）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentteacher:remove')")
    @Log(title = "儿童学习与发展档案（教师）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byChildLearndevelopmentTeacherService.deleteByChildLearndevelopmentTeacherByIds(ids));
    }
}
