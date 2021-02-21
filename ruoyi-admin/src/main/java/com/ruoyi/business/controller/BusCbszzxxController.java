package com.ruoyi.business.controller;

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
import com.ruoyi.business.domain.BusCbszzxx;
import com.ruoyi.business.service.IBusCbszzxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 承包商资质信息Controller
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@RestController
@RequestMapping("/business/cbszzxx")
public class BusCbszzxxController extends BaseController
{
    @Autowired
    private IBusCbszzxxService busCbszzxxService;

    /**
     * 查询承包商资质信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:cbszzxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusCbszzxx busCbszzxx)
    {
        startPage();
        List<BusCbszzxx> list = busCbszzxxService.selectBusCbszzxxList(busCbszzxx);
        return getDataTable(list);
    }

    /**
     * 导出承包商资质信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:cbszzxx:export')")
    @Log(title = "承包商资质信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusCbszzxx busCbszzxx)
    {
        List<BusCbszzxx> list = busCbszzxxService.selectBusCbszzxxList(busCbszzxx);
        ExcelUtil<BusCbszzxx> util = new ExcelUtil<BusCbszzxx>(BusCbszzxx.class);
        return util.exportExcel(list, "cbszzxx");
    }

    /**
     * 获取承包商资质信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:cbszzxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busCbszzxxService.selectBusCbszzxxById(id));
    }

    /**
     * 新增承包商资质信息
     */
    @PreAuthorize("@ss.hasPermi('business:cbszzxx:add')")
    @Log(title = "承包商资质信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusCbszzxx busCbszzxx)
    {
        return toAjax(busCbszzxxService.insertBusCbszzxx(busCbszzxx));
    }

    /**
     * 修改承包商资质信息
     */
    @PreAuthorize("@ss.hasPermi('business:cbszzxx:edit')")
    @Log(title = "承包商资质信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCbszzxx busCbszzxx)
    {
        return toAjax(busCbszzxxService.updateBusCbszzxx(busCbszzxx));
    }

    /**
     * 删除承包商资质信息
     */
    @PreAuthorize("@ss.hasPermi('business:cbszzxx:remove')")
    @Log(title = "承包商资质信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busCbszzxxService.deleteBusCbszzxxByIds(ids));
    }
}
