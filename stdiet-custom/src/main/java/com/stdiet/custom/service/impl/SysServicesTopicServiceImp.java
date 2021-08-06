package com.stdiet.custom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.uuid.UUID;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysServicesTopic;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.mapper.SysServicesTopicMapper;
import com.stdiet.custom.server.WebSocketServer;
import com.stdiet.custom.service.ISysMessageNoticeService;
import com.stdiet.custom.service.ISysServicesTopicService;
import com.stdiet.custom.service.ISysWxUserInfoService;
import com.stdiet.custom.utils.WsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysServicesTopicServiceImp implements ISysServicesTopicService {
    @Autowired
    SysServicesTopicMapper servicesTopicMapper;

    @Autowired
    SysCustomerMapper sysCustomerMapper;

    @Autowired
    ISysWxUserInfoService iSysWxUserInfoService;

    @Autowired
    ISysMessageNoticeService sysMessageNoticeService;

    @Override
    public List<SysServicesTopic> selectSysServicesTopicByUserIdAndRole(SysServicesTopic topic) {
        return servicesTopicMapper.selectSysServicesTopicByUserIdAndRole(topic);
    }

    @Override
    public List<SysServicesTopic> selectCustomerListByUserIdAndRole(SysServicesTopic topic) {
        return servicesTopicMapper.selectCustomerListByUserIdAndRole(topic);
    }

    @Override
    public SysServicesTopic insertSysServicesTopic(SysServicesTopic topic) {
        // 生成uuid
        topic.setTopicId(UUID.randomUUID().toString().replaceAll("-", ""));
        topic.setRead(1);
        topic.setCreateTime(DateUtils.getNowDate());

        servicesTopicMapper.insertSysServicesTopic(topic);

        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(Long.parseLong(topic.getUid()));

        List<SysServicesTopic> statusList = new ArrayList<>();

        String customerId = String.valueOf(customer.getId());
        SysServicesTopic customerStatus = new SysServicesTopic();
        customerStatus.setUid(customerId);
        customerStatus.setRole("customer");
        customerStatus.setRead(1);
        customerStatus.setTopicId(topic.getTopicId());
        statusList.add(customerStatus);

        String dieticianId = String.valueOf(customer.getMainDietitian());
        SysServicesTopic dieticianStatus = new SysServicesTopic();
        dieticianStatus.setUid(dieticianId);
        dieticianStatus.setRole("dietician");
        dieticianStatus.setRead(0);
        dieticianStatus.setTopicId(topic.getTopicId());
        statusList.add(dieticianStatus);

        String afterSaleId = String.valueOf(customer.getAfterDietitian());
        SysServicesTopic afterSaleStatus = new SysServicesTopic();
        afterSaleStatus.setUid(afterSaleId);
        afterSaleStatus.setRole("after_sale");
        afterSaleStatus.setRead(0);
        afterSaleStatus.setTopicId(topic.getTopicId());
        statusList.add(afterSaleStatus);

        String dieticianAssistantId = String.valueOf(customer.getAssistantDietitian());
        SysServicesTopic dieticianAssistantStatus = new SysServicesTopic();
        dieticianAssistantStatus.setUid(dieticianAssistantId);
        dieticianAssistantStatus.setRole("dietician_assistant");
        dieticianAssistantStatus.setRead(0);
        dieticianAssistantStatus.setTopicId(topic.getTopicId());
        statusList.add(dieticianAssistantStatus);

        int rows = servicesTopicMapper.insertSysServicesTopicStatus(statusList);
        if (rows > 0) {
            try {
                List<SysServicesTopic> counts = servicesTopicMapper.selectUnreadTopicCount(statusList);
                for (int i = 0; i < counts.size(); i++) {
                    topic.setId(statusList.get(i).getId());
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("count", counts.get(i).getCount());
                    dataObj.put("data", topic);

                    JSONObject userObj = new JSONObject();
                    SysWxUserInfo userInfo = iSysWxUserInfoService.selectSysWxUserInfoByCusId(Long.parseLong(customerId));
                    if (!StringUtils.isNull(userInfo)) {
                        userObj.put("avatar", userInfo.getAvatarUrl());
                    }
                    userObj.put("name", customer.getName());
                    userObj.put("create_time", DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss"));
                    userObj.put("update_time", DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss"));
                    userObj.put("read", 0);
                    userObj.put("uid", customerId);
                    userObj.put("role", "customer");

                    JSONObject msgObj = new JSONObject();
                    msgObj.put("type", WsUtils.WS_TYPE_MESSAGE_COUNT);
                    msgObj.put("msg", "未读消息数");
                    msgObj.put("data", dataObj);
                    msgObj.put("customer", userObj);
                    WebSocketServer.sendInfo(msgObj.toJSONString(), counts.get(i).getUid());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        topic.setId(customerStatus.getId());
        topic.setUid(null);

        return topic;
    }

    @Override
    public int updateSysServicesTopicStatus(SysServicesTopic topic) {
        return servicesTopicMapper.updateSysServicesTopicStatus(topic);
    }


    @Override
    public SysServicesTopic inserSysServicesTopicReply(SysServicesTopic topic) {
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        topic.setId(uuid);
        int row = servicesTopicMapper.inserSysServicesTopicReply(topic);
        if (row > 0) {
            // 设置未读
            SysServicesTopic status = new SysServicesTopic();
            status.setRead(0);
            status.setTopicId(topic.getTopicId());
            status.setRole(topic.getRole());
            servicesTopicMapper.updateSysServicesTopicStatus(status);

            /*SysServicesTopic tp2 =  servicesTopicMapper.getTopicContentByCommentId(topic.getCommentId());
            System.out.println(tp2.getContent() + "---------" + tp2.getTopicId());*/

            //发送消息
            if("customer".equals(topic.getToRole()) && StringUtils.isNotEmpty(topic.getToUid())){
                String content = "";
                Long statusId = 0L;
                SysServicesTopic tp = null;
                if(StringUtils.isNotEmpty(topic.getCommentId())){
                    tp =  servicesTopicMapper.getTopicContentByCommentId(topic.getCommentId());
                    if(tp != null){
                        content = tp.getContent();
                        //查询客户对象status表的id
                        statusId = servicesTopicMapper.getStatusIdByRoleAndTopicId(tp.getTopicId(), topic.getToRole());
                    }
                }
                if(tp != null && StringUtils.isNotEmpty(content)){
                    sysMessageNoticeService.sendTopicMessage(topic, 2, tp.getTopicId(), statusId, content);
                }
            }

            afterReply(topic);
        }
        return topic;
    }


    @Override
    public SysServicesTopic inserSysServicesTopicComment(SysServicesTopic topic) {
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        topic.setId(uuid);
        int row = servicesTopicMapper.inserSysServicesTopicComment(topic);
        if (row > 0) {
            // 设置未读
            SysServicesTopic status = new SysServicesTopic();
            status.setRead(0);
            status.setTopicId(topic.getTopicId());
            status.setRole(topic.getRole());
            servicesTopicMapper.updateSysServicesTopicStatus(status);

            //发送消息
            if("customer".equals(topic.getToRole()) && StringUtils.isNotEmpty(topic.getToUid())){
                //查询topic内容
                String content = servicesTopicMapper.getTopicContentByTopicId(topic.getTopicId());
                //查询客户对象status表的id
                Long statusId = servicesTopicMapper.getStatusIdByRoleAndTopicId(topic.getTopicId(), topic.getToRole());
                if(StringUtils.isNotEmpty(content)){
                    sysMessageNoticeService.sendTopicMessage(topic, 1, topic.getTopicId(), statusId, content);
                }
            }

            afterReply(topic);
        }
        return topic;
    }

    public void afterReply(SysServicesTopic topic) {
        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(Long.parseLong(topic.getFromUid()));

        List<SysServicesTopic> statusList = new ArrayList<>();
        SysServicesTopic dieticianStatus = new SysServicesTopic();
        dieticianStatus.setUid(String.valueOf(customer.getMainDietitian()));
        dieticianStatus.setRole("dietician");
        statusList.add(dieticianStatus);
        SysServicesTopic afterSaleStatus = new SysServicesTopic();
        afterSaleStatus.setUid(String.valueOf(customer.getAfterDietitian()));
        afterSaleStatus.setRole("after_sale");
        statusList.add(afterSaleStatus);
        SysServicesTopic dieticianAssistantStatus = new SysServicesTopic();
        dieticianAssistantStatus.setUid(String.valueOf(customer.getAssistantDietitian()));
        dieticianAssistantStatus.setRole("dietician_assistant");
        statusList.add(dieticianAssistantStatus);

        try {
            List<SysServicesTopic> counts = servicesTopicMapper.selectUnreadTopicCount(statusList);
            for (int i = 0; i < counts.size(); i++) {
                JSONObject dataObj = new JSONObject();
                dataObj.put("count", counts.get(i).getCount());
                dataObj.put("topicId", topic.getTopicId());
                dataObj.put("uid", topic.getFromUid());

                JSONObject msgObj = new JSONObject();
                msgObj.put("type", WsUtils.WS_TYPE_NEW_CUSTOMER_REPLY);
                msgObj.put("msg", "新客户回复");
                msgObj.put("data", dataObj);
                WebSocketServer.sendInfo(msgObj.toJSONString(), statusList.get(i).getUid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SysServicesTopic> selectSysServicesTopicSessionByTopicId(String topicId) {
        return servicesTopicMapper.selectSysServicesTopicSessionByTopicId(topicId);
    }

    @Override
    public List<SysServicesTopic> selectUnreadTopicCount(List<SysServicesTopic> statusList) {
        return servicesTopicMapper.selectUnreadTopicCount(statusList);
    }

    @Override
    public List<SysServicesTopic> selectTopicListByUid(SysServicesTopic topic) {
        return servicesTopicMapper.selectTopicListByUid(topic);
    }

    /**
     * 根据客户ID查询总共提交反馈意见数量
     * @param cusId 客户ID
     * @return
     */
    @Override
    public int getServicesTopicNum(Long cusId){
        return servicesTopicMapper.getServicesTopicNum(cusId);
    }

    /**
     * 根据topicId查询内容
     * @param topicId
     * @return
     */
    public String getTopicContentByTopicId(String topicId){
        return servicesTopicMapper.getTopicContentByTopicId(topicId);
    }
}
