package com.ruoyi.quartz.task;

import com.heweather.api.HeWeatherInitialize;
import com.heweather.api.response.weather.HeWeatherWeatherNowResponse;
import com.heweather.api.response.weather.Now;
import com.heweather.api.service.HeWeatherWeatherService;
import com.heweather.api.service.impl.HeWeatherWeatherServiceImpl;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.hotel.domain.ChinaCityList;
import com.ruoyi.hotel.domain.CurrentWeather;
import com.ruoyi.hotel.service.IChinaCityListService;
import com.ruoyi.hotel.service.ICurrentWeatherService;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("weatherTask")
public class WeatherTask {
    @Autowired
    private IChinaCityListService cityListService;

    @Autowired
    private IHtlHotelInfoService hotelInfoService;

    @Autowired
    private ICurrentWeatherService currentWeatherService;

    public void getWeatherNow() {
        HeWeatherInitialize.init("HE2012251059551268", "edfc714e591d4b0986810bbb3a2e06f4", "https://devapi.qweather.com/");
        HeWeatherWeatherService heWeatherService = new HeWeatherWeatherServiceImpl();

        List<Long> locationList = hotelInfoService.selectAllLocationList();
        for (Long location : locationList) {
            HeWeatherWeatherNowResponse response = heWeatherService.getWeatherNow(String.valueOf(location));
            if (response.getCode().isOk()) {
                Now now = response.getNow();

                CurrentWeather nowWeather = currentWeatherService.selectCurrentWeatherById(location);
                if (null == nowWeather) {
                    nowWeather = new CurrentWeather();
                    BeanUtils.copyBeanProp(nowWeather, now);
                    ChinaCityList cityInfo = cityListService.selectChinaCityListById(location);
                    nowWeather.setLocationId(location);
                    nowWeather.setAdm1(cityInfo.getAdm1());
                    nowWeather.setAdm2(cityInfo.getAdm2());
                    nowWeather.setLocationName(cityInfo.getLocationName());
                    currentWeatherService.insertCurrentWeather(nowWeather);
                } else {
                    BeanUtils.copyBeanProp(nowWeather, now);
                    currentWeatherService.updateCurrentWeather(nowWeather);
                }
            }
        }
    }
}
