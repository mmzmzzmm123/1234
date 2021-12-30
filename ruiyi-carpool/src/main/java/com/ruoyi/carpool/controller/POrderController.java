package com.ruoyi.carpool.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@Api(value = "小程序拼单-公共模块", tags = "订单模块")
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
     * 通过openID查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:list')")
    @GetMapping("/queryOrderInfo")
    @ApiOperation(value = "carpool_04_通过openID查询当前用户的所有订单", notes = "通过openID查询当前用户的所有订单,不传则获取全都。返回参数参照 POrder 模块")
    public TableDataInfo queryOrderInfoByOpenId(@ApiParam(name = "openId必传", required = false ) @RequestParam(value = "openId" , required = false) String  openId)
    {
        startPage();
        List<POrder> list = pOrderService.queryOrderInfoByOpenId(openId);
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
    @ApiOperation(value = "carpool_03_创建订单（发起拼单的接口）", notes = "发起拼单的接口")
    public AjaxResult add(@RequestBody POrder pOrder)
    {
        return pOrderService.insertPOrderWx(pOrder);
    }
    /**
     * 司机接单
     */
    @PreAuthorize("@ss.hasPermi('carpool:order:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/takeOrder")
    public AjaxResult takeOrder(@RequestBody POrder pOrder)
    {
        return pOrderService.takeOrder(pOrder);
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
