package com.ruoyi.web.controller.gzh;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.http.HttpUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author code威
 * @version 1.0
 * @description: 天气控制层
 * @date 2023/5/9 14:42
 */
@RestController
public class WeatherController {

    @RequestMapping("/getWeatherByLocalIP")
    public AjaxResult getWeather() throws UnsupportedEncodingException {
        AjaxResult result = AjaxResult.success();
        String localCityName = GetLocationAndIP.getLocalCityName();
        //调用天气API
        String encodeCity = URLEncoder.encode(localCityName, "UTF-8");
        System.out.println(encodeCity);
        String url = "http://apis.juhe.cn/simpleWeather/query?city=" + encodeCity + "&key=81fe33a6077267b2e4ae2967af47eeb7";
        String weatherInfo = HttpUtils.sendGet(url);
        result.put("msg", weatherInfo);
        return result;



    }


}
