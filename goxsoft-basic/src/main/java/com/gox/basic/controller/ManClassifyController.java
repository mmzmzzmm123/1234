package com.gox.basic.controller;

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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.basic.domain.ManClassify;
import com.gox.basic.service.IManClassifyService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 * 分类管理Controller
 * 
 * @author gox
 * @date 2021-03-09
 */
@RestController
@RequestMapping("/system/classify")
public class ManClassifyController extends BaseController
{
    @Autowired
    private IManClassifyService manClassifyService;

    /**
     * 查询分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:classify:list')")
    @GetMapping("/list")
    public TableDataInfo list(ManClassify manClassify)
    {
        startPage();
        List<ManClassify> list = manClassifyService.selectManClassifyList(manClassify);
        return getDataTable(list);
    }

    /**
     * 导出分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:classify:export')")
    @Log(title = "分类管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ManClassify manClassify)
    {
        List<ManClassify> list = manClassifyService.selectManClassifyList(manClassify);
        ExcelUtil<ManClassify> util = new ExcelUtil<ManClassify>(ManClassify.class);
        return util.exportExcel(list, "classify");
    }

    /**
     * 获取分类管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:classify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(manClassifyService.selectManClassifyById(id));
    }

    /**
     * 新增分类管理
     */
    @PreAuthorize("@ss.hasPermi('system:classify:add')")
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ManClassify manClassify)
    {
        return toAjax(manClassifyService.insertManClassify(manClassify));
    }

    /**
     * 修改分类管理
     */
    @PreAuthorize("@ss.hasPermi('system:classify:edit')")
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ManClassify manClassify)
    {
        return toAjax(manClassifyService.updateManClassify(manClassify));
    }

    /**
     * 删除分类管理
     */
    @PreAuthorize("@ss.hasPermi('system:classify:remove')")
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(manClassifyService.deleteManClassifyByIds(ids));
    }
}
