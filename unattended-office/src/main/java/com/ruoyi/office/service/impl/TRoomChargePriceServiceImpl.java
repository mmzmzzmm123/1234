package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomChargePriceMapper;
import com.ruoyi.office.domain.TRoomChargePrice;
import com.ruoyi.office.service.ITRoomChargePriceService;

/**
 * 房间续费套餐Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class TRoomChargePriceServiceImpl extends ServiceImpl<TRoomChargePriceMapper, TRoomChargePrice> implements ITRoomChargePriceService
{
    @Autowired
    private TRoomChargePriceMapper tRoomChargePriceMapper;

    /**
     * 查询房间续费套餐
     * 
     * @param id 房间续费套餐主键
     * @return 房间续费套餐
     */
    @Override
    public TRoomChargePrice selectTRoomChargePriceById(Long id)
    {
        return tRoomChargePriceMapper.selectTRoomChargePriceById(id);
    }

    /**
     * 查询房间续费套餐列表
     * 
     * @param tRoomChargePrice 房间续费套餐
     * @return 房间续费套餐
     */
    @Override
    public List<TRoomChargePrice> selectTRoomChargePriceList(TRoomChargePrice tRoomChargePrice)
    {
        return tRoomChargePriceMapper.selectTRoomChargePriceList(tRoomChargePrice);
    }

    /**
     * 新增房间续费套餐
     * 
     * @param tRoomChargePrice 房间续费套餐
     * @return 结果
     */
    @Override
    public int insertTRoomChargePrice(TRoomChargePrice tRoomChargePrice)
    {
        tRoomChargePrice.setCreateTime(DateUtils.getNowDate());
        return tRoomChargePriceMapper.insertTRoomChargePrice(tRoomChargePrice);
    }

    /**
     * 修改房间续费套餐
     * 
     * @param tRoomChargePrice 房间续费套餐
     * @return 结果
     */
    @Override
    public int updateTRoomChargePrice(TRoomChargePrice tRoomChargePrice)
    {
        tRoomChargePrice.setUpdateTime(DateUtils.getNowDate());
        return tRoomChargePriceMapper.updateTRoomChargePrice(tRoomChargePrice);
    }

    /**
     * 批量删除房间续费套餐
     * 
     * @param ids 需要删除的房间续费套餐主键
     * @return 结果
     */
    @Override
    public int deleteTRoomChargePriceByIds(Long[] ids)
    {
        return tRoomChargePriceMapper.deleteTRoomChargePriceByIds(ids);
    }

    /**
     * 删除房间续费套餐信息
     * 
     * @param id 房间续费套餐主键
     * @return 结果
     */
    @Override
    public int deleteTRoomChargePriceById(Long id)
    {
        return tRoomChargePriceMapper.deleteTRoomChargePriceById(id);
    }


}
