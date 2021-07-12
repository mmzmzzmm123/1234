package com.apollo.usv.controller;

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
import com.apollo.usv.domain.BoatStatus;
import com.apollo.usv.service.IBoatStatusService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 航船状态Controller
 * 
 * @author lwq
 * @date 2021-07-02
 */
@RestController
@RequestMapping("/usv/status")
public class BoatStatusController extends BaseController
{
    @Autowired
    private IBoatStatusService boatStatusService;

    /**
     * 查询航船状态列表
     */
    @PreAuthorize("@ss.hasPermi('usv:status:list')")
    @GetMapping("/list")
    public TableDataInfo list(BoatStatus boatStatus)
    {
        startPage();
        List<BoatStatus> list = boatStatusService.selectBoatStatusList(boatStatus);
        return getDataTable(list);
    }

    /**
     * 导出航船状态列表
     */
    @PreAuthorize("@ss.hasPermi('usv:status:export')")
    @Log(title = "航船状态", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BoatStatus boatStatus)
    {
        List<BoatStatus> list = boatStatusService.selectBoatStatusList(boatStatus);
        ExcelUtil<BoatStatus> util = new ExcelUtil<BoatStatus>(BoatStatus.class);
        return util.exportExcel(list, "航船状态数据");
    }

    /**
     * 获取航船状态详细信息
     */
    @PreAuthorize("@ss.hasPermi('usv:status:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(boatStatusService.selectBoatStatusById(id));
    }

    /**
     * 新增航船状态
     */
    @PreAuthorize("@ss.hasPermi('usv:status:add')")
    @Log(title = "航船状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BoatStatus boatStatus)
    {
        return toAjax(boatStatusService.insertBoatStatus(boatStatus));
    }

    /**
     * 修改航船状态
     */
    @PreAuthorize("@ss.hasPermi('usv:status:edit')")
    @Log(title = "航船状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BoatStatus boatStatus)
    {
        return toAjax(boatStatusService.updateBoatStatus(boatStatus));
    }

    /**
     * 删除航船状态
     */
    @PreAuthorize("@ss.hasPermi('usv:status:remove')")
    @Log(title = "航船状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(boatStatusService.deleteBoatStatusByIds(ids));
    }
}
