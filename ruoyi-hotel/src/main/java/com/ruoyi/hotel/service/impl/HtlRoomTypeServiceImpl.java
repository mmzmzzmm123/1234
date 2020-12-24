package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlRoomTypeMapper;
import com.ruoyi.hotel.domain.HtlRoomType;
import com.ruoyi.hotel.service.IHtlRoomTypeService;

/**
 * 房间类型Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-23
 */
@Service
public class HtlRoomTypeServiceImpl implements IHtlRoomTypeService 
{
    @Autowired
    private HtlRoomTypeMapper htlRoomTypeMapper;

    /**
     * 查询房间类型
     * 
     * @param roomTypeId 房间类型ID
     * @return 房间类型
     */
    @Override
    public HtlRoomType selectHtlRoomTypeById(Long roomTypeId)
    {
        return htlRoomTypeMapper.selectHtlRoomTypeById(roomTypeId);
    }

    /**
     * 查询房间类型列表
     * 
     * @param htlRoomType 房间类型
     * @return 房间类型
     */
    @Override
    public List<HtlRoomType> selectHtlRoomTypeList(HtlRoomType htlRoomType)
    {
        return htlRoomTypeMapper.selectHtlRoomTypeList(htlRoomType);
    }

    /**
     * 新增房间类型
     * 
     * @param htlRoomType 房间类型
     * @return 结果
     */
    @Override
    public int insertHtlRoomType(HtlRoomType htlRoomType)
    {
        htlRoomType.setCreateTime(DateUtils.getNowDate());
        return htlRoomTypeMapper.insertHtlRoomType(htlRoomType);
    }

    /**
     * 修改房间类型
     * 
     * @param htlRoomType 房间类型
     * @return 结果
     */
    @Override
    public int updateHtlRoomType(HtlRoomType htlRoomType)
    {
        htlRoomType.setUpdateTime(DateUtils.getNowDate());
        return htlRoomTypeMapper.updateHtlRoomType(htlRoomType);
    }

    /**
     * 批量删除房间类型
     * 
     * @param roomTypeIds 需要删除的房间类型ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomTypeByIds(Long[] roomTypeIds)
    {
        return htlRoomTypeMapper.deleteHtlRoomTypeByIds(roomTypeIds);
    }

    /**
     * 删除房间类型信息
     * 
     * @param roomTypeId 房间类型ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomTypeById(Long roomTypeId)
    {
        return htlRoomTypeMapper.deleteHtlRoomTypeById(roomTypeId);
    }
}
