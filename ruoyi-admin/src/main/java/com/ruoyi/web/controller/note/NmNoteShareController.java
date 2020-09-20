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
import com.ruoyi.note.domain.NmNoteShare;
import com.ruoyi.note.service.INmNoteShareService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * share书签分享Controller
 *
 * @author ruoyi
 * @date 2020-09-20
 */
@RestController
@RequestMapping("/note/share")
public class NmNoteShareController extends BaseController
{
    @Autowired
    private INmNoteShareService nmNoteShareService;

    /**
     * 查询share书签分享列表
     */
    @PreAuthorize("@ss.hasPermi('note:share:list')")
    @GetMapping("/list")
    public TableDataInfo list(NmNoteShare nmNoteShare)
    {
        startPage();
        List<NmNoteShare> list = nmNoteShareService.selectNmNoteShareList(nmNoteShare);
        return getDataTable(list);
    }

    /**
     * 导出share书签分享列表
     */
    @PreAuthorize("@ss.hasPermi('note:share:export')")
    @Log(title = "share书签分享", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NmNoteShare nmNoteShare)
    {
        List<NmNoteShare> list = nmNoteShareService.selectNmNoteShareList(nmNoteShare);
        ExcelUtil<NmNoteShare> util = new ExcelUtil<NmNoteShare>(NmNoteShare.class);
        return util.exportExcel(list, "share");
    }

    /**
     * 获取share书签分享详细信息
     */
    @PreAuthorize("@ss.hasPermi('note:share:query')")
    @GetMapping(value = "/{noteId}")
    public AjaxResult getInfo(@PathVariable("noteId") Long noteId)
    {
        return AjaxResult.success(nmNoteShareService.selectNmNoteShareById(noteId));
    }

    /**
     * 新增share书签分享
     */
    @PreAuthorize("@ss.hasPermi('note:share:add')")
    @Log(title = "share书签分享", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NmNoteShare nmNoteShare)
    {
        return toAjax(nmNoteShareService.insertNmNoteShare(nmNoteShare));
    }

    /**
     * 修改share书签分享
     */
    @PreAuthorize("@ss.hasPermi('note:share:edit')")
    @Log(title = "share书签分享", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NmNoteShare nmNoteShare)
    {
        return toAjax(nmNoteShareService.updateNmNoteShare(nmNoteShare));
    }

    /**
     * 删除share书签分享
     */
    @PreAuthorize("@ss.hasPermi('note:share:remove')")
    @Log(title = "share书签分享", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noteIds}")
    public AjaxResult remove(@PathVariable Long[] noteIds)
    {
        return toAjax(nmNoteShareService.deleteNmNoteShareByIds(noteIds));
    }
}
