package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.BySchoolNews;

/**
 * 新闻中心Service接口
 *
 * @author tsbz
 * @date 2020-12-25
 */
public interface IBySchoolNewsService
{
    /**
     * 查询新闻中心
     *
     * @param id 新闻中心ID
     * @return 新闻中心
     */
    public BySchoolNews selectBySchoolNewsById(Long id);

    /**
     * 查询新闻中心列表
     *
     * @param bySchoolNews 新闻中心
     * @return 新闻中心集合
     */
    public List<BySchoolNews> selectBySchoolNewsList(BySchoolNews bySchoolNews);

    /**
     * 查询新闻中心列表
     *
     * @param bySchoolNews 新闻中心
     * @return 新闻中心集合
     */
    public List<BySchoolNews> selectAllBySchoolNewsList(BySchoolNews bySchoolNews);

    /**
     * 新增新闻中心
     *
     * @param bySchoolNews 新闻中心
     * @return 结果
     */
    public int insertBySchoolNews(BySchoolNews bySchoolNews);

    /**
     * 修改新闻中心
     *
     * @param bySchoolNews 新闻中心
     * @return 结果
     */
    public int updateBySchoolNews(BySchoolNews bySchoolNews);

    /**
     * 批量删除新闻中心
     *
     * @param ids 需要删除的新闻中心ID
     * @return 结果
     */
    public int deleteBySchoolNewsByIds(Long[] ids);

    /**
     * 删除新闻中心信息
     *
     * @param id 新闻中心ID
     * @return 结果
     */
    public int deleteBySchoolNewsById(Long id);
}
