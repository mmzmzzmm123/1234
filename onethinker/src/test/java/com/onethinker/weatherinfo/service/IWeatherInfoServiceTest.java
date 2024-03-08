package com.onethinker.weatherinfo.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : yangyouqi
 * @date : 2024/3/8 10:49
 */
@SpringBootTest
@SpringBootApplication
class IWeatherInfoServiceTest {


    @Autowired
    private IWeatherInfoService weatherInfoService;
    @Test
    void selectNewWeatherInfoByCity() {
        String city = "020";
        String s = weatherInfoService.selectNewWeatherInfoByCity(city);

        System.out.println(s);
    }
}
