package com.apollo.usv.service;

import java.util.List;
import com.apollo.usv.domain.ChargingPile;

/**
 * 充电桩Service接口
 * 
 * @author lwq
 * @date 2021-07-02
 */
public interface IChargingPileService 
{
    /**
     * 查询充电桩
     * 
     * @param chargingPileId 充电桩ID
     * @return 充电桩
     */
    public ChargingPile selectChargingPileById(Long chargingPileId);

    /**
     * 查询充电桩列表
     * 
     * @param chargingPile 充电桩
     * @return 充电桩集合
     */
    public List<ChargingPile> selectChargingPileList(ChargingPile chargingPile);

    /**
     * 新增充电桩
     * 
     * @param chargingPile 充电桩
     * @return 结果
     */
    public int insertChargingPile(ChargingPile chargingPile);

    /**
     * 修改充电桩
     * 
     * @param chargingPile 充电桩
     * @return 结果
     */
    public int updateChargingPile(ChargingPile chargingPile);

    /**
     * 批量删除充电桩
     * 
     * @param chargingPileIds 需要删除的充电桩ID
     * @return 结果
     */
    public int deleteChargingPileByIds(Long[] chargingPileIds);

    /**
     * 删除充电桩信息
     * 
     * @param chargingPileId 充电桩ID
     * @return 结果
     */
    public int deleteChargingPileById(Long chargingPileId);
}
