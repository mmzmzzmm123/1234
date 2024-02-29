package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TpCountries;

/**
 * 全球国家管理Mapper接口
 * 
 * @author carol
 * @date 2024-02-29
 */
public interface TpCountriesMapper 
{
    /**
     * 查询全球国家管理
     * 
     * @param id 全球国家管理主键
     * @return 全球国家管理
     */
    public TpCountries selectTpCountriesById(Long id);

    /**
     * 查询全球国家管理列表
     * 
     * @param tpCountries 全球国家管理
     * @return 全球国家管理集合
     */
    public List<TpCountries> selectTpCountriesList(TpCountries tpCountries);

    /**
     * 新增全球国家管理
     * 
     * @param tpCountries 全球国家管理
     * @return 结果
     */
    public int insertTpCountries(TpCountries tpCountries);

    /**
     * 修改全球国家管理
     * 
     * @param tpCountries 全球国家管理
     * @return 结果
     */
    public int updateTpCountries(TpCountries tpCountries);

    /**
     * 删除全球国家管理
     * 
     * @param id 全球国家管理主键
     * @return 结果
     */
    public int deleteTpCountriesById(Long id);

    /**
     * 批量删除全球国家管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTpCountriesByIds(Long[] ids);
}
