package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.BzkZhdPfmxmxb;
import com.ruoyi.system.service.IBzkZhdPfmxmxbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体工商户信贷_标准库_评分模型明细Controller
 * 
 * @author genius
 * @date 2021-11-30
 */
@RestController
@RequestMapping("/system/pfmxmxb")
public class BzkZhdPfmxmxbController extends BaseController
{
    @Autowired
    private IBzkZhdPfmxmxbService bzkZhdPfmxmxbService;

    /**
     * 查询个体工商户信贷_标准库_评分模型明细列表
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxmxb:list')")
    @GetMapping("/list")
    public TableDataInfo list(BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        startPage();
        List<BzkZhdPfmxmxb> list = bzkZhdPfmxmxbService.selectBzkZhdPfmxmxbList(bzkZhdPfmxmxb);
        return getDataTable(list);
    }

    /**
     * 导出个体工商户信贷_标准库_评分模型明细列表
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxmxb:export')")
    @Log(title = "个体工商户信贷_标准库_评分模型明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        List<BzkZhdPfmxmxb> list = bzkZhdPfmxmxbService.selectBzkZhdPfmxmxbList(bzkZhdPfmxmxb);
        ExcelUtil<BzkZhdPfmxmxb> util = new ExcelUtil<BzkZhdPfmxmxb>(BzkZhdPfmxmxb.class);
        return util.exportExcel(list, "个体工商户信贷_标准库_评分模型明细数据");
    }

    /**
     * 获取个体工商户信贷_标准库_评分模型明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxmxb:query')")
    @GetMapping(value = "/{tyshxydm}")
    public AjaxResult getInfo(@PathVariable("tyshxydm") String tyshxydm)
    {
        return AjaxResult.success(bzkZhdPfmxmxbService.selectBzkZhdPfmxmxbById(tyshxydm));
    }

    /**
     * 新增个体工商户信贷_标准库_评分模型明细
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxmxb:add')")
    @Log(title = "个体工商户信贷_标准库_评分模型明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        return toAjax(bzkZhdPfmxmxbService.insertBzkZhdPfmxmxb(bzkZhdPfmxmxb));
    }

    /**
     * 修改个体工商户信贷_标准库_评分模型明细
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxmxb:edit')")
    @Log(title = "个体工商户信贷_标准库_评分模型明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        return toAjax(bzkZhdPfmxmxbService.updateBzkZhdPfmxmxb(bzkZhdPfmxmxb));
    }

    /**
     * 删除个体工商户信贷_标准库_评分模型明细
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxmxb:remove')")
    @Log(title = "个体工商户信贷_标准库_评分模型明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tyshxydms}")
    public AjaxResult remove(@PathVariable String[] tyshxydms)
    {
        return toAjax(bzkZhdPfmxmxbService.deleteBzkZhdPfmxmxbByIds(tyshxydms));
    }
}
