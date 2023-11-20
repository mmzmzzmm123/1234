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
import com.ruoyi.ufreight.domain.InboundHistory;
import com.ruoyi.ufreight.service.IInboundHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库流水Controller
 * 
 * @author derek
 * @date 2023-11-20
 */
@RestController
@RequestMapping("/ufreight/inboundHistory")
public class InboundHistoryController extends BaseController
{
    @Autowired
    private IInboundHistoryService inboundHistoryService;

    /**
     * 查询入库流水列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:inboundHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(InboundHistory inboundHistory)
    {
        startPage();
        List<InboundHistory> list = inboundHistoryService.selectInboundHistoryList(inboundHistory);
        return getDataTable(list);
    }

    /**
     * 导出入库流水列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:inboundHistory:export')")
    @Log(title = "入库流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InboundHistory inboundHistory)
    {
        List<InboundHistory> list = inboundHistoryService.selectInboundHistoryList(inboundHistory);
        ExcelUtil<InboundHistory> util = new ExcelUtil<InboundHistory>(InboundHistory.class);
        util.exportExcel(response, list, "入库流水数据");
    }

    /**
     * 获取入库流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('ufreight:inboundHistory:query')")
    @GetMapping(value = "/{inboundId}")
    public AjaxResult getInfo(@PathVariable("inboundId") Long inboundId)
    {
        return success(inboundHistoryService.selectInboundHistoryByInboundId(inboundId));
    }

    /**
     * 新增入库流水
     */
    @PreAuthorize("@ss.hasPermi('ufreight:inboundHistory:add')")
    @Log(title = "入库流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InboundHistory inboundHistory)
    {
        return toAjax(inboundHistoryService.insertInboundHistory(inboundHistory));
    }

    /**
     * 修改入库流水
     */
    @PreAuthorize("@ss.hasPermi('ufreight:inboundHistory:edit')")
    @Log(title = "入库流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InboundHistory inboundHistory)
    {
        return toAjax(inboundHistoryService.updateInboundHistory(inboundHistory));
    }

    /**
     * 删除入库流水
     */
    @PreAuthorize("@ss.hasPermi('ufreight:inboundHistory:remove')")
    @Log(title = "入库流水", businessType = BusinessType.DELETE)
	@DeleteMapping("/{inboundIds}")
    public AjaxResult remove(@PathVariable Long[] inboundIds)
    {
        return toAjax(inboundHistoryService.deleteInboundHistoryByInboundIds(inboundIds));
    }
}
