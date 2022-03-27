package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.CoinRecord;

/**
 * 金币记录Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface ICoinRecordService 
{
    /**
     * 查询金币记录
     * 
     * @param id 金币记录主键
     * @return 金币记录
     */
    public CoinRecord selectCoinRecordById(Long id);

    /**
     * 查询金币记录列表
     * 
     * @param coinRecord 金币记录
     * @return 金币记录集合
     */
    public List<CoinRecord> selectCoinRecordList(CoinRecord coinRecord);

    /**
     * 新增金币记录
     * 
     * @param coinRecord 金币记录
     * @return 结果
     */
    public int insertCoinRecord(CoinRecord coinRecord);

    /**
     * 修改金币记录
     * 
     * @param coinRecord 金币记录
     * @return 结果
     */
    public int updateCoinRecord(CoinRecord coinRecord);

    /**
     * 批量删除金币记录
     * 
     * @param ids 需要删除的金币记录主键集合
     * @return 结果
     */
    public int deleteCoinRecordByIds(Long[] ids);

    /**
     * 删除金币记录信息
     * 
     * @param id 金币记录主键
     * @return 结果
     */
    public int deleteCoinRecordById(Long id);
}
