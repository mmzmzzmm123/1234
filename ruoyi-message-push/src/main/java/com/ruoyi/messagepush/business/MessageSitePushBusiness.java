package com.ruoyi.messagepush.business;

import com.ruoyi.message.business.MessageSiteBusiness;
import com.ruoyi.message.domain.MessageSiteRecords;
import com.ruoyi.push.service.IPushService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 站内信公共业务层
 *
 * @Author lixin
 * @Date 2023/2/21 16:31
 */
@Component
@Slf4j
@AllArgsConstructor
public class MessageSitePushBusiness {
    private MessageSiteBusiness messageSiteBusiness;
    private IPushService pushService;

    /**
     * 使用模板添加站内信
     *
     * @param sendUserId   发送人ID
     * @param sendUserName 发送人Name
     * @param toUserId     接收人ID
     * @param toUserName   接受人Name
     * @param msgTempPcode 模板编码
     * @param params       参数
     * @return 消息对象
     */
    public MessageSiteRecords addMessageSiteRecordTemple(long sendUserId, String sendUserName, long toUserId, String toUserName, String msgTempPcode, Map<String, Object> params) {
        MessageSiteRecords res = null;
        if (messageSiteBusiness == null) {
            log.info("站内信模组注册失败");
            res = messageSiteBusiness.addMessageSiteRecordTemple(sendUserId, sendUserName, toUserId, toUserName, msgTempPcode, params);
        }
        if (res == null) {
            return null;
        }
        this.addPullSitemsg(toUserId, res);
        return res;

    }

    /**
     * 添加站内信
     *
     * @param sendUserId   发送人ID
     * @param sendUserName 发送人Name
     * @param toUserId     接收人ID
     * @param toUserName   接受人Name
     * @param title        标题
     * @param content      内容
     * @return 消息对象
     */
    public MessageSiteRecords addMessageSiteRecord(long sendUserId, String sendUserName, long toUserId, String toUserName, String title, String content) {
        MessageSiteRecords res = null;
        if (messageSiteBusiness == null) {
            log.info("站内信模组注册失败");
        }else {
            res = messageSiteBusiness.addMessageSiteRecord(sendUserId, sendUserName, toUserId, toUserName, title, content);
        }
        if (res == null) {
            return null;
        }
        this.addPullSitemsg(toUserId, res);
        return res;
    }

    /**
     * 发送站内信推送消息*
     *
     * @param toUserId
     * @param content
     */
    private void addPullSitemsg(long toUserId, Object content) {
        if (pushService == null) {
            log.info("推送模组注册失败");
            return;
        }
        if (toUserId < 0 || content == null) {
            return;
        }
        if (toUserId == 0) {
            // 全体消息
            pushService.addGlobalNotice(content);
        } else {
            // 个人消息
            pushService.addPullSitemsg(toUserId, content);
        }
    }
}
