package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.GetRoomPriceVo;
import com.ruoyi.office.domain.vo.PrepayReq;
import com.ruoyi.office.domain.vo.PrepayResp;
import com.ruoyi.office.domain.vo.RoomAvailablePeriod;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.service.ITRoomOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）Controller
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/office/roomorder")
public class TRoomOrderController extends BaseController {
    @Autowired
    private ITRoomOrderService tRoomOrderService;

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomOrder tRoomOrder) {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tRoomOrder.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TRoomOrder> list = tRoomOrderService.selectTRoomOrderList(tRoomOrder);
        return getDataTable(list);
    }

    /**
     * 导出房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:export')")
    @Log(title = "房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomOrder tRoomOrder) {
        List<TRoomOrder> list = tRoomOrderService.selectTRoomOrderList(tRoomOrder);
        ExcelUtil<TRoomOrder> util = new ExcelUtil<TRoomOrder>(TRoomOrder.class);
        util.exportExcel(response, list, "房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）数据");
    }

    /**
     * 获取房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tRoomOrderService.selectTRoomOrderById(id));
    }

    /**
     * 新增房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:add')")
    @Log(title = "房间占用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomOrder tRoomOrder) {
        tRoomOrder.setCreateBy(SecurityUtils.getUserId() + "");
        tRoomOrder.setUserId(SecurityUtils.getUserId());
        return toAjax(tRoomOrderService.insertTRoomOrder(tRoomOrder));
    }

    /**
     * 修改房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:edit')")
    @Log(title = "房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomOrder tRoomOrder) {
        return toAjax(tRoomOrderService.updateTRoomOrder(tRoomOrder));
    }

    /**
     * 删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:remove')")
    @Log(title = "房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tRoomOrderService.deleteTRoomOrderByIds(ids));
    }

    /**
     * 小程序使用微信使用prepayId支付成功后，回调通知
     */
    @ApiOperation("支付成功")
    @Log(title = "支付成功", businessType = BusinessType.INSERT)
    @PostMapping("/payquery")
    public AjaxResult wxPayQuery(@RequestBody PrepayResp vo) {
        try {
            tRoomOrderService.finish(vo, SecurityUtils.getLoginUser().getWxUser().getId());
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @Log(title = "预定", businessType = BusinessType.INSERT)
    @PostMapping("/order")
    public AjaxResult order(@RequestBody PrepayReq order) {
        order.setUserId(SecurityUtils.getLoginUser().getWxUser().getId());
        try {
            tRoomOrderService.prepay(order, SecurityUtils.getLoginUser().getWxUser().getId());
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 获取房间时间段价格
     */
    @ApiOperation(value = "获取房间时间段价格")
    @GetMapping(value = "/price")
    public AjaxResult getPeriodPrice(GetRoomPriceVo vo) {
        return success(tRoomOrderService.getPeriodPrice(vo));
    }

    /**
     * 获取房间时间段价格
     */
    @ApiOperation(value = "获取房间时间段可用时段")
    @GetMapping(value = "/available")
    public AjaxResult getAvailablePeriod(RoomAvailablePeriod vo) {
        final RoomAvailablePeriod availablePeriod = tRoomOrderService.getAvailablePeriod(vo);
        return success(availablePeriod);
    }
}
