package com.ruoyi.web.controller.bk;


import com.onethinker.bk.domain.Article;
import com.onethinker.bk.service.IArticleService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.onethinker.bk.vo.BaseRequestVO;
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
        List<Article> articles =  articleService.listArticle(baseRequestVO);
        return getDataTable(articles);
    }

    /**
     * 查询分类文章List
     */
    @GetMapping("/listSortArticle")
    public AjaxResult listSortArticle() {
        Map<Integer, List<Article>> resultMap =  articleService.listSortArticle();
        return AjaxResult.success(resultMap);
    }
}

