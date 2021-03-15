package com.gox.basic.mapper;

import java.util.List;
import com.gox.basic.domain.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *   Mapper接口
 * 
 * @author gox
 * @date 2021-03-11
 */
public interface NodeMapper 
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
     * 删除  
     * 
     * @param id   ID
     * @return 结果
     */
    public int deleteNodeById(Long id);

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNodeByIds(Long[] ids);

    /**
     * 查询  列表
     *
     * @param
     * @return   集合
     */
    @Select("select * from node where workid=#{workid} limit 0,2")
    public List<Node> selectNodeListByWorkId(Long workid);


    @Update(value = "update node set nextid = #{nextid},nexttext=#{nexttext},sort=#{sort},desci=#{desci} where id=#{id}")
    int updateNodeById(@Param("nextid") String nextid,@Param("nexttext") String nexttext,@Param("sort")  Long sort,@Param("desci") String desci,@Param("id") Long id);

}
