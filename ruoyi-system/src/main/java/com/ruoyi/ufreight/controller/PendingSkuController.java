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
import com.ruoyi.ufreight.domain.PendingSku;
import com.ruoyi.ufreight.service.IPendingSkuService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 待处理skuController
 * 
 * @author derek
 * @date 2023-11-20
 */
@RestController
@RequestMapping("/ufreight/sku")
public class PendingSkuController extends BaseController
{
    @Autowired
    private IPendingSkuService pendingSkuService;

    /**
     * 查询待处理sku列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:sku:list')")
    @GetMapping("/list")
    public TableDataInfo list(PendingSku pendingSku)
    {
        startPage();
        List<PendingSku> list = pendingSkuService.selectPendingSkuList(pendingSku);
        return getDataTable(list);
    }

    /**
     * 导出待处理sku列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:sku:export')")
    @Log(title = "待处理sku", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PendingSku pendingSku)
    {
        List<PendingSku> list = pendingSkuService.selectPendingSkuList(pendingSku);
        ExcelUtil<PendingSku> util = new ExcelUtil<PendingSku>(PendingSku.class);
        util.exportExcel(response, list, "待处理sku数据");
    }

    /**
     * 获取待处理sku详细信息
     */
    @PreAuthorize("@ss.hasPermi('ufreight:sku:query')")
    @GetMapping(value = "/{pendingId}")
    public AjaxResult getInfo(@PathVariable("pendingId") Long pendingId)
    {
        return success(pendingSkuService.selectPendingSkuByPendingId(pendingId));
    }

    /**
     * 新增待处理sku
     */
    @PreAuthorize("@ss.hasPermi('ufreight:sku:add')")
    @Log(title = "待处理sku", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PendingSku pendingSku)
    {
        return toAjax(pendingSkuService.insertPendingSku(pendingSku));
    }

    /**
     * 修改待处理sku
     */
    @PreAuthorize("@ss.hasPermi('ufreight:sku:edit')")
    @Log(title = "待处理sku", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PendingSku pendingSku)
    {
        return toAjax(pendingSkuService.updatePendingSku(pendingSku));
    }

    /**
     * 删除待处理sku
     */
    @PreAuthorize("@ss.hasPermi('ufreight:sku:remove')")
    @Log(title = "待处理sku", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pendingIds}")
    public AjaxResult remove(@PathVariable Long[] pendingIds)
    {
        return toAjax(pendingSkuService.deletePendingSkuByPendingIds(pendingIds));
    }
}
