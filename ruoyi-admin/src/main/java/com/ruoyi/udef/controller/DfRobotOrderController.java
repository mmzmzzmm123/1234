package com.ruoyi.udef.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.udef.domain.DfRobotOrder;
import com.ruoyi.udef.service.IDfRobotOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机器人订单Controller
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/udef/position")
public class DfRobotOrderController extends BaseController
{
    @Autowired
    private IDfRobotOrderService dfRobotOrderService;

    /**
     * 查询机器人订单列表
     */
    @PreAuthorize("@ss.hasPermi('udef:position:list')")
    @GetMapping("/list")
    public TableDataInfo list(DfRobotOrder dfRobotOrder)
    {
        startPage();
        List<DfRobotOrder> list = dfRobotOrderService.selectDfRobotOrderList(dfRobotOrder);
        return getDataTable(list);
    }

    /**
     * 导出机器人订单列表
     */
    @PreAuthorize("@ss.hasPermi('udef:position:export')")
    @Log(title = "机器人订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DfRobotOrder dfRobotOrder)
    {
        List<DfRobotOrder> list = dfRobotOrderService.selectDfRobotOrderList(dfRobotOrder);
        ExcelUtil<DfRobotOrder> util = new ExcelUtil<DfRobotOrder>(DfRobotOrder.class);
        util.exportExcel(response, list, "机器人订单数据");
    }

    /**
     * 获取机器人订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('udef:position:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dfRobotOrderService.selectDfRobotOrderById(id));
    }

    /**
     * 新增机器人订单
     */
    @PreAuthorize("@ss.hasPermi('udef:position:add')")
    @Log(title = "机器人订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DfRobotOrder dfRobotOrder)
    {
        return toAjax(dfRobotOrderService.insertDfRobotOrder(dfRobotOrder));
    }

    /**
     * 修改机器人订单
     */
    @PreAuthorize("@ss.hasPermi('udef:position:edit')")
    @Log(title = "机器人订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DfRobotOrder dfRobotOrder)
    {
        return toAjax(dfRobotOrderService.updateDfRobotOrder(dfRobotOrder));
    }

    /**
     * 删除机器人订单
     */
    @PreAuthorize("@ss.hasPermi('udef:position:remove')")
    @Log(title = "机器人订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dfRobotOrderService.deleteDfRobotOrderByIds(ids));
    }
}
