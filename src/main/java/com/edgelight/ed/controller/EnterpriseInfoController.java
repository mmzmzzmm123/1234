package com.edgelight.ed.controller;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.edgelight.ed.domain.EnterpriseInfo;
import com.edgelight.ed.service.IEnterpriseInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业账号Controller
 * 
 * @author limingcai
 * @date 2021-04-09
 */
@RestController
@RequestMapping("/enterprise-direct/enterpriseinfo")
public class EnterpriseInfoController extends BaseController
{
    @Autowired
    private IEnterpriseInfoService enterpriseInfoService;

    /**
     * 查询企业账号列表
     */
    @PreAuthorize("@ss.hasPermi('enterprise-direct:enterpriseinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(EnterpriseInfo enterpriseInfo)
    {
        startPage();
        List<EnterpriseInfo> list = enterpriseInfoService.selectEnterpriseInfoList(enterpriseInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业账号列表
     */
    @PreAuthorize("@ss.hasPermi('enterprise-direct:enterpriseinfo:export')")
    @Log(title = "企业账号", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EnterpriseInfo enterpriseInfo)
    {
        List<EnterpriseInfo> list = enterpriseInfoService.selectEnterpriseInfoList(enterpriseInfo);
        ExcelUtil<EnterpriseInfo> util = new ExcelUtil<EnterpriseInfo>(EnterpriseInfo.class);
        return util.exportExcel(list, "enterpriseinfo");
    }

    /**
     * 获取企业账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('enterprise-direct:enterpriseinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(enterpriseInfoService.selectEnterpriseInfoById(id));
    }

    /**
     * 新增企业账号
     */
    @PreAuthorize("@ss.hasPermi('enterprise-direct:enterpriseinfo:add')")
    @Log(title = "企业账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return toAjax(enterpriseInfoService.insertEnterpriseInfo(enterpriseInfo));
    }

    /**
     * 修改企业账号
     */
    @PreAuthorize("@ss.hasPermi('enterprise-direct:enterpriseinfo:edit')")
    @Log(title = "企业账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return toAjax(enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo));
    }

    /**
     * 删除企业账号
     */
    @PreAuthorize("@ss.hasPermi('enterprise-direct:enterpriseinfo:remove')")
    @Log(title = "企业账号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(enterpriseInfoService.deleteEnterpriseInfoByIds(ids));
    }
}
