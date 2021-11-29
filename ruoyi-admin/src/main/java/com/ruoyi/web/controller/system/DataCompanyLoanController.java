package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.BzkZbhZrjgb;
import com.ruoyi.system.domain.ZtkZbhZrjgb;
import com.ruoyi.system.domain.ZtkZhdPfmxb;
import com.ruoyi.system.domain.model.CreditReport;
import com.ruoyi.system.service.IBzkZbhZrjgbService;
import com.ruoyi.system.service.IZtkZbhZrjgbService;
import com.ruoyi.system.service.IZtkZhdPfmxbService;
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
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.service.IDataCompanyLoanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业贷款信息Controller
 * 
 * @author genius
 * @date 2021-07-13
 */
@RestController
@RequestMapping("/system/loan")
public class DataCompanyLoanController extends BaseController
{
    @Autowired
    private IDataCompanyLoanService dataCompanyLoanService;
    @Autowired
    private IBzkZbhZrjgbService bzkZbhZrjgbService;
    @Autowired
    private IZtkZhdPfmxbService ztkZhdPfmxbService;
    @Autowired
    private IZtkZbhZrjgbService ztkZbhZrjgbService;

    /**
     * 查询企业贷款信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:loan:list')")
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
    @PreAuthorize("@ss.hasPermi('system:loan:export')")
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
    @PreAuthorize("@ss.hasPermi('system:loan:query')")
    @GetMapping(value = "/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        return AjaxResult.success(dataCompanyLoanService.selectDataCompanyLoanById(companyId));
    }

    /**
     * 新增企业贷款信息
     */
    @PreAuthorize("@ss.hasPermi('system:loan:add')")
    @Log(title = "企业贷款信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataCompanyLoan dataCompanyLoan)
    {
        return toAjax(dataCompanyLoanService.insertDataCompanyLoan(dataCompanyLoan));
    }

    /**
     * 修改企业贷款信息
     */
    @PreAuthorize("@ss.hasPermi('system:loan:edit')")
    @Log(title = "企业贷款信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataCompanyLoan dataCompanyLoan)
    {
        return toAjax(dataCompanyLoanService.updateDataCompanyLoan(dataCompanyLoan));
    }

    /**
     * 删除企业贷款信息
     */
    @PreAuthorize("@ss.hasPermi('system:loan:remove')")
    @Log(title = "企业贷款信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companyIds}")
    public AjaxResult remove(@PathVariable Long[] companyIds)
    {
        return toAjax(dataCompanyLoanService.deleteDataCompanyLoanByIds(companyIds));
    }

    @PreAuthorize("@ss.hasPermi('system:loan:report')")
    @GetMapping("/report/{tyshxydm}")
    public AjaxResult getCreditReport(@PathVariable String tyshxydm){

        CreditReport creditReport = new CreditReport();

        ZtkZbhZrjgb ztkZbhZrjgb = new ZtkZbhZrjgb();
        ztkZbhZrjgb.setTyshxydm(tyshxydm);

        ZtkZhdPfmxb ztkZhdPfmxb = new ZtkZhdPfmxb();
        ztkZhdPfmxb.setTyshxydm(tyshxydm);

        BzkZbhZrjgb bzkZbhZrjgb = new BzkZbhZrjgb();
        bzkZbhZrjgb.setTyshxydm(tyshxydm);

        List<BzkZbhZrjgb> bzkZbhZrjgbs = bzkZbhZrjgbService.selectBzkZbhZrjgbList(bzkZbhZrjgb);
        List<ZtkZhdPfmxb> ztkZhdPfmxbs = ztkZhdPfmxbService.selectZtkZhdPfmxbList(ztkZhdPfmxb);
        List<ZtkZbhZrjgb> ztkZbhZrjgbs = ztkZbhZrjgbService.selectZtkZbhZrjgbList(ztkZbhZrjgb);

        if (bzkZbhZrjgbs!=null && bzkZbhZrjgbs.size()>0){
            creditReport.setBzkZbhZrjgb(bzkZbhZrjgbs.get(0));
        }

        if (ztkZhdPfmxbs!=null && ztkZhdPfmxbs.size()>0){
            creditReport.setZtkZhdPfmxb(ztkZhdPfmxbs.get(0));
        }

        if (ztkZbhZrjgbs!=null && ztkZbhZrjgbs.size()>0){
            creditReport.setZtkZbhZrjgb(ztkZbhZrjgbs.get(0));
        }
        return AjaxResult.success(creditReport);
    }
}
