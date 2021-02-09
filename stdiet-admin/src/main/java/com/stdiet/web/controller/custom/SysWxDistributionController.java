package com.stdiet.web.controller.custom;

import java.util.List;

import org.aspectj.weaver.loadtime.Aj;
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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysWxDistribution;
import com.stdiet.custom.service.ISysWxDistributionService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 微信分配管理Controller
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@RestController
@RequestMapping("/custom/wxDistribution")
public class SysWxDistributionController extends BaseController
{
    @Autowired
    private ISysWxDistributionService sysWxDistributionService;

    /**
     * 查询微信分配管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxDistribution:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxDistribution sysWxDistribution)
    {
        startPage();
        List<SysWxDistribution> list = sysWxDistributionService.selectSysWxDistributionList(sysWxDistribution);
        return getDataTable(list);
    }

    /**
     * 导出微信分配管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxDistribution:export')")
    @Log(title = "微信分配管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxDistribution sysWxDistribution)
    {
        List<SysWxDistribution> list = sysWxDistributionService.selectSysWxDistributionList(sysWxDistribution);
        ExcelUtil<SysWxDistribution> util = new ExcelUtil<SysWxDistribution>(SysWxDistribution.class);
        return util.exportExcel(list, "wxDistribution");
    }

    /**
     * 获取微信分配管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxDistribution:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysWxDistributionService.selectSysWxDistributionById(id));
    }

    /**
     * 新增微信分配管理
     */
    @PreAuthorize("@ss.hasPermi('custom:wxDistribution:add')")
    @Log(title = "微信分配管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxDistribution sysWxDistribution)
    {
        if(sysWxDistribution.getWechatAccount() != null){
            SysWxDistribution wxIdDistribution  = sysWxDistributionService.selectWxDistributionByWxId(sysWxDistribution.getWechatAccount());
            if(wxIdDistribution != null){
                return AjaxResult.error("该微信号已分配，无法重复分配");
            }
        }
        return toAjax(sysWxDistributionService.insertSysWxDistribution(sysWxDistribution));
    }

    /**
     * 修改微信分配管理
     */
    @PreAuthorize("@ss.hasPermi('custom:wxDistribution:edit')")
    @Log(title = "微信分配管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxDistribution sysWxDistribution)
    {
        if(sysWxDistribution.getWechatAccount() != null){
            SysWxDistribution wxIdDistribution  = sysWxDistributionService.selectWxDistributionByWxId(sysWxDistribution.getWechatAccount());
            if(wxIdDistribution != null && wxIdDistribution.getId().intValue() != sysWxDistribution.getId().intValue()){
                return AjaxResult.error("该微信号已分配，无法重复分配");
            }
        }
        return toAjax(sysWxDistributionService.updateSysWxDistribution(sysWxDistribution));
    }

    /**
     * 删除微信分配管理
     */
    @PreAuthorize("@ss.hasPermi('custom:wxDistribution:remove')")
    @Log(title = "微信分配管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysWxDistributionService.deleteSysWxDistributionByIds(ids));
    }
}