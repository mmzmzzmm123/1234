package com.ruoyi.web.controller.custom;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.custom.controller.OrderBaseController;
import com.ruoyi.custom.page.OrderTableDataInfo;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.custom.domain.SysOrder;
import com.ruoyi.custom.service.ISysOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 *
 * @author wonder
 * @date 2020-09-24
 */
@RestController
@RequestMapping("/custom/order")
public class SysOrderController extends OrderBaseController {
    @Autowired
    private ISysOrderService sysOrderService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('custom:order:list')")
    @GetMapping("/list")
    public OrderTableDataInfo list(SysOrder sysOrder) {
        startPage();
        List<SysOrder> list = sysOrderService.selectSysOrderList(sysOrder);
        List<SysUser> userList = userService.selectAllUser();
        BigDecimal totalAmount = sysOrderService.selectAllOrderAmount(sysOrder);

        for (SysOrder order : list) {
            for (SysUser user : userList) {
                if (user.getUserId().equals(order.getPreSaleId())) {
                    order.setPreSale(user.getNickName());
                } else if (user.getUserId().equals(order.getAfterSaleId())) {
                    order.setAfterSale(user.getNickName());
                } else if (user.getUserId().equals(order.getNutritionistId())) {
                    order.setNutritionist(user.getNickName());
                } else if (user.getUserId().equals(order.getNutriAssisId())) {
                    order.setNutriAssis(user.getNickName());
                } else if (user.getUserId().equals(order.getOperatorId())) {
                    order.setOperator(user.getNickName());
                } else if (user.getUserId().equals(order.getPlannerId())) {
                    order.setPlanner(user.getNickName());
                } else if (user.getUserId().equals(order.getPlannerAssisId())) {
                    order.setPlannerAssis(user.getNickName());
                } else if (user.getUserId().equals(order.getOperatorAssisId())) {
                    order.setOperatorAssis(user.getNickName());
                }
            }
        }
        return getOrderDataTable(list, totalAmount.longValue());
    }


    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('custom:order:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysOrder sysOrder) {
        List<SysOrder> list = sysOrderService.selectSysOrderList(sysOrder);
        List<SysUser> userList = userService.selectAllUser();

        for (SysOrder order : list) {
            for (SysUser user : userList) {
                if (user.getUserId().equals(order.getPreSaleId())) {
                    order.setPreSale(user.getNickName());
                } else if (user.getUserId().equals(order.getAfterSaleId())) {
                    order.setAfterSale(user.getNickName());
                } else if (user.getUserId().equals(order.getNutritionistId())) {
                    order.setNutritionist(user.getNickName());
                } else if (user.getUserId().equals(order.getNutriAssisId())) {
                    order.setNutriAssis(user.getNickName());
                } else if (user.getUserId().equals(order.getOperatorId())) {
                    order.setOperator(user.getNickName());
                } else if (user.getUserId().equals(order.getPlannerId())) {
                    order.setPlanner(user.getNickName());
                } else if (user.getUserId().equals(order.getPlannerAssisId())) {
                    order.setPlannerAssis(user.getNickName());
                } else if (user.getUserId().equals(order.getOperatorAssisId())) {
                    order.setOperatorAssis(user.getNickName());
                }
            }
        }
        ExcelUtil<SysOrder> util = new ExcelUtil<SysOrder>(SysOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return AjaxResult.success(sysOrderService.selectSysOrderById(orderId));
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('custom:order:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysOrder sysOrder) {
        return toAjax(sysOrderService.insertSysOrder(sysOrder));
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('custom:order:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysOrder sysOrder) {
        return toAjax(sysOrderService.updateSysOrder(sysOrder));
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('custom:order:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(sysOrderService.deleteSysOrderByIds(orderIds));
    }


}