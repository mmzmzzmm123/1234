package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysRecipesTemplate;
import com.stdiet.custom.service.ISysRecipesTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 食谱模板
 *
 * @author wonder
 * @date 2021-02-27
 */
@RestController
@RequestMapping("/recipes/template")
public class SysRecipesTemplateController extends BaseController {
    @Autowired
    private ISysRecipesTemplateService iSysRecipesTemplateService;

    /**
     * 查询
     */
    @PreAuthorize("@ss.hasPermi('recipes:template:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SysRecipesTemplate sysRecipesTemplate) {
        startPage();
        List<SysRecipesTemplate> list = iSysRecipesTemplateService.selectRecipesTemplateListByCondition(sysRecipesTemplate);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('recipes:template:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return AjaxResult.success(iSysRecipesTemplateService.getRecipesTemplateById(id));
    }

    /**
     * 添加
     */
    @PreAuthorize("@ss.hasPermi('recipes:template:edit')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysRecipesTemplate sysRecipesTemplate) {
        Map<String, Long> result = iSysRecipesTemplateService.insertRecipsesTemplate(sysRecipesTemplate);
        if (StringUtils.isEmpty(result)) {
            return AjaxResult.error();
        }
        return AjaxResult.success(result);
    }

    /**
     * 更新
     */
    @PreAuthorize("@ss.hasPermi('recipes:template:list')")
    @PutMapping(value = "/update")
    public AjaxResult update(@RequestBody SysRecipesTemplate sysRecipesTemplate) {
        return toAjax(iSysRecipesTemplateService.updateRecipesTemplate(sysRecipesTemplate));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('recipes:template:query')")
    @DeleteMapping(value = "/delete/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        return AjaxResult.success(iSysRecipesTemplateService.removeRecipesTemplate(id));
    }


}