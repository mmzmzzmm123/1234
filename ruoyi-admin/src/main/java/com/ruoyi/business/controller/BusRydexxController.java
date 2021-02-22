package com.ruoyi.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.business.domain.BusRydexx;
import com.ruoyi.business.service.IBusRydexxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 人员定额信息Controller
 * 
 * @author yaowei
 * @date 2021-02-23
 */
@RestController
@RequestMapping("/business/rydexx")
public class BusRydexxController extends BaseController
{
    @Autowired
    private IBusRydexxService busRydexxService;

    /**
     * 查询人员定额信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BusRydexx busRydexx)
    {
        startPage();
        List<BusRydexx> list = busRydexxService.selectBusRydexxList(busRydexx);
        return getDataTable(list);
    }

    /**
     * 导出人员定额信息列表
     */
    @Log(title = "人员定额信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusRydexx busRydexx)
    {
        List<BusRydexx> list = busRydexxService.selectBusRydexxList(busRydexx);
        ExcelUtil<BusRydexx> util = new ExcelUtil<BusRydexx>(BusRydexx.class);
        return util.exportExcel(list, "rydexx");
    }

    /**
     * 获取人员定额信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busRydexxService.selectBusRydexxById(id));
    }

    /**
     * 新增人员定额信息
     */
    @Log(title = "人员定额信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusRydexx busRydexx)
    {
        return toAjax(busRydexxService.insertBusRydexx(busRydexx));
    }

    /**
     * 修改人员定额信息
     */
    @Log(title = "人员定额信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusRydexx busRydexx)
    {
        return toAjax(busRydexxService.updateBusRydexx(busRydexx));
    }

    /**
     * 删除人员定额信息
     */
    @Log(title = "人员定额信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busRydexxService.deleteBusRydexxByIds(ids));
    }
}
