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

import com.ruoyi.business.domain.BusCbszdxx;
import com.ruoyi.business.service.IBusCbszdxxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

/**
 * 承包商站点信息Controller
 * 
 * @author yaowei
 * @date 2021-02-22
 */
@RestController
@RequestMapping("/business/cbszdxx")
public class BusCbszdxxController extends BaseController
{
    @Autowired
    private IBusCbszdxxService busCbszdxxService;

    /**
     * 查询承包商站点信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BusCbszdxx busCbszdxx)
    {
        startPage();
        List<BusCbszdxx> list = busCbszdxxService.selectBusCbszdxxList(busCbszdxx);
        return getDataTable(list);
    }

    /**
     * 获取承包商站点信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busCbszdxxService.selectBusCbszdxxById(id));
    }

    /**
     * 新增承包商站点信息
     */
    @Log(title = "承包商站点信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusCbszdxx busCbszdxx)
    {
        return toAjax(busCbszdxxService.insertBusCbszdxx(busCbszdxx));
    }

    /**
     * 修改承包商站点信息
     */
    @Log(title = "承包商站点信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCbszdxx busCbszdxx)
    {
        return toAjax(busCbszdxxService.updateBusCbszdxx(busCbszdxx));
    }

    /**
     * 删除承包商站点信息
     */
    @Log(title = "承包商站点信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busCbszdxxService.deleteBusCbszdxxByIds(ids));
    }
}
