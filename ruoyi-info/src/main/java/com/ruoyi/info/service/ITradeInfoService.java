package com.ruoyi.info.service;

import java.util.List;
import com.ruoyi.info.domain.TradeInfo;

/**
 * 交易信息Service接口
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
public interface ITradeInfoService 
{
    /**
     * 查询交易信息
     * 
     * @param id 交易信息主键
     * @return 交易信息
     */
    public TradeInfo selectTradeInfoById(Long id);

    /**
     * 查询交易信息列表
     * 
     * @param tradeInfo 交易信息
     * @return 交易信息集合
     */
    public List<TradeInfo> selectTradeInfoList(TradeInfo tradeInfo);

    /**
     * 新增交易信息
     * 
     * @param tradeInfo 交易信息
     * @return 结果
     */
    public int insertTradeInfo(TradeInfo tradeInfo);

    /**
     * 修改交易信息
     * 
     * @param tradeInfo 交易信息
     * @return 结果
     */
    public int updateTradeInfo(TradeInfo tradeInfo);

    /**
     * 批量删除交易信息
     * 
     * @param ids 需要删除的交易信息主键集合
     * @return 结果
     */
    public int deleteTradeInfoByIds(Long[] ids);

    /**
     * 删除交易信息信息
     * 
     * @param id 交易信息主键
     * @return 结果
     */
    public int deleteTradeInfoById(Long id);
}
