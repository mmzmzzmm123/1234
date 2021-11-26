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
import com.ruoyi.system.domain.ZtkZbhZrjgb;
import com.ruoyi.system.service.IZtkZbhZrjgbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体工商户信贷_主题库_准入监管Controller
 * 
 * @author genius
 * @date 2021-11-26
 */
@RestController
@RequestMapping("/system/ztkzbhzrjgb")
public class ZtkZbhZrjgbController extends BaseController
{
    @Autowired
    private IZtkZbhZrjgbService ztkZbhZrjgbService;

    /**
     * 查询个体工商户信贷_主题库_准入监管列表
     */
    @PreAuthorize("@ss.hasPermi('system:ztkzbhzrjgb:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZtkZbhZrjgb ztkZbhZrjgb)
    {
        startPage();
        List<ZtkZbhZrjgb> list = ztkZbhZrjgbService.selectZtkZbhZrjgbList(ztkZbhZrjgb);
        return getDataTable(list);
    }

    /**
     * 导出个体工商户信贷_主题库_准入监管列表
     */
    @PreAuthorize("@ss.hasPermi('system:ztkzbhzrjgb:export')")
    @Log(title = "个体工商户信贷_主题库_准入监管", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZtkZbhZrjgb ztkZbhZrjgb)
    {
        List<ZtkZbhZrjgb> list = ztkZbhZrjgbService.selectZtkZbhZrjgbList(ztkZbhZrjgb);
        ExcelUtil<ZtkZbhZrjgb> util = new ExcelUtil<ZtkZbhZrjgb>(ZtkZbhZrjgb.class);
        return util.exportExcel(list, "个体工商户信贷_主题库_准入监管数据");
    }

    /**
     * 获取个体工商户信贷_主题库_准入监管详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:ztkzbhzrjgb:query')")
    @GetMapping(value = "/{zjlid}")
    public AjaxResult getInfo(@PathVariable("zjlid") String zjlid)
    {
        return AjaxResult.success(ztkZbhZrjgbService.selectZtkZbhZrjgbById(zjlid));
    }

    /**
     * 新增个体工商户信贷_主题库_准入监管
     */
    @PreAuthorize("@ss.hasPermi('system:ztkzbhzrjgb:add')")
    @Log(title = "个体工商户信贷_主题库_准入监管", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZtkZbhZrjgb ztkZbhZrjgb)
    {
        return toAjax(ztkZbhZrjgbService.insertZtkZbhZrjgb(ztkZbhZrjgb));
    }

    /**
     * 修改个体工商户信贷_主题库_准入监管
     */
    @PreAuthorize("@ss.hasPermi('system:ztkzbhzrjgb:edit')")
    @Log(title = "个体工商户信贷_主题库_准入监管", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZtkZbhZrjgb ztkZbhZrjgb)
    {
        return toAjax(ztkZbhZrjgbService.updateZtkZbhZrjgb(ztkZbhZrjgb));
    }

    /**
     * 删除个体工商户信贷_主题库_准入监管
     */
    @PreAuthorize("@ss.hasPermi('system:ztkzbhzrjgb:remove')")
    @Log(title = "个体工商户信贷_主题库_准入监管", businessType = BusinessType.DELETE)
	@DeleteMapping("/{zjlids}")
    public AjaxResult remove(@PathVariable String[] zjlids)
    {
        return toAjax(ztkZbhZrjgbService.deleteZtkZbhZrjgbByIds(zjlids));
    }
}
