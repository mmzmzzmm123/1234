package com.ruoyi.project.bysite.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataSource;
import com.ruoyi.framework.aspectj.lang.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.bysite.mapper.ByNewsMapper;
import com.ruoyi.project.bysite.domain.ByNews;
import com.ruoyi.project.bysite.service.IByNewsService;

/**
 * 新闻中心Service业务层处理
 *
 * @author tsbz
 * @date 2020-06-28
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class ByNewsServiceImpl implements IByNewsService {
    @Autowired
    private ByNewsMapper byNewsMapper;

    /**
     * 查询新闻中心
     *
     * @param id 新闻中心ID
     * @return 新闻中心
     */
    @Override
    public ByNews selectByNewsById(Long id) {
        return byNewsMapper.selectByNewsById(id);
    }

    /**
     * 查询新闻中心列表
     *
     * @param byNews 新闻中心
     * @return 新闻中心
     */
    @Override
    public List<ByNews> selectByNewsList(ByNews byNews) {
        return byNewsMapper.selectByNewsList(byNews);
    }

    /**
     * 新增新闻中心
     *
     * @param byNews 新闻中心
     * @return 结果
     */
    @Override
    public int insertByNews(ByNews byNews) {
        byNews.setCreateTime(DateUtils.getNowDate());
        return byNewsMapper.insertByNews(byNews);
    }

    /**
     * 修改新闻中心
     *
     * @param byNews 新闻中心
     * @return 结果
     */
    @Override
    public int updateByNews(ByNews byNews) {
        return byNewsMapper.updateByNews(byNews);
    }

    /**
     * 批量删除新闻中心
     *
     * @param ids 需要删除的新闻中心ID
     * @return 结果
     */
    @Override
    public int deleteByNewsByIds(Long[] ids) {
        return byNewsMapper.deleteByNewsByIds(ids);
    }

    /**
     * 删除新闻中心信息
     *
     * @param id 新闻中心ID
     * @return 结果
     */
    @Override
    public int deleteByNewsById(Long id) {
        return byNewsMapper.deleteByNewsById(id);
    }
}
