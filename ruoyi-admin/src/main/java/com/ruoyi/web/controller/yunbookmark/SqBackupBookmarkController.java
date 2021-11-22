package com.ruoyi.web.controller.yunbookmark;


import com.ruoyi.bookmark.pojo.SqBackupBookmarkReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.bookmark.domain.SqBackupBookmark;
import com.ruoyi.bookmark.service.ISqBackupBookmarkService;

/**
 * 备份书签Controller
 *
 * @author ruoyi
 * @date 2021-11-22
 */
@RestController
@RequestMapping("/ruoyi-yunbookmark/backp")
public class SqBackupBookmarkController extends BaseController
{
    @Autowired
    private ISqBackupBookmarkService sqBackupBookmarkService;


    /**
     * 获取当前用户  备份书签配置 信息
     */
    @GetMapping("/getBackupinfo")
    public AjaxResult getInfo()
    {
        SqBackupBookmark sqBackupBookmark = new SqBackupBookmark();
        sqBackupBookmark.setUserId(getAuthUser().getUserId());
        return AjaxResult.success(sqBackupBookmarkService.selectSqBackupBookmarkList(sqBackupBookmark).get(0));
    }



     /**
     * 修改备份书签 配置信息
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SqBackupBookmarkReq req)
    {
        SqBackupBookmark sqBackupBookmark = new SqBackupBookmark();
        BeanUtils.copyProperties(req,sqBackupBookmark);
        sqBackupBookmark.setUserId(getAuthUser().getUserId());
        return toAjax(sqBackupBookmarkService.updateSqBackupBookmark(sqBackupBookmark));
    }






//    /**
//     * 查询备份书签列表
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:backp:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(SqBackupBookmark sqBackupBookmark)
//    {
//        startPage();
//        List<SqBackupBookmark> list = sqBackupBookmarkService.selectSqBackupBookmarkList(sqBackupBookmark);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出备份书签列表
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:backp:export')")
//    @Log(title = "备份书签", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(SqBackupBookmark sqBackupBookmark)
//    {
//        List<SqBackupBookmark> list = sqBackupBookmarkService.selectSqBackupBookmarkList(sqBackupBookmark);
//        ExcelUtil<SqBackupBookmark> util = new ExcelUtil<SqBackupBookmark>(SqBackupBookmark.class);
//        return util.exportExcel(list, "backp");
//    }
//
//    /**
//     * 获取备份书签详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:backp:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id)
//    {
//        return AjaxResult.success(sqBackupBookmarkService.selectSqBackupBookmarkById(id));
//    }
//
//    /**
//     * 新增备份书签
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:backp:add')")
//    @Log(title = "备份书签", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SqBackupBookmark sqBackupBookmark)
//    {
//        return toAjax(sqBackupBookmarkService.insertSqBackupBookmark(sqBackupBookmark));
//    }
//
//    /**
//     * 修改备份书签
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:backp:edit')")
//    @Log(title = "备份书签", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SqBackupBookmark sqBackupBookmark)
//    {
//        return toAjax(sqBackupBookmarkService.updateSqBackupBookmark(sqBackupBookmark));
//    }
//
//    /**
//     * 删除备份书签
//     */
//    @PreAuthorize("@ss.hasPermi('ruoyi-yunbookmark:backp:remove')")
//    @Log(title = "备份书签", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(sqBackupBookmarkService.deleteSqBackupBookmarkByIds(ids));
//    }
}
