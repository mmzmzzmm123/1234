package com.ruoyi.hotel.service;

import java.util.List;
import com.ruoyi.hotel.domain.HtlRoomPriceType;

/**
 * 房价类型Service接口
 * 
 * @author sucheng
 * @date 2020-11-22
 */
public interface IHtlRoomPriceTypeService 
{
    /**
     * 查询房价类型
     * 
     * @param hotelId 房价类型ID
     * @return 房价类型
     */
    public HtlRoomPriceType selectHtlRoomPriceTypeById(Long hotelId);

    /**
     * 查询房价类型列表
     * 
     * @param htlRoomPriceType 房价类型
     * @return 房价类型集合
     */
    public List<HtlRoomPriceType> selectHtlRoomPriceTypeList(HtlRoomPriceType htlRoomPriceType);

    /**
     * 新增房价类型
     * 
     * @param htlRoomPriceType 房价类型
     * @return 结果
     */
    public int insertHtlRoomPriceType(HtlRoomPriceType htlRoomPriceType);

    /**
     * 修改房价类型
     * 
     * @param htlRoomPriceType 房价类型
     * @return 结果
     */
    public int updateHtlRoomPriceType(HtlRoomPriceType htlRoomPriceType);

    /**
     * 批量删除房价类型
     * 
     * @param hotelIds 需要删除的房价类型ID
     * @return 结果
     */
    public int deleteHtlRoomPriceTypeByIds(Long[] hotelIds);

    /**
     * 删除房价类型信息
     * 
     * @param hotelId 房价类型ID
     * @return 结果
     */
    public int deleteHtlRoomPriceTypeById(Long hotelId);
}
