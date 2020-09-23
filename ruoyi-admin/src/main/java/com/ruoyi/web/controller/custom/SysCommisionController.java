package com.ruoyi.web.controller.custom;

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
import com.ruoyi.custom.domain.SysCommision;
import com.ruoyi.custom.service.ISysCommisionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 业务提成比例Controller
 * 
 * @author wonder
 * @date 2020-09-24
 */
@RestController
@RequestMapping("/custom/commision")
public class SysCommisionController extends BaseController
{
    @Autowired
    private ISysCommisionService sysCommisionService;

    /**
     * 查询业务提成比例列表
     */
    @PreAuthorize("@ss.hasPermi('custom:commision:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCommision sysCommision)
    {
        startPage();
        List<SysCommision> list = sysCommisionService.selectSysCommisionList(sysCommision);
        return getDataTable(list);
    }

    /**
     * 导出业务提成比例列表
     */
    @PreAuthorize("@ss.hasPermi('custom:commision:export')")
    @Log(title = "业务提成比例", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCommision sysCommision)
    {
        List<SysCommision> list = sysCommisionService.selectSysCommisionList(sysCommision);
        ExcelUtil<SysCommision> util = new ExcelUtil<SysCommision>(SysCommision.class);
        return util.exportExcel(list, "commision");
    }

    /**
     * 获取业务提成比例详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:commision:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId)
    {
        return AjaxResult.success(sysCommisionService.selectSysCommisionById(ruleId));
    }

    /**
     * 新增业务提成比例
     */
    @PreAuthorize("@ss.hasPermi('custom:commision:add')")
    @Log(title = "业务提成比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCommision sysCommision)
    {
        return toAjax(sysCommisionService.insertSysCommision(sysCommision));
    }

    /**
     * 修改业务提成比例
     */
    @PreAuthorize("@ss.hasPermi('custom:commision:edit')")
    @Log(title = "业务提成比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCommision sysCommision)
    {
        return toAjax(sysCommisionService.updateSysCommision(sysCommision));
    }

    /**
     * 删除业务提成比例
     */
    @PreAuthorize("@ss.hasPermi('custom:commision:remove')")
    @Log(title = "业务提成比例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds)
    {
        return toAjax(sysCommisionService.deleteSysCommisionByIds(ruleIds));
    }
}
