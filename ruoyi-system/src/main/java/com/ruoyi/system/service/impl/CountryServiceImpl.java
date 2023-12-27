package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.Country;
import com.ruoyi.system.domain.vo.UbpmCountryVO;
import com.ruoyi.system.service.CountryService;
import com.ruoyi.system.mapper.CountryMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author JingZhang
* @description 针对表【t_country(ubpm国家字典表)】的数据库操作Service实现
* @createDate 2023-12-27 21:00:21
*/
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country>
    implements CountryService{
    private Map<String, List<Country>> map = null;

    private List<Country> list = null;

    private Map<String, List<Country>> getMap() {
        if (map == null) {
            List<Country> allPhoneCountryInfo = getAll();
            map = CollectionUtils.isEmpty(allPhoneCountryInfo) ? new HashMap<>() : allPhoneCountryInfo.stream()
                    .collect(Collectors.groupingBy(Country::getPhoneArea));
        }
        return map;
    }

    @Override
    public Country getCountryInfoByPhone(String phone) {
        int checkLen = 4;
        if (StringUtils.isEmpty(phone) || phone.length() <= checkLen) {
            return null;
        }
        int i = checkLen + 1;
        while (i-- > 0) {
            List<Country> ubpmCountries = getMap().get(phone.substring(0, i));
            if (CollectionUtils.isNotEmpty(ubpmCountries)) {
                if (i < checkLen) {
                    getMap().put(phone.substring(0, checkLen), ubpmCountries);
                }

                // 存在相同区号 但是国家名不同的情况 - 需要修复(但是之前代码中有表只存了Code字段,再关联查询的)
                return ubpmCountries.get(0);
            }
        }
        return null;
    }


    public UbpmCountryVO getCountriesByPhone(String phone) {
        int checkLen = 4;
        if (StringUtils.isEmpty(phone) || phone.length() <= checkLen) {
            return null;
        }
        int i = checkLen + 1;
        while (i-- > 0) {
            List<Country> ubpmCountries = getMap().get(phone.substring(0, i));
            if (CollectionUtils.isNotEmpty(ubpmCountries)) {
                return new UbpmCountryVO().init(ubpmCountries);
            }
        }
        return null;
    }


    @Override
    public List<Country> getAll() {
        if (CollectionUtils.isEmpty(list)) {
            list = baseMapper.selectList(new LambdaQueryWrapper<>());
        }
        return list;
    }

}




