package com.ruoyi.custom.service;

import java.util.List;
import com.ruoyi.custom.domain.SysCommision;

/**
 * 业务提成比例Service接口
 * 
 * @author wonder
 * @date 2020-09-24
 */
public interface ISysCommisionService 
{
    /**
     * 查询业务提成比例
     * 
     * @param ruleId 业务提成比例ID
     * @return 业务提成比例
     */
    public SysCommision selectSysCommisionById(Long ruleId);

    /**
     * 查询业务提成比例列表
     * 
     * @param sysCommision 业务提成比例
     * @return 业务提成比例集合
     */
    public List<SysCommision> selectSysCommisionList(SysCommision sysCommision);

    /**
     * 新增业务提成比例
     * 
     * @param sysCommision 业务提成比例
     * @return 结果
     */
    public int insertSysCommision(SysCommision sysCommision);

    /**
     * 修改业务提成比例
     * 
     * @param sysCommision 业务提成比例
     * @return 结果
     */
    public int updateSysCommision(SysCommision sysCommision);

    /**
     * 批量删除业务提成比例
     * 
     * @param ruleIds 需要删除的业务提成比例ID
     * @return 结果
     */
    public int deleteSysCommisionByIds(Long[] ruleIds);

    /**
     * 删除业务提成比例信息
     * 
     * @param ruleId 业务提成比例ID
     * @return 结果
     */
    public int deleteSysCommisionById(Long ruleId);
}
