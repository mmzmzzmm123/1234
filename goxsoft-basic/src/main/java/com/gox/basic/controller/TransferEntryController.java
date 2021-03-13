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
import com.gox.basic.domain.TransferEntry;
import com.gox.basic.service.ITransferEntryService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 *   Controller
 * 
 * @author gox
 * @date 2021-03-10
 */
@RestController
@RequestMapping("/basic/entry")
public class TransferEntryController extends BaseController
{
    @Autowired
    private ITransferEntryService transferEntryService;

    /**
     * 查询  列表
     */
    @PreAuthorize("@ss.hasPermi('basic:entry:list')")
    @GetMapping("/list")
    public TableDataInfo list(TransferEntry transferEntry)
    {
        startPage();
        List<TransferEntry> list = transferEntryService.selectTransferEntryList(transferEntry);
        return getDataTable(list);
    }

    /**
     * 导出  列表
     */
    @PreAuthorize("@ss.hasPermi('basic:entry:export')")
    @Log(title = "  ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TransferEntry transferEntry)
    {
        List<TransferEntry> list = transferEntryService.selectTransferEntryList(transferEntry);
        ExcelUtil<TransferEntry> util = new ExcelUtil<TransferEntry>(TransferEntry.class);
        return util.exportExcel(list, "entry");
    }

    /**
     * 获取  详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:entry:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(transferEntryService.selectTransferEntryById(id));
    }

    /**
     * 新增  
     */
    @PreAuthorize("@ss.hasPermi('basic:entry:add')")
    @Log(title = "  ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TransferEntry transferEntry)
    {
        return toAjax(transferEntryService.insertTransferEntry(transferEntry));
    }

    /**
     * 修改  
     */
    @PreAuthorize("@ss.hasPermi('basic:entry:edit')")
    @Log(title = "  ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TransferEntry transferEntry)
    {
        return toAjax(transferEntryService.updateTransferEntry(transferEntry));
    }

    /**
     * 删除  
     */
    @PreAuthorize("@ss.hasPermi('basic:entry:remove')")
    @Log(title = "  ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(transferEntryService.deleteTransferEntryByIds(ids));
    }
}
