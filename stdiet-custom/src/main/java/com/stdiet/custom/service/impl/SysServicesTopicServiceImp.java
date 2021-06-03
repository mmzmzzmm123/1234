package com.stdiet.custom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.uuid.UUID;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysServicesTopic;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.mapper.SysServicesTopicMapper;
import com.stdiet.custom.server.WebSocketServer;
import com.stdiet.custom.service.ISysServicesTopicService;
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

    @Override
    public List<SysServicesTopic> selectSysServicesTopicByUserIdAndRole(SysServicesTopic topic) {
        return servicesTopicMapper.selectSysServicesTopicByUserIdAndRole(topic);
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

                    JSONObject msgObj = new JSONObject();
                    msgObj.put("type", WsUtils.WS_TYPE_MESSAGE_COUNT);
                    msgObj.put("msg", "未读消息数");
                    msgObj.put("data", dataObj);
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

            afterReply(topic);
        }
        return topic;
    }

    public void afterReply(SysServicesTopic topic) {
        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(Long.parseLong(topic.getFromUid()));

        List<SysServicesTopic> statusList = new ArrayList<>();
        SysServicesTopic dieticianStatus = new SysServicesTopic();
        dieticianStatus.setUid(String.valueOf(customer.getMainDietitian()));
        statusList.add(dieticianStatus);
        SysServicesTopic afterSaleStatus = new SysServicesTopic();
        afterSaleStatus.setUid(String.valueOf(customer.getAfterDietitian()));
        statusList.add(afterSaleStatus);
        SysServicesTopic dieticianAssistantStatus = new SysServicesTopic();
        dieticianAssistantStatus.setUid(String.valueOf(customer.getAssistantDietitian()));
        statusList.add(dieticianAssistantStatus);

        try {
            List<SysServicesTopic> counts = servicesTopicMapper.selectUnreadTopicCount(statusList);
            for (int i = 0; i < counts.size(); i++) {
                JSONObject dataObj = new JSONObject();
                dataObj.put("count", counts.get(i).getCount());
                dataObj.put("topicId", topic.getTopicId());

                JSONObject msgObj = new JSONObject();
                msgObj.put("type", WsUtils.WS_TYPE_NEW_CUSTOMER_REPLY);
                msgObj.put("msg", "新客户回复");
                msgObj.put("data", dataObj);
                WebSocketServer.sendInfo(msgObj.toJSONString(), counts.get(i).getUid());
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
}
