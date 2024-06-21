package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BusOrderAssignments;

/**
 * 记录用户接单的信息Service接口
 * 
 * @author ruoyi
 * @date 2024-06-20
 */
public interface IBusOrderAssignmentsService 
{
    /**
     * 查询记录用户接单的信息
     * 
     * @param assignmentId 记录用户接单的信息主键
     * @return 记录用户接单的信息
     */
    public BusOrderAssignments selectBusOrderAssignmentsByAssignmentId(Long assignmentId);

    /**
     * 查询记录用户接单的信息列表
     * 
     * @param busOrderAssignments 记录用户接单的信息
     * @return 记录用户接单的信息集合
     */
    public List<BusOrderAssignments> selectBusOrderAssignmentsList(BusOrderAssignments busOrderAssignments);

    /**
     * 新增记录用户接单的信息
     * 
     * @param busOrderAssignments 记录用户接单的信息
     * @return 结果
     */
    public int insertBusOrderAssignments(BusOrderAssignments busOrderAssignments);

    /**
     * 修改记录用户接单的信息
     * 
     * @param busOrderAssignments 记录用户接单的信息
     * @return 结果
     */
    public int updateBusOrderAssignments(BusOrderAssignments busOrderAssignments);

    /**
     * 批量删除记录用户接单的信息
     * 
     * @param assignmentIds 需要删除的记录用户接单的信息主键集合
     * @return 结果
     */
    public int deleteBusOrderAssignmentsByAssignmentIds(Long[] assignmentIds);

    /**
     * 删除记录用户接单的信息信息
     * 
     * @param assignmentId 记录用户接单的信息主键
     * @return 结果
     */
    public int deleteBusOrderAssignmentsByAssignmentId(Long assignmentId);
}
