package com.apollo.usv.controller;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.apollo.usv.domain.CollectorRecord;
import com.apollo.usv.service.ICollectorRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 水质记录Controller
 * 
 * @author lwq
 * @date 2021-07-02
 */
@RestController
@RequestMapping("/usv/record")
public class CollectorRecordController extends BaseController
{
    @Autowired
    private ICollectorRecordService collectorRecordService;

    /**
     * 查询水质记录列表
     */
    @PreAuthorize("@ss.hasPermi('usv:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(CollectorRecord collectorRecord)
    {
        startPage();
        List<CollectorRecord> list = collectorRecordService.selectCollectorRecordList(collectorRecord);
        return getDataTable(list);
    }

    /**
     * 导出水质记录列表
     */
    @PreAuthorize("@ss.hasPermi('usv:record:export')")
    @Log(title = "水质记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CollectorRecord collectorRecord)
    {
        List<CollectorRecord> list = collectorRecordService.selectCollectorRecordList(collectorRecord);
        ExcelUtil<CollectorRecord> util = new ExcelUtil<CollectorRecord>(CollectorRecord.class);
        return util.exportExcel(list, "水质记录数据");
    }

    /**
     * 获取水质记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('usv:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(collectorRecordService.selectCollectorRecordById(id));
    }

    /**
     * 新增水质记录
     */
    @PreAuthorize("@ss.hasPermi('usv:record:add')")
    @Log(title = "水质记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CollectorRecord collectorRecord)
    {
        return toAjax(collectorRecordService.insertCollectorRecord(collectorRecord));
    }

    /**
     * 修改水质记录
     */
    @PreAuthorize("@ss.hasPermi('usv:record:edit')")
    @Log(title = "水质记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CollectorRecord collectorRecord)
    {
        return toAjax(collectorRecordService.updateCollectorRecord(collectorRecord));
    }

    /**
     * 删除水质记录
     */
    @PreAuthorize("@ss.hasPermi('usv:record:remove')")
    @Log(title = "水质记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(collectorRecordService.deleteCollectorRecordByIds(ids));
    }
}
