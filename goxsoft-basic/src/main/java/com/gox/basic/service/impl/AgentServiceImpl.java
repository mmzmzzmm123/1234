package com.gox.basic.service.impl;

import com.gox.basic.domain.Agent;
import com.gox.basic.mapper.AgentMapper;
import com.gox.basic.service.IAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构人员Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class AgentServiceImpl implements IAgentService 
{
    @Autowired
    private AgentMapper agentMapper;

    /**
     * 查询机构人员
     * 
     * @param agentIdentifier 机构人员ID
     * @return 机构人员
     */
    @Override
    public Agent selectAgentById(Long agentIdentifier)
    {
        return agentMapper.selectAgentById(agentIdentifier);
    }

    /**
     * 查询机构人员列表
     * 
     * @param agent 机构人员
     * @return 机构人员
     */
    @Override
    public List<Agent> selectAgentList(Agent agent)
    {
        return agentMapper.selectAgentList(agent);
    }

    /**
     * 新增机构人员
     * 
     * @param agent 机构人员
     * @return 结果
     */
    @Override
    public int insertAgent(Agent agent)
    {
        return agentMapper.insertAgent(agent);
    }

    /**
     * 修改机构人员
     * 
     * @param agent 机构人员
     * @return 结果
     */
    @Override
    public int updateAgent(Agent agent)
    {
        return agentMapper.updateAgent(agent);
    }

    /**
     * 批量删除机构人员
     * 
     * @param agentIdentifiers 需要删除的机构人员ID
     * @return 结果
     */
    @Override
    public int deleteAgentByIds(Long[] agentIdentifiers)
    {
        return agentMapper.deleteAgentByIds(agentIdentifiers);
    }

    /**
     * 删除机构人员信息
     * 
     * @param agentIdentifier 机构人员ID
     * @return 结果
     */
    @Override
    public int deleteAgentById(Long agentIdentifier)
    {
        return agentMapper.deleteAgentById(agentIdentifier);
    }
}
