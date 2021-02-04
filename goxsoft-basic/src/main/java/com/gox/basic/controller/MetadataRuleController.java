package com.gox.basic.controller;

import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.page.TableDataInfo;
import com.gox.common.enums.BusinessType;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.basic.domain.MetadataRule;
import com.gox.basic.domain.vo.WSDefinition;
import com.gox.basic.service.IMetadataRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 元数据定义规则Controller
 * 
 * @author gox
 * @date 2021-01-05
 */
@RestController
@RequestMapping("/system/metadataRule")
public class MetadataRuleController extends BaseController
{
    @Autowired
    private IMetadataRuleService metadataRuleService;

    /**
     * 查询元数据定义规则列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(MetadataRule metadataRule)
    {
        startPage();
        List<MetadataRule> list = metadataRuleService.selectMetadataRuleList(metadataRule);
        return getDataTable(list);
    }
    /**
     * 查询元数据定义规则列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:list')")
    @GetMapping("/definition/{nameCn}")
    public AjaxResult listDefinition(@PathVariable String nameCn)
    {
        List<WSDefinition> list = metadataRuleService.selectMetadata(nameCn);
        return AjaxResult.success(list);
    }

    /**
     * 导出元数据定义规则列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:export')")
    @Log(title = "元数据定义规则", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MetadataRule metadataRule)
    {
        List<MetadataRule> list = metadataRuleService.selectMetadataRuleList(metadataRule);
        ExcelUtil<MetadataRule> util = new ExcelUtil<MetadataRule>(MetadataRule.class);
        return util.exportExcel(list, "metadataRule");
    }

    /**
     * 获取元数据定义规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(metadataRuleService.selectMetadataRuleById(id));
    }

    /**
     * 新增元数据定义规则
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:add')")
    @Log(title = "元数据定义规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MetadataRule metadataRule)
    {
        return toAjax(metadataRuleService.insertMetadataRule(metadataRule));
    }

    /**
     * 修改元数据定义规则
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:edit')")
    @Log(title = "元数据定义规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MetadataRule metadataRule)
    {
        return toAjax(metadataRuleService.updateMetadataRule(metadataRule));
    }

    /**
     * 删除元数据定义规则
     */
    @PreAuthorize("@ss.hasPermi('basic:metadataRule:remove')")
    @Log(title = "元数据定义规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(metadataRuleService.deleteMetadataRuleByIds(ids));
    }
}
