package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysServicesTopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysServicesTopicMapper {
    List<SysServicesTopic> selectSysServicesTopicByUserIdAndRole(SysServicesTopic topic);

    List<SysServicesTopic> selectCustomerListByUserIdAndRole(SysServicesTopic topic);

    int insertSysServicesTopic(SysServicesTopic topic);

    int insertSysServicesTopicStatus(List<SysServicesTopic> topics);

    int updateSysServicesTopicStatus(SysServicesTopic topic);

    int inserSysServicesTopicComment(SysServicesTopic topic);

    int inserSysServicesTopicReply(SysServicesTopic topic);

    List<SysServicesTopic> selectSysServicesTopicSessionByTopicId(String topicId);

    List<SysServicesTopic> selectUnreadTopicCount(List<SysServicesTopic> topics);

    List<SysServicesTopic> selectTopicListByUid(SysServicesTopic topic);

    /**
     * 根据客户ID查询总共提交反馈意见数量
     * @param cusId 客户ID
     * @return
     */
    int getServicesTopicNum(@Param("cusId")Long cusId);

    /**
     * 根据topicId查询内容
     * @param topicId
     * @return
     */
    String getTopicContentByTopicId(String topicId);

    /**
     * 根据评论ID查询评论内容
     * @param commentId
     * @return
     */
    String getCommentContentByCommentId(String commentId);

    /**
     * 查询回复内容
     * @param replyId
     * @return
     */
    String getReplyContentByReplyId(String replyId);
}
