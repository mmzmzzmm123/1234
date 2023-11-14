package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.TagInfo;
import com.ruoyi.system.service.ITagInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * tag信息Controller
 * 
 * @author derek
 * @date 2023-11-14
 */
@RestController
@RequestMapping("/system/taginfo")
public class TagInfoController extends BaseController
{
    @Autowired
    private ITagInfoService tagInfoService;

    /**
     * 查询tag信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(TagInfo tagInfo)
    {
        startPage();
        List<TagInfo> list = tagInfoService.selectTagInfoList(tagInfo);
        return getDataTable(list);
    }

    /**
     * 导出tag信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "tag信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TagInfo tagInfo)
    {
        List<TagInfo> list = tagInfoService.selectTagInfoList(tagInfo);
        ExcelUtil<TagInfo> util = new ExcelUtil<TagInfo>(TagInfo.class);
        util.exportExcel(response, list, "tag信息数据");
    }

    /**
     * 获取tag信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{tagInfoId}")
    public AjaxResult getInfo(@PathVariable("tagInfoId") Long tagInfoId)
    {
        return success(tagInfoService.selectTagInfoByTagInfoId(tagInfoId));
    }

    /**
     * 新增tag信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "tag信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TagInfo tagInfo)
    {
        return toAjax(tagInfoService.insertTagInfo(tagInfo));
    }

    /**
     * 修改tag信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "tag信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TagInfo tagInfo)
    {
        return toAjax(tagInfoService.updateTagInfo(tagInfo));
    }

    /**
     * 删除tag信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "tag信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tagInfoIds}")
    public AjaxResult remove(@PathVariable Long[] tagInfoIds)
    {
        return toAjax(tagInfoService.deleteTagInfoByTagInfoIds(tagInfoIds));
    }
}
