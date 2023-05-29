package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomMapper;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.service.ITRoomService;

/**
 * 店铺房间Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TRoomServiceImpl extends ServiceImpl<TRoomMapper, TRoom> implements ITRoomService
{
    @Autowired
    private TRoomMapper tRoomMapper;

    /**
     * 查询店铺房间
     * 
     * @param id 店铺房间主键
     * @return 店铺房间
     */
    @Override
    public TRoom selectTRoomById(Long id)
    {
        return tRoomMapper.selectTRoomById(id);
    }

    /**
     * 查询店铺房间列表
     * 
     * @param tRoom 店铺房间
     * @return 店铺房间
     */
    @Override
    public List<TRoom> selectTRoomList(TRoom tRoom)
    {
        return tRoomMapper.selectTRoomList(tRoom);
    }

    /**
     * 新增店铺房间
     * 
     * @param tRoom 店铺房间
     * @return 结果
     */
    @Override
    public int insertTRoom(TRoom tRoom)
    {
        tRoom.setCreateTime(DateUtils.getNowDate());
        return tRoomMapper.insertTRoom(tRoom);
    }

    /**
     * 修改店铺房间
     * 
     * @param tRoom 店铺房间
     * @return 结果
     */
    @Override
    public int updateTRoom(TRoom tRoom)
    {
        tRoom.setUpdateTime(DateUtils.getNowDate());
        return tRoomMapper.updateTRoom(tRoom);
    }

    /**
     * 批量删除店铺房间
     * 
     * @param ids 需要删除的店铺房间主键
     * @return 结果
     */
    @Override
    public int deleteTRoomByIds(Long[] ids)
    {
        return tRoomMapper.deleteTRoomByIds(ids);
    }

    /**
     * 删除店铺房间信息
     * 
     * @param id 店铺房间主键
     * @return 结果
     */
    @Override
    public int deleteTRoomById(Long id)
    {
        return tRoomMapper.deleteTRoomById(id);
    }
}
