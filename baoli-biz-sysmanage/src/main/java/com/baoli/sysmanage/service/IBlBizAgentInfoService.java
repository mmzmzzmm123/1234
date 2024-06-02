package com.baoli.sysmanage.service;

import java.util.List;
import com.baoli.sysmanage.domain.BlBizAgentInfo;

/**
 * 经销商档案Service接口
 * 
 * @author niujs
 * @date 2024-03-12
 */
public interface IBlBizAgentInfoService 
{
    /**
     * 查询经销商档案
     * 
     * @param id 经销商档案主键
     * @return 经销商档案
     */
    public BlBizAgentInfo selectBlBizAgentInfoById(Long id);

    /**
     * 查询经销商档案列表
     * 
     * @param blBizAgentInfo 经销商档案
     * @return 经销商档案集合
     */
    public List<BlBizAgentInfo> selectBlBizAgentInfoList(BlBizAgentInfo blBizAgentInfo);

    /**
     * 新增经销商档案
     * 
     * @param blBizAgentInfo 经销商档案
     * @return 结果
     */
    public int insertBlBizAgentInfo(BlBizAgentInfo blBizAgentInfo);

    /**
     * 修改经销商档案
     * 
     * @param blBizAgentInfo 经销商档案
     * @return 结果
     */
    public int updateBlBizAgentInfo(BlBizAgentInfo blBizAgentInfo);

    /**
     * 批量删除经销商档案
     * 
     * @param ids 需要删除的经销商档案主键集合
     * @return 结果
     */
    public int deleteBlBizAgentInfoByIds(Long[] ids);

    /**
     * 删除经销商档案信息
     * 
     * @param id 经销商档案主键
     * @return 结果
     */
    public int deleteBlBizAgentInfoById(Long id);
}
