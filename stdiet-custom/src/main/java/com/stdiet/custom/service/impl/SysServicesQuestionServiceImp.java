package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.uuid.UUID;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysServicesQuestion;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.mapper.SysServicesQuestionMapper;
import com.stdiet.custom.service.ISysServicesQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysServicesQuestionServiceImp implements ISysServicesQuestionService {
    @Autowired
    SysServicesQuestionMapper servicesQuestionMapper;

    @Autowired
    SysCustomerMapper sysCustomerMapper;

    @Override
    public List<SysServicesQuestion> selectSysServicesQuestionByUserIdAndRole(SysServicesQuestion servicesQuestion) {
        return servicesQuestionMapper.selectSysServicesQuestionByUserIdAndRole(servicesQuestion);
    }

    @Override
    public int insertSysServicesQuestion(SysServicesQuestion servicesQuestion) {
        // 生成uuid
        servicesQuestion.setQueId(UUID.randomUUID().toString().replaceAll("-", ""));

        servicesQuestionMapper.insertSysServicesQuestion(servicesQuestion);

        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(servicesQuestion.getCusId());

        List<SysServicesQuestion> statusList = new ArrayList<>();
        SysServicesQuestion customerStatus = new SysServicesQuestion();
        customerStatus.setUserId(customer.getId());
        customerStatus.setRole("customer");
        customerStatus.setRead(1);
        customerStatus.setQueId(servicesQuestion.getQueId());
        statusList.add(customerStatus);

        SysServicesQuestion dieticianStatus = new SysServicesQuestion();
        dieticianStatus.setUserId(customer.getMainDietitian());
        dieticianStatus.setRole("dietician");
        customerStatus.setRead(0);
        dieticianStatus.setQueId(servicesQuestion.getQueId());
        statusList.add(dieticianStatus);

        SysServicesQuestion afterSaleStatus = new SysServicesQuestion();
        afterSaleStatus.setUserId(customer.getAfterDietitian());
        afterSaleStatus.setRole("after_sale");
        customerStatus.setRead(0);
        afterSaleStatus.setQueId(servicesQuestion.getQueId());
        statusList.add(afterSaleStatus);

        SysServicesQuestion dieticianAssistantStatus = new SysServicesQuestion();
        dieticianAssistantStatus.setUserId(customer.getAssistantDietitian());
        dieticianAssistantStatus.setRole("dietician_assistant");
        customerStatus.setRead(0);
        dieticianAssistantStatus.setQueId(servicesQuestion.getQueId());
        statusList.add(dieticianAssistantStatus);

        return servicesQuestionMapper.insertSysServicesQuestionStatus(statusList);
    }


    @Override
    public int updateSysServicesQuestionStatus(SysServicesQuestion sysServicesQuestion) {
        return servicesQuestionMapper.updateSysServicesQuestionStatus(sysServicesQuestion);
    }

    @Override
    public int inserSysServicesQuestionReply(SysServicesQuestion sysServicesQuestion) {
        int row = servicesQuestionMapper.inserSysServicesQuestionReply(sysServicesQuestion);
        if (row > 0) {
            // 设置未读
            SysServicesQuestion status = new SysServicesQuestion();
            status.setRead(0);
            status.setQueId(sysServicesQuestion.getQueId());
            status.setRole(sysServicesQuestion.getRole());
            servicesQuestionMapper.updateSysServicesQuestionStatus(status);
        }
        return row;
    }

    @Override
    public List<SysServicesQuestion> selectSysServicesQuestionSessionByQueId(String queId) {
        return servicesQuestionMapper.selectSysServicesQuestionSessionByQueId(queId);
    }
}
