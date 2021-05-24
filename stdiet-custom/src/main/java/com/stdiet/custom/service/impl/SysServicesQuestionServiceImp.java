package com.stdiet.custom.service.impl;

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
        servicesQuestionMapper.insertSysServicesQuestion(servicesQuestion);

        SysCustomer customer = sysCustomerMapper.selectSysCustomerById(servicesQuestion.getCusId());

        List<SysServicesQuestion> statusList = new ArrayList<>();
        SysServicesQuestion customerStatus = new SysServicesQuestion();
        customerStatus.setUserId(customer.getId());
        customerStatus.setRole("customer");
        customerStatus.setQueId(servicesQuestion.getId());
        statusList.add(customerStatus);

        SysServicesQuestion dieticianStatus = new SysServicesQuestion();
        dieticianStatus.setUserId(customer.getMainDietitian());
        dieticianStatus.setRole("dietician");
        dieticianStatus.setQueId(servicesQuestion.getId());
        statusList.add(dieticianStatus);

        SysServicesQuestion afterSaleStatus = new SysServicesQuestion();
        afterSaleStatus.setUserId(customer.getAfterDietitian());
        afterSaleStatus.setRole("after_sale");
        afterSaleStatus.setQueId(servicesQuestion.getId());
        statusList.add(afterSaleStatus);

        SysServicesQuestion dieticianAssistantStatus = new SysServicesQuestion();
        dieticianAssistantStatus.setUserId(customer.getAssistantDietitian());
        dieticianAssistantStatus.setRole("dietician_assistant");
        dieticianAssistantStatus.setQueId(servicesQuestion.getId());
        statusList.add(dieticianAssistantStatus);

        return servicesQuestionMapper.insertSysServicesQuestionStatus(statusList);
    }


    @Override
    public int updateSysServicesQuestionStatus(SysServicesQuestion sysServicesQuestion) {
        return servicesQuestionMapper.updateSysServicesQuestionStatus(sysServicesQuestion);
    }
}
