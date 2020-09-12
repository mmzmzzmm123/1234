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
import com.ruoyi.note.domain.NmNoteTag;
import com.ruoyi.note.service.INmNoteTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 便签标签Controller
 *
 * @author wang
 * @date 2020-09-12
 */
@RestController
@RequestMapping("/note/tag")
public class NmNoteTagController extends BaseController
{
    @Autowired
    private INmNoteTagService nmNoteTagService;

    /**
     * 查询便签标签列表
     */
    @PreAuthorize("@ss.hasPermi('note:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(NmNoteTag nmNoteTag)
    {
        startPage();
        List<NmNoteTag> list = nmNoteTagService.selectNmNoteTagList(nmNoteTag);
        return getDataTable(list);
    }

    /**
     * 导出便签标签列表
     */
    @PreAuthorize("@ss.hasPermi('note:tag:export')")
    @Log(title = "便签标签", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NmNoteTag nmNoteTag)
    {
        List<NmNoteTag> list = nmNoteTagService.selectNmNoteTagList(nmNoteTag);
        ExcelUtil<NmNoteTag> util = new ExcelUtil<NmNoteTag>(NmNoteTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 获取便签标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('note:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(nmNoteTagService.selectNmNoteTagById(id));
    }

    /**
     * 新增便签标签
     */
    @PreAuthorize("@ss.hasPermi('note:tag:add')")
    @Log(title = "便签标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NmNoteTag nmNoteTag)
    {
        return toAjax(nmNoteTagService.insertNmNoteTag(nmNoteTag));
    }

    /**
     * 修改便签标签
     */
    @PreAuthorize("@ss.hasPermi('note:tag:edit')")
    @Log(title = "便签标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NmNoteTag nmNoteTag)
    {
        return toAjax(nmNoteTagService.updateNmNoteTag(nmNoteTag));
    }

    /**
     * 删除便签标签
     */
    @PreAuthorize("@ss.hasPermi('note:tag:remove')")
    @Log(title = "便签标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(nmNoteTagService.deleteNmNoteTagByIds(ids));
    }
}
