package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.OrderCommentMapper;
import com.ruoyi.order.domain.OrderComment;
import com.ruoyi.order.service.IOrderCommentService;

/**
 * 订单评论管理Service业务层处理
 *
 * @author Lam
 * @date 2023-10-12
 */
@Service
@RequiredArgsConstructor
public class OrderCommentServiceImpl implements IOrderCommentService {

    private final OrderCommentMapper orderCommentMapper;

    /**
     * 查询订单评论管理
     *
     * @param id 订单评论管理主键
     * @return 订单评论管理
     */
    @Override
    public OrderComment selectOrderCommentById(Long id) {
        return orderCommentMapper.selectOrderCommentById(id);
    }

    /**
     * 查询订单评论管理列表
     *
     * @param orderComment 订单评论管理
     * @return 订单评论管理
     */
    @Override
    public List<OrderComment> selectOrderCommentList(OrderComment orderComment) {
        return orderCommentMapper.selectOrderCommentList(orderComment);
    }

    /**
     * 新增订单评论管理
     *
     * @param orderComment 订单评论管理
     * @return 结果
     */
    @Override
    public int insertOrderComment(OrderComment orderComment) {
        orderComment.setCreateTime(DateUtils.getNowDate());
        return orderCommentMapper.insertOrderComment(orderComment);
    }

    /**
     * 修改订单评论管理
     *
     * @param orderComment 订单评论管理
     * @return 结果
     */
    @Override
    public int updateOrderComment(OrderComment orderComment) {
        return orderCommentMapper.updateOrderComment(orderComment);
    }

    /**
     * 批量删除订单评论管理
     *
     * @param ids 需要删除的订单评论管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderCommentByIds(Long[] ids) {
        return orderCommentMapper.deleteOrderCommentByIds(ids);
    }

    /**
     * 删除订单评论管理信息
     *
     * @param id 订单评论管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderCommentById(Long id) {
        return orderCommentMapper.deleteOrderCommentById(id);
    }
}
