package com.ruoyi.system.service.impl.extra;

import com.ruoyi.common.enums.OrderAssignmentStatus;
import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.mapper.extra.BusOrderAssignmentsExtraMapper;
import com.ruoyi.system.service.extra.BusOrderAssignmentsExtraService;
import com.ruoyi.system.service.impl.BusOrderAssignmentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-19 15:55:50
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-19     张李鑫                     1.0         1.0 Version
 */
@Primary
@Service
public class BusOrderAssignmentsExtraServiceImpl extends BusOrderAssignmentsServiceImpl implements BusOrderAssignmentsExtraService {


    @Autowired
    private BusOrderAssignmentsExtraMapper busOrderAssignmentsExtraMapper;


    /**
     * 在生成订单的时候同时生成接单数据 行锁保证线程安全
     *
     * @param busPostOrder
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createByOrder(BusPostOrder busPostOrder) {
        BusOrderAssignments busOrderAssignments = new BusOrderAssignments();
        busOrderAssignments.setOrderId(busPostOrder.getOrderId());
        busOrderAssignments.setStatus(OrderAssignmentStatus.UNACCEPTED.getValue());
        return insertBusOrderAssignments(busOrderAssignments);
    }

    /**
     * 抢单
     *
     * @param userId
     * @param newStatus
     * @param oldStatus
     * @param assignmentId
     * @return
     */
    @Override
    public boolean takeOrder(Long userId, int newStatus, int oldStatus, Long assignmentId) {
        return busOrderAssignmentsExtraMapper.setUserIdAndUpdateStatusById(userId, newStatus, oldStatus, assignmentId) > 0;
    }

    @Override
    public BusOrderAssignments selectByOrderId(Long orderId) {
        List<BusOrderAssignments> busOrderAssignments = busOrderAssignmentsExtraMapper.selectByOrderId(orderId);
        if (busOrderAssignments.size()==1){
            return busOrderAssignments.get(0);
        }
        throw new RuntimeException("订单异常");
    }


    @Override
    public int insertBusOrderAssignments(BusOrderAssignments busOrderAssignments) {
        busOrderAssignments.setCreatedAt(new Date());
        busOrderAssignments.setCreateTime(new Date());
        return super.insertBusOrderAssignments(busOrderAssignments);
    }

    @Override
    public int updateBusOrderAssignments(BusOrderAssignments busOrderAssignments) {
        busOrderAssignments.setCreateTime(new Date());
        return super.updateBusOrderAssignments(busOrderAssignments);
    }
}
