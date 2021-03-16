package com.gox.basic.service;

import java.util.List;
import com.gox.basic.domain.Node;

/**
 *   Service接口
 * 
 * @author gox
 * @date 2021-03-11
 */
public interface INodeService 
{
    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    public Node selectNodeById(Long id);

    /**
     * 查询  列表
     * 
     * @param node   
     * @return   集合
     */
    public List<Node> selectNodeList(Node node);

    /**
     * 新增  
     * 
     * @param node   
     * @return 结果
     */
    public int insertNode(Node node);

    /**
     * 修改  
     * 
     * @param node   
     * @return 结果
     */
    public int updateNode(Node node);

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteNodeByIds(Long[] ids);

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    public int deleteNodeById(Long id);


    /**
     * 设置节点权限
     * @param id
     * @return
     */
    public List<Node> getNodeQx(Long id);

    /**
     * 查看下一节点集合
     * @param id
     * @return
     */
    public List<String> getNexttext(Long id);


    /**
     * 设置节点权限
     *
     * @param node
     * @return 结果
     */
    public int updateQxNode(Node node);


    public int deleteNode(Long id);
}
