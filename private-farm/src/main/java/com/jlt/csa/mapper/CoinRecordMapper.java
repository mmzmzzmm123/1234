package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.CoinRecord;

/**
 * 金币记录Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface CoinRecordMapper 
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
     * 删除金币记录
     * 
     * @param id 金币记录主键
     * @return 结果
     */
    public int deleteCoinRecordById(Long id);

    /**
     * 批量删除金币记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCoinRecordByIds(Long[] ids);
}
