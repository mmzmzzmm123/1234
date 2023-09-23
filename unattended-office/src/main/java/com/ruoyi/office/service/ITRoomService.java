package com.ruoyi.office.service;

import java.util.List;

import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.vo.RoomEquipeOpenReq;

/**
 * 店铺房间Service接口
 *
 * @author ruoyi
 * @date 2023-05-29
 */
public interface ITRoomService {
    /**
     * 查询店铺房间
     *
     * @param id 店铺房间主键
     * @return 店铺房间
     */
    public TRoom selectTRoomById(Long id);

    /**
     * 查询店铺房间列表
     *
     * @param tRoom 店铺房间
     * @return 店铺房间集合
     */
    public List<TRoom> selectTRoomList(TRoom tRoom);

    /**
     * 新增店铺房间
     *
     * @param tRoom 店铺房间
     * @return 结果
     */
    public int insertTRoom(TRoom tRoom);

    /**
     * 修改店铺房间
     *
     * @param tRoom 店铺房间
     * @return 结果
     */
    public int updateTRoom(TRoom tRoom);

    /**
     * 批量删除店铺房间
     *
     * @param ids 需要删除的店铺房间主键集合
     * @return 结果
     */
    public int deleteTRoomByIds(Long[] ids);

    /**
     * 删除店铺房间信息
     *
     * @param id 店铺房间主键
     * @return 结果
     */
    public int deleteTRoomById(Long id);

    void openRoom(Long id);

    void closeRoom(Long id);

    void openRoomEquipment(RoomEquipeOpenReq req, Long userId);

    void closeRoomEquipment(RoomEquipeOpenReq req, Long userId);
}
