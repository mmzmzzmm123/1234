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
import com.ruoyi.office.domain.TRoomOrderCharge;
import com.ruoyi.office.service.ITRoomOrderChargeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 续费订单Controller
 * 
 * @author ruoyi
 * @date 2023-10-22
 */
@RestController
@RequestMapping("/office/ordercharge")
public class TRoomOrderChargeController extends BaseController
{
    @Autowired
    private ITRoomOrderChargeService tRoomOrderChargeService;

    /**
     * 查询续费订单列表
     */
    @PreAuthorize("@ss.hasPermi('office:ordercharge:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomOrderCharge tRoomOrderCharge)
    {
        startPage();
        List<TRoomOrderCharge> list = tRoomOrderChargeService.selectTRoomOrderChargeList(tRoomOrderCharge);
        return getDataTable(list);
    }

    /**
     * 导出续费订单列表
     */
    @PreAuthorize("@ss.hasPermi('office:ordercharge:export')")
    @Log(title = "续费订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomOrderCharge tRoomOrderCharge)
    {
        List<TRoomOrderCharge> list = tRoomOrderChargeService.selectTRoomOrderChargeList(tRoomOrderCharge);
        ExcelUtil<TRoomOrderCharge> util = new ExcelUtil<TRoomOrderCharge>(TRoomOrderCharge.class);
        util.exportExcel(response, list, "续费订单数据");
    }

    /**
     * 获取续费订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:ordercharge:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tRoomOrderChargeService.selectTRoomOrderChargeById(id));
    }

    /**
     * 新增续费订单
     */
    @PreAuthorize("@ss.hasPermi('office:ordercharge:add')")
    @Log(title = "续费订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomOrderCharge tRoomOrderCharge)
    {
        return toAjax(tRoomOrderChargeService.insertTRoomOrderCharge(tRoomOrderCharge));
    }

    /**
     * 修改续费订单
     */
    @PreAuthorize("@ss.hasPermi('office:ordercharge:edit')")
    @Log(title = "续费订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomOrderCharge tRoomOrderCharge)
    {
        return toAjax(tRoomOrderChargeService.updateTRoomOrderCharge(tRoomOrderCharge));
    }

    /**
     * 删除续费订单
     */
    @PreAuthorize("@ss.hasPermi('office:ordercharge:remove')")
    @Log(title = "续费订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tRoomOrderChargeService.deleteTRoomOrderChargeByIds(ids));
    }
}
