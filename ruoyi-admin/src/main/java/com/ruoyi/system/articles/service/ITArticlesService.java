package com.ruoyi.system.articles.service;

import java.util.List;
import com.ruoyi.system.articles.domain.TArticles;

/**
 * 文章Service接口
 * 
 * @author ruoyi
 * @date 2024-04-29
 */
public interface ITArticlesService 
{
    /**
     * 查询文章
     * 
     * @param id 文章主键
     * @return 文章
     */
    public TArticles selectTArticlesById(Long id);

    /**
     * 查询文章列表
     * 
     * @param tArticles 文章
     * @return 文章集合
     */
    public List<TArticles> selectTArticlesList(TArticles tArticles);

    /**
     * 新增文章
     * 
     * @param tArticles 文章
     * @return 结果
     */
    public int insertTArticles(TArticles tArticles);

    /**
     * 修改文章
     * 
     * @param tArticles 文章
     * @return 结果
     */
    public int updateTArticles(TArticles tArticles);

    /**
     * 批量删除文章
     * 
     * @param ids 需要删除的文章主键集合
     * @return 结果
     */
    public int deleteTArticlesByIds(Long[] ids);

    /**
     * 删除文章信息
     * 
     * @param id 文章主键
     * @return 结果
     */
    public int deleteTArticlesById(Long id);
}
