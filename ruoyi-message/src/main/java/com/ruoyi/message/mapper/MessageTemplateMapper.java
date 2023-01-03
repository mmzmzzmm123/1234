package com.ruoyi.message.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.message.domain.MessageTemplate;

import java.util.List;

/**
 * 消息模板 数据层
 * @Author lixin
 * @Date 2022/12/16 17:28
 */
public interface MessageTemplateMapper extends BaseMapper<MessageTemplate>
{
    /**
     * 查询消息模板
     *
     * @param msgTempId 消息模板主键
     * @return 消息模板
     */
    public MessageTemplate selectMessageTemplateByMsgTempId(Long msgTempId);

    /**
     * 查询消息模板列表
     *
     * @param messageTemplate 消息模板
     * @return 消息模板集合
     */
    public List<MessageTemplate> selectMessageTemplateList(MessageTemplate messageTemplate);

    /**
     * 新增消息模板
     *
     * @param messageTemplate 消息模板
     * @return 结果
     */
    public int insertMessageTemplate(MessageTemplate messageTemplate);

    /**
     * 修改消息模板
     *
     * @param messageTemplate 消息模板
     * @return 结果
     */
    public int updateMessageTemplate(MessageTemplate messageTemplate);

    /**
     * 删除消息模板
     *
     * @param msgTempId 消息模板主键
     * @return 结果
     */
    public int deleteMessageTemplateByMsgTempId(Long msgTempId);

    /**
     * 批量删除消息模板
     *
     * @param msgTempIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageTemplateByMsgTempIds(Long[] msgTempIds);
}
