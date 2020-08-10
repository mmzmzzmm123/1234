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
import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentFamily;
import com.ruoyi.project.benyi.service.IByChildLearndevelopmentFamilyService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 儿童学习与发展档案（家长）Controller
 *
 * @author tsbz
 * @date 2020-08-10
 */
@RestController
@RequestMapping("/benyi/learndevelopmentfamily")
public class ByChildLearndevelopmentFamilyController extends BaseController {
    @Autowired
    private IByChildLearndevelopmentFamilyService byChildLearndevelopmentFamilyService;

    /**
     * 查询儿童学习与发展档案（家长）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        startPage();
        List<ByChildLearndevelopmentFamily> list = byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyList(byChildLearndevelopmentFamily);
        return getDataTable(list);
    }

    /**
     * 导出儿童学习与发展档案（家长）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:export')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        List<ByChildLearndevelopmentFamily> list = byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyList(byChildLearndevelopmentFamily);
        ExcelUtil<ByChildLearndevelopmentFamily> util = new ExcelUtil<ByChildLearndevelopmentFamily>(ByChildLearndevelopmentFamily.class);
        return util.exportExcel(list, "family");
    }

    /**
     * 获取儿童学习与发展档案（家长）详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyById(id));
    }

    /**
     * 新增儿童学习与发展档案（家长）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:add')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        return toAjax(byChildLearndevelopmentFamilyService.insertByChildLearndevelopmentFamily(byChildLearndevelopmentFamily));
    }

    /**
     * 修改儿童学习与发展档案（家长）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:edit')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        return toAjax(byChildLearndevelopmentFamilyService.updateByChildLearndevelopmentFamily(byChildLearndevelopmentFamily));
    }

    /**
     * 删除儿童学习与发展档案（家长）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:remove')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byChildLearndevelopmentFamilyService.deleteByChildLearndevelopmentFamilyByIds(ids));
    }
}
