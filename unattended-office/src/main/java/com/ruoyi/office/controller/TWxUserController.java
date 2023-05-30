package com.ruoyi.office.controller;

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
import com.ruoyi.office.domain.TWxUser;
import com.ruoyi.office.service.ITWxUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户信息 t_wx_userController
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/office/wxuser")
public class TWxUserController extends BaseController
{
    @Autowired
    private ITWxUserService tWxUserService;

    /**
     * 查询微信用户信息 t_wx_user列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuser:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxUser tWxUser)
    {
        startPage();
        List<TWxUser> list = tWxUserService.selectTWxUserList(tWxUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户信息 t_wx_user列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuser:export')")
    @Log(title = "微信用户信息 t_wx_user", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxUser tWxUser)
    {
        List<TWxUser> list = tWxUserService.selectTWxUserList(tWxUser);
        ExcelUtil<TWxUser> util = new ExcelUtil<TWxUser>(TWxUser.class);
        util.exportExcel(response, list, "微信用户信息 t_wx_user数据");
    }

    /**
     * 获取微信用户信息 t_wx_user详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:wxuser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tWxUserService.selectTWxUserById(id));
    }

    /**
     * 新增微信用户信息 t_wx_user
     */
    @PreAuthorize("@ss.hasPermi('office:wxuser:add')")
    @Log(title = "微信用户信息 t_wx_user", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxUser tWxUser)
    {
        return toAjax(tWxUserService.insertTWxUser(tWxUser));
    }

    /**
     * 修改微信用户信息 t_wx_user
     */
    @PreAuthorize("@ss.hasPermi('office:wxuser:edit')")
    @Log(title = "微信用户信息 t_wx_user", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxUser tWxUser)
    {
        return toAjax(tWxUserService.updateTWxUser(tWxUser));
    }

    /**
     * 删除微信用户信息 t_wx_user
     */
    @PreAuthorize("@ss.hasPermi('office:wxuser:remove')")
    @Log(title = "微信用户信息 t_wx_user", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tWxUserService.deleteTWxUserByIds(ids));
    }
}
