package com.ruoyi.course.mapper;

import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.vo.OrderVO;

import java.util.List;

/**
 * 课程订单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
public interface CourOrderMapper 
{
    /**
     * 查询课程订单
     * 
     * @param id 课程订单主键
     * @return 课程订单
     */
    public CourOrder selectCourOrderById(Long id);

    /**
     * 查询课程订单列表
     * 
     * @param courOrder 课程订单
     * @return 课程订单集合
     */
    public List<CourOrder> selectCourOrderList(CourOrder courOrder);

    /**
     * 新增课程订单
     * 
     * @param courOrder 课程订单
     * @return 结果
     */
    public int insertCourOrder(CourOrder courOrder);

    /**
     * 修改课程订单
     * 
     * @param courOrder 课程订单
     * @return 结果
     */
    public int updateCourOrder(CourOrder courOrder);

    /**
     * 删除课程订单
     * 
     * @param id 课程订单主键
     * @return 结果
     */
    public int deleteCourOrderById(Long id);

    /**
     * 批量删除课程订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourOrderByIds(Long[] ids);

    /**
     * 根据订单编号查询课程订单详情
     *
     * @param orderId 课程订单编号
     * @return 课程订单详情
     */
    public OrderVO getOrderDetailByOrderId(String orderId);
}
