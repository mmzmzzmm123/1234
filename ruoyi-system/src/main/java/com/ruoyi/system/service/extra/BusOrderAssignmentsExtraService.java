package com.ruoyi.system.service.extra;

import com.ruoyi.common.enums.OrderAssignmentStatus;
import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.IBusOrderAssignmentsService;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Past;
import java.util.List;

/**
 * 记录用户接单的信息Service接口
 *
 * @author ruoyi
 * @date 2024-06-18
 */
public interface BusOrderAssignmentsExtraService extends IBusOrderAssignmentsService {

    int createByOrder(BusPostOrder busPostOrder);

    boolean takeOrder(Long userId, int newStatus,int oldStatus, Long assignmentId);
}
