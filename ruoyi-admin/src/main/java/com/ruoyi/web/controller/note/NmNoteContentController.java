package com.ruoyi.web.controller.note;

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
import com.ruoyi.note.domain.NmNoteContent;
import com.ruoyi.note.service.INmNoteContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文章内容Controller
 *
 * @author wang
 * @date 2020-09-12
 */
@RestController
@RequestMapping("/note/content")
public class NmNoteContentController extends BaseController
{
    @Autowired
    private INmNoteContentService nmNoteContentService;

    /**
     * 查询文章内容列表
     */
    @PreAuthorize("@ss.hasPermi('note:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(NmNoteContent nmNoteContent)
    {
        startPage();
        List<NmNoteContent> list = nmNoteContentService.selectNmNoteContentList(nmNoteContent);
        return getDataTable(list);
    }

    /**
     * 导出文章内容列表
     */
    @PreAuthorize("@ss.hasPermi('note:content:export')")
    @Log(title = "文章内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NmNoteContent nmNoteContent)
    {
        List<NmNoteContent> list = nmNoteContentService.selectNmNoteContentList(nmNoteContent);
        ExcelUtil<NmNoteContent> util = new ExcelUtil<NmNoteContent>(NmNoteContent.class);
        return util.exportExcel(list, "content");
    }

    /**
     * 获取文章内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('note:content:query')")
    @GetMapping(value = "/{noteId}")
    public AjaxResult getInfo(@PathVariable("noteId") Long noteId)
    {
        return AjaxResult.success(nmNoteContentService.selectNmNoteContentById(noteId));
    }

    /**
     * 新增文章内容
     */
    @PreAuthorize("@ss.hasPermi('note:content:add')")
    @Log(title = "文章内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NmNoteContent nmNoteContent)
    {
        return toAjax(nmNoteContentService.insertNmNoteContent(nmNoteContent));
    }

    /**
     * 修改文章内容
     */
    @PreAuthorize("@ss.hasPermi('note:content:edit')")
    @Log(title = "文章内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NmNoteContent nmNoteContent)
    {
        return toAjax(nmNoteContentService.updateNmNoteContent(nmNoteContent));
    }

    /**
     * 删除文章内容
     */
    @PreAuthorize("@ss.hasPermi('note:content:remove')")
    @Log(title = "文章内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noteIds}")
    public AjaxResult remove(@PathVariable Long[] noteIds)
    {
        return toAjax(nmNoteContentService.deleteNmNoteContentByIds(noteIds));
    }
}
