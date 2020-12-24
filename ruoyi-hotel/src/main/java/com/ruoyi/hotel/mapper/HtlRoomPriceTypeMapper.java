package com.ruoyi.hotel.mapper;

import java.util.List;
import com.ruoyi.hotel.domain.HtlRoomPriceType;

/**
 * 房价类型Mapper接口
 * 
 * @author sucheng
 * @date 2020-12-23
 */
public interface HtlRoomPriceTypeMapper 
{
    /**
     * 查询房价类型
     * 
     * @param roomPriceTypeId 房价类型ID
     * @return 房价类型
     */
    public HtlRoomPriceType selectHtlRoomPriceTypeById(Long roomPriceTypeId);

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
     * 删除房价类型
     * 
     * @param roomPriceTypeId 房价类型ID
     * @return 结果
     */
    public int deleteHtlRoomPriceTypeById(Long roomPriceTypeId);

    /**
     * 批量删除房价类型
     * 
     * @param roomPriceTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteHtlRoomPriceTypeByIds(Long[] roomPriceTypeIds);
}
