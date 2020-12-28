package com.ruoyi.hotel.service;

import java.util.List;
import com.ruoyi.hotel.domain.HtlPriceType;

/**
 * 房价类型Service接口
 * 
 * @author sucheng
 * @date 2020-12-24
 */
public interface IHtlPriceTypeService 
{
    /**
     * 查询房价类型
     * 
     * @param priceTypeId 房价类型ID
     * @return 房价类型
     */
    public HtlPriceType selectHtlPriceTypeById(Long priceTypeId);

    /**
     * 查询房价类型列表
     * 
     * @param htlPriceType 房价类型
     * @return 房价类型集合
     */
    public List<HtlPriceType> selectHtlPriceTypeList(HtlPriceType htlPriceType);

    /**
     * 新增房价类型
     * 
     * @param htlPriceType 房价类型
     * @return 结果
     */
    public int insertHtlPriceType(HtlPriceType htlPriceType);

    /**
     * 修改房价类型
     * 
     * @param htlPriceType 房价类型
     * @return 结果
     */
    public int updateHtlPriceType(HtlPriceType htlPriceType);

    /**
     * 批量删除房价类型
     * 
     * @param priceTypeIds 需要删除的房价类型ID
     * @return 结果
     */
    public int deleteHtlPriceTypeByIds(Long[] priceTypeIds);

    /**
     * 删除房价类型信息
     * 
     * @param priceTypeId 房价类型ID
     * @return 结果
     */
    public int deleteHtlPriceTypeById(Long priceTypeId);
}
