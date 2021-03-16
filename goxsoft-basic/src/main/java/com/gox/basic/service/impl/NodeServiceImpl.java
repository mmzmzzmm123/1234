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
        Node node1 = nodeList.get(0);//结束
        Node node2 = nodeList.get(1);//档案管理员
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


    @Override
    public List<Node> getNodeQx(Long id) {
        Node node = nodeMapper.selectNodeById(id);
        List<Node> list = nodeMapper.selectNodeListByid(node.getWorkid());
        for(int i=0;i<list.size();i++){
            if("启动".equals(list.get(i).getText())){
                list.remove(i);
            }
            if("结束".equals(list.get(i).getText())){
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public List<String> getNexttext(Long id) {
        List<String> nextTextList=new ArrayList<>();
        Node node=nodeMapper.selectNodeById(id);
        nextTextList.add(node.getNexttext());
        return nextTextList;
    }


    @Override
    public int updateQxNode(Node node) {
        Long nodeId = node.getId();
        int rows = nodeMapper.updateNode(node);
        // 删除节点与角色关联
        nodeRoleMapper.deleteNodeRoleByNodeId(nodeId);
        // 新增节点与角色管理
        insertNodeRole(node);
        return rows;
    }

    @Override
    public int deleteNode(Long id) {
        Node node = nodeMapper.selectNodeById(id);
        //获取工作流中所有节点
        List<Node> nodes = nodeMapper.selectNodeListByid(node.getWorkid());
        int j = nodeMapper.deleteNodeById(id);//删除环节
        nodeRoleMapper.deleteNodeRoleByNodeId(id);//删除环节用户对应数据
        int orders = 1;
        for(int i=0;i<nodes.size();i++){//更新相邻环节
            //获取每一个节点信息
            Node node1  = nodes.get(i);
            //判断要删除节点是否等于集合中节点
            if(node.getId().equals(node1.getId())){
                continue;
            }
            if(!"结束".equals(node1.getText())){
                //获取删除节点的所有下一级节点id
                String[] nextIds = node1.getNextid().split(",");
                //节点的下一节点是被删除的节点
                if(node1.getNextid().indexOf(node.getId().toString())>-1){
                    String nextId = (node1.getNextid()==null?"":node1.getNextid()).replace(","+node.getId(),"");
                    nextId = nextId.replace(node.getId()+",","");
                    nextId = nextId.replace(node.getId().toString(),"");
                    node1.setNextid(nextId);
                }
                List<Node> nodes2 = nodeMapper.findByNodeidIn(nextIds);
                String nodeText = "";
                for(Node node2 : nodes2){
                    nodeText += ","+node2.getText();
                }
                if(!"".equals(nodeText)){
                    nodeText = nodeText.substring(1);
                }else{
                    if(nodes.size()>3){
                        nodeText = nodes.get(i+2).getText();
                        node1.setNextid(nodes.get(i+2).getId().toString());
                    }
                }
                node1.setNexttext(nodeText);
            }
            node1.setSort(new Long((long)orders));
            orders++;
           int a=nodeMapper.updateNode(node1);
        }
        return j;
    }
}
