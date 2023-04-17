package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.DataWechatUser;
import com.ruoyi.system.service.IDataWechatUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户信息Controller
 * 
 * @author genius
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/system/wechatUser")
public class DataWechatUserController extends BaseController
{
    @Autowired
    private IDataWechatUserService dataWechatUserService;

    /**
     * 查询微信用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:wechatUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataWechatUser dataWechatUser)
    {
        startPage();
        List<DataWechatUser> list = dataWechatUserService.selectDataWechatUserList(dataWechatUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:wechatUser:export')")
    @Log(title = "微信用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataWechatUser dataWechatUser)
    {
        List<DataWechatUser> list = dataWechatUserService.selectDataWechatUserList(dataWechatUser);
        ExcelUtil<DataWechatUser> util = new ExcelUtil<DataWechatUser>(DataWechatUser.class);
        return util.exportExcel(list, "微信用户信息数据");
    }

    /**
     * 获取微信用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wechatUser:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(dataWechatUserService.selectDataWechatUserById(userId));
    }

    /**
     * 新增微信用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:wechatUser:add')")
    @Log(title = "微信用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataWechatUser dataWechatUser)
    {
        return toAjax(dataWechatUserService.insertDataWechatUser(dataWechatUser));
    }

    /**
     * 修改微信用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:wechatUser:edit')")
    @Log(title = "微信用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataWechatUser dataWechatUser)
    {
        return toAjax(dataWechatUserService.updateDataWechatUser(dataWechatUser));
    }

    /**
     * 删除微信用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:wechatUser:remove')")
    @Log(title = "微信用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(dataWechatUserService.deleteDataWechatUserByIds(userIds));
    }
}
