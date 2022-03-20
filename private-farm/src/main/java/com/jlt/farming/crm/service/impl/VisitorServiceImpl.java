package com.jlt.farming.crm.service.impl;

import java.util.List;

import com.jlt.farming.crm.domain.Visitor;
import com.jlt.farming.crm.mapper.VisitorMapper;
import com.jlt.farming.crm.service.IVisitorService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 访客信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-02-08
 */
@Service
public class VisitorServiceImpl implements IVisitorService
{
    @Autowired
    private VisitorMapper VisitorMapper;

    /**
     * 查询访客信息
     * 
     * @param visitorId 访客信息主键
     * @return 访客信息
     */
    @Override
    public Visitor selectVisitorByVisitorId(Long visitorId)
    {
        return VisitorMapper.selectVisitorByVisitorId(visitorId);
    }

    /**
     * 查询访客信息列表
     * 
     * @param Visitor 访客信息
     * @return 访客信息
     */
    @Override
    public List<Visitor> selectVisitorList(Visitor Visitor)
    {
        return VisitorMapper.selectVisitorList(Visitor);
    }

    /**
     * 新增访客信息
     * 
     * @param Visitor 访客信息
     * @return 结果
     */
    @Override
    public int insertVisitor(Visitor Visitor)
    {
        Visitor.setCreateTime(DateUtils.getNowDate());
        return VisitorMapper.insertVisitor(Visitor);
    }

    /**
     * 修改访客信息
     * 
     * @param Visitor 访客信息
     * @return 结果
     */
    @Override
    public int updateVisitor(Visitor Visitor)
    {
        Visitor.setUpdateTime(DateUtils.getNowDate());
        return VisitorMapper.updateVisitor(Visitor);
    }

    /**
     * 批量删除访客信息
     * 
     * @param visitorIds 需要删除的访客信息主键
     * @return 结果
     */
    @Override
    public int deleteVisitorByVisitorIds(Long[] visitorIds)
    {
        return VisitorMapper.deleteVisitorByVisitorIds(visitorIds);
    }

    /**
     * 删除访客信息信息
     * 
     * @param visitorId 访客信息主键
     * @return 结果
     */
    @Override
    public int deleteVisitorByVisitorId(Long visitorId)
    {
        return VisitorMapper.deleteVisitorByVisitorId(visitorId);
    }
}
