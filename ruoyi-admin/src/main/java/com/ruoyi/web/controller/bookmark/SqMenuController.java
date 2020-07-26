package com.ruoyi.web.controller.bookmark;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.sq.domain.SqMenu;
import com.ruoyi.sq.service.ISqMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书签_菜单Controller
 * 
 * @author wanghao
 * @date 2020-07-26
 */
@RestController
@RequestMapping("/bookmark/menu")
public class SqMenuController extends BaseController
{
    @Autowired
    private ISqMenuService sqMenuService;

    /**
     * 查询书签_菜单列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqMenu sqMenu)
    {
        startPage();
        List<SqMenu> list = sqMenuService.selectSqMenuList(sqMenu);
        return getDataTable(list);
    }

    /**
     * 导出书签_菜单列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:export')")
    @Log(title = "书签_菜单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqMenu sqMenu)
    {
        List<SqMenu> list = sqMenuService.selectSqMenuList(sqMenu);
        ExcelUtil<SqMenu> util = new ExcelUtil<SqMenu>(SqMenu.class);
        return util.exportExcel(list, "menu");
    }

    /**
     * 获取书签_菜单详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable("menuId") Long menuId)
    {
        return AjaxResult.success(sqMenuService.selectSqMenuById(menuId));
    }

    /**
     * 新增书签_菜单
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:add')")
    @Log(title = "书签_菜单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqMenu sqMenu)
    {
        return toAjax(sqMenuService.insertSqMenu(sqMenu));
    }

    /**
     * 修改书签_菜单
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:edit')")
    @Log(title = "书签_菜单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqMenu sqMenu)
    {
        return toAjax(sqMenuService.updateSqMenu(sqMenu));
    }

    /**
     * 删除书签_菜单
     */
    @PreAuthorize("@ss.hasPermi('bookmark:menu:remove')")
    @Log(title = "书签_菜单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuIds}")
    public AjaxResult remove(@PathVariable Long[] menuIds)
    {
        return toAjax(sqMenuService.deleteSqMenuByIds(menuIds));
    }
}
