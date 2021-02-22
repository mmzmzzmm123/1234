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

import com.ruoyi.business.domain.ViewBusZdxx;
import com.ruoyi.business.service.IViewBusZdxxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

import io.swagger.annotations.ApiOperation;

/**
 * VIEWController
 * 
 * @author yaowei
 * @date 2021-02-22
 */
@RestController
@RequestMapping("/business/zdxx")
public class ViewBusZdxxController extends BaseController
{
    @Autowired
    private IViewBusZdxxService viewBusZdxxService;

    /**
     * 查询VIEW列表
     */
    @ApiOperation("查询站点和定价信息")
    @GetMapping("/list")
    public TableDataInfo list(ViewBusZdxx viewBusZdxx)
    {
        startPage();
        List<ViewBusZdxx> list = viewBusZdxxService.selectViewBusZdxxList(viewBusZdxx);
        return getDataTable(list);
    }

    /**
     * 获取VIEW详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(viewBusZdxxService.selectViewBusZdxxById(id));
    }

    /**
     * 新增VIEW
     */
    @Log(title = "VIEW", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ViewBusZdxx viewBusZdxx)
    {
        return toAjax(viewBusZdxxService.insertViewBusZdxx(viewBusZdxx));
    }

    /**
     * 修改VIEW
     */
    @Log(title = "VIEW", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ViewBusZdxx viewBusZdxx)
    {
        return toAjax(viewBusZdxxService.updateViewBusZdxx(viewBusZdxx));
    }

    /**
     * 删除VIEW
     */
    @Log(title = "VIEW", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(viewBusZdxxService.deleteViewBusZdxxByIds(ids));
    }
}
