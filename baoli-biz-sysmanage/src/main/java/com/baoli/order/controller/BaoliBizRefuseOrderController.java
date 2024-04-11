package com.baoli.order.controller;

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
import com.baoli.order.domain.BaoliBizRefuseOrder;
import com.baoli.order.service.IBaoliBizRefuseOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 拒单订单管理Controller
 * 
 * @author niujs
 * @date 2024-04-11
 */
@RestController
@RequestMapping("/order/refuseOrder")
public class BaoliBizRefuseOrderController extends BaseController
{
    @Autowired
    private IBaoliBizRefuseOrderService baoliBizRefuseOrderService;

    /**
     * 查询拒单订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:refuseOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        startPage();
        List<BaoliBizRefuseOrder> list = baoliBizRefuseOrderService.selectBaoliBizRefuseOrderList(baoliBizRefuseOrder);
        return getDataTable(list);
    }

    /**
     * 导出拒单订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:refuseOrder:export')")
    @Log(title = "拒单订单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        List<BaoliBizRefuseOrder> list = baoliBizRefuseOrderService.selectBaoliBizRefuseOrderList(baoliBizRefuseOrder);
        ExcelUtil<BaoliBizRefuseOrder> util = new ExcelUtil<BaoliBizRefuseOrder>(BaoliBizRefuseOrder.class);
        util.exportExcel(response, list, "拒单订单管理数据");
    }

    /**
     * 获取拒单订单管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:refuseOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizRefuseOrderService.selectBaoliBizRefuseOrderById(id));
    }

    /**
     * 新增拒单订单管理
     */
    @PreAuthorize("@ss.hasPermi('order:refuseOrder:add')")
    @Log(title = "拒单订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        return toAjax(baoliBizRefuseOrderService.insertBaoliBizRefuseOrder(baoliBizRefuseOrder));
    }

    /**
     * 修改拒单订单管理
     */
    @PreAuthorize("@ss.hasPermi('order:refuseOrder:edit')")
    @Log(title = "拒单订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        return toAjax(baoliBizRefuseOrderService.updateBaoliBizRefuseOrder(baoliBizRefuseOrder));
    }

    /**
     * 删除拒单订单管理
     */
    @PreAuthorize("@ss.hasPermi('order:refuseOrder:remove')")
    @Log(title = "拒单订单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizRefuseOrderService.deleteBaoliBizRefuseOrderByIds(ids));
    }
}
