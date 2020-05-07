package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByDayFlowDetail;

/**
 * 一日流程Service接口
 * 
 * @author tsbz
 * @date 2020-05-07
 */
public interface IByDayFlowDetailService 
{
    /**
     * 查询一日流程
     * 
     * @param id 一日流程ID
     * @return 一日流程
     */
    public ByDayFlowDetail selectByDayFlowDetailById(Long id);

    /**
     * 查询一日流程列表
     * 
     * @param byDayFlowDetail 一日流程
     * @return 一日流程集合
     */
    public List<ByDayFlowDetail> selectByDayFlowDetailList(ByDayFlowDetail byDayFlowDetail);

    /**
     * 新增一日流程
     * 
     * @param byDayFlowDetail 一日流程
     * @return 结果
     */
    public int insertByDayFlowDetail(ByDayFlowDetail byDayFlowDetail);

    /**
     * 修改一日流程
     * 
     * @param byDayFlowDetail 一日流程
     * @return 结果
     */
    public int updateByDayFlowDetail(ByDayFlowDetail byDayFlowDetail);

    /**
     * 批量删除一日流程
     * 
     * @param ids 需要删除的一日流程ID
     * @return 结果
     */
    public int deleteByDayFlowDetailByIds(Long[] ids);

    /**
     * 删除一日流程信息
     * 
     * @param id 一日流程ID
     * @return 结果
     */
    public int deleteByDayFlowDetailById(Long id);
}
