package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.course.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/course/order")
@Api(value = "AppCourOrderController" ,tags = {"课程订单API"})
public class AppCourOrderController extends BaseController
{
    @Autowired
    private ICourOrderService courOrderService;

    /**
     * 查询课程订单列表
     */
//    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程订单列表")
    public TableDataInfo list(@RequestBody CourOrder courOrder)
    {
        startPage();
        List<CourOrder> list = courOrderService.selectCourOrderList(courOrder);
        return getDataTable(list);
    }

    /**
     * 根据订单编号查询课程订单详情
     */
//    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/detail")
    @ApiOperation("根据订单编号查询课程订单详情")
    public AjaxResult detail(@RequestBody CourOrder courOrder)
    {
        OrderVO orderVO = courOrderService.getOrderDetailByOrderId(courOrder.getOrderId());
        return AjaxResult.success(orderVO);
    }

}
