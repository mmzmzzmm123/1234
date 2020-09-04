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
import com.ruoyi.bookmark.domain.SqUserTag;
import com.ruoyi.bookmark.service.ISqUserTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标签管理Controller
 *
 * @author wang
 * @date 2020-09-04
 */
@RestController
@RequestMapping("/system/usertag")
public class SqUserTagController extends BaseController
{
    @Autowired
    private ISqUserTagService sqUserTagService;

    /**
     * 查询标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqUserTag sqUserTag)
    {
        startPage();
        List<SqUserTag> list = sqUserTagService.selectSqUserTagList(sqUserTag);
        return getDataTable(list);
    }

    /**
     * 导出标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:export')")
    @Log(title = "标签管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqUserTag sqUserTag)
    {
        List<SqUserTag> list = sqUserTagService.selectSqUserTagList(sqUserTag);
        ExcelUtil<SqUserTag> util = new ExcelUtil<SqUserTag>(SqUserTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 获取标签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sqUserTagService.selectSqUserTagById(id));
    }

    /**
     * 新增标签管理
     */
    @PreAuthorize("@ss.hasPermi('system:tag:add')")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqUserTag sqUserTag)
    {
        return toAjax(sqUserTagService.insertSqUserTag(sqUserTag));
    }

    /**
     * 修改标签管理
     */
    @PreAuthorize("@ss.hasPermi('system:tag:edit')")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqUserTag sqUserTag)
    {
        return toAjax(sqUserTagService.updateSqUserTag(sqUserTag));
    }

    /**
     * 删除标签管理
     */
    @PreAuthorize("@ss.hasPermi('system:tag:remove')")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sqUserTagService.deleteSqUserTagByIds(ids));
    }
}
