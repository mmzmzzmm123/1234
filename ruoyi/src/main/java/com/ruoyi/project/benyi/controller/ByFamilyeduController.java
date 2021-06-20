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
import com.ruoyi.project.benyi.domain.ByFamilyedu;
import com.ruoyi.project.benyi.service.IByFamilyeduService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 家庭教育Controller
 *
 * @author tsbz
 * @date 2021-06-20
 */
@RestController
@RequestMapping("/benyi/familyedu")
public class ByFamilyeduController extends BaseController {
    @Autowired
    private IByFamilyeduService byFamilyeduService;

    /**
     * 查询家庭教育列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:familyedu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByFamilyedu byFamilyedu) {
        startPage();
        List<ByFamilyedu> list = byFamilyeduService.selectByFamilyeduList(byFamilyedu);
        return getDataTable(list);
    }

    /**
     * 导出家庭教育列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:familyedu:export')")
    @Log(title = "家庭教育", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByFamilyedu byFamilyedu) {
        List<ByFamilyedu> list = byFamilyeduService.selectByFamilyeduList(byFamilyedu);
        ExcelUtil<ByFamilyedu> util = new ExcelUtil<ByFamilyedu>(ByFamilyedu.class);
        return util.exportExcel(list, "familyedu");
    }

    /**
     * 获取家庭教育详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:familyedu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byFamilyeduService.selectByFamilyeduById(id));
    }

    /**
     * 新增家庭教育
     */
    @PreAuthorize("@ss.hasPermi('benyi:familyedu:add')")
    @Log(title = "家庭教育", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByFamilyedu byFamilyedu) {
        return toAjax(byFamilyeduService.insertByFamilyedu(byFamilyedu));
    }

    /**
     * 修改家庭教育
     */
    @PreAuthorize("@ss.hasPermi('benyi:familyedu:edit')")
    @Log(title = "家庭教育", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByFamilyedu byFamilyedu) {
        return toAjax(byFamilyeduService.updateByFamilyedu(byFamilyedu));
    }

    /**
     * 删除家庭教育
     */
    @PreAuthorize("@ss.hasPermi('benyi:familyedu:remove')")
    @Log(title = "家庭教育", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byFamilyeduService.deleteByFamilyeduByIds(ids));
    }
}
