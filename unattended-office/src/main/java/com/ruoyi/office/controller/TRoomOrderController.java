package com.ruoyi.office.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.service.IBusinessAnalysisService;
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

    @Autowired
    IBusinessAnalysisService iBusinessAnalysisService;

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:list')")
    @ApiOperation("预约订单、记录")
    @GetMapping("/list")
    public TableDataInfo list(TRoomOrder tRoomOrder) {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tRoomOrder.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TRoomOrder> list = tRoomOrderService.selectTRoomOrderList(tRoomOrder);
        return getDataTable(list);
    }

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:list')")
    @ApiOperation("预约订单、记录;只展示已经支付过的订单")
    @GetMapping("/h5list")
    public TableDataInfo h5list(RoomOrderH5QryVo tRoomOrder) {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tRoomOrder.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<RoomOrderH5Vo> list = tRoomOrderService.selectTRoomOrderH5List(tRoomOrder);

        for (RoomOrderH5Vo order : list) {
            if (order.getPayType() != 0)
                order.setPayTypeName(OfficeEnum.PayType.GetValueByCode(order.getPayType()).getInfo());
        }
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
    @ApiOperation("根据id获取订单")
//    @PreAuthorize("@ss.hasPermi('office:roomorder:query')")
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
            logger.info("/payquery:" + vo.toString());
            WxPayOrderQueryV3Result v3Result = tRoomOrderService.finish(vo, SecurityUtils.getLoginUser().getWxUser().getId());
            if (v3Result == null)
                logger.info("支付成功,微信已经回调");
            else
                logger.info("套餐购买查询 /roomOrder/payquery:" + v3Result.toString());
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation("预约支付")
    @Log(title = "预定", businessType = BusinessType.INSERT)
    @PostMapping("/order")
    public AjaxResult order(@RequestBody PrepayReq order) {
        long wxUserId = SecurityUtils.getLoginUser().getWxUser().getId();
//        long wxUserId = 9l;
        order.setUserId(wxUserId);
        try {
            logger.info("/order:" + order.toString());
            final PrepayResp prepay = tRoomOrderService.prepay(order, wxUserId);
//            logger.info("/order: return:" + prepay.getOrderId() + prepay.getJsapiResult().toString());
            return AjaxResult.success(prepay);
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
     * 续单,校验是否有进行中的订单，并返回
     *
     * @return
     */
    @ApiOperation("续单,校验是否有进行中的订单，并返回")
    @GetMapping("/continue")
    public AjaxResult continueOrder() {
        TRoomOrder order = tRoomOrderService.continueOrder(SecurityUtils.getLoginUser().getWxUser().getId());
        return AjaxResult.success(order);
    }

    @ApiOperation("订单分享")
    @GetMapping("/share/{id}")
    public AjaxResult shareOrder(@PathVariable Long orderId) {
        String unionId = SecurityUtils.getLoginUser().getWxUser().getUnionId();

        String url = "https://www.btjingling.com/#/pages/login/signup/signup?data=" + java.net.URLEncoder.encode(unionId) + ";" + orderId;

        return AjaxResult.success(new String(QrCodeUtil.generatePng(url, new QrConfig())));
    }

    /**
     * 新增房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    @PreAuthorize("@ss.hasPermi('office:roomorder:add')")
    @Log(title = "代客预约", businessType = BusinessType.INSERT)
    @PostMapping("/order4Guest")
    public AjaxResult order4Guest(@RequestBody TRoomOrder tRoomOrder) {
        tRoomOrder.setCreateBy(SecurityUtils.getUserId() + "");
        return AjaxResult.success(tRoomOrderService.order4Guest(tRoomOrder));
    }


    @ApiOperation("营收总览")
    @GetMapping("/businessAnalysis")
    public AjaxResult businessAnalysis() {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        BusinessAnalysisH5Vo businessAnalysisH5Vo = iBusinessAnalysisService.getBusinessAnalysis(userId);

        return success(businessAnalysisH5Vo);
    }

    @PreAuthorize("@ss.hasPermi('office:roomorder:add')")
    @Log(title = "商户待客续单", businessType = BusinessType.UPDATE)
    @PostMapping("/charge")
    public AjaxResult charge(@RequestBody OrderChargeReq orderChargeReq) {
        try {
            tRoomOrderService.chargrge(orderChargeReq);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('office:roomorder:add')")
    @Log(title = "商户待客换包间", businessType = BusinessType.INSERT)
    @PostMapping("/changeRoom")
    public AjaxResult changeRoom(@RequestBody OrderChangeRoomReq req) {
        try {
            tRoomOrderService.changeRoom(req);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

}
