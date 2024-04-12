package com.ruoyi.autoee.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.autoee.mapper.ARoomMapper;
import com.ruoyi.autoee.domain.ARoom;
import com.ruoyi.autoee.service.IARoomService;

/**
 * 房间管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@Service
public class ARoomServiceImpl implements IARoomService 
{
    @Autowired
    private ARoomMapper aRoomMapper;

    /**
     * 查询房间管理
     * 
     * @param id 房间管理主键
     * @return 房间管理
     */
    @Override
    public ARoom selectARoomById(Long id)
    {
        return aRoomMapper.selectARoomById(id);
    }

    /**
     * 查询房间管理列表
     * 
     * @param aRoom 房间管理
     * @return 房间管理
     */
    @Override
    public List<ARoom> selectARoomList(ARoom aRoom)
    {
        return aRoomMapper.selectARoomList(aRoom);
    }

    /**
     * 新增房间管理
     * 
     * @param aRoom 房间管理
     * @return 结果
     */
    @Override
    public int insertARoom(ARoom aRoom)
    {
        aRoom.setCreateTime(DateUtils.getNowDate());
		aRoom.setUpdateTime(aRoom.getCreateTime());
        return aRoomMapper.insertARoom(aRoom);
    }

    /**
     * 修改房间管理
     * 
     * @param aRoom 房间管理
     * @return 结果
     */
    @Override
    public int updateARoom(ARoom aRoom)
    {
        aRoom.setUpdateTime(DateUtils.getNowDate());
        return aRoomMapper.updateARoom(aRoom);
    }

    /**
     * 批量删除房间管理
     * 
     * @param ids 需要删除的房间管理主键
     * @return 结果
     */
    @Override
    public int deleteARoomByIds(Long[] ids)
    {
        return aRoomMapper.deleteARoomByIds(ids);
    }

    /**
     * 删除房间管理信息
     * 
     * @param id 房间管理主键
     * @return 结果
     */
    @Override
    public int deleteARoomById(Long id)
    {
        return aRoomMapper.deleteARoomById(id);
    }
}
