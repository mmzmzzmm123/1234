package com.ruoyi.system.service;

import com.ruoyi.system.domain.Country;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.vo.UbpmCountryVO;

import java.util.List;

/**
* @author JingZhang
* @description 针对表【t_country(ubpm国家字典表)】的数据库操作Service
* @createDate 2023-12-27 21:00:21
*/
public interface CountryService extends IService<Country> {


    Country getCountryInfoByPhone(String phone);

    UbpmCountryVO getCountriesByPhone(String phone);

    List<Country> getAll();

}
