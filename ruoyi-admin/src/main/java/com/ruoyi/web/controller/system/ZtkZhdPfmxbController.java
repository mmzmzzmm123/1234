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
import com.ruoyi.system.domain.ZtkZhdPfmxb;
import com.ruoyi.system.service.IZtkZhdPfmxbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 福建省泉州市个体工商户信贷_主题库_评分模型Controller
 * 
 * @author genius
 * @date 2021-11-26
 */
@RestController
@RequestMapping("/system/pfmxb")
public class ZtkZhdPfmxbController extends BaseController
{
    @Autowired
    private IZtkZhdPfmxbService ztkZhdPfmxbService;

    /**
     * 查询福建省泉州市个体工商户信贷_主题库_评分模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxb:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZtkZhdPfmxb ztkZhdPfmxb)
    {
        startPage();
        List<ZtkZhdPfmxb> list = ztkZhdPfmxbService.selectZtkZhdPfmxbList(ztkZhdPfmxb);
        return getDataTable(list);
    }

    /**
     * 导出福建省泉州市个体工商户信贷_主题库_评分模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxb:export')")
    @Log(title = "福建省泉州市个体工商户信贷_主题库_评分模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZtkZhdPfmxb ztkZhdPfmxb)
    {
        List<ZtkZhdPfmxb> list = ztkZhdPfmxbService.selectZtkZhdPfmxbList(ztkZhdPfmxb);
        ExcelUtil<ZtkZhdPfmxb> util = new ExcelUtil<ZtkZhdPfmxb>(ZtkZhdPfmxb.class);
        return util.exportExcel(list, "福建省泉州市个体工商户信贷_主题库_评分模型数据");
    }

    /**
     * 获取福建省泉州市个体工商户信贷_主题库_评分模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxb:query')")
    @GetMapping(value = "/{zjlid}")
    public AjaxResult getInfo(@PathVariable("zjlid") String zjlid)
    {
        return AjaxResult.success(ztkZhdPfmxbService.selectZtkZhdPfmxbById(zjlid));
    }

    /**
     * 新增福建省泉州市个体工商户信贷_主题库_评分模型
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxb:add')")
    @Log(title = "福建省泉州市个体工商户信贷_主题库_评分模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZtkZhdPfmxb ztkZhdPfmxb)
    {
        return toAjax(ztkZhdPfmxbService.insertZtkZhdPfmxb(ztkZhdPfmxb));
    }

    /**
     * 修改福建省泉州市个体工商户信贷_主题库_评分模型
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxb:edit')")
    @Log(title = "福建省泉州市个体工商户信贷_主题库_评分模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZtkZhdPfmxb ztkZhdPfmxb)
    {
        return toAjax(ztkZhdPfmxbService.updateZtkZhdPfmxb(ztkZhdPfmxb));
    }

    /**
     * 删除福建省泉州市个体工商户信贷_主题库_评分模型
     */
    @PreAuthorize("@ss.hasPermi('system:pfmxb:remove')")
    @Log(title = "福建省泉州市个体工商户信贷_主题库_评分模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{zjlids}")
    public AjaxResult remove(@PathVariable String[] zjlids)
    {
        return toAjax(ztkZhdPfmxbService.deleteZtkZhdPfmxbByIds(zjlids));
    }
}
