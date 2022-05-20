package com.moon.home.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moon.home.mapper.HomeDailyMapper;
import com.moon.home.domain.HomeDaily;
import com.moon.home.service.IHomeDailyService;

/**
 * 家居Service业务层处理
 * 
 * @author fo
 * @date 2022-05-20
 */
@Service
public class HomeDailyServiceImpl implements IHomeDailyService 
{
    @Autowired
    private HomeDailyMapper homeDailyMapper;

    /**
     * 查询家居
     * 
     * @param id 家居主键
     * @return 家居
     */
    @Override
    public HomeDaily selectHomeDailyById(Long id)
    {
        return homeDailyMapper.selectHomeDailyById(id);
    }

    /**
     * 查询家居列表
     * 
     * @param homeDaily 家居
     * @return 家居
     */
    @Override
    public List<HomeDaily> selectHomeDailyList(HomeDaily homeDaily)
    {
        return homeDailyMapper.selectHomeDailyList(homeDaily);
    }

    /**
     * 新增家居
     * 
     * @param homeDaily 家居
     * @return 结果
     */
    @Override
    public int insertHomeDaily(HomeDaily homeDaily)
    {
        homeDaily.setCreateTime(DateUtils.getNowDate());
        return homeDailyMapper.insertHomeDaily(homeDaily);
    }

    /**
     * 修改家居
     * 
     * @param homeDaily 家居
     * @return 结果
     */
    @Override
    public int updateHomeDaily(HomeDaily homeDaily)
    {
        homeDaily.setUpdateTime(DateUtils.getNowDate());
        return homeDailyMapper.updateHomeDaily(homeDaily);
    }

    /**
     * 批量删除家居
     * 
     * @param ids 需要删除的家居主键
     * @return 结果
     */
    @Override
    public int deleteHomeDailyByIds(Long[] ids)
    {
        return homeDailyMapper.deleteHomeDailyByIds(ids);
    }

    /**
     * 删除家居信息
     * 
     * @param id 家居主键
     * @return 结果
     */
    @Override
    public int deleteHomeDailyById(Long id)
    {
        return homeDailyMapper.deleteHomeDailyById(id);
    }
}
