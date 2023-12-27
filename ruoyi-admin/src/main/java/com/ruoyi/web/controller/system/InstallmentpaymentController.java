package com.ruoyi.web.controller.system;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.Srchecksheet;
import com.ruoyi.system.service.ISrchecksheetService;
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
import com.ruoyi.system.domain.Installmentpayment;
import com.ruoyi.system.service.IInstallmentpaymentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 查询Controller
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
@RestController
@RequestMapping("/system/installmentpayment")
public class InstallmentpaymentController extends BaseController
{
    @Autowired
    private IInstallmentpaymentService installmentpaymentService;

    @Autowired
    private ISrchecksheetService iSrchecksheetService;

    /**
     * 查询查询列表
     */
    @PreAuthorize("@ss.hasPermi('system:installmentpayment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Installmentpayment installmentpayment)
    {
        startPage();
        List<Installmentpayment> list = installmentpaymentService.selectInstallmentpaymentList(installmentpayment);
        return getDataTable(list);
    }

    /**
     * 导出查询列表
     */
    @PreAuthorize("@ss.hasPermi('system:installmentpayment:export')")
    @Log(title = "查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Installmentpayment installmentpayment)
    {
        List<Installmentpayment> list = installmentpaymentService.selectInstallmentpaymentList(installmentpayment);
        ExcelUtil<Installmentpayment> util = new ExcelUtil<Installmentpayment>(Installmentpayment.class);
        util.exportExcel(response, list, "查询数据");
    }

    /**
     * 获取查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:installmentpayment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(installmentpaymentService.selectInstallmentpaymentById(id));
    }

    /**
     * 新增查询
     */
    @PreAuthorize("@ss.hasPermi('system:installmentpayment:add')")
    @Log(title = "查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Installmentpayment installmentpayment)
    {
        return toAjax(installmentpaymentService.insertInstallmentpayment(installmentpayment));
    }

    /**
     * 修改查询
     */
    @PreAuthorize("@ss.hasPermi('system:installmentpayment:edit')")
    @Log(title = "查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Installmentpayment installmentpayment)
    {
        return toAjax(installmentpaymentService.updateInstallmentpayment(installmentpayment));
    }

    /**
     * 删除查询
     */
    @PreAuthorize("@ss.hasPermi('system:installmentpayment:remove')")
    @Log(title = "查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(installmentpaymentService.deleteInstallmentpaymentByIds(ids));
    }

    /**
     * 根据Id查询installmentpayment表记录
     */

    @PreAuthorize("@ss.hasPermi('system:installmentpayment:query')")
    @GetMapping("/getInstall/{id}")
    public TableDataInfo getInstall(@PathVariable("id") Long id)
    {
        startPage();
        List<Installmentpayment> install = installmentpaymentService.selectsrchecksheetId(id);
        double a = 0;
        for (Installmentpayment installmentpayment : install) {
            a = a+installmentpayment.getAmountpaid();
        }
        install.get(0).setReserve1(String.valueOf(a));
        return getDataTable(install);
    }

    @PreAuthorize("@ss.hasPermi('system:installmentpayment:query')")
    @GetMapping("/getSrcheck/{id}")
    public Srchecksheet getSrcheck(@PathVariable("id") Long id)
    {
        return iSrchecksheetService.selectSrchecksheetById(id);
    }
}
