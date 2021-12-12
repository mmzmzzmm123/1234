package com.ruoyi.carpool.service.impl;

import com.ruoyi.carpool.domain.CommonVO;
import com.ruoyi.carpool.domain.POrder;
import com.ruoyi.carpool.mapper.PCommonMapper;
import com.ruoyi.carpool.mapper.PDriverMapper;
import com.ruoyi.carpool.mapper.POrderMapper;
import com.ruoyi.carpool.service.IPCommonService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import jdk.nashorn.api.scripting.AbstractJSObject;
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
        return AjaxResult.error();
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
        data.put("state" ,0);
        data.put("createTime" , DateUtils.dateTimeNow());
        pCommonMapper.insertOrderMemberInfo(data);
        return AjaxResult.success();
    }
}
