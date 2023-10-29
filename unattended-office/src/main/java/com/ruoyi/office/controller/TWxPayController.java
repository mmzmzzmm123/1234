package com.ruoyi.office.controller;

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
import com.ruoyi.office.domain.TWxPay;
import com.ruoyi.office.service.ITWxPayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信支付配置Controller
 * 
 * @author ruoyi
 * @date 2023-10-25
 */
@RestController
@RequestMapping("/office/wxpay")
public class TWxPayController extends BaseController
{
    @Autowired
    private ITWxPayService tWxPayService;

    /**
     * 查询微信支付配置列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxpay:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxPay tWxPay)
    {
        startPage();
        List<TWxPay> list = tWxPayService.selectTWxPayList(tWxPay);
        return getDataTable(list);
    }

    /**
     * 导出微信支付配置列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxpay:export')")
    @Log(title = "微信支付配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxPay tWxPay)
    {
        List<TWxPay> list = tWxPayService.selectTWxPayList(tWxPay);
        ExcelUtil<TWxPay> util = new ExcelUtil<TWxPay>(TWxPay.class);
        util.exportExcel(response, list, "微信支付配置数据");
    }

    /**
     * 获取微信支付配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:wxpay:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tWxPayService.selectTWxPayById(id));
    }

    /**
     * 新增微信支付配置
     */
    @PreAuthorize("@ss.hasPermi('office:wxpay:add')")
    @Log(title = "微信支付配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxPay tWxPay)
    {
        return toAjax(tWxPayService.insertTWxPay(tWxPay));
    }

    /**
     * 修改微信支付配置
     */
    @PreAuthorize("@ss.hasPermi('office:wxpay:edit')")
    @Log(title = "微信支付配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxPay tWxPay)
    {
        return toAjax(tWxPayService.updateTWxPay(tWxPay));
    }

    /**
     * 删除微信支付配置
     */
    @PreAuthorize("@ss.hasPermi('office:wxpay:remove')")
    @Log(title = "微信支付配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tWxPayService.deleteTWxPayByIds(ids));
    }
}
