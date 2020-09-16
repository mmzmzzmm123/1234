package com.ruoyi.web.controller.yunbookmark;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/usertag/tag")
public class SqUserTagController extends BaseController
{
    @Autowired
    private ISqUserTagService sqUserTagService;

    /**
     * 用戶获取标签
     */

    @GetMapping(value = "/selectTagByUserID")
    public AjaxResult selectSqUserTagByUserId()
    {
        SysUser sysUser=getAuthUser();
        startPage();
        List<Map<String,Object>> map =  sqUserTagService.selectSqUserTagByUserId(sysUser.getUserId());
        return AjaxResult.success(map);
    }

    /**
     * 用户删除标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:remove')")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/userRemoveByid/{ids}")
    public AjaxResult userRemoveByid(@PathVariable Long[] ids)
    {
        SysUser sysUser=getAuthUser();
        return toAjax(sqUserTagService.userRemoveByid(ids,sysUser.getUserId()));
    }

    /**
     * 用户新增标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:add')")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/SqUserTagAdd")
    public AjaxResult SqUserTagAdd(@RequestBody SqUserTag sqUserTag) {
        SysUser sysUser = getAuthUser();
        sqUserTag.setUserId(sysUser.getUserId());
        //检测标签是否已经存在
        int i = sqUserTagService.selectCountByName(sqUserTag.getTagName(), sysUser.getUserId());
        if (i > 0) {
            return AjaxResult.error("新增标签'" + sqUserTag.getTagName() + "'失败，该标签已存在");
        }
        return toAjax(sqUserTagService.insertSqUserTagAdd(sqUserTag));
    }

    /**
     * 用户修改标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:edit')")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/SqUserTagEdit")
    public AjaxResult SqUserTagEdit(@RequestBody SqUserTag sqUserTag) {
        SysUser sysUser = getAuthUser();
        //检测操作的是否自己的标签
        SqUserTag tag = sqUserTagService.selectSqUserTagById(sqUserTag.getId());
        if (!tag.getUserId().equals(sysUser.getUserId())) {
            return AjaxResult.error("修改失败,检测到异常操作已被系统记录!");
        }
        //检测标签是否已经存在
        int i = sqUserTagService.selectCountByName(sqUserTag.getTagName(), sysUser.getUserId());
        if (i > 0) {
            return AjaxResult.error("修改标签【" +tag.getTagName()+"】为【"+ sqUserTag.getTagName() + "】失败，标签【"+sqUserTag.getTagName()+"】已存在");
        }

        //做一次数据初始化 防止用户传入的 注入的数据
        tag.setTagName(sqUserTag.getTagName());
        tag.setIorder(sqUserTag.getIorder());
        return toAjax(sqUserTagService.updateSqUserTagEdit(tag));
    }



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
