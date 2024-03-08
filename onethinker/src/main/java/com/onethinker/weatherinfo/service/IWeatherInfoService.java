package com.onethinker.weatherinfo.service;

import com.onethinker.weatherinfo.domain.WeatherInfo;

import java.util.List;


/**
 * 天气预报最新结果Service接口
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
public interface IWeatherInfoService {
    /**
     * 查询天气预报最新结果
     *
     * @param id 天气预报最新结果主键
     * @return 天气预报最新结果
     */
    public WeatherInfo selectWeatherInfoById(Long id);

    /**
     * 查询天气预报最新结果列表
     *
     * @param weatherInfo 天气预报最新结果
     * @return 天气预报最新结果集合
     */
    public List<WeatherInfo> selectWeatherInfoList(WeatherInfo weatherInfo);

    /**
     * 新增天气预报最新结果
     *
     * @param weatherInfo 天气预报最新结果
     * @return 结果
     */
    public int insertWeatherInfo(WeatherInfo weatherInfo);

    /**
     * 修改天气预报最新结果
     *
     * @param weatherInfo 天气预报最新结果
     * @return 结果
     */
    public int updateWeatherInfo(WeatherInfo weatherInfo);

    /**
     * 批量删除天气预报最新结果
     *
     * @param ids 需要删除的天气预报最新结果主键集合
     * @return 结果
     */
    public int deleteWeatherInfoByIds(Long[] ids);

    /**
     * 删除天气预报最新结果信息
     *
     * @param id 天气预报最新结果主键
     * @return 结果
     */
    public int deleteWeatherInfoById(Long id);

    /**
     * 最后处理成一段文字返回
     * @param city
     * @return
     */
    String selectNewWeatherInfoByCity(String city);
}
