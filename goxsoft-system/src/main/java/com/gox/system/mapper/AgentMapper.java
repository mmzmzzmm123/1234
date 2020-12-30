package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.Agent;

/**
 * 机构人员Mapper接口
 * 
 * @author gox
 * @date 2020-12-29
 */
public interface AgentMapper 
{
    /**
     * 查询机构人员
     * 
     * @param agentIdentifier 机构人员ID
     * @return 机构人员
     */
    public Agent selectAgentById(Long agentIdentifier);

    /**
     * 查询机构人员列表
     * 
     * @param agent 机构人员
     * @return 机构人员集合
     */
    List<Agent> selectAgentList(Agent agent);

    /**
     * 新增机构人员
     * 
     * @param agent 机构人员
     * @return 结果
     */
    public int insertAgent(Agent agent);

    /**
     * 修改机构人员
     * 
     * @param agent 机构人员
     * @return 结果
     */
    public int updateAgent(Agent agent);

    /**
     * 删除机构人员
     * 
     * @param agentIdentifier 机构人员ID
     * @return 结果
     */
    public int deleteAgentById(Long agentIdentifier);

    /**
     * 批量删除机构人员
     * 
     * @param agentIdentifiers 需要删除的数据ID
     * @return 结果
     */
    public int deleteAgentByIds(Long[] agentIdentifiers);
}
