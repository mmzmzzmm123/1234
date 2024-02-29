package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TpCountriesMapper;
import com.ruoyi.system.domain.TpCountries;
import com.ruoyi.system.service.ITpCountriesService;

/**
 * 全球国家管理Service业务层处理
 * 
 * @author carol
 * @date 2024-02-29
 */
@Service
public class TpCountriesServiceImpl implements ITpCountriesService 
{
    @Autowired
    private TpCountriesMapper tpCountriesMapper;

    /**
     * 查询全球国家管理
     * 
     * @param id 全球国家管理主键
     * @return 全球国家管理
     */
    @Override
    public TpCountries selectTpCountriesById(Long id)
    {
        return tpCountriesMapper.selectTpCountriesById(id);
    }

    /**
     * 查询全球国家管理列表
     * 
     * @param tpCountries 全球国家管理
     * @return 全球国家管理
     */
    @Override
    public List<TpCountries> selectTpCountriesList(TpCountries tpCountries)
    {
        return tpCountriesMapper.selectTpCountriesList(tpCountries);
    }

    /**
     * 新增全球国家管理
     * 
     * @param tpCountries 全球国家管理
     * @return 结果
     */
    @Override
    public int insertTpCountries(TpCountries tpCountries)
    {
        return tpCountriesMapper.insertTpCountries(tpCountries);
    }

    /**
     * 修改全球国家管理
     * 
     * @param tpCountries 全球国家管理
     * @return 结果
     */
    @Override
    public int updateTpCountries(TpCountries tpCountries)
    {
        return tpCountriesMapper.updateTpCountries(tpCountries);
    }

    /**
     * 批量删除全球国家管理
     * 
     * @param ids 需要删除的全球国家管理主键
     * @return 结果
     */
    @Override
    public int deleteTpCountriesByIds(Long[] ids)
    {
        return tpCountriesMapper.deleteTpCountriesByIds(ids);
    }

    /**
     * 删除全球国家管理信息
     * 
     * @param id 全球国家管理主键
     * @return 结果
     */
    @Override
    public int deleteTpCountriesById(Long id)
    {
        return tpCountriesMapper.deleteTpCountriesById(id);
    }
}
