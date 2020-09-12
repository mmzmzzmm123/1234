package com.ruoyi.web.controller.note;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
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
import com.ruoyi.note.domain.NmNote;
import com.ruoyi.note.service.INmNoteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 便签管理Controller
 *
 * @author wang
 * @date 2020-09-12
 */
@RestController
@RequestMapping("/note/note")
public class NmNoteController extends BaseController
{
    @Autowired
    private INmNoteService nmNoteService;


    /**
     * 查看栏目下 用户的便签
     */
    @PreAuthorize("@ss.hasPermi('note:note:list')")
    @GetMapping("/selectBymenuNote")
    public TableDataInfo selectBymenuNote(NmNote nmNote)
    {
        SysUser sysUser=getAuthUser();
        nmNote.setUserId(sysUser.getUserId());
        startPage();
        List<NmNote> list = nmNoteService.selectNmNoteList(nmNote);
        return getDataTable(list);
    }





    /**
     * 查询便签管理列表
     */
    @PreAuthorize("@ss.hasPermi('note:note:list')")
    @GetMapping("/list")
    public TableDataInfo list(NmNote nmNote)
    {
        startPage();
        List<NmNote> list = nmNoteService.selectNmNoteList(nmNote);
        return getDataTable(list);
    }

    /**
     * 导出便签管理列表
     */
    @PreAuthorize("@ss.hasPermi('note:note:export')")
    @Log(title = "便签管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NmNote nmNote)
    {
        List<NmNote> list = nmNoteService.selectNmNoteList(nmNote);
        ExcelUtil<NmNote> util = new ExcelUtil<NmNote>(NmNote.class);
        return util.exportExcel(list, "note");
    }

    /**
     * 获取便签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('note:note:query')")
    @GetMapping(value = "/{noteId}")
    public AjaxResult getInfo(@PathVariable("noteId") Long noteId)
    {
        return AjaxResult.success(nmNoteService.selectNmNoteById(noteId));
    }

    /**
     * 新增便签管理
     */
    @PreAuthorize("@ss.hasPermi('note:note:add')")
    @Log(title = "便签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NmNote nmNote)
    {
        return toAjax(nmNoteService.insertNmNote(nmNote));
    }

    /**
     * 修改便签管理
     */
    @PreAuthorize("@ss.hasPermi('note:note:edit')")
    @Log(title = "便签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NmNote nmNote)
    {
        return toAjax(nmNoteService.updateNmNote(nmNote));
    }

    /**
     * 删除便签管理
     */
    @PreAuthorize("@ss.hasPermi('note:note:remove')")
    @Log(title = "便签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noteIds}")
    public AjaxResult remove(@PathVariable Long[] noteIds)
    {
        return toAjax(nmNoteService.deleteNmNoteByIds(noteIds));
    }
}
