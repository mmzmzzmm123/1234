package com.onethinker.weatherinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.weatherinfo.domain.WeatherInfo;

import java.util.List;

/**
 * 天气预报最新结果Mapper接口
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
public interface WeatherInfoMapper extends BaseMapper<WeatherInfo> {
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
     * 删除天气预报最新结果
     *
     * @param id 天气预报最新结果主键
     * @return 结果
     */
    public int deleteWeatherInfoById(Long id);

    /**
     * 批量删除天气预报最新结果
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWeatherInfoByIds(Long[] ids);
}
