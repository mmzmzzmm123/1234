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
import com.ruoyi.system.domain.BzkZbhZrjgb;
import com.ruoyi.system.service.IBzkZbhZrjgbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体工商户信贷_标准库_准入监管Controller
 * 
 * @author genius
 * @date 2021-11-26
 */
@RestController
@RequestMapping("/system/bzkzbhzrjgb")
public class BzkZbhZrjgbController extends BaseController
{
    @Autowired
    private IBzkZbhZrjgbService bzkZbhZrjgbService;

    /**
     * 查询个体工商户信贷_标准库_准入监管列表
     */
    @PreAuthorize("@ss.hasPermi('system:bzkzbhzrjgb:list')")
    @GetMapping("/list")
    public TableDataInfo list(BzkZbhZrjgb bzkZbhZrjgb)
    {
        startPage();
        List<BzkZbhZrjgb> list = bzkZbhZrjgbService.selectBzkZbhZrjgbList(bzkZbhZrjgb);
        return getDataTable(list);
    }

    /**
     * 导出个体工商户信贷_标准库_准入监管列表
     */
    @PreAuthorize("@ss.hasPermi('system:bzkzbhzrjgb:export')")
    @Log(title = "个体工商户信贷_标准库_准入监管", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BzkZbhZrjgb bzkZbhZrjgb)
    {
        List<BzkZbhZrjgb> list = bzkZbhZrjgbService.selectBzkZbhZrjgbList(bzkZbhZrjgb);
        ExcelUtil<BzkZbhZrjgb> util = new ExcelUtil<BzkZbhZrjgb>(BzkZbhZrjgb.class);
        return util.exportExcel(list, "个体工商户信贷_标准库_准入监管数据");
    }

    /**
     * 获取个体工商户信贷_标准库_准入监管详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bzkzbhzrjgb:query')")
    @GetMapping(value = "/{zjlid}")
    public AjaxResult getInfo(@PathVariable("zjlid") String zjlid)
    {
        return AjaxResult.success(bzkZbhZrjgbService.selectBzkZbhZrjgbById(zjlid));
    }

    /**
     * 新增个体工商户信贷_标准库_准入监管
     */
    @PreAuthorize("@ss.hasPermi('system:bzkzbhzrjgb:add')")
    @Log(title = "个体工商户信贷_标准库_准入监管", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BzkZbhZrjgb bzkZbhZrjgb)
    {
        return toAjax(bzkZbhZrjgbService.insertBzkZbhZrjgb(bzkZbhZrjgb));
    }

    /**
     * 修改个体工商户信贷_标准库_准入监管
     */
    @PreAuthorize("@ss.hasPermi('system:bzkzbhzrjgb:edit')")
    @Log(title = "个体工商户信贷_标准库_准入监管", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BzkZbhZrjgb bzkZbhZrjgb)
    {
        return toAjax(bzkZbhZrjgbService.updateBzkZbhZrjgb(bzkZbhZrjgb));
    }

    /**
     * 删除个体工商户信贷_标准库_准入监管
     */
    @PreAuthorize("@ss.hasPermi('system:bzkzbhzrjgb:remove')")
    @Log(title = "个体工商户信贷_标准库_准入监管", businessType = BusinessType.DELETE)
	@DeleteMapping("/{zjlids}")
    public AjaxResult remove(@PathVariable String[] zjlids)
    {
        return toAjax(bzkZbhZrjgbService.deleteBzkZbhZrjgbByIds(zjlids));
    }
}
