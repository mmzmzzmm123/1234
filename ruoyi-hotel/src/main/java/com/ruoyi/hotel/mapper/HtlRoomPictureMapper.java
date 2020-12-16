package com.ruoyi.hotel.mapper;

import java.util.List;
import com.ruoyi.hotel.domain.HtlRoomPicture;

/**
 * 房间图片Mapper接口
 * 
 * @author sucheng
 * @date 2020-11-22
 */
public interface HtlRoomPictureMapper 
{
    /**
     * 查询房间图片
     * 
     * @param pictureId 房间图片ID
     * @return 房间图片
     */
    public HtlRoomPicture selectHtlRoomPictureById(Long pictureId);

    /**
     * 查询房间图片列表
     * 
     * @param htlRoomPicture 房间图片
     * @return 房间图片集合
     */
    public List<HtlRoomPicture> selectHtlRoomPictureList(HtlRoomPicture htlRoomPicture);

    /**
     * 新增房间图片
     * 
     * @param htlRoomPicture 房间图片
     * @return 结果
     */
    public int insertHtlRoomPicture(HtlRoomPicture htlRoomPicture);

    /**
     * 修改房间图片
     * 
     * @param htlRoomPicture 房间图片
     * @return 结果
     */
    public int updateHtlRoomPicture(HtlRoomPicture htlRoomPicture);

    /**
     * 删除房间图片
     * 
     * @param pictureId 房间图片ID
     * @return 结果
     */
    public int deleteHtlRoomPictureById(Long pictureId);

    /**
     * 批量删除房间图片
     * 
     * @param pictureIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteHtlRoomPictureByIds(Long[] pictureIds);
}
