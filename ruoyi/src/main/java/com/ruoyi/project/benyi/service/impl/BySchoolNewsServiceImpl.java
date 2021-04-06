package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                                                    import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.BySchoolNewsMapper;
import com.ruoyi.project.benyi.domain.BySchoolNews;
import com.ruoyi.project.benyi.service.IBySchoolNewsService;

/**
 * 新闻中心Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-25
 */
@Service
public class BySchoolNewsServiceImpl implements IBySchoolNewsService
{
    @Autowired
    private BySchoolNewsMapper bySchoolNewsMapper;

    /**
     * 查询新闻中心
     *
     * @param id 新闻中心ID
     * @return 新闻中心
     */
    @Override
    public BySchoolNews selectBySchoolNewsById(Long id)
    {
        return bySchoolNewsMapper.selectBySchoolNewsById(id);
    }

    /**
     * 查询新闻中心列表
     *
     * @param bySchoolNews 新闻中心
     * @return 新闻中心
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<BySchoolNews> selectBySchoolNewsList(BySchoolNews bySchoolNews)
    {
        return bySchoolNewsMapper.selectBySchoolNewsList(bySchoolNews);
    }

    /**
     * 查询新闻中心列表
     *
     * @param bySchoolNews 新闻中心
     * @return 新闻中心
     */
    @Override
    public List<BySchoolNews> selectAllBySchoolNewsList(BySchoolNews bySchoolNews)
    {
        return bySchoolNewsMapper.selectAllBySchoolNewsList(bySchoolNews);
    }

    /**
     * 新增新闻中心
     *
     * @param bySchoolNews 新闻中心
     * @return 结果
     */
    @Override
    public int insertBySchoolNews(BySchoolNews bySchoolNews)
    {
                                                                                                                                                                                                    bySchoolNews.setCreateTime(DateUtils.getNowDate());
                                                                                                                                return bySchoolNewsMapper.insertBySchoolNews(bySchoolNews);
    }

    /**
     * 修改新闻中心
     *
     * @param bySchoolNews 新闻中心
     * @return 结果
     */
    @Override
    public int updateBySchoolNews(BySchoolNews bySchoolNews)
    {
                                                                                                                                                                                                                                                                                                        return bySchoolNewsMapper.updateBySchoolNews(bySchoolNews);
    }

    /**
     * 批量删除新闻中心
     *
     * @param ids 需要删除的新闻中心ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolNewsByIds(Long[] ids)
    {
        return bySchoolNewsMapper.deleteBySchoolNewsByIds(ids);
    }

    /**
     * 删除新闻中心信息
     *
     * @param id 新闻中心ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolNewsById(Long id)
    {
        return bySchoolNewsMapper.deleteBySchoolNewsById(id);
    }
}
