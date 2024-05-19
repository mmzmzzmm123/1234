package com.onethinker.weatherinfo.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.weatherinfo.domain.WeatherInfo;
import com.onethinker.weatherinfo.mapper.WeatherInfoMapper;
import com.onethinker.weatherinfo.service.IWeatherInfoService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 天气预报最新结果Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
@Service
@Log4j2
public class WeatherInfoServiceImpl extends ServiceImpl<WeatherInfoMapper, WeatherInfo> implements IWeatherInfoService {
    @Resource
    private WeatherInfoMapper weatherInfoMapper;

    /**
     * 查询天气预报最新结果
     *
     * @param id 天气预报最新结果主键
     * @return 天气预报最新结果
     */
    @Override
    public WeatherInfo selectWeatherInfoById(Long id) {
        return weatherInfoMapper.selectWeatherInfoById(id);
    }

    /**
     * 查询天气预报最新结果列表
     *
     * @param weatherInfo 天气预报最新结果
     * @return 天气预报最新结果
     */
    @Override
    public List<WeatherInfo> selectWeatherInfoList(WeatherInfo weatherInfo) {
        return weatherInfoMapper.selectWeatherInfoList(weatherInfo);
    }

    /**
     * 新增天气预报最新结果
     *
     * @param weatherInfo 天气预报最新结果
     * @return 结果
     */
    @Override
    public int insertWeatherInfo(WeatherInfo weatherInfo) {
        weatherInfo.setCreateTime(DateUtils.getNowDate());
        return weatherInfoMapper.insertWeatherInfo(weatherInfo);
    }

    /**
     * 修改天气预报最新结果
     *
     * @param weatherInfo 天气预报最新结果
     * @return 结果
     */
    @Override
    public int updateWeatherInfo(WeatherInfo weatherInfo) {
        weatherInfo.setUpdateTime(DateUtils.getNowDate());
        return weatherInfoMapper.updateWeatherInfo(weatherInfo);
    }

    /**
     * 批量删除天气预报最新结果
     *
     * @param ids 需要删除的天气预报最新结果主键
     * @return 结果
     */
    @Override
    public int deleteWeatherInfoByIds(Long[] ids) {
        return weatherInfoMapper.deleteWeatherInfoByIds(ids);
    }

    /**
     * 删除天气预报最新结果信息
     *
     * @param id 天气预报最新结果主键
     * @return 结果
     */
    @Override
    public int deleteWeatherInfoById(Long id) {
        return weatherInfoMapper.deleteWeatherInfoById(id);
    }

    @Override
    public String selectNewWeatherInfoByCity(String city,String... type) {
        StringBuffer url = new StringBuffer("https://restapi.amap.com/v3/weather/weatherInfo?parameters");
        url.append("&key=e00f9370db14dae128473190e1b73507")
                .append("&city=").append(city)
                .append("&extensions=");
        // 获取实时温度
        String baseUrl = url.toString() + "base";
        String baseResult = HttpUtils.sendGet(baseUrl);
        JSONObject resultObj = JSONObject.parseObject(baseResult).getJSONArray("lives").getJSONObject(0);
        // 获取预报温度
        String allUrl = url.toString() + "all";
        String allResult = HttpUtils.sendGet(allUrl);
        JSONArray casts = JSONObject.parseObject(allResult).getJSONArray("forecasts").getJSONObject(0).getJSONArray("casts");
        // 取当天的温度差
        Date now = new Date();
        String nowDateFormat = DateFormatUtils.format(now, "yyyy-MM-dd");
        JSONObject allResultObj = new JSONObject();
        for (int i = 0; i < casts.size(); i++) {
            JSONObject doTemp = casts.getJSONObject(i);
            String date = doTemp.getString("date");
            // 当前时间
            if (nowDateFormat.equals(date)) {
                allResultObj = doTemp;
                break;
            }
        }

        // 城市名
        String cityName = resultObj.getString("city");
        // 天气现象
        String weather = resultObj.getString("weather");
        // 当前温度
        String temperature = resultObj.getString("temperature");
        // 早上温度
        String dayTemp = allResultObj.getString("daytemp");
        // 晚上温度
        String nightTemp = allResultObj.getString("nighttemp");
        // 当前星期几
        String week = DateUtils.DayOfWeek();
        // 拼接
        String resultText = "";
        if (Objects.nonNull(type) && type.length > 0) {
            resultText = "早上温度：{{dayTemp}}°,晚上温度：{{nightTemp}}°";
        } else {
            resultText = "宝！早上好ლ(°◕‵ƹ′◕ლ)，今天{{DayOfWeek}},{{cityName}}今天{{weather}}哦。早上温度：{{dayTemp}}°,晚上温度：{{nightTemp}}°,当前温度:{{temperature}}°";
        }

        return resultText.replace("{{DayOfWeek}}", week)
                .replace("{{cityName}}", cityName)
                .replace("{{weather}}", weather)
                .replace("{{dayTemp}}", dayTemp)
                .replace("{{nightTemp}}", nightTemp)
                .replace("{{temperature}}", temperature);
    }
}
