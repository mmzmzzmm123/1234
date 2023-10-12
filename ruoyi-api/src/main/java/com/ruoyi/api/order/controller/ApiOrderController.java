package com.ruoyi.api.order.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.order.model.dto.*;
import com.ruoyi.api.order.model.vo.ApiOrderDetailsVo;
import com.ruoyi.api.order.model.vo.ApiOrderInfoVo;
import com.ruoyi.api.order.model.vo.ApiPageOrderInfoVo;
import com.ruoyi.api.order.service.ApiOrderService;
import com.ruoyi.api.payment.model.vo.ApiOrderPayInfoVo;
import com.ruoyi.api.staff.model.vo.ApiStaffInfoVo;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.user.domain.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/10 1:52
 */
@Anonymous
@Slf4j
@Api("api-订单相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class ApiOrderController extends BaseController {

    private final ApiOrderService service;

    @ApiOperation("礼物赠送订单提交")
    @PostMapping("/giftOrderSubmit")
    @RepeatSubmit(interval = 1000, message = "订单已提交，请勿重复提交")
    public R<ApiOrderPayInfoVo> giftOrderSubmit(@Valid ApiGiftOrderFormDto dto) {
        return R.ok(service.giftOrderSubmit(dto));
    }

    @ApiOperation("打赏订单提交")
    @PostMapping("/rewardOrderSubmit")
    @RepeatSubmit(interval = 1000, message = "订单已提交，请勿重复提交")
    public R<ApiOrderPayInfoVo> rewardOrderSubmit(@Valid ApiRewardOrderFormDto dto) {
        return R.ok(service.rewardOrderSubmit(dto));
    }

    @ApiOperation("指定单提交")
    @PostMapping("/appointOrderSubmit")
    @RepeatSubmit(interval = 1000, message = "订单已提交，请勿重复提交")
    public R<ApiOrderPayInfoVo> appointOrderSubmit(@Valid ApiAppointOrderFormDto dto) {
        return R.ok(service.appointOrderSubmit(dto));
    }

    @ApiOperation("随机单提交")
    @PostMapping("/randomOrderSubmit")
    @RepeatSubmit(interval = 1000, message = "订单已提交，请勿重复提交")
    public R<ApiOrderPayInfoVo> randomOrderSubmit(@Valid ApiRandomOrderFormDto dto){
        return R.ok(service.randomOrderSubmit(dto));
    }

    @ApiOperation("订单取消")
    @PostMapping("/orderCancel")
    @RepeatSubmit(interval = 1000)
    public R<Boolean> orderCancel(@Valid ApiOrderCancelDto dto) {
        return R.ok(service.orderCancel(dto));
    }

    @ApiOperation("订单再次支付")
    @GetMapping("/payAgain")
    @RepeatSubmit(interval = 1000)
    public R<ApiOrderPayInfoVo> payAgain(String orderNo) {
        return R.ok(service.payAgain(orderNo));
    }

    @ApiOperation("订单完成")
    @PostMapping("/orderFinish")
    @RepeatSubmit(interval = 1000)
    public R<Boolean> orderFinish(@Valid ApiOrderFinishDto dto) {
        return R.ok(service.orderFinish(dto));
    }

    @ApiOperation("店员随机单接单")
    @GetMapping("/randomOrderTaking")
    @RepeatSubmit(interval = 1000)
    public R<Boolean> randomOrderTaking(String orderNo) {
        return R.ok(service.randomOrderTaking(orderNo));
    }

    @ApiOperation("店员接单")
    @GetMapping("/orderTaking")
    @RepeatSubmit(interval = 1000)
    public R<Boolean> orderTaking(String orderNo){
        return R.ok(service.orderTaking(orderNo));
    }

    @ApiOperation("店员开始服务")
    @GetMapping("/orderStartService")
    @RepeatSubmit(interval = 1000)
    public R<Boolean> orderStartService(String orderNo){
        return R.ok(service.orderStartService(orderNo));
    }

    @ApiOperation("订单分页查询")
    @GetMapping("/page")
    public R<ApiPageOrderInfoVo> page(@Valid ApiPageOrderInfoDto dto) {
        log.info("订单分页查询：开始，参数：{}", dto);
        ApiPageOrderInfoVo vo = new ApiPageOrderInfoVo();
        List<ApiOrderInfoVo> voList = new ArrayList<>();
        startPage();
        List<OrderInfo> orderInfoList = service.select(dto);
        TableDataInfo dataTable = getDataTable(orderInfoList);
        if (ObjectUtil.isNotEmpty(dataTable)) {
            orderInfoList.forEach(orderInfo -> {
                // 订单信息
                ApiOrderInfoVo orderInfoVo = new ApiOrderInfoVo();
                BeanUtils.copyBeanProp(orderInfoVo, orderInfo);
                // 订单详情数据
                List<OrderDetails> orderDetailsList = orderInfo.getOrderDetailsList();
                if (ObjectUtil.isNotEmpty(orderDetailsList)) {
                    List<ApiOrderDetailsVo> apiOrderDetailsVos = new ArrayList<>();
                    orderDetailsList.forEach(details -> {
                        ApiOrderDetailsVo orderDetailsVo = new ApiOrderDetailsVo();
                        BeanUtils.copyBeanProp(orderDetailsVo, details);
                        apiOrderDetailsVos.add(orderDetailsVo);
                    });
                    orderInfoVo.setOrderDetailsVos(apiOrderDetailsVos);
                }
                // 用户信息
                UserInfo userInfo = orderInfo.getUserInfo();
                if (ObjectUtil.isNotNull(userInfo)) {
                    ApiUserVo apiUserVo = new ApiUserVo();
                    BeanUtils.copyBeanProp(apiUserVo, userInfo);
                    orderInfoVo.setApiUserVo(apiUserVo);
                }
                // 店员信息
                StaffInfo staffInfo = orderInfo.getStaffInfo();
                if (ObjectUtil.isNotNull(staffInfo)) {
                    ApiStaffInfoVo staffInfoVo = new ApiStaffInfoVo();
                    BeanUtils.copyBeanProp(staffInfoVo, staffInfo);
                    orderInfoVo.setApiStaffInfoVo(staffInfoVo);
                }
                voList.add(orderInfoVo);
            });
        }
        vo.setData(voList)
                .setTotal(dataTable.getTotal());
        log.info("订单分页查询：完成，返回数据：{}", vo);
        return R.ok(vo);
    }

    @ApiOperation("查询服务过的用户id集合")
    @GetMapping("/selectServedUserId")
    public R<List<Long>> selectServedUserId(){
        return R.ok(service.selectServedUserId());
    }
}
