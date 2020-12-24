package com.ruoyi.hotel.service;

import java.util.List;
import com.ruoyi.hotel.domain.HtlRoomType;

/**
 * 房间类型Service接口
 * 
 * @author sucheng
 * @date 2020-12-23
 */
public interface IHtlRoomTypeService 
{
    /**
     * 查询房间类型
     * 
     * @param roomTypeId 房间类型ID
     * @return 房间类型
     */
    public HtlRoomType selectHtlRoomTypeById(Long roomTypeId);

    /**
     * 查询房间类型列表
     * 
     * @param htlRoomType 房间类型
     * @return 房间类型集合
     */
    public List<HtlRoomType> selectHtlRoomTypeList(HtlRoomType htlRoomType);

    /**
     * 新增房间类型
     * 
     * @param htlRoomType 房间类型
     * @return 结果
     */
    public int insertHtlRoomType(HtlRoomType htlRoomType);

    /**
     * 修改房间类型
     * 
     * @param htlRoomType 房间类型
     * @return 结果
     */
    public int updateHtlRoomType(HtlRoomType htlRoomType);

    /**
     * 批量删除房间类型
     * 
     * @param roomTypeIds 需要删除的房间类型ID
     * @return 结果
     */
    public int deleteHtlRoomTypeByIds(Long[] roomTypeIds);

    /**
     * 删除房间类型信息
     * 
     * @param roomTypeId 房间类型ID
     * @return 结果
     */
    public int deleteHtlRoomTypeById(Long roomTypeId);
}
