package com.jlt.crm.mapper;

import java.util.List;
import com.jlt.crm.domain.Visitor;

/**
 * 访客信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-02-08
 */
public interface VisitorMapper
{
    /**
     * 查询访客信息
     * 
     * @param visitorId 访客信息主键
     * @return 访客信息
     */
    public Visitor selectVisitorByVisitorId(Long visitorId);

    /**
     * 查询访客信息列表
     * 
     * @param Visitor 访客信息
     * @return 访客信息集合
     */
    public List<Visitor> selectVisitorList(Visitor Visitor);

    /**
     * 新增访客信息
     * 
     * @param Visitor 访客信息
     * @return 结果
     */
    public int insertVisitor(Visitor Visitor);

    /**
     * 修改访客信息
     * 
     * @param Visitor 访客信息
     * @return 结果
     */
    public int updateVisitor(Visitor Visitor);

    /**
     * 删除访客信息
     * 
     * @param visitorId 访客信息主键
     * @return 结果
     */
    public int deleteVisitorByVisitorId(Long visitorId);

    /**
     * 批量删除访客信息
     * 
     * @param visitorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitorByVisitorIds(Long[] visitorIds);
}
