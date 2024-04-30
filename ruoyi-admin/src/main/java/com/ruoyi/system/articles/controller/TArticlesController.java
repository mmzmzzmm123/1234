package com.ruoyi.system.articles.controller;

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
import com.ruoyi.system.articles.domain.TArticles;
import com.ruoyi.system.articles.service.ITArticlesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文章Controller
 * 
 * @author ruoyi
 * @date 2024-04-29
 */
@RestController
@RequestMapping("/system/articles")
public class TArticlesController extends BaseController
{
    @Autowired
    private ITArticlesService tArticlesService;

    /**
     * 查询文章列表
     */
    @PreAuthorize("@ss.hasPermi('system:articles:list')")
    @GetMapping("/list")
    public TableDataInfo list(TArticles tArticles)
    {
        startPage();
        List<TArticles> list = tArticlesService.selectTArticlesList(tArticles);
        return getDataTable(list);
    }

    /**
     * 导出文章列表
     */
    @PreAuthorize("@ss.hasPermi('system:articles:export')")
    @Log(title = "文章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TArticles tArticles)
    {
        List<TArticles> list = tArticlesService.selectTArticlesList(tArticles);
        ExcelUtil<TArticles> util = new ExcelUtil<TArticles>(TArticles.class);
        util.exportExcel(response, list, "文章数据");
    }

    /**
     * 获取文章详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:articles:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tArticlesService.selectTArticlesById(id));
    }

    /**
     * 新增文章
     */
    @PreAuthorize("@ss.hasPermi('system:articles:add')")
    @Log(title = "文章", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TArticles tArticles)
    {
        return toAjax(tArticlesService.insertTArticles(tArticles));
    }

    /**
     * 修改文章
     */
    @PreAuthorize("@ss.hasPermi('system:articles:edit')")
    @Log(title = "文章", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TArticles tArticles)
    {
        return toAjax(tArticlesService.updateTArticles(tArticles));
    }

    /**
     * 删除文章
     */
    @PreAuthorize("@ss.hasPermi('system:articles:remove')")
    @Log(title = "文章", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tArticlesService.deleteTArticlesByIds(ids));
    }
}
