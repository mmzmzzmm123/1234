package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlRoomPriceTypeMapper;
import com.ruoyi.hotel.domain.HtlRoomPriceType;
import com.ruoyi.hotel.service.IHtlRoomPriceTypeService;

/**
 * 房价类型Service业务层处理
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Service
public class HtlRoomPriceTypeServiceImpl implements IHtlRoomPriceTypeService 
{
    @Autowired
    private HtlRoomPriceTypeMapper htlRoomPriceTypeMapper;

    /**
     * 查询房价类型
     * 
     * @param hotelId 房价类型ID
     * @return 房价类型
     */
    @Override
    public HtlRoomPriceType selectHtlRoomPriceTypeById(Long hotelId)
    {
        return htlRoomPriceTypeMapper.selectHtlRoomPriceTypeById(hotelId);
    }

    /**
     * 查询房价类型列表
     * 
     * @param htlRoomPriceType 房价类型
     * @return 房价类型
     */
    @Override
    public List<HtlRoomPriceType> selectHtlRoomPriceTypeList(HtlRoomPriceType htlRoomPriceType)
    {
        return htlRoomPriceTypeMapper.selectHtlRoomPriceTypeList(htlRoomPriceType);
    }

    /**
     * 新增房价类型
     * 
     * @param htlRoomPriceType 房价类型
     * @return 结果
     */
    @Override
    public int insertHtlRoomPriceType(HtlRoomPriceType htlRoomPriceType)
    {
        htlRoomPriceType.setCreateTime(DateUtils.getNowDate());
        return htlRoomPriceTypeMapper.insertHtlRoomPriceType(htlRoomPriceType);
    }

    /**
     * 修改房价类型
     * 
     * @param htlRoomPriceType 房价类型
     * @return 结果
     */
    @Override
    public int updateHtlRoomPriceType(HtlRoomPriceType htlRoomPriceType)
    {
        htlRoomPriceType.setUpdateTime(DateUtils.getNowDate());
        return htlRoomPriceTypeMapper.updateHtlRoomPriceType(htlRoomPriceType);
    }

    /**
     * 批量删除房价类型
     * 
     * @param hotelIds 需要删除的房价类型ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomPriceTypeByIds(Long[] hotelIds)
    {
        return htlRoomPriceTypeMapper.deleteHtlRoomPriceTypeByIds(hotelIds);
    }

    /**
     * 删除房价类型信息
     * 
     * @param hotelId 房价类型ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomPriceTypeById(Long hotelId)
    {
        return htlRoomPriceTypeMapper.deleteHtlRoomPriceTypeById(hotelId);
    }
}
