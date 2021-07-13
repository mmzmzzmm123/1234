package com.ruoyi.app.controller;

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
import com.ruoyi.app.domain.DataCompanyLoan;
import com.ruoyi.app.service.IDataCompanyLoanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业贷款信息Controller
 * 
 * @author genius
 * @date 2021-07-13
 */
@RestController
@RequestMapping("/app/loan")
public class DataCompanyLoanController extends BaseController
{
    @Autowired
    private IDataCompanyLoanService dataCompanyLoanService;

    /**
     * 查询企业贷款信息列表
     */
    @PreAuthorize("@ss.hasPermi('app:loan:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataCompanyLoan dataCompanyLoan)
    {
        startPage();
        List<DataCompanyLoan> list = dataCompanyLoanService.selectDataCompanyLoanList(dataCompanyLoan);
        return getDataTable(list);
    }

    /**
     * 导出企业贷款信息列表
     */
    @PreAuthorize("@ss.hasPermi('app:loan:export')")
    @Log(title = "企业贷款信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataCompanyLoan dataCompanyLoan)
    {
        List<DataCompanyLoan> list = dataCompanyLoanService.selectDataCompanyLoanList(dataCompanyLoan);
        ExcelUtil<DataCompanyLoan> util = new ExcelUtil<DataCompanyLoan>(DataCompanyLoan.class);
        return util.exportExcel(list, "企业贷款信息数据");
    }

    /**
     * 获取企业贷款信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:loan:query')")
    @GetMapping(value = "/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        return AjaxResult.success(dataCompanyLoanService.selectDataCompanyLoanById(companyId));
    }

    /**
     * 新增企业贷款信息
     */
    @PreAuthorize("@ss.hasPermi('app:loan:add')")
    @Log(title = "企业贷款信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataCompanyLoan dataCompanyLoan)
    {
        return toAjax(dataCompanyLoanService.insertDataCompanyLoan(dataCompanyLoan));
    }

    /**
     * 修改企业贷款信息
     */
    @PreAuthorize("@ss.hasPermi('app:loan:edit')")
    @Log(title = "企业贷款信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataCompanyLoan dataCompanyLoan)
    {
        return toAjax(dataCompanyLoanService.updateDataCompanyLoan(dataCompanyLoan));
    }

    /**
     * 删除企业贷款信息
     */
    @PreAuthorize("@ss.hasPermi('app:loan:remove')")
    @Log(title = "企业贷款信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companyIds}")
    public AjaxResult remove(@PathVariable Long[] companyIds)
    {
        return toAjax(dataCompanyLoanService.deleteDataCompanyLoanByIds(companyIds));
    }
}
