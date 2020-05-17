package com.ruoyi.project.system.controller;

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
import com.ruoyi.project.system.domain.PCommForm;
import com.ruoyi.project.system.service.IPCommFormService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 数据模型Controller
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
@RestController
@RequestMapping("/system/form")
public class PCommFormController extends BaseController
{
    @Autowired
    private IPCommFormService pCommFormService;

    /**
     * 查询数据模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:form:list')")
    @GetMapping("/list")
    public TableDataInfo list(PCommForm pCommForm)
    {
        startPage();
        List<PCommForm> list = pCommFormService.selectPCommFormList(pCommForm);
        return getDataTable(list);
    }

    /**
     * 导出数据模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:form:export')")
    @Log(title = "数据模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PCommForm pCommForm)
    {
        List<PCommForm> list = pCommFormService.selectPCommFormList(pCommForm);
        ExcelUtil<PCommForm> util = new ExcelUtil<PCommForm>(PCommForm.class);
        return util.exportExcel(list, "form");
    }

    /**
     * 获取数据模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:form:query')")
    @GetMapping(value = "/{formId}")
    public AjaxResult getInfo(@PathVariable("formId") Long formId)
    {
        return AjaxResult.success(pCommFormService.selectPCommFormById(formId));
    }

    /**
     * 新增数据模型
     */
    @PreAuthorize("@ss.hasPermi('system:form:add')")
    @Log(title = "数据模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PCommForm pCommForm)
    {
        return toAjax(pCommFormService.insertPCommForm(pCommForm));
    }

    /**
     * 修改数据模型
     */
    @PreAuthorize("@ss.hasPermi('system:form:edit')")
    @Log(title = "数据模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PCommForm pCommForm)
    {
        return toAjax(pCommFormService.updatePCommForm(pCommForm));
    }

    /**
     * 删除数据模型
     */
    @PreAuthorize("@ss.hasPermi('system:form:remove')")
    @Log(title = "数据模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{formIds}")
    public AjaxResult remove(@PathVariable Long[] formIds)
    {
        return toAjax(pCommFormService.deletePCommFormByIds(formIds));
    }
}
