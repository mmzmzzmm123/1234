package com.ruoyi.project.bysite.mapper;

import java.util.List;

import com.ruoyi.project.bysite.domain.ByNews;

/**
 * 新闻中心Mapper接口
 *
 * @author tsbz
 * @date 2020-06-28
 */
public interface ByNewsMapper {
    /**
     * 查询新闻中心
     *
     * @param id 新闻中心ID
     * @return 新闻中心
     */
    public ByNews selectByNewsById(Long id);

    /**
     * 查询新闻中心列表
     *
     * @param byNews 新闻中心
     * @return 新闻中心集合
     */
    public List<ByNews> selectByNewsList(ByNews byNews);

    /**
     * 新增新闻中心
     *
     * @param byNews 新闻中心
     * @return 结果
     */
    public int insertByNews(ByNews byNews);

    /**
     * 修改新闻中心
     *
     * @param byNews 新闻中心
     * @return 结果
     */
    public int updateByNews(ByNews byNews);

    /**
     * 删除新闻中心
     *
     * @param id 新闻中心ID
     * @return 结果
     */
    public int deleteByNewsById(Long id);

    /**
     * 批量删除新闻中心
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByNewsByIds(Long[] ids);
}
