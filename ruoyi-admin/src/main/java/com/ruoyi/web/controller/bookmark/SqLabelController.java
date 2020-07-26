package com.ruoyi.web.controller.bookmark;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.sq.domain.SqLabel;
import com.ruoyi.sq.service.ISqLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书签_标签Controller
 * 
 * @author wanghao
 * @date 2020-07-26
 */
@RestController
@RequestMapping("/bookmark/label")
public class SqLabelController extends BaseController
{
    @Autowired
    private ISqLabelService sqLabelService;

    /**
     * 查询书签_标签列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:label:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqLabel sqLabel)
    {
        startPage();
        List<SqLabel> list = sqLabelService.selectSqLabelList(sqLabel);
        return getDataTable(list);
    }

    /**
     * 导出书签_标签列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:label:export')")
    @Log(title = "书签_标签", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqLabel sqLabel)
    {
        List<SqLabel> list = sqLabelService.selectSqLabelList(sqLabel);
        ExcelUtil<SqLabel> util = new ExcelUtil<SqLabel>(SqLabel.class);
        return util.exportExcel(list, "label");
    }

    /**
     * 获取书签_标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookmark:label:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sqLabelService.selectSqLabelById(id));
    }

    /**
     * 新增书签_标签
     */
    @PreAuthorize("@ss.hasPermi('bookmark:label:add')")
    @Log(title = "书签_标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqLabel sqLabel)
    {
        return toAjax(sqLabelService.insertSqLabel(sqLabel));
    }

    /**
     * 修改书签_标签
     */
    @PreAuthorize("@ss.hasPermi('bookmark:label:edit')")
    @Log(title = "书签_标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqLabel sqLabel)
    {
        return toAjax(sqLabelService.updateSqLabel(sqLabel));
    }

    /**
     * 删除书签_标签
     */
    @PreAuthorize("@ss.hasPermi('bookmark:label:remove')")
    @Log(title = "书签_标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sqLabelService.deleteSqLabelByIds(ids));
    }
}
