package com.apollo.usv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apollo.usv.mapper.ChargingPileMapper;
import com.apollo.usv.domain.ChargingPile;
import com.apollo.usv.service.IChargingPileService;

/**
 * 充电桩Service业务层处理
 * 
 * @author lwq
 * @date 2021-07-02
 */
@Service
public class ChargingPileServiceImpl implements IChargingPileService 
{
    @Autowired
    private ChargingPileMapper chargingPileMapper;

    /**
     * 查询充电桩
     * 
     * @param chargingPileId 充电桩ID
     * @return 充电桩
     */
    @Override
    public ChargingPile selectChargingPileById(Long chargingPileId)
    {
        return chargingPileMapper.selectChargingPileById(chargingPileId);
    }

    /**
     * 查询充电桩列表
     * 
     * @param chargingPile 充电桩
     * @return 充电桩
     */
    @Override
    public List<ChargingPile> selectChargingPileList(ChargingPile chargingPile)
    {
        return chargingPileMapper.selectChargingPileList(chargingPile);
    }

    /**
     * 新增充电桩
     * 
     * @param chargingPile 充电桩
     * @return 结果
     */
    @Override
    public int insertChargingPile(ChargingPile chargingPile)
    {
        return chargingPileMapper.insertChargingPile(chargingPile);
    }

    /**
     * 修改充电桩
     * 
     * @param chargingPile 充电桩
     * @return 结果
     */
    @Override
    public int updateChargingPile(ChargingPile chargingPile)
    {
        return chargingPileMapper.updateChargingPile(chargingPile);
    }

    /**
     * 批量删除充电桩
     * 
     * @param chargingPileIds 需要删除的充电桩ID
     * @return 结果
     */
    @Override
    public int deleteChargingPileByIds(Long[] chargingPileIds)
    {
        return chargingPileMapper.deleteChargingPileByIds(chargingPileIds);
    }

    /**
     * 删除充电桩信息
     * 
     * @param chargingPileId 充电桩ID
     * @return 结果
     */
    @Override
    public int deleteChargingPileById(Long chargingPileId)
    {
        return chargingPileMapper.deleteChargingPileById(chargingPileId);
    }
}
