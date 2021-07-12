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
import com.apollo.usv.domain.ChargingPile;
import com.apollo.usv.service.IChargingPileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 充电桩Controller
 * 
 * @author lwq
 * @date 2021-07-02
 */
@RestController
@RequestMapping("/usv/pile")
public class ChargingPileController extends BaseController
{
    @Autowired
    private IChargingPileService chargingPileService;

    /**
     * 查询充电桩列表
     */
    @PreAuthorize("@ss.hasPermi('usv:pile:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChargingPile chargingPile)
    {
        startPage();
        List<ChargingPile> list = chargingPileService.selectChargingPileList(chargingPile);
        return getDataTable(list);
    }

    /**
     * 导出充电桩列表
     */
    @PreAuthorize("@ss.hasPermi('usv:pile:export')")
    @Log(title = "充电桩", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ChargingPile chargingPile)
    {
        List<ChargingPile> list = chargingPileService.selectChargingPileList(chargingPile);
        ExcelUtil<ChargingPile> util = new ExcelUtil<ChargingPile>(ChargingPile.class);
        return util.exportExcel(list, "充电桩数据");
    }

    /**
     * 获取充电桩详细信息
     */
    @PreAuthorize("@ss.hasPermi('usv:pile:query')")
    @GetMapping(value = "/{chargingPileId}")
    public AjaxResult getInfo(@PathVariable("chargingPileId") Long chargingPileId)
    {
        return AjaxResult.success(chargingPileService.selectChargingPileById(chargingPileId));
    }

    /**
     * 新增充电桩
     */
    @PreAuthorize("@ss.hasPermi('usv:pile:add')")
    @Log(title = "充电桩", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChargingPile chargingPile)
    {
        return toAjax(chargingPileService.insertChargingPile(chargingPile));
    }

    /**
     * 修改充电桩
     */
    @PreAuthorize("@ss.hasPermi('usv:pile:edit')")
    @Log(title = "充电桩", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChargingPile chargingPile)
    {
        return toAjax(chargingPileService.updateChargingPile(chargingPile));
    }

    /**
     * 删除充电桩
     */
    @PreAuthorize("@ss.hasPermi('usv:pile:remove')")
    @Log(title = "充电桩", businessType = BusinessType.DELETE)
	@DeleteMapping("/{chargingPileIds}")
    public AjaxResult remove(@PathVariable Long[] chargingPileIds)
    {
        return toAjax(chargingPileService.deleteChargingPileByIds(chargingPileIds));
    }
}
