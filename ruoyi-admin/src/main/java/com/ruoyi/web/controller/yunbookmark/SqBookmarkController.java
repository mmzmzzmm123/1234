package com.ruoyi.web.controller.yunbookmark;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.BookmarkHtml.ImportHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 书签管理Controller
 *
 * @author wanghao
 * @date 2020-08-02
 */
@RestController
@RequestMapping("/bookmark/bookmark")
public class SqBookmarkController extends BaseController
{
    public final static Logger logger =  LoggerFactory.getLogger(SqBookmarkController.class);
    @Autowired
    private ISqBookmarkService sqBookmarkService;


    /**
     * 测试通用mapper
     */
    @GetMapping("/selectByID")
    public TableDataInfo selectByID( Long userID) {
        List<SqBookmark> list = sqBookmarkService.selectByID(userID);
        return getDataTable(list);
    }


    /**
     * 查询用户栏目下的书签
     * @return
     */
    @GetMapping("/selectBymenuIdUserID")
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:list')")
    public TableDataInfo selectBymenuIdUserID(Long menuId,Integer sort,String sousuo) {
        SysUser sysUser=getAuthUser();
        startPage();
        List<SqBookmark> list = sqBookmarkService.selectBymenuIdUserID(menuId,sysUser.getUserId(),sort,sousuo);
        return getDataTable(list);
    }
    /**
     * 回收站
     * @return
     */
    @GetMapping("/selectBydelete")
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:list')")
    public TableDataInfo selectBydelete() {
        SysUser sysUser=getAuthUser();
        startPage();
        List<SqBookmark> list = sqBookmarkService.selectBydelete(sysUser.getUserId());
        return getDataTable(list);
    }

    /**
     * 用户全部书签
     * @return
     */
    @GetMapping("/selectByUseridList")
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:list')")
    public TableDataInfo selectByUseridList() {
        SysUser sysUser=getAuthUser();
        startPage();
        List<SqBookmark> list = sqBookmarkService.selectByUseridList(sysUser.getUserId());
        return getDataTable(list);
    }


    /**
     * 查询书签管理列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:list')")
    @GetMapping("/list")
    public TableDataInfo list(SqBookmark sqBookmark)
    {
        startPage();
        List<SqBookmark> list = sqBookmarkService.selectSqBookmarkList(sqBookmark);
        return getDataTable(list);
    }

    /**
     * 导出书签管理列表
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:export')")
    @Log(title = "书签管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SqBookmark sqBookmark)
    {
        List<SqBookmark> list = sqBookmarkService.selectSqBookmarkList(sqBookmark);
        ExcelUtil<SqBookmark> util = new ExcelUtil<SqBookmark>(SqBookmark.class);
        return util.exportExcel(list, "bookmark");
    }

    /**
     * 获取书签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:query')")
    @GetMapping(value = "/{bookmarkId}")
    public AjaxResult getInfo(@PathVariable("bookmarkId") Long bookmarkId)
    {
        return AjaxResult.success(sqBookmarkService.selectSqBookmarkById(bookmarkId));
    }

    /**
     * 新增书签管理
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:add')")
    @Log(title = "书签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SqBookmark sqBookmark)
    {
        SysUser sysUser=getAuthUser();
        sqBookmark.setUserid(sysUser.getUserId());
        try {
            sqBookmark.setUrls(ImportHtml.Urlutils(new URL(sqBookmark.getUrl())));
        } catch (MalformedURLException e) {
            logger.info(sysUser.getUserId()+"新增书签 获取网址的 主机信息 报错"+new Date());
            e.printStackTrace();
        }
        return toAjax(sqBookmarkService.insertSqBookmark(sqBookmark));
    }

    /**
     * 修改书签管理
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:edit')")
    @Log(title = "书签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SqBookmark sqBookmark)
    {
        return toAjax(sqBookmarkService.updateSqBookmark(sqBookmark));
    }

    /**
     * 删除书签管理
     */
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:remove')")
    @Log(title = "书签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookmarkIds}")
    public AjaxResult remove(@PathVariable Long[] bookmarkIds)
    {
        return toAjax(sqBookmarkService.deleteSqBookmarkByIds(bookmarkIds));
    }
}
