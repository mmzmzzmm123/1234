package com.ruoyi.common.service.impl;

import com.ruoyi.common.domain.City;
import com.ruoyi.common.mapper.CityMapper;
import com.ruoyi.common.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-08
 */
@Service
public class CityServiceImpl implements ICityService {
    @Autowired
    private CityMapper cityMapper;

    /**
     * 查询 城市
     *
     * @param code 城市主键
     * @return 城市
     */
    @Override
    public City selectCityByCode(Long code) {
        return cityMapper.selectCityByCode(code);
    }

    /**
     * 查询 城市列表
     *
     * @param city 城市
     * @return 城市
     */
    @Override
    public List<City> selectCityList(City city) {
        return cityMapper.selectCityList(city);
    }

    /**
     * 新增 城市
     *
     * @param city 城市
     * @return 结果
     */
    @Override
    public int insertCity(City city) {
        return cityMapper.insertCity(city);
    }

    /**
     * 修改 城市
     *
     * @param city 城市
     * @return 结果
     */
    @Override
    public int updateCity(City city) {
        return cityMapper.updateCity(city);
    }

    /**
     * 批量删除 城市
     *
     * @param codes 需要删除的 城市主键
     * @return 结果
     */
    @Override
    public int deleteCityByCodes(Long[] codes) {
        return cityMapper.deleteCityByCodes(codes);
    }

    /**
     * 删除 城市信息
     *
     * @param code 城市主键
     * @return 结果
     */
    @Override
    public int deleteCityByCode(Long code) {
        return cityMapper.deleteCityByCode(code);
    }
}
