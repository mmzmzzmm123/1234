package com.ruoyi.message.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.DateUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.message.mapper.MessageTemplateMapper;
import com.ruoyi.message.domain.MessageTemplate;
import com.ruoyi.message.service.IMessageTemplateService;

/**
 * 消息模板Service业务层处理
 *
 * @author lixin
 * @date 2022-12-16
 */
@Service
@AllArgsConstructor
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements IMessageTemplateService {
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 查询消息模板
     *
     * @param msgTempId 消息模板主键
     * @return 消息模板
     */
    @Override
    public MessageTemplate selectMessageTemplateByMsgTempId(Long msgTempId) {
        return messageTemplateMapper.selectMessageTemplateByMsgTempId(msgTempId);
    }

    /**
     * 查询消息模板列表
     *
     * @param messageTemplate 消息模板
     * @return 消息模板
     */
    @Override
    public List<MessageTemplate> selectMessageTemplateList(MessageTemplate messageTemplate) {
        return messageTemplateMapper.selectMessageTemplateList(messageTemplate);
    }

    /**
     * 新增消息模板
     *
     * @param messageTemplate 消息模板
     * @return 结果
     */
    @Override
    public int insertMessageTemplate(MessageTemplate messageTemplate) {
        messageTemplate.setCreateTime(DateUtils.getNowDate());
        return messageTemplateMapper.insertMessageTemplate(messageTemplate);
    }

    /**
     * 修改消息模板
     *
     * @param messageTemplate 消息模板
     * @return 结果
     */
    @Override
    public int updateMessageTemplate(MessageTemplate messageTemplate) {
        messageTemplate.setUpdateTime(DateUtils.getNowDate());
        return messageTemplateMapper.updateMessageTemplate(messageTemplate);
    }

    /**
     * 批量删除消息模板
     *
     * @param msgTempIds 需要删除的消息模板主键
     * @return 结果
     */
    @Override
    public int deleteMessageTemplateByMsgTempIds(Long[] msgTempIds) {
        return messageTemplateMapper.deleteMessageTemplateByMsgTempIds(msgTempIds);
    }

    /**
     * 删除消息模板信息
     *
     * @param msgTempId 消息模板主键
     * @return 结果
     */
    @Override
    public int deleteMessageTemplateByMsgTempId(Long msgTempId) {
        return messageTemplateMapper.deleteMessageTemplateByMsgTempId(msgTempId);
    }

    /**
     * 按pcode获得消息模板
     * @param pcode
     * @return
     */
    @Override
    public MessageTemplate selectMessageTemplateByMsgTempPcode(String pcode) {
        return this.lambdaQuery().eq(MessageTemplate::getMsgTempPcode, pcode).getEntity();
    }
}
