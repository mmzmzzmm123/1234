package com.ruoyi.system.service;

import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.domain.BusPostOrder;

import java.util.List;

/**
 * 记录用户接单的信息Service接口
 *
 * @author ruoyi
 * @date 2024-06-18
 */
public interface BusOrderAssignmentsExtraService extends IBusOrderAssignmentsService {

    int createByOrder(BusPostOrder busPostOrder);
}
