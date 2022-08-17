package com.ruoyi.ibaiqi.wxUser.controller;

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
import com.ruoyi.ibaiqi.wxUser.domain.IbWxUser;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户Controller
 * 
 * @author zhangxuDev
 * @date 2022-08-17
 */
@RestController
@RequestMapping("/wxUser/wxUser")
public class IbWxUserController extends BaseController
{
    @Autowired
    private IIbWxUserService ibWxUserService;

    /**
     * 查询微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('wxUser:wxUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(IbWxUser ibWxUser)
    {
        startPage();
        List<IbWxUser> list = ibWxUserService.selectIbWxUserList(ibWxUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('wxUser:wxUser:export')")
    @Log(title = "微信用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IbWxUser ibWxUser)
    {
        List<IbWxUser> list = ibWxUserService.selectIbWxUserList(ibWxUser);
        ExcelUtil<IbWxUser> util = new ExcelUtil<IbWxUser>(IbWxUser.class);
        util.exportExcel(response, list, "微信用户数据");
    }

    /**
     * 获取微信用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('wxUser:wxUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ibWxUserService.selectIbWxUserById(id));
    }

    /**
     * 新增微信用户
     */
    @PreAuthorize("@ss.hasPermi('wxUser:wxUser:add')")
    @Log(title = "微信用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IbWxUser ibWxUser)
    {
        return toAjax(ibWxUserService.insertIbWxUser(ibWxUser));
    }

    /**
     * 修改微信用户
     */
    @PreAuthorize("@ss.hasPermi('wxUser:wxUser:edit')")
    @Log(title = "微信用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IbWxUser ibWxUser)
    {
        return toAjax(ibWxUserService.updateIbWxUser(ibWxUser));
    }

    /**
     * 删除微信用户
     */
    @PreAuthorize("@ss.hasPermi('wxUser:wxUser:remove')")
    @Log(title = "微信用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ibWxUserService.deleteIbWxUserByIds(ids));
    }
}
