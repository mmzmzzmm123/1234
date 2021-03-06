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
import com.gox.basic.domain.TemplatesPreserve;
import com.gox.basic.service.ITemplatesPreserveService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 * 模板维护Controller
 * 
 * @author gox
 * @date 2021-03-06
 */
@RestController
@RequestMapping("/basic/templates")
public class TemplatesPreserveController extends BaseController
{
    @Autowired
    private ITemplatesPreserveService templatesPreserveService;

    /**
     * 查询模板维护列表
     */
    @PreAuthorize("@ss.hasPermi('basic:templates:list')")
    @GetMapping("/list")
    public TableDataInfo list(TemplatesPreserve templatesPreserve)
    {
        startPage();
        List<TemplatesPreserve> list = templatesPreserveService.selectTemplatesPreserveList(templatesPreserve);
        return getDataTable(list);
    }

    /**
     * 导出模板维护列表
     */
    @PreAuthorize("@ss.hasPermi('basic:templates:export')")
    @Log(title = "模板维护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TemplatesPreserve templatesPreserve)
    {
        List<TemplatesPreserve> list = templatesPreserveService.selectTemplatesPreserveList(templatesPreserve);
        ExcelUtil<TemplatesPreserve> util = new ExcelUtil<TemplatesPreserve>(TemplatesPreserve.class);
        return util.exportExcel(list, "preserve");
    }

    /**
     * 获取模板维护详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:templates:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(templatesPreserveService.selectTemplatesPreserveById(id));
    }

    /**
     * 新增模板维护
     */
    @PreAuthorize("@ss.hasPermi('basic:templates:add')")
    @Log(title = "模板维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TemplatesPreserve templatesPreserve)
    {
        return toAjax(templatesPreserveService.insertTemplatesPreserve(templatesPreserve));
    }

    /**
     * 修改模板维护
     */
    @PreAuthorize("@ss.hasPermi('basic:templates:edit')")
    @Log(title = "模板维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TemplatesPreserve templatesPreserve)
    {
        return toAjax(templatesPreserveService.updateTemplatesPreserve(templatesPreserve));
    }

    /**
     * 删除模板维护
     */
    @PreAuthorize("@ss.hasPermi('basic:templates:remove')")
    @Log(title = "模板维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(templatesPreserveService.deleteTemplatesPreserveByIds(ids));
    }
}
