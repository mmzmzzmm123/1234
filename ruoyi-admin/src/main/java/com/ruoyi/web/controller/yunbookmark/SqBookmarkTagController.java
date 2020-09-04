package com.ruoyi.web.controller.yunbookmark;

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
import com.ruoyi.bookmark.domain.SqBookmarkTag;
import com.ruoyi.bookmark.service.ISqBookmarkTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 书签标签管理Controller
 *
 * @author ruoyi
 * @date 2020-09-04
 */
@RestController
@RequestMapping("/tag/tag")
public class SqBookmarkTagController extends BaseController
{
    @Autowired
    private ISqBookmarkTagService sqBookmarkTagService;

    /**
     * 查询书签标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('tag:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqBookmarkTag sqBookmarkTag)
    {
        startPage();
        List<SqBookmarkTag> list = sqBookmarkTagService.selectSqBookmarkTagList(sqBookmarkTag);
        return getDataTable(list);
    }

    /**
     * 导出书签标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('tag:tag:export')")
    @Log(title = "书签标签管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqBookmarkTag sqBookmarkTag)
    {
        List<SqBookmarkTag> list = sqBookmarkTagService.selectSqBookmarkTagList(sqBookmarkTag);
        ExcelUtil<SqBookmarkTag> util = new ExcelUtil<SqBookmarkTag>(SqBookmarkTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 获取书签标签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('tag:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sqBookmarkTagService.selectSqBookmarkTagById(id));
    }

    /**
     * 新增书签标签管理
     */
    @PreAuthorize("@ss.hasPermi('tag:tag:add')")
    @Log(title = "书签标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqBookmarkTag sqBookmarkTag)
    {
        return toAjax(sqBookmarkTagService.insertSqBookmarkTag(sqBookmarkTag));
    }

    /**
     * 修改书签标签管理
     */
    @PreAuthorize("@ss.hasPermi('tag:tag:edit')")
    @Log(title = "书签标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqBookmarkTag sqBookmarkTag)
    {
        return toAjax(sqBookmarkTagService.updateSqBookmarkTag(sqBookmarkTag));
    }

    /**
     * 删除书签标签管理
     */
    @PreAuthorize("@ss.hasPermi('tag:tag:remove')")
    @Log(title = "书签标签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sqBookmarkTagService.deleteSqBookmarkTagByIds(ids));
    }
}
