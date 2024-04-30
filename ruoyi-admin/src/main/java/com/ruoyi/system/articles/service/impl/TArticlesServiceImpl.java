package com.ruoyi.system.articles.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.articles.mapper.TArticlesMapper;
import com.ruoyi.system.articles.domain.TArticles;
import com.ruoyi.system.articles.service.ITArticlesService;

/**
 * 文章Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-29
 */
@Service
public class TArticlesServiceImpl implements ITArticlesService 
{
    @Autowired
    private TArticlesMapper tArticlesMapper;

    /**
     * 查询文章
     * 
     * @param id 文章主键
     * @return 文章
     */
    @Override
    public TArticles selectTArticlesById(Long id)
    {
        return tArticlesMapper.selectTArticlesById(id);
    }

    /**
     * 查询文章列表
     * 
     * @param tArticles 文章
     * @return 文章
     */
    @Override
    public List<TArticles> selectTArticlesList(TArticles tArticles)
    {
        return tArticlesMapper.selectTArticlesList(tArticles);
    }

    /**
     * 新增文章
     * 
     * @param tArticles 文章
     * @return 结果
     */
    @Override
    public int insertTArticles(TArticles tArticles)
    {
        tArticles.setCreateTime(DateUtils.getNowDate());
        return tArticlesMapper.insertTArticles(tArticles);
    }

    /**
     * 修改文章
     * 
     * @param tArticles 文章
     * @return 结果
     */
    @Override
    public int updateTArticles(TArticles tArticles)
    {
        tArticles.setUpdateTime(DateUtils.getNowDate());
        return tArticlesMapper.updateTArticles(tArticles);
    }

    /**
     * 批量删除文章
     * 
     * @param ids 需要删除的文章主键
     * @return 结果
     */
    @Override
    public int deleteTArticlesByIds(Long[] ids)
    {
        return tArticlesMapper.deleteTArticlesByIds(ids);
    }

    /**
     * 删除文章信息
     * 
     * @param id 文章主键
     * @return 结果
     */
    @Override
    public int deleteTArticlesById(Long id)
    {
        return tArticlesMapper.deleteTArticlesById(id);
    }
}
