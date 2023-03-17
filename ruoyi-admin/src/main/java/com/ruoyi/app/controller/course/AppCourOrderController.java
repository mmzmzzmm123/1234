package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.framework.web.service.AppTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 课程订单Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/app/course/order")
@Api(value = "AppCourOrderController" ,tags = {"用户课程订单API"})
public class AppCourOrderController extends BaseController
{
    @Autowired
    private ICourOrderService courOrderService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 查询课程订单列表
     */

    @GetMapping("/list")
    @ApiOperation("查询用户课程订单列表")
    public TableDataInfo list(CourOrder courOrder)
    {
//        LoginDTO loginUser = appTokenService.getLoginUser(request);
        startPage();
        List<CourOrder> list = courOrderService.selectCourOrderList(courOrder);
        return getDataTable(list);
    }




}
