package com.ruoyi.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import com.ruoyi.message.domain.MessageTemplate;

/**
 * 消息模板Service接口
 *
 * @author lixin
 * @date 2022-12-16
 */
public interface IMessageTemplateService extends IService<MessageTemplate>
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
     * 批量删除消息模板
     *
     * @param msgTempIds 需要删除的消息模板主键集合
     * @return 结果
     */
    public int deleteMessageTemplateByMsgTempIds(Long[] msgTempIds);

    /**
     * 删除消息模板信息
     *
     * @param msgTempId 消息模板主键
     * @return 结果
     */
    public int deleteMessageTemplateByMsgTempId(Long msgTempId);
}
