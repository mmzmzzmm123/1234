package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayFlowDetailMapper;
import com.ruoyi.project.benyi.domain.ByDayFlowDetail;
import com.ruoyi.project.benyi.service.IByDayFlowDetailService;

/**
 * 一日流程Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-07
 */
@Service
public class ByDayFlowDetailServiceImpl implements IByDayFlowDetailService 
{
    @Autowired
    private ByDayFlowDetailMapper byDayFlowDetailMapper;

    /**
     * 查询一日流程
     * 
     * @param id 一日流程ID
     * @return 一日流程
     */
    @Override
    public ByDayFlowDetail selectByDayFlowDetailById(Long id)
    {
        return byDayFlowDetailMapper.selectByDayFlowDetailById(id);
    }

    /**
     * 查询一日流程列表
     * 
     * @param byDayFlowDetail 一日流程
     * @return 一日流程
     */
    @Override
    public List<ByDayFlowDetail> selectByDayFlowDetailList(ByDayFlowDetail byDayFlowDetail)
    {
        return byDayFlowDetailMapper.selectByDayFlowDetailList(byDayFlowDetail);
    }

    /**
     * 新增一日流程
     * 
     * @param byDayFlowDetail 一日流程
     * @return 结果
     */
    @Override
    public int insertByDayFlowDetail(ByDayFlowDetail byDayFlowDetail)
    {
        byDayFlowDetail.setCreateTime(DateUtils.getNowDate());
        return byDayFlowDetailMapper.insertByDayFlowDetail(byDayFlowDetail);
    }

    /**
     * 修改一日流程
     * 
     * @param byDayFlowDetail 一日流程
     * @return 结果
     */
    @Override
    public int updateByDayFlowDetail(ByDayFlowDetail byDayFlowDetail)
    {
        byDayFlowDetail.setUpdateTime(DateUtils.getNowDate());
        return byDayFlowDetailMapper.updateByDayFlowDetail(byDayFlowDetail);
    }

    /**
     * 批量删除一日流程
     * 
     * @param ids 需要删除的一日流程ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowDetailByIds(Long[] ids)
    {
        return byDayFlowDetailMapper.deleteByDayFlowDetailByIds(ids);
    }

    /**
     * 删除一日流程信息
     * 
     * @param id 一日流程ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowDetailById(Long id)
    {
        return byDayFlowDetailMapper.deleteByDayFlowDetailById(id);
    }
}
