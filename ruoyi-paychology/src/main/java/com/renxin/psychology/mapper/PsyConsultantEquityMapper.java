package com.renxin.psychology.mapper;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantEquity;

/**
 * 咨询师权益Mapper接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface PsyConsultantEquityMapper 
{
    /**
     * 查询咨询师权益
     * 
     * @param id 咨询师权益主键
     * @return 咨询师权益
     */
    public PsyConsultantEquity selectPsyConsultantEquityById(Long id);

    /**
     * 查询咨询师权益列表
     * 
     * @param psyConsultantEquity 咨询师权益
     * @return 咨询师权益集合
     */
    public List<PsyConsultantEquity> selectPsyConsultantEquityList(PsyConsultantEquity psyConsultantEquity);

    /**
     * 新增咨询师权益
     * 
     * @param psyConsultantEquity 咨询师权益
     * @return 结果
     */
    public int insertPsyConsultantEquity(PsyConsultantEquity psyConsultantEquity);

    /**
     * 修改咨询师权益
     * 
     * @param psyConsultantEquity 咨询师权益
     * @return 结果
     */
    public int updatePsyConsultantEquity(PsyConsultantEquity psyConsultantEquity);

    /**
     * 删除咨询师权益
     * 
     * @param id 咨询师权益主键
     * @return 结果
     */
    public int deletePsyConsultantEquityById(Long id);

    /**
     * 批量删除咨询师权益
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantEquityByIds(Long[] ids);
}
