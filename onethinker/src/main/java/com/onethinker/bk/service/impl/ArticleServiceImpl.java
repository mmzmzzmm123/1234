package com.onethinker.bk.service.impl;

import java.util.List;
import java.util.Map;

import com.onethinker.bk.vo.BaseRequestVO;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bk.mapper.ArticleMapper;
import com.onethinker.bk.domain.Article;
import com.onethinker.bk.service.IArticleService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 文章Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 查询文章
     *
     * @param id 文章主键
     * @return 文章
     */
    @Override
    public Article selectArticleById(Long id) {
        return articleMapper.selectArticleById(id);
    }

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章
     */
    @Override
    public List<Article> selectArticleList(Article article) {
        return articleMapper.selectArticleList(article);
    }

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 结果
     */
    @Override
    public int insertArticle(Article article) {
                article.setCreateTime(DateUtils.getNowDate());
            return articleMapper.insertArticle(article);
    }

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    @Override
    public int updateArticle(Article article) {
                article.setUpdateTime(DateUtils.getNowDate());
        return articleMapper.updateArticle(article);
    }

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的文章主键
     * @return 结果
     */
    @Override
    public int deleteArticleByIds(Long[] ids) {
        return articleMapper.deleteArticleByIds(ids);
    }

    /**
     * 删除文章信息
     *
     * @param id 文章主键
     * @return 结果
     */
    @Override
    public int deleteArticleById(Long id) {
        return articleMapper.deleteArticleById(id);
    }

    @Override
    public List<Article> listArticle(BaseRequestVO baseRequestVO) {
        return null;
    }

    @Override
    public Map<Integer, List<Article>> listSortArticle() {
        return null;
    }
}
