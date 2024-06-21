package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BusOrderAssignmentsMapper;
import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.service.IBusOrderAssignmentsService;

/**
 * 记录用户接单的信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-20
 */
@Service
public class BusOrderAssignmentsServiceImpl implements IBusOrderAssignmentsService 
{
    @Autowired
    private BusOrderAssignmentsMapper busOrderAssignmentsMapper;

    /**
     * 查询记录用户接单的信息
     * 
     * @param assignmentId 记录用户接单的信息主键
     * @return 记录用户接单的信息
     */
    @Override
    public BusOrderAssignments selectBusOrderAssignmentsByAssignmentId(Long assignmentId)
    {
        return busOrderAssignmentsMapper.selectBusOrderAssignmentsByAssignmentId(assignmentId);
    }

    /**
     * 查询记录用户接单的信息列表
     * 
     * @param busOrderAssignments 记录用户接单的信息
     * @return 记录用户接单的信息
     */
    @Override
    public List<BusOrderAssignments> selectBusOrderAssignmentsList(BusOrderAssignments busOrderAssignments)
    {
        return busOrderAssignmentsMapper.selectBusOrderAssignmentsList(busOrderAssignments);
    }

    /**
     * 新增记录用户接单的信息
     * 
     * @param busOrderAssignments 记录用户接单的信息
     * @return 结果
     */
    @Override
    public int insertBusOrderAssignments(BusOrderAssignments busOrderAssignments)
    {
        return busOrderAssignmentsMapper.insertBusOrderAssignments(busOrderAssignments);
    }

    /**
     * 修改记录用户接单的信息
     * 
     * @param busOrderAssignments 记录用户接单的信息
     * @return 结果
     */
    @Override
    public int updateBusOrderAssignments(BusOrderAssignments busOrderAssignments)
    {
        return busOrderAssignmentsMapper.updateBusOrderAssignments(busOrderAssignments);
    }

    /**
     * 批量删除记录用户接单的信息
     * 
     * @param assignmentIds 需要删除的记录用户接单的信息主键
     * @return 结果
     */
    @Override
    public int deleteBusOrderAssignmentsByAssignmentIds(Long[] assignmentIds)
    {
        return busOrderAssignmentsMapper.deleteBusOrderAssignmentsByAssignmentIds(assignmentIds);
    }

    /**
     * 删除记录用户接单的信息信息
     * 
     * @param assignmentId 记录用户接单的信息主键
     * @return 结果
     */
    @Override
    public int deleteBusOrderAssignmentsByAssignmentId(Long assignmentId)
    {
        return busOrderAssignmentsMapper.deleteBusOrderAssignmentsByAssignmentId(assignmentId);
    }
}
