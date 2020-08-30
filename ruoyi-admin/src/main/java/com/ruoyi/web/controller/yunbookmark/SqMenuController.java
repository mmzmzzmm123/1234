package com.ruoyi.web.controller.yunbookmark;

import java.util.List;

import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 书签菜单Controller
 *
 * @author wanghao
 * @date 2020-08-06
 */
@RestController
@RequestMapping("/bookmark/menu")
public class SqMenuController extends BaseController
{
    @Autowired
    private ISqMenuService sqMenuService;
    @Autowired
    private ISqBookmarkService sqBookmarkService;
    /**
     * 功能描述:查询用户的 所有书签菜单
     */
    @GetMapping("/selectMenuByUserID")
    public AjaxResult selecByUserID()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) auth.getPrincipal();
        List<SqMenu> list = sqMenuService.selecByUserID(user.getUser().getUserId());
        return AjaxResult.success(list);
    }

    /**
     * 查询MenuID单个书签信息
     */
    @GetMapping("/selectByMenuId")
    public AjaxResult list(@RequestParam("menuId") Long menuId)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) auth.getPrincipal();
        Long userId=user.getUser().getUserId();

        SqMenu sqMenu=new SqMenu();
        sqMenu.setMenuId(menuId);
        sqMenu.setUserId(userId);
        List<SqMenu> list = sqMenuService.selectSqMenuList(sqMenu);
        return AjaxResult.success(list);
    }

    /**
     * 查询书签菜单列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SqMenu sqMenu)
    {
        List<SqMenu> list = sqMenuService.selectSqMenuList(sqMenu);
        return AjaxResult.success(list);
    }

    /**
     * 导出书签菜单列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:export')")
    @Log(title = "书签菜单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqMenu sqMenu)
    {
        List<SqMenu> list = sqMenuService.selectSqMenuList(sqMenu);
        ExcelUtil<SqMenu> util = new ExcelUtil<SqMenu>(SqMenu.class);
        return util.exportExcel(list, "menu");
    }

    /**
     * 获取书签菜单详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable("menuId") Long menuId)
    {
        return AjaxResult.success(sqMenuService.selectSqMenuById(menuId));
    }

    /**
     * 新增书签菜单
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:add')")
    @Log(title = "书签菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqMenu sqMenu)
    {
        return toAjax(sqMenuService.insertSqMenu(sqMenu));
    }

    /**
     * 修改书签菜单
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:edit')")
    @Log(title = "书签菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqMenu sqMenu)
    {
        return toAjax(sqMenuService.updateSqMenu(sqMenu));
    }

    /**
     * 删除书签菜单
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:remove')")
    @Log(title = "书签菜单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuIds}")
    public AjaxResult removes(@PathVariable Long[] menuIds)
    {
        return toAjax(sqMenuService.deleteSqMenuByIds(menuIds));
    }

    /**
     * 删除书签菜单
     */

    @Log(title = "书签菜单", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete/{menuId}")
    public AjaxResult remove(@PathVariable Long menuId)
    {
        SysUser sysUser=getAuthUser();
        //查询目录下是否还有目录
        SqMenu sqMenu=new SqMenu();
        sqMenu.setParentId(menuId);
        List<SqMenu> sqMenuList=sqMenuService.selectSqMenuList(sqMenu);
        if (sqMenuList==null||sqMenuList.isEmpty()){
        sqMenuService.deleteSqMenuById(menuId,sysUser.getUserId());
            //修改目录下的所有书签状态为 删除状态
            sqBookmarkService.updateSqBookmarkBymenuId(menuId);

            return toAjax(1);
        }else{
            return AjaxResult.error("删除失败,该目录下级还有目录菜单");
        }
    }

//    /**
//     * 批量更新书签的数量
//     */
//
    @RequestMapping("/bookmarkcount")
    public void bookmarkcount()
    {

//        Long[] menuIds={1L,2L,3L};
        //所有的父级目录
        Long[] menuIds=  sqMenuService.selectBymenuidParentid(4L);
        sqMenuService.updateCountAdd(menuIds,5);

        logger.info("执行完毕");
    }


}
