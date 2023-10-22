package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderChargeMapper;
import com.ruoyi.office.domain.TRoomOrderCharge;
import com.ruoyi.office.service.ITRoomOrderChargeService;

/**
 * 续费订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-22
 */
@Service
public class TRoomOrderChargeServiceImpl extends ServiceImpl<TRoomOrderChargeMapper, TRoomOrderCharge> implements ITRoomOrderChargeService
{
    @Autowired
    private TRoomOrderChargeMapper tRoomOrderChargeMapper;

    /**
     * 查询续费订单
     * 
     * @param id 续费订单主键
     * @return 续费订单
     */
    @Override
    public TRoomOrderCharge selectTRoomOrderChargeById(Long id)
    {
        return tRoomOrderChargeMapper.selectTRoomOrderChargeById(id);
    }

    /**
     * 查询续费订单列表
     * 
     * @param tRoomOrderCharge 续费订单
     * @return 续费订单
     */
    @Override
    public List<TRoomOrderCharge> selectTRoomOrderChargeList(TRoomOrderCharge tRoomOrderCharge)
    {
        return tRoomOrderChargeMapper.selectTRoomOrderChargeList(tRoomOrderCharge);
    }

    /**
     * 新增续费订单
     * 
     * @param tRoomOrderCharge 续费订单
     * @return 结果
     */
    @Override
    public int insertTRoomOrderCharge(TRoomOrderCharge tRoomOrderCharge)
    {
        tRoomOrderCharge.setCreateTime(DateUtils.getNowDate());
        return tRoomOrderChargeMapper.insertTRoomOrderCharge(tRoomOrderCharge);
    }

    /**
     * 修改续费订单
     * 
     * @param tRoomOrderCharge 续费订单
     * @return 结果
     */
    @Override
    public int updateTRoomOrderCharge(TRoomOrderCharge tRoomOrderCharge)
    {
        tRoomOrderCharge.setUpdateTime(DateUtils.getNowDate());
        return tRoomOrderChargeMapper.updateTRoomOrderCharge(tRoomOrderCharge);
    }

    /**
     * 批量删除续费订单
     * 
     * @param ids 需要删除的续费订单主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderChargeByIds(Long[] ids)
    {
        return tRoomOrderChargeMapper.deleteTRoomOrderChargeByIds(ids);
    }

    /**
     * 删除续费订单信息
     * 
     * @param id 续费订单主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderChargeById(Long id)
    {
        return tRoomOrderChargeMapper.deleteTRoomOrderChargeById(id);
    }

    @Override
    public Long getHourMaxOrder(String prefix) {
        return tRoomOrderChargeMapper.getHourMaxOrder(prefix);
    }
}
