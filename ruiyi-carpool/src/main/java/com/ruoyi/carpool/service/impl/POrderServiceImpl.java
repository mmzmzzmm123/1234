package com.ruoyi.carpool.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.carpool.domain.POrderMember;
import com.ruoyi.carpool.domain.PPassenger;
import com.ruoyi.carpool.mapper.PPassengerMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.POrderMapper;
import com.ruoyi.carpool.domain.POrder;
import com.ruoyi.carpool.service.IPOrderService;

/**
 * 订单信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@Service
public class POrderServiceImpl implements IPOrderService
{
    @Autowired
    private POrderMapper pOrderMapper;


    @Autowired
    private PPassengerMapper pPassengerMapper;

    /**
     * 查询订单信息
     * 
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public POrder selectPOrderById(Long id)
    {
        return pOrderMapper.selectPOrderById(id);
    }

    /**
     * 查询订单信息列表
     * 
     * @param pOrder 订单信息
     * @return 订单信息
     */
    @Override
    public List<POrder> selectPOrderList(POrder pOrder)
    {
        List<POrder> pOrders = new ArrayList<>();
        if(StringUtils.isNotEmpty(pOrder.getMemberOpenId())){
            pOrders = pOrderMapper.selectPOrderListContentMember(pOrder);
        }else {
            pOrders = pOrderMapper.selectPOrderList(pOrder);
        }
        /*查询成员信息*/
        for (POrder order : pOrders) {
            List<POrderMember> pOrderMembers = pOrderMapper.queryOrderMenberListByOrderNum(order.getOrderNum());
            order.setOrderMemberList(pOrderMembers);
        }
        return pOrders;
    }


    @Override
    public List<POrder> queryOrderInfoByOpenId(String openId) {
        return pOrderMapper.queryOrderInfoByOpenId(openId);
    }

    /**
     * 新增订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    @Override
    public int insertPOrder(POrder pOrder)
    {
        String orderNum = DateUtils.dateTimeNow();
        pOrder.setOrderNum("carpool_"+orderNum);
        pOrder.setIsTake("0");
        pOrder.setState("0");
        pOrder.setCreateTime(DateUtils.getNowDate());
        return pOrderMapper.insertPOrder(pOrder);
    }


    /**
     * 新增订单信息
     *
     * @param pOrder 订单信息
     * @return 结果
     */
    @Override
    public AjaxResult insertPOrderWx(POrder pOrder)
    {
        try {
            /*校验当前用户是否存在未完成的订单*/
            String openId = pOrder.getCreaterOpenId();
            if(StringUtils.isEmpty(openId)) return AjaxResult.error("createrOpenId 必传");
            /*判断当前用户是否是黑名单*/
            PPassenger pPassenger = pPassengerMapper.selectPPassengerByOpenId(openId);
            if("1".equals(pPassenger.getIsBlacklist())) return AjaxResult.error("您现在处于系统黑名单，请联系管理员。");
            /*查询当前用户是否存在拼单中的订单*/
            List<POrder> pOrders = pOrderMapper.queryOrderInfoByOpenIdAndState(openId);
            if(pOrders.size() > 0){
                return AjaxResult.error("您在系统中存在拼单中的订单。");
            }
            String orderNum = DateUtils.dateTimeNow();
            pOrder.setOrderNum("carpool_"+orderNum);
            pOrder.setIsTake("0");
            pOrder.setState("0");
            pOrder.setCreateTime(DateUtils.getNowDate());
            /*订单成员表中添加一条成员信息*/
            POrderMember pOrderMember = new POrderMember();
            pOrderMember.setOrderNum(pOrder.getOrderNum());
            pOrderMember.setOpenId(openId);
            pOrderMember.setState(0);
            pOrderMember.setCustName(pOrder.getCreaterName());
            pOrderMember.setCustPhone(pOrder.getCreaterPhone());
            pOrderMember.setCreateTime(DateUtils.getNowDate());
            pOrderMember.setIndex(1);
            pOrderMember.setNum(pOrder.getMember());
            pOrderMapper.insertPOrder(pOrder);
            pOrderMapper.insertPOrderMember(pOrderMember);
            return AjaxResult.success("发起拼单成功。");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxResult.error(999 ,"发起拼单失败.");
    }



    /**
     * 修改订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    @Override
    public int updatePOrder(POrder pOrder)
    {
        pOrder.setUpdateTime(DateUtils.getNowDate());
        return pOrderMapper.updatePOrder(pOrder);
    }

    /**
     * 批量删除订单信息
     * 
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deletePOrderByIds(Long[] ids)
    {
        return pOrderMapper.deletePOrderByIds(ids);
    }

    /**
     * 删除订单信息信息
     * 
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deletePOrderById(Long id)
    {
        return pOrderMapper.deletePOrderById(id);
    }

    /**
     * 删除订单信息信息
     *
     * @param
     * @return 结果
     */
    @Override
    public AjaxResult takeOrder(POrder pOrder)
    {
        pOrder.setIsTake("1");
        pOrderMapper.takeOrderById(pOrder);

        return null ;

    }


}
