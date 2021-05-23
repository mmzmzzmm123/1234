package com.ruoyi.web.controller.yunbookmark;

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
import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.service.ISqTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 书签_标签Controller
 *
 * @author ruoyi
 * @date 2020-09-04
 */
@RestController
@RequestMapping("/system/tag")
public class SqTagController extends BaseController
{
    @Autowired
    private ISqTagService sqTagService;



    /**
     * 获取所有的书签TAG 并且翻页
     */
    @GetMapping("/listByUser")
    public TableDataInfo listByUser()
    {
        startPage();
        List<SqTag> list = sqTagService.selectSqTagList(new SqTag(getAuthUser().getUserId()));
        return getDataTable(list);
    }


    /**
     * 模糊查书签TAG
     */
    @GetMapping("/listByUserLike")
    public TableDataInfo listByUserLike()
    {
        startPage();
        List<SqTag> list = sqTagService.selectSqTagList(new SqTag(getAuthUser().getUserId()));
        return getDataTable(list);
    }










    // 以下是系统管理员的操作


    /**
     * 查询书签_标签列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqTag sqTag)
    {
        startPage();
        List<SqTag> list = sqTagService.selectSqTagList(sqTag);
        return getDataTable(list);
    }

    /**
     * 导出书签_标签列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:export')")
    @Log(title = "书签_标签", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqTag sqTag)
    {
        List<SqTag> list = sqTagService.selectSqTagList(sqTag);
        ExcelUtil<SqTag> util = new ExcelUtil<SqTag>(SqTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 获取书签_标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sqTagService.selectSqTagById(id));
    }

    /**
     * 新增书签_标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:add')")
    @Log(title = "书签_标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqTag sqTag)
    {
        return toAjax(sqTagService.insertSqTag(sqTag));
    }

    /**
     * 修改书签_标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:edit')")
    @Log(title = "书签_标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqTag sqTag)
    {
        return toAjax(sqTagService.updateSqTag(sqTag));
    }

    /**
     * 删除书签_标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:remove')")
    @Log(title = "书签_标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sqTagService.deleteSqTagByIds(ids));
    }
}
