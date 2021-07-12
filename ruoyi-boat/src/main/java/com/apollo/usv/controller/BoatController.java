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
import com.apollo.usv.domain.Boat;
import com.apollo.usv.service.IBoatService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 航船Controller
 * 
 * @author lwq
 * @date 2021-07-02
 */
@RestController
@RequestMapping("/usv/boat")
public class BoatController extends BaseController
{
    @Autowired
    private IBoatService boatService;

    /**
     * 查询航船列表
     */
    @PreAuthorize("@ss.hasPermi('usv:boat:list')")
    @GetMapping("/list")
    public TableDataInfo list(Boat boat)
    {
        startPage();
        List<Boat> list = boatService.selectBoatList(boat);
        return getDataTable(list);
    }

    /**
     * 导出航船列表
     */
    @PreAuthorize("@ss.hasPermi('usv:boat:export')")
    @Log(title = "航船", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Boat boat)
    {
        List<Boat> list = boatService.selectBoatList(boat);
        ExcelUtil<Boat> util = new ExcelUtil<Boat>(Boat.class);
        return util.exportExcel(list, "航船数据");
    }

    /**
     * 获取航船详细信息
     */
    @PreAuthorize("@ss.hasPermi('usv:boat:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(boatService.selectBoatById(id));
    }

    /**
     * 新增航船
     */
    @PreAuthorize("@ss.hasPermi('usv:boat:add')")
    @Log(title = "航船", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Boat boat)
    {
        return toAjax(boatService.insertBoat(boat));
    }

    /**
     * 修改航船
     */
    @PreAuthorize("@ss.hasPermi('usv:boat:edit')")
    @Log(title = "航船", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Boat boat)
    {
        return toAjax(boatService.updateBoat(boat));
    }

    /**
     * 删除航船
     */
    @PreAuthorize("@ss.hasPermi('usv:boat:remove')")
    @Log(title = "航船", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(boatService.deleteBoatByIds(ids));
    }
}
