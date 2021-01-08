package com.gox.web.controller.system;

import java.util.List;

import com.gox.system.domain.vo.WSDefinition;
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
import com.gox.system.domain.MetadataRule;
import com.gox.system.service.IMetadataRuleService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

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
    @PreAuthorize("@ss.hasPermi('system:metadataRule:list')")
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
    @PreAuthorize("@ss.hasPermi('system:metadataRule:list')")
    @GetMapping("/definition/{nameCn}")
    public AjaxResult listDefinition(@PathVariable String nameCn)
    {
        List<WSDefinition> list = metadataRuleService.selectMetadata(nameCn);
        return AjaxResult.success(list);
    }

    /**
     * 导出元数据定义规则列表
     */
    @PreAuthorize("@ss.hasPermi('system:metadataRule:export')")
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
    @PreAuthorize("@ss.hasPermi('system:metadataRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(metadataRuleService.selectMetadataRuleById(id));
    }

    /**
     * 新增元数据定义规则
     */
    @PreAuthorize("@ss.hasPermi('system:metadataRule:add')")
    @Log(title = "元数据定义规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MetadataRule metadataRule)
    {
        return toAjax(metadataRuleService.insertMetadataRule(metadataRule));
    }

    /**
     * 修改元数据定义规则
     */
    @PreAuthorize("@ss.hasPermi('system:metadataRule:edit')")
    @Log(title = "元数据定义规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MetadataRule metadataRule)
    {
        return toAjax(metadataRuleService.updateMetadataRule(metadataRule));
    }

    /**
     * 删除元数据定义规则
     */
    @PreAuthorize("@ss.hasPermi('system:metadataRule:remove')")
    @Log(title = "元数据定义规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(metadataRuleService.deleteMetadataRuleByIds(ids));
    }
}
