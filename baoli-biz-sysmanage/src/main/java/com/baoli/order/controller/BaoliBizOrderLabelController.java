package com.baoli.order.controller;

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
import com.baoli.order.domain.BaoliBizOrderLabel;
import com.baoli.order.service.IBaoliBizOrderLabelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单标签管理Controller
 * 
 * @author niujs
 * @date 2024-04-25
 */
@RestController
@RequestMapping("/order/label")
public class BaoliBizOrderLabelController extends BaseController
{
    @Autowired
    private IBaoliBizOrderLabelService baoliBizOrderLabelService;

    /**
     * 查询订单标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:label:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizOrderLabel baoliBizOrderLabel)
    {
        startPage();
        List<BaoliBizOrderLabel> list = baoliBizOrderLabelService.selectBaoliBizOrderLabelList(baoliBizOrderLabel);
        return getDataTable(list);
    }

    /**
     * 导出订单标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:label:export')")
    @Log(title = "订单标签管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizOrderLabel baoliBizOrderLabel)
    {
        List<BaoliBizOrderLabel> list = baoliBizOrderLabelService.selectBaoliBizOrderLabelList(baoliBizOrderLabel);
        ExcelUtil<BaoliBizOrderLabel> util = new ExcelUtil<BaoliBizOrderLabel>(BaoliBizOrderLabel.class);
        util.exportExcel(response, list, "订单标签管理数据");
    }

    /**
     * 获取订单标签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:label:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizOrderLabelService.selectBaoliBizOrderLabelById(id));
    }

    /**
     * 新增订单标签管理
     */
    @PreAuthorize("@ss.hasPermi('order:label:add')")
    @Log(title = "订单标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizOrderLabel baoliBizOrderLabel)
    {
        baoliBizOrderLabel.setSendUserId(getUserId());
        baoliBizOrderLabel.setStatus("01");//未审核
        return toAjax(baoliBizOrderLabelService.insertBaoliBizOrderLabel(baoliBizOrderLabel));
    }

    /**
     * 修改订单标签管理
     */
    @PreAuthorize("@ss.hasPermi('order:label:edit')")
    @Log(title = "订单标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizOrderLabel baoliBizOrderLabel)
    {
        baoliBizOrderLabel.setStatus("02");//已审核
        return toAjax(baoliBizOrderLabelService.updateBaoliBizOrderLabel(baoliBizOrderLabel));
    }

    /**
     * 删除订单标签管理
     */
    @PreAuthorize("@ss.hasPermi('order:label:remove')")
    @Log(title = "订单标签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizOrderLabelService.deleteBaoliBizOrderLabelByIds(ids));
    }
}
