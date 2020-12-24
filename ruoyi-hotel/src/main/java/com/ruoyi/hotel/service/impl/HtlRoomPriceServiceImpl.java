package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlRoomPriceMapper;
import com.ruoyi.hotel.domain.HtlRoomPrice;
import com.ruoyi.hotel.service.IHtlRoomPriceService;

/**
 * 房间价格Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-24
 */
@Service
public class HtlRoomPriceServiceImpl implements IHtlRoomPriceService 
{
    @Autowired
    private HtlRoomPriceMapper htlRoomPriceMapper;

    /**
     * 查询房间价格
     * 
     * @param roomTypeId 房间价格ID
     * @return 房间价格
     */
    @Override
    public HtlRoomPrice selectHtlRoomPriceById(Long roomTypeId)
    {
        return htlRoomPriceMapper.selectHtlRoomPriceById(roomTypeId);
    }

    /**
     * 查询房间价格列表
     * 
     * @param htlRoomPrice 房间价格
     * @return 房间价格
     */
    @Override
    public List<HtlRoomPrice> selectHtlRoomPriceList(HtlRoomPrice htlRoomPrice)
    {
        return htlRoomPriceMapper.selectHtlRoomPriceList(htlRoomPrice);
    }

    /**
     * 新增房间价格
     * 
     * @param htlRoomPrice 房间价格
     * @return 结果
     */
    @Override
    public int insertHtlRoomPrice(HtlRoomPrice htlRoomPrice)
    {
        htlRoomPrice.setCreateTime(DateUtils.getNowDate());
        return htlRoomPriceMapper.insertHtlRoomPrice(htlRoomPrice);
    }

    /**
     * 修改房间价格
     * 
     * @param htlRoomPrice 房间价格
     * @return 结果
     */
    @Override
    public int updateHtlRoomPrice(HtlRoomPrice htlRoomPrice)
    {
        htlRoomPrice.setUpdateTime(DateUtils.getNowDate());
        return htlRoomPriceMapper.updateHtlRoomPrice(htlRoomPrice);
    }

    /**
     * 批量删除房间价格
     * 
     * @param roomTypeIds 需要删除的房间价格ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomPriceByIds(Long[] roomTypeIds)
    {
        return htlRoomPriceMapper.deleteHtlRoomPriceByIds(roomTypeIds);
    }

    /**
     * 删除房间价格信息
     * 
     * @param roomTypeId 房间价格ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomPriceById(Long roomTypeId)
    {
        return htlRoomPriceMapper.deleteHtlRoomPriceById(roomTypeId);
    }
}
