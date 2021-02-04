package com.gox.basic.service;

import com.gox.basic.domain.Agent;

import java.util.List;

/**
 * 机构人员Service接口
 * 
 * @author gox
 * @date 2020-12-29
 */
public interface IAgentService 
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
    public List<Agent> selectAgentList(Agent agent);

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
     * 批量删除机构人员
     * 
     * @param agentIdentifiers 需要删除的机构人员ID
     * @return 结果
     */
    public int deleteAgentByIds(Long[] agentIdentifiers);

    /**
     * 删除机构人员信息
     * 
     * @param agentIdentifier 机构人员ID
     * @return 结果
     */
    public int deleteAgentById(Long agentIdentifier);
}
