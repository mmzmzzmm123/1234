package com.ruoyi.carpool.mapper;


import com.ruoyi.carpool.domain.CommonVO;
import com.ruoyi.carpool.domain.PDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PCommonMapper {

    /**
     * 查询订单是有司机接单
     * @param orderNum
     * @return
     */
    public String queryIsTake(String orderNum);

    /**
     * 查询乘客下单的状态
     * @param commonVO
     * @return
     */
    public Map queryOrderMemberState(CommonVO commonVO);

    /**
     * 乘客取消订单
     * @param commonVO
     * @return
     */
    public int  updateOrderMenber(CommonVO commonVO);


    /**
     * 查询订单乘客详情
     * @param orderNum
     * @return
     */
    public int queryOrderMemberCount(String orderNum);


    /**
     * 司机取消订单-取消拼单
     * @param orderNum
     * @return
     */
    public int updateOrderState(String orderNum);

    /**
     * 司机取消订单-取消拼单
     * @param orderNum
     * @return
     */
    public int updateOrderIsTake(String orderNum);


    /**
     * 添加一条拼单记录
     * @param orderNum
     * @return
     */
    public int insertOrderMemberInfo(Map<String,Object> orderMember);



}
