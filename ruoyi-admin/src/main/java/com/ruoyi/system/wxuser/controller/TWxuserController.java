package com.ruoyi.system.wxuser.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.TWxuser;
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
import com.ruoyi.system.wxuser.service.ITWxuserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户Controller
 *
 * @author ruoyi
 * @date 2024-04-18
 */
@RestController
@RequestMapping("/system/wxuser")
public class TWxuserController extends BaseController
{
    @Autowired
    private ITWxuserService tWxuserService;

    /**
     * 查询微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxuser tWxuser)
    {
        startPage();
        List<TWxuser> list = tWxuserService.selectTWxuserList(tWxuser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:export')")
    @Log(title = "微信用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxuser tWxuser)
    {
        List<TWxuser> list = tWxuserService.selectTWxuserList(tWxuser);
        ExcelUtil<TWxuser> util = new ExcelUtil<TWxuser>(TWxuser.class);
        util.exportExcel(response, list, "微信用户数据");
    }

    /**
     * 获取微信用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tWxuserService.selectTWxuserById(id));
    }

    /**
     * 新增微信用户
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:add')")
    @Log(title = "微信用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxuser tWxuser)
    {
        return toAjax(tWxuserService.insertTWxuser(tWxuser));
    }

    /**
     * 修改微信用户
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:edit')")
    @Log(title = "微信用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxuser tWxuser)
    {
        return toAjax(tWxuserService.updateTWxuser(tWxuser));
    }

    /**
     * 删除微信用户
     */
    @PreAuthorize("@ss.hasPermi('system:wxuser:remove')")
    @Log(title = "微信用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tWxuserService.deleteTWxuserByIds(ids));
    }
}
