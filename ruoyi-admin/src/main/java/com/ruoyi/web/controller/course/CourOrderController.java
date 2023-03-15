package com.ruoyi.web.controller.course;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课程订单Controller
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/course/order")
public class CourOrderController extends BaseController
{
    @Autowired
    private ICourOrderService courOrderService;

    /**
     * 查询课程订单列表
     */
    @PreAuthorize("@ss.hasPermi('course:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourOrder courOrder)
    {
        startPage();
        List<CourOrder> list = courOrderService.selectCourOrderList(courOrder);
        return getDataTable(list);
    }

    /**
     * 导出课程订单列表
     */
    @PreAuthorize("@ss.hasPermi('course:order:export')")
    @Log(title = "课程订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourOrder courOrder)
    {
        List<CourOrder> list = courOrderService.selectCourOrderList(courOrder);
        ExcelUtil<CourOrder> util = new ExcelUtil<CourOrder>(CourOrder.class);
        util.exportExcel(response, list, "课程订单数据");
    }

    /**
     * 获取课程订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(courOrderService.selectCourOrderById(id));
    }

    /**
     * 新增课程订单
     */
    @PreAuthorize("@ss.hasPermi('course:order:add')")
    @Log(title = "课程订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourOrder courOrder)
    {
        return toAjax(courOrderService.insertCourOrder(courOrder));
    }

    /**
     * 修改课程订单
     */
    @PreAuthorize("@ss.hasPermi('course:order:edit')")
    @Log(title = "课程订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourOrder courOrder)
    {
        return toAjax(courOrderService.updateCourOrder(courOrder));
    }

    /**
     * 删除课程订单
     */
    @PreAuthorize("@ss.hasPermi('course:order:remove')")
    @Log(title = "课程订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courOrderService.deleteCourOrderByIds(ids));
    }
}
