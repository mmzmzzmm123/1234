package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.QzInvite;
import com.ruoyi.system.service.IQzInviteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 邀请Controller
 * 
 * @author zlx
 * @date 2021-08-10
 */
@RestController
@RequestMapping("/system/invite")
public class QzInviteController extends BaseController
{
    @Autowired
    private IQzInviteService qzInviteService;

    /**
     * 查询邀请列表
     */
    @PreAuthorize("@ss.hasPermi('system:invite:list')")
    @GetMapping("/list")
    public TableDataInfo list(QzInvite qzInvite)
    {
        startPage();
        List<QzInvite> list = qzInviteService.selectQzInviteList(qzInvite);
        return getDataTable(list);
    }

    /**
     * 导出邀请列表
     */
    @PreAuthorize("@ss.hasPermi('system:invite:export')")
    @Log(title = "邀请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QzInvite qzInvite)
    {
        List<QzInvite> list = qzInviteService.selectQzInviteList(qzInvite);
        ExcelUtil<QzInvite> util = new ExcelUtil<QzInvite>(QzInvite.class);
        return util.exportExcel(list, "邀请数据");
    }

    /**
     * 获取邀请详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:invite:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qzInviteService.selectQzInviteById(id));
    }

    /**
     * 新增邀请
     */
    @PreAuthorize("@ss.hasPermi('system:invite:add')")
    @Log(title = "邀请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QzInvite qzInvite)
    {
        return toAjax(qzInviteService.insertQzInvite(qzInvite));
    }

    /**
     * 修改邀请
     */
    @PreAuthorize("@ss.hasPermi('system:invite:edit')")
    @Log(title = "邀请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QzInvite qzInvite)
    {
        return toAjax(qzInviteService.updateQzInvite(qzInvite));
    }

    /**
     * 删除邀请
     */
    @PreAuthorize("@ss.hasPermi('system:invite:remove')")
    @Log(title = "邀请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qzInviteService.deleteQzInviteByIds(ids));
    }
}
