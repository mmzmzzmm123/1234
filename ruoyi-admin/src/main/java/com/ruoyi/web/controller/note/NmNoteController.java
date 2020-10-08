package com.ruoyi.web.controller.note;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.sun.org.apache.bcel.internal.generic.NEW;
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
     * 用户查看栏目下的所有便签
     */

    @GetMapping("/selectBymenuNote")
    public TableDataInfo selectBymenuNote(Long menuId)
    {
        SysUser sysUser=getAuthUser();
        NmNote nmNote= new NmNote();
        nmNote.setUserId(sysUser.getUserId());
        nmNote.setMenuId(menuId);
        startPage();
        List<NmNote> list = nmNoteService.selectNmNoteList(nmNote);
        return getDataTable(list);
    }


    /**
     * 用户新增便签
     */
    @PostMapping("/addUserNote")
    public AjaxResult userAddNote(@RequestBody NmNote nmNote)
    {
        SysUser sysUser=getAuthUser();
        nmNote.setUserId(sysUser.getUserId());
        return toAjax(nmNoteService.insertNmNote(nmNote));
    }

    /**
     *用户 获取便签详细信息 然后修改
     */

    @GetMapping(value = "/userGetNoteInfo/{noteId}")
    public AjaxResult userGetNoteInfo(@PathVariable("noteId") Long noteId)
    {
        SysUser sysUser=getAuthUser();
        return AjaxResult.success(nmNoteService.selectNmNoteuserById(noteId,sysUser.getUserId()));
    }

    /**
     * 用户修改便签
     */




    /**
     * 用户删除便签
     */










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
