package com.ruoyi.web.controller.yunbookmark;

import java.util.List;

import com.ruoyi.bookmark.domain.SqBackupBookmarkLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;

import com.ruoyi.bookmark.service.ISqBackupBookmarkLogService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 备份记录表Controller
 *
 * @author wanghao
 * @date 2021-11-22
 */
@RestController
@RequestMapping("/ruoyi-yunbookmark/log")
public class SqBackupBookmarkLogController extends BaseController
{
    @Autowired
    private ISqBackupBookmarkLogService sqBackupBookmarkLogService;

    /**
     * 查询备份记录表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SqBackupBookmarkLog sqBackupBookmarkLog)
    {
        startPage();
        sqBackupBookmarkLog.setUserId(getAuthUser().getUserId());
        List<SqBackupBookmarkLog> list = sqBackupBookmarkLogService.selectSqBackupBookmarkLogList(sqBackupBookmarkLog);
        return getDataTable(list);
    }
//
//    /**
//     * 导出备份记录表列表
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:log:export')")
//    @Log(title = "备份记录表", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(SqBackupBookmarkLog sqBackupBookmarkLog)
//    {
//        List<SqBackupBookmarkLog> list = sqBackupBookmarkLogService.selectSqBackupBookmarkLogList(sqBackupBookmarkLog);
//        ExcelUtil<SqBackupBookmarkLog> util = new ExcelUtil<SqBackupBookmarkLog>(SqBackupBookmarkLog.class);
//        return util.exportExcel(list, "log");
//    }
//
//    /**
//     * 获取备份记录表详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:log:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id)
//    {
//        return AjaxResult.success(sqBackupBookmarkLogService.selectSqBackupBookmarkLogById(id));
//    }
//
//    /**
//     * 新增备份记录表
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:log:add')")
//    @Log(title = "备份记录表", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SqBackupBookmarkLog sqBackupBookmarkLog)
//    {
//        return toAjax(sqBackupBookmarkLogService.insertSqBackupBookmarkLog(sqBackupBookmarkLog));
//    }
//
//    /**
//     * 修改备份记录表
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:log:edit')")
//    @Log(title = "备份记录表", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SqBackupBookmarkLog sqBackupBookmarkLog)
//    {
//        return toAjax(sqBackupBookmarkLogService.updateSqBackupBookmarkLog(sqBackupBookmarkLog));
//    }
//
//    /**
//     * 删除备份记录表
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:log:remove')")
//    @Log(title = "备份记录表", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(sqBackupBookmarkLogService.deleteSqBackupBookmarkLogByIds(ids));
//    }
}
