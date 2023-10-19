package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.CleanRecordH5Vo;
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
import com.ruoyi.office.domain.TRoomCleanRecord;
import com.ruoyi.office.service.ITRoomCleanRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间打扫记录Controller
 *
 * @author ruoyi
 * @date 2023-09-30
 */
@RestController
@RequestMapping("/office/cleanrecord")
public class TRoomCleanRecordController extends BaseController {
    @Autowired
    private ITRoomCleanRecordService tRoomCleanRecordService;

    /**
     * 查询房间打扫记录列表
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomCleanRecord tRoomCleanRecord) {
        startPage();
        List<TRoomCleanRecord> list = tRoomCleanRecordService.selectTRoomCleanRecordList(tRoomCleanRecord);
        return getDataTable(list);
    }


    /**
     * 查询房间打扫记录列表
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:list')")
    @GetMapping("/h5list")
    public TableDataInfo h5list(CleanRecordH5Vo tRoomCleanRecord) {
        Long userId = SecurityUtils.getUserId();
        tRoomCleanRecord.setCreateBy(userId + "");
        startPage();
        List<CleanRecordH5Vo> list = tRoomCleanRecordService.selectTRoomCleanRecordH5List(tRoomCleanRecord);
        return getDataTable(list);
    }

    /**
     * 导出房间打扫记录列表
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:export')")
    @Log(title = "房间打扫记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomCleanRecord tRoomCleanRecord) {
        List<TRoomCleanRecord> list = tRoomCleanRecordService.selectTRoomCleanRecordList(tRoomCleanRecord);
        ExcelUtil<TRoomCleanRecord> util = new ExcelUtil<TRoomCleanRecord>(TRoomCleanRecord.class);
        util.exportExcel(response, list, "房间打扫记录数据");
    }

    /**
     * 获取房间打扫记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tRoomCleanRecordService.selectTRoomCleanRecordById(id));
    }

    /**
     * 新增房间打扫记录
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:add')")
    @Log(title = "房间打扫记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomCleanRecord tRoomCleanRecord) {
        return toAjax(tRoomCleanRecordService.insertTRoomCleanRecord(tRoomCleanRecord));
    }

    /**
     * 修改房间打扫记录
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:edit')")
    @Log(title = "房间打扫记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomCleanRecord tRoomCleanRecord) {
        return toAjax(tRoomCleanRecordService.updateTRoomCleanRecord(tRoomCleanRecord));
    }

    /**
     * 删除房间打扫记录
     */
    @PreAuthorize("@ss.hasPermi('office:cleanrecord:remove')")
    @Log(title = "房间打扫记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tRoomCleanRecordService.deleteTRoomCleanRecordByIds(ids));
    }
}
