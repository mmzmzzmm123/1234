package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.office.domain.TWxUserAmount;
import com.ruoyi.office.service.ITWxUserAmountService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户余额Controller
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/office/wxuseramount")
public class TWxUserAmountController extends BaseController
{
    @Autowired
    private ITWxUserAmountService tWxUserAmountService;

    /**
     * 查询微信用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuseramount:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxUserAmount tWxUserAmount)
    {
        startPage();
        List<TWxUserAmount> list = tWxUserAmountService.selectTWxUserAmountList(tWxUserAmount);
        return getDataTable(list);
    }

    /**
     * 导出微信用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuseramount:export')")
    @Log(title = "微信用户余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxUserAmount tWxUserAmount)
    {
        List<TWxUserAmount> list = tWxUserAmountService.selectTWxUserAmountList(tWxUserAmount);
        ExcelUtil<TWxUserAmount> util = new ExcelUtil<TWxUserAmount>(TWxUserAmount.class);
        util.exportExcel(response, list, "微信用户余额数据");
    }

    /**
     * 获取微信用户余额详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:wxuseramount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tWxUserAmountService.selectTWxUserAmountById(id));
    }

    /**
     * 新增/充值 微信用户余额
     */
    @PreAuthorize("@ss.hasPermi('office:wxuseramount:add')")
    @Log(title = "微信用户余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxUserAmount tWxUserAmount)
    {
        tWxUserAmount.setCreateBy(SecurityUtils.getUserId()+"");
        return toAjax(tWxUserAmountService.insertTWxUserAmount(tWxUserAmount));
    }

    /**
     * 修改微信用户余额
     */
    @PreAuthorize("@ss.hasPermi('office:wxuseramount:edit')")
    @Log(title = "微信用户余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxUserAmount tWxUserAmount)
    {
        tWxUserAmount.setUpdateBy(SecurityUtils.getUserId()+"");
        return toAjax(tWxUserAmountService.updateTWxUserAmount(tWxUserAmount));
    }

    /**
     * 删除微信用户余额
     */
    @PreAuthorize("@ss.hasPermi('office:wxuseramount:remove')")
    @Log(title = "微信用户余额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tWxUserAmountService.deleteTWxUserAmountByIds(ids));
    }
}
