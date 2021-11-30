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
import com.ruoyi.system.domain.BzkZhdZrjgmxb;
import com.ruoyi.system.service.IBzkZhdZrjgmxbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体工商户信贷_标准库_准入监管明细Controller
 * 
 * @author genius
 * @date 2021-11-30
 */
@RestController
@RequestMapping("/system/zrjgmxb")
public class BzkZhdZrjgmxbController extends BaseController
{
    @Autowired
    private IBzkZhdZrjgmxbService bzkZhdZrjgmxbService;

    /**
     * 查询个体工商户信贷_标准库_准入监管明细列表
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgmxb:list')")
    @GetMapping("/list")
    public TableDataInfo list(BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        startPage();
        List<BzkZhdZrjgmxb> list = bzkZhdZrjgmxbService.selectBzkZhdZrjgmxbList(bzkZhdZrjgmxb);
        return getDataTable(list);
    }

    /**
     * 导出个体工商户信贷_标准库_准入监管明细列表
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgmxb:export')")
    @Log(title = "个体工商户信贷_标准库_准入监管明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        List<BzkZhdZrjgmxb> list = bzkZhdZrjgmxbService.selectBzkZhdZrjgmxbList(bzkZhdZrjgmxb);
        ExcelUtil<BzkZhdZrjgmxb> util = new ExcelUtil<BzkZhdZrjgmxb>(BzkZhdZrjgmxb.class);
        return util.exportExcel(list, "个体工商户信贷_标准库_准入监管明细数据");
    }

    /**
     * 获取个体工商户信贷_标准库_准入监管明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgmxb:query')")
    @GetMapping(value = "/{tyshxydm}")
    public AjaxResult getInfo(@PathVariable("tyshxydm") String tyshxydm)
    {
        return AjaxResult.success(bzkZhdZrjgmxbService.selectBzkZhdZrjgmxbById(tyshxydm));
    }

    /**
     * 新增个体工商户信贷_标准库_准入监管明细
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgmxb:add')")
    @Log(title = "个体工商户信贷_标准库_准入监管明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        return toAjax(bzkZhdZrjgmxbService.insertBzkZhdZrjgmxb(bzkZhdZrjgmxb));
    }

    /**
     * 修改个体工商户信贷_标准库_准入监管明细
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgmxb:edit')")
    @Log(title = "个体工商户信贷_标准库_准入监管明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        return toAjax(bzkZhdZrjgmxbService.updateBzkZhdZrjgmxb(bzkZhdZrjgmxb));
    }

    /**
     * 删除个体工商户信贷_标准库_准入监管明细
     */
    @PreAuthorize("@ss.hasPermi('system:zrjgmxb:remove')")
    @Log(title = "个体工商户信贷_标准库_准入监管明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tyshxydms}")
    public AjaxResult remove(@PathVariable String[] tyshxydms)
    {
        return toAjax(bzkZhdZrjgmxbService.deleteBzkZhdZrjgmxbByIds(tyshxydms));
    }
}
