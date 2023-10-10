package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TRoomChargePrice;

/**
 * 房间续费套餐Service接口
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public interface ITRoomChargePriceService 
{
    /**
     * 查询房间续费套餐
     * 
     * @param id 房间续费套餐主键
     * @return 房间续费套餐
     */
    public TRoomChargePrice selectTRoomChargePriceById(Long id);

    /**
     * 查询房间续费套餐列表
     * 
     * @param tRoomChargePrice 房间续费套餐
     * @return 房间续费套餐集合
     */
    public List<TRoomChargePrice> selectTRoomChargePriceList(TRoomChargePrice tRoomChargePrice);

    /**
     * 新增房间续费套餐
     * 
     * @param tRoomChargePrice 房间续费套餐
     * @return 结果
     */
    public int insertTRoomChargePrice(TRoomChargePrice tRoomChargePrice);

    /**
     * 修改房间续费套餐
     * 
     * @param tRoomChargePrice 房间续费套餐
     * @return 结果
     */
    public int updateTRoomChargePrice(TRoomChargePrice tRoomChargePrice);

    /**
     * 批量删除房间续费套餐
     * 
     * @param ids 需要删除的房间续费套餐主键集合
     * @return 结果
     */
    public int deleteTRoomChargePriceByIds(Long[] ids);

    /**
     * 删除房间续费套餐信息
     * 
     * @param id 房间续费套餐主键
     * @return 结果
     */
    public int deleteTRoomChargePriceById(Long id);
}
