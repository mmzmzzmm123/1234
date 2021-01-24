package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisUtil;
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
import com.ruoyi.system.domain.SysUserFollow;
import com.ruoyi.system.service.ISysUserFollowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 我的关注Controller
 *
 * @author ruoyi
 * @date 2021-01-24
 */
@RestController
@RequestMapping("/system/follow")
public class SysUserFollowController extends BaseController
{
    @Autowired
    private ISysUserFollowService sysUserFollowService;
    @Autowired
    private RedisUtil redisUtil;



    /**
     * 查看我的关注列表
     */
    @GetMapping("/listFollwUser")
    public TableDataInfo listFollwUser()
    {
        startPage();
        List<Map<String,Object>> list = sysUserFollowService.listFollwUser(getAuthUser().getUserId());
        return getDataTable(list);
    }


    /**
     * 查看我的粉丝列表
     */
    @GetMapping("/listFansUser")
    public TableDataInfo listFansUser()
    {
        startPage();
        List<Map<String,Object>> list = sysUserFollowService.listFansUser(getAuthUser().getUserId());
        return getDataTable(list);
    }







































    /**
     * 查询我的关注列表
     */
    @PreAuthorize("@ss.hasPermi('system:follow:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserFollow sysUserFollow)
    {
        startPage();
        List<SysUserFollow> list = sysUserFollowService.selectSysUserFollowList(sysUserFollow);
        return getDataTable(list);
    }

    /**
     * 导出我的关注列表
     */
    @PreAuthorize("@ss.hasPermi('system:follow:export')")
    @Log(title = "我的关注", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysUserFollow sysUserFollow)
    {
        List<SysUserFollow> list = sysUserFollowService.selectSysUserFollowList(sysUserFollow);
        ExcelUtil<SysUserFollow> util = new ExcelUtil<SysUserFollow>(SysUserFollow.class);
        return util.exportExcel(list, "follow");
    }

    /**
     * 获取我的关注详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:follow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysUserFollowService.selectSysUserFollowById(id));
    }

    /**
     * 新增我的关注
     */
    @PreAuthorize("@ss.hasPermi('system:follow:add')")
    @Log(title = "我的关注", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserFollow sysUserFollow)
    {
        return toAjax(sysUserFollowService.insertSysUserFollow(sysUserFollow));
    }

    /**
     * 修改我的关注
     */
    @PreAuthorize("@ss.hasPermi('system:follow:edit')")
    @Log(title = "我的关注", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserFollow sysUserFollow)
    {
        return toAjax(sysUserFollowService.updateSysUserFollow(sysUserFollow));
    }

    /**
     * 删除我的关注
     */
    @PreAuthorize("@ss.hasPermi('system:follow:remove')")
    @Log(title = "我的关注", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysUserFollowService.deleteSysUserFollowByIds(ids));
    }
}
