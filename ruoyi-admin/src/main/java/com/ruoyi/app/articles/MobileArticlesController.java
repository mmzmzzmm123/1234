package com.ruoyi.app.articles;

import com.ruoyi.app.articles.vo.ArticleVO;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.articles.domain.TArticles;
import com.ruoyi.system.articles.service.ITArticlesService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("移动端帖子接口")
@RestController
@RequestMapping("/app/articles")
public class MobileArticlesController extends BaseController {
    @Resource
    private ITArticlesService articlesService;

    @GetMapping("/hotList")
    public TableDataInfo queryHotList() {
        startPage();
        List<TArticles> articles = articlesService.selectTArticlesList(null);
        TableDataInfo tableDataInfo = getDataTable(articles);
        tableDataInfo.setRows(articles);
        return tableDataInfo;
    }

    @PostMapping("/addArticle")
    public R<Integer> addArticle(ArticleVO articleVO) {
        return R.ok();
    }

}
