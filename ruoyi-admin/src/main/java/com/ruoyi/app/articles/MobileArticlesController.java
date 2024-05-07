package com.ruoyi.app.articles;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.app.articles.vo.ArticleVO;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.articles.domain.TArticles;
import com.ruoyi.system.articles.service.ITArticlesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Api("移动端帖子接口")
@RestController
@RequestMapping("/app/articles")
public class MobileArticlesController extends BaseController {
    @Resource
    private ITArticlesService articlesService;

    @ApiOperation(value = "帖子分页查询接口",notes = "帖子分页查询接口")
    @GetMapping("/hotList")
    public TableDataInfo queryHotList() {
        startPage();
        List<TArticles> articles = articlesService.selectTArticlesList(null);
        TableDataInfo tableDataInfo = getDataTable(articles);
        tableDataInfo.setRows(articles);
        return tableDataInfo;
    }

    @ApiOperation(value = "发帖接口",notes = "发帖接口")
    @PostMapping("/addArticle")
    @RepeatSubmit
    public R<Integer> addArticle(ArticleVO articleVO) {
        if (StringUtils.isAnyBlank(articleVO.getTitle(),articleVO.getContent())|| Objects.isNull(articleVO.getUserId())){
            return R.fail("保存失败，参数缺失");
        }
        TArticles tArticles = new TArticles();
        BeanUtil.copyProperties(articleVO,tArticles);
        return R.ok(articlesService.insertTArticles(tArticles));
    }

}
