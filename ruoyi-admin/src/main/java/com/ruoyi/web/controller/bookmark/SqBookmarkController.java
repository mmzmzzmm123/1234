package com.ruoyi.web.controller.bookmark;



import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.sq.domain.SqBookmark;
import com.ruoyi.sq.service.ISqBookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书签Controller
 * 
 * @author wanghao
 * @date 2020-07-26
 */
@RestController
@RequestMapping("/bookmark/bookmark")
public class SqBookmarkController extends BaseController
{
    @Autowired
    private ISqBookmarkService sqBookmarkService;

    /**
     * 查询书签列表
     */
    @Log(title = "用户管理", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqBookmark sqBookmark)
    {
        startPage();
        List<SqBookmark> list = sqBookmarkService.selectSqBookmarkList(sqBookmark);
        return getDataTable(list);
    }

    /**
     * 导出书签列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:export')")
    @Log(title = "书签", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqBookmark sqBookmark)
    {
        List<SqBookmark> list = sqBookmarkService.selectSqBookmarkList(sqBookmark);
        ExcelUtil<SqBookmark> util = new ExcelUtil<SqBookmark>(SqBookmark.class);
        return util.exportExcel(list, "bookmark");
    }

    /**
     * 获取书签详细信息
     */
    @Log(title = "查询书签信息ID", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:query')")
    @GetMapping(value = "/{bookmarkId}")
    public AjaxResult getInfo(@PathVariable("bookmarkId") Long bookmarkId)
    {
        return AjaxResult.success(sqBookmarkService.selectSqBookmarkById(bookmarkId));
    }

    /**
     * 新增书签
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:add')")
    @Log(title = "新增书签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqBookmark sqBookmark)
    {
        return toAjax(sqBookmarkService.insertSqBookmark(sqBookmark));
    }

    /**
     * 修改书签
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:edit')")
    @Log(title = "修改书签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqBookmark sqBookmark)
    {
        return toAjax(sqBookmarkService.updateSqBookmark(sqBookmark));
    }

    /**
     * 删除书签
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:remove')")
    @Log(title = "删除书签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookmarkIds}")
    public AjaxResult remove(@PathVariable Long[] bookmarkIds)
    {
        return toAjax(sqBookmarkService.deleteSqBookmarkByIds(bookmarkIds));
    }
}
