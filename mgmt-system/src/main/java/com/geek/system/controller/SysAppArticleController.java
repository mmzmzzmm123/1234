package com.geek.system.controller;

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
import com.geek.common.annotation.Log;
import com.geek.common.core.controller.BaseController;
import com.geek.common.core.domain.AjaxResult;
import com.geek.common.enums.BusinessType;
import com.geek.system.domain.SysAppArticle;
import com.geek.system.service.ISysAppArticleService;
import com.geek.common.utils.poi.ExcelUtil;
import com.geek.common.core.page.TableDataInfo;

/**
 * app内容管理Controller
 * 
 * @author xuek
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/system/article")
public class SysAppArticleController extends BaseController{

    @Autowired
    private ISysAppArticleService sysAppArticleService;

    /**
     * 查询app内容管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:article:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAppArticle sysAppArticle){

        startPage();
        List<SysAppArticle> list = sysAppArticleService.selectSysAppArticleList(sysAppArticle);
        return getDataTable(list);
    }

    /**
     * 导出app内容管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:article:export')")
    @Log(title = "app内容管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAppArticle sysAppArticle){

        List<SysAppArticle> list = sysAppArticleService.selectSysAppArticleList(sysAppArticle);
        ExcelUtil<SysAppArticle> util = new ExcelUtil<SysAppArticle>(SysAppArticle.class);
        util.exportExcel(response, list, "app内容管理数据");
    }

    /**
     * 获取app内容管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:article:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){

        return success(sysAppArticleService.selectSysAppArticleById(id));
    }

    /**
     * 新增app内容管理
     */
    @PreAuthorize("@ss.hasPermi('system:article:add')")
    @Log(title = "app内容管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAppArticle sysAppArticle){

        return toAjax(sysAppArticleService.insertSysAppArticle(sysAppArticle));
    }

    /**
     * 修改app内容管理
     */
    @PreAuthorize("@ss.hasPermi('system:article:edit')")
    @Log(title = "app内容管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAppArticle sysAppArticle){

        return toAjax(sysAppArticleService.updateSysAppArticle(sysAppArticle));
    }

    /**
     * 删除app内容管理
     */
    @PreAuthorize("@ss.hasPermi('system:article:remove')")
    @Log(title = "app内容管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){

        return toAjax(sysAppArticleService.deleteSysAppArticleByIds(ids));
    }
}
