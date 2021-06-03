package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysServicesTopic;

import java.util.List;

public interface ISysServicesTopicService {

    List<SysServicesTopic> selectSysServicesTopicByUserIdAndRole(SysServicesTopic topic);

    SysServicesTopic insertSysServicesTopic(SysServicesTopic topic);

    int updateSysServicesTopicStatus(SysServicesTopic topic);

    SysServicesTopic inserSysServicesTopicReply(SysServicesTopic topic);

    SysServicesTopic inserSysServicesTopicComment(SysServicesTopic topic);

    List<SysServicesTopic> selectSysServicesTopicSessionByTopicId(String topicId);

    List<SysServicesTopic> selectUnreadTopicCount(List<SysServicesTopic> topic);
}
