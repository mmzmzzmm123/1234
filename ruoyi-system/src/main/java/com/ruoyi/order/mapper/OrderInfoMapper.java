package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.OrderInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 订单信息Mapper接口
 *
 * @author Lam
 * @date 2023-09-15
 */
public interface OrderInfoMapper {

    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 根据订单编号查询订单
     *
     * @param orderNo 订单编号
     * @return 结果
     * */
    public OrderInfo selectByOrderNo(String orderNo);

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 级联所有关系数据
     *
     * @param orderInfo 数据
     * @return 结果
     * */
    public List<OrderInfo> selectJoinAll(OrderInfo orderInfo);


    /**
     * 根据用户标识和店员标识和状态集合查询id集合
     *
     * @param stateList 状态集合
     * @param userId 用户标识
     * @param staffUserId 店员标识
     * @return id集合
     * */
    public List<Long> selectIdByUserIdAndStaffUserIdAndStateList(@Param("stateList")List<String> stateList, @Param("userId") Long userId, @Param("staffUserId") Long staffUserId);

    /**
     * 根据店员标识查询用户标识集合
     *
     * @param staffUserId 店员标识
     * @return 结果
     * */
    public List<Long> selectUserIdByStaffUserId(Long staffUserId);

    /**
     * 根据用户标识查询服务过的店员标识
     *
     * @param customUserId 客户标识
     * @return 结果
     * */
    public List<Long> selectStaffUserIdByCustomUserId(Long customUserId);

    /**
     * 根据店员标识与状态集合查询id集合
     *
     * @param staffUserId 店员标识
     * @param orderType 订单类型
     * @param stateList 状态集合
     * @return 结果
     * */
    public List<Long> selectIdByStaffIdAndTypeAndStateList(@Param("staffUserId") Long staffUserId, @Param("orderType") String orderType, @Param("stateList") List<String> stateList);

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 删除订单信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);
}
