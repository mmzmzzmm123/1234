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
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.service.IByChildService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿信息Controller
 *
 * @author tsbz
 * @date 2020-07-20
 */
@RestController
@RequestMapping("/benyi/child")
public class ByChildController extends BaseController {
    @Autowired
    private IByChildService byChildService;

    /**
     * 查询幼儿信息列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByChild byChild) {
        startPage();
        List<ByChild> list = byChildService.selectByChildList(byChild);
        return getDataTable(list);
    }

    /**
     * 导出幼儿信息列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:export')")
    @Log(title = "幼儿信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChild byChild) {
        List<ByChild> list = byChildService.selectByChildList(byChild);
        ExcelUtil<ByChild> util = new ExcelUtil<ByChild>(ByChild.class);
        return util.exportExcel(list, "child");
    }

    /**
     * 获取幼儿信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byChildService.selectByChildById(id));
    }

    /**
     * 新增幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:add')")
    @Log(title = "幼儿信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChild byChild) {
        return toAjax(byChildService.insertByChild(byChild));
    }

    /**
     * 修改幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:edit')")
    @Log(title = "幼儿信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChild byChild) {
        return toAjax(byChildService.updateByChild(byChild));
    }

    /**
     * 删除幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:remove')")
    @Log(title = "幼儿信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byChildService.deleteByChildByIds(ids));
    }
}
