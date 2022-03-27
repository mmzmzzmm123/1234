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
import com.jlt.csa.domain.CommunicateRecord;
import com.jlt.csa.service.ICommunicateRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 沟通记录Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/commrecord")
public class CommunicateRecordController extends BaseController
{
    @Autowired
    private ICommunicateRecordService communicateRecordService;

    /**
     * 查询沟通记录列表
     */
    @PreAuthorize("@ss.hasPermi('csa:commrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CommunicateRecord communicateRecord)
    {
        startPage();
        List<CommunicateRecord> list = communicateRecordService.selectCommunicateRecordList(communicateRecord);
        return getDataTable(list);
    }

    /**
     * 导出沟通记录列表
     */
    @PreAuthorize("@ss.hasPermi('csa:commrecord:export')")
    @Log(title = "沟通记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunicateRecord communicateRecord)
    {
        List<CommunicateRecord> list = communicateRecordService.selectCommunicateRecordList(communicateRecord);
        ExcelUtil<CommunicateRecord> util = new ExcelUtil<CommunicateRecord>(CommunicateRecord.class);
        util.exportExcel(response, list, "沟通记录数据");
    }

    /**
     * 获取沟通记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:commrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(communicateRecordService.selectCommunicateRecordById(id));
    }

    /**
     * 新增沟通记录
     */
    @PreAuthorize("@ss.hasPermi('csa:commrecord:add')")
    @Log(title = "沟通记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunicateRecord communicateRecord)
    {
        return toAjax(communicateRecordService.insertCommunicateRecord(communicateRecord));
    }

    /**
     * 修改沟通记录
     */
    @PreAuthorize("@ss.hasPermi('csa:commrecord:edit')")
    @Log(title = "沟通记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunicateRecord communicateRecord)
    {
        return toAjax(communicateRecordService.updateCommunicateRecord(communicateRecord));
    }

    /**
     * 删除沟通记录
     */
    @PreAuthorize("@ss.hasPermi('csa:commrecord:remove')")
    @Log(title = "沟通记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communicateRecordService.deleteCommunicateRecordByIds(ids));
    }
}
