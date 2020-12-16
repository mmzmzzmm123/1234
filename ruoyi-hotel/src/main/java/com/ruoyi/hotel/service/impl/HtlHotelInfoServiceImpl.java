package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlHotelInfoMapper;
import com.ruoyi.hotel.domain.HtlHotelInfo;
import com.ruoyi.hotel.service.IHtlHotelInfoService;

/**
 * 酒店信息Service业务层处理
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Service
public class HtlHotelInfoServiceImpl implements IHtlHotelInfoService 
{
    @Autowired
    private HtlHotelInfoMapper htlHotelInfoMapper;

    /**
     * 查询酒店信息
     * 
     * @param hotelId 酒店信息ID
     * @return 酒店信息
     */
    @Override
    public HtlHotelInfo selectHtlHotelInfoById(Long hotelId)
    {
        return htlHotelInfoMapper.selectHtlHotelInfoById(hotelId);
    }

	@Override
	public HtlHotelInfo selectHtlHotelInfoByUserId(Long userId) {
		return htlHotelInfoMapper.selectHtlHotelInfoByUserId(userId);
	}
	
    /**
     * 查询酒店信息列表
     * 
     * @param htlHotelInfo 酒店信息
     * @return 酒店信息
     */
    @Override
    public List<HtlHotelInfo> selectHtlHotelInfoList(HtlHotelInfo htlHotelInfo)
    {
        return htlHotelInfoMapper.selectHtlHotelInfoList(htlHotelInfo);
    }

    /**
     * 新增酒店信息
     * 
     * @param htlHotelInfo 酒店信息
     * @return 结果
     */
    @Override
    public int insertHtlHotelInfo(HtlHotelInfo htlHotelInfo)
    {
        htlHotelInfo.setCreateTime(DateUtils.getNowDate());
        return htlHotelInfoMapper.insertHtlHotelInfo(htlHotelInfo);
    }

    /**
     * 修改酒店信息
     * 
     * @param htlHotelInfo 酒店信息
     * @return 结果
     */
    @Override
    public int updateHtlHotelInfo(HtlHotelInfo htlHotelInfo)
    {
        htlHotelInfo.setUpdateTime(DateUtils.getNowDate());
        return htlHotelInfoMapper.updateHtlHotelInfo(htlHotelInfo);
    }

    /**
     * 批量删除酒店信息
     * 
     * @param hotelIds 需要删除的酒店信息ID
     * @return 结果
     */
    @Override
    public int deleteHtlHotelInfoByIds(Long[] hotelIds)
    {
        return htlHotelInfoMapper.deleteHtlHotelInfoByIds(hotelIds);
    }

    /**
     * 删除酒店信息信息
     * 
     * @param hotelId 酒店信息ID
     * @return 结果
     */
    @Override
    public int deleteHtlHotelInfoById(Long hotelId)
    {
        return htlHotelInfoMapper.deleteHtlHotelInfoById(hotelId);
    }

	@Override
	public long getHotelIdByUserId(Long userId) {
		HtlHotelInfo hotelInfo = this.selectHtlHotelInfoByUserId(userId);
		if (null != hotelInfo) {
			return hotelInfo.getHotelId();
		}
		return -1;
	}

}
