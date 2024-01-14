package com.ruoyi.common.service;

import com.ruoyi.common.domain.City;

import java.util.List;

/**
 * 城市Service接口
 *
 * @author ruoyi
 * @date 2024-01-08
 */
public interface ICityService {
    /**
     * 查询 城市
     *
     * @param code 城市主键
     * @return 城市
     */
    public City selectCityByCode(Long code);

    /**
     * 查询 城市列表
     *
     * @param city 城市
     * @return 城市集合
     */
    public List<City> selectCityList(City city);

    /**
     * 新增 城市
     *
     * @param city 城市
     * @return 结果
     */
    public int insertCity(City city);

    /**
     * 修改 城市
     *
     * @param city 城市
     * @return 结果
     */
    public int updateCity(City city);

    /**
     * 批量删除 城市
     *
     * @param codes 需要删除的 城市主键集合
     * @return 结果
     */
    public int deleteCityByCodes(Long[] codes);

    /**
     * 删除 城市信息
     *
     * @param code 城市主键
     * @return 结果
     */
    public int deleteCityByCode(Long code);
}
