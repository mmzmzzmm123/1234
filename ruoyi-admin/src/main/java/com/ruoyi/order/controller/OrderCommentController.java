package com.ruoyi.order.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.order.domain.OrderComment;
import com.ruoyi.order.service.IOrderCommentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单评论管理Controller
 *
 * @author Lam
 * @date 2023-10-11
 */
@Api(tags = "admin-订单评论管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order/orderComment")
public class OrderCommentController extends BaseController {

    private final IOrderCommentService orderCommentService;


    @ApiOperation("查询订单评论管理列表")
    @PreAuthorize("@ss.hasPermi('order:orderComment:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderComment orderComment) {
        startPage();
        List<OrderComment> list = orderCommentService.selectOrderCommentList(orderComment);
        return getDataTable(list);
    }


    @ApiOperation("导出订单评论管理列表")
    @PreAuthorize("@ss.hasPermi('order:orderComment:export')")
    @Log(title = "订单评论管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderComment orderComment) {
        List<OrderComment> list = orderCommentService.selectOrderCommentList(orderComment);
        ExcelUtil<OrderComment> util = new ExcelUtil<OrderComment>(OrderComment.class);
        util.exportExcel(response, list, "订单评论管理数据");
    }


    @ApiOperation("获取订单评论管理详细信息")
    @PreAuthorize("@ss.hasPermi('order:orderComment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(orderCommentService.selectOrderCommentById(id));
    }


    @ApiOperation("新增订单评论管理")
    @PreAuthorize("@ss.hasPermi('order:orderComment:add')")
    @Log(title = "订单评论管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderComment orderComment) {
        return toAjax(orderCommentService.insertOrderComment(orderComment));
    }


    @ApiOperation("修改订单评论管理")
    @PreAuthorize("@ss.hasPermi('order:orderComment:edit')")
    @Log(title = "订单评论管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderComment orderComment) {
        return toAjax(orderCommentService.updateOrderComment(orderComment));
    }


    @ApiOperation("删除订单评论管理")
    @PreAuthorize("@ss.hasPermi('order:orderComment:remove')")
    @Log(title = "订单评论管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderCommentService.deleteOrderCommentByIds(ids));
    }
}
