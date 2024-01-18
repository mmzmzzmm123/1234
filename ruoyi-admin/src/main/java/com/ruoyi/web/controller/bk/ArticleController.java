package com.ruoyi.web.controller.bk;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onethinker.bk.domain.Article;
import com.onethinker.bk.service.IArticleService;
import com.onethinker.bk.vo.ArticleVO;
import com.onethinker.bk.vo.BaseRequestVO;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 文章表Controller
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@RestController
@RequestMapping("/bk/article")
public class ArticleController extends BaseController {

    @Autowired
    private IArticleService articleService;


    /**
     * 查询文章List
     */
    @PostMapping("/listArticle")
    public TableDataInfo listArticle(@RequestBody BaseRequestVO baseRequestVO) {
        startPage();
        List<Article> articles = articleService.listArticle(baseRequestVO);
        return getDataTable(articles);
    }

    /**
     * 查询分类文章List
     */
    @GetMapping("/listSortArticle")
    public AjaxResult listSortArticle() {
        Map<Integer, List<Article>> resultMap = articleService.listSortArticle();
        return AjaxResult.success(resultMap);
    }

    /**
     * 保存文章
     */
    @PostMapping("/saveArticle")
    public AjaxResult saveArticle(@Validated @RequestBody ArticleVO articleVO) {
        articleService.insertArticle(articleVO);
        return AjaxResult.success();
    }


    /**
     * 删除文章
     */
    @GetMapping("/deleteArticle")
    public AjaxResult deleteArticle(@RequestParam("id") Long id) {
        articleService.deleteArticleById(id);
        return AjaxResult.success();
    }


    /**
     * 更新文章
     */
    @PostMapping("/updateArticle")
    public AjaxResult updateArticle(@Validated @RequestBody ArticleVO articleVO) {
         articleService.updateArticle(articleVO);
        return AjaxResult.success();
    }
    /**
     * 查询文章
     */
    @GetMapping("/getArticleById")
    public AjaxResult getArticleById(@RequestParam("id") Long id, @RequestParam(value = "password", required = false) String password) {
       return AjaxResult.success(articleService.getArticleById(id, password));
    }
}

