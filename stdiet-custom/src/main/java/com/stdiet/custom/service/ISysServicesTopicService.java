package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysServicesTopic;

import java.util.List;

public interface ISysServicesTopicService {

    List<SysServicesTopic> selectSysServicesTopicByUserIdAndRole(SysServicesTopic topic);

    List<SysServicesTopic> selectCustomerListByUserIdAndRole(SysServicesTopic topic);

    SysServicesTopic insertSysServicesTopic(SysServicesTopic topic);

    int updateSysServicesTopicStatus(SysServicesTopic topic);

    SysServicesTopic inserSysServicesTopicReply(SysServicesTopic topic);

    SysServicesTopic inserSysServicesTopicComment(SysServicesTopic topic);

    List<SysServicesTopic> selectSysServicesTopicSessionByTopicId(String topicId);

    List<SysServicesTopic> selectUnreadTopicCount(List<SysServicesTopic> topic);

    List<SysServicesTopic> selectTopicListByUid(SysServicesTopic topic);

    /**
     * 根据客户ID查询总共提交反馈意见数量
     * @param cusId 客户ID
     * @return
     */
    int getServicesTopicNum(Long cusId);

    /**
     * 根据topicId查询内容
     * @param topicId
     * @return
     */
    String getTopicContentByTopicId(String topicId);
}
