package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysServicesQuestion;

import java.util.List;

public interface ISysServicesQuestionService {

    List<SysServicesQuestion> selectSysServicesQuestionByUserIdAndRole(SysServicesQuestion servicesQuestion);

    int insertSysServicesQuestion(SysServicesQuestion servicesQuestion);

    int updateSysServicesQuestionStatus(SysServicesQuestion sysServicesQuestion);

    int inserSysServicesQuestionReply(SysServicesQuestion sysServicesQuestion);

    List<SysServicesQuestion> selectSysServicesQuestionSessionByQueId(String queId);
}
