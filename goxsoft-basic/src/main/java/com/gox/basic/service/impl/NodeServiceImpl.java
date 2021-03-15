package com.gox.basic.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gox.basic.domain.NodeRole;
import com.gox.basic.mapper.NodeRoleMapper;
import com.gox.common.core.domain.entity.SysUser;
import com.gox.common.utils.StringUtils;
import com.gox.system.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.NodeMapper;
import com.gox.basic.domain.Node;
import com.gox.basic.service.INodeService;

/**
 *   Service业务层处理
 * 
 * @author gox
 * @date 2021-03-11
 */
@Service
public class NodeServiceImpl implements INodeService 
{
    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private NodeRoleMapper nodeRoleMapper;

    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    @Override
    public Node selectNodeById(Long id)
    {
        return nodeMapper.selectNodeById(id);
    }

    /**
     * 查询  列表
     * 
     * @param node   
     * @return   
     */
    @Override
    public List<Node> selectNodeList(Node node)
    {
        return nodeMapper.selectNodeList(node);
    }

    /**
     * 新增  
     * 
     * @param node   
     * @return 结果
     */
    @Override
    public int insertNode(Node node)
    {
        List<Node> nodeList=nodeMapper.selectNodeListByWorkId(node.getWorkid());
        Node node1 = nodeList.get(1);
        Node node2 = nodeList.get(0);
        node.setSort(node1.getSort());
        node.setNextid(node1.getId().toString());
        node.setNexttext(node1.getText());
        int rows=nodeMapper.insertNode(node);
        nodeMapper.updateNodeById(node1.getNextid(),node1.getNexttext(),node1.getSort()+1,node1.getDesci(),node1.getId());//更新上一节点
        nodeMapper.updateNodeById(node.getId().toString(),node.getText(),node2.getSort(),node2.getDesci(),node2.getId());
        return rows;
    }

    /**
     * 修改  
     * 
     * @param node   
     * @return 结果
     */
    @Override
    public int updateNode(Node node)
    {
        Long nodeId = node.getId();
        int rows = nodeMapper.updateNode(node);
        // 删除节点与角色关联
        nodeRoleMapper.deleteNodeRoleByNodeId(nodeId);
        // 新增节点与角色管理
        insertNodeRole(node);
        return rows;
    }

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteNodeByIds(Long[] ids)
    {
        for (Long id : ids) {
            // 删除节点与角色关联
            nodeRoleMapper.deleteNodeRoleByNodeId(id);
        }
        return nodeMapper.deleteNodeByIds(ids);
    }

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteNodeById(Long id)
    {
        return nodeMapper.deleteNodeById(id);
    }

    /**
     * 新增用户角色信息
     *
     * @param node 节点对象
     */
    public void insertNodeRole(Node node) {
        Long[] roles = node.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增节点与角色管理
            List<NodeRole> list = new ArrayList<NodeRole>();
            for (Long roleId : roles) {
                NodeRole ur = new NodeRole();
                ur.setId(node.getId());
                ur.setRoleid(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                nodeRoleMapper.batchNodeRole(list);
            }
        }
    }
}
