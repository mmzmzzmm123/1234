package com.moon.home.service;

import java.util.List;
import com.moon.home.domain.HomeDaily;

/**
 * 家居Service接口
 * 
 * @author fo
 * @date 2022-05-23
 */
public interface IHomeDailyService 
{
    /**
     * 查询家居
     * 
     * @param id 家居主键
     * @return 家居
     */
    public HomeDaily selectHomeDailyById(Long id);

    /**
     * 查询家居列表
     * 
     * @param homeDaily 家居
     * @return 家居集合
     */
    public List<HomeDaily> selectHomeDailyList(HomeDaily homeDaily);

    /**
     * 新增家居
     * 
     * @param homeDaily 家居
     * @return 结果
     */
    public int insertHomeDaily(HomeDaily homeDaily);

    /**
     * 修改家居
     * 
     * @param homeDaily 家居
     * @return 结果
     */
    public int updateHomeDaily(HomeDaily homeDaily);

    /**
     * 批量删除家居
     * 
     * @param ids 需要删除的家居主键集合
     * @return 结果
     */
    public int deleteHomeDailyByIds(Long[] ids);

    /**
     * 删除家居信息
     * 
     * @param id 家居主键
     * @return 结果
     */
    public int deleteHomeDailyById(Long id);
}
