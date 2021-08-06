package com.stdiet.custom.service;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysMessageNotice;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.domain.SysServicesTopic;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.domain.entityEnum.MessageNoticeEnum;
import com.stdiet.custom.dto.response.MessageNoticeResponse;
import org.apache.ibatis.annotations.Param;

/**
 * 客户消息通知Service接口
 *
 * @author xzj
 * @date 2021-04-26
 */
public interface ISysMessageNoticeService
{
    /**
     * 查询客户消息通知
     *
     * @param id 客户消息通知ID
     * @return 客户消息通知
     */
    public SysMessageNotice selectSysMessageNoticeById(Long id);

    /**
     * 查询客户消息通知列表
     *
     * @param sysMessageNotice 客户消息通知
     * @return 客户消息通知集合
     */
    public List<SysMessageNotice> selectSysMessageNoticeList(SysMessageNotice sysMessageNotice);

    /**
     * 新增客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    public int insertSysMessageNotice(SysMessageNotice sysMessageNotice);

    /**
     * 修改客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    public int updateSysMessageNotice(SysMessageNotice sysMessageNotice);

    /**
     * 批量删除客户消息通知
     *
     * @param ids 需要删除的客户消息通知ID
     * @return 结果
     */
    public int deleteSysMessageNoticeByIds(Long[] ids);

    /**
     * 删除客户消息通知信息
     *
     * @param id 客户消息通知ID
     * @return 结果
     */
    public int deleteSysMessageNoticeById(Long id);

    /**
     * 根据客户ID查询客户信息（包含私有信息以及公共消息）
     * @param sysMessageNotice
     * @return
     */
    public List<MessageNoticeResponse> getCustomerMessage(SysMessageNotice sysMessageNotice);

    /**
     * 消息发送
     * @param messageNoticeEnum
     * @param sysMessageNotice
     * @return
     */
    public int sendMessageNoticeToCustomer(MessageNoticeEnum messageNoticeEnum, SysMessageNotice sysMessageNotice);

    /**
     * 根据客户ID查询客户消息数量
     * @param sysMessageNotice
     * @return
     */
    public int getCustomerMessageCount(SysMessageNotice sysMessageNotice);

    /**
     * 发送打卡点评消息
     * @param sysWxUserLog
     */
    public void sendPunchCommentMessage(SysWxUserLog sysWxUserLog);

    /**
     * 发送打卡动态点赞消息
     * @param sysWxUserLog
     * @param openId 点赞用户openid
     */
    public void sendpunchDynamicThumbsUpMessage(SysWxUserLog sysWxUserLog, String openId);

    /**
     * 发送食谱消息
     * @param sysRecipesPlan
     */
    public void sendRecipesPlanMessage(SysRecipesPlan sysRecipesPlan);

    /**
     * 发送执行反馈话题消息
     * @param topic 话题对象
     * @param type 类型 1评论 2回复
     * @param originalContent 话题内容或被回复的内容
     */
    public void sendTopicMessage(SysServicesTopic topic, int type, Long statusId, String originalContent);

    /**
     * 根据客户ID查询打卡点评消息以及未读消息数量（包含点评营养师信息、客户信息、点评内容）
     * @param cusId 客户ID
     * @return
     */
    Map<String,Object> getPunchCommentMessageByCusId(Long cusId);

    /**
     * 根据客户ID查询点赞消息列表
     * @param cusId 客户ID
     * @return
     */
    Map<String,Object> getPunchDynamicThumbsUpMessage(Long cusId);

    /**
     * 根据客户ID查询服务消息（食谱发送消息、执行反馈回复消息）
     * @return
     */
    Map<String,Object> getServiceMessage(Long cusId);
}