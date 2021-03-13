package com.gox.basic.controller;

import java.util.List;
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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.basic.domain.Transfer;
import com.gox.basic.service.ITransferService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 *   Controller
 * 
 * @author gox
 * @date 2021-03-10
 */
@RestController
@RequestMapping("/basic/transfer")
public class TransferController extends BaseController
{
    @Autowired
    private ITransferService transferService;

    /**
     * 查询  列表
     */
    @PreAuthorize("@ss.hasPermi('basic:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Transfer transfer)
    {
        startPage();
        List<Transfer> list = transferService.selectTransferList(transfer);
        return getDataTable(list);
    }

    /**
     * 导出  列表
     */
    @PreAuthorize("@ss.hasPermi('basic:transfer:export')")
    @Log(title = "  ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Transfer transfer)
    {
        List<Transfer> list = transferService.selectTransferList(transfer);
        ExcelUtil<Transfer> util = new ExcelUtil<Transfer>(Transfer.class);
        return util.exportExcel(list, "transfer");
    }

    /**
     * 获取  详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:transfer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(transferService.selectTransferById(id));
    }

    /**
     * 新增  
     */
    @PreAuthorize("@ss.hasPermi('basic:transfer:add')")
    @Log(title = "  ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Transfer transfer)
    {
        return toAjax(transferService.insertTransfer(transfer));
    }

    /**
     * 修改  
     */
    @PreAuthorize("@ss.hasPermi('basic:transfer:edit')")
    @Log(title = "  ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Transfer transfer)
    {
        return toAjax(transferService.updateTransfer(transfer));
    }

    /**
     * 删除  
     */
    @PreAuthorize("@ss.hasPermi('basic:transfer:remove')")
    @Log(title = "  ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(transferService.deleteTransferByIds(ids));
    }
}
