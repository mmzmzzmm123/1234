package com.ruoyi.hotel.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.CurrentWeatherMapper;
import com.ruoyi.hotel.domain.CurrentWeather;
import com.ruoyi.hotel.service.ICurrentWeatherService;

/**
 * 实况天气Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-30
 */
@Service
public class CurrentWeatherServiceImpl implements ICurrentWeatherService 
{
    @Autowired
    private CurrentWeatherMapper currentWeatherMapper;

    /**
     * 查询实况天气
     * 
     * @param locationId 实况天气ID
     * @return 实况天气
     */
    @Override
    public CurrentWeather selectCurrentWeatherById(Long locationId)
    {
        return currentWeatherMapper.selectCurrentWeatherById(locationId);
    }

    /**
     * 查询实况天气列表
     * 
     * @param currentWeather 实况天气
     * @return 实况天气
     */
    @Override
    public List<CurrentWeather> selectCurrentWeatherList(CurrentWeather currentWeather)
    {
        return currentWeatherMapper.selectCurrentWeatherList(currentWeather);
    }

    /**
     * 新增实况天气
     * 
     * @param currentWeather 实况天气
     * @return 结果
     */
    @Override
    public int insertCurrentWeather(CurrentWeather currentWeather)
    {
        return currentWeatherMapper.insertCurrentWeather(currentWeather);
    }

    /**
     * 修改实况天气
     * 
     * @param currentWeather 实况天气
     * @return 结果
     */
    @Override
    public int updateCurrentWeather(CurrentWeather currentWeather)
    {
        return currentWeatherMapper.updateCurrentWeather(currentWeather);
    }

    /**
     * 批量删除实况天气
     * 
     * @param locationIds 需要删除的实况天气ID
     * @return 结果
     */
    @Override
    public int deleteCurrentWeatherByIds(Long[] locationIds)
    {
        return currentWeatherMapper.deleteCurrentWeatherByIds(locationIds);
    }

    /**
     * 删除实况天气信息
     * 
     * @param locationId 实况天气ID
     * @return 结果
     */
    @Override
    public int deleteCurrentWeatherById(Long locationId)
    {
        return currentWeatherMapper.deleteCurrentWeatherById(locationId);
    }
}
