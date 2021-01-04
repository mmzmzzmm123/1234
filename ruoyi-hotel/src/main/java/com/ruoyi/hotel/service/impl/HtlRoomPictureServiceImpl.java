package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlRoomPictureMapper;
import com.ruoyi.hotel.domain.HtlRoomPicture;
import com.ruoyi.hotel.service.IHtlRoomPictureService;

/**
 * 房间图片Service业务层处理
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Service
public class HtlRoomPictureServiceImpl implements IHtlRoomPictureService 
{
    @Autowired
    private HtlRoomPictureMapper htlRoomPictureMapper;

    /**
     * 查询房间图片
     * 
     * @param pictureId 房间图片ID
     * @return 房间图片
     */
    @Override
    public HtlRoomPicture selectHtlRoomPictureById(Long pictureId)
    {
        return htlRoomPictureMapper.selectHtlRoomPictureById(pictureId);
    }

    /**
     * 查询房间图片列表
     * 
     * @param htlRoomPicture 房间图片
     * @return 房间图片
     */
    @Override
    public List<HtlRoomPicture> selectHtlRoomPictureList(HtlRoomPicture htlRoomPicture)
    {
        return htlRoomPictureMapper.selectHtlRoomPictureList(htlRoomPicture);
    }

    /**
     * 新增房间图片
     * 
     * @param htlRoomPicture 房间图片
     * @return 结果
     */
    @Override
    public int insertHtlRoomPicture(HtlRoomPicture htlRoomPicture)
    {
        htlRoomPicture.setCreateTime(DateUtils.getNowDate());
        return htlRoomPictureMapper.insertHtlRoomPicture(htlRoomPicture);
    }

    /**
     * 修改房间图片
     * 
     * @param htlRoomPicture 房间图片
     * @return 结果
     */
    @Override
    public int updateHtlRoomPicture(HtlRoomPicture htlRoomPicture)
    {
        htlRoomPicture.setUpdateTime(DateUtils.getNowDate());
        return htlRoomPictureMapper.updateHtlRoomPicture(htlRoomPicture);
    }

    /**
     * 批量删除房间图片
     * 
     * @param pictureIds 需要删除的房间图片ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomPictureByIds(Long[] pictureIds)
    {
        return htlRoomPictureMapper.deleteHtlRoomPictureByIds(pictureIds);
    }

    /**
     * 删除房间图片信息
     * 
     * @param pictureId 房间图片ID
     * @return 结果
     */
    @Override
    public int deleteHtlRoomPictureById(Long pictureId)
    {
        return htlRoomPictureMapper.deleteHtlRoomPictureById(pictureId);
    }
}
