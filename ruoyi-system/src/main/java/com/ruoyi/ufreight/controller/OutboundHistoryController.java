package com.ruoyi.ufreight.controller;

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
import com.ruoyi.ufreight.domain.OutboundHistory;
import com.ruoyi.ufreight.service.IOutboundHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库流水Controller
 * 
 * @author derek
 * @date 2023-11-20
 */
@RestController
@RequestMapping("/ufreight/outboundHistory")
public class OutboundHistoryController extends BaseController
{
    @Autowired
    private IOutboundHistoryService outboundHistoryService;

    /**
     * 查询出库流水列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:outboundHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(OutboundHistory outboundHistory)
    {
        startPage();
        List<OutboundHistory> list = outboundHistoryService.selectOutboundHistoryList(outboundHistory);
        return getDataTable(list);
    }

    /**
     * 导出出库流水列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:outboundHistory:export')")
    @Log(title = "出库流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutboundHistory outboundHistory)
    {
        List<OutboundHistory> list = outboundHistoryService.selectOutboundHistoryList(outboundHistory);
        ExcelUtil<OutboundHistory> util = new ExcelUtil<OutboundHistory>(OutboundHistory.class);
        util.exportExcel(response, list, "出库流水数据");
    }

    /**
     * 获取出库流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('ufreight:outboundHistory:query')")
    @GetMapping(value = "/{outboundId}")
    public AjaxResult getInfo(@PathVariable("outboundId") Long outboundId)
    {
        return success(outboundHistoryService.selectOutboundHistoryByOutboundId(outboundId));
    }

    /**
     * 新增出库流水
     */
    @PreAuthorize("@ss.hasPermi('ufreight:outboundHistory:add')")
    @Log(title = "出库流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OutboundHistory outboundHistory)
    {
        return toAjax(outboundHistoryService.insertOutboundHistory(outboundHistory));
    }

    /**
     * 修改出库流水
     */
    @PreAuthorize("@ss.hasPermi('ufreight:outboundHistory:edit')")
    @Log(title = "出库流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OutboundHistory outboundHistory)
    {
        return toAjax(outboundHistoryService.updateOutboundHistory(outboundHistory));
    }

    /**
     * 删除出库流水
     */
    @PreAuthorize("@ss.hasPermi('ufreight:outboundHistory:remove')")
    @Log(title = "出库流水", businessType = BusinessType.DELETE)
	@DeleteMapping("/{outboundIds}")
    public AjaxResult remove(@PathVariable Long[] outboundIds)
    {
        return toAjax(outboundHistoryService.deleteOutboundHistoryByOutboundIds(outboundIds));
    }
}
