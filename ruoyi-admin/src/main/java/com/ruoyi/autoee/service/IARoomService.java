package com.ruoyi.autoee.service;

import java.util.List;
import com.ruoyi.autoee.domain.ARoom;

/**
 * 房间管理Service接口
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
public interface IARoomService 
{
    /**
     * 查询房间管理
     * 
     * @param id 房间管理主键
     * @return 房间管理
     */
    public ARoom selectARoomById(Long id);

    /**
     * 查询房间管理列表
     * 
     * @param aRoom 房间管理
     * @return 房间管理集合
     */
    public List<ARoom> selectARoomList(ARoom aRoom);

    /**
     * 新增房间管理
     * 
     * @param aRoom 房间管理
     * @return 结果
     */
    public int insertARoom(ARoom aRoom);

    /**
     * 修改房间管理
     * 
     * @param aRoom 房间管理
     * @return 结果
     */
    public int updateARoom(ARoom aRoom);

    /**
     * 批量删除房间管理
     * 
     * @param ids 需要删除的房间管理主键集合
     * @return 结果
     */
    public int deleteARoomByIds(Long[] ids);

    /**
     * 删除房间管理信息
     * 
     * @param id 房间管理主键
     * @return 结果
     */
    public int deleteARoomById(Long id);
}
