package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.uuid.UUID;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysServicesTopic;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.mapper.SysServicesTopicMapper;
import com.stdiet.custom.service.ISysServicesTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(topic.getUid());

        List<SysServicesTopic> statusList = new ArrayList<>();

        SysServicesTopic customerStatus = new SysServicesTopic();
        customerStatus.setUid(customer.getId());
        customerStatus.setRole("customer");
        customerStatus.setRead(1);
        customerStatus.setTopicId(topic.getTopicId());
        statusList.add(customerStatus);

        SysServicesTopic dieticianStatus = new SysServicesTopic();
        dieticianStatus.setUid(customer.getMainDietitian());
        dieticianStatus.setRole("dietician");
        dieticianStatus.setRead(0);
        dieticianStatus.setTopicId(topic.getTopicId());
        statusList.add(dieticianStatus);

        SysServicesTopic afterSaleStatus = new SysServicesTopic();
        afterSaleStatus.setUid(customer.getAfterDietitian());
        afterSaleStatus.setRole("after_sale");
        afterSaleStatus.setRead(0);
        afterSaleStatus.setTopicId(topic.getTopicId());
        statusList.add(afterSaleStatus);

        SysServicesTopic dieticianAssistantStatus = new SysServicesTopic();
        dieticianAssistantStatus.setUid(customer.getAssistantDietitian());
        dieticianAssistantStatus.setRole("dietician_assistant");
        dieticianAssistantStatus.setRead(0);
        dieticianAssistantStatus.setTopicId(topic.getTopicId());
        statusList.add(dieticianAssistantStatus);

        servicesTopicMapper.insertSysServicesTopicStatus(statusList);

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
        }
        return topic;
    }

    @Override
    public List<SysServicesTopic> selectSysServicesTopicSessionByTopicId(String topicId) {
        return servicesTopicMapper.selectSysServicesTopicSessionByTopicId(topicId);
    }
}
