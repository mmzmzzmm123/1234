package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlPriceTypeMapper;
import com.ruoyi.hotel.domain.HtlPriceType;
import com.ruoyi.hotel.service.IHtlPriceTypeService;

/**
 * 房价类型Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-24
 */
@Service
public class HtlPriceTypeServiceImpl implements IHtlPriceTypeService 
{
    @Autowired
    private HtlPriceTypeMapper htlPriceTypeMapper;

    /**
     * 查询房价类型
     * 
     * @param priceTypeId 房价类型ID
     * @return 房价类型
     */
    @Override
    public HtlPriceType selectHtlPriceTypeById(Long priceTypeId)
    {
        return htlPriceTypeMapper.selectHtlPriceTypeById(priceTypeId);
    }

    /**
     * 查询房价类型列表
     * 
     * @param htlPriceType 房价类型
     * @return 房价类型
     */
    @Override
    public List<HtlPriceType> selectHtlPriceTypeList(HtlPriceType htlPriceType)
    {
        return htlPriceTypeMapper.selectHtlPriceTypeList(htlPriceType);
    }

    /**
     * 新增房价类型
     * 
     * @param htlPriceType 房价类型
     * @return 结果
     */
    @Override
    public int insertHtlPriceType(HtlPriceType htlPriceType)
    {
        htlPriceType.setCreateTime(DateUtils.getNowDate());
        return htlPriceTypeMapper.insertHtlPriceType(htlPriceType);
    }

    /**
     * 修改房价类型
     * 
     * @param htlPriceType 房价类型
     * @return 结果
     */
    @Override
    public int updateHtlPriceType(HtlPriceType htlPriceType)
    {
        htlPriceType.setUpdateTime(DateUtils.getNowDate());
        return htlPriceTypeMapper.updateHtlPriceType(htlPriceType);
    }

    /**
     * 批量删除房价类型
     * 
     * @param priceTypeIds 需要删除的房价类型ID
     * @return 结果
     */
    @Override
    public int deleteHtlPriceTypeByIds(Long[] priceTypeIds)
    {
        return htlPriceTypeMapper.deleteHtlPriceTypeByIds(priceTypeIds);
    }

    /**
     * 删除房价类型信息
     * 
     * @param priceTypeId 房价类型ID
     * @return 结果
     */
    @Override
    public int deleteHtlPriceTypeById(Long priceTypeId)
    {
        return htlPriceTypeMapper.deleteHtlPriceTypeById(priceTypeId);
    }
}
