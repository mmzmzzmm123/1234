package com.ruoyi.project.axsystem.controller;

import java.util.List;

import com.ruoyi.common.core.lang.UUID;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.axsystem.domain.UserBase;
import com.ruoyi.project.axsystem.service.IUserBaseService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 用户基础信息Controller
 * 
 * @author joy
 * @date 2020-05-18
 */
@RestController
@RequestMapping("/axsystem/base")
public class UserBaseController extends BaseController
{
    @Autowired
    private IUserBaseService userBaseService;

    /**
     * 查询用户基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('axsystem:base:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserBase userBase)
    {
        startPage();
        List<UserBase> list = userBaseService.selectUserBaseList(userBase);
        return getDataTable(list);
    }

    /**
     * 导出用户基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('axsystem:base:export')")
    @Log(title = "用户基础信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UserBase userBase)
    {
        List<UserBase> list = userBaseService.selectUserBaseList(userBase);
        ExcelUtil<UserBase> util = new ExcelUtil<UserBase>(UserBase.class);
        return util.exportExcel(list, "base");
    }

    /**
     * 获取用户基础信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('axsystem:base:query')")
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid)
    {
        return AjaxResult.success(userBaseService.selectUserBaseById(uid));
    }

    /**
     * 新增用户基础信息
     */
    @PreAuthorize("@ss.hasPermi('axsystem:base:add')")
    @Log(title = "用户基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserBase userBase)
    {
        return toAjax(userBaseService.insertUserBase(userBase));
    }

    /**
     * 修改用户基础信息
     */
    @PreAuthorize("@ss.hasPermi('axsystem:base:edit')")
    @Log(title = "用户基础信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserBase userBase)
    {
        return toAjax(userBaseService.updateUserBase(userBase));
    }

    /**
     * 删除用户基础信息
     */
    @PreAuthorize("@ss.hasPermi('axsystem:base:remove')")
    @Log(title = "用户基础信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable Long[] uids)
    {
        return toAjax(userBaseService.deleteUserBaseByIds(uids));
    }

    public static void main(String[] args) {
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }

}
