package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.office.domain.TRoomChargePrice;
import com.ruoyi.office.service.ITRoomChargePriceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间续费套餐Controller
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/office/roomchargeprice")
public class TRoomChargePriceController extends BaseController
{
    @Autowired
    private ITRoomChargePriceService tRoomChargePriceService;

    /**
     * 查询房间续费套餐列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomchargeprice:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomChargePrice tRoomChargePrice)
    {
        startPage();
        List<TRoomChargePrice> list = tRoomChargePriceService.selectTRoomChargePriceList(tRoomChargePrice);
        return getDataTable(list);
    }

    /**
     * 导出房间续费套餐列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomchargeprice:export')")
    @Log(title = "房间续费套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomChargePrice tRoomChargePrice)
    {
        List<TRoomChargePrice> list = tRoomChargePriceService.selectTRoomChargePriceList(tRoomChargePrice);
        ExcelUtil<TRoomChargePrice> util = new ExcelUtil<TRoomChargePrice>(TRoomChargePrice.class);
        util.exportExcel(response, list, "房间续费套餐数据");
    }

    /**
     * 获取房间续费套餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:roomchargeprice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tRoomChargePriceService.selectTRoomChargePriceById(id));
    }

    /**
     * 新增房间续费套餐
     */
    @PreAuthorize("@ss.hasPermi('office:roomchargeprice:add')")
    @Log(title = "房间续费套餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomChargePrice tRoomChargePrice)
    {
        return toAjax(tRoomChargePriceService.insertTRoomChargePrice(tRoomChargePrice));
    }

    /**
     * 修改房间续费套餐
     */
    @PreAuthorize("@ss.hasPermi('office:roomchargeprice:edit')")
    @Log(title = "房间续费套餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomChargePrice tRoomChargePrice)
    {
        return toAjax(tRoomChargePriceService.updateTRoomChargePrice(tRoomChargePrice));
    }

    /**
     * 删除房间续费套餐
     */
    @PreAuthorize("@ss.hasPermi('office:roomchargeprice:remove')")
    @Log(title = "房间续费套餐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tRoomChargePriceService.deleteTRoomChargePriceByIds(ids));
    }
}
