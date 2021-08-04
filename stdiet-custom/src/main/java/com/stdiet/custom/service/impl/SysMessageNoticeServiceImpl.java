package com.stdiet.custom.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.domain.entityEnum.MessageNoticeEnum;
import com.stdiet.custom.dto.response.MessageNoticeResponse;
import com.stdiet.custom.service.ISysCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysMessageNoticeMapper;
import com.stdiet.custom.service.ISysMessageNoticeService;

/**
 * 客户消息通知Service业务层处理
 *
 * @author xzj
 * @date 2021-04-26
 */
@Service
public class SysMessageNoticeServiceImpl implements ISysMessageNoticeService
{
    @Autowired
    private SysMessageNoticeMapper sysMessageNoticeMapper;

    @Autowired
    private ISysCustomerService sysCustomerService;

    /**
     * 查询客户消息通知
     *
     * @param id 客户消息通知ID
     * @return 客户消息通知
     */
    @Override
    public SysMessageNotice selectSysMessageNoticeById(Long id)
    {
        return sysMessageNoticeMapper.selectSysMessageNoticeById(id);
    }

    /**
     * 查询客户消息通知列表
     *
     * @param sysMessageNotice 客户消息通知
     * @return 客户消息通知
     */
    @Override
    public List<SysMessageNotice> selectSysMessageNoticeList(SysMessageNotice sysMessageNotice)
    {
        return sysMessageNoticeMapper.selectSysMessageNoticeList(sysMessageNotice);
    }

    /**
     * 新增客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    @Override
    public int insertSysMessageNotice(SysMessageNotice sysMessageNotice)
    {
        sysMessageNotice.setCreateTime(DateUtils.getNowDate());
        return sysMessageNoticeMapper.insertSysMessageNotice(sysMessageNotice);
    }

    /**
     * 修改客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    @Override
    public int updateSysMessageNotice(SysMessageNotice sysMessageNotice)
    {
        sysMessageNotice.setUpdateTime(DateUtils.getNowDate());
        return sysMessageNoticeMapper.updateSysMessageNotice(sysMessageNotice);
    }

    /**
     * 批量删除客户消息通知
     *
     * @param ids 需要删除的客户消息通知ID
     * @return 结果
     */
    @Override
    public int deleteSysMessageNoticeByIds(Long[] ids)
    {
        return sysMessageNoticeMapper.deleteSysMessageNoticeByIds(ids);
    }

    /**
     * 删除客户消息通知信息
     *
     * @param id 客户消息通知ID
     * @return 结果
     */
    @Override
    public int deleteSysMessageNoticeById(Long id)
    {
        return sysMessageNoticeMapper.deleteSysMessageNoticeById(id);
    }

    /**
     * 根据客户ID查询客户信息（包含私有信息以及公共消息）
     * @param sysMessageNotice
     * @return
     */
    @Override
    public List<MessageNoticeResponse> getCustomerMessage(SysMessageNotice sysMessageNotice){
        List<MessageNoticeResponse> responsesList = sysMessageNoticeMapper.getCustomerMessage(sysMessageNotice);
        if(responsesList != null && responsesList.size() > 0){
            for (MessageNoticeResponse messageNoticeResponse : responsesList) {
                messageNoticeResponse.setMessageTypeName(MessageNoticeEnum.getNoticeEnumByType(messageNoticeResponse.getMessageType()).getName());
            }
        }
        return responsesList;
    }

    /**
     * 消息发送
     * @param messageNoticeEnum
     * @param sysMessageNotice
     * @return
     */
    @Override
    public int sendMessageNoticeToCustomer(MessageNoticeEnum messageNoticeEnum, SysMessageNotice sysMessageNotice){
        sysMessageNotice.setMessageProperty(messageNoticeEnum.getProperty());
        sysMessageNotice.setMessageType(messageNoticeEnum.getType());
        return sysMessageNoticeMapper.insertSysMessageNotice(sysMessageNotice);
    }

    /**
     * 根据客户ID查询客户消息数量
     * @param sysMessageNotice
     * @return
     */
    @Override
    public int getCustomerMessageCount(SysMessageNotice sysMessageNotice){
        return sysMessageNoticeMapper.getCustomerMessageCount(sysMessageNotice);
    }

    /**
     * 发送打卡点评消息
     * @param sysWxUserLog
     */
    @Override
    @Async
    public void sendPunchCommentMessage(SysWxUserLog sysWxUserLog){
        if(sysWxUserLog == null || StringUtils.isEmpty(sysWxUserLog.getOpenid()) || sysWxUserLog.getId() == null){
            return;
        }
        SysCustomer sysCustomer = sysCustomerService.getCustomerByOpenId(sysWxUserLog.getOpenid());
        if(sysCustomer != null){
            SysMessageNotice sysMessageNotice = new SysMessageNotice();
            sysMessageNotice.setMessageProperty(1);
            sysMessageNotice.setMessageType(MessageNoticeEnum.punchComment.getType());
            sysMessageNotice.setReadType(0);
            sysMessageNotice.setMessageCustomer(sysCustomer.getId());
            sysMessageNotice.setMessageTitle("");
            sysMessageNotice.setMessageContent(sysWxUserLog.getComment());
            sysMessageNotice.setMessageKey(sysWxUserLog.getId()+"");
            sendMessageNoticeToCustomer(MessageNoticeEnum.punchComment, sysMessageNotice);
        }
    }

    /**
     * 发送打卡动态点赞消息
     * @param sysWxUserLog
     * @param openId 点赞用户openid
     */
    @Override
    @Async
    public void sendpunchDynamicThumbsUpMessage(SysWxUserLog sysWxUserLog, String openId){
        if(sysWxUserLog == null || StringUtils.isEmpty(sysWxUserLog.getOpenid()) || sysWxUserLog.getId() == null){
            return;
        }
        //自己给自己点赞不发送消息
        if(sysWxUserLog.getOpenid().equals(openId)){
            return;
        }
        SysCustomer sysCustomer = sysCustomerService.getCustomerByOpenId(sysWxUserLog.getOpenid());
        if(sysCustomer != null){
            //根据openid、打卡ID查询是否已经存在点赞消息
            SysMessageNotice existSysMessageNotice = sysMessageNoticeMapper.getMessageByPunchIdAndOpenId(sysWxUserLog.getId(), openId);
            if(existSysMessageNotice != null){
                return;
            }
            SysMessageNotice sysMessageNotice = new SysMessageNotice();
            sysMessageNotice.setMessageProperty(1);
            sysMessageNotice.setMessageType(MessageNoticeEnum.punchDynamicThumbsUp.getType());
            sysMessageNotice.setReadType(0);
            sysMessageNotice.setMessageCustomer(sysCustomer.getId());
            sysMessageNotice.setMessageTitle("");
            sysMessageNotice.setMessageContent(openId);
            sysMessageNotice.setMessageKey(sysWxUserLog.getId()+"");
            sendMessageNoticeToCustomer(MessageNoticeEnum.punchDynamicThumbsUp, sysMessageNotice);
        }
    }

    /**
     * 发送执行反馈消息
     */
    @Override
    @Async
    public void sendTopicMessage(SysServicesTopic topic, int type, String originalContent){
        if(topic == null){
            return;
        }
        Map<String,Object> content = new HashMap<>();
        //回复内容
        content.put("replyContent", topic.getContent());
        //类型：1 评论  2回复
        content.put("type", type);
        //topic的内容
        content.put("originalContent", originalContent);

        SysMessageNotice sysMessageNotice = new SysMessageNotice();
        sysMessageNotice.setMessageProperty(1);
        sysMessageNotice.setMessageType(MessageNoticeEnum.topicMessage.getType());
        sysMessageNotice.setReadType(0);
        sysMessageNotice.setMessageCustomer(Long.parseLong(topic.getToUid()));
        sysMessageNotice.setMessageTitle("");
        sysMessageNotice.setMessageContent(JSONArray.toJSONString(content));
        sysMessageNotice.setMessageKey(topic.getTopicId());
        sendMessageNoticeToCustomer(MessageNoticeEnum.topicMessage, sysMessageNotice);
    }

    /**
     * 发送食谱更新消息
     */
    @Override
    @Async
    public void sendRecipesPlanMessage(SysRecipesPlan sysRecipesPlan){
        if(sysRecipesPlan == null || sysRecipesPlan.getCusId() == null || sysRecipesPlan.getRecipesId() == null){
            return;
        }
        SysMessageNotice sysMessageNotice = new SysMessageNotice();
        sysMessageNotice.setMessageType(MessageNoticeEnum.recipesPlanMessage.getType());
        sysMessageNotice.setMessageKey(sysRecipesPlan.getRecipesId()+"");
        sysMessageNotice.setMessageCustomer(sysRecipesPlan.getCusId());
        //根据食谱ID查询是否已经发送过
        SysMessageNotice existMessageNotice = sysMessageNoticeMapper.getMessageByCusIdAndTypeKey(sysMessageNotice);
        if(existMessageNotice != null){
            return;
        }
        sysMessageNotice.setMessageProperty(1);
        sysMessageNotice.setReadType(0);
        sysMessageNotice.setMessageTitle("");
        Map<String,Object> content = new HashMap<>();
        content.put("startNumDay", sysRecipesPlan.getStartNumDay());
        content.put("endNumDay", sysRecipesPlan.getEndNumDay());
        sysMessageNotice.setMessageContent(JSONArray.toJSONString(content));
        sendMessageNoticeToCustomer(MessageNoticeEnum.recipesPlanMessage, sysMessageNotice);
    }

    /**
     * 根据客户ID查询打卡点评消息以及未读消息数量（包含点评营养师信息、客户信息、点评内容）
     * @param cusId 客户ID
     * @return
     */
    @Override
    public Map<String,Object> getPunchCommentMessageByCusId(Long cusId){
        List<Map<String,Object>> punchMessageList = sysMessageNoticeMapper.getPunchCommentMessageByCusId(cusId);
        int unReadPunchMessageNum = 0;
        if(punchMessageList != null && punchMessageList.size() > 0){
            //查询未读消息
            unReadPunchMessageNum = sysMessageNoticeMapper.getUnReadPunchMessageNum(cusId);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("data", punchMessageList);
        result.put("unReadNum", unReadPunchMessageNum);
        return result;
    }

    /**
     * 根据客户ID查询点赞消息列表
     * @param cusId 客户ID
     * @return
     */
    @Override
    public Map<String,Object> getPunchDynamicThumbsUpMessage(Long cusId){
        List<Map<String,Object>> thumbsUpMessageList = sysMessageNoticeMapper.getPunchDynamicThumbsUpMessage(cusId);
        int unReadThumbsUpMessageNum = 0;
        if(thumbsUpMessageList != null && thumbsUpMessageList.size() > 0){
            //查询未读消息
            unReadThumbsUpMessageNum = sysMessageNoticeMapper.getUnReadThumbsUpMessageNum(cusId);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", thumbsUpMessageList);
        result.put("unReadNum", unReadThumbsUpMessageNum);
        return result;
    }

    /**
     * 根据客户ID查询服务消息（食谱发送消息、执行反馈回复消息）
     * @return
     */
    @Override
    public Map<String,Object> getServiceMessage(Long cusId){
        List<Map<String,Object>> serviceMessageList = sysMessageNoticeMapper.getServiceMessage(cusId);
        int unReadServiceMessageNum = 0;
        if(serviceMessageList != null && serviceMessageList.size() > 0){
            //查询未读消息
            unReadServiceMessageNum = sysMessageNoticeMapper.getUnReadServiceMessageNum(cusId);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", serviceMessageList);
        result.put("unReadNum", unReadServiceMessageNum);
        return result;
    }
}