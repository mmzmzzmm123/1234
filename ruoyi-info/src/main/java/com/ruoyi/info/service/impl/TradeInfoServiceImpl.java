package com.ruoyi.info.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.info.util.GenerateNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.info.mapper.TradeInfoMapper;
import com.ruoyi.info.domain.TradeInfo;
import com.ruoyi.info.service.ITradeInfoService;

/**
 * 交易信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
@Service
public class TradeInfoServiceImpl implements ITradeInfoService 
{
    @Autowired
    private TradeInfoMapper tradeInfoMapper;
    @Autowired
    private GenerateNoUtils generateNoUtils;

    /**
     * 查询交易信息
     * 
     * @param id 交易信息主键
     * @return 交易信息
     */
    @Override
    public TradeInfo selectTradeInfoById(Long id)
    {
        return tradeInfoMapper.selectTradeInfoById(id);
    }

    /**
     * 查询交易信息列表
     * 
     * @param tradeInfo 交易信息
     * @return 交易信息
     */
    @Override
    public List<TradeInfo> selectTradeInfoList(TradeInfo tradeInfo)
    {
        return tradeInfoMapper.selectTradeInfoList(tradeInfo);
    }

    /**
     * 新增交易信息
     * 
     * @param tradeInfo 交易信息
     * @return 结果
     */
    @Override
    public int insertTradeInfo(TradeInfo tradeInfo)
    {
        String tradeNo = generateNoUtils.tradeNo();
        tradeInfo.setCreateTime(DateUtils.getNowDate());
        tradeInfo.setTradeNo(tradeNo);
        return tradeInfoMapper.insertTradeInfo(tradeInfo);
    }

    /**
     * 修改交易信息
     * 
     * @param tradeInfo 交易信息
     * @return 结果
     */
    @Override
    public int updateTradeInfo(TradeInfo tradeInfo)
    {
        tradeInfo.setUpdateTime(DateUtils.getNowDate());
        return tradeInfoMapper.updateTradeInfo(tradeInfo);
    }

    /**
     * 批量删除交易信息
     * 
     * @param ids 需要删除的交易信息主键
     * @return 结果
     */
    @Override
    public int deleteTradeInfoByIds(Long[] ids)
    {
        return tradeInfoMapper.deleteTradeInfoByIds(ids);
    }

    /**
     * 删除交易信息信息
     * 
     * @param id 交易信息主键
     * @return 结果
     */
    @Override
    public int deleteTradeInfoById(Long id)
    {
        return tradeInfoMapper.deleteTradeInfoById(id);
    }
}
