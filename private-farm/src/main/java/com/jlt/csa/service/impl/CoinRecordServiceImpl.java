package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CoinRecordMapper;
import com.jlt.csa.domain.CoinRecord;
import com.jlt.csa.service.ICoinRecordService;

/**
 * 金币记录Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class CoinRecordServiceImpl implements ICoinRecordService 
{
    @Autowired
    private CoinRecordMapper coinRecordMapper;

    /**
     * 查询金币记录
     * 
     * @param id 金币记录主键
     * @return 金币记录
     */
    @Override
    public CoinRecord selectCoinRecordById(Long id)
    {
        return coinRecordMapper.selectCoinRecordById(id);
    }

    /**
     * 查询金币记录列表
     * 
     * @param coinRecord 金币记录
     * @return 金币记录
     */
    @Override
    public List<CoinRecord> selectCoinRecordList(CoinRecord coinRecord)
    {
        return coinRecordMapper.selectCoinRecordList(coinRecord);
    }

    /**
     * 新增金币记录
     * 
     * @param coinRecord 金币记录
     * @return 结果
     */
    @Override
    public int insertCoinRecord(CoinRecord coinRecord)
    {
        coinRecord.setCreateTime(DateUtils.getNowDate());
        return coinRecordMapper.insertCoinRecord(coinRecord);
    }

    /**
     * 修改金币记录
     * 
     * @param coinRecord 金币记录
     * @return 结果
     */
    @Override
    public int updateCoinRecord(CoinRecord coinRecord)
    {
        coinRecord.setUpdateTime(DateUtils.getNowDate());
        return coinRecordMapper.updateCoinRecord(coinRecord);
    }

    /**
     * 批量删除金币记录
     * 
     * @param ids 需要删除的金币记录主键
     * @return 结果
     */
    @Override
    public int deleteCoinRecordByIds(Long[] ids)
    {
        return coinRecordMapper.deleteCoinRecordByIds(ids);
    }

    /**
     * 删除金币记录信息
     * 
     * @param id 金币记录主键
     * @return 结果
     */
    @Override
    public int deleteCoinRecordById(Long id)
    {
        return coinRecordMapper.deleteCoinRecordById(id);
    }
}
