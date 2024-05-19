package com.onethinker.bk.service;

import com.onethinker.bk.domain.Article;
import com.onethinker.bk.vo.ArticleVO;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.common.core.domain.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 文章Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IArticleService {

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章集合
     */
    List<Article> selectArticleList(Article article);

    /**
     * 新增文章
     *
     * @param articleVO 文章
     * @return 结果
     */
    int insertArticle(ArticleVO articleVO);

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    int updateArticle(ArticleVO article);

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的文章主键集合
     * @return 结果
     */
    int deleteArticleByIds(Long[] ids);

    /**
     * 删除文章信息
     *
     * @param id 文章主键
     * @return 结果
     */
    int deleteArticleById(Long id);

    /**
     * 查询文章List
     *
     * @param baseRequestVO
     * @return
     */
    List<Article> listArticle(BaseRequestVO baseRequestVO);

    /**
     * 查询分类文章List
     *
     * @return
     */
    Map<Integer, List<Article>> listSortArticle();

    /**
     * 获取文章信息
     * @param id
     * @param password
     * @return
     */
    ArticleVO getArticleById(Long id, String password);

    /**
     * 根据id查数
     * @param source
     * @return
     */
    Article selectArticleById(Long source);
}
