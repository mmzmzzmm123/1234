package com.ruoyi.api.common.mqconsumer;

import com.ruoyi.common.constant.WxMpTemplateIdConstants;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxMpTemplateMassageService;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LAM
 * @date 2023/11/4 10:46
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_DISTRIBUTION_RELATION_BIND_NOTICE, consumerGroup = MqConstants.GROUP_DISTRIBUTION_RELATION_BIND_NOTICE, selectorExpression = "*")
public class DistributionRelationBindNoticeMqConsumer implements RocketMQListener<List<Long>> {

    private final UserInfoMapper userInfoMapper;
    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final WxMpTemplateMassageService wxMpTemplateMassageService;
    private final WxProperties wxProperties;

    @Override
    public void onMessage(List<Long> userIdList) {
        log.info("mq消费-分销关系绑定通知：开始，参数：{}", userIdList);
        for (Long userId : userIdList) {
            String userUnionId = userInfoMapper.getUnionIdById(userId);
            if (StringUtils.isNotBlank(userUnionId)) {
                String userOpenId = userOfficialAccountMapper.getOpenIdByUnionId(userUnionId);
                if (StringUtils.isNotBlank(userOpenId)) {
                    // 构建用户消息提醒
                    WxMpTemplateMessage userMessage = new WxMpTemplateMessage();
                    userMessage.setToUser(userOpenId);
                    userMessage.setTemplateId(WxMpTemplateIdConstants.USER_BIND_SUCCESS);
                    // 跳转到小程序中
                    WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                    miniProgram.setPagePath("distributionPackages/page/index/index");
                    miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
                    userMessage.setMiniProgram(miniProgram);
                    // 模板数据
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("thing1", "已绑定新的分销客户");
                    dataMap.put("time2", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, DateUtils.getNowDate()));
                    List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
                    userMessage.setData(wxMpTemplateData);
                    wxMpTemplateMassageService.wxMpSendTemplateMessage(userMessage);
                }
            }
        }
        log.info("mq消费-分销关系绑定通知：完成");
    }
}
