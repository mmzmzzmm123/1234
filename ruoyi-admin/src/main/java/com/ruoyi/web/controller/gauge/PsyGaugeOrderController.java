package com.ruoyi.web.controller.gauge;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.dto.OrderQueryDTO;
import com.ruoyi.gauge.service.IPsyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程订单Controller
 * 
 * @author ruoyi
 * @date 2023-03-17
 */
@RestController
@RequestMapping("/gauge/order")
public class PsyGaugeOrderController extends BaseController
{
    @Autowired
    private IPsyOrderService psyOrderService;

    /**
     * 查询课程订单列表
     */
    @PreAuthorize("@ss.hasPermi('gauge:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyOrder psyOrder)
    {
        startPage();
        List<PsyOrder> list = psyOrderService.selectPsyOrderList(psyOrder);
        return getDataTable(list);
    }

    /**
     * 根据条件查询课程订单列表
     */
    @PreAuthorize("@ss.hasPermi('course:order:list')")
    @PostMapping("/query")
    public TableDataInfo queryOrderList(@RequestBody @NotNull OrderQueryDTO psyOrder) {
        startPage();
        List<PsyOrder> list = psyOrderService.queryOrderList(psyOrder);
        return getDataTable(list);
    }

    /**
     * 导出课程订单列表
     */
    @PreAuthorize("@ss.hasPermi('gauge:order:export')")
    @Log(title = "课程订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderQueryDTO psyOrder)
    {
        List<PsyOrder> list = psyOrderService.queryOrderList(psyOrder);
        ExcelUtil<PsyOrder> util = new ExcelUtil<PsyOrder>(PsyOrder.class);
        util.exportExcel(response, list, "课程订单数据");
    }

    /**
     * 获取课程订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('gauge:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(psyOrderService.selectPsyOrderById(id));
    }

}
