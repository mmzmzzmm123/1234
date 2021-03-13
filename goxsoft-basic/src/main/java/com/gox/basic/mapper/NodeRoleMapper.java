package com.gox.basic.mapper;

import java.util.List;
import com.gox.basic.domain.NodeRole;
import com.gox.system.domain.SysUserRole;

/**
 *   Mapper接口
 * 
 * @author gox
 * @date 2021-03-13
 */
public interface NodeRoleMapper 
{
    /**
     * 查询  
     * 
     * @param nodeid   ID
     * @return   
     */
    public NodeRole selectNodeRoleById(Long nodeid);

    /**
     * 查询  列表
     * 
     * @param nodeRole   
     * @return   集合
     */
    public List<NodeRole> selectNodeRoleList(NodeRole nodeRole);


    /**
     * 通过节点ID删除节点和角色关联
     *
     * @param nodeId 节点ID
     * @return 结果
     */
    public int deleteNodeRoleByNodeId(Long nodeId);

    /**
     * 新增  
     * 
     * @param nodeRole   
     * @return 结果
     */
    public int insertNodeRole(NodeRole nodeRole);

    /**
     * 批量新增节点角色信息
     *
     * @param nodeRoles 用户角色列表
     * @return 结果
     */
    public int batchNodeRole(List<NodeRole> nodeRoles);

    /**
     * 修改  
     * 
     * @param nodeRole   
     * @return 结果
     */
    public int updateNodeRole(NodeRole nodeRole);

    /**
     * 删除  
     * 
     * @param nodeid   ID
     * @return 结果
     */
    public int deleteNodeRoleById(Long nodeid);

    /**
     * 批量删除  
     * 
     * @param nodeids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNodeRoleByIds(Long[] nodeids);
}
