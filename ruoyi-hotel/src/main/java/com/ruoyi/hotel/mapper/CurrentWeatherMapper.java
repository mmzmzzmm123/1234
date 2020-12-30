package com.ruoyi.hotel.mapper;

import java.util.List;
import com.ruoyi.hotel.domain.CurrentWeather;

/**
 * 实况天气Mapper接口
 * 
 * @author sucheng
 * @date 2020-12-30
 */
public interface CurrentWeatherMapper 
{
    /**
     * 查询实况天气
     * 
     * @param locationId 实况天气ID
     * @return 实况天气
     */
    public CurrentWeather selectCurrentWeatherById(Long locationId);

    /**
     * 查询实况天气列表
     * 
     * @param currentWeather 实况天气
     * @return 实况天气集合
     */
    public List<CurrentWeather> selectCurrentWeatherList(CurrentWeather currentWeather);

    /**
     * 新增实况天气
     * 
     * @param currentWeather 实况天气
     * @return 结果
     */
    public int insertCurrentWeather(CurrentWeather currentWeather);

    /**
     * 修改实况天气
     * 
     * @param currentWeather 实况天气
     * @return 结果
     */
    public int updateCurrentWeather(CurrentWeather currentWeather);

    /**
     * 删除实况天气
     * 
     * @param locationId 实况天气ID
     * @return 结果
     */
    public int deleteCurrentWeatherById(Long locationId);

    /**
     * 批量删除实况天气
     * 
     * @param locationIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCurrentWeatherByIds(Long[] locationIds);
}
