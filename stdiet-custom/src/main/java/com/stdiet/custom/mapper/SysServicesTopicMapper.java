package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysServicesQuestion;
import com.stdiet.custom.domain.SysServicesTopic;

import java.util.List;

public interface SysServicesTopicMapper {
    List<SysServicesTopic> selectSysServicesTopicByUserIdAndRole(SysServicesTopic topic);

    int insertSysServicesTopic(SysServicesTopic topic);

    int insertSysServicesTopicStatus(List<SysServicesTopic> topics);

    int updateSysServicesTopicStatus(SysServicesTopic topic);

    int inserSysServicesTopicComment(SysServicesTopic topic);

    int inserSysServicesTopicReply(SysServicesTopic topic);

    List<SysServicesTopic> selectSysServicesTopicSessionByTopicId(String topicId);
}
