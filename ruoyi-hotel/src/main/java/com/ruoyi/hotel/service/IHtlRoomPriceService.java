package com.ruoyi.hotel.service;

import java.util.List;
import com.ruoyi.hotel.domain.HtlRoomPrice;

/**
 * 房间价格Service接口
 * 
 * @author sucheng
 * @date 2020-11-22
 */
public interface IHtlRoomPriceService 
{
    /**
     * 查询房间价格
     * 
     * @param roomRateId 房间价格ID
     * @return 房间价格
     */
    public HtlRoomPrice selectHtlRoomPriceById(Long roomRateId);

    /**
     * 查询房间价格列表
     * 
     * @param htlRoomPrice 房间价格
     * @return 房间价格集合
     */
    public List<HtlRoomPrice> selectHtlRoomPriceList(HtlRoomPrice htlRoomPrice);

    /**
     * 新增房间价格
     * 
     * @param htlRoomPrice 房间价格
     * @return 结果
     */
    public int insertHtlRoomPrice(HtlRoomPrice htlRoomPrice);

    /**
     * 修改房间价格
     * 
     * @param htlRoomPrice 房间价格
     * @return 结果
     */
    public int updateHtlRoomPrice(HtlRoomPrice htlRoomPrice);

    /**
     * 批量删除房间价格
     * 
     * @param roomRateIds 需要删除的房间价格ID
     * @return 结果
     */
    public int deleteHtlRoomPriceByIds(Long[] roomRateIds);

    /**
     * 删除房间价格信息
     * 
     * @param roomRateId 房间价格ID
     * @return 结果
     */
    public int deleteHtlRoomPriceById(Long roomRateId);
}
