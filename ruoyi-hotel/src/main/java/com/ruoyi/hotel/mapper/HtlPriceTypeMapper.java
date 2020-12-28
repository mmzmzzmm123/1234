package com.ruoyi.hotel.mapper;

import java.util.List;
import com.ruoyi.hotel.domain.HtlPriceType;

/**
 * 房价类型Mapper接口
 * 
 * @author sucheng
 * @date 2020-12-24
 */
public interface HtlPriceTypeMapper 
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
     * 删除房价类型
     * 
     * @param priceTypeId 房价类型ID
     * @return 结果
     */
    public int deleteHtlPriceTypeById(Long priceTypeId);

    /**
     * 批量删除房价类型
     * 
     * @param priceTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteHtlPriceTypeByIds(Long[] priceTypeIds);
}
