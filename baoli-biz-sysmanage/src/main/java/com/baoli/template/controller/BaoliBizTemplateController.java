package com.baoli.template.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.baoli.template.domain.BaoliBizTemplate;
import com.baoli.template.service.IBaoliBizTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模板管理Controller
 * 
 * @author niujs
 * @date 2024-05-22
 */
@RestController
@RequestMapping("/sysmanage/template")
public class BaoliBizTemplateController extends BaseController
{
    @Autowired
    private IBaoliBizTemplateService baoliBizTemplateService;

    /**
     * 查询模板管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizTemplate baoliBizTemplate)
    {
        startPage();
        List<BaoliBizTemplate> list = baoliBizTemplateService.selectBaoliBizTemplateList(baoliBizTemplate);
        return getDataTable(list);
    }

    /**
     * 导出模板管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:template:export')")
    @Log(title = "模板管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizTemplate baoliBizTemplate)
    {
        List<BaoliBizTemplate> list = baoliBizTemplateService.selectBaoliBizTemplateList(baoliBizTemplate);
        ExcelUtil<BaoliBizTemplate> util = new ExcelUtil<BaoliBizTemplate>(BaoliBizTemplate.class);
        util.exportExcel(response, list, "模板管理数据");
    }

    /**
     * 获取模板管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizTemplateService.selectBaoliBizTemplateById(id));
    }

    /**
     * 新增模板管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:template:add')")
    @Log(title = "模板管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizTemplate baoliBizTemplate)
    {
        return toAjax(baoliBizTemplateService.insertBaoliBizTemplate(baoliBizTemplate));
    }

    /**
     * 修改模板管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:template:edit')")
    @Log(title = "模板管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizTemplate baoliBizTemplate)
    {
        return toAjax(baoliBizTemplateService.updateBaoliBizTemplate(baoliBizTemplate));
    }

    /**
     * 删除模板管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:template:remove')")
    @Log(title = "模板管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizTemplateService.deleteBaoliBizTemplateByIds(ids));
    }
}
