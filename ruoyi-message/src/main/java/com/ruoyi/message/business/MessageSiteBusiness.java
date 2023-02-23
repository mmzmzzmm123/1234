package com.ruoyi.message.business;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.message.domain.MessageSiteRecords;
import com.ruoyi.message.domain.MessageTemplate;
import com.ruoyi.message.service.IMessageSiteRecordsService;
import com.ruoyi.message.service.IMessageTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

/**
 * 站内信业务
 *
 * @Author lixin
 * @Date 2023/2/21 18:16
 */
@Component
@Slf4j
@AllArgsConstructor
public class MessageSiteBusiness {
    private IMessageSiteRecordsService messageSiteRecordsService;
    private IMessageTemplateService messageTemplateService;

    /**
     * 新增加模板站内信(使用模板)
     *
     * @param sendUserId   发送人id
     * @param sendUserName 发送人名称
     * @param toUserId     接受人id
     * @param toUserName   接受人名称
     * @param msgTempPcode 站内信模板编码
     * @param params       替换参数
     * @return
     */
    public MessageSiteRecords addMessageSiteRecordTemple(long sendUserId, String sendUserName, long toUserId, String toUserName, String msgTempPcode, Map<String, Object> params) {
        // 找到模板
        MessageTemplate messageTemplate = messageTemplateService.selectMessageTemplateByMsgTempPcode(msgTempPcode);
        if (messageTemplate == null) {
            log.error("消息模板不存在，模板编码:" + msgTempPcode);
            return null;
        }

        if (StringUtils.isBlank(messageTemplate.getMsgTempContent())) {
            return null;
        }

        StringWriter title = new StringWriter();
        StringWriter content = new StringWriter();
        if (params != null && params.size() > 0) {

            VelocityContext velocityContext = new VelocityContext();
            params.forEach((k, v) -> velocityContext.put(k, v));
            // 渲染内容模板
            Velocity.evaluate(velocityContext, title, "", messageTemplate.getMsgTempTitle());
            Velocity.evaluate(velocityContext, content, "", messageTemplate.getMsgTempContent());

        } else {
            title.append(messageTemplate.getMsgTempTitle());
            content.append(messageTemplate.getMsgTempContent());
        }

        return this.addMessageSiteRecord(sendUserId, sendUserName, toUserId, toUserName, title.toString(), content.toString());
    }

    /**
     * 新增加站内信
     *
     * @param sendUserId   发送人id
     * @param sendUserName 发送人名称
     * @param toUserId     接受人id
     * @param toUserName   接受人名称
     * @param title        标题
     * @param content      内容
     * @return
     */
    public MessageSiteRecords addMessageSiteRecord(long sendUserId, String sendUserName, long toUserId, String toUserName, String title, String content) {
        MessageSiteRecords messageSiteRecords = new MessageSiteRecords();
        messageSiteRecords.setToUserId(toUserId <= 0 ? 0 : toUserId);
        messageSiteRecords.setToUserName(toUserId <= 0 ? "【全体成员】" : toUserName);
        messageSiteRecords.setSendUserId(sendUserId);
        messageSiteRecords.setSendUserName(sendUserName);
        messageSiteRecords.setHasRead(toUserId <= 0 ? "1" : "0");
        messageSiteRecords.setMsgSiteTitle(title);
        messageSiteRecords.setMsgSiteContent(content);
        messageSiteRecords.setCreateTime(DateUtils.getNowDate());
        return messageSiteRecordsService.insertMessageSiteRecords(messageSiteRecords) > 0 ? messageSiteRecords : null;
    }


}
