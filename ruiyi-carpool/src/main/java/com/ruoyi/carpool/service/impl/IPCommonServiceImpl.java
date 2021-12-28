package com.ruoyi.carpool.service.impl;

import com.ruoyi.carpool.domain.*;
import com.ruoyi.carpool.mapper.*;
import com.ruoyi.carpool.service.IPCommonService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IPCommonServiceImpl implements IPCommonService {
    private static Logger logger = LoggerFactory.getLogger(IPCommonServiceImpl.class);


    @Autowired
    private PCommonMapper pCommonMapper;

    @Autowired
    private POrderMapper pOrderMapper;

    @Autowired
    private PPassengerMapper pPassengerMapper;

    @Autowired
    private PDriverMapper pDriverMapper ;


    @Override
    public AjaxResult cancelOrder(CommonVO commonVO) {
        String orderNum = commonVO.getOrderNum();
        String userId = commonVO.getUserID();
        if (StringUtils.isEmpty(orderNum) && StringUtils.isEmpty(userId) ){
            return AjaxResult.error("请求参数错误！");
        }
        /*乘客取消订单*/
        if(userId.contains("passenger_")){
            /*是否接单：1：已接单，0：未接单*/
            String state = pCommonMapper.queryIsTake(orderNum);
            if("1".equals(state)){
                logger.info("订单：" + orderNum+" 已经有司机接单，请联系司机。");
                return AjaxResult.error("订单已经有司机接单，请联系司机。");
            }else {
                /*校验是否可以取消*/
                Map<String , Integer> info =  pCommonMapper.queryOrderMemberState(commonVO);
                if(info.isEmpty() && info.get("state") == 0){
                    /*查询订单详情*/
                    POrder pOrder = pOrderMapper.selectPOrderByOrderNum(orderNum);
                    Integer currnt = pOrder.getMember()-info.get("num");
                    pOrder.setMember(currnt);
                    /*更新当前人数表*/
                    pOrderMapper.updateOrderMemberNum(pOrder);
                    /*更新订单成员信息表*/
                    pCommonMapper.updateOrderMenber(commonVO);
                    return AjaxResult.success();
                }
                return AjaxResult.error();
            }
        }
        /*司机取消订单*/
        if(userId.contains("driver_")){
             Integer count  = pCommonMapper.queryOrderMemberCount(orderNum);
            if( count > 0 ){
                logger.info("订单：" + orderNum+" 已经有乘客，请联系乘客。");
                pCommonMapper.updateOrderIsTake(orderNum);
                return AjaxResult.success("订单已经取消，请及时联系乘客。");
            }else {
                pCommonMapper.updateOrderState(orderNum);
                return AjaxResult.success();
            }
        }
        return AjaxResult.error(200 , "请求参数有误");
    }

    @Override
    public AjaxResult finshOrder(CommonVO commonVO) {
        String orderNum = commonVO.getOrderNum();
        pCommonMapper.updateOrderStateFinsh(orderNum);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult joinOrderList(CommonVO commonVO) {
        String orderNum = commonVO.getOrderNum();
        String userId = commonVO.getUserID();
        Integer num = commonVO.getNum();
        if (StringUtils.isEmpty(orderNum) && StringUtils.isEmpty(userId) ){
            return AjaxResult.error("请求参数错误！");
        }
        Integer count  = pCommonMapper.queryOrderMemberCount(orderNum);
        Integer menberNum = count + num;
        if(menberNum >= 4){
            return AjaxResult.error("订单人数大于4人");
        }
        Map<String , Object> data = new HashMap<>();
        data.put("orderNum",orderNum);
        data.put("custId" ,userId);
        data.put("num" ,num);
        data.put("cust_name" ,commonVO.getName());
        data.put("open_id" ,commonVO.getOpenId());
        data.put("cust_phone" ,commonVO.getPhone());
        data.put("state" ,0);
        data.put("createTime" , DateUtils.dateTimeNow());
        pCommonMapper.insertOrderMemberInfo(data);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult initMember(PPassenger pPassenger) {
        String openId = pPassenger.getOpenId();
        if(StringUtils.isEmpty(openId)) return AjaxResult.error("请求参数错误,请检查入参是否有误！");
        PPassenger passemger = pPassengerMapper.selectPPassengerByOpenId(openId);
        if(passemger != null){
            /*如果已经申请成为看司机则返回diverID*/
            if("1".equals(passemger.getApplyState())){
                PDriver driverInfoByOpenId = pDriverMapper.getDriverInfoByOpenId(openId);
                passemger.setCustId(driverInfoByOpenId.getDriverId());
            }
            return AjaxResult.success(passemger);
        }else {
            /*系统没有用户，初始化一个乘客信息*/
            pPassenger.setIsBlacklist("0");
            if(StringUtils.isEmpty(pPassenger.getCustName())) pPassenger.setCustName(pPassenger.getNickName());/*用户名为空取昵称*/
            if(StringUtils.isEmpty(pPassenger.getSex())) pPassenger.setSex("2");/*性别设置为未知*/
            pPassenger.setCreateBy("weixi_mini_admin");
            pPassenger.setCreateTime(DateUtils.getNowDate());
            pPassenger.setCustId("wxmini_passenger_"+IdUtils.simpleUUID());
            pPassenger.setApplyState("0");/*默认是乘客*/
            pPassengerMapper.insertPPassenger(pPassenger);
        }
        return AjaxResult.success(pPassenger);
    }
}
