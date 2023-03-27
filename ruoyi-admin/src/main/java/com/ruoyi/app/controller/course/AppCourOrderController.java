package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.OrderIdUtils;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.course.vo.OrderVO;
import com.ruoyi.gauge.domain.PsyOrderPay;
import com.ruoyi.gauge.service.IPsyOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import com.ruoyi.course.service.ICourCourseService;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/course/order")
@Api(value = "AppCourOrderController" ,tags = {"课程订单API"})
public class AppCourOrderController extends BaseController
{
    @Autowired
    private ICourOrderService courOrderService;

    @Autowired
    private ICourCourseService courCourseService;

    @Autowired
    private IPsyOrderPayService psyOrderPayService;

    /**
     * 根据用户ID查询课程订单列表
     */
//    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程订单列表")
    public TableDataInfo list(@RequestBody CourOrder courOrder)
    {
        startPage();
        List<CourOrder> list = courOrderService.selectCourOrderList(courOrder);

        List<OrderVO> orderVOList = new ArrayList<>();
        // 返回的订单信息中需要包含课程详情信息
        list.forEach(item -> {
           CourCourse courCourse =  courCourseService.selectCourCourseById(item.getCourseId());
           OrderVO orderVO = new OrderVO();
           BeanUtils.copyProperties(item, orderVO);
           orderVO.setCourseInfo(courCourse);
           orderVOList.add(orderVO);
        });
        return getDataTable(orderVOList);
    }

    /**
     * 根据订单ID查询课程订单详情
     */
//    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/detail")
    @ApiOperation("根据订单ID查询课程订单详情")
    public AjaxResult detail(@RequestBody Map<String, String> params)
    {
        Integer orderId = Integer.valueOf(params.get("orderId")); // 订单ID
        OrderVO orderVO = courOrderService.getOrderDetailByOrderId(orderId);
        return AjaxResult.success(orderVO);
    }

    /**
     * 根据订单信息生成课程订单
     */
    @PutMapping("/add")
    @ApiOperation("根据订单信息生成课程订单")
    public AjaxResult generateOrder(@RequestBody CourOrder courOrder)
    {

        courOrder.setOrderId(OrderIdUtils.getOrderId());
        courOrder.setCreateTime(new Date());
        courOrder.setStatus(0); // 0代表订单处于创建状态

        return AjaxResult.success(courOrderService.insertCourOrder(courOrder));
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    @ApiOperation("根据订单信息生成课程订单")
    public AjaxResult cancelOrder(@RequestBody Map<String, Object> map)
    {
        int orderId = Integer.parseInt(map.get("orderId").toString());
        CourOrder courOrder = new CourOrder();
        courOrder.setId(orderId);
        courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_CANCELED); // 订单处于取消状态

        // 订单取消同时关闭支付信息
        PsyOrderPay orderPay = new PsyOrderPay();
        orderPay.setPayStatus(CourConstant.PAY_STATUE_CANCEL);
        orderPay.setOrderId(Long.parseLong(Integer.toString(orderId)));
        psyOrderPayService.updatePsyOrderPayByOrderId(orderPay);

        return AjaxResult.success(courOrderService.updateCourOrder(courOrder));
    }
}
