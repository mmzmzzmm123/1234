package com.ruoyi.carpool.mapper;

import java.util.List;
import com.ruoyi.carpool.domain.POrder;
import com.ruoyi.carpool.domain.POrderMember;

/**
 * 订单信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface POrderMapper 
{
    /**
     * 查询订单信息
     * 
     * @param id 订单信息主键
     * @return 订单信息
     */
    public POrder selectPOrderById(Long id);

    /**
     * 查询订单信息列表
     * 
     * @param pOrder 订单信息
     * @return 订单信息集合
     */
    public List<POrder> selectPOrderList(POrder pOrder);

    /**
     * 查询订单信息列表
     *
     * @param pOrder 订单信息
     * @return 订单信息集合
     */
    public List<POrder> selectPOrderListContentMember(POrder pOrder);

    /**
     * 新增订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    public int insertPOrder(POrder pOrder);


    /**
     * 新增订单成员信息
     *
     * @param pOrderMember 订单信息
     * @return 结果
     */
    public int insertPOrderMember(POrderMember pOrderMember);

    /**
     * 修改订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    public int updatePOrder(POrder pOrder);

    /**
     * 删除订单信息
     * 
     * @param id 订单信息主键
     * @return 结果
     */
    public int deletePOrderById(Long id);

    /**
     * 批量删除订单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePOrderByIds(Long[] ids);

    /**
     * 司机接单
     *
     * @param pOrder 订单信息
     * @return 结果
     */
    public int takeOrderById (POrder pOrder);

    /**
     * 通过orderNum查询订单信息
     * @param orderNum
     * @return
     */
    public POrder selectPOrderByOrderNum(String orderNum);


    /**
     * 更新订单当前的人数
     * @param pOrder
     * @return
     */
    public int updateOrderMemberNum(POrder pOrder);

    /**
     * 通过openID查询订单信息
     * @param openId
     * @return
     */
    public List<POrder> queryOrderInfoByOpenId(String openId);

    /**
     * 通过openID查询订单信息
     * @param openId
     * @return
     */
    public List<POrder> queryOrderInfoByOpenIdAndState(String openId);


    public List<POrderMember> queryOrderMenberListByOrderNum(String orderNum);



}
