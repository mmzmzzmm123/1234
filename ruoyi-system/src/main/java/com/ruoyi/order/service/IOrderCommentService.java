package com.ruoyi.order.service;

import java.util.List;
import com.ruoyi.order.domain.OrderComment;

/**
 * 订单评论管理Service接口
 *
 * @author Lam
 * @date 2023-10-12
 */
public interface IOrderCommentService {

    /**
     * 查询订单评论管理
     *
     * @param id 订单评论管理主键
     * @return 订单评论管理
     */
    public OrderComment selectOrderCommentById(Long id);

    /**
     * 查询订单评论管理列表
     *
     * @param orderComment 订单评论管理
     * @return 订单评论管理集合
     */
    public List<OrderComment> selectOrderCommentList(OrderComment orderComment);

    /**
     * 新增订单评论管理
     *
     * @param orderComment 订单评论管理
     * @return 结果
     */
    public int insertOrderComment(OrderComment orderComment);

    /**
     * 修改订单评论管理
     *
     * @param orderComment 订单评论管理
     * @return 结果
     */
    public int updateOrderComment(OrderComment orderComment);

    /**
     * 批量删除订单评论管理
     *
     * @param ids 需要删除的订单评论管理主键集合
     * @return 结果
     */
    public int deleteOrderCommentByIds(Long[] ids);

    /**
     * 删除订单评论管理信息
     *
     * @param id 订单评论管理主键
     * @return 结果
     */
    public int deleteOrderCommentById(Long id);
}
