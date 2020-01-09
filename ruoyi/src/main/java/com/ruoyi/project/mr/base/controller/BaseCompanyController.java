package com.ruoyi.project.mr.base.controller;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.mr.base.domain.BaseCompany;
import com.ruoyi.project.mr.base.service.IBaseCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司管理Controller
 *
 * @author mr
 * @date 2020-01-04
 */
@RestController
@RequestMapping("/base/company")
public class BaseCompanyController extends BaseController
{
    @Autowired
    private IBaseCompanyService baseCompanyService;

    /**
     * 查询公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('base:company:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseCompany baseCompany)
    {
        startPage();
        List<BaseCompany> list = baseCompanyService.selectBaseCompanyList(baseCompany);
        return getDataTable(list);
    }

    /**
     * 导出公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('base:company:export')")
    @Log(title = "公司管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BaseCompany baseCompany)
    {
        List<BaseCompany> list = baseCompanyService.selectBaseCompanyList(baseCompany);
        ExcelUtil<BaseCompany> util = new ExcelUtil<BaseCompany>(BaseCompany.class);
        return util.exportExcel(list, "公司信息");
    }

    /**
     * 获取公司管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:company:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(baseCompanyService.selectBaseCompanyById(id));
    }

    /**
     * 新增公司管理
     */
    @PreAuthorize("@ss.hasPermi('base:company:add')")
    @Log(title = "公司管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseCompany baseCompany)
    {
        baseCompany.setId(IdUtils.fastUUID());
        return toAjax(baseCompanyService.insertBaseCompany(baseCompany));
    }

    /**
     * 修改公司管理
     */
    @PreAuthorize("@ss.hasPermi('base:company:edit')")
    @Log(title = "公司管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseCompany baseCompany)
    {
        return toAjax(baseCompanyService.updateBaseCompany(baseCompany));
    }

    /**
     * 删除公司管理
     */
    @PreAuthorize("@ss.hasPermi('base:company:remove')")
    @Log(title = "公司管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(baseCompanyService.deleteBaseCompanyByIds(ids));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('base:company:edit')")
    @Log(title = "公司管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeEnabled")
    public AjaxResult changeEnabled(@RequestBody BaseCompany baseCompany)
    {
        baseCompany.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(baseCompanyService.updateBaseCompanyEnabled(baseCompany));
    }
}
