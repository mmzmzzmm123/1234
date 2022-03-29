package com.ruoyi.pay.controller;

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
import com.ruoyi.pay.domain.SysPay;
import com.ruoyi.pay.service.ISysPayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 支付相关Controller
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
@RestController
@RequestMapping("/pay/wxPay")
public class SysPayController extends BaseController
{
    @Autowired
    private ISysPayService sysPayService;

    /**
     * 查询支付相关列表
     */
    @PreAuthorize("@ss.hasPermi('pay:wxPay:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPay sysPay)
    {
        startPage();
        List<SysPay> list = sysPayService.selectSysPayList(sysPay);
        return getDataTable(list);
    }

    /**
     * 导出支付相关列表
     */
    @PreAuthorize("@ss.hasPermi('pay:wxPay:export')")
    @Log(title = "支付相关", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPay sysPay)
    {
        List<SysPay> list = sysPayService.selectSysPayList(sysPay);
        ExcelUtil<SysPay> util = new ExcelUtil<SysPay>(SysPay.class);
        util.exportExcel(response, list, "支付相关数据");
    }

    /**
     * 获取支付相关详细信息
     */
    @PreAuthorize("@ss.hasPermi('pay:wxPay:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysPayService.selectSysPayById(id));
    }

    /**
     * 新增支付相关
     */
    @PreAuthorize("@ss.hasPermi('pay:wxPay:add')")
    @Log(title = "支付相关", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPay sysPay)
    {
        return toAjax(sysPayService.insertSysPay(sysPay));
    }

    /**
     * 修改支付相关
     */
    @PreAuthorize("@ss.hasPermi('pay:wxPay:edit')")
    @Log(title = "支付相关", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPay sysPay)
    {
        return toAjax(sysPayService.updateSysPay(sysPay));
    }

    /**
     * 删除支付相关
     */
    @PreAuthorize("@ss.hasPermi('pay:wxPay:remove')")
    @Log(title = "支付相关", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysPayService.deleteSysPayByIds(ids));
    }
}
