package com.ruoyi.carpool.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
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
import com.ruoyi.carpool.domain.POrder;
import com.ruoyi.carpool.service.IPOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@RestController
@RequestMapping("/carpool/order")
public class POrderController extends BaseController
{
    @Autowired
    private IPOrderService pOrderService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(POrder pOrder)
    {
        startPage();
        List<POrder> list = pOrderService.selectPOrderList(pOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, POrder pOrder)
    {
        List<POrder> list = pOrderService.selectPOrderList(pOrder);
        ExcelUtil<POrder> util = new ExcelUtil<POrder>(POrder.class);
        util.exportExcel(response, list, "订单信息数据");
    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pOrderService.selectPOrderById(id));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody POrder pOrder)
    {
        String orderNum = DateUtils.dateTimeNow();
        pOrder.setOrderNum("carpool_"+orderNum);
        return toAjax(pOrderService.insertPOrder(pOrder));
    }
    /**
     * 司机接单
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/takeOrder")
    public AjaxResult takeOrder(@RequestBody POrder pOrder)
    {
        return toAjax(pOrderService.takeOrder(pOrder));
    }
    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody POrder pOrder)
    {
        return toAjax(pOrderService.updatePOrder(pOrder));
    }
    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pOrderService.deletePOrderByIds(ids));
    }





}
