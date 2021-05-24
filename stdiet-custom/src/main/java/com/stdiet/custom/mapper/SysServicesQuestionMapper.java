package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysServicesQuestion;

import java.util.List;

public interface SysServicesQuestionMapper {
    List<SysServicesQuestion> selectSysServicesQuestionByUserIdAndRole(SysServicesQuestion servicesQuestion);

    int insertSysServicesQuestion(SysServicesQuestion servicesQuestion);

    int insertSysServicesQuestionStatus(List<SysServicesQuestion> sysServicesQuestion);

    int updateSysServicesQuestionStatus(SysServicesQuestion sysServicesQuestion);
}
