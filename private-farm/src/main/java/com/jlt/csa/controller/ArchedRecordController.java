package com.jlt.csa.controller;

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
import com.jlt.csa.domain.ArchedRecord;
import com.jlt.csa.service.IArchedRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 拱棚使用记录Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/archedrecord")
public class ArchedRecordController extends BaseController
{
    @Autowired
    private IArchedRecordService archedRecordService;

    /**
     * 查询拱棚使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('csa:archedrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(ArchedRecord archedRecord)
    {
        startPage();
        List<ArchedRecord> list = archedRecordService.selectArchedRecordList(archedRecord);
        return getDataTable(list);
    }

    /**
     * 导出拱棚使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('csa:archedrecord:export')")
    @Log(title = "拱棚使用记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ArchedRecord archedRecord)
    {
        List<ArchedRecord> list = archedRecordService.selectArchedRecordList(archedRecord);
        ExcelUtil<ArchedRecord> util = new ExcelUtil<ArchedRecord>(ArchedRecord.class);
        util.exportExcel(response, list, "拱棚使用记录数据");
    }

    /**
     * 获取拱棚使用记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:archedrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(archedRecordService.selectArchedRecordById(id));
    }

    /**
     * 新增拱棚使用记录
     */
    @PreAuthorize("@ss.hasPermi('csa:archedrecord:add')")
    @Log(title = "拱棚使用记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ArchedRecord archedRecord)
    {
        return toAjax(archedRecordService.insertArchedRecord(archedRecord));
    }

    /**
     * 修改拱棚使用记录
     */
    @PreAuthorize("@ss.hasPermi('csa:archedrecord:edit')")
    @Log(title = "拱棚使用记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ArchedRecord archedRecord)
    {
        return toAjax(archedRecordService.updateArchedRecord(archedRecord));
    }

    /**
     * 删除拱棚使用记录
     */
    @PreAuthorize("@ss.hasPermi('csa:archedrecord:remove')")
    @Log(title = "拱棚使用记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(archedRecordService.deleteArchedRecordByIds(ids));
    }
}
